<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Results for "${keyword}"</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/search-results.css">
</head>
<body>
    <div class="search-header">
		<a href="${pageContext.request.contextPath}/dashboard">← Back to Dashboard</a>
        <h2 class="search-heading">Search Results for "${keyword}"</h2>
    </div>
    
    <div class="results-section">
        <h3>Restaurants</h3>
        <c:choose>
            <c:when test="${not empty restaurants}">
                <div class="restaurant-grid">
                    <c:forEach items="${restaurants}" var="restaurant">
                        <div class="restaurant-card">
                            <div class="card-info">
                                <h2>${restaurant.name}</h3>
                                <p><strong>Location:</strong> ${restaurant.location}</p>
                                <p>${restaurant.description}</p>
                                <a href="${pageContext.request.contextPath}/dashboard/restaurant/${restaurant.id}" class="cart-btn">View Menu</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="no-results">
                    <p>No restaurants found matching "${keyword}".</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    
    <div class="results-section">
        <h3>Menu Items</h3>
        <c:choose>
            <c:when test="${not empty menuItems}">
                <div class="menu-item-grid">
                    <c:forEach items="${menuItems}" var="item">
                        <div class="menu-item-card">
                            <div class="card-info">
                                <h3>${item.name}</h3>
                                <p>${item.description}</p>
								<p><strong>Restaurant:</strong> ${item.restaurant.name}</p>
                                <p><strong>Category:</strong> ${item.category}</p>
                                <p><strong>Price:</strong> $${String.format("%.2f", item.price)}</p>
                                <form action="${pageContext.request.contextPath}/cart/add" method="post">
                                    <input type="hidden" name="menuItemId" value="${item.id}">
                                    <input type="hidden" name="restaurantId" value="${item.restaurant.id}">
                                    <button type="submit" class="cart-btn">Add to Cart</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="no-results">
                    <p>No menu items found matching "${keyword}".</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>