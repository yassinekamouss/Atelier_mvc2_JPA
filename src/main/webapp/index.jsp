<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Hello E-commerce</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/neutrals.css" />
</head>
<body>
<header class="header">
    <div class="header-inner container">
        <a href="${pageContext.request.contextPath}/produits" class="text-strong" style="font-size: 1.1rem;">Boutique</a>
        <nav style="display:flex; gap:1rem;">
            <a href="${pageContext.request.contextPath}/produits">Produits</a>
            <a href="${pageContext.request.contextPath}/panier?action=view">Panier</a>
            <a href="${pageContext.request.contextPath}/compte/showLogin">Se connecter</a>
        </nav>
    </div>
</header>
<main class="container" style="padding-top:2rem; padding-bottom:2rem;">
    <section class="card" style="padding:2rem; text-align:center;">
        <h1 class="mb-2" style="font-size:2rem;">Bienvenue sur notre boutique</h1>
        <p class="text-muted mb-6">Découvrez une sélection de produits avec une interface épurée et professionnelle.</p>
        <div style="display:flex; gap:.75rem; justify-content:center; flex-wrap:wrap;">
            <a class="btn btn-dark" href="${pageContext.request.contextPath}/produits">Voir les produits</a>
            <a class="btn btn-light" href="${pageContext.request.contextPath}/compte/showRegister">Créer un compte</a>
        </div>
    </section>
</main>
<footer class="container text-muted" style="padding:1.5rem 1rem; text-align:center; font-size:.9rem;">
    © <%= java.time.Year.now() %> Boutique. Tous droits réservés.
</footer>
</body>
</html>