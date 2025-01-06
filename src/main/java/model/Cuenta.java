package model;

import java.util.Date;
import java.util.List;

public class Cuenta {
    private int id;
    private String nombre;
    private String descripcion;
    private double balance;
    private Date ultimoMovimiento;
    private List<Movimiento> movimientos;
    private Usuario usuario;
    
    public Cuenta(String nombre, String descripcion, double balance) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.balance = balance;
    }
    
    public Cuenta(String nombre, String descripcion) {
        this(nombre, descripcion, 0.0);
    }
    
    // Getters y Setters básicos
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getBalance() { return balance; }

    public void setBalance(double balance) { 
        this.balance = balance; 
    }

    public Date getUltimoMovimiento() { return ultimoMovimiento; }

    public void setUltimoMovimiento(Date ultimoMovimiento) { this.ultimoMovimiento = ultimoMovimiento; }

    public List<Movimiento> getMovimientos() { return movimientos; }

    public void setMovimientos(List<Movimiento> movimientos) { this.movimientos = movimientos; }
	
    public Usuario getUsuario() { return usuario; }
	
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    public String getDescripcion() { return descripcion; }
    
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public double calcularBalanceReal() {
        if (movimientos == null || movimientos.isEmpty()) {
            return 0.0;
        }
        return movimientos.stream()
                .mapToDouble(m -> {
                    switch (m.getTipo()) {
                        case INGRESO:
                        case TRANSFERENCIA_ENTRANTE:
                            return m.getValor();
                        case EGRESO:
                        case TRANSFERENCIA_SALIENTE:
                            return -m.getValor();
                        default:
                            return 0.0;
                    }
                })
                .sum();
    }
    
}