package dao;

import model.User;
import model.UserCreation;
import model.UserLogin;

public interface UserDao {
	User login(UserLogin loginCredentials);
	
	boolean createUser(UserCreation userCreation);
}
