package org.example.furnituresaleproject.config.jwt;

import com.auth0.json.mgmt.Token;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.furnituresaleproject.service.AccountService.JWTTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Collections;


public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    public JwtAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }
// validate email and password
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getParameter("email"),
                        request.getParameter("password"),
                        Collections.emptyList() // generate List<role> to add into here, ex: admin, user,..
                )
        );
    }
    // after validating successfully redirect this method and add token to header
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult)
            throws IOException, ServletException {
        JWTTokenService.addJWTTokenToHeader(response, authResult.getName());
    }


//    private final JwtService jwtService;
//    @Override
//    protected void doFilterInternal(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull FilterChain filterChain)
//            throws ServletException, IOException {
//            final String authorHeader = request.getHeader("Authorization");
//            final String jwt;
//            final String userEmail;
//
//            if(authorHeader == null || !authorHeader.startsWith("Bearer")) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//            jwt = authorHeader.substring(7);
//            userEmail =
//    }
}
