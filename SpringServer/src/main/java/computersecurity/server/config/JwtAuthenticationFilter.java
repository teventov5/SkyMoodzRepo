package computersecurity.server.config;

import computersecurity.server.model.User;
import computersecurity.server.model.UserService;
import computersecurity.server.security.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class is responsible for filtering requests such that we allow to known users only, to perform actions.<br/>
 * Refer to {@link SpringBootConfiguration} to see what paths we filter and what paths are public.
 *
 * @since 21-Mar-21
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private UserService userService;
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String pathInfo = request.getServletPath();
        if ((pathInfo == null) || pathInfo.isBlank()) {
            pathInfo = request.getPathInfo(); // This is usable during unit tests only.
        }

        // Some hard coded shit, I know.. But this is a "before" filter without a bean to control on which paths it should not apply.
        // We cannot validate JWT token for the signup and signin, since server has not created a JWT yet.
        if ((pathInfo != null) && !pathInfo.isBlank() && !pathInfo.trim().equals("/") && !pathInfo.toLowerCase().contains("user/signin") && !pathInfo.toLowerCase().contains("user/signup")) {
            // Get these by ourselves since the class is not a @Component, cause we don't want the filter to be applied for all requests.
            if (userService == null) {
                ServletContext servletContext = request.getServletContext();
                WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                userService = webApplicationContext.getBean(UserService.class);
                jwtUtils = webApplicationContext.getBean(JwtUtils.class);
            }

            final String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);

            User user = null;

            // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                String jwtToken = requestTokenHeader.substring(7);

                try {
                    user = jwtUtils.parseToken(jwtToken);
                } catch (Exception e) {
                    logger.warn("Unable to get JWT Token: " + e.getMessage());
                }
            } else {
                logger.warn("Incorrect syntax for JWT Token: " + requestTokenHeader);
            }

            // Once we get the token validate it.
            if (user != null) {
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userService.loadUserByUsername(user.getId());

                    UsernamePasswordAuthenticationToken userPassAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    userPassAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the
                    // Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(userPassAuth);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}

