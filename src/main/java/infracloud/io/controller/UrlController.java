package infracloud.io.controller;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	@Autowired
	UrlService urlService;
	
	@Autowired
	CommonUtil commonUtil;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(UrlController.class);
	private static final String EMPTY_NULL_URL_MSG = "Please provide URL";

	@PostMapping(value = "/short-url")
	public String createShortUrl(@Valid @RequestParam @NotNull(message = EMPTY_NULL_URL_MSG) @NotBlank(message = EMPTY_NULL_URL_MSG) String url) {
		commonUtil.isValidUrl(url);
				 
		LOGGER.info("url-service: shortning of url");
		LOGGER.debug("url-service: shortning of url:{}", url);
		
		String shortUrl = urlService.getShortenUrl(url);
		
		LOGGER.info("url-service: URL shortned:{}", shortUrl);
		return shortUrl;
	}

	@GetMapping(value = "/{url}")
	public void locateUrl(@PathVariable("url") String url, HttpServletResponse httpServletResponse) {
		LOGGER.info("url-service: short url invoked");
		LOGGER.debug("url-service: short url invoked:{}", url);

		String urlLocation = urlService.locateUrl(url);
		
		httpServletResponse.setHeader("Location", urlLocation);
		httpServletResponse.setStatus(302);
	}
}
