package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionUtil {
	// Fail Fast
	private static Logger logger = LogManager.getLogger(ConnectionUtil.class);
	private static Properties props = getProperties();

	// Magic Strings
	private static final String URL = props.getProperty("jdbc.url");
	private static final String USERNAME = props.getProperty("jdbc.username");
	private static final String PASSWORD = props.getProperty("jdbc.password");

	// Restrict Instantiation
	private ConnectionUtil() {
	}

	public static Connection getConnection() {
		try {
			logger.debug(URL);
			logger.debug(USERNAME);
			logger.debug(PASSWORD);
			logger.debug(DriverManager.getConnection(URL, USERNAME, PASSWORD));
			return DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			Exceptions.logSQLException(e);
			return null;
		}
	}

	// All this method does is retrieve our particular properties needed to connect
	// to DB
	private static Properties getProperties() {
		try {
			Properties props = new Properties();

			// Load the values from src/main/resources/application.properties
			// TL;DR - this method looks in src/main/resources for the file that you pass to
			// getResourceAsStream()
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
			return props;
		} catch (IOException | NullPointerException e) {
			logger.error("Unable to locate JDBC Properties at src/main/resources/application.properties");
			logger.error("Stack Trace: ", e);
			throw new RuntimeException("Check Logs; Failed to get connection properties");
		}
	}
}
