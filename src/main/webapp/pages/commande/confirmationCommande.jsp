<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Confirmation commande</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 text-gray-900">
<header class="bg-white shadow">
    <div class="mx-auto max-w-6xl px-4 py-4 flex justify-between items-center">
        <a href="${pageContext.request.contextPath}/produits" class="text-xl font-semibold">Boutique</a>
        <nav class="space-x-4">
            <a href="${pageContext.request.contextPath}/produits" class="text-gray-700 hover:text-black">Produits</a>
            <a href="${pageContext.request.contextPath}/commande?action=history" class="text-gray-700 hover:text-black">Mes commandes</a>
            <a href="${pageContext.request.contextPath}/panier?action=view" class="text-gray-700 hover:text-black">Panier</a>
        </nav>
    </div>
</header>
<main class="mx-auto max-w-3xl px-4 py-10">
    <div class="rounded border border-green-200 bg-green-50 text-green-800 px-4 py-3 mb-6">
        <div class="font-semibold">Merci pour votre commande !</div>
        <div>Votre commande a bien été enregistrée.</div>
    </div>

    <c:if test="${empty commande}">
        <div class="bg-white rounded shadow p-6 text-center text-gray-600">Aucune commande à afficher.</div>
    </c:if>

    <c:if test="${not empty commande}">
        <div class="bg-white rounded shadow p-6">
            <h1 class="text-xl font-bold mb-2">Commande #${commande.id}</h1>
            <div class="text-sm text-gray-600 mb-4">Date: <fmt:formatDate value="${commande.dateCommande}" type="both" dateStyle="medium" timeStyle="short"/> — Statut: <span class="font-medium text-gray-800">${commande.statut}</span></div>

            <div class="overflow-x-auto">
                <table class="min-w-full">
                    <thead class="bg-gray-100 text-left text-sm text-gray-600">
                    <tr>
                        <th class="p-3">Produit</th>
                        <th class="p-3">Prix unitaire</th>
                        <th class="p-3">Quantité</th>
                        <th class="p-3">Sous-total</th>
                    </tr>
                    </thead>
                    <tbody class="divide-y">
                    <c:forEach var="l" items="${commande.ligneCommande}">
                        <tr>
                            <td class="p-3">${l.produit.nom}</td>
                            <td class="p-3"><fmt:formatNumber value="${l.prixUnitaire}" type="currency" currencySymbol="€"/></td>
                            <td class="p-3">${l.quantite}</td>
                            <td class="p-3 font-semibold text-blue-700"><fmt:formatNumber value="${l.prixUnitaire * l.quantite}" type="currency" currencySymbol="€"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="mt-4 text-right text-lg">
                Total: <span class="font-bold text-blue-700"><fmt:formatNumber value="${commande.montantTotal}" type="currency" currencySymbol="€"/></span>
            </div>
        </div>

        <div class="mt-6 flex flex-wrap gap-3 justify-end">
            <a href="${pageContext.request.contextPath}/commande?action=history" class="rounded bg-gray-800 px-4 py-2 text-white hover:bg-black">Voir mes commandes</a>
            <a href="${pageContext.request.contextPath}/produits" class="rounded bg-blue-600 px-4 py-2 text-white hover:bg-blue-700">Continuer mes achats</a>
        </div>
    </c:if>
</main>
</body>
</html>
