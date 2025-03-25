package url.shortener.appliction.config;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@SuppressWarnings("deprecation")
@Component
public class JWTUtils {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JWTUtils.class);

	@Value("${url.app.jwtExpirationMs}")
	private long jwtExpirationMs;

	@Value("${url.app.jwtCookieName}")
	private String jwtCookie;

	private final CustomSigningKeyResolver signingKeyResolver = new CustomSigningKeyResolver();

	public ResponseCookie generateJwtToken(Map<String, Object> claims,String username) throws ParseException {

		String jwt = Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
				.signWith(signingKeyResolver.getSigningKey(), SignatureAlgorithm.HS256).compact();
		
		ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/").maxAge(24 * 60 * 60).httpOnly(true)
				.build();
		return cookie;
		
	}

	public String extractUsername(String token) {
		return getClaims(token).getSubject();
	}

	public Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKeyResolver(signingKeyResolver).build().parseClaimsJws(token).getBody();
	}

	public boolean validateJwtToken(String authToken, String username) {
		try {
			return extractUsername(authToken).equals(username)
					&& getClaims(authToken).getExpiration().after(new Date());
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

	public String getJwtFromCookies(HttpServletRequest request) {
		Cookie cookie = WebUtils.getCookie(request, jwtCookie);
		if (cookie != null) {
			return cookie.getValue();
		} else {
			return null;
		}
	}

}
