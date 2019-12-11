package util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AuthUtil {

	private static final int ITERATIONS = 65536;
	private static final int KEY_LENGTH = 256;
	private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
	//salt length needs to be greater than 0
	private static final int SALT_LENGTH = 16;
	
	private static Logger logger = LogManager.getLogger();
	
	private static final SecureRandom RAND = new SecureRandom();

	public static Optional<String> generateSalt(){
		byte[] salt = new byte[SALT_LENGTH];
		RAND.nextBytes(salt);
		
		return Optional.of(Base64.getEncoder().encodeToString(salt));
	}
	
	public static Optional<String> hashPassword(String password, String salt){
		
		//turn password and salt into arrays
		char[] chars = password.toCharArray();
		byte[] bytes = salt.getBytes();
		
		//make key
		PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);
		
		//clear password array
		Arrays.fill(chars, Character.MIN_VALUE);
		
		try {
			//get secret key
			SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
			byte[] securePassword = fac.generateSecret(spec).getEncoded();
			return Optional.of(Base64.getEncoder().encodeToString(securePassword));
		}
		catch(NoSuchAlgorithmException e) {
			Exceptions.logNoSuchAlgorithmException(e, AuthUtil.class, ALGORITHM);
			return Optional.empty();
		}
		catch(InvalidKeySpecException e) {
			Exceptions.logInvalidKeySpecException(e, AuthUtil.class);
			return Optional.empty();
		}
		finally {
			spec.clearPassword();
		}
	}
	
	public static boolean verifyPassword(String password, String key, String salt) {
		//get hashed password
		Optional<String> optEncrypted = hashPassword(password, salt);
		//check if hashing returned anything
		if(!optEncrypted.isPresent()) {
			return false;
		}
		//check if key is the same
		return optEncrypted.get().equals(key);
	}
}
