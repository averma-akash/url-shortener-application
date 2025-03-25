package url.shortener.appliction.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import url.shortener.appliction.config.JWTUtils;
import url.shortener.appliction.model.AuthRequest;
import url.shortener.appliction.model.Roles;
import url.shortener.appliction.model.ShortenedUrl;
import url.shortener.appliction.model.User;
import url.shortener.appliction.repository.RolesRepository;
import url.shortener.appliction.repository.ShortenedRepository;
import url.shortener.appliction.repository.UserRepository;

@Service
public class ShortenerService {

	@Autowired
	UserRepository userDao;
	@Autowired
	RolesRepository rolesDao;
	@Autowired
	ShortenedRepository urlRepository;
	@Autowired
	JWTUtils utils;
	@Autowired
	RedisService redisService;

	private static final int SHORT_URL_LENGTH = 6;
	private static final String BASE_URL = "https://short.ly/";
	
	public User findUser(AuthRequest request) {

		User userDtls = userDao.findByUsername(request.getUsername());
		if (null != userDtls && request.getPassword().equals(userDtls.getPassword())) {
			return userDtls;
		}
		return null;
	}
	public Map<String, Object> createClaims(User user) {

		Map<String, Object> claims = new HashMap<>();
		Roles findById = rolesDao.getById(user.getRoleId());
		claims.put("employeeId", user.getEmpId());
		claims.put("username", user.getUsername());
		claims.put("role", findById != null ? findById.getRoleName() : null);
		return claims;
	}

	public User loadUserByUsername(String username) {
		return userDao.findByUsername(username);
	}

	public Roles getRole(Integer roleId) {
		return rolesDao.getById(roleId);
	}

	public String generateShortUrl(String longUrl) {

		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		// Check if URL is already shortened
		Optional<ShortenedUrl> existingUrl = urlRepository.findByShortUrl(longUrl);
		if (existingUrl.isPresent()) {
			return BASE_URL + existingUrl.get().getShortUrl();
		}

		ShortenedUrl url = new ShortenedUrl();
		String shortUrl = generateRandomShortUrl();
		url.setCreatedBy(username);
		url.setLongUrl(longUrl);
		url.setShortUrl(shortUrl);
		urlRepository.save(url);
		redisService.cacheUrl(shortUrl, longUrl);

		return BASE_URL + shortUrl;
	}

	private String generateRandomShortUrl() {
		byte[] randomBytes = new byte[4]; // 4 bytes = 32 bits
		new SecureRandom().nextBytes(randomBytes);
		return Base64.getUrlEncoder().encodeToString(randomBytes).substring(0, SHORT_URL_LENGTH);
	}

	public String getLongUrl(String shortUrl) {
		String cachedUrl = redisService.getCachedUrl(shortUrl);
		if (cachedUrl != null) {
			return cachedUrl;
		}

		Optional<ShortenedUrl> urlMapping = urlRepository.findByShortUrl(shortUrl);
		return urlMapping.map(ShortenedUrl::getLongUrl).orElse(null);
	}

}
