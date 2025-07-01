package com.Filters;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig FilterConfig) throws ServletException{
        System.out.println("authentification filter initialized");
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
            throws IOException, ServletException {
        HttpServletRequest request = ((HttpServletRequest) req); 
        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();
        String loginPage = contextPath + "/user/form-login";
        if(session.getAttribute("auth") != null || urlException(contextPath, request)) {
            fc.doFilter(req, res);
        } else {
            ((HttpServletResponse) res).sendRedirect(loginPage);
            return;
        }
    }

    // Verify exceptions that don't need the auth filter
    public boolean urlException(String contextPath, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String[] exceptions = new String[2];
        exceptions[0] = "/user/form-login";
        exceptions[1] = "/user/login";
        // Ajouter le contextPath
        for(int i = 0; i < exceptions.length; i++) {
            exceptions[i] = contextPath + exceptions[i];
            if(exceptions[i].equals(requestURI)) return true;
        }
        return false;
    }
}