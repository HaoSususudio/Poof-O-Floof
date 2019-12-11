package handler;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import model.UserCreation;
import model.UserLogin;
import util.AuthUtil;
import util.Exceptions;
import util.Json;

public class AuthHandler {

	private static final UserDao userDao = UserDaoImpl.getInstance();
	private static final Logger logger = LogManager.getLogger();
	
	public static void handleLogin(HttpServletRequest req, HttpServletResponse resp) {
		//add way to get user ip?
		try {
			UserLogin cred = (UserLogin) Json.read(req.getInputStream(), UserLogin.class);
			logger.trace("provided credentials: " + cred);
			
			//get user from username
			User logUser = userDao.findByUsername(cred.getUsername());
			logger.trace("user with matching username " + logUser);
			
			//if username not found, set status code and return
			if(logUser == null) {
				logger.info("user not found");
				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
			else {	
				//verify
				if(AuthUtil.verifyPassword(cred.getPassword(), logUser.getKey(), logUser.getSalt())) {
					//User user = userDao.login(cred);
					resp.setContentType(Json.CONTENT_TYPE);
					//storing user id in cookie because i dont want to store a name
					Cookie cookie = new Cookie("currentUser", Integer.toString(logUser.getUserId()));
					cookie.setPath("/Poof-O-Floof/api");
					resp.addCookie(cookie);
									
					resp.getOutputStream().write(Json.write(logUser));
					return;
				}
				else {
					logger.info("incorrect password");
					resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void handleLogout(HttpServletRequest req, HttpServletResponse resp) {
	    //make cookie with no value or lifespan
		Cookie cookie=new Cookie("currentUser", null);
	    cookie.setMaxAge(0);
	    //replace cookie
	    resp.addCookie(cookie);
	}
	
	public static void handleRegister(HttpServletRequest req, HttpServletResponse resp) {
		try {
			//get user info from front end
			UserCreation userCreation = (UserCreation) Json.read(req.getInputStream(), UserCreation.class);
			
			//set secure key and salt
			Optional<String> salt = AuthUtil.generateSalt();
			Optional<String> key = AuthUtil.hashPassword(userCreation.getPassword(), salt.get());
			
			//get user data
			String displayName = userCreation.getDisplayName();
			
			//clear provided password
			userCreation = null;
			
			User newUser = new User(666, displayName, key.get(), salt.get());
			
			//check if username is already taken
			//return status code 409 if so
			if(userDao.findByUsername(displayName) == null) {
				//save user to database
				userDao.createUser(newUser);
				return;
			}
			else {
				//set status code to 409
				resp.setStatus(HttpServletResponse.SC_CONFLICT);
				return;
			}
		}
		catch(IOException e) {
			Exceptions.logJsonUnmarshalException(e, AuthHandler.class);
		}
	}
}
