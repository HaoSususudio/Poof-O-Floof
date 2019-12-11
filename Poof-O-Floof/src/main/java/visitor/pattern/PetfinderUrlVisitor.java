package visitor.pattern;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of {@link visitor.pattern.PetfinderVisitor}
 * 
 * @author Tim
 */
public class PetfinderUrlVisitor implements PetfinderVisitor {
  private static final PetfinderUrlVisitor instance = new PetfinderUrlVisitor();
  private static Logger logger = LogManager.getRootLogger();
  private static Properties props = getProperties();
  private static final String PET_FINDER_API_KEY = props.getProperty("petfinder.key");
  private static final String PET_FINDER_SECRET = props.getProperty("petfinder.secret");

  private static final String TOKEN_URL = "https://api.petfinder.com/v2/oauth2/token";
  private static final String PET_FINDER_API_ROOT = "https://api.petfinder.com/v2/";

  private static int randomNumberMax = 10;

  private PetfinderUrlVisitor() {
  }

  public static PetfinderUrlVisitor getInstance() {
    return instance;
  }

  /**
   * Returns the <a href="petfinder.com">PetFinder.com</a> API key.
   * 
   * @return {@link String}
   */
  public String getAuth() {
    return PET_FINDER_API_KEY + ":" + PET_FINDER_SECRET;
  }

  @Override
  public URL makePetfinderURL(String str) throws MalformedURLException {
    return new URL(str);
  }

  @Override
  public URL urlBuilder(String location, int distance, int page) throws MalformedURLException {
    StringBuilder sb = new StringBuilder(getPetFinderAnimalsString());
    sb.append("?status=adoptable");
    if (location != null && !"".equals(location)) {
      sb.append("&" + getLocationString(location));
    }
    sb.append(buildDistanceString(distance));
    sb.append(buildPageString(page));
    logger.info(sb.toString());
    return new URL(sb.toString());
  }

  /**
   * Build the string for the Distance part of the URL
   * 
   * @param distance
   * @return {@link String}
   */
  private String buildDistanceString(int distance) {
    if (distance < 0) {
      return "";
    } else if (distance > 0) {
      return "&" + getDistanceString(distance);
    } else {
      return "&" + getDistanceString(10);
    }
  }

  /**
   * Build the string for the Page part of the URL
   * 
   * @param page
   * @return {@link String}
   */
  private String buildPageString(int page) {
    if (page > 0) {
      return "&" + getPageString(randomNumber());
    } else if (page < 0) {
      return "";
    } else {
      return "&" + getPageString(1);
    }
  }

  /**
   * generate a random number between 1 and <b>randomNumberMax</b>
   * 
   * @return
   */
  private int randomNumber() {
    return (int) (Math.random() * (PetfinderUrlVisitor.randomNumberMax - 1)) + 1;
  }

  /**
   * Set the random number for <b>randomNumberMax</b>
   * 
   * @param randomNumberMax
   */
  public void setRandomNumber(int randomNumberMax) {
    logger.debug("randomNumberMax = {}", randomNumberMax);
    PetfinderUrlVisitor.randomNumberMax = randomNumberMax;
  }

  @Override
  public URL getTokenURL() throws MalformedURLException {
    return new URL(TOKEN_URL);
  }

  /**
   * The url for petfinder: <b>https://api.petfinder.com/v2</b>
   * 
   * @return
   */
  public String getPetFinderApiRootString() {
    return PET_FINDER_API_ROOT;
  }

  @Override
  public URL getPetFinderApiRootURL() throws MalformedURLException {
    return new URL(PET_FINDER_API_ROOT);
  }

  /**
   * The url for petfinder: <b>https://api.petfinder.com/v2/animals</b>
   * 
   * @return {@link String}
   */
  public String getPetFinderAnimalsString() {
    return PET_FINDER_API_ROOT + "animals";
  }

  /**
   * get web page formatting for creating {@link URL}
   * 
   * @deprecated if being used, use it with <strong>makePetfinderURL</strong>
   * @param status
   * @return {@link String}
   */
  @SuppressWarnings("unused")
  private String getStatusString(String status) {
    return String.format("status=%s", status);
  }

  /**
   * get location formatting for creating {@link URL}
   * 
   * @param location
   * @return {@link String}
   */
  private String getLocationString(String location) {
    return String.format("location=%s", location);
  }

  /**
   * get distance formatting for creating {@link URL}
   * 
   * @param distance
   * @return {@link String}
   */
  private String getDistanceString(int distance) {
    return String.format("distance=%d", distance);
  }

  /**
   * get web page formatting for creating {@link URL}
   * 
   * @param page
   * @return {@link String}
   */
  private String getPageString(int page) {
    return String.format("page=%d", page);
  }

  /**
   * load properties from {@link application.properties}
   * 
   * @return {@link Properties}
   */
  private static Properties getProperties() {
    try {
      Properties props = new Properties();
      props.load(Thread.currentThread().getContextClassLoader()
          .getResourceAsStream("application.properties"));
      return props;
    } catch (IOException | NullPointerException e) {
      logger.error("Unable to locate Properties at src/main/resources/application.properties");
      logger.error("Stack Trace: ", e);
      throw new RuntimeException("Check Logs; Failed to get properties");
    }
  }
}
