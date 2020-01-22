package bodega.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the bitacora database table.
 * 
 */
@Entity
@Table(name="bitacora")
@NamedQuery(name="Bitacora.findAll", query="SELECT b FROM Bitacora b")
public class Bitacora implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BITACORA_IDBITACORA_GENERATOR", sequenceName="BITACORA_ID_BITACORA_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BITACORA_IDBITACORA_GENERATOR")
	@Column(name="id_bitacora", unique=true, nullable=false)
	private Integer idBitacora;

	@Column(nullable=false, length=90)
	private String accion;

	@Column(length=90)
	private String descripcion;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fecha;

	@Column(nullable=false, length=20)
	private String ip;

	public Bitacora() {
	}

	public Integer getIdBitacora() {
		return this.idBitacora;
	}

	public void setIdBitacora(Integer idBitacora) {
		this.idBitacora = idBitacora;
	}

	public String getAccion() {
		return this.accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}