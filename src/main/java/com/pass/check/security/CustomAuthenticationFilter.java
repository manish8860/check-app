package com.pass.check.security;

import com.pass.check.converter.RoleDtoConverter;
import com.pass.check.dto.UserDto;
import com.pass.check.entity.Role;
import com.pass.check.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class CustomAuthenticationFilter implements Filter {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authenticationHeader = httpRequest.getHeader("authorization");

        if(authenticationHeader!=null && authenticationHeader.startsWith("Bearer")){
            String token = authenticationHeader.substring(7);
            if(jwtUtility.verifyToken(token)){
                UserDto userDto = userService.getUserFromToken(token);
                List<Role> roles = RoleDtoConverter.getRoles(userDto.getRoles());
                SecurityContextHolder.getContext().setAuthentication(
                        new PreAuthenticatedAuthenticationToken(userDto,"",
                                roles)
                );
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
