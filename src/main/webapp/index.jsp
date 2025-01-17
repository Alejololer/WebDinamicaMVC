<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    // Verificar si es la primera vez que se abre la sesión
    if (session.getAttribute("initialized") == null) {
        // Marcar como inicializado
        session.setAttribute("initialized", true);
        // Redirigir al servlet
        response.sendRedirect(request.getContextPath() + "/verCuenta");
    }
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalle Cuenta</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="icon" type="image/png" href="images/dollar.png">
    <script src="https://kit.fontawesome.com/d2aae01839.js"></script>
</head>

<body>
    <header class="header bg-primary ds-flex jc-sb pd-y-16 pd-x-24 font-secondary text-white align-center">
        <div class="text-xl font-bold">
            <span>Chaucherita Web</span>
        </div>
        <div onclick="location.href='logout.jsp'" style="cursor: pointer;">
            <span class="pd-r-8 font-bold font-primary">Cerrar Sesión</span>
            <i class="fa-solid fa-right-from-bracket text-white text-xl"></i>
        </div>
    </header>

    <main class="ds-flex gap-24">
        <nav class="sidemenu bg-light">
            <img src="images/wallet-512px.png" alt="wallet" class="pb-md">
            <ul class="menu ls-none">
                <li>
                    <button class="menu-button">
                        <i class="fa-solid fa-house"></i> Inicio
                    </button>
                </li>
                <li>
                    <button class="menu-button" onclick="history.back()">
                        <i class="fa-solid fa-arrow-left"></i> Regresar
                    </button>
                </li>
            </ul>
        </nav>

        <div class="main-content">
            <h2 class="font-primary text-dark text-2xl pb-lg">Detalle de la Cuenta</h2>

            <section class="account-details">
                <div class="ds-flex gap-16 font-primary text-dark">
                    <div>
                        <p class="text-lg font-bold">
                            <c:out value="${cuenta.nombre}" />
                        </p>
                    </div>
                    <div class="ds-flex gap-24">
                        <div>
                            <p class="text-sm">Balance Actual</p>
                            <p class="text-xl font-bold ${cuenta.balance >= 0 ? 'text-positive' : 'text-negative'}">
                                <fmt:formatNumber value="${cuenta.balance}" type="currency" />
                            </p>
                        </div>
                    </div>
                </div>
            </section>

            <section class="movimientos-filter mt-lg">
                <h3 class="font-primary text-dark pb-md">Filtrar Movimientos</h3>
                <form id="filter-form" action="verCuenta" method="GET" class="ds-flex gap-16 flex-wrap">
                    <input type="hidden" name="id" value="${cuenta.id}">

                    <div class="form-group">
                        <label for="tipo" class="font-primary text-dark">Tipo</label>
                        <select id="tipo" name="tipo" class="input">
                            <option value="">Todos</option>
                            <option value="INGRESO" ${param.tipo=='INGRESO' ? 'selected' : '' }>Ingresos</option>
                            <option value="EGRESO" ${param.tipo=='EGRESO' ? 'selected' : '' }>Egresos</option>
                            <option value="TRANSFERENCIA" ${param.tipo=='TRANSFERENCIA' ? 'selected' : '' }>
                                Transferencias</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="categoria" class="font-primary text-dark">Categoría</label>
                        <select id="categoria" name="categoria" class="input">
                            <option value="">Todas</option>
                            <c:forEach var="categoria" items="${categorias}">
                                <option value="${categoria.id}" ${param.categoria==categoria.id ? 'selected' : '' }>
                                    ${categoria.nombre}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="fecha-inicio" class="font-primary text-dark">Desde</label>
                        <input type="date" id="fecha-inicio" name="fechaInicio" class="input"
                            value="${param.fechaInicio}">
                    </div>

                    <div class="form-group">
                        <label for="fecha-fin" class="font-primary text-dark">Hasta</label>
                        <input type="date" id="fecha-fin" name="fechaFin" class="input" value="${param.fechaFin}">
                    </div>

                    <div class="form-group" style="justify-content: flex-end;">
                        <button type="submit" class="button bg-primary text-white">
                            <i class="fa-solid fa-filter"></i> Filtrar
                        </button>
                    </div>
                </form>
            </section>

            <section class="mt-lg">
                <h3 class="font-primary text-dark pb-md">Movimientos</h3>
                <div class="table-container">
                    <table class="table">
                        <thead>
                            <tr class="bg-light text-dark">
                                <th>Fecha</th>
                                <th>Concepto</th>
                                <th>Categoría</th>
                                <th>Tipo</th>
                                <th class="text-right">Valor</th>
                                <th class="text-right">Saldo</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="movimiento" items="${movimientos}">
                                <tr class="border-b-light">
                                    <td>
                                        <fmt:formatDate value="${movimiento.fecha}" pattern="dd/MM/yyyy" />
                                    </td>
                                    <td>
                                        <c:out value="${movimiento.concepto}" />
                                    </td>
                                    <td>
                                        <c:out value="${movimiento.categoria.nombre}" />
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${movimiento.tipo == 'TRANSFERENCIA_SALIENTE'}">
                                                <span class="text-negative">
                                                    <i class="fa-solid fa-arrow-right"></i> Transferencia a
                                                    ${movimiento.cuentaDestino.nombre}
                                                </span>
                                            </c:when>
                                            <c:when test="${movimiento.tipo == 'TRANSFERENCIA_ENTRANTE'}">
                                                <span class="text-positive">
                                                    <i class="fa-solid fa-arrow-left"></i> Transferencia desde
                                                    ${movimiento.cuentaOrigen.nombre}
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                ${movimiento.tipo}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td
                                        class="text-right ${movimiento.tipo == 'EGRESO' || movimiento.tipo == 'TRANSFERENCIA_SALIENTE' ? 'text-negative' : 'text-positive'}">
                                        <fmt:formatNumber value="${movimiento.valor}" type="currency" />
                                    </td>
                                    <td
                                        class="text-right ${movimiento.saldoDespues >= 0 ? 'text-positive' : 'text-negative'}">
                                        <fmt:formatNumber value="${movimiento.saldoDespues}" type="currency" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </section>

            <section class="mt-lg">
                <h3 class="font-primary text-dark pb-md">Resumen</h3>
                <div class="ds-flex gap-24">
                    <div class="card flex-1">
                        <p class="text-sm text-dark">Total Ingresos</p>
                        <p class="text-xl font-bold text-positive">
                            <fmt:formatNumber value="${totalIngresos}" type="currency" />
                        </p>
                    </div>
                    <div class="card flex-1">
                        <p class="text-sm text-dark">Total Egresos</p>
                        <p class="text-xl font-bold text-negative">
                            <fmt:formatNumber value="${totalEgresos}" type="currency" />
                        </p>
                    </div>
                    <div class="card flex-1">
                        <p class="text-sm text-dark">Balance del Período</p>
                        <p
                            class="text-xl font-bold ${totalIngresos - totalEgresos >= 0 ? 'text-positive' : 'text-negative'}">
                            <fmt:formatNumber value="${totalIngresos - totalEgresos}" type="currency" />
                        </p>
                    </div>
                </div>
            </section>
        </div>
    </main>
</body>

</html>