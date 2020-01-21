package bodega.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the genero database table.
 * 
 */
@Entity
@Table(name="genero")
@NamedQuery(name="Genero.findAll", query="SELECT g FROM Genero g")
public class Genero implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GENERO_IDGENERO_GENERATOR", sequenceName="GENERO_ID_GENERO_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GENERO_IDGENERO_GENERATOR")
	@Column(name="id_genero", unique=true, nullable=false)
	private Integer idGenero;

	@Column(name="tipo_genero", nullable=false, length=30)
	private String tipoGenero;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="genero")
	private List<Usuario> usuarios;

	public Genero() {
	}

	public Integer getIdGenero() {
		return this.idGenero;
	}

	public void setIdGenero(Integer idGenero) {
		this.idGenero = idGenero;
	}

	public String getTipoGenero() {
		return this.tipoGenero;
	}

	public void setTipoGenero(String tipoGenero) {
		this.tipoGenero = tipoGenero;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setGenero(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setGenero(null);

		return usuario;
	}

}