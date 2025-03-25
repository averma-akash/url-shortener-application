package url.shortener.appliction.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import url.shortener.appliction.config.JWTUtils;
import url.shortener.appliction.model.AuthRequest;
import url.shortener.appliction.model.User;
import url.shortener.appliction.service.ShortenerService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	ShortenerService service;
	@Autowired
	JWTUtils jwtUtil;
	

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest request) throws ParseException {

		User usrDtl = service.findUser(request);
		if (null == usrDtl) {
			return ResponseEntity.badRequest().body(Map.of("ERROR_DESC","**** Invalid Username or Password ****"));
		}
		Map<String, Object> claims = service.createClaims(usrDtl);
		ResponseCookie responseCookie = jwtUtil.generateJwtToken(claims,request.getUsername());

		return ResponseEntity.ok().header("Set-Cookie", responseCookie.toString())
				.body(Map.of(usrDtl.getUsername(), new Date()));
	}
}
