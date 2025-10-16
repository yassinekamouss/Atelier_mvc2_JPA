<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Mes Commandes</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/neutrals.css" />
</head>
<body class="bg-gray-50 text-gray-900">
<header class="bg-white shadow">
    <div class="mx-auto max-w-6xl px-4 py-4 flex justify-between items-center">
        <a href="${pageContext.request.contextPath}/produits" class="text-xl font-semibold">Boutique</a>
        <nav class="space-x-4">
            <a href="${pageContext.request.contextPath}/produits" class="text-gray-700 hover:text-black">Produits</a>
            <a href="${pageContext.request.contextPath}/panier?action=view" class="text-gray-700 hover:text-black">Panier</a>
            <a href="${pageContext.request.contextPath}/compte/profile" class="text-gray-700 hover:text-black">Mon compte</a>
        </nav>
    </div>
</header>
<main class="mx-auto max-w-6xl px-4 py-8">
    <h1 class="text-2xl font-bold mb-6">Mes commandes</h1>

    <c:choose>
        <c:when test="${empty commandes}">
            <div class="bg-white rounded shadow p-6 text-center text-gray-600">Aucune commande passée pour le moment.</div>
        </c:when>
        <c:otherwise>
            <div class="bg-white rounded shadow overflow-hidden">
                <table class="min-w-full">
                    <thead class="bg-gray-100 text-left text-sm text-gray-600">
                    <tr>
                        <th class="p-3">#</th>
                        <th class="p-3">Date</th>
                        <th class="p-3">Statut</th>
                        <th class="p-3">Montant</th>
                        <th class="p-3 text-right">Actions</th>
                    </tr>
                    </thead>
                    <tbody class="divide-y">
                    <c:forEach var="cde" items="${commandes}">
                        <tr>
                            <td class="p-3">${cde.id}</td>
                            <td class="p-3"><fmt:formatDate value="${cde.dateCommande}" type="both" dateStyle="medium" timeStyle="short"/></td>
                            <td class="p-3">${cde.statut}</td>
                            <td class="p-3 font-semibold text-blue-700"><fmt:formatNumber value="${cde.montantTotal}" type="currency" currencySymbol="€"/></td>
                            <td class="p-3 text-right">
                                <a href="${pageContext.request.contextPath}/commande?action=details&id=${cde.id}" class="text-blue-600 hover:underline">Détails</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</main>
</body>
</html>
