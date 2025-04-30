<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signup.css">
</head>
<body>
    <p class="error-message">${message}</p>
    <form action="./signup" method="post">
        Email: <input type="email" name="email" required><br>
        Password: <input type="password" name="password" required><br>
        Confirm Password: <input type="password" name="confirmPassword" required><br>
		Delivery Address: <input type="address" name="address"  placeholder="Street, City, State, Zip Code"><br>
        <button type="submit">Sign Up</button>
		<p>Already have an account? <a href="./login">Login here</a></p>
    </form>
</body>
</html>
