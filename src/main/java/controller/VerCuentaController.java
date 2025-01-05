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

@WebServlet("/verCuenta")
public class VerCuentaController extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CuentaService cuentaService;
    private MovimientoService movimientoService;
    
    @Override
    public void init() {
        cuentaService = new CuentaService();
        movimientoService = new MovimientoService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int cuentaId = Integer.parseInt(request.getParameter("id"));
            Cuenta cuenta = cuentaService.getCuenta(cuentaId);
            
            // Parámetros de filtrado
            String fechaInicioStr = request.getParameter("fechaInicio");
            String fechaFinStr = request.getParameter("fechaFin");
            String categoria = request.getParameter("etiqueta");
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaInicio = fechaInicioStr != null ? sdf.parse(fechaInicioStr) : null;
            Date fechaFin = fechaFinStr != null ? sdf.parse(fechaFinStr) : null;
            
            List<Movimiento> movimientos = movimientoService.getMovimientosFiltrados(
                cuentaId, fechaInicio, fechaFin, categoria);
            
            request.setAttribute("account", cuenta);
            request.setAttribute("movements", movimientos);
            request.getRequestDispatcher("/WEB-INF/views/verCuenta.jsp").forward(request, response);
            
        } catch (SQLException | ParseException e) {
            throw new ServletException("Error processing request", e);
        }
    }
}
