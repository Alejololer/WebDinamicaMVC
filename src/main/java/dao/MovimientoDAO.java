package dao;

import model.*;
import util.DatabaseConnection;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class MovimientoDAO {

    public List<Movimiento> getAllMovimientos(int cuentaId) throws SQLException {
        String sql = "SELECT m.*, c.nombre as categoria_nombre, c.id as categoria_id, " +
                    "co.id as cuenta_origen_id, co.nombre as cuenta_origen_nombre, " +
                    "cd.id as cuenta_destino_id, cd.nombre as cuenta_destino_nombre, " +
                    "(SELECT SUM(CASE " +
                    "    WHEN tipo = 'INGRESO' OR (tipo = 'TRANSFERENCIA' AND cuenta_destino_id = m.cuenta_id) THEN valor " +
                    "    ELSE -valor END) " +
                    "FROM Movimiento m2 " +
                    "WHERE m2.cuenta_id = m.cuenta_id AND m2.fecha <= m.fecha) as saldo_despues " +
                    "FROM Movimiento m " +
                    "LEFT JOIN Categoria c ON m.categoria_id = c.id " +
                    "LEFT JOIN Cuenta co ON m.cuenta_id = co.id " +
                    "LEFT JOIN Cuenta cd ON m.cuenta_destino_id = cd.id " +
                    "WHERE m.cuenta_id = ? " +
                    "ORDER BY m.fecha ASC";

        List<Movimiento> movimientos = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cuentaId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    movimientos.add(crearMovimientoDesdeResultSet(rs, cuentaId));
                }
            }
        }
        return movimientos;
    }

    private Movimiento crearMovimientoDesdeResultSet(ResultSet rs, int cuentaConsultada) throws SQLException {
        int id = rs.getInt("id");
        String concepto = rs.getString("concepto");
        Categoria categoria = new Categoria(
            rs.getInt("categoria_id"),
            rs.getString("categoria_nombre")
        );
        Date fecha = rs.getDate("fecha");
        double valor = rs.getDouble("valor");
        String tipoBaseDatos = rs.getString("tipo");
        int cuentaId = rs.getInt("cuenta_id");
        double saldoDespues = rs.getDouble("saldo_despues");
        
        // Determinar el tipo de movimiento basado en la cuenta consultada
        TipoMovimiento tipo;
        if ("TRANSFERENCIA".equals(tipoBaseDatos)) {
            Integer cuentaDestinoId = rs.getObject("cuenta_destino_id", Integer.class);
            // Si la cuenta consultada es la cuenta destino, es una transferencia entrante
            if (cuentaDestinoId != null && cuentaDestinoId == cuentaConsultada) {
                tipo = TipoMovimiento.TRANSFERENCIA_ENTRANTE;
            } else {
                tipo = TipoMovimiento.TRANSFERENCIA_SALIENTE;
            }
        } else {
            tipo = TipoMovimiento.valueOf(tipoBaseDatos);
        }
        
        if (tipo == TipoMovimiento.TRANSFERENCIA_SALIENTE || 
            tipo == TipoMovimiento.TRANSFERENCIA_ENTRANTE) {
            
            Cuenta cuentaOrigen = new Cuenta(
                rs.getString("cuenta_origen_nombre")
            );
            cuentaOrigen.setId(rs.getInt("cuenta_origen_id"));
            
            Cuenta cuentaDestino = null;
            Integer cuentaDestinoId = rs.getObject("cuenta_destino_id", Integer.class);
            
            if (cuentaDestinoId != null) {
                cuentaDestino = new Cuenta(
                    rs.getString("cuenta_destino_nombre")
                );
                cuentaDestino.setId(cuentaDestinoId);
            }
            
            return new Movimiento(id, concepto, categoria, fecha, valor, tipo, 
                                cuentaId, cuentaDestinoId,
                                cuentaOrigen, cuentaDestino, saldoDespues);
        } else {
            return new Movimiento(id, concepto, categoria, fecha, valor, tipo,
                                cuentaId, saldoDespues);
        }
    }
}