package infracloud.io.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import infracloud.io.exception.exceptions.InvalidServiceUrlException;
import infracloud.io.util.CommonUtil;

@Service
public class UrlServiceImpl implements UrlService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UrlServiceImpl.class);

	private Set<String> duplicateCheckSet = new HashSet<String>();
	private Map<String, String> shorturl_url = new HashMap<String, String>();
	private Map<String, String> url_shorturl = new HashMap<String, String>();

	@Autowired
	private CommonUtil commonUtil;

	@Value("${service.url}")
	private String serviceUrl;

	@Override
	public String getShortenUrl(String url) {
		if (duplicateCheckSet.add(url)) {
			LOGGER.debug("url-service: started shortning of url");
			String shortUrl = commonUtil.generateAlphaNumericTokenOfSize(4);
			shorturl_url.put(shortUrl, url);
			url_shorturl.put(url, shortUrl);
			LOGGER.debug("url-service: shortUrl generated:{}", shortUrl);
			return generateUrl(shortUrl);
		}
		
		LOGGER.info("url-service: shortUrl is alrady generated");
		String shortUrl = url_shorturl.get(url);
		LOGGER.debug("url-service: existing shortUrl:{}", shortUrl);
		return generateUrl(shortUrl);
	}

	@Override
	public String locateUrl(String url) {
		Optional<String> urlLocation = Optional.ofNullable(shorturl_url.get(url));
		LOGGER.info("url-service: serving shortUrl:{} with {}",url,urlLocation.get());
		
		if(!urlLocation.isPresent()) {
			throw new InvalidServiceUrlException("url-service: Invalid Service Url");
		}
		return urlLocation.get();
	}
	
	private String generateUrl(String shortUrl) {
		return serviceUrl + "/" + shortUrl;
	}

}
