package com.fstt.atelier2.mvc2_jpa.web;

import com.fstt.atelier2.mvc2_jpa.entities.Internaute;
import com.fstt.atelier2.mvc2_jpa.entities.Panier;
import com.fstt.atelier2.mvc2_jpa.entities.Produit;
import com.fstt.atelier2.mvc2_jpa.services.IPanierService;
import com.fstt.atelier2.mvc2_jpa.services.IProduitService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "PanierServlet", urlPatterns = {"/panier"})
public class PanierServlet extends HttpServlet {

    @Inject
    private IPanierService panierService;
    @Inject
    private IProduitService produitService;

    @Override
    public void init(){}

    /**
     * Cette servlet est responsable de la gestion du panier.
     * GET /panier?action=view : Afficher le contenu du panier
     * GET /panier?action=remove&id= : Supprimer un article du panier puis rediriger vers la vue du panier
     * GET /panier?action=clear : Vider le panier
     */
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String action = req.getParameter("action");
        if (pathInfo != null && pathInfo.length() > 1) {
            action = pathInfo.substring(1); // ex: /view
        }
        if (action == null || action.isBlank()) action = "view";

        Internaute user = (Internaute) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/compte/showLogin");
            return;
        }

        Panier panier = panierService.getOrCreateForInternaute(user);

        switch (action) {
            case "view":
                req.setAttribute("panier", panier);
                req.getRequestDispatcher("/pages/panier/panier.jsp").forward(req, resp);
                break;
            case "remove": {
                String idParam = req.getParameter("id");
                if (idParam == null) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre id manquant");
                    return;
                }
                try {
                    Long produitId = Long.parseLong(idParam);
                    panierService.removeProduit(panier, produitId);
                    resp.sendRedirect(req.getContextPath() + "/panier?action=view");
                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre id invalide");
                }
                break;
            }
            case "clear":
                panierService.vider(panier);
                resp.sendRedirect(req.getContextPath() + "/panier?action=view");
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    /**
     * Ajoute un produit (avec une quantité) au panier
     * Met à jour la quantité d'un produit dans le panier.
     * POST /panier?action=add&productId=&quantite=
     * POST /panier?action=update&productId=&quantite=
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        if (action == null || action.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action manquante");
            return;
        }

        Internaute user = (Internaute) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/compte/showLogin");
            return;
        }

        Panier panier = panierService.getOrCreateForInternaute(user);

        String pid = req.getParameter("productId");
        String qty = req.getParameter("quantite");

        try {
            if (pid == null || qty == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètres productId et quantite requis");
                return;
            }
            Long productId = Long.parseLong(pid);
            int quantite = Integer.parseInt(qty);
            if (quantite < 0) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quantité invalide");
                return;
            }

            switch (action) {
                case "add": {
                    Optional<Produit> produitOpt = produitService.getById(productId);
                    if (produitOpt.isEmpty()) {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Produit introuvable");
                        return;
                    }
                    panierService.addProduit(panier, produitOpt.get(), quantite == 0 ? 1 : quantite);
                    resp.sendRedirect(req.getContextPath() + "/panier?action=view");
                    break;
                }
                case "update":
                    panierService.updateQuantite(panier, productId, quantite);
                    resp.sendRedirect(req.getContextPath() + "/panier?action=view");
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètres numériques invalides");
        }
    }
}
