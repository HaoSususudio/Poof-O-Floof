package driver;


import java.net.MalformedURLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import petfinder.PetFinderConnectionUtil;

public class MainDriver {
	private static Logger logger = LogManager.getRootLogger();
	private static PetFinderConnectionUtil petFinderConnInst = PetFinderConnectionUtil.getInstance();
	
	public static void main(String[] args) {
		testPetFinder();
	}
	
	private static void testPetFinder() {
		try {
			petFinderConnInst.requestNewToken();
		    logger.debug(petFinderConnInst.getCurrentToken());
		    petFinderConnInst.requestAnimalsByLocation("38.953524,-77.350450", 20);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
