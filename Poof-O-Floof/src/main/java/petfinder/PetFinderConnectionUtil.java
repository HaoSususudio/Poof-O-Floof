package petfinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;

import util.Json;
import visitor.pattern.PetfinderUrlVisitor;

/**
 * Request animals from Petfinder.com API.
 * @see <a href="https://www.petfinder.com/developers/v2/docs/">Petfinder Dev Docs</a>
 * 
 * 
 * @author Tim
 *
 * @apiNote Originally made by Hao, Updated by Tim
 */
public class PetFinderConnectionUtil {
	private static final PetFinderConnectionUtil instance = new PetFinderConnectionUtil();
	private static PetfinderUrlVisitor petVisitor = PetfinderUrlVisitor.getInstance();

	private static final Pattern pat = Pattern.compile(".*\"access_token\"\\s*:\\s*\"([^\"]+)\".*");
	private static Logger logger = LogManager.getRootLogger();
	
	private static final String authentication = Base64.getEncoder().encodeToString(petVisitor.getAuth().getBytes());
	private static String currentToken;
	
	private static boolean pageMaxSet = false;

	private PetFinderConnectionUtil() {
	}

	/**
	 * Request a new Token from the <strong>PetFinder API</strong>
	 * @throws MalformedURLException
	 */
	public void requestNewToken() throws MalformedURLException {
		URL tokenUrl = petVisitor.getTokenURL();
		String content = "grant_type=client_credentials";
		BufferedReader reader = null;
		HttpsURLConnection connection = null;

		try {
			connection = (HttpsURLConnection) tokenUrl.openConnection();
			logger.debug("tokenUrl: " + tokenUrl);
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Basic " + authentication);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Accept", "application/json");
			PrintStream prntStrm = new PrintStream(connection.getOutputStream());
			prntStrm.print(content);
			prntStrm.close();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = null;
			StringWriter out = new StringWriter(
					connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
			String response = out.toString();
			logger.info("TOKEN: {}", response);
			Matcher matcher = pat.matcher(response);
			if (matcher.matches() && matcher.groupCount() > 0) {
				currentToken = matcher.group(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			connection.disconnect();
		}
	}

	/**
	 * Get animals based on the location of the user.
	 * @param location: city, state; latitude,longitude; or postal code.
	 * @param distance:   in miles
	 * @throws MalformedURLException
	 */
	public String requestAnimalsByLocation(String location, int distance) throws MalformedURLException {
		URL apiUrl = petVisitor.urlBuilder(location, distance, 1);
		HttpsURLConnection connection = null;
		try {
			connection = (HttpsURLConnection) apiUrl.openConnection();
			connection.setRequestProperty("Authorization", "Bearer " + currentToken);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = null;
			StringWriter out = new StringWriter(
					connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
			String response = out.toString();
			reader.close();
			out.close();
			StringBuilder sbResponse = parseOutAnimalInformation(response);
			if (null != sbResponse) {
				return "[" + sbResponse + "]";
			} else {
				logger.debug("animal Json object was null {}", sbResponse);
			}
		} catch (Exception e) {
			logger.warn("Exception Message: {}", e.getMessage());
			logger.warn("Stack Trace: ", e);
		} finally {
			connection.disconnect();
		}
		return null;
	}
	
	/**
	 * Get animals with no restrictions
	 * @throws MalformedURLException
	 */
	public String requestAnimals() throws MalformedURLException {
		URL apiUrl = petVisitor.urlBuilder(null, -1, 1);
		HttpsURLConnection connection = null;
		try {
			connection = (HttpsURLConnection) apiUrl.openConnection();
			connection.setRequestProperty("Authorization", "Bearer " + currentToken);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = null;
			StringWriter out = new StringWriter(
					connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
			String response = out.toString();
			reader.close();
			out.close();
			StringBuilder sbResponse = parseOutAnimalInformation(response);
			if (null != sbResponse) {
				return "[" + sbResponse + "]";
			} else {
				logger.debug("animal Json object was null {}", sbResponse);
			}
		} catch (Exception e) {
			logger.warn("Exception Message: {}", e.getMessage());
			logger.warn("Stack Trace: ", e);
		} finally {
			connection.disconnect();
		}
		return null;
	}

	/**
	 * This will build a JSON out of the response from petfinder.com for our needs.
	 * id and photos. If there is an animal without photos, that animal will be
	 * removed from the creation.
	 * 
	 * @param response = the information from petfinder.com
	 * @return StringBuilder JSON value OR NULL. if null something went wrong.
	 * 
	 * @apiNote The Json format looks like:
	 * <code>
	   	[{
        	"id": 46794611,
        	"photoId": 1575856900,
        	"type": "Dog",
        	"fullUrl": https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/46794611/4/?bust=1575856900
    	}]
    	</code>
	 */
	private StringBuilder parseOutAnimalInformation(String response) {
		JsonNode jsonNode = Json.readString(response, PetFinderConnectionUtil.class);
		StringBuilder sb = new StringBuilder("");
		boolean firstForPassed = false;
		// Create Json formatted String
		for (JsonNode animal : jsonNode.get("animals")) {
			if (null != (animal.get("photos").get(0))) {
				for (JsonNode photo : animal.get("photos")) {
					if (firstForPassed) {
						sb.append(",");
					} else {
						firstForPassed = true;
					}
					String urlString = "" + photo.get("full");
					String[] urlArray = urlString.replace("\"", "").split("=");
					if (urlArray.length > 1) {
						// add every picture to the StringBuilder. 
						sb.append("{\"animalId\":" + animal.get("id") 
								+ ",\"photoId\":" + urlArray[1] 
								+ ",\"type\":" + animal.get("type") 
								+ ",\"fullUrl\":" + "\"" + urlArray[0] + "=" + urlArray[1] + "\""
								+ ",\"url\":" + animal.get("url")
								+ "}");
						// logger information in JSON format. 
						logger.trace("\n{" + "\n\t\"animalId\":" + animal.get("id") 
								+ ",\n\t\"photoId\":" + urlArray[1]
								+ ",\"type\":" + animal.get("type") 
								+ ",\n\t\"fullUrl\":" + "\"" + urlArray[0] + "="
								+ ",\n\t\"url\":" + animal.get("url")
									+ urlArray[1] + "\"" + "\n}");
					}
				}
			}
		}
		
		// if you want to prevent wasting memory by setting an Integer everytime. once this runs, set it pageMaxSet = true;
		if(!pageMaxSet) {
			// pageMaxSet = true; // set true to prevent resetting the total page count
			petVisitor.setRandomNumber(Integer.parseInt(jsonNode.get("pagination").get("total_pages").toString()));
		}
		return sb;
	}

	/**
	 * @deprecated Not currently used.
	 * @param response
	 * @return
	 */
//	private List<Photo> parseAnimalInformationAsList(String response) {
//		JsonNode jsonNode = null;
//		ObjectMapper om = new ObjectMapper();
//		try {
//			jsonNode = om.readValue(response, JsonNode.class);
//		} catch (IOException e) {
//			logger.warn("IOException Message: {}", e.getMessage());
//			logger.warn("Stack Trace: ", e);
//		}
//		JsonNode animals = jsonNode.get("animals");
//		List<Photo> animalList = new ArrayList<Photo>();
//
//		for (JsonNode animal : animals) {
//			if (null != (animal.get("photos").get(0))) {
//				for (JsonNode photo : animal.get("photos")) {
//					String urlString = "" + photo.get("full");
//					String[] urlArray = urlString.split("=");
//					if (urlArray.length > 1) {
//						String photoId = urlArray[1];
//						animalList.add(new Photo("" + animal.get("id"), photoId.replace("\"", ""),
//								urlString.replace("\"", "")));
//					}
//				}
//			}
//		}
//		logger.info(animalList);
//		return animalList;
//	}

	/**
	 * 
	 * @return
	 */
	public String getCurrentToken() {
		return currentToken;
	}

	/**
	 * 
	 * @return
	 */
	public static PetFinderConnectionUtil getInstance() {
		return instance;
	}
}
