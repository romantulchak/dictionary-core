package com.dictionary.security.jwt;

import com.dictionary.constant.DictionaryContents;
import com.dictionary.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenFilter.class);

    /**
     * Checks if our jwt is correct and set user details
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUsernameFromJwtToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            LOGGER.error("Cannot set user authentication", e);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Checks if header contains Authorization and its stats with
     * Bearer then return jwt without this prefix otherwise null
     *
     * @param request to get value from Authorization header
     * @return jwt without Bearer prefix
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(DictionaryContents.APP_AUTH_HEADER);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(DictionaryContents.APP_AUTH_TOKEN_PREFIX)) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
