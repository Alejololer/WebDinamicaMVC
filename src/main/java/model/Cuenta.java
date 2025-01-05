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
    
    public Cuenta() {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.balance = balance;
		this.ultimoMovimiento = ultimoMovimiento;
		this.movimientos = movimientos;
	}
    
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public double getBalance() {
		return balance;
	}



	public void setBalance(double balance) {
		this.balance = balance;
	}



	public Date getUltimoMovimiento() {
		return ultimoMovimiento;
	}



	public void setUltimoMovimiento(Date ultimoMovimiento) {
		this.ultimoMovimiento = ultimoMovimiento;
	}



	public List<Movimiento> getMovimientos() {
		return movimientos;
	}



	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}
	
	public double calcularBalance() {
        return movimientos.stream()
                .mapToDouble(m -> m.getTipo() == TipoMovimiento.INGRESO ? 
                           m.getValor() : -m.getValor())
                .sum();
    }
}