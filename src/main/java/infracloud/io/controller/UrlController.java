package infracloud.io.controller;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import infracloud.io.service.UrlService;

@RestController
@Validated
public class UrlController {
	@Autowired
	UrlService urlService;
	
	@PostMapping(value="/shortner")
	public String createShortUrl(@Valid @RequestParam @NotNull @NotBlank String url) {
		System.out.println("ha ha");
		return urlService.getShortenUrl(url);
	}
	
	@GetMapping("/{url}")
	public void serviceUrl(@PathVariable("url") String url, HttpServletResponse httpServletResponse) {
		System.out.println("ok:"+url);
		String location = urlService.serviceUrl(url);
		httpServletResponse.setHeader("Location", location);
	    httpServletResponse.setStatus(302);
	}
	
	
}
