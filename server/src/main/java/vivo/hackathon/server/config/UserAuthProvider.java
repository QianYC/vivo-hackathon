package vivo.hackathon.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import vivo.hackathon.server.service.UserService;

public class UserAuthProvider implements AuthenticationProvider {
    @Autowired
    UserService service;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String userName = token.getName();
        String password = token.getCredentials().toString();
        UserDetails details = service.loadUserByUsername(userName);
        if (!details.getPassword().equals(password)) throw new BadCredentialsException("login failed");

        return new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}
