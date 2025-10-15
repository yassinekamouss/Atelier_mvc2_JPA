package com.fstt.atelier2.mvc2_jpa.web;

import com.fstt.atelier2.mvc2_jpa.entities.Produit;
import com.fstt.atelier2.mvc2_jpa.services.IProduitService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "ProduitServlet", urlPatterns = {"/produits/*"})
public class ProduitServlet extends HttpServlet {

    @Inject
    private IProduitService produitService;

    @Override
    public void init() {}

    /**
     * Cette servlet est responsable de l'affichage des produits.
     */
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null) path = "/";

        switch (path) {
            case "/":
            case "/list": {
                String kw = firstNonNull(req.getParameter("q"), req.getParameter("keyword"));
                List<Produit> produits = (kw == null || kw.isBlank())
                        ? produitService.getAll()
                        : produitService.search(kw);
                req.setAttribute("produits", produits);
                req.setAttribute("keyword", kw);
                req.getRequestDispatcher("/pages/produit/vitrine.jsp").forward(req, resp);
                break;
            }
            case "/search": {
                String kw = firstNonNull(req.getParameter("q"), req.getParameter("keyword"));
                List<Produit> produits = (kw == null || kw.isBlank())
                        ? produitService.getAll()
                        : produitService.search(kw);
                req.setAttribute("produits", produits);
                req.setAttribute("keyword", kw);
                req.getRequestDispatcher("/pages/produit/vitrine.jsp").forward(req, resp);
                break;
            }
            case "/details": {
                String idParam = req.getParameter("id");
                if (idParam == null) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre id manquant");
                    return;
                }
                try {
                    Long id = Long.parseLong(idParam);
                    Optional<Produit> produitOpt = produitService.getById(id);
                    if (produitOpt.isEmpty()) {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Produit introuvable");
                        return;
                    }
                    req.setAttribute("produit", produitOpt.get());
                    req.getRequestDispatcher("/pages/produit/detailsProduit.jsp").forward(req, resp);
                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre id invalide");
                }
                break;
            }
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private String firstNonNull(String a, String b) {
        return a != null ? a : b;
    }
}
