package visitor.pattern;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Photo;
import model.User;
import model.UserCreation;
import model.UserLogin;

public class SqlPreparedVisitor implements SqlVisitor{
	private static SqlPreparedVisitor instance = new SqlPreparedVisitor();

	private SqlPreparedVisitor() {}

	public static SqlPreparedVisitor getInstance() {
		return instance;
	}

	/************************************************************************************
	 * 									Magic Strings									*
	 *  	Some string are placed above their method implementation for quicker		*
	 *  								fixes/changes 									*
	 ***********************************************************************************/
	private static final String SELECT_ALL = "SELECT * FROM ";
	private static final String SELECT_PHOTO_BY_USERID = SELECT_ALL + "photo WHERE user_id = ?";
	private static final String SELECT_USER_BY_USERNAME = SELECT_ALL + "users WHERE display_name = ? ";
	private static final String SELECT_USER_BY_LOGIN = SELECT_USER_BY_USERNAME + "AND salt = ?";

	/**
	 * Not yet implemented
	 */
	private static final String GET_DAILY_LIKES = "SELECT likes FROM daily_likes WHERE species = ?";
	private static final String UPDATE_DAILY_LIKES = "UPDATE daily_likes SET likes = ? WHERE species = ?";
	private static final String INSERT_DAILY_LIKES = "INSERT INTO daily_likes (daily_likes_id, species, likes, current_date) "
			+ "VALUES (daily_likes_id.next, ?, 1, CURRENT_TIMESTAMP)";
	
	/*************************************************************************************
	 * get Logic *
	 ************************************************************************************/
	@Override
	public PreparedStatement selectPhotoByUserId(Connection conn, int userId) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(SELECT_PHOTO_BY_USERID);
		ps.setInt(1, userId);
		return ps;
	}

	@Override
	public PreparedStatement selectUserByUsername(Connection conn, String username) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_USERNAME);
		ps.setString(1, username);
		return ps;
	}
	
	@Override
	public PreparedStatement login(Connection conn, UserLogin loginCredentials) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_LOGIN);
		int stIndex = 0;
		ps.setString(++stIndex, loginCredentials.getUsername());
		ps.setString(++stIndex, loginCredentials.getPassword());
		return ps;
	}

	/*************************************************************************************
	 * Save Logic *
	 ************************************************************************************/
	private static final String SAVE_USERS = "INSERT INTO users (user_id, current_ip, current_ip_location, display_name, email, secure_key, salt) "
			+ "VALUES(user_id_seq.nextval, ?, ?, ?, ?, ?, ?)";
	@Override
	public PreparedStatement saveUsers(Connection conn, User user) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(SAVE_USERS);
		int stIndex = 0;
		ps.setString(++stIndex, "666, 666");
		ps.setString(++stIndex, "behind you");
		ps.setString(++stIndex, user.getDisplayName());
		ps.setString(++stIndex, "dog@cat.com");
		ps.setString(++stIndex, user.getKey());
		ps.setString(++stIndex, user.getSalt());
		return ps;
	}

	private static final String SAVE_PHOTO = "INSERT INTO photo (id, photo_id, animal_id, user_id, animal_type, full_url, url) "
			+ "VALUES (photo_id_seq.nextval, ?, ?, ?, ?, ?, ?)";
	@Override
	public PreparedStatement savePhoto(Connection conn, int userId, Photo photo) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(SAVE_PHOTO);
		int stIndex = 0;
		ps.setInt(++stIndex, photo.getAnimalId());
		ps.setInt(++stIndex, photo.getPhotoId());
		ps.setInt(++stIndex, userId);
		ps.setString(++stIndex, photo.getType());
		ps.setString(++stIndex, photo.getFullUrl());
		ps.setString(++stIndex, photo.getUrl());
		return ps;
	}
}
