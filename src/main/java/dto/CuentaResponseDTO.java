package dto;

import java.util.List;

import model.Categoria;
import model.Cuenta;
import model.Movimiento;

public class CuentaResponseDTO {
    private Cuenta cuenta;
    private List<Movimiento> movimientos;
    private double totalIngresos;
    private double totalEgresos;
    private double saldoReal;
    private List<Categoria> categorias;
	public CuentaResponseDTO(Cuenta cuenta, List<Movimiento> movimientos, double totalIngresos, double totalEgresos,
			double saldoReal, List<Categoria> categorias) {
		super();
		this.cuenta = cuenta;
		this.movimientos = movimientos;
		this.totalIngresos = totalIngresos;
		this.totalEgresos = totalEgresos;
		this.saldoReal = saldoReal;
		this.categorias = categorias;
	}
	public Cuenta getCuenta() {
		return cuenta;
	}
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	public List<Movimiento> getMovimientos() {
		return movimientos;
	}
	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}
	public double getTotalIngresos() {
		return totalIngresos;
	}
	public void setTotalIngresos(double totalIngresos) {
		this.totalIngresos = totalIngresos;
	}
	public double getTotalEgresos() {
		return totalEgresos;
	}
	public void setTotalEgresos(double totalEgresos) {
		this.totalEgresos = totalEgresos;
	}
	public double getSaldoReal() {
		return saldoReal;
	}
	public void setSaldoReal(double saldoReal) {
		this.saldoReal = saldoReal;
	}
	public List<Categoria> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
    
    
}
