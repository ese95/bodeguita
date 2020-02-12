package bodega.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usuario_rol database table.
 * 
 */
@Entity
@Table(name="usuario_rol")
@NamedQuery(name="UsuarioRol.findAll", query="SELECT u FROM UsuarioRol u")
public class UsuarioRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USUARIO_ROL_IDUSUARIOROL_GENERATOR", sequenceName="USUARIO_ROL_ID_USUARIO_ROL_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_ROL_IDUSUARIOROL_GENERATOR")
	@Column(name="id_usuario_rol", unique=true, nullable=false)
	private Integer idUsuarioRol;

	//bi-directional many-to-one association to PuntoVenta
	@OneToMany(mappedBy="usuarioRol")
	private List<PuntoVenta> puntoVentas;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="id_rol")
	private Rol rol;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	public UsuarioRol() {
	}

	public Integer getIdUsuarioRol() {
		return this.idUsuarioRol;
	}

	public void setIdUsuarioRol(Integer idUsuarioRol) {
		this.idUsuarioRol = idUsuarioRol;
	}

	public List<PuntoVenta> getPuntoVentas() {
		return this.puntoVentas;
	}

	public void setPuntoVentas(List<PuntoVenta> puntoVentas) {
		this.puntoVentas = puntoVentas;
	}

	public PuntoVenta addPuntoVenta(PuntoVenta puntoVenta) {
		getPuntoVentas().add(puntoVenta);
		puntoVenta.setUsuarioRol(this);

		return puntoVenta;
	}

	public PuntoVenta removePuntoVenta(PuntoVenta puntoVenta) {
		getPuntoVentas().remove(puntoVenta);
		puntoVenta.setUsuarioRol(null);

		return puntoVenta;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}