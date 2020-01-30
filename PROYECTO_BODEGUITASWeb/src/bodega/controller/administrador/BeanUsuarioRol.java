package bodega.controller.administrador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bodega.model.administrador.ManagerUsuario;
import bodega.model.administrador.ManagerUsuarioRol;
import bodega.model.administrador.ManagerRol;

import bodega.model.entities.UsuarioRol;
import bodega.model.entities.Rol;
import bodega.model.entities.Usuario;

@Named
@SessionScoped
public class BeanUsuarioRol implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UsuarioRol> listaUsuarioRol;
	private List<Rol> listaRol;
	private List<Usuario> listaUsuario;
	@EJB
	private ManagerUsuarioRol managerUsuarioRol;
	
	@EJB
	private ManagerRol managerRol;
	
	@EJB
	private ManagerUsuario managerUser;
	
	private UsuarioRol uRol;
	private Usuario user;
	private Rol rol;
	private Integer idUser;
	private Integer idRol;

	@PostConstruct
	public void inicializar() {
		try {
			listaRol=managerRol.findAllRol();
			listaUsuarioRol=managerUsuarioRol.findAllUsuarioRol();
			listaUsuario=managerUser.findAllUsuarios();
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeError(e.getMessage());
		}
		uRol=new UsuarioRol();
	
	}

	public void actionListenerCargarUsuarioRol(UsuarioRol ur) {
		try {
			uRol=new UsuarioRol();
			Usuario user=managerUser.findByIdUsuario(ur.getUsuario().getIdUsuario());
			Rol r=managerRol.findByIdRol(ur.getRol().getIdRol());	
			uRol.setIdUsuarioRol(ur.getUsuario().getIdUsuario());
			idUser=ur.getUsuario().getIdUsuario();
			idRol=ur.getRol().getIdRol();
			uRol.setRol(r);
			uRol.setUsuario(user);	
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeError("No se ha podido cargar su usuario rol");
		}
	}

	public void actionListenerActualizarUsuarioRol() {
		try {
			
				
				UsuarioRol ur=managerUsuarioRol.findByIdUsuarioRol(uRol.getIdUsuarioRol());
				ur.setRol(managerRol.findByIdRol(idRol));
				ur.setUsuario(managerUser.findByIdUsuario(idUser));
				managerUsuarioRol.actualizarUsuarioRol(ur);
				listaUsuarioRol=managerUsuarioRol.findAllUsuarioRol();
				JSFUtil.crearMensajeInfo("Actualizado con éxito");
				
			

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al actualizar "+e.getMessage());
		}

	}

	public void limpiarUsuarioRol() {
		uRol.setRol(null);
		uRol.setUsuario(null);
	}

	public void actionListenerInsertarUsuario() {
			try {
			//	if ( uRol.ge) {
					
					UsuarioRol ur=new UsuarioRol();
					
					ur.setRol(managerRol.findByIdRol(idRol));
					ur.setUsuario(managerUser.findByIdUsuario(idUser));
					managerUsuarioRol.insertarUsuarioRol(ur);
					
					listaUsuarioRol=managerUsuarioRol.findAllUsuarioRol();
					limpiarUsuarioRol();
					JSFUtil.crearMensajeInfo("Insertado con éxito");
					
			/*	} else {
					JSFUtil.crearMensajeError("Debe ingresar todos los campos");
				}
*/
			} catch (Exception e) {
				e.printStackTrace();	
				JSFUtil.crearMensajeError("Error al crear "+e.getMessage());
			}

	}

	public void actionListenerEliminarUsuarioRol(Integer id) {
		try {
			
			managerUsuarioRol.eliminarUsuarioRol(id);
			listaUsuarioRol=managerUsuarioRol.findAllUsuarioRol();
			JSFUtil.crearMensajeInfo("Su 'USUARIO - ROL' ha sido eliminado");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al eliminar ");
		}
	}

	public List<UsuarioRol> getListaUsuarioRol() {
		return listaUsuarioRol;
	}

	public void setListaUsuarioRol(List<UsuarioRol> listaUsuarioRol) {
		this.listaUsuarioRol = listaUsuarioRol;
	}

	public List<Rol> getListaRol() {
		return listaRol;
	}

	public void setListaRol(List<Rol> listaRol) {
		this.listaRol = listaRol;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public UsuarioRol getuRol() {
		return uRol;
	}

	public void setuRol(UsuarioRol uRol) {
		this.uRol = uRol;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	
	
}
