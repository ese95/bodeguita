package bodega.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the producto database table.
 * 
 */
@Entity
@Table(name="producto")
@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PRODUCTO_IDPRODUCTO_GENERATOR", sequenceName="PRODUCTO_ID_PRODUCTO_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRODUCTO_IDPRODUCTO_GENERATOR")
	@Column(name="id_producto", unique=true, nullable=false)
	private Integer idProducto;

	@Column(name="cantidad_stock_producto", nullable=false)
	private Integer cantidadStockProducto;

	@Column(name="caracteristicas_producto", length=2147483647)
	private String caracteristicasProducto;

	@Column(name="costo_producto", nullable=false, precision=5, scale=2)
	private BigDecimal costoProducto;

	@Column(name="descripcion_producto", length=250)
	private String descripcionProducto;

	@Column(name="estado_producto", nullable=false)
	private Boolean estadoProducto;

	@Column(name="imagen_producto", length=2147483647)
	private String imagenProducto;

	@Column(name="nombre_producto", nullable=false, length=50)
	private String nombreProducto;

	@Column(name="precio_base_producto", nullable=false, precision=5, scale=2)
	private BigDecimal precioBaseProducto;

	//bi-directional many-to-one association to Movimiento
	@OneToMany(mappedBy="producto")
	private List<Movimiento> movimientos;

	//bi-directional many-to-one association to Categoria
	@ManyToOne
	@JoinColumn(name="id_categoria")
	private Categoria categoria;

	//bi-directional many-to-one association to Medida
	@ManyToOne
	@JoinColumn(name="id_medida")
	private Medida medida;

	public Producto() {
	}

	public Integer getIdProducto() {
		return this.idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Integer getCantidadStockProducto() {
		return this.cantidadStockProducto;
	}

	public void setCantidadStockProducto(Integer cantidadStockProducto) {
		this.cantidadStockProducto = cantidadStockProducto;
	}

	public String getCaracteristicasProducto() {
		return this.caracteristicasProducto;
	}

	public void setCaracteristicasProducto(String caracteristicasProducto) {
		this.caracteristicasProducto = caracteristicasProducto;
	}

	public BigDecimal getCostoProducto() {
		return this.costoProducto;
	}

	public void setCostoProducto(BigDecimal costoProducto) {
		this.costoProducto = costoProducto;
	}

	public String getDescripcionProducto() {
		return this.descripcionProducto;
	}

	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}

	public Boolean getEstadoProducto() {
		return this.estadoProducto;
	}

	public void setEstadoProducto(Boolean estadoProducto) {
		this.estadoProducto = estadoProducto;
	}

	public String getImagenProducto() {
		return this.imagenProducto;
	}

	public void setImagenProducto(String imagenProducto) {
		this.imagenProducto = imagenProducto;
	}

	public String getNombreProducto() {
		return this.nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public BigDecimal getPrecioBaseProducto() {
		return this.precioBaseProducto;
	}

	public void setPrecioBaseProducto(BigDecimal precioBaseProducto) {
		this.precioBaseProducto = precioBaseProducto;
	}

	public List<Movimiento> getMovimientos() {
		return this.movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	public Movimiento addMovimiento(Movimiento movimiento) {
		getMovimientos().add(movimiento);
		movimiento.setProducto(this);

		return movimiento;
	}

	public Movimiento removeMovimiento(Movimiento movimiento) {
		getMovimientos().remove(movimiento);
		movimiento.setProducto(null);

		return movimiento;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Medida getMedida() {
		return this.medida;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}

}