package controller;

import service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import dto.CuentaRequestDTO;
import dto.CuentaResponseDTO;
import util.Constants;

@WebServlet("/verCuenta")
public class VerCuentaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CuentaService cuentaService;
    
    @Override
    public void init() {
        cuentaService = new CuentaService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Construir DTO con los parámetros de la request
            CuentaRequestDTO requestDTO = buildRequestDTO(request);
            
            // Obtener datos procesados del service
            CuentaResponseDTO responseDTO = cuentaService.obtenerCuentaConMovimientos(requestDTO);
            
            // Establecer atributos para la vista
            setRequestAttributes(request, responseDTO);
            
            // Forward a la vista
            request.getRequestDispatcher("index.jsp").forward(request, response);
            
        } catch (SQLException e) {
            throw new ServletException("Error processing request", e);
        }
    }
    
    private CuentaRequestDTO buildRequestDTO(HttpServletRequest request) {
        return new CuentaRequestDTO(
            request.getParameter("id") != null ? 
                Integer.parseInt(request.getParameter("id")) : 
                Constants.DEMO_CUENTA_PRINCIPAL_ID,
            parseDate(request.getParameter("fechaInicio")),
            parseDate(request.getParameter("fechaFin")),
            request.getParameter("categoria"),
            request.getParameter("tipo")
        );
    }
    
    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            // Log error if needed
            return null;
        }
    }
    
    private void setRequestAttributes(HttpServletRequest request, CuentaResponseDTO dto) {
        request.setAttribute("cuenta", dto.getCuenta());
        request.setAttribute("movimientos", dto.getMovimientos());
        request.setAttribute("categorias", dto.getCategorias());
        request.setAttribute("totalIngresos", dto.getTotalIngresos());
        request.setAttribute("totalEgresos", dto.getTotalEgresos());
    }
}