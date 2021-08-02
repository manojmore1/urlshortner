package infracloud.io.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements UrlService{
	Set<String> duplicateCheckSet = new HashSet<String>();
	Map<String,String> tinyToUrl = new HashMap<String,String>();
	Map<String,String> urlToTiny = new HashMap<String,String>();
	
	
	@Override
	public String getShortenUrl(String url) {
		if(duplicateCheckSet.add(url)) {
			byte[] array = new byte[4]; // length is bounded by 7
		    new Random().nextBytes(array);
		    String generatedString = getRandomUrl();
		    tinyToUrl.put(generatedString, url);
		    urlToTiny.put(url, generatedString);
		    System.out.println("shorten url:"+generatedString);
		    return "http://localhost:8080/"+generatedString;
		} else {
			
		}
		return url;
	}
	
	@Override
	public String serviceUrl(String url) {
		String location = tinyToUrl.get(url);
		System.out.println(location);
		return location;
	}
	
	public String getRandomUrl() {
		int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 4;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    return generatedString;
	}
}
