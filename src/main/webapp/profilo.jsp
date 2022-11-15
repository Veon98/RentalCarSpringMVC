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

    #modbtn {
      background-color: #4d4dff;
      color: white;
      border: 2px solid #dddddd;
      width: 90px;
      height: 30px;
      margin-left: 0%;
      font-size: medium;
      text-decoration: none;
      padding: 5px;
    }

    #modbtn:hover {
      background-color: #6666ff;
    }
  </style>
</head>
<body>

<div class="topnav">
  <a href="<spring:url value="/index"/>">Homepage</a>

  <a class="active" href="<spring:url value="/profilo"/>">Profilo</a>

  <a href="<spring:url value="/prenotazione/showprens"/>">Prenotazioni</a>

  <c:choose>
    <c:when test="${Utente.admin==true}">
      <a href="<spring:url value="/utente/showusers"/>">Utenti</a>

      <a href="<spring:url value="/auto/showcars"/>">Parco auto</a>
    </c:when>
  </c:choose>

  <c:choose>
    <c:when test="${Utente==null}">
      <a style="margin-left: 70%;" href="<c:url value='/login/form' />">Login</a>
    </c:when>
    <c:otherwise>
      <a style="margin-left: 65%;" href="<c:url value='/logout' />">Logout</a>
    </c:otherwise>
  </c:choose>
</div>


<h1>Benvenuto nel tuo profilo ${Utente.nome}</h1>

<br>
<c:choose>
  <c:when test="${Utente.admin==true}">
    <a id="modbtn" href="<spring:url value="/utente/showusers"/>">Visualizza gli utenti</a>
    <br><br><br>
    <a id="modbtn" href="<spring:url value="/prenotazione/showprens"/>">Visualizza le prenotazioni</a>
    <br><br><br>
    <a id="modbtn" href="<spring:url value="/auto/showcars"/>">Visualizza il parco auto</a>
  </c:when>
  <c:otherwise>
    <a id="modbtn" href="<spring:url value="/utente/modifica/${Utente.idUtente}"/>">Modifica i tuoi dati</a>
    <br><br><br>
    <a id="modbtn" href="<spring:url value="/prenotazione/showprens"/>">Visualizza le tue prenotazioni</a>
  </c:otherwise>
</c:choose>


</body>
</html>
