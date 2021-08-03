package infracloud.io.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import infracloud.io.util.CommonUtil;

@Service
public class UrlServiceImpl implements UrlService {
	public static final Logger LOGGER = LoggerFactory.getLogger(UrlServiceImpl.class);

	Set<String> duplicateCheckSet = new HashSet<String>();
	Map<String, String> tinyToUrl = new HashMap<String, String>();
	Map<String, String> urlToTiny = new HashMap<String, String>();

	@Autowired
	CommonUtil commonUtil;

	@Value("${service.url}")
	private String serviceUrl;

	@Override
	public String getShortenUrl(String url) {
		if (duplicateCheckSet.add(url)) {
			LOGGER.debug("url-service: started shortning of url");
			String shortUrl = commonUtil.generateAlphaNumericTokenOfSize(4);
			tinyToUrl.put(shortUrl, url);
			urlToTiny.put(url, shortUrl);
			LOGGER.debug("url-service: shortUrl generated:{}", shortUrl);
			return generateUrl(shortUrl);
		}
		
		LOGGER.info("url-service: shortUrl is alrady generated");
		String shortUrl = urlToTiny.get(url);
		LOGGER.debug("url-service: existing shortUrl:{}", shortUrl);
		return shortUrl;
	}

	@Override
	public String serviceUrl(String url) {
		String location = tinyToUrl.get(url);
		System.out.println(location);
		return location;
	}
	
	private String generateUrl(String shortUrl) {
		return serviceUrl + "/" + shortUrl;
	}

}
