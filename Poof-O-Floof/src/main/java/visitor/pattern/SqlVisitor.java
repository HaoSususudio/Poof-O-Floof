package visitor.pattern;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Photo;
import model.User;
import model.UserCreation;
import model.UserLogin;

public interface SqlVisitor {

	/**
	 * get all saved photos that were saved by a select user
	 * @param conn
	 * @param userId
	 * @return a {@link PreparedStatement}
	 * @throws SQLException
	 */
	PreparedStatement selectPhotoByUserId(Connection conn, int userId) throws SQLException;
	
	/**
	 * find a user by username
	 * @param conn
	 * @param username
	 * @return a {@link PreparedStatement}
	 * @throws SQLException
	 */
	PreparedStatement selectUserByUsername(Connection conn, String username) throws SQLException;
	
	/**
	 * check credentials of a user for login. 
	 * @param conn
	 * @param loginCredentials
	 * @return a {@link PreparedStatement}
	 * @throws SQLException
	 */
	PreparedStatement login(Connection conn, UserLogin loginCredentials) throws SQLException;

	/**
	 * Creates/saves a new user to the DB
	 * @param conn
	 * @param userCreation
	 * @return a {@link PreparedStatement}
	 * @throws SQLException
	 */
	PreparedStatement saveUsers(Connection conn, User user) throws SQLException;

	/**
	 * Saves a photo linked to the users ID. 
	 * @param conn
	 * @param userId
	 * @param photo
	 * @return a {@link PreparedStatement}
	 * @throws SQLException
	 */
	PreparedStatement savePhoto(Connection conn, int userId, Photo photo) throws SQLException;

}
