package handler;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import model.UserLogin;
import util.Json;

public class AuthHandler {

	private static final UserDao userDao = UserDaoImpl.getInstance();
	private static final Logger logger = LogManager.getLogger();
	
	public static void handleLogin(HttpServletRequest req, HttpServletResponse resp) {
		try {
			UserLogin cred = (UserLogin) Json.read(req.getInputStream(), UserLogin.class);
			logger.trace(cred);
			User user = userDao.login(cred);
			
			if(user != null) {
				resp.setContentType(Json.CONTENT_TYPE);
				//storing user id in cookie because i dont want to store a name
				Cookie cookie = new Cookie("currentUser", Integer.toString(user.getUserId()));
				cookie.setPath("/Poof-O-Floof/api");
				resp.addCookie(cookie);
								
				resp.getOutputStream().write(Json.write(user));
				return;
			}
			else {
				resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
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
}
