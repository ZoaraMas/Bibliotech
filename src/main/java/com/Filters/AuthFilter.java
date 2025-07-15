package com.Filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.Entite.User;
import com.Service.UserService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component("authFilter")
public class AuthFilter implements Filter {

    private Set<String> publicPaths = new HashSet<>();

    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Authentication filter initialized");

        // Configuration directe des chemins publics
        publicPaths.add("/user/form-login");
        publicPaths.add("/user/login");
        publicPaths.add("/public/home");
        publicPaths.add("/public/liste-livre");
        publicPaths.add("/home/public");
        publicPaths.add("/css/**");
        publicPaths.add("/js/**");
        publicPaths.add("/images/**");

        System.out.println("Public paths configured: " + publicPaths);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();
        String loginPage = contextPath + "/user/form-login";
        String publicHomePage = contextPath + "/home/public";
        String requestURI = request.getRequestURI();

        System.out.println("=== DEBUG INFO ===");
        System.out.println("Context Path: " + contextPath);
        System.out.println("Request URI: " + requestURI);
        System.out.println("Public paths: " + publicPaths);

        // Check if URL is in exceptions (login pages, static resources)
        if (urlException(contextPath, request)) {
            System.out.println("URL is in exceptions, allowing access");
            fc.doFilter(req, res);
            return;
        }

        // Check if path is public
        if (isPublicPath(contextPath, requestURI)) {
            System.out.println("Path is public, allowing access");
            fc.doFilter(req, res);
            return;
        }

        // Check if user is authenticated
        Object authAttribute = session.getAttribute("auth");
        if (authAttribute != null) {
            Long idUser = (Long) authAttribute;
            User user = this.userService.findById(idUser);
            if (user != null) {
                try {
                    // Check user type
                    if (user.getTypeUser().getId() != 1) {
                        // Not an admin, redirect to public home
                        System.out.println("User is not admin, redirecting to public home");
                        response.sendRedirect(publicHomePage);
                        return;
                    }

                    // User is admin, continue with the request
                    System.out.println("User is admin, allowing access");
                    fc.doFilter(req, res);
                    return;
                } catch (Exception e) {
                    System.err.println("Error getting user info: " + e.getMessage());
                    session.invalidate();
                    // Redirect with error message for insufficient privileges
                    response.sendRedirect(loginPage + "?error=" + e.getMessage());
                    return;
                }
            } else {
                // User not found in database
                session.invalidate();
                response.sendRedirect(loginPage + "?error=user_not_found");
                return;
            }
        }

        // Not authenticated, redirect to login
        System.out.println("User not authenticated, redirecting to login");
        response.sendRedirect(loginPage + "?error=not_authenticated");
    }

    private boolean isPublicPath(String contextPath, String requestURI) {
        for (String publicPath : publicPaths) {
            String fullPath = contextPath + publicPath;
            System.out.println("Checking: " + fullPath + " against " + requestURI);

            if (requestURI.equals(fullPath) || requestURI.startsWith(fullPath)) {
                System.out.println("MATCH FOUND!");
                System.out.println("Full Path: " + fullPath);
                System.out.println("Request URI: " + requestURI);
                System.out.println("========\n\n");
                return true;
            }
        }
        System.out.println("No public path match found");
        return false;
    }

    // Verify exceptions that don't need the auth filter
    public boolean urlException(String contextPath, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String[] exceptions = {
                "/user/form-login",
                "/user/login",
                "/home/login",
                "/css/",
                "/js/",
                "/images/",
                "/favicon.ico"
        };

        for (String exception : exceptions) {
            String fullPath = contextPath + exception;
            if (requestURI.equals(fullPath) || requestURI.startsWith(fullPath)) {
                return true;
            }
        }
        return false;
    }
}