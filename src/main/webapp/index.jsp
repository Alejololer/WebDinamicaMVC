<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalle Cuenta</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="icon" type="image/png" href="images/dollar.png">
    <script src="https://kit.fontawesome.com/d2aae01839.js" crossorigin="anonymous"></script>
</head>
<body>
    <header class="header bg-primary ds-flex jc-sb pd-y-16 pd-x-24 font-secondary text-white align-center">
        <div></div>
        <div onclick="location.href='logout.jsp'" style="cursor: pointer;">
            <span class="pd-r-8 font-bold font-primary">Cerrar Sesión</span>
            <i class="fa-solid fa-right-from-bracket text-white text-xl"></i>
        </div>
    </header>
    <main class="ds-flex gap-24">
        <nav class="sidemenu bg-light">
            <img src="images/wallet-512px.png" alt="wallet">
            <span class="text-dark font-primary text-center pd-b-16">Chaucherita<br>Web</span>
            <ul class="menu ls-none">
                <li>
                    <button class="menu-button" onclick="location.href='home.jsp'">
                        <i class="fa-solid fa-house text-xl"></i> Regresar
                    </button>
                </li>
            </ul>
        </nav>
        <div class="main-content pd-24">
            <h2 class="font-primary text-dark">Detalle de la Cuenta</h2>
            <section class="account-details pd-y-16 text-dark font-primary font-base mg-y-16">
                <p><strong>Nombre:</strong> <c:out value="${account.name}" /></p>
                <p><strong>Descripción:</strong> <c:out value="${account.description}" /></p>
                <p><strong>Balance Actual:</strong> <fmt:formatNumber value="${account.balance}" type="currency" /></p>
                <p><strong>Último Movimiento:</strong> <fmt:formatDate value="${account.lastMovement}" pattern="yyyy-MM-dd" /></p>
            </section>
            <section class="movements-filter pd-y-16 mg-y-16">
                <h3 class="font-primary text-dark">Filtrar Movimientos</h3>
                <form id="filter-form" class="font-primary text-dark ds-flex gap-16" action="filterMovements.jsp" method="GET">
                    <div class="form-group">
                        <label for="etiqueta">Etiqueta:</label>
                        <select id="etiqueta" name="etiqueta" class="input">
                            <option value="" selected>Todas</option>
                            <c:forEach var="tag" items="${tags}">
                                <option value="${tag}"><c:out value="${tag}" /></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="fecha-inicio">Desde:</label>
                        <input type="date" id="fecha-inicio" name="fechaInicio" class="input">
                    </div>
                    <div class="form-group">
                        <label for="fecha-fin">Hasta:</label>
                        <input type="date" id="fecha-fin" name="fechaFin" class="input">
                    </div>
                    <button type="submit" class="button bg-primary text-white">Filtrar</button>
                </form>
            </section>
            <section class="movements-list pd-y-16">
                <h3 class="font-primary text-dark">Movimientos</h3>
                <table class="table border-light font-primary text-dark text-base">
                    <thead>
                        <tr class="bg-light text-dark">
                            <th>Valor</th>
                            <th>Etiqueta</th>
                            <th>Fecha</th>
                            <th>Saldo</th>
                        </tr>
                    </thead>
                    <tbody id="movements-tbody">
                        <c:forEach var="movement" items="${movements}">
                            <tr>
                                <td class="${movement.value > 0 ? 'positive' : 'negative'}">
                                    <fmt:formatNumber value="${movement.value}" />
                                </td>
                                <td><c:out value="${movement.tag}" /></td>
                                <td><fmt:formatDate value="${movement.date}" pattern="yyyy-MM-dd" /></td>
                                <td><fmt:formatNumber value="${movement.balance}" type="currency" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </section>
        </div>
    </main>
</body>
</html>
