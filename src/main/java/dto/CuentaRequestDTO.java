package dto;

import java.util.Date;

public class CuentaRequestDTO {
    private int cuentaId;
    private Date fechaInicio;
    private Date fechaFin;
    private String categoria;
    private String tipo;
	public CuentaRequestDTO(int cuentaId, Date fechaInicio, Date fechaFin, String categoria, String tipo) {
		super();
		this.cuentaId = cuentaId;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.categoria = categoria;
		this.tipo = tipo;
	}
	public int getCuentaId() {
		return cuentaId;
	}
	public void setCuentaId(int cuentaId) {
		this.cuentaId = cuentaId;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
    
    
}
