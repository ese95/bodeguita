package bodega.controller.admin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import bodega.model.admin.ManagerUsuario;
import bodega.model.admin.ManagerUsuarioRol;
import bodega.controller.login.BeanLogin;
import bodega.model.admin.ManagerBitacora;
import bodega.model.admin.ManagerRol;

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
	private List<Rol> listaRolADM;
	private List<UsuarioRol> listaUsuarioRolADM;
	private List<Usuario> listaUsuario;
	@EJB
	private ManagerUsuarioRol managerUsuarioRol;
	
	@EJB
	private ManagerBitacora managerbit;

	@EJB
	private ManagerRol managerRol;

	@EJB
	private ManagerUsuario managerUser;

	@Inject
	BeanLogin login;

	private UsuarioRol uRol;
	private Usuario user;
	private Rol rol;
	private Integer idUser;
	private Integer idRol;
	private Integer idUsuarioRol;

	@PostConstruct
	public void inicializar() {
		try {
			listaRol = managerRol.findAllRol();
			listaRolADM = managerRol.findAllRolADM();
			listaUsuarioRolADM = managerUsuarioRol.findAllUsuarioRolADM();
			listaUsuarioRol = managerUsuarioRol.findAllUsuarioRol();
			listaUsuario = managerUser.findAllUsuarios();
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeError(e.getMessage());
		}
		uRol = new UsuarioRol();

	}

	public void actionListenerCargarUsuarioRol(UsuarioRol ur) {
		try {
			uRol = new UsuarioRol();
			Usuario user = managerUser.findByIdUsuario(ur.getUsuario().getIdUsuario());
			Rol r = managerRol.findByIdRol(ur.getRol().getIdRol());
			uRol.setIdUsuarioRol(ur.getUsuario().getIdUsuario());
			// uRol.se

			idUsuarioRol = ur.getIdUsuarioRol();
			idUser = ur.getUsuario().getIdUsuario();
			idRol = ur.getRol().getIdRol();
			uRol.setRol(r);
			uRol.setUsuario(user);
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeError("No se ha podido cargar su usuario rol");
		}
	}

	public void actionListenerCargarUsuarioRolADM(UsuarioRol ur) {
		try {
			uRol = new UsuarioRol();
			Usuario user = managerUser.findByIdUsuario(ur.getUsuario().getIdUsuario());
			Rol r = managerRol.findByIdRol(ur.getRol().getIdRol());
			idUsuarioRol = ur.getIdUsuarioRol();
			uRol.setIdUsuarioRol(ur.getUsuario().getIdUsuario());
			idUser = ur.getUsuario().getIdUsuario();
			idRol = ur.getRol().getIdRol();

			uRol.setRol(r);
			uRol.setUsuario(user);
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeError("No se ha podido cargar su usuario rol");
		}
	}

	public void actionListenerActualizarUsuarioRol() {
		try {
			if (idUsuarioRol.equals(login.getLoginDTO().getIdRolUsuario())) {
				JSFUtil.crearMensajeError("No debe editar el usuario en el que está iniciado sesión");
			} else {
				boolean validar = false;
				int cont = 0;
				int cont1 = 0;
				
				UsuarioRol ur = managerUsuarioRol.findByIdUsuarioRol(idUsuarioRol);

				ur.setRol(managerRol.findByIdRol(idRol));
				ur.setUsuario(managerUser.findByIdUsuario(idUser));
				
				
				
				for (UsuarioRol u : listaUsuarioRol) {
					if (u.getRol().getIdRol().equals(ur.getRol().getIdRol())
							&& u.getUsuario().getIdUsuario().equals(ur.getUsuario().getIdUsuario())) {
						validar = false;
						cont++;

					} else {
						validar = true;
						cont1++;
					}
				}
				System.out.println("cont " + cont);
				System.out.println("cont2 " + cont1);
				if (validar && cont == 0) {
				managerUsuarioRol.actualizarUsuarioRol(ur);
				listaUsuarioRol = managerUsuarioRol.findAllUsuarioRol();
				JSFUtil.crearMensajeInfo("Actualizado con éxito");
				managerbit.crearEvento("actionListenerActualizarUsuarioRol()", "Actualiza un rol de usuario ");
				} else {
					JSFUtil.crearMensajeError("No se ha podido actualizar debido ha que ya existe");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al actualizar " + e.getMessage());
		}

	}

	public void actionListenerActualizarUsuarioRolADM() {
		try {

			if (idUsuarioRol.equals(login.getLoginDTO().getIdRolUsuario())) {
				JSFUtil.crearMensajeError("No debe editar el usuario en el que está iniciado sesión");
			} else {
				
				boolean validar = false;
				int cont = 0;
				int cont1 = 0;
				
				UsuarioRol ur = managerUsuarioRol.findByIdUsuarioRol(idUsuarioRol);
				Rol r = managerRol.findByIdRol(idRol);
				System.out.println("r " + r.getTipoRol());
				ur.setRol(r);
				System.out.println("rol xx" + ur.getRol().getTipoRol());
				ur.setUsuario(managerUser.findByIdUsuario(idUser));
				System.out.println("ur " + ur.getUsuario().getApellidoUsuario());
				
				
				for (UsuarioRol u : listaUsuarioRolADM) {
					if (u.getRol().getIdRol().equals(ur.getRol().getIdRol())
							&& u.getUsuario().getIdUsuario().equals(ur.getUsuario().getIdUsuario())) {
						validar = false;
						cont++;

					} else {
						validar = true;
						cont1++;
					}
				}
				System.out.println("cont " + cont);
				System.out.println("cont2 " + cont1);
				
				
				if (validar && cont == 0) {
					managerUsuarioRol.actualizarUsuarioRol(ur);
					listaUsuarioRolADM = managerUsuarioRol.findAllUsuarioRolADM();
					JSFUtil.crearMensajeInfo("Actualizado con éxito");
					managerbit.crearEvento("actionListenerActualizarUsuarioRolADM()", "Actualiza un rol de usuario del admin");
					
					} else {
						JSFUtil.crearMensajeError("No se ha podido actualizar debido ha que ya existe");
					}
	
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al actualizar " + e.getMessage());
		}

	}

	public void limpiarUsuarioRol() {
		uRol.setRol(null);
		uRol.setUsuario(null);
	}


	public void actionListenerInsertarUsuario() {
		try {
			// if ( uRol.ge) {
			boolean validar = false;
			int cont = 0;
			int cont1 = 0;
			UsuarioRol ur = new UsuarioRol();

			ur.setRol(managerRol.findByIdRol(idRol));
			ur.setUsuario(managerUser.findByIdUsuario(idUser));
			for (UsuarioRol u : listaUsuarioRol) {
				if (u.getRol().getIdRol().equals(ur.getRol().getIdRol())
						&& u.getUsuario().getIdUsuario().equals(ur.getUsuario().getIdUsuario())) {

					validar = false;
					cont++;

				} else {
					validar = true;
					cont1++;
				}

			}
			System.out.println("cont " + cont);
			System.out.println("cont2 " + cont1);
			if (validar && cont == 0) {
				managerUsuarioRol.insertarUsuarioRol(ur);

				listaUsuarioRol = managerUsuarioRol.findAllUsuarioRol();
				limpiarUsuarioRol();
				JSFUtil.crearMensajeInfo("Insertado con éxito");
				managerbit.crearEvento("actionListenerInsertarUsuario()", "Inserta un rol de usuario ");
				
			} else {
				JSFUtil.crearMensajeError("No se ha podido insertar debido ha que ya existe");
			}

			/*
			 * } else { JSFUtil.crearMensajeError("Debe ingresar todos los campos"); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al crear " + e.getMessage());
		}

	}

	public void actionListenerInsertarUsuarioADM() {
		try {
			// if ( uRol.ge) {
			boolean validar = false;
			int cont = 0;
			int cont1 = 0;
			UsuarioRol ur = new UsuarioRol();

			ur.setRol(managerRol.findByIdRol(idRol));
			ur.setUsuario(managerUser.findByIdUsuario(idUser));

			for (UsuarioRol u : listaUsuarioRolADM) {
				if (u.getRol().getIdRol().equals(ur.getRol().getIdRol())
						&& u.getUsuario().getIdUsuario().equals(ur.getUsuario().getIdUsuario())) {
					validar = false;
					cont++;
				} else {
					validar = true;
					cont1++;
				}

			}
			System.out.println("cont " + cont);
			System.out.println("cont2 " + cont1);
			
			if (validar && cont == 0) {
				managerUsuarioRol.insertarUsuarioRol(ur);

				listaUsuarioRolADM = managerUsuarioRol.findAllUsuarioRolADM();
				limpiarUsuarioRol();
				JSFUtil.crearMensajeInfo("Insertado con éxito");
			} else {
				JSFUtil.crearMensajeError("No se ha podido insertar debido ha que ya existe");
			}

			/*
			 * } else { JSFUtil.crearMensajeError("Debe ingresar todos los campos"); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al crear " + e.getMessage());
		}

	}

	public void actionListenerEliminarUsuarioRol(Integer id) {
		try {
			if (id.equals(login.getLoginDTO().getIdRolUsuario())) {
				JSFUtil.crearMensajeError("No debe eliminar el usuario en el que está iniciado sesión");
			} else {
				managerUsuarioRol.eliminarUsuarioRol(id);
				listaUsuarioRol = managerUsuarioRol.findAllUsuarioRol();
				JSFUtil.crearMensajeInfo("Su 'USUARIO - ROL' ha sido eliminado");
				managerbit.crearEvento("actionListenerEliminarUsuarioRol()", "Elimina un rol de usuario ");
				
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al eliminar ");
		}
	}

	public void actionListenerEliminarUsuarioRolADM(Integer id) {

		try {
			if (id.equals(login.getLoginDTO().getIdRolUsuario())) {
				JSFUtil.crearMensajeError("No debe eliminar el usuario en el que está iniciado sesión");
			} else {
				managerUsuarioRol.eliminarUsuarioRol(id);
				listaUsuarioRolADM = managerUsuarioRol.findAllUsuarioRolADM();
				JSFUtil.crearMensajeInfo("Su 'USUARIO - ROL' ha sido eliminado");
				managerbit.crearEvento("actionListenerEliminarUsuarioRolADM()", "Elimina un rol de usuario DEL admin ");
				
			}

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

	public List<UsuarioRol> getListaUsuarioRolADM() {
		return listaUsuarioRolADM;
	}

	public void setListaUsuarioRolADM(List<UsuarioRol> listaUsuarioRolADM) {
		this.listaUsuarioRolADM = listaUsuarioRolADM;
	}

	public List<Rol> getListaRolADM() {
		return listaRolADM;
	}

	public void setListaRolADM(List<Rol> listaRolADM) {
		this.listaRolADM = listaRolADM;
	}

	// admin

}
