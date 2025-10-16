package com.fstt.atelier2.mvc2_jpa.web;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String requestUri = req.getRequestURI(); // inclut le contextPath
        String path = requestUri.substring(contextPath.length());

        if (isPublic(path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        Object user = (session != null) ? session.getAttribute("user") : null;
        if (user == null) {
            String qs = req.getQueryString();
            String full = requestUri + (qs != null ? ("?" + qs) : "");
            String next = URLEncoder.encode(full, StandardCharsets.UTF_8);
            resp.sendRedirect(contextPath + "/compte/showLogin?next=" + next);
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isPublic(String path) {
        if (path == null || path.isEmpty() || "/".equals(path)) return true;
        if (path.equals("/index.jsp")) return true;
        if (path.startsWith("/assets/")) return true; // CSS, images, etc.
        if (path.startsWith("/favicon")) return true;

        // Auth endpoints
        if (path.startsWith("/compte/showLogin")) return true;
        if (path.startsWith("/compte/login")) return true;
        if (path.startsWith("/compte/showRegister")) return true;
        if (path.startsWith("/compte/register")) return true;

        // Produits: public (vitrine + détails + recherche)
        if (path.startsWith("/produits")) return true;
        if (path.startsWith("/pages/produit/")) return true; // accès direct à la vitrine/détails

        // Tout le reste est protégé (panier, commande, profil, autres JSP)
        return false;
    }
}

