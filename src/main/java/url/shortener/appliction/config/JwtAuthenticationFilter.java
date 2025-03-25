package url.shortener.appliction.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import url.shortener.appliction.repository.UserRepository;
import url.shortener.appliction.service.ShortenerService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JWTUtils jwtUtils;

	public JwtAuthenticationFilter(JWTUtils jwtUtils, UserRepository userDao, ShortenerService userService) {
		this.jwtUtils = jwtUtils;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);
			if (null != jwt) {
				String username = jwtUtils.extractUsername(jwt);
				if (null != username && jwtUtils.validateJwtToken(jwt, username)) {
					Map<String, Object> claims = jwtUtils.getClaims(jwt);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
							null, getAuthorities(claims));
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		}

		filterChain.doFilter(request, response);
	}
	
	private List<GrantedAuthority> getAuthorities(Map<String, Object> claims) {
	    List<GrantedAuthority> authorities = new ArrayList<>();
	    if (claims.get("role") instanceof List<?>) {
	        List<?> roles = (List<?>) claims.get("role");
	        for (Object role : roles) {
	            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.toString()));
	        }
	    } else {
	    	String role = claims.get("role").toString();
	    	 authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
	    }
	    return authorities;
	}

	private String parseJwt(HttpServletRequest request) {
		String jwt = jwtUtils.getJwtFromCookies(request);
		return jwt;
	}

//	public Map<String, Object> createClaims(String username) {
//
//		User user = userDao.findByUsername(username);
//		return userService.createClaims(user);
//	}

}
