package bodega.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_documento database table.
 * 
 */
@Entity
@Table(name="tipo_documento")
@NamedQuery(name="TipoDocumento.findAll", query="SELECT t FROM TipoDocumento t")
public class TipoDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_DOCUMENTO_IDDOCUMENTO_GENERATOR", sequenceName="TIPO_DOCUMENTO_ID_DOCUMENTO_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPO_DOCUMENTO_IDDOCUMENTO_GENERATOR")
	@Column(name="id_documento", unique=true, nullable=false)
	private Integer idDocumento;

	@Column(name="tipo_documento", nullable=false, length=25)
	private String tipoDocumento;

	//bi-directional many-to-one association to Movimiento
	@OneToMany(mappedBy="tipoDocumento")
	private List<Movimiento> movimientos;

	public TipoDocumento() {
	}

	public Integer getIdDocumento() {
		return this.idDocumento;
	}

	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getTipoDocumento() {
		return this.tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<Movimiento> getMovimientos() {
		return this.movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	public Movimiento addMovimiento(Movimiento movimiento) {
		getMovimientos().add(movimiento);
		movimiento.setTipoDocumento(this);

		return movimiento;
	}

	public Movimiento removeMovimiento(Movimiento movimiento) {
		getMovimientos().remove(movimiento);
		movimiento.setTipoDocumento(null);

		return movimiento;
	}

}