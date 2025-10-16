<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Connexion</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/neutrals.css" />
</head>
<body class="min-h-screen bg-gray-50 flex items-center justify-center p-4">
<div class="w-full max-w-md bg-white rounded-lg shadow p-6">
    <h1 class="text-2xl font-bold mb-4 text-center">Se connecter</h1>
    <c:if test="${not empty error}">
        <div class="mb-4 rounded border border-red-200 bg-red-50 text-red-700 px-3 py-2">${error}</div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/compte/login" class="space-y-4">
        <input type="hidden" name="next" value="${not empty param.next ? param.next : next}" />
        <div>
            <label class="block text-sm mb-1">Email</label>
            <input type="email" name="email" required class="w-full rounded border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
        </div>
        <div>
            <label class="block text-sm mb-1">Mot de passe</label>
            <input type="password" name="password" required class="w-full rounded border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
        </div>
        <button type="submit" class="w-full rounded bg-blue-600 px-4 py-2 text-white hover:bg-blue-700">Connexion</button>
    </form>
    <div class="text-center text-sm mt-4">
        <c:set var="nextVal" value="${not empty param.next ? param.next : next}" />
        <c:url var="registerUrl" value="/compte/showRegister">
            <c:if test="${not empty nextVal}">
                <c:param name="next" value="${nextVal}" />
            </c:if>
        </c:url>
        Pas de compte ? <a href="${registerUrl}" class="text-blue-600 hover:underline">S'inscrire</a>
    </div>
</div>
</body>
</html>
