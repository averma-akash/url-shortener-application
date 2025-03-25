package url.shortener.appliction.config;

import java.security.Key;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class CustomSigningKeyResolver extends SigningKeyResolverAdapter {

	private static String jwtSecret = "urlShortnerServiceSecretKey32byte";
	
	

    public Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(Base64.getEncoder().encodeToString(jwtSecret.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Key resolveSigningKey(JwsHeader header, Claims claims) {
        return getSigningKey();
    }
}
