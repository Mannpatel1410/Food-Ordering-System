<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>Your Shopping Cart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
</head>
<body>
    <div class="cart-container">
        <a href="${pageContext.request.contextPath}/dashboard" class="back-link">← Back to Restaurants</a>
        <h1>Your Cart</h1>
        
        <c:choose>
            <c:when test="${not empty cart.items}">
                <c:forEach items="${cart.items}" var="item">
                    <div class="cart-item">
                        <div>
                            <h3>${item.menuItem.name}</h3>
                            <p>$${item.menuItem.price} × ${item.quantity}</p>
                        </div>
                        <div class="item-actions">
                            <form action="<c:url value='/cart/update'/>" method="post">
                                <input type="hidden" name="menuItemId" value="${item.menuItem.id}">
                                <input type="number" name="quantity" class="input-field" value="${item.quantity}" min="1">
                                <button type="submit" class="update-btn">Update</button>
                            </form>
                            <form action="<c:url value='/cart/remove'/>" method="post">
                                <input type="hidden" name="menuItemId" value="${item.menuItem.id}">
                                <button type="submit" class="delete-btn">Remove</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
                <div class="cart-total">
                    <h3>Total: $<fmt:formatNumber value="${total}" minFractionDigits="2"/></h3>
                    <a href="<c:url value='/cart/checkout'/>">Proceed to Checkout</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-cart">
                    <p>Your cart is empty.</p>
                    <a href="${pageContext.request.contextPath}/dashboard">Browse Restaurants</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>