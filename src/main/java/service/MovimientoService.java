package service;

import dao.MovimientoDAO;
import model.Movimiento;
import model.TipoMovimiento;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MovimientoService {
    private MovimientoDAO movimientoDAO;
    
    public MovimientoService() {
        this.movimientoDAO = new MovimientoDAO();
    }
    
    public List<Movimiento> getMovimientosFiltrados(List<Movimiento> movimientos, Date fechaInicio, 
                                                   Date fechaFin, String categoria, String tipo) {
        return movimientos.stream()
            .filter(mov -> {
                boolean cumpleFiltros = true;
                
                if (fechaInicio != null) {
                    cumpleFiltros &= !mov.getFecha().before(fechaInicio);
                }
                if (fechaFin != null) {
                    cumpleFiltros &= !mov.getFecha().after(fechaFin);
                }
                if (categoria != null && !categoria.isEmpty()) {
                    cumpleFiltros &= mov.getCategoria().getId() == Integer.parseInt(categoria);
                }
                if (tipo != null && !tipo.isEmpty()) {
                    if (tipo.equals("TRANSFERENCIA_ENTRANTE")) {
                        cumpleFiltros &= mov.getTipo() == TipoMovimiento.TRANSFERENCIA_ENTRANTE;
                    } else if (tipo.equals("TRANSFERENCIA_SALIENTE")) {
                        cumpleFiltros &= mov.getTipo() == TipoMovimiento.TRANSFERENCIA_SALIENTE;
                    } else {
                        cumpleFiltros &= mov.getTipo() == TipoMovimiento.valueOf(tipo);
                    }
                }
                
                return cumpleFiltros;
            })
            .collect(Collectors.toList());
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