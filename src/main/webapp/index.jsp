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

        input[type=date] {
            width: 20%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-top: 6px;
            margin-bottom: 16px;
            resize: vertical;
            display: inline;
        }

        input[type=submit] {
            background-color: #4d4dff;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button[type=submit] {
            background-color: #4d4dff;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button[type=submit]:hover {
            background-color: #6666ff;
        }
    </style>
</head>
<body>

      <div class="topnav">
          <a class="active" href="<spring:url value="/index"/>">Homepage</a>

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
                  <a style="margin-left: 70%;" href="<c:url value='/login/form' />">Login</a>
              </c:when>
              <c:otherwise>
                  <a style="margin-left: 65%;" href="<c:url value='/logout' />">Logout</a>
              </c:otherwise>
          </c:choose>
      </div>


      <h1>${titolo}</h1>   <!--viene preso da IndexController-->

      <br><br>

      <h2>Cerca auto da noleggiare</h2>
      <form:form id="disponibili" role="disponibili" method="get" action="auto/disponibili">
          <label>Data inizio noleggio: </label>
          <input type="date" id="dataInizio" name="dataInizio">
          <label> | Data fine noleggio: </label>
          <input type="date" id="dataFine" name="dataFine">
          <label> | </label>
          <button type="submit">Cerca</button>
      </form:form>



      <footer style="margin-top: 31%;">
          <p>Powered by Andrea Doddis<br>
              <a href="mailto:andrea.doddis98@gmail.com">andrea.doddis98@gmail.com</a></p>
      </footer>

</body>
</html>
