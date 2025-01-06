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
    
    public List<Movimiento> getMovimientosFiltrados(List<Movimiento> movimientos, 
                                                   Date fechaInicio, 
                                                   Date fechaFin, 
                                                   String categoria, 
                                                   String tipo) {
        return movimientos.stream()
            .filter(mov -> cumpleFiltros(mov, fechaInicio, fechaFin, categoria, tipo))
            .collect(Collectors.toList());
    }
    
    private boolean cumpleFiltros(Movimiento mov, Date fechaInicio, Date fechaFin, 
                                String categoria, String tipo) {
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
            cumpleFiltros &= validarTipoMovimiento(mov, tipo);
        }
        
        return cumpleFiltros;
    }
    
    private boolean validarTipoMovimiento(Movimiento mov, String tipo) {
        return switch (tipo) {
            case "TRANSFERENCIA" -> mov.getTipo() == TipoMovimiento.TRANSFERENCIA_ENTRANTE || mov.getTipo() == TipoMovimiento.TRANSFERENCIA_SALIENTE;
            default -> mov.getTipo() == TipoMovimiento.valueOf(tipo);
        };
    }
    
    public List<Movimiento> getAllMovimientos(int cuentaId) throws SQLException {
        List<Movimiento> movimientos = movimientoDAO.getAllMovimientos(cuentaId);
        return procesarTransferencias(movimientos);
    }
    
    private List<Movimiento> procesarTransferencias(List<Movimiento> movimientos) {
        movimientos.forEach(mov -> {
            if (mov.getTipo() == TipoMovimiento.TRANSFERENCIA_SALIENTE || 
                mov.getTipo() == TipoMovimiento.TRANSFERENCIA_ENTRANTE) {
                
                mov.setCuentaRelacionadaId(
                    mov.getTipo() == TipoMovimiento.TRANSFERENCIA_SALIENTE ? 
                    mov.getCuentaDestinoId() : mov.getCuentaId()
                );
            }
        });
        return movimientos;
    }
}