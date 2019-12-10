package visitor.pattern;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Using the visitor design pattern to replace the need of magic strings within
 * {@link petfinder.PetFinderConnectionUtil} and to create {@link URL}s.
 * 
 * @author Tim
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Visitor_pattern">Wikipedia</a>
 * @see <a href="https://blog.jooq.org/tag/design-pattern/">design-pattern</a>
 *
 */
public class PetfinderUrlVisitor implements PetfinderVisitor {
	private static final PetfinderUrlVisitor instance = new PetfinderUrlVisitor();
	private static Logger logger = LogManager.getRootLogger();
	private static Properties props = getProperties();
	private static final String PET_FINDER_API_KEY = props.getProperty("petfinder.key");
	private static final String PET_FINDER_SECRET = props.getProperty("petfinder.secret");

	private static final String TOKEN_URL = "https://api.petfinder.com/v2/oauth2/token";
	private static final String PET_FINDER_API_ROOT = "https://api.petfinder.com/v2/";
	
	private static int randomNumberMax = 20;

	private PetfinderUrlVisitor() {}
	
	public static PetfinderUrlVisitor getInstance() {
		return instance;
	}

	public String getAuth() {
		return PET_FINDER_API_KEY + ":" + PET_FINDER_SECRET;
	}
	
	@Override
	public URL makePetfinderURL(String str) throws MalformedURLException {
		return new URL(str);
	}

	private static boolean placeQuestionMark;
	@Override
	public URL urlBuilder(String location, String status, int distance, int page) throws MalformedURLException {
		placeQuestionMark = false;
		StringBuilder sb = new StringBuilder(getPetFinderAnimalsString());
		if (location != null && !"".equals(location)) {
			placeQuestionMark = true;
			sb.append("?" + getLocationString(location));
		}
		if (status != null && !"".equals(status)) {
			if (placeQuestionMark) {
				sb.append("&" + getStatusString(status));
			} else {
				placeQuestionMark = true;
				sb.append("?" + getStatusString(status));
			}
		}
		sb.append(buildDistanceString(distance));
		sb.append(buildPageString(page));
		logger.info(sb.toString());
		return new URL(sb.toString());
	}

	/**
	 * Build the string for the Distance part of the URL
	 * @param distance
	 * @return
	 */
	private String buildDistanceString(int distance) {
		if(distance < 0) {
			return "";
		} else if (distance > 0) {
			if (placeQuestionMark) {
				return "&" + getDistanceString(distance);
			} else {
				placeQuestionMark = true;
				return "?" + getDistanceString(distance);
			}
		} else {
			if (placeQuestionMark) {
				return "&" + getDistanceString(10);
			} else {
				placeQuestionMark = true;
				return "?" + getDistanceString(10);
			}
		}
	}
	
	/**
	 * Build the string for the Page part of the URL
	 * @param page
	 * @return
	 */
	private String buildPageString(int page) {
		if (page > 0) {
			if (placeQuestionMark) {
				return "&" + getPageString(randomNumber());
			} else {
				return "?" + getPageString(randomNumber());
			}
		} else if(page < 0) {
			return "";
		} else {
			if (placeQuestionMark) {
				return "&" + getPageString(1);
			} else {
				return "?" + getPageString(1);
			}
		}
	}
	
	private int randomNumber() {
		return (int) (Math.random() * (PetfinderUrlVisitor.randomNumberMax - 1)) + 1;
	}
	
	public void setRandomNumber(int randomNumberMax) {
		logger.debug("randomNumberMax = {}", randomNumberMax);
		PetfinderUrlVisitor.randomNumberMax = randomNumberMax;
	}

	public String getTokenString() {
		return TOKEN_URL;
	}

	@Override
	public URL getTokenURL() throws MalformedURLException {
		return new URL(TOKEN_URL);
	}

	public String getPetFinderApiRootString() {
		return PET_FINDER_API_ROOT;
	}

	@Override
	public URL getPetFinderApiRootURL() throws MalformedURLException {
		return new URL(PET_FINDER_API_ROOT);
	}

	public String getPetFinderAnimalsString() {
		return PET_FINDER_API_ROOT + "animals";
	}

	private String getLocationString(String location) {
		return String.format("location=%s", location);
	}

	private String getStatusString(String status) {
		return String.format("status=%s", status);
	}

	private String getDistanceString(int distance) {
		return String.format("distance=%d", distance);
	}

	private String getPageString(int page) {
		return String.format("page=%d", page);
	}

	/**
	 * load properties from {@link application.properties}
	 * 
	 * @return
	 */
	private static Properties getProperties() {
		try {
			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
			return props;
		} catch (IOException | NullPointerException e) {
			logger.error("Unable to locate Properties at src/main/resources/application.properties");
			logger.error("Stack Trace: ", e);
			throw new RuntimeException("Check Logs; Failed to get properties");
		}
	}
}
