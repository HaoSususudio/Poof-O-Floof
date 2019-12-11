package dao;

import java.util.List;

import model.AnimalBasic;
import model.Photo;
import model.ServerFavPhotos;

public interface AnimalDao {

	/************************************************
	 * Save Logic *
	 ***********************************************/
	/**
	 * 
	 * save the all of the information of a photo
	 * 
	 * @param userId = the {@link model.User}'s ID that wanted to save the photo
	 * @param photo  = the {@link model.Photo} with all the information
	 * @return
	 */
	boolean savePhoto(int userId, Photo photo);

	/************************************************
	 * Update Logic *
	 ***********************************************/

	// may need a function to add new column to likes unless it should be handled
	// here
	boolean updateDailyLikes(AnimalBasic animalBasic);

	// maybe have a function to reset daily likes
	boolean updateTotalLikes(AnimalBasic animalBasic);

	/************************************************
	 * Get Logic *
	 ***********************************************/
	/**
	 * 
	 * @param userId = the {@link model.User}'s ID wanting to view saved photos
	 * @return
	 */
	List<ServerFavPhotos> getFavoriteList(int userId);

	/**
	 * @deprecated
	 * @param userId
	 * @param photoId
	 * @return
	 */
	boolean saveFavorite(int userId, int photoId);
}
