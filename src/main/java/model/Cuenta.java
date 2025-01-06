package model;

import java.util.List;

public class Cuenta {
	private int id;
	private String nombre;
	private double balance;
	private List<Movimiento> movimientos;

	public Cuenta(String nombre, double balance) {
		this.nombre = nombre;
		this.balance = balance;
	}

	public Cuenta(String nombre) {
		this(nombre, 0.0);
	}

	// Getters y Setters básicos
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

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public List<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

}