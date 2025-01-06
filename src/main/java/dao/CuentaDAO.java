package dao;

import model.Cuenta;
import util.DatabaseConnection;
import java.sql.*;

public class CuentaDAO {
    public Cuenta getCuentaById(int id) throws SQLException {
        String sql = "SELECT * FROM Cuenta WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cuenta cuenta = new Cuenta(
                        rs.getString("nombre")
                    );
                    cuenta.setId(rs.getInt("id"));
                    cuenta.setBalance(rs.getDouble("balance"));
                    return cuenta;
                }
            }
        }
        return null;
    }
}