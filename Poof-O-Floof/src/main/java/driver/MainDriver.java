package driver;


import java.net.MalformedURLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.AnimalDao;
import dao.AnimalDaoImpl;
import model.Photo;
import model.User;
import model.UserCreation;
import petfinder.PetFinderConnectionUtil;
import util.AuthUtil;

/**
 * 
 * @author Tim
 *
 */
public class MainDriver {
	private static Logger logger = LogManager.getRootLogger();
	private static PetFinderConnectionUtil petFinderConnInst = PetFinderConnectionUtil.getInstance();
	private static final AnimalDao animalDao = AnimalDaoImpl.getInstance();
	
	public static void main(String[] args) {
		// testPetFinder();
		// testSaveDao();
		// testGetFavoriteList();
		// testAuth();
	}
	
	private static void testPetFinder() {
		PetFinderConnectionUtil petFinderConnInst = PetFinderConnectionUtil.getInstance();
		try {
			petFinderConnInst.requestNewToken();
		    logger.debug(petFinderConnInst.getCurrentToken());
		    petFinderConnInst.requestAnimalsByLocation("38.953524,-77.350450", 20);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private static void testGetFavoriteList() {
		logger.trace(animalDao.getFavoriteList(525252));
	}
	
	private static void testSaveDao() {
		Photo p = new Photo(113, 121,  "spider", "javadog.com/picture.png", "javadog.com");
		logger.trace(animalDao.savePhoto(525252, p));
		//logger.trace(animalDao.saveFavorite(2, 121));
		
	}
	
	private static void testAuth() {
		String testSalt = AuthUtil.generateSalt().get();
		logger.trace("testSalt= " + testSalt);
		String testKey = AuthUtil.hashPassword("duck", testSalt).get();
		logger.trace("testKey= " + testKey);

		logger.trace(AuthUtil.verifyPassword("duck", testKey, testSalt));
		logger.trace(AuthUtil.verifyPassword("goose", testKey, testSalt));
	}

}
