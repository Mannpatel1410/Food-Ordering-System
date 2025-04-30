<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order History</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order-history.css">
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
    
    <div class="history-container">
        <a href="${pageContext.request.contextPath}/dashboard" class="back-link">← Back to Restaurants</a>
        
        <h1>Your Order History</h1>
        
        <c:if test="${empty orders}">
            <div class="no-orders">
                <p>You don't have any orders yet. <a href="${pageContext.request.contextPath}/dashboard">Browse restaurants</a> to place your first order.</p>
            </div>
        </c:if>
        
        <c:if test="${not empty orders}">
            <div class="order-list">
                <c:forEach items="${orders}" var="order">
                    <div class="order-card">
                        <div class="order-header">
                            <h3>Order #${order.orderNumber}</h3>
                            <span class="order-date">
                                <fmt:formatDate value="${order.orderDate}" pattern="MMM dd, yyyy HH:mm" />
                            </span>
                        </div>
                        <div class="order-body">
                            <div>
                                <span class="order-status ${order.status.toLowerCase()}">${order.status}</span>
                            </div>
                            <div class="order-total">
                                Total: $<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00" />
                            </div>
                            <div class="order-items-count">
                                ${order.items.size()} item(s)
                            </div>
                        </div>
                        <div class="order-footer">
                            <a href="${pageContext.request.contextPath}/orders/details/${order.id}" class="view-details-btn">View Details</a>
                            <form action="${pageContext.request.contextPath}/orders/reorder/${order.id}" method="post" style="display: inline;">
                                <button type="submit" class="reorder-btn">Reorder</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
</body>
</html>
