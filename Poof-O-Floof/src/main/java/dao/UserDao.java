package dao;

import model.User;
import model.UserCreation;
import model.UserLogin;

public interface UserDao {
	User login(UserLogin loginCredentials);
	
	User findByUsername(String username);
	
	boolean createUser(User user);
}
