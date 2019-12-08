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

public class UserDaoImpl implements UserDao {

	private final Logger logger = LogManager.getLogger(getClass());
	
	public static final String CHECK_LOGIN = "SELECT * FROM users WHERE display_name = ? AND salt = ?";
	
	public static final String CREATE_USER = "INSERT INTO users (user_id, current_ip, current_ip_location, display_name, email, secure_key, salt) "
			+ "VALUES (user_id_seq.nextval, ?, ?, ?, ?, ?, ?)";
				
	//ask about that salt thing
	
	@Override
	public User login(UserLogin loginCredentials) {
		//check if user with those credentials is in database
		//if yes, return user
		//if no, log error
		// TODO Auto-generated method stub
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(CHECK_LOGIN);
			
			int stIndex = 0;
			ps.setString(++stIndex, loginCredentials.getUsername());
			ps.setString(++stIndex, loginCredentials.getPassword());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				String displayName = loginCredentials.getUsername();
				int userId = rs.getInt("user_id");
				String currentIp = rs.getString("current_ip");
				String currentIpLocation = rs.getString("current_ip_location");
				
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
			PreparedStatement ps = conn.prepareStatement(CREATE_USER);
			
			int stIndex = 0;
			ps.setString(++stIndex, userCreation.getCurrentIp());
			ps.setString(++stIndex, userCreation.getCurrentIpLocation());
			ps.setString(++stIndex, userCreation.getDisplayName());
			ps.setString(++stIndex, userCreation.getEmail());
			ps.setString(++stIndex, "?");
			ps.setString(++stIndex, userCreation.getPassword());
			
			return ps.executeUpdate() == 1;
		}
		catch(SQLException e) {
			Exceptions.logSQLException(e);
			return false;
		}
	}

}
