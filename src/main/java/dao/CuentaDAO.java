package dao;

import model.Cuenta;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAO {
    public Cuenta getCuentaById(int id) throws SQLException {
        String sql = "SELECT * FROM Cuenta WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cuenta cuenta = new Cuenta();
                    cuenta.setId(rs.getInt("id"));
                    cuenta.setNombre(rs.getString("nombre"));
                    cuenta.setDescripcion(rs.getString("descripcion"));
                    cuenta.setBalance(rs.getDouble("balance"));
                    cuenta.setUltimoMovimiento(rs.getDate("ultimo_movimiento"));
                    return cuenta;
                }
            }
        }
        return null;
    }
}