package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.User;
import model.UserCreation;
import model.UserLogin;
import util.ConnectionUtil;
import util.Exceptions;
import visitor.pattern.SqlPreparedVisitor;

public class UserDaoImpl implements UserDao {

	private static SqlPreparedVisitor sqlVisitor = SqlPreparedVisitor.getInstance();
	private static final UserDao instance = new UserDaoImpl();
	
	private UserDaoImpl() {}
	
	public static UserDao getInstance() {
		return instance;
	}
	
	private final Logger logger = LogManager.getLogger(getClass());
				
	//ask about that salt thing
	
	@Override
	public User login(UserLogin loginCredentials) {
		//check if user with those credentials is in database
		//if yes, return user
		//if no, log error
		try(Connection conn = ConnectionUtil.getConnection()){
			ResultSet rs = sqlVisitor.login(conn, loginCredentials).executeQuery();
			
			if(rs.next()) {
				String displayName = loginCredentials.getUsername();
				int userId = rs.getInt("user_id");
				String key = rs.getString("secure_key");
				String salt = rs.getString("salt");
				
				return new User(userId, displayName, key, salt);
			}
			else {
				logger.warn("User with credentials: " + loginCredentials + " not found");
				return null;
			}
		}
		catch(SQLException e) {
			Exceptions.logSQLException(e);
			return null;
		}
	}

	@Override
	public User findByUsername(String username) {
		try(Connection conn = ConnectionUtil.getConnection()){
			ResultSet rs = sqlVisitor.selectUserByUsername(conn, username).executeQuery();
			
			if(rs.next()) {
				String displayName = username;
				int userId = rs.getInt("user_id");
				String key = rs.getString("secure_key");
				String salt = rs.getString("salt");
				
				return new User(userId, displayName, key, salt);
			}
			else {
				logger.warn("User with username: " + username + " not found");
				return null;
			}
		}
		catch(SQLException e) {
			Exceptions.logSQLException(e);
			return null;
		}
	}
	
	@Override
	public boolean createUser(User user) {
		try(Connection conn = ConnectionUtil.getConnection()){
			return sqlVisitor.saveUsers(conn, user).executeUpdate() == 1;
		}
		catch(SQLException e) {
			Exceptions.logSQLException(e);
			return false;
		}
	}



}
