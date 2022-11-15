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
      margin-left: 11%;
    }

    input[type=submit]:hover {
      background-color: #6666ff;
    }


    #backbtn {
      background-color: #4d4dff;
      color: white;
      border: 2px solid #dddddd;
      padding: 3px;
      margin-left: 2%;
      font-size: medium;
      text-decoration: none;
    }

    #backbtn:hover {
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
    <c:when test="${Utente.admin==true}">
      <a href="<spring:url value="/utente/showusers"/>">Utenti</a>

      <a class="active" href="<spring:url value="/auto/showcars"/>">Parco auto</a>
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

<br>
<c:choose>
  <c:when test="${check!=null}">
    <h1>Modifica auto</h1>
    <br />
  </c:when>
  <c:otherwise>
    <h1>Inserisci nuova auto</h1>
    <br />
  </c:otherwise>
</c:choose>

<div id="container">
  <form:form method="post" modelAttribute="Auto">  <!--il modelAttribute deve coincidere con quello del metodo del controller-->
    <label>Targa:</label><br><br>
    <form:input id="targa" path="targa" type="text" placeholder="Targa" required="required"/><br><br>
    <label>Marca:</label><br><br>
    <form:input id="marca" path="marca" type="text" placeholder="Marca" required="required"/><br><br>
    <label>Modello:</label><br><br>
    <form:input id="modello" path="modello" type="text" placeholder="Modello" required="required"/><br><br>
    <label for="disponibile">Disponibilità: </label>
    <form:select path="disponibile">
      <form:option value="true" label="Disponibile"/>
      <form:option value="false" label="Non disponibile"/>
    </form:select>
    <br><br><br><br>

    <c:choose>
      <c:when test="${check!=null}">
        <input type="submit" id="btnMod" value = "Modifica auto"/>
        <br />
      </c:when>
      <c:otherwise>
        <input type="submit" id="btnIns" value = "Inserisci auto"/>
        <br />
      </c:otherwise>
    </c:choose>
  </form:form>
</div>

<br><br>
<a id="backbtn" href="<spring:url value="/auto/showcars"/>">Annulla</a>

</body>
</html>
