package org.seguridad.api_rest.filters;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.seguridad.api_rest.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimiterFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

   private final ConcurrentHashMap<String, UserRequestCount> requestCounts = new ConcurrentHashMap<>();

    private static final int MAX_REQUESTS = 50; // Máximo de peticiones
    private static final long TIME_WINDOW = TimeUnit.HOURS.toMillis(1); // Ventana de tiempo de 1 hora

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String username = getUsernameFromRequest(request); // Obtener el nombre de usuario de la petición

        if (username != null) {
            UserRequestCount userRequestCount = requestCounts.computeIfAbsent(username, UserRequestCount::new);

            synchronized (userRequestCount) {
                if (System.currentTimeMillis() - userRequestCount.getStartTime() > TIME_WINDOW) {
                    userRequestCount.resetCount(); // Reiniciar el conteo después de 1 hora
                }

                if (userRequestCount.getRequestCount() < MAX_REQUESTS) {
                    userRequestCount.incrementCount();
                    filterChain.doFilter(request, response); // Permitir que la petición continúe
                } else {
                    response.setStatus(HttpServletResponse.SC_REQUEST_TIMEOUT);
                    response.getWriter().write("Too many requests. Please try again later.");
                    return;
                }
            }
        } else {
            filterChain.doFilter(request, response); // Continua si no hay usuario
        }
    }

    private String getUsernameFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return jwtUtils.extractUsername(token);
        }
        return null;
    }

    private static class UserRequestCount {
        private int requestCount;
        private long startTime;

        public UserRequestCount(String username) {
            this.requestCount = 0;
            this.startTime = System.currentTimeMillis();
        }

        public int getRequestCount() {
            return requestCount;
        }

        public long getStartTime() {
            return startTime;
        }

        public void incrementCount() {
            requestCount++;
        }

        public void resetCount() {
            requestCount = 0;
            startTime = System.currentTimeMillis();
        }
    }

}
