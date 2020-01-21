package bodega.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USUARIO_IDUSUARIO_GENERATOR", sequenceName="USUARIO_ID_USUARIO_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_IDUSUARIO_GENERATOR")
	@Column(name="id_usuario", unique=true, nullable=false)
	private Integer idUsuario;

	@Column(name="apellido_usuario", nullable=false, length=60)
	private String apellidoUsuario;

	@Column(name="cedula_usuario", nullable=false, length=13)
	private String cedulaUsuario;

	@Column(name="clave_usuario", nullable=false, length=30)
	private String claveUsuario;

	@Column(name="correo_usuario", nullable=false, length=120)
	private String correoUsuario;

	@Column(name="direccion_usuario", length=220)
	private String direccionUsuario;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_nac_usuario")
	private Date fechaNacUsuario;

	@Column(name="imagen_usuario", length=2147483647)
	private String imagenUsuario;

	@Column(name="nombre_usuario", nullable=false, length=60)
	private String nombreUsuario;

	@Column(name="telefono_usuario", length=15)
	private String telefonoUsuario;

	//bi-directional many-to-one association to Bodega
	@OneToMany(mappedBy="usuario")
	private List<Bodega> bodegas;

	//bi-directional many-to-one association to Genero
	@ManyToOne
	@JoinColumn(name="id_genero")
	private Genero genero;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="id_rol")
	private Rol rol;

	public Usuario() {
	}

	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getApellidoUsuario() {
		return this.apellidoUsuario;
	}

	public void setApellidoUsuario(String apellidoUsuario) {
		this.apellidoUsuario = apellidoUsuario;
	}

	public String getCedulaUsuario() {
		return this.cedulaUsuario;
	}

	public void setCedulaUsuario(String cedulaUsuario) {
		this.cedulaUsuario = cedulaUsuario;
	}

	public String getClaveUsuario() {
		return this.claveUsuario;
	}

	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	public String getCorreoUsuario() {
		return this.correoUsuario;
	}

	public void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}

	public String getDireccionUsuario() {
		return this.direccionUsuario;
	}

	public void setDireccionUsuario(String direccionUsuario) {
		this.direccionUsuario = direccionUsuario;
	}

	public Date getFechaNacUsuario() {
		return this.fechaNacUsuario;
	}

	public void setFechaNacUsuario(Date fechaNacUsuario) {
		this.fechaNacUsuario = fechaNacUsuario;
	}

	public String getImagenUsuario() {
		return this.imagenUsuario;
	}

	public void setImagenUsuario(String imagenUsuario) {
		this.imagenUsuario = imagenUsuario;
	}

	public String getNombreUsuario() {
		return this.nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getTelefonoUsuario() {
		return this.telefonoUsuario;
	}

	public void setTelefonoUsuario(String telefonoUsuario) {
		this.telefonoUsuario = telefonoUsuario;
	}

	public List<Bodega> getBodegas() {
		return this.bodegas;
	}

	public void setBodegas(List<Bodega> bodegas) {
		this.bodegas = bodegas;
	}

	public Bodega addBodega(Bodega bodega) {
		getBodegas().add(bodega);
		bodega.setUsuario(this);

		return bodega;
	}

	public Bodega removeBodega(Bodega bodega) {
		getBodegas().remove(bodega);
		bodega.setUsuario(null);

		return bodega;
	}

	public Genero getGenero() {
		return this.genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

}