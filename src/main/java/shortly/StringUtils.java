package shortly;

import org.apache.commons.text.RandomStringGenerator;
import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class StringUtils {

	/**
	* @param Link of generated string
	* @return Random string of alphanumeric characters
	*/
	public static String generateRandomString(final int length) {
		RandomStringGenerator generator = new RandomStringGenerator.Builder()
			.withinRange('0', 'z')
			.filteredBy(LETTERS, DIGITS)
			.build();
		return generator.generate(length);
	}
}
