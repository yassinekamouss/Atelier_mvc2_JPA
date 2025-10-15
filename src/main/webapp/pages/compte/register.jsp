<%--
  Created by IntelliJ IDEA.
  User: yassine-kamouss
  Date: 15/10/2025
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Inscription</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="min-h-screen bg-gray-50 flex items-center justify-center p-4">
<div class="w-full max-w-md bg-white rounded-lg shadow p-6">
    <h1 class="text-2xl font-bold mb-4 text-center">Créer un compte</h1>
    <c:if test="${not empty error}">
        <div class="mb-4 rounded border border-red-200 bg-red-50 text-red-700 px-3 py-2">${error}</div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/compte/register" class="space-y-4">
        <div>
            <label class="block text-sm mb-1">Nom</label>
            <input type="text" name="nom" class="w-full rounded border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
        </div>
        <div>
            <label class="block text-sm mb-1">Prénom</label>
            <input type="text" name="prenom" class="w-full rounded border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
        </div>
        <div>
            <label class="block text-sm mb-1">Email</label>
            <input type="email" name="email" required class="w-full rounded border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
        </div>
        <div>
            <label class="block text-sm mb-1">Mot de passe</label>
            <input type="password" name="password" required class="w-full rounded border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
        </div>
        <button type="submit" class="w-full rounded bg-green-600 px-4 py-2 text-white hover:bg-green-700">Créer mon compte</button>
    </form>
    <div class="text-center text-sm mt-4">
        Déjà inscrit ? <a href="${pageContext.request.contextPath}/compte/showLogin" class="text-blue-600 hover:underline">Se connecter</a>
    </div>
</div>
</body>
</html>
