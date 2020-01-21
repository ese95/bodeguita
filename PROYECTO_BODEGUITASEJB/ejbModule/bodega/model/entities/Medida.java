package bodega.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the medida database table.
 * 
 */
@Entity
@Table(name="medida")
@NamedQuery(name="Medida.findAll", query="SELECT m FROM Medida m")
public class Medida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MEDIDA_IDMEDIDA_GENERATOR", sequenceName="MEDIDA_ID_MEDIDA_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MEDIDA_IDMEDIDA_GENERATOR")
	@Column(name="id_medida", unique=true, nullable=false)
	private Integer idMedida;

	@Column(name="tipo_medida", nullable=false, length=50)
	private String tipoMedida;

	//bi-directional many-to-one association to Producto
	@OneToMany(mappedBy="medida")
	private List<Producto> productos;

	public Medida() {
	}

	public Integer getIdMedida() {
		return this.idMedida;
	}

	public void setIdMedida(Integer idMedida) {
		this.idMedida = idMedida;
	}

	public String getTipoMedida() {
		return this.tipoMedida;
	}

	public void setTipoMedida(String tipoMedida) {
		this.tipoMedida = tipoMedida;
	}

	public List<Producto> getProductos() {
		return this.productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public Producto addProducto(Producto producto) {
		getProductos().add(producto);
		producto.setMedida(this);

		return producto;
	}

	public Producto removeProducto(Producto producto) {
		getProductos().remove(producto);
		producto.setMedida(null);

		return producto;
	}

}