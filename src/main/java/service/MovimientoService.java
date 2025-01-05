package service;

import dao.MovimientoDAO;
import model.Movimiento;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class MovimientoService {
    private MovimientoDAO movimientoDAO;
    
    public MovimientoService() {
        this.movimientoDAO = new MovimientoDAO();
    }
    
    public List<Movimiento> getMovimientosFiltrados(int cuentaId, Date fechaInicio, 
                                                   Date fechaFin, String categoria) throws SQLException {
        return movimientoDAO.getMovimientosFiltrados(cuentaId, fechaInicio, fechaFin, categoria);
    }
}