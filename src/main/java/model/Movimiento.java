package model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Movimiento")
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "concepto")
    private String concepto;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    @Column(name = "valor")
    private double valor;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoMovimiento tipo;
    
    @Column(name = "cuenta_id")
    private int cuentaId;
    
    @Column(name = "cuenta_destino_id")
    private Integer cuentaDestinoId;
    
    @ManyToOne
    @JoinColumn(name = "cuenta_id", insertable = false, updatable = false)
    private Cuenta cuentaOrigen;
    
    @ManyToOne
    @JoinColumn(name = "cuenta_destino_id", insertable = false, updatable = false)
    private Cuenta cuentaDestino;
    
    @Transient // This field is calculated
    private double saldoDespues;

	public Movimiento() {}

	// Constructor para movimientos normales
	public Movimiento(int id, String nombre, Categoria categoria, Date fecha, double valor, TipoMovimiento tipo,
			int cuentaId, double saldoDespues) {
		this.id = id;
		this.concepto = nombre;
		this.categoria = categoria;
		this.fecha = fecha;
		this.valor = valor;
		this.tipo = tipo;
		this.cuentaId = cuentaId;
		this.cuentaDestinoId = null;
		this.cuentaOrigen = null;
		this.cuentaDestino = null;
		this.saldoDespues = saldoDespues;
	}

	// Constructor para transferencias
	public Movimiento(int id, String nombre, Categoria categoria, Date fecha, double valor, TipoMovimiento tipo,
			int cuentaId, Integer cuentaDestinoId, Cuenta cuentaOrigen, Cuenta cuentaDestino,
			double saldoDespues) {
		this.id = id;
		this.concepto = nombre;
		this.categoria = categoria;
		this.fecha = fecha;
		this.valor = valor;
		this.tipo = tipo;
		this.cuentaId = cuentaId;
		this.cuentaDestinoId = cuentaDestinoId;
		this.cuentaOrigen = cuentaOrigen;
		this.cuentaDestino = cuentaDestino;
		this.saldoDespues = saldoDespues;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String nombre) {
		this.concepto = nombre;
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
	
	public Cuenta getCuentaOrigen() {
		return cuentaOrigen;
	}

	public void setCuentaOrigen(Cuenta cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}

	public Cuenta getCuentaDestino() {
		return cuentaDestino;
	}

	public void setCuentaDestino(Cuenta cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}

	public double getSaldoDespues() {
		return saldoDespues;
	}

	public void setSaldoDespues(double saldoDespues) {
		this.saldoDespues = saldoDespues;
	}
}