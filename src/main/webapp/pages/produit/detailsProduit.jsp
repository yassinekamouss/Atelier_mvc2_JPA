<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Détails du produit</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script crossorigin src="https://unpkg.com/react@18/umd/react.production.min.js"></script>
    <script crossorigin src="https://unpkg.com/react-dom@18/umd/react-dom.production.min.js"></script>
    <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
</head>
<body class="bg-gray-50 text-gray-900">
<header class="bg-white shadow">
    <div class="mx-auto max-w-5xl px-4 py-4 flex justify-between items-center">
        <a href="${pageContext.request.contextPath}/produits" class="text-xl font-semibold">Boutique</a>
        <nav class="space-x-4">
            <a href="${pageContext.request.contextPath}/produits" class="text-gray-700 hover:text-black">Produits</a>
            <a href="${pageContext.request.contextPath}/panier?action=view" class="text-gray-700 hover:text-black">Panier</a>
            <a href="${pageContext.request.contextPath}/compte/profile" class="text-gray-700 hover:text-black">Mon compte</a>
        </nav>
    </div>
</header>
<main class="mx-auto max-w-5xl px-4 py-8">
    <c:if test="${empty produit}">
        <div class="text-center text-gray-600">Produit introuvable.</div>
    </c:if>
    <c:if test="${not empty produit}">
        <div class="bg-white rounded-lg shadow p-6">
            <h1 class="text-2xl font-bold mb-2">${produit.nom}</h1>
            <div class="text-gray-600 mb-4">${produit.description}</div>
            <div class="text-2xl text-blue-700 font-bold mb-1">
                <fmt:formatNumber value="${produit.prix}" type="currency" currencySymbol="€" />
            </div>
            <div class="text-sm text-gray-500 mb-6">Stock: ${produit.quantite}</div>

            <div class="flex items-center gap-4">
                <div id="qty-widget-${produit.id}"></div>
                <form action="${pageContext.request.contextPath}/panier" method="post" class="contents">
                    <input type="hidden" name="action" value="add" />
                    <input type="hidden" name="productId" value="${produit.id}" />
                    <input type="hidden" name="quantite" id="qty-input-${produit.id}" value="1" />
                    <button type="submit" class="rounded bg-green-600 px-5 py-2 text-white hover:bg-green-700">Ajouter au panier</button>
                </form>
                <a href="${pageContext.request.contextPath}/produits" class="text-gray-600 hover:underline">Retour à la vitrine</a>
            </div>
        </div>
    </c:if>
</main>

<script type="text/babel">
    const Qty = ({id, initial=1, min=1, max=99}) => {
        const [q, setQ] = React.useState(initial);
        const updateHidden = (val) => {
            const input = document.getElementById(`qty-input-${id}`);
            if (input) input.value = val;
        };
        React.useEffect(()=>updateHidden(q), [q]);
        const dec = () => setQ(v => Math.max(min, v-1));
        const inc = () => setQ(v => Math.min(max, v+1));
        const onChange = (e) => {
            const v = parseInt(e.target.value||"1", 10);
            if (!Number.isNaN(v)) setQ(Math.min(max, Math.max(min, v)));
        };
        return (
            <div className="inline-flex items-center border rounded overflow-hidden">
                <button type="button" onClick={dec} className="px-2 py-1 bg-gray-100 hover:bg-gray-200">-</button>
                <input value={q} onChange={onChange} className="w-12 text-center outline-none" />
                <button type="button" onClick={inc} className="px-2 py-1 bg-gray-100 hover:bg-gray-200">+</button>
            </div>
        );
    };
    window.addEventListener('DOMContentLoaded', () => {
        const el = document.getElementById('qty-widget-${produit.id}');
        if (el) ReactDOM.createRoot(el).render(<Qty id={'${produit.id}'} />);
    });
</script>
</body>
</html>
