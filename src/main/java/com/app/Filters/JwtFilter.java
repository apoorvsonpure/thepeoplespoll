package com.app.Filters;

import com.app.Services.UserInfoService;
import com.app.Utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter
{

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserInfoService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {

        final String header = request.getHeader("Authorization");
        String email = null;
        String token = null;
        if (header != null && header.startsWith("Bearer "))
        {
            token = header.substring(7);
            email = jwtTokenUtil.getSubjectFromToken(token);
        }

        if (email != null && !email.isEmpty())
        {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if(jwtTokenUtil.validateTokenExists(token))
            {
                UsernamePasswordAuthenticationToken auth =  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }

}
