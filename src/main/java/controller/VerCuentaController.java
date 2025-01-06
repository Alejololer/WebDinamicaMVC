package controller;

import model.*;
import service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import util.Constants;

@WebServlet("/verCuenta")
public class VerCuentaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CuentaService cuentaService;
    private MovimientoService movimientoService;
    private CategoriaService categoriaService;
    
    @Override
    public void init() {
        cuentaService = new CuentaService();
        movimientoService = new MovimientoService();
        categoriaService = new CategoriaService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int cuentaId = request.getParameter("id") != null ? 
                          Integer.parseInt(request.getParameter("id")) : 
                          Constants.DEMO_CUENTA_PRINCIPAL_ID;
            
            // Cargar datos básicos
            Cuenta cuenta = cuentaService.getCuenta(cuentaId);
            List<Categoria> categorias = categoriaService.getAllCategorias();
            List<Movimiento> todosLosMovimientos = movimientoService.getAllMovimientos(cuentaId);
            
            // Parámetros de filtrado
            String fechaInicioStr = request.getParameter("fechaInicio");
            String fechaFinStr = request.getParameter("fechaFin");
            String categoria = request.getParameter("categoria");
            String tipo = request.getParameter("tipo");
            
            // Calcular saldo real con todos los movimientos
            double saldoReal = todosLosMovimientos.stream()
                .mapToDouble(m -> {
                    if (m.getTipo() == TipoMovimiento.INGRESO || 
                        m.getTipo() == TipoMovimiento.TRANSFERENCIA_ENTRANTE) {
                        return m.getValor();
                    } else {
                        return -m.getValor();
                    }
                })
                .sum();
            
            // Actualizar el balance real de la cuenta
            cuenta.setBalance(saldoReal);
            cuenta.setUltimoMovimiento(todosLosMovimientos.getLast().getFecha());
            
            // Aplicar filtros si existen
            List<Movimiento> movimientosFiltrados = todosLosMovimientos;
            if (fechaInicioStr != null || fechaFinStr != null || categoria != null || tipo != null) {
                Date fechaInicio = null;
                Date fechaFin = null;
                
                if (fechaInicioStr != null && !fechaInicioStr.isEmpty()) {
                    fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicioStr);
                }
                if (fechaFinStr != null && !fechaFinStr.isEmpty()) {
                    fechaFin = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFinStr);
                }
                
                movimientosFiltrados = movimientoService.getMovimientosFiltrados(
                    todosLosMovimientos, fechaInicio, fechaFin, categoria, tipo);
            }
            
            // Calcular totales solo de los movimientos filtrados
            double totalIngresos = 0;
            double totalEgresos = 0;
            
            for (Movimiento m : movimientosFiltrados) {
                if (m.getTipo() == TipoMovimiento.INGRESO || 
                    m.getTipo() == TipoMovimiento.TRANSFERENCIA_ENTRANTE) {
                    totalIngresos += m.getValor();
                } else if (m.getTipo() == TipoMovimiento.EGRESO || 
                         m.getTipo() == TipoMovimiento.TRANSFERENCIA_SALIENTE) {
                    totalEgresos += Math.abs(m.getValor());
                }
            }
            
            // Actualizar los movimientos filtrados en la cuenta
            cuenta.setMovimientos(movimientosFiltrados);
            
            // Establecer atributos para la vista
            request.setAttribute("cuenta", cuenta);
            request.setAttribute("movimientos", movimientosFiltrados);
            request.setAttribute("categorias", categorias);
            request.setAttribute("totalIngresos", totalIngresos);
            request.setAttribute("totalEgresos", totalEgresos);
            
            request.getRequestDispatcher("index.jsp").forward(request, response);
            
        } catch (SQLException | ParseException e) {
            throw new ServletException("Error processing request", e);
        }
    }
}
