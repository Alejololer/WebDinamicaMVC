package service;

import dao.CuentaDAO;
import dto.CuentaRequestDTO;
import dto.CuentaResponseDTO;
import model.*;
import java.sql.SQLException;
import java.util.List;

public class CuentaService {
    private CuentaDAO cuentaDAO;
    private MovimientoService movimientoService;
    private CategoriaService categoriaService;
    
    public CuentaService() {
        this.cuentaDAO = new CuentaDAO();
        this.movimientoService = new MovimientoService();
        this.categoriaService = new CategoriaService();
    }
    
    public CuentaResponseDTO obtenerCuentaConMovimientos(CuentaRequestDTO requestDTO) 
            throws SQLException {
        try {
            // Obtener datos básicos
            Cuenta cuenta = getCuenta(requestDTO.getCuentaId());
            if (cuenta == null) {
                throw new SQLException("No se pudo encontrar la cuenta " + requestDTO.getCuentaId());
            }
            
            List<Categoria> categorias = categoriaService.getAllCategorias();
            List<Movimiento> todosLosMovimientos = 
                movimientoService.getAllMovimientos(requestDTO.getCuentaId());
            
            // Calcular saldo real
            double saldoReal = calcularSaldoReal(todosLosMovimientos);
            
            // Actualizar cuenta con saldo real
            cuenta.setBalance(saldoReal);
            
            // Obtener movimientos filtrados
            List<Movimiento> movimientosFiltrados = movimientoService.getMovimientosFiltrados(
                todosLosMovimientos,
                requestDTO.getFechaInicio(),
                requestDTO.getFechaFin(),
                requestDTO.getCategoria(),
                requestDTO.getTipo()
            );
            
            // Calcular totales
            MovimientosResumen resumen = calcularTotales(movimientosFiltrados);
            
            return new CuentaResponseDTO(
                cuenta,
                movimientosFiltrados,
                resumen.getTotalIngresos(),
                resumen.getTotalEgresos(),
                saldoReal,
                categorias
            );
            
        } catch (Exception e) {
            throw new SQLException("Error processing account data", e);
        }
    }
    
    private double calcularSaldoReal(List<Movimiento> movimientos) {
        return movimientos.stream()
            .mapToDouble(m -> {
                if (m.getTipo() == TipoMovimiento.INGRESO || 
                    m.getTipo() == TipoMovimiento.TRANSFERENCIA_ENTRANTE) {
                    return m.getValor();
                } else {
                    return -m.getValor();
                }
            })
            .sum();
    }
    
    private MovimientosResumen calcularTotales(List<Movimiento> movimientos) {
        double totalIngresos = 0;
        double totalEgresos = 0;
        
        for (Movimiento m : movimientos) {
            if (m.getTipo() == TipoMovimiento.INGRESO || 
                m.getTipo() == TipoMovimiento.TRANSFERENCIA_ENTRANTE) {
                totalIngresos += m.getValor();
            } else if (m.getTipo() == TipoMovimiento.EGRESO || 
                      m.getTipo() == TipoMovimiento.TRANSFERENCIA_SALIENTE) {
                totalEgresos += Math.abs(m.getValor());
            }
        }
        
        return new MovimientosResumen(totalIngresos, totalEgresos);
    }
    
    public Cuenta getCuenta(int id) throws SQLException {
        return cuentaDAO.getCuentaById(id);
    }
}