package bodega.model.dto;

import java.util.List;

import bodega.model.entities.UsuarioRol;

/**
 * DTO para el acceso al sistema.
 * @author mrea
 *
 */
public class LoginDTO {
	private String usuario;
	private String codigoCorreo;
	private List<UsuarioRol> tipoUsuario;
	private String rutaAcceso;
	private int idUsuario;
	private int idRolUsuario;
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getCodigoCorreo() {
		return codigoCorreo;
	}
	public void setCodigoCorreo(String codigoCorreo) {
		this.codigoCorreo = codigoCorreo;
	}

	public List<UsuarioRol> getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(List<UsuarioRol> tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	public String getRutaAcceso() {
		return rutaAcceso;
	}
	public void setRutaAcceso(String rutaAcceso) {
		this.rutaAcceso = rutaAcceso;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdRolUsuario() {
		return idRolUsuario;
	}
	public void setIdRolUsuario(int idRolUsuario) {
		this.idRolUsuario = idRolUsuario;
	}
	
}
