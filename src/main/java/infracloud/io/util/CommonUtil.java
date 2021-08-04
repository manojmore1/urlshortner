package infracloud.io.util;

import java.util.Random;
import java.util.regex.Matcher;

import org.springframework.stereotype.Service;

@Service
public class CommonUtil {
	private static final String URL_PATTERN = "(http://|https://|www.)([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?";
	
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
	
	public boolean isValidUrl(String url) {
		java.util.regex.Pattern urlPattern = java.util.regex.Pattern.compile(URL_PATTERN);
		Matcher urlMatcher = urlPattern.matcher(url);
		if(!urlMatcher.find()) {
			throw new IllegalArgumentException("Invalid URL");
		}
		return true;
	}
}
