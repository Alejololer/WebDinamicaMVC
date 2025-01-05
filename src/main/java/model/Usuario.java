package model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private List<Cuenta> cuentas;
    
    public Usuario(int id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.cuentas = new ArrayList<>();
    }
    
    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public List<Cuenta> getCuentas() { return cuentas; }
    
    // Métodos para manejar cuentas
    public void addCuenta(Cuenta cuenta) {
        this.cuentas.add(cuenta);
    }
    
    public void removeCuenta(Cuenta cuenta) {
        this.cuentas.remove(cuenta);
    }
    
    public int getNumCuentas() {
        return this.cuentas.size();
    }
}
