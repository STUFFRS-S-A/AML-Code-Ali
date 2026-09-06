package com.example.didit.app.security;

import com.example.didit.app.model.ApiResponse;
import com.example.didit.app.util.ResponseCodes;
import com.example.didit.app.service.auth.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                Long userId = jwtUtil.extractUserId(token);
                Instant issuedAt = jwtUtil.getIssuedAt(token);

                if (SecurityContextHolder.getContext().getAuthentication() == null) {

                    CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserById(userId, issuedAt);

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (ExpiredJwtException ex) {
                this.writeAuthErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, ResponseCodes.TokenIsExpired);

                return;
            } catch (Exception ex) {
                this.writeAuthErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, ResponseCodes.AuthenticationError);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private void writeAuthErrorResponse(HttpServletResponse response, int httpStatus, String responseCode) throws IOException {
        SecurityContextHolder.clearContext();

        response.setStatus(httpStatus);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.fail(responseCode)));
    }
}
