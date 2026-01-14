package com.example.library.common.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.web.servlet.HandlerInterceptor;

public class SubsystemLoginInterceptor implements HandlerInterceptor {

    private final String sessionAttribute;
    private final String loginPath;

    public SubsystemLoginInterceptor(String sessionAttribute, String loginPath) {
        this.sessionAttribute = sessionAttribute;
        this.loginPath = loginPath;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(sessionAttribute) != null) {
            return true;
        }
        String target = buildTarget(request);
        String redirectUrl = loginPath + "?target="
                + URLEncoder.encode(target, StandardCharsets.UTF_8);
        response.sendRedirect(redirectUrl);
        return false;
    }

    private String buildTarget(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        if (query == null || query.isBlank()) {
            return uri;
        }
        return uri + "?" + query;
    }
}
