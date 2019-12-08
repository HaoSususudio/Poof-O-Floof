package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.AnimalBasic;
import model.AnimalFull;
import util.ConnectionUtil;
import util.Exceptions;

public class AnimalDaoImpl implements AnimalDao {

	private static final AnimalDao instance = new AnimalDaoImpl();
	
	private AnimalDaoImpl() {}
	
	public static AnimalDao getInstance() {
		return instance;
	}
	
	public static final String SAVE_ANIMAL = "INSERT INTO animal (animal_id, animal_type, species, age, gender, animal_size) " + 
			"VALUES (?, ?, ?, ?, ?, ?)";
	
	public static final String SAVE_FAVORITE = "INSERT INTO favorite (favorite_id, user_id, animal_id) " +
		    "VALUES (favorite_id_seq.nextval, ?, ?)";
	
	public static final String GET_DAILY_LIKES = "SELECT likes FROM daily_likes WHERE species = ?";
	public static final String UPDATE_DAILY_LIKES = "UPDATE daily_likes SET likes = ? WHERE species = ?";
	public static final String INSERT_DAILY_LIKES = "INSERT INTO daily_likes (daily_likes_id, species, likes, current_date) " +
			"VALUES (daily_likes_id.next, ?, 1, CURRENT_TIMESTAMP)";
	
	//deprecated, probably broken
	public boolean saveFullAnimal(AnimalFull animalFull) {
		// TODO Auto-generated method stub
		return false;
	}

	//deprecated, probably broken
	public boolean saveBasicAnimal(AnimalBasic animalBasic) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = SAVE_ANIMAL;
			
			int stIndex = 0;
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(++stIndex, animalBasic.getId());
			ps.setString(++stIndex, animalBasic.getType());
			ps.setString(++stIndex, animalBasic.getSpecies());
			ps.setString(++stIndex, animalBasic.getAge());
			ps.setString(++stIndex, animalBasic.getGender());
			ps.setString(++stIndex, animalBasic.getSize());
			
			return ps.executeUpdate() == 1;
			
		}
		catch(SQLException e) {
			Exceptions.logSQLException(e);
			return false;
		}
	}
	
	@Override
	public boolean saveFavoritePicture(int animalId, int userId) {
		try(Connection conn = ConnectionUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(SAVE_FAVORITE);
			
			int stIndex = 0;
			ps.setInt(++stIndex, userId);
			ps.setInt(++stIndex, animalId);
			
			return ps.executeUpdate() == 1;
			
		}
		catch(SQLException e) {
			Exceptions.logSQLException(e);
			return false;
		}
	}

	@Override
	public boolean updateDailyLikes(AnimalBasic animalBasic) {
		//check if species is already in table
		//if not, insert
		//if yes, get number of likes and increment
		try(Connection conn = ConnectionUtil.getConnection()){
			//gets 1 row containing only the number of likes
			PreparedStatement getStmt = conn.prepareStatement(GET_DAILY_LIKES);
			int stIndex = 0;
			getStmt.setString(1, animalBasic.getSpecies());
			
			ResultSet rs = getStmt.executeQuery();
			
			//species is already in table
			if(rs.next()) {
				int numLikes = rs.getInt("likes");
				numLikes++;
				PreparedStatement incrStmt = conn.prepareStatement(UPDATE_DAILY_LIKES);
				stIndex = 0;
				incrStmt.setInt(++stIndex, numLikes);
				incrStmt.setString(++stIndex, animalBasic.getSpecies());
				
				return incrStmt.executeUpdate() == 1;
			}
			//species is not in table
			else {
				PreparedStatement insrStmt = conn.prepareStatement(INSERT_DAILY_LIKES);
				stIndex = 0;
				insrStmt.setString(++stIndex, animalBasic.getSpecies());
				
				return insrStmt.executeUpdate() == 1;
				
			}
			
		}
		catch(SQLException e) {
			Exceptions.logSQLException(e);
			return false;
		}
	}

	@Override
	public boolean updateTotalLikes(AnimalBasic animalBasic) {
		// TODO Auto-generated method stub
		return false;
	}



}
