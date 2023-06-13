package com.ssafy.enjoytrip.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssafy.enjoytrip.user.model.UserRole;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JwtCookieFilter extends OncePerRequestFilter {

    private JWTVerifier jwtVerifier;

    public JwtCookieFilter(final Algorithm cryptoAlgorithm) {
        jwtVerifier = JWT.require(cryptoAlgorithm).build();
    }

    private String extractTokenFromRequest(final HttpServletRequest req) {
        if (req.getCookies() != null) {
            for (final Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals("token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doFilterInternal(
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final FilterChain filterChain) throws ServletException, IOException {
        final String token = extractTokenFromRequest(req);
        if (token != null) {
            try {
                final DecodedJWT jwt = jwtVerifier.verify(token);
                final Map<String, Object> userInfo = jwt.getClaim("client").asMap();

                final JwtAuthentication jwtAuth = new JwtAuthentication();
                jwtAuth.setAuthenticated(true);
                jwtAuth.setToken(token);
                jwtAuth.setName(jwt.getSubject());
                jwtAuth.setPrincipal((String) userInfo.get("id"));
                for (final String userRole : (List<String>) userInfo.get("roles")) {
                    jwtAuth.addAuthority(UserRole.of(userRole));
                }

                SecurityContextHolder.getContext().setAuthentication(jwtAuth);
            } catch (SignatureVerificationException e) {
                resp.setHeader("Set-Cookie", "token=;Max-Age=0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        filterChain.doFilter(req, resp);
    }

}
