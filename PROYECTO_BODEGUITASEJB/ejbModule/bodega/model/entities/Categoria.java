package bodega.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the categoria database table.
 * 
 */
@Entity
@Table(name="categoria")
@NamedQuery(name="Categoria.findAll", query="SELECT c FROM Categoria c")
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CATEGORIA_IDCATEGORIA_GENERATOR", sequenceName="CATEGORIA_ID_CATEGORIA_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CATEGORIA_IDCATEGORIA_GENERATOR")
	@Column(name="id_categoria", unique=true, nullable=false)
	private Integer idCategoria;

	@Column(name="estado_categoria", nullable=false)
	private Boolean estadoCategoria;

	@Column(name="nombre_categoria", nullable=false, length=45)
	private String nombreCategoria;

	//bi-directional many-to-one association to Producto
	@OneToMany(mappedBy="categoria")
	private List<Producto> productos;

	public Categoria() {
	}

	public Integer getIdCategoria() {
		return this.idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Boolean getEstadoCategoria() {
		return this.estadoCategoria;
	}

	public void setEstadoCategoria(Boolean estadoCategoria) {
		this.estadoCategoria = estadoCategoria;
	}

	public String getNombreCategoria() {
		return this.nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public List<Producto> getProductos() {
		return this.productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public Producto addProducto(Producto producto) {
		getProductos().add(producto);
		producto.setCategoria(this);

		return producto;
	}

	public Producto removeProducto(Producto producto) {
		getProductos().remove(producto);
		producto.setCategoria(null);

		return producto;
	}

}