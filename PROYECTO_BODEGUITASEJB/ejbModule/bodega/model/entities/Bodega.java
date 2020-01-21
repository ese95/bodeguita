package bodega.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the bodega database table.
 * 
 */
@Entity
@Table(name="bodega")
@NamedQuery(name="Bodega.findAll", query="SELECT b FROM Bodega b")
public class Bodega implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BODEGA_IDBODEGA_GENERATOR", sequenceName="BODEGA_ID_BODEGA_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BODEGA_IDBODEGA_GENERATOR")
	@Column(name="id_bodega", unique=true, nullable=false)
	private Integer idBodega;

	@Column(name="direccion_bodega", length=220)
	private String direccionBodega;

	@Column(name="nombre_bodega", nullable=false, length=50)
	private String nombreBodega;

	//bi-directional many-to-one association to PuntoVenta
	@ManyToOne
	@JoinColumn(name="id_pto_venta")
	private PuntoVenta puntoVenta;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario_usuario")
	private Usuario usuario;

	//bi-directional many-to-one association to Movimiento
	@OneToMany(mappedBy="bodega")
	private List<Movimiento> movimientos;

	public Bodega() {
	}

	public Integer getIdBodega() {
		return this.idBodega;
	}

	public void setIdBodega(Integer idBodega) {
		this.idBodega = idBodega;
	}

	public String getDireccionBodega() {
		return this.direccionBodega;
	}

	public void setDireccionBodega(String direccionBodega) {
		this.direccionBodega = direccionBodega;
	}

	public String getNombreBodega() {
		return this.nombreBodega;
	}

	public void setNombreBodega(String nombreBodega) {
		this.nombreBodega = nombreBodega;
	}

	public PuntoVenta getPuntoVenta() {
		return this.puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Movimiento> getMovimientos() {
		return this.movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	public Movimiento addMovimiento(Movimiento movimiento) {
		getMovimientos().add(movimiento);
		movimiento.setBodega(this);

		return movimiento;
	}

	public Movimiento removeMovimiento(Movimiento movimiento) {
		getMovimientos().remove(movimiento);
		movimiento.setBodega(null);

		return movimiento;
	}

}