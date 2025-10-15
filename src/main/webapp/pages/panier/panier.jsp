<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Mon Panier</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script crossorigin src="https://unpkg.com/react@18/umd/react.production.min.js"></script>
    <script crossorigin src="https://unpkg.com/react-dom@18/umd/react-dom.production.min.js"></script>
    <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
</head>
<body class="bg-gray-50 text-gray-900">
<header class="bg-white shadow">
    <div class="mx-auto max-w-6xl px-4 py-4 flex justify-between items-center">
        <a href="${pageContext.request.contextPath}/produits" class="text-xl font-semibold">Boutique</a>
        <nav class="space-x-4">
            <a href="${pageContext.request.contextPath}/produits" class="text-gray-700 hover:text-black">Produits</a>
            <a href="${pageContext.request.contextPath}/commande?action=history" class="text-gray-700 hover:text-black">Mes commandes</a>
            <a href="${pageContext.request.contextPath}/compte/profile" class="text-gray-700 hover:text-black">Mon compte</a>
        </nav>
    </div>
</header>
<main class="mx-auto max-w-6xl px-4 py-8">
    <h1 class="text-2xl font-bold mb-6">Mon panier</h1>

    <c:choose>
        <c:when test="${empty panier || empty panier.lignePaniers}">
            <div class="bg-white rounded shadow p-6 text-center text-gray-600">Votre panier est vide.</div>
        </c:when>
        <c:otherwise>
            <div class="bg-white rounded shadow overflow-hidden">
                <table class="min-w-full">
                    <thead class="bg-gray-100 text-left text-sm text-gray-600">
                    <tr>
                        <th class="p-3">Produit</th>
                        <th class="p-3">Prix unitaire</th>
                        <th class="p-3">Quantité</th>
                        <th class="p-3">Sous-total</th>
                        <th class="p-3 text-right">Actions</th>
                    </tr>
                    </thead>
                    <tbody class="divide-y">
                    <c:set var="total" value="0"/>
                    <c:forEach var="l" items="${panier.lignePaniers}">
                        <tr>
                            <td class="p-3">
                                <div class="font-medium">${l.produit.nom}</div>
                                <div class="text-xs text-gray-500">Ref: ${l.produit.id}</div>
                            </td>
                            <td class="p-3"><fmt:formatNumber value="${l.produit.prix}" type="currency" currencySymbol="€"/></td>
                            <td class="p-3">
                                <div class="flex items-center gap-2">
                                    <div id="qty-widget-${l.produit.id}"></div>
                                    <form action="${pageContext.request.contextPath}/panier" method="post" class="contents">
                                        <input type="hidden" name="action" value="update" />
                                        <input type="hidden" name="productId" value="${l.produit.id}" />
                                        <input type="hidden" name="quantite" id="qty-input-${l.produit.id}" value="${l.quantite}" />
                                        <button type="submit" class="rounded bg-blue-600 px-3 py-1 text-white hover:bg-blue-700">Mettre à jour</button>
                                    </form>
                                </div>
                            </td>
                            <td class="p-3 font-semibold text-blue-700">
                                <fmt:formatNumber value="${l.produit.prix * l.quantite}" type="currency" currencySymbol="€"/>
                            </td>
                            <td class="p-3 text-right">
                                <a href="${pageContext.request.contextPath}/panier?action=remove&id=${l.produit.id}"
                                   class="text-red-600 hover:underline">Supprimer</a>
                            </td>
                        </tr>
                        <c:set var="total" value="${total + (l.produit.prix * l.quantite)}"/>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="flex justify-between items-center p-4 border-t bg-gray-50">
                    <a href="${pageContext.request.contextPath}/panier?action=clear" class="text-red-600 hover:underline">Vider le panier</a>
                    <div class="text-lg">Total: <span class="font-bold text-blue-700"><fmt:formatNumber value="${total}" type="currency" currencySymbol="€"/></span></div>
                </div>
            </div>

            <div class="mt-6 flex justify-end">
                <form action="${pageContext.request.contextPath}/commande" method="post">
                    <input type="hidden" name="action" value="placeOrder" />
                    <button type="submit" class="rounded bg-green-600 px-6 py-2 text-white hover:bg-green-700">Passer commande</button>
                </form>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<script type="text/babel">
    const Qty = ({id, initial=1, min=1, max=99}) => {
        const [q, setQ] = React.useState(initial);
        React.useEffect(() => {
            const input = document.getElementById(`qty-input-${id}`);
            if (input) input.value = q;
        }, [q]);
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
            const hidden = document.getElementById(`qty-input-${id}`);
            const initial = hidden ? parseInt(hidden.value||'1',10) : 1;
            ReactDOM.createRoot(el).render(<Qty id={id} initial={initial} />);
        });
    });
</script>
</body>
</html>
