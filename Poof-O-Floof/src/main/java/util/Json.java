package util;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import servlet.LocationDispatcher;

/**
 * 
 * @author Tim
 *
 */
public class Json {

	// For readability/maintainability
	public static final String CONTENT_TYPE = "application/json";

	private static final ObjectMapper mapper = new ObjectMapper();

	// invoke <clinit> (don't have to do this, I like the pretty print)
	static {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	// Restrict Instantiation
	private Json() {
	}

	/**
	 * Replace {@link ObjectMapper} <b>writeValueAsBytes</b> with just one one
	 * object
	 * 
	 * @param o = the <b>Object</b> being passed in
	 * @return
	 */
	public static byte[] write(Object o) {
		try {
			return mapper.writeValueAsBytes(o);
		} catch (IOException e) {
			Exceptions.logJsonMarshalException(e, o.getClass());
			return null;
		}
	}

	/**
	 * 
	 * @param is
	 * @param clazz
	 * @return
	 */
	public static Object read(InputStream is, Class<?> clazz) {
		try {
			return mapper.readValue(is, clazz);
		} catch (IOException e) {
			Exceptions.logJsonUnmarshalException(e, clazz);
			return null;
		}
	}

	/**
	 * Reads a String Json value and palces information within JsonNode.class
	 * 
	 * @param str   = the String Json that was inputted
	 * @param clazz = class using this method
	 * @return return an ObjectMapper value OR null.
	 */
	public static JsonNode readString(String str, Class<?> clazz) {
		try {
			return mapper.readValue(str, JsonNode.class);
		} catch (IOException e) {
			Exceptions.logJsonUnmarshalException(e, clazz);
			return null;
		}
	}
}
