<%--
  Created by IntelliJ IDEA.
  User: yassine-kamouss
  Date: 15/10/2025
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Mon Compte</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/neutrals.css" />
</head>
<body class="bg-gray-50 text-gray-900">
<header class="bg-white shadow">
    <div class="mx-auto max-w-5xl px-4 py-4 flex justify-between items-center">
        <a href="${pageContext.request.contextPath}/produits" class="text-xl font-semibold">Boutique</a>
        <nav class="space-x-4">
            <a href="${pageContext.request.contextPath}/commande?action=history" class="text-gray-700 hover:text-black">Mes commandes</a>
            <a href="${pageContext.request.contextPath}/panier?action=view" class="text-gray-700 hover:text-black">Panier</a>
            <a href="${pageContext.request.contextPath}/compte/logout" class="text-gray-700 hover:text-black">Se déconnecter</a>
        </nav>
    </div>
</header>
<main class="mx-auto max-w-5xl px-4 py-8">
    <c:set var="user" value="${sessionScope.user}" />
    <c:if test="${empty user}">
        <div class="bg-white rounded shadow p-6">Vous n'êtes pas connecté. <a class="text-blue-600" href="${pageContext.request.contextPath}/compte/showLogin">Se connecter</a></div>
    </c:if>
    <c:if test="${not empty user}">
        <div class="bg-white rounded-lg shadow p-6">
            <h1 class="text-2xl font-bold mb-4">Mon compte</h1>
            <div class="grid sm:grid-cols-2 gap-4">
                <div><span class="text-gray-500">Nom:</span> <span class="font-medium">${user.nom}</span></div>
                <div><span class="text-gray-500">Prénom:</span> <span class="font-medium">${user.prenom}</span></div>
                <div class="sm:col-span-2"><span class="text-gray-500">Email:</span> <span class="font-medium">${user.email}</span></div>
            </div>
            <div class="mt-6 flex flex-wrap gap-3">
                <a href="${pageContext.request.contextPath}/produits" class="rounded bg-blue-600 px-4 py-2 text-white hover:bg-blue-700">Voir les produits</a>
                <a href="${pageContext.request.contextPath}/panier?action=view" class="rounded bg-gray-800 px-4 py-2 text-white hover:bg-black">Voir mon panier</a>
                <a href="${pageContext.request.contextPath}/commande?action=history" class="rounded bg-green-600 px-4 py-2 text-white hover:bg-green-700">Mes commandes</a>
                <a href="${pageContext.request.contextPath}/compte/logout" class="rounded bg-red-600 px-4 py-2 text-white hover:bg-red-700">Se déconnecter</a>
            </div>
        </div>
    </c:if>
</main>
</body>
</html>
