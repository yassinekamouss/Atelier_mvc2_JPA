package com.fstt.atelier2.mvc2_jpa.web;

import com.fstt.atelier2.mvc2_jpa.entities.Commande;
import com.fstt.atelier2.mvc2_jpa.entities.Internaute;
import com.fstt.atelier2.mvc2_jpa.entities.Panier;
import com.fstt.atelier2.mvc2_jpa.services.ICommandeService;
import com.fstt.atelier2.mvc2_jpa.services.IPanierService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "CommandeServlet", urlPatterns = {"/commande"})
public class CommandeServlet extends HttpServlet {

    @Inject
    private ICommandeService commandeService;
    @Inject
    private IPanierService panierService;

    @Override
    public void init(){}


    /**
     *
     * /history : Affiche l'historique des commandes de l'utilisateur connecté (page historique.jsp)
     * /details?id= : Affiche les détails d'une commande spécifique (page detailsCommande.jsp)
     */
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String action = req.getParameter("action");
        if (pathInfo != null && pathInfo.length() > 1) {
            action = pathInfo.substring(1);
        }
        if (action == null || action.isBlank()) action = "history";

        Internaute user = (Internaute) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/compte/showLogin");
            return;
        }

        switch (action) {
            case "history": {
                List<Commande> commandes = commandeService.getByInternauteId(user.getId());
                req.setAttribute("commandes", commandes);
                req.getRequestDispatcher("/pages/commande/historique.jsp").forward(req, resp);
                break;
            }
            case "details": {
                String idParam = req.getParameter("id");
                if (idParam == null) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre id manquant");
                    return;
                }
                try {
                    Long id = Long.parseLong(idParam);
                    Optional<Commande> cmdOpt = commandeService.getById(id);
                    if (cmdOpt.isEmpty() || cmdOpt.get().getInternaute() == null || !cmdOpt.get().getInternaute().getId().equals(user.getId())) {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Commande introuvable");
                        return;
                    }
                    req.setAttribute("commande", cmdOpt.get());
                    req.getRequestDispatcher("/pages/commande/detailsCommande.jsp").forward(req, resp);
                } catch (NumberFormatException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre id invalide");
                }
                break;
            }
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     *
     * /placeOrder : Crée une commande à partir du panier, puis vide le panier.(page confirmationCommande.jsp)
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null || action.isBlank()) action = "placeOrder";

        Internaute user = (Internaute) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/compte/showLogin");
            return;
        }

        if ("placeOrder".equals(action)) {
            Panier panier = panierService.getOrCreateForInternaute(user);
            if (panier.getLignePaniers() == null || panier.getLignePaniers().isEmpty()) {
                req.setAttribute("error", "Votre panier est vide.");
                resp.sendRedirect(req.getContextPath() + "/panier?action=view");
                return;
            }
            try {
                Commande commande = commandeService.creerDepuisPanier(panier);
                panierService.vider(panier);
                req.setAttribute("commande", commande);
                req.getRequestDispatcher("/pages/commande/confirmationCommande.jsp").forward(req, resp);
            } catch (IllegalArgumentException e) {
                req.setAttribute("error", e.getMessage());
                resp.sendRedirect(req.getContextPath() + "/panier?action=view");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}
