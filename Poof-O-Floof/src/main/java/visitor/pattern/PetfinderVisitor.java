package visitor.pattern;

import java.net.MalformedURLException;
import java.net.URL;

public interface PetfinderVisitor {

	/**
	 * No longer in use.
	 * @deprecated
	 * @param str
	 * @return {@link URL}
	 * @throws MalformedURLException
	 * @apiNote It would be preferred to insert values into {@code urlBuilder} instead of <strong>this</strong> method
	 * it is left just incase a dev needs to create a custom URL for PetFinder.com. 
	 */
	public URL makePetfinderURL(String str) throws MalformedURLException;

	/**
	 * creates a url to <a href="https://www.petfinder.com">Petfinder</a> to retrieve animals. 
	 * @param location = the location example: <strong>"38.953524,-77.350450"</strong>, 
	 * 					leave <b>null</b> or <b>""</b> to exclude
	 * @param status = the desired status example: <strong>"adopted"</strong>, 
	 * 					leave <b>null</b> or <b>""</b> to exclude
	 * @param distance = the radius desired to search,
						leave <b>-1</b> to exclude <b>OR</b> leave <b>0</b> to default to distance of 10
	 * @param page = the page number, 
	 * 					leave <b>-1</b> to exclude <b>OR</b> leave <b>0</b> to default to page 1
	 * @return {@link URL}
	 * @throws MalformedURLException
	 */
	public URL urlBuilder(String location, String status, int distance, int page) throws MalformedURLException;

	/**
	 * 
	 * @return {@link URL}
	 * @throws MalformedURLException
	 */
	public URL getTokenURL() throws MalformedURLException;

	/**
	 * 
	 * @return {@link URL}
	 * @throws MalformedURLException
	 */
	public URL getPetFinderApiRootURL() throws MalformedURLException;
}
