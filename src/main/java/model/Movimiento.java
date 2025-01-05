package model;

import java.util.Date;

public class Movimiento {
    private int id;
    private String nombre;
    private Categoria categoria;
    private Date fecha;
    private double valor;
    private TipoMovimiento tipo;
    private int cuentaId;
    private Integer cuentaDestinoId; // Solo para transferencias
    private String descripcion;
    
    // Constructor para movimientos normales
    public Movimiento(String nombre, Categoria categoria, double valor, 
                     TipoMovimiento tipo, int cuentaId) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.valor = valor;
        this.tipo = tipo;
        this.cuentaId = cuentaId;
        this.fecha = new Date();
    }
    
    // Constructor para transferencias
    public Movimiento(String nombre, Categoria categoria, double valor, 
                     int cuentaOrigenId, int cuentaDestinoId, String descripcion) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.valor = valor;
        this.tipo = TipoMovimiento.TRANSFERENCIA;
        this.cuentaId = cuentaOrigenId;
        this.cuentaDestinoId = cuentaDestinoId;
        this.descripcion = descripcion;
        this.fecha = new Date();
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public TipoMovimiento getTipo() {
		return tipo;
	}

	public void setTipo(TipoMovimiento tipo) {
		this.tipo = tipo;
	}

	public int getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(int cuentaId) {
		this.cuentaId = cuentaId;
	}

	public Integer getCuentaDestinoId() {
		return cuentaDestinoId;
	}

	public void setCuentaDestinoId(Integer cuentaDestinoId) {
		this.cuentaDestinoId = cuentaDestinoId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
    
    
}