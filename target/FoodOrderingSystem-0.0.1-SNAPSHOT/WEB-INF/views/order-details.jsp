<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order-details.css">

</head>
<body>
    <div class="cart-header">
        <a href="${pageContext.request.contextPath}/cart/view" class="cart-link">
            🛒 Cart
            <c:if test="${not empty cart && not empty cart.items}">
                <span class="cart-count">${cart.items.size()}</span>
            </c:if>
        </a>
    </div>
    
    <div class="details-container">
        <a href="${pageContext.request.contextPath}/orders/history" class="back-link">← Back to Order History</a>
        
        <div class="header-with-button">
            <h1>Order Details</h1>
        </div>
        
        <div class="details-panel">
            <div class="order-meta">
                <div class="order-meta-group">
                    <p><strong>Order Number:</strong> ${order.orderNumber}</p>
                    <p><strong>Date:</strong> <fmt:formatDate value="${order.orderDate}" pattern="MMM dd, yyyy HH:mm" /></p>
                </div>
                <div class="order-meta-group">
                    <p><strong>Status:</strong> <span class="badge ${order.status.toLowerCase()}">${order.status}</span></p>
                </div>
            </div>
            
            <h2 class="section-title">Items</h2>
            
            <table>
                <thead>
                    <tr>
                        <th>Item</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th class="text-right">Subtotal</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${order.items}" var="item">
                        <tr>
                            <td>${item.itemName}</td>
                            <td>$<fmt:formatNumber value="${item.itemPrice}" pattern="#,##0.00" /></td>
                            <td>${item.quantity}</td>
                            <td class="text-right">$<fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <th colspan="3" class="text-right">Total:</th>
                        <th class="text-right">$<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00" /></th>
                    </tr>
                </tfoot>
            </table>
        </div>
        
        <div style="text-align: right;">
            <form action="${pageContext.request.contextPath}/orders/reorder/${order.id}" method="post" style="display: inline;">
                <button type="submit" class="reorder-btn">Reorder</button>
            </form>
        </div>
    </div>
</body>
</html>
