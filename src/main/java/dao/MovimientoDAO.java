package dao;

import model.*;
import util.DatabaseConnection;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class MovimientoDAO {
    
    public void crearMovimiento(Movimiento movimiento) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            String sql = "INSERT INTO Movimiento (cuenta_id, categoria_id, nombre, valor, " +
                        "tipo, fecha, cuenta_destino_id, descripcion) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, movimiento.getCuentaId());
                stmt.setInt(2, movimiento.getCategoria().getId());
                stmt.setString(3, movimiento.getNombre());
                stmt.setDouble(4, movimiento.getValor());
                stmt.setString(5, movimiento.getTipo().toString());
                stmt.setTimestamp(6, new Timestamp(movimiento.getFecha().getTime()));
                
                if (movimiento.getTipo() == TipoMovimiento.TRANSFERENCIA) {
                    stmt.setInt(7, movimiento.getCuentaDestinoId());
                    stmt.setString(8, movimiento.getDescripcion());
                    
                    // Crear el movimiento espejo en la cuenta destino
                    createMirrorTransferMovement(conn, movimiento);
                } else {
                    stmt.setNull(7, java.sql.Types.INTEGER);
                    stmt.setNull(8, java.sql.Types.VARCHAR);
                }
                
                stmt.executeUpdate();
            }
            
            // Actualizar balances
            actualizarBalanceCuenta(conn, movimiento);
            
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new SQLException("Error en rollback", ex);
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
    
    private void createMirrorTransferMovement(Connection conn, Movimiento movimiento) 
            throws SQLException {
        String sql = "INSERT INTO Movimiento (cuenta_id, categoria_id, nombre, valor, " +
                    "tipo, fecha, cuenta_destino_id, descripcion) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movimiento.getCuentaDestinoId());
            stmt.setInt(2, movimiento.getCategoria().getId());
            stmt.setString(3, "Transferencia desde " + movimiento.getNombre());
            stmt.setDouble(4, Math.abs(movimiento.getValor()));
            stmt.setString(5, TipoMovimiento.TRANSFERENCIA.toString());
            stmt.setTimestamp(6, new Timestamp(movimiento.getFecha().getTime()));
            stmt.setInt(7, movimiento.getCuentaId());
            stmt.setString(8, movimiento.getDescripcion());
            
            stmt.executeUpdate();
        }
    }
    
    public List<Movimiento> getMovimientosFiltrados(int cuentaId, Date fechaInicio, 
                                                   Date fechaFin, String categoria) 
            throws SQLException {
        StringBuilder sql = new StringBuilder(
            "SELECT m.*, c.nombre as categoria_nombre, cd.nombre as cuenta_destino_nombre " +
            "FROM Movimiento m " +
            "JOIN Categoria c ON m.categoria_id = c.id " +
            "LEFT JOIN Cuenta cd ON m.cuenta_destino_id = cd.id " +
            "WHERE m.cuenta_id = ?");
        
        List<Object> params = new ArrayList<>();
        params.add(cuentaId);
        
        if (fechaInicio != null) {
            sql.append(" AND m.fecha >= ?");
            params.add(fechaInicio);
        }
        if (fechaFin != null) {
            sql.append(" AND m.fecha <= ?");
            params.add(fechaFin);
        }
        if (categoria != null && !categoria.isEmpty()) {
            sql.append(" AND c.nombre = ?");
            params.add(categoria);
        }
        
        sql.append(" ORDER BY m.fecha DESC");
        
        List<Movimiento> movimientos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    movimientos.add(buildMovimientoFromResultSet(rs));
                }
            }
        }
        return movimientos;
    }
    
    private Movimiento buildMovimientoFromResultSet(ResultSet rs) throws SQLException {
        // Implementar la construcción del objeto Movimiento desde el ResultSet
        // ...
        return null; // Implementar
    }
    
    private void actualizarBalanceCuenta(Connection conn, Movimiento movimiento) 
            throws SQLException {
        String sql = "UPDATE Cuenta SET balance = balance + ?, ultimo_movimiento = ? " +
                    "WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Actualizar cuenta origen
            stmt.setDouble(1, movimiento.getTipo() == TipoMovimiento.INGRESO ? 
                            movimiento.getValor() : -movimiento.getValor());
            stmt.setTimestamp(2, new Timestamp(movimiento.getFecha().getTime()));
            stmt.setInt(3, movimiento.getCuentaId());
            stmt.executeUpdate();
            
            // Si es transferencia, actualizar cuenta destino
            if (movimiento.getTipo() == TipoMovimiento.TRANSFERENCIA) {
                stmt.setDouble(1, movimiento.getValor());
                stmt.setTimestamp(2, new Timestamp(movimiento.getFecha().getTime()));
                stmt.setInt(3, movimiento.getCuentaDestinoId());
                stmt.executeUpdate();
            }
        }
    }
}