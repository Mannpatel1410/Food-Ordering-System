<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>${restaurant.name} Menu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/restaurant-menu.css">
</head>
<body>
    <div class="cart-header" style="text-align: right; padding: 10px 20px;">
        <a href="${pageContext.request.contextPath}/cart/view" 
           class="cart-link"
           style="text-decoration: none; color: #333; font-weight: bold;">
           🛒 Cart 
           <c:if test="${not empty cart && not empty cart.items}">
              <span class="cart-count" style="background: #4CAF50; color: white; 
                    border-radius: 50%; padding: 2px 6px; font-size: 0.8em;">
                  ${cart.items.size()}
              </span>
           </c:if>
        </a>
</div>
    <div class="menu-container">
        <a href="${pageContext.request.contextPath}/dashboard" class="back-link">← Back to Restaurants</a>
        
        <div class="restaurant-header">
            <h1>${restaurant.name}</h1>
            <p><strong>Location:</strong> ${restaurant.location}</p>
            <p>${restaurant.description}</p>
        </div>
        
        <h2 class="menu-category">Menu</h2>
        
        <c:forEach items="${restaurant.menuItems}" var="item">
            <div class="menu-item">
                <div class="menu-item-info">
                    <h3>${item.name}</h3>
                    <p>${item.description}</p>
                </div>
                <div class="menu-item-actions">
                    <p class="item-price">$${String.format("%.2f", item.price)}</p>
                    <form action="${pageContext.request.contextPath}/cart/add" method="post">
                        <input type="hidden" name="menuItemId" value="${item.id}">
                        <input type="hidden" name="restaurantId" value="${restaurant.id}">
                        <button type="submit" class="add-to-cart">Add to Cart</button>
                    </form>
                    <c:if test="${param.addedItemId == item.id}">
                        <p class="cart-message">Added to cart!</p>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>
</body>
</html>
