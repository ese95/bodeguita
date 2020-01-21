package bodega.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the punto_venta database table.
 * 
 */
@Entity
@Table(name="punto_venta")
@NamedQuery(name="PuntoVenta.findAll", query="SELECT p FROM PuntoVenta p")
public class PuntoVenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PUNTO_VENTA_IDPTOVENTA_GENERATOR", sequenceName="PUNTO_VENTA_ID_PTO_VENTA_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PUNTO_VENTA_IDPTOVENTA_GENERATOR")
	@Column(name="id_pto_venta", unique=true, nullable=false)
	private Integer idPtoVenta;

	@Column(name="correo_pto_venta", length=120)
	private String correoPtoVenta;

	@Column(name="direccion_pto_venta", length=220)
	private String direccionPtoVenta;

	@Column(name="nombre_pto_venta", nullable=false, length=75)
	private String nombrePtoVenta;

	@Column(name="telefono_pto_venta", length=15)
	private String telefonoPtoVenta;

	//bi-directional many-to-one association to Bodega
	@OneToMany(mappedBy="puntoVenta")
	private List<Bodega> bodegas;

	public PuntoVenta() {
	}

	public Integer getIdPtoVenta() {
		return this.idPtoVenta;
	}

	public void setIdPtoVenta(Integer idPtoVenta) {
		this.idPtoVenta = idPtoVenta;
	}

	public String getCorreoPtoVenta() {
		return this.correoPtoVenta;
	}

	public void setCorreoPtoVenta(String correoPtoVenta) {
		this.correoPtoVenta = correoPtoVenta;
	}

	public String getDireccionPtoVenta() {
		return this.direccionPtoVenta;
	}

	public void setDireccionPtoVenta(String direccionPtoVenta) {
		this.direccionPtoVenta = direccionPtoVenta;
	}

	public String getNombrePtoVenta() {
		return this.nombrePtoVenta;
	}

	public void setNombrePtoVenta(String nombrePtoVenta) {
		this.nombrePtoVenta = nombrePtoVenta;
	}

	public String getTelefonoPtoVenta() {
		return this.telefonoPtoVenta;
	}

	public void setTelefonoPtoVenta(String telefonoPtoVenta) {
		this.telefonoPtoVenta = telefonoPtoVenta;
	}

	public List<Bodega> getBodegas() {
		return this.bodegas;
	}

	public void setBodegas(List<Bodega> bodegas) {
		this.bodegas = bodegas;
	}

	public Bodega addBodega(Bodega bodega) {
		getBodegas().add(bodega);
		bodega.setPuntoVenta(this);

		return bodega;
	}

	public Bodega removeBodega(Bodega bodega) {
		getBodegas().remove(bodega);
		bodega.setPuntoVenta(null);

		return bodega;
	}

}