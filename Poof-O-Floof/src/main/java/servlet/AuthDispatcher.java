package servlet;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.UserDao;
import dao.UserDaoImpl;
import handler.AuthHandler;
import model.User;
import model.UserLogin;
import util.Json;

public class AuthDispatcher implements Dispatcher{

	private final UserDao userDao = UserDaoImpl.getInstance();
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public boolean supports(HttpServletRequest req) {
		return isLogin(req) || isLogout(req) || isRegister(req);
	}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		if(isLogin(req)) {
			AuthHandler.handleLogin(req, resp);
		}
		else if(isLogout(req)) {
			AuthHandler.handleLogout(req, resp);
		}
		else if(isRegister(req)) {
			AuthHandler.handleRegister(req, resp);
		}
	}

	private boolean isLogin(HttpServletRequest req) {
		return req.getMethod().equals("POST") && req.getRequestURI().equals("/Poof-O-Floof/api/login");
	}
	
	private boolean isLogout(HttpServletRequest req) {
		return req.getMethod().equals("POST") && req.getRequestURI().equals("/Poof-O-Floof/api/logout");
	}
	
	private boolean isCurrentUser(HttpServletRequest req) {
		return req.getMethod().equals("GET") && req.getRequestURI().equals("/Poof-O-Floof/api/info");
	}
	
	private boolean isRegister(HttpServletRequest req) {
		return req.getMethod().equals("POST") && req.getRequestURI().equals("/Poof-O-Floof/api/register");
	}
}
