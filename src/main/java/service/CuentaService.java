package service;

import java.sql.SQLException;

import dao.CuentaDAO;
import model.Cuenta;

public class CuentaService {
    private CuentaDAO cuentaDAO;
    
    public CuentaService() {
        this.cuentaDAO = new CuentaDAO();
    }
    
    public Cuenta getCuenta(int id) throws SQLException {
        return cuentaDAO.getCuentaById(id);
    }
}
