package handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.AnimalDao;
import dao.AnimalDaoImpl;
import model.Photo;
import model.ServerFavPhotos;
import util.Exceptions;
import util.Json;

public class FavoriteHandler {

	private static final AnimalDao animalDao = AnimalDaoImpl.getInstance();
	private static final Logger logger = LogManager.getLogger();

	public static void handleSaveFavorite(HttpServletRequest req, HttpServletResponse resp) {
		try {
			logger.trace("here doggy");
			// get photo from json
			Photo photo = (Photo) Json.read(req.getInputStream(), Photo.class);
			// put photo in dao
			int userId = Integer.parseInt(req.getParameter("userId"));
			logger.trace(userId);
			animalDao.savePhoto(userId, photo);
		} catch (IOException e) {
			Exceptions.logJsonUnmarshalException(e, FavoriteHandler.class);
			return;
		}
	}

	public static void handleGetFavorites(HttpServletRequest req, HttpServletResponse resp) {
		// get user id
		int userId = Integer.parseInt(req.getParameter("userId"));
		// get photos from database
		List<ServerFavPhotos> favList = animalDao.getFavoriteList(userId);
		// say so if empty
		if (favList.isEmpty()) {
			resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return;
		} else {
			try {
				// write list
				resp.setHeader("Content-type", "application/json");
				resp.getOutputStream().write(Json.write(favList));
				return;
			} catch (IOException e) {
				Exceptions.logJsonMarshalException(e, FavoriteHandler.class);
				return;
			}
		}
	}
}
