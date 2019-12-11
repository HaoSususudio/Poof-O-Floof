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
				int userId = rs.getInt("user_id");
				String currentIp = rs.getString("current_ip");
				String currentIpLocation = rs.getString("current_ip_location");
				String displayName = loginCredentials.getUsername();
				return new User(userId, currentIp, currentIpLocation, displayName);
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

	//ask where to get secure key
	@Override
	public boolean createUser(UserCreation userCreation) {
		try(Connection conn = ConnectionUtil.getConnection()){
			return sqlVisitor.saveUsers(conn, userCreation).executeUpdate() == 1;
		}
		catch(SQLException e) {
			Exceptions.logSQLException(e);
			return false;
		}
	}

}
