package util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Exceptions {
	private static Logger logger = LogManager.getLogger(Exceptions.class);

	private Exceptions() {
	}

	public static void logSQLException(SQLException e) {
		logger.warn("SQL Message: {}", e.getMessage());
		logger.warn("Error Code: {}", e.getErrorCode());
		logger.warn("SQL State: {}", e.getSQLState());
		logger.warn("Stack Trace: ", e);
	}
	
	// Marshalling: POJO -> JSON (aka, a write)
	public static void logJsonMarshalException(IOException e, Class<?> clazz) {
		logger.warn("Failed to Marshal object of type {}", clazz.getName());
		logger.warn("Stack Trace: ", e);
	}
	
	// Unmarshalling: JSON -> POJO (aka, a read)
	public static void logJsonUnmarshalException(IOException e, Class<?> clazz) {
		logger.warn("Failed to Unmarshal object of type {}", clazz.getName());
		logger.warn("Stack Trace: ", e);
	}
	
	public static void logNoSuchAlgorithmException(NoSuchAlgorithmException e, Class<?> clazz, String badAlg) {
		logger.warn("Algorithm: " + badAlg + "not found", clazz.getName());
		logger.warn("Stack Trace: ", e);
	}
	
	public static void logInvalidKeySpecException(InvalidKeySpecException e, Class<?> clazz) {
		logger.warn("Invalid key spec shhhh careful");
		logger.warn("Stack Trace: ", e);
	}
}
