package infracloud.io.util;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class CommonUtil {

	public String generateAlphaNumericTokenOfSize(int size) {
		int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    Random random = new Random();

	    String randomToken = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(size)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    return randomToken;
	}
}
