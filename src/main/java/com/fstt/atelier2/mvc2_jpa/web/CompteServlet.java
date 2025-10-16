package com.fstt.atelier2.mvc2_jpa.web;

import com.fstt.atelier2.mvc2_jpa.entities.Internaute;
import com.fstt.atelier2.mvc2_jpa.services.IInternauteService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.*;
import java.util.Optional;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


@WebServlet(name = "compteServlet", urlPatterns = "/compte/*")
public class CompteServlet extends HttpServlet {

    @Inject
    private IInternauteService internauteService;

    public void init() {
    }

    /**
     *
     * Handles the HTTP GET request.
     * Login, logout, register, view account details, etc.
     */
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            resp.sendRedirect(req.getContextPath() + "/compte/showLogin");
            return;
        }

        switch (path) {
            case "/showLogin":
            case "/login":
                if (req.getParameter("next") != null) req.setAttribute("next", req.getParameter("next"));
                req.getRequestDispatcher("/pages/compte/login.jsp").forward(req, resp);
                break;
            case "/logout":
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath() + "/compte/showLogin");
                break;
            case "/showRegister":
            case "/register":
                if (req.getParameter("next") != null) req.setAttribute("next", req.getParameter("next"));
                req.getRequestDispatcher("/pages/compte/register.jsp").forward(req, resp);
                break;
            case "/profile": {
                Internaute user = (Internaute) req.getSession().getAttribute("user");
                if (user == null) {
                    resp.sendRedirect(req.getContextPath() + "/compte/showLogin");
                    return;
                }
                // Charger depuis la base (optionnel) pour données fraîches
                Optional<Internaute> fresh = internauteService.getById(user.getId());
                fresh.ifPresent(u -> req.getSession().setAttribute("user", u));
                req.getRequestDispatcher("/pages/compte/account.jsp").forward(req, resp);
                break;
            }
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     *
     * (/login) Traite les données du formulaire de connexion. Ouvre une session si succès.
     * (register) Traite les données du formulaire d'inscription et crée un nouvel internaute.
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        switch (path) {
            case "/login": {
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String next = req.getParameter("next");
                if (next != null) next = URLDecoder.decode(next, StandardCharsets.UTF_8);
                if (email == null || password == null || email.isBlank() || password.isBlank()) {
                    req.setAttribute("error", "Veuillez fournir email et mot de passe.");
                    req.getRequestDispatcher("/pages/compte/login.jsp").forward(req, resp);
                    return;
                }
                Optional<Internaute> userOpt = internauteService.getByEmail(email);
                if (userOpt.isPresent() && password.equals(userOpt.get().getMotDePasse())) {
                    HttpSession session = req.getSession(true);
                    session.setAttribute("user", userOpt.get());

                    // Redirection sécurisée
                    if (next != null && !next.isBlank()) {
                        String ctx = req.getContextPath();
                        if (next.startsWith(ctx + "/")) {
                            resp.sendRedirect(next);
                            return;
                        } else if (next.startsWith("/")) {
                            resp.sendRedirect(ctx + next);
                            return;
                        }
                    }
                    resp.sendRedirect(req.getContextPath() + "/compte/profile");
                } else {
                    req.setAttribute("error", "Identifiants invalides.");
                    req.setAttribute("next", next);
                    req.getRequestDispatcher("/pages/compte/login.jsp").forward(req, resp);
                }
                break;
            }
            case "/register": {
                String nom = req.getParameter("nom");
                String prenom = req.getParameter("prenom");
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String next = req.getParameter("next");
                if (next != null) next = URLDecoder.decode(next, StandardCharsets.UTF_8);

                if (email == null || password == null || email.isBlank() || password.isBlank()) {
                    req.setAttribute("error", "Email et mot de passe sont obligatoires.");
                    req.setAttribute("next", next);
                    req.getRequestDispatcher("/pages/compte/register.jsp").forward(req, resp);
                    return;
                }

                if (internauteService.getByEmail(email).isPresent()) {
                    req.setAttribute("error", "Un compte existe déjà avec cet email.");
                    req.setAttribute("next", next);
                    req.getRequestDispatcher("/pages/compte/register.jsp").forward(req, resp);
                    return;
                }

                Internaute internaute = new Internaute();
                internaute.setNom(nom);
                internaute.setPrenom(prenom);
                internaute.setEmail(email);
                internaute.setMotDePasse(password);
                internauteService.create(internaute);

                req.getSession(true).setAttribute("user", internaute);

                // Redirection sécurisée après inscription
                if (next != null && !next.isBlank()) {
                    String ctx = req.getContextPath();
                    if (next.startsWith(ctx + "/")) {
                        resp.sendRedirect(next);
                        return;
                    } else if (next.startsWith("/")) {
                        resp.sendRedirect(ctx + next);
                        return;
                    }
                }
                resp.sendRedirect(req.getContextPath() + "/compte/profile");
                break;
            }
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
