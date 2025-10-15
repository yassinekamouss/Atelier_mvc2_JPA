<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Vitrine des produits</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script crossorigin src="https://unpkg.com/react@18/umd/react.production.min.js"></script>
    <script crossorigin src="https://unpkg.com/react-dom@18/umd/react-dom.production.min.js"></script>
    <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
</head>
<body class="bg-gray-50 text-gray-900">
<header class="bg-white shadow">
    <div class="mx-auto max-w-7xl px-4 py-4 flex justify-between items-center">
        <a href="${pageContext.request.contextPath}/produits" class="text-xl font-semibold">Boutique</a>
        <nav class="space-x-4">
            <a href="${pageContext.request.contextPath}/produits" class="text-gray-700 hover:text-black">Produits</a>
            <a href="${pageContext.request.contextPath}/panier?action=view" class="text-gray-700 hover:text-black">Panier</a>
            <a href="${pageContext.request.contextPath}/compte/profile" class="text-gray-700 hover:text-black">Mon compte</a>
        </nav>
    </div>
</header>
<main class="mx-auto max-w-7xl px-4 py-6">
    <div class="mb-6">
        <form action="${pageContext.request.contextPath}/produits/search" method="get" class="flex gap-2">
            <input name="q" value="${keyword}" placeholder="Rechercher un produit..."
                   class="flex-1 rounded border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
            <button type="submit" class="rounded bg-blue-600 px-4 py-2 text-white hover:bg-blue-700">Rechercher</button>
        </form>
    </div>

    <c:choose>
        <c:when test="${empty produits}">
            <div class="text-center text-gray-600">Aucun produit à afficher.</div>
        </c:when>
        <c:otherwise>
            <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                <c:forEach var="p" items="${produits}">
                    <div class="bg-white rounded-lg shadow p-5 flex flex-col">
                        <div class="flex-1">
                            <h3 class="text-lg font-semibold mb-1">${p.nom}</h3>
                            <p class="text-sm text-gray-600 mb-2 line-clamp-3">${p.description}</p>
                            <div class="text-blue-700 font-bold text-lg mb-1">
                                <fmt:formatNumber value="${p.prix}" type="currency" currencySymbol="€" />
                            </div>
                            <div class="text-xs text-gray-500 mb-4">Stock: ${p.quantite}</div>
                        </div>
                        <div class="mt-4 flex items-center gap-2">
                            <div id="qty-widget-${p.id}" class="flex-1"></div>
                            <form action="${pageContext.request.contextPath}/panier" method="post" class="contents">
                                <input type="hidden" name="action" value="add" />
                                <input type="hidden" name="productId" value="${p.id}" />
                                <input type="hidden" name="quantite" id="qty-input-${p.id}" value="1" />
                                <button type="submit" class="rounded bg-green-600 px-4 py-2 text-white hover:bg-green-700">Ajouter</button>
                            </form>
                        </div>
                        <a href="${pageContext.request.contextPath}/produits/details?id=${p.id}" class="mt-3 text-sm text-blue-600 hover:underline">Voir détails</a>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
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
        document.querySelectorAll('[id^="qty-widget-"]').forEach(el => {
            const id = el.id.replace('qty-widget-','');
            ReactDOM.createRoot(el).render(<Qty id={id} />);
        });
    });
</script>
</body>
</html>
