package com.codecool.travely.security.jwt;

import com.codecool.travely.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final CustomerService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = jwtTokenService.getTokenFromRequest(request);

            if (StringUtils.hasText(jwt) && jwtTokenService.validateToken(jwt)) {
                String userId = jwtTokenService.getUserIdFromToken(jwt);
                if (idMadeOfDigits(userId)) {
                    UserDetails userDetails = userService.loadOauthUserById(userId);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    Authentication auth = jwtTokenService.parseUserFromTokenInfo(jwt);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    public boolean idMadeOfDigits(String userId) {
        int digitCount = 0;
        for (char c: userId.toCharArray()) {
            if(Character.isDigit(c)) {
                digitCount++;
            }
        }
        return digitCount == userId.length();
    }
}
