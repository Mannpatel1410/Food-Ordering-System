<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Order Confirmation</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/checkout.css">
</head>
<body>
    <div class="confirm-box">
        <div class="success-icon">✓</div>
        <h1>Order Placed Successfully!</h1>
		<p><strong>Delivery Address:</strong> ${userAddress}</p>
        <p>Thank you for your order. Your food is being prepared and will be delivered soon.</p>
        <p>Order ID: #${orderID}</p>
        <a href="${pageContext.request.contextPath}/cart/complete" class="continue-btn">Continue Shopping</a>
        <div style="margin-top: 20px">
            <a href="${pageContext.request.contextPath}/invoice/download">Download Invoice</a>
        </div>
    </div>
</body>
</html>
