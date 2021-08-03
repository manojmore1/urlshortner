package infracloud.io.controller;


import java.util.regex.Matcher;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import infracloud.io.service.UrlService;
import infracloud.io.util.CommonUtil;

@RestController
@Validated
public class UrlController {
	public static final Logger LOGGER = LoggerFactory.getLogger(UrlController.class);
	
	@Autowired
	UrlService urlService;
	
	@Autowired
	CommonUtil commonUtil;

	@PostMapping(value = "/shortner")
	public String createShortUrl(@Valid @RequestParam @NotNull @NotBlank String url) {
		commonUtil.isValidUrl(url);
				 
		LOGGER.info("url-service: shortning of url");
		LOGGER.debug("url-service: shortning of url:{}", url);
		
		return urlService.getShortenUrl(url);
	}

	@GetMapping("/{url}")
	public void serviceUrl(@PathVariable("url") String url, HttpServletResponse httpServletResponse) {
		LOGGER.info("url-service: short url invoked");
		LOGGER.debug("url-service: short url invoked:{}", url);

		String location = urlService.serviceUrl(url);
		
		httpServletResponse.setHeader("Location", location);
		httpServletResponse.setStatus(302);
	}
	
	/*
	 * public static void main(String arsg[]) { java.util.regex.Pattern p =
	 * java.util.regex.Pattern.compile(
	 * "(http://|https://|www.)([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?");
	 * Matcher m;
	 * m=p.matcher("https://www.datacamp.com/community/tutorials/git-push-pull");
	 * System.out.println(m.find()); }
	 */
}
