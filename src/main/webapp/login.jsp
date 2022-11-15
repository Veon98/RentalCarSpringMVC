<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>RentalCar</title>

    <style>
        .topnav {
            overflow: hidden;
            background-color: #333;
        }

        .topnav a {
            float: left;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-size: 17px;
        }

        .topnav a:hover {
            background-color: #ddd;
            color: black;
        }

        .topnav a.active {
            background-color: #4d4dff;
            color: white;
        }

        #container {
            border-radius: 5px;
            background-color: #f2f2f2;
            padding: 20px;
            margin-top: 0%;
            width: 35%;
        }

        input[type=text], input[type=password] {
            width: 45%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-top: 0px;
            margin-bottom: 16px;
            resize: vertical;
        }

        input[type=submit] {
            background-color: #4d4dff;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-left: 16%;
        }

        input[type=submit]:hover {
            background-color: #6666ff;
        }
    </style>
</head>
<body>

<div class="topnav">
    <a href="<spring:url value="/index"/>">Homepage</a>

    <a href="<spring:url value="/profilo"/>">Profilo</a>

    <a href="<spring:url value="/prenotazione/showprens"/>">Prenotazioni</a>

    <c:choose>
        <c:when test="${Utente=='admin'}">
            <a href="<spring:url value="/utente/showusers"/>">Utenti</a>

            <a href="<spring:url value="/auto/showcars"/>">Parco auto</a>
        </c:when>
    </c:choose>

    <c:choose>
        <c:when test="${Utente==null}">
            <a class="active" style="margin-left: 70%;" href="<c:url value='/login/form' />">Login</a>
        </c:when>
        <c:otherwise>
            <a style="margin-left: 65%;" href="<c:url value='/logout' />">Logout</a>
        </c:otherwise>
    </c:choose>
</div>
<br>

    <h1>Login</h1>

    <div id="container">
        <c:url var="loginUrl" value="/login"/>   <!--invia le credenziali al securityconfig dal quale parte l'operazione di autenticazione-->
        <form action="${loginUrl}" method="post">   <!--login è stato specificato come url nel file di configurazione-->
            <label>Codice Fiscale: </label><br><br>
            <input type="text" id="username" name="username" placeholder="Codice Fiscale" required><br><br>
            <label>Password: </label><br><br>
            <input type="password" id="password" name="password" placeholder="Password" required><br><br>

            <input type="submit" value="Login">
        </form>
    </div>

</body>
</html>
