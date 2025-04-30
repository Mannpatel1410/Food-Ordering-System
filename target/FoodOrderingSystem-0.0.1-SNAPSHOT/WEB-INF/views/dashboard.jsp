<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body>
    <header class="header-container">
        <div class="header-actions">
            <form action="${pageContext.request.contextPath}/search" method="get" class="search-container">
                <input type="text" name="keyword" placeholder="Search for restaurants or food">
                <button type="submit">Search</button>
            </form>
            <nav class="navigation-links">
                <a href="${pageContext.request.contextPath}/orders/history" class="nav-btn">Order History</a>
                <a href="${pageContext.request.contextPath}/cart/view" class="nav-btn">View Cart</a>
                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Logout</a>
            </nav>
        </div>
    </header>

    <main>
        <c:choose>
            <c:when test="${not empty restaurants}">
                <section class="restaurant-grid">
                    <c:forEach items="${restaurants}" var="restaurant">
                        <div class="restaurant-card">
							<img src="${pageContext.request.contextPath}${restaurant.imageUrl}"
							     alt="${restaurant.name}"
							     class="restaurant-image"
							     onerror="this.src='${pageContext.request.contextPath}/images/burgerking.png'">
                            <div class="restaurant-info">
                                <h3>${restaurant.name}</h3>
                                <p><strong>📍 ${restaurant.location}</strong></p>
                                <p>${restaurant.description}</p>
                                <a href="${pageContext.request.contextPath}/dashboard/restaurant/${restaurant.id}" class="view-menu-btn">View Menu</a>
                            </div>
                        </div>
                    </c:forEach>
                </section>
            </c:when>
            <c:otherwise>
                <div class="no-restaurants">
                    <p>No restaurants available at the moment.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </main>
</body>
</html>