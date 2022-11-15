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

    input[type=submit] {
      background-color: #4d4dff;
      color: white;
      padding: 16px 26px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      margin-left: 6%;
    }

    #backbtn {
      background-color: #4d4dff;
      color: white;
      border: 2px solid #dddddd;
      padding: 3px;
      margin-left: 0%;
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

  <a class="active" href="<spring:url value="/prenotazione/showprens"/>">Prenotazioni</a>

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


<c:choose>
  <c:when test="${check!=null}">
    <h1>Approva prenotazione</h1>
    <br />
  </c:when>
  <c:otherwise>
    <h1>Effettua prenotazione</h1>
  </c:otherwise>
</c:choose>

<h3>Riepilogo:</h3>
<p>Utente: ${Utente.nome} ${Utente.cognome} - Auto: ${Auto.marca} ${Auto.modello}</p>
<p>Data Inizio: ${PrenotazioneDto.dataInizio} - Data Fine: ${PrenotazioneDto.dataFine}</p>


<c:url value="/prenotazione/insprenotazione" var="subLink">  <!--serve a ripulire l'url per questioni di sicurezza-->
</c:url>

<form:form action="${subLink}" method="post" modelAttribute="PrenotazioneDto">  <!--il modelAttribute deve coincidere con quello del metodo del controller-->
  <form:input path="dataInizio" value="${PrenotazioneDto.dataInizio}" type="hidden"/>
  <form:input path="dataFine" value="${PrenotazioneDto.dataFine}" type="hidden"/>
  <form:input path="dataPren" value="${PrenotazioneDto.dataPren}" type="hidden"/>
  <form:input path="idUtente" value="${PrenotazioneDto.idUtente}" type="hidden"/>
  <form:input path="idAuto" value="${PrenotazioneDto.idAuto}" type="hidden"/>
  <c:choose>
    <c:when test="${check!=null}">
      <label for="approvata">Approvazione</label>
      <form:select path="approvata">
        <form:option value="true" label="Approvata"/>
        <form:option selected="selected" value="false" label="Non approvata"/>
      </form:select>
      <br />
    </c:when>
    <c:otherwise>
      <form:input id="approvata" path="approvata" type="hidden" value="false" required="required"/>
      <br />
    </c:otherwise>
  </c:choose>

  <br>

  <c:choose>
    <c:when test="${check!=null}">
      <input type="submit" id="btnUpd" value = "Approva"/>
      <br />
    </c:when>
    <c:otherwise>
      <input type="submit" id="btnIns" value = "Noleggia"/>
      <br>
    </c:otherwise>
  </c:choose>
</form:form>

<br><br><br>
<c:url var="reloadLink" value="/auto/disponibili">  <!--specifica il path dell'url-->
  <c:param name="dataInizio" value="${PrenotazioneDto.dataInizio}" />
  <c:param name="dataFine" value="${PrenotazioneDto.dataFine}" />
</c:url>

<c:choose>
  <c:when test="${UtenteLog.admin==true}">
    <a id="backbtn" href="../../prenotazione/showprens">Annulla</a>
  </c:when>
  <c:otherwise>
    <a id="backbtn" href="${reloadLink}">Annulla</a>
  </c:otherwise>
</c:choose>



</body>
</html>

