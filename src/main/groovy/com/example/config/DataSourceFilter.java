package com.example.config;


import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class DataSourceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String dataSourceType = determineDataSourceType(request);
            DataSourceContextHolder.setDataSourceType(dataSourceType);
            filterChain.doFilter(request, response);
        } finally {
            DataSourceContextHolder.clearDataSourceType();
        }
    }

    private String determineDataSourceType(HttpServletRequest request) {
        String header = request.getHeader("X-DataSource");
        if ("MYSQL".equalsIgnoreCase(header)) {
            return "MYSQL";
        } else {
            return "ORACLE";
        }
    }
}

