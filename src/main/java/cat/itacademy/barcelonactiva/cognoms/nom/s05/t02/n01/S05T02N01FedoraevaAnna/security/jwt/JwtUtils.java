package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.security.jwt;

import java.util.Date;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01FedoraevaAnna.model.service.UserDetailsImpl;
import io.jsonwebtoken.*;

@Component
public class JwtUtils {

private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	  @Value("${jwt.secret}")
	  private String jwtSecret;

	  @Value("${jwt.expiration}")
	  private int jwtExpirationMs;

	  @Value("${jwt.cookieName}")
	  private String jwtCookie;
	  
//	  public String generateJwtToken(Authentication authentication) {
//
//			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
//
//			return Jwts.builder()
//					.setSubject((userPrincipal.getUsername()))
//					.setIssuedAt(new Date())
//					.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//					.signWith(SignatureAlgorithm.HS512, jwtSecret)
//					.compact();
//		}

//	  public String getJwtFromCookies(HttpServletRequest request) {
//	    Cookie cookie = WebUtils.getCookie(request, jwtCookie);
//	    if (cookie != null) {
//	      return cookie.getValue();
//	    } else {
//	      return null;
//	    }
//	  }
//
//	  public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
//	    String jwt = generateTokenFromUsername(userPrincipal.getUsername());
//	    ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true).build();
//	    return cookie;
//	  }
//
//	  public ResponseCookie getCleanJwtCookie() {
//	    ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
//	    return cookie;
//	  }
//
//	  public String getUserNameFromJwtToken(String token) {
//	    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
//	  }
//
//	  public boolean validateJwtToken(String authToken) {
//	    try {
//	      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//	      return true;
//	    } catch (SignatureException e) {
//	      logger.error("Invalid JWT signature: {}", e.getMessage());
//	    } catch (MalformedJwtException e) {
//	      logger.error("Invalid JWT token: {}", e.getMessage());
//	    } catch (ExpiredJwtException e) {
//	      logger.error("JWT token is expired: {}", e.getMessage());
//	    } catch (UnsupportedJwtException e) {
//	      logger.error("JWT token is unsupported: {}", e.getMessage());
//	    } catch (IllegalArgumentException e) {
//	      logger.error("JWT claims string is empty: {}", e.getMessage());
//	    }
//
//	    return false;
//	  }
//	  
//	  public String generateTokenFromUsername(String username) {
//	    return Jwts.builder()
//	        .setSubject(username)
//	        .setIssuedAt(new Date())
//	        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//	        .signWith(SignatureAlgorithm.HS512, jwtSecret)
//	        .compact();
//	  }
//	}
//	  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
//
//		
//		public String generateJwtToken(Authentication authentication) {
//
//			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
//
//			return Jwts.builder()
//					.setSubject((userPrincipal.getUsername()))
//					.setIssuedAt(new Date())
//					.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//					.signWith(SignatureAlgorithm.HS512, jwtSecret)
//					.compact();
//		}
//
//		public String getUserNameFromJwtToken(String token) {
//			return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
//		}
//
//		public boolean validateJwtToken(String authToken) {
//			try {
//				Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//				return true;
//			} catch (SignatureException e) {
//				logger.error("Invalid JWT signature: {}", e.getMessage());
//			} catch (MalformedJwtException e) {
//				logger.error("Invalid JWT token: {}", e.getMessage());
//			} catch (ExpiredJwtException e) {
//				logger.error("JWT token is expired: {}", e.getMessage());
//			} catch (UnsupportedJwtException e) {
//				logger.error("JWT token is unsupported: {}", e.getMessage());
//			} catch (IllegalArgumentException e) {
//				logger.error("JWT claims string is empty: {}", e.getMessage());
//			}
//
//			return false;
//		}
//	}

	  
	//  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	

//		public String generateJwtToken(Authentication authentication) {
//
//			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
//
//			return Jwts.builder()
//					.setSubject((userPrincipal.getUsername()))
//					.setIssuedAt(new Date())
//					.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//					.signWith(SignatureAlgorithm.HS512, jwtSecret)
//					.compact();
//		}

		
		public String generateJwtToken(Authentication authentication) {

			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

			return Jwts.builder()
					.setSubject((userPrincipal.getEmail()))
					.setIssuedAt(new Date())
					.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
					.signWith(SignatureAlgorithm.HS512, jwtSecret)
					.compact();
		}
		public String getUserNameFromJwtToken(String token) {
			return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
		}


		public boolean validateJwtToken(String authToken) {
			try {
				Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
				return true;
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
	}
