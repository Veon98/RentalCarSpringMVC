<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
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

        #tab {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 70%;
            margin-left: 15%;
            margin-top: 2%;
        }

        #tab td, #tab th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        #tab tr:nth-child(even){
            background-color: #f2f2f2;
        }

        #tab tr:hover {
            background-color: #ddd;
        }

        #tab th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: #4d4dff;
            color: white;
        }

        #addbtn {
            background-color: #4d4dff;
            color: white;
            border: 2px solid #dddddd;
            padding: 7px;
            margin-left: 15%;
            font-size: medium;
            text-decoration: none;
        }

        #addbtn:hover {
            background-color: #6666ff;
        }

        #backbtn {
            background-color: #4d4dff;
            color: white;
            border: 2px solid #dddddd;
            padding: 5px;
            margin-left: 2%;
            font-size: medium;
            text-decoration: none;
        }

        #backbtn:hover {
            background-color: #6666ff;
        }

        input[type=text] {
            width: 35%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-top: 6px;
            margin-bottom: 16px;
            resize: vertical;
        }

        input[type=date] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-top: 6px;
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
        }

        input[type=submit]:hover {
            background-color: #6666ff;
        }

        #updbtn {
            background-color: #4d4dff;
            color: white;
            border-radius: 0em;
            border: 2px solid lightgray;
            text-decoration: none;
        }

        #updbtn:hover {
            background-color: #6666ff;
        }

        #dltbtn {
            background-color: #e60000;
            color: white;
            border-radius: 0em;
            border: 2px solid lightgray;
            text-decoration: none;
        }

        #dltbtn:hover {
            background-color: #ff471a;
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

<br><br>
<div>

    <div>

        <form:form id="search" role="search" method="get" action="../auto/search">
            <p>Cerca per:</p>
            <input type="radio" id="tipo" name="tipo" value="targa" required>
            <label for="tipo">Targa</label>
            <input type="radio" id="tipo" name="tipo" value="marca" required>
            <label for="tipo">Marca</label>
            <input type="radio" id="tipo" name="tipo" value="modello" required>
            <label for="tipo">Modello</label>
            <input type="text" onclick="this.select();" name="filter" value="${filter}" placeholder="Cerca auto" required>
            <button type="submit">Cerca</button>
        </form:form>

        <br><br>
        <a id="addbtn" href="<spring:url value="/auto/insauto"/>">Inserisci una nuova auto</a>
        <br>

        <table id="tab">

            <tr>
                <th style="text-align: center;">Targa</th>
                <th style="text-align: center;">Marca</th>
                <th style="text-align: center;">Modello</th>
            </tr>

            <c:forEach var="tmpCar" items="${listaAuto}">  <!--jstl che scorre l'arraylist fornita dal controller (UserServlet)-->

                <tr>
                    <!--l'oggetto arriva con i campi della tabella (attributi dell'Entità User, proprietà della Classe User.java)-->
                    <td>${tmpCar.targa}</td>
                    <td>${tmpCar.marca}</td>
                    <td>${tmpCar.modello}</td>
                    <td style="text-align: center;">
                        <c:choose>
                            <c:when test="${isAdmin==true}">
                                <a id="updbtn" href="<spring:url value="/auto/modifica/${tmpCar.idAuto}"/>">Modifica</a>
                            </c:when>
                            <c:otherwise>
                                <a id="updbtn" href="<spring:url value="/prenotazione/insprenotazione/${tmpCar.idAuto}"/>">Noleggia</a>
                            </c:otherwise>
                        </c:choose>
                        |
                        <a id="dltbtn" href="<spring:url value="/auto/elimina/${tmpCar.idAuto}" />" onclick="if (!(confirm('Vuoi davvero eliminare questa auto?'))) return false">Elimina</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <br><br><br>
        <a id="backbtn" href="<spring:url value="/profilo"/>">Indietro</a>

    </div>

</div>

</body>
</html>

