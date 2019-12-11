package servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.LocationRequest;
import petfinder.PetFinderConnectionUtil;
import util.Json;

/**
 * 
 * @author Tim
 *
 */
public class LocationDispatcher implements Dispatcher {
	private static final Logger logger = LogManager.getLogger();
	private static PetFinderConnectionUtil pfcu = PetFinderConnectionUtil.getInstance();
	private static final int RADIUS = 50;

	@Override
	public boolean supports(HttpServletRequest request) {
		logger.info(isPostLocation(request));
		return isPostLocation(request);
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		try {
			// get the token
			pfcu.requestNewToken();

			// get coordinates
			LocationRequest locReq = (LocationRequest) Json.read(request.getInputStream(), LocationRequest.class);
			// if location object is not null
			if (locReq != null) {
				String location = locReq.getCoords();
				logger.info("location is: " + location);
				String animals = pfcu.requestAnimalsByLocation(location, RADIUS);

				// pass only photos to front end
				logger.debug("A Photo Bundle is sent to the requester.");
				logger.info("Animal sent:" + animals);
				response.setHeader("Content-type", "application/json");
				response.getWriter().write(animals);
				return;
			} else {
				String animals = pfcu.requestAnimals();

				// pass only photos to front end
				logger.debug("A Photo Bundle is sent to the requester.");
				logger.info("Animal sent:" + animals);
				response.setHeader("Content-type", "application/json");
				response.getWriter().write(animals);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isPostLocation(HttpServletRequest req) {
		return req.getMethod().equals("POST") && req.getRequestURI().contentEquals("/Poof-O-Floof/api/location");
	}

}
