package bodega.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the movimiento database table.
 * 
 */
@Entity
@Table(name="movimiento")
@NamedQuery(name="Movimiento.findAll", query="SELECT m FROM Movimiento m")
public class Movimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MOVIMIENTO_IDMOVIM_GENERATOR", sequenceName="MOVIMIENTO_ID_MOVIM_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MOVIMIENTO_IDMOVIM_GENERATOR")
	@Column(name="id_movim", unique=true, nullable=false)
	private Integer idMovim;

	@Column(name="cantidad_movim", nullable=false)
	private Integer cantidadMovim;

	@Column(length=80)
	private String comentario;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_movim", nullable=false)
	private Date fechaMovim;

	@Column(name="orden_compra_o_factura_movim", length=10)
	private String ordenCompraOFacturaMovim;

	//bi-directional many-to-one association to Bodega
	@ManyToOne
	@JoinColumn(name="id_bodega")
	private Bodega bodega;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="id_producto")
	private Producto producto;

	public Movimiento() {
	}

	public Integer getIdMovim() {
		return this.idMovim;
	}

	public void setIdMovim(Integer idMovim) {
		this.idMovim = idMovim;
	}

	public Integer getCantidadMovim() {
		return this.cantidadMovim;
	}

	public void setCantidadMovim(Integer cantidadMovim) {
		this.cantidadMovim = cantidadMovim;
	}

	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getFechaMovim() {
		return this.fechaMovim;
	}

	public void setFechaMovim(Date fechaMovim) {
		this.fechaMovim = fechaMovim;
	}

	public String getOrdenCompraOFacturaMovim() {
		return this.ordenCompraOFacturaMovim;
	}

	public void setOrdenCompraOFacturaMovim(String ordenCompraOFacturaMovim) {
		this.ordenCompraOFacturaMovim = ordenCompraOFacturaMovim;
	}

	public Bodega getBodega() {
		return this.bodega;
	}

	public void setBodega(Bodega bodega) {
		this.bodega = bodega;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}