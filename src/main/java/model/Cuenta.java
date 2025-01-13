package model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Cuenta")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "balance")
    private double balance;
    
    @OneToMany(mappedBy = "cuentaOrigen", fetch = FetchType.LAZY)
    private List<Movimiento> movimientos;

    public Cuenta() {}

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