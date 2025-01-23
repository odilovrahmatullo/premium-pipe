package premium_pipe.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import premium_pipe.model.response.JwtResponse;
import premium_pipe.service.CustomUserDetailsService;
import premium_pipe.service.JwtService;

import java.io.IOException;
@Configuration
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        if(header==null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        try {
            final String token =header.substring(7).trim();
            JwtResponse jwt = jwtService.decode(token);
            String username = jwt.username();

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            filterChain.doFilter(request,response);
        }catch (UsernameNotFoundException e){
            filterChain.doFilter(request,response);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
        }


    }
}
