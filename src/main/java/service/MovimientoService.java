package service;

import dao.MovimientoDAO;
import model.Movimiento;
import model.TipoMovimiento;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class MovimientoService {
    private MovimientoDAO movimientoDAO;
    
    public MovimientoService() {
        this.movimientoDAO = new MovimientoDAO();
    }
    
    public List<Movimiento> getMovimientosFiltrados(int cuentaId, Date fechaInicio, 
                                                   Date fechaFin, String categoria, String tipo) throws SQLException {
        return movimientoDAO.getMovimientosFiltrados(cuentaId, fechaInicio, fechaFin, categoria, tipo);
    }
    
    public List<Movimiento> getAllMovimientos(int cuentaId) throws SQLException {
        List<Movimiento> movimientos = movimientoDAO.getAllMovimientos(cuentaId);
        
        // Procesar transferencias
        for (Movimiento mov : movimientos) {
            if (mov.getTipo() == TipoMovimiento.TRANSFERENCIA_SALIENTE || 
                mov.getTipo() == TipoMovimiento.TRANSFERENCIA_ENTRANTE) {
                
                mov.setCuentaRelacionadaId(
                    mov.getTipo() == TipoMovimiento.TRANSFERENCIA_SALIENTE ? 
                    mov.getCuentaDestinoId() : mov.getCuentaId()
                );
            }
        }
        
        return movimientos;
    }
}