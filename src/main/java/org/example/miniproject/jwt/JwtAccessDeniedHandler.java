package org.example.miniproject.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        Map<String, Object> problemDetails = new HashMap<>();
        problemDetails.put("type", "about:blank");
        problemDetails.put("title", "Forbidden");
        problemDetails.put("status", HttpServletResponse.SC_FORBIDDEN);
        problemDetails.put("detail", "You do not have permission to access this resource.");
        problemDetails.put("instance", request.getRequestURI());
        problemDetails.put("timestamp", Instant.now().toString());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), problemDetails);
    }
}
