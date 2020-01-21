package bodega.controller.admin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bodega.model.admin.ManagerRol;
import bodega.model.entities.Rol;

@Named
@SessionScoped
public class BeanRol implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Rol> listaRol;
	@EJB
	private ManagerRol managerRol;
	private Rol rol;

	@PostConstruct
	public void inicializar() {
		try {
			listaRol = managerRol.findAllRol();
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}

		rol= new Rol();
	}

	public void actionListenerCargarRol(Rol r) {
		try {
			rol = new Rol();
			rol.setIdRol(r.getIdRol());
			rol.setTipoRol(r.getTipoRol());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionListenerActualizarRol() {
		try {
			if (rol.getTipoRol().length() > 0) {
				Rol r = managerRol.findByIdRol(rol.getIdRol());
				r.setTipoRol(rol.getTipoRol());
	
				managerRol.actualizarRol(r);
				listaRol=managerRol.findAllRol();
				
				JSFUtil.crearMensajeInfo("Actualizado con éxito");
			} else {
				JSFUtil.crearMensajeError("Debe ingresar el tipo de rol");
			}
		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al actualizar");
		}
	}

	public void limpiarRol() {
		rol.setTipoRol("");;
	}

	public void actionListenerInsertarRol() {
		try {
			if (rol.getTipoRol().length() > 0) {
				Rol r=new Rol();
				r.setTipoRol(rol.getTipoRol());
				managerRol.insertarRol(r);
				listaRol=managerRol.findAllRol();
				limpiarRol();
				JSFUtil.crearMensajeInfo("Insertado con éxito");
			} else {
				JSFUtil.crearMensajeError("Debe ingresar todos los campos");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al crear");
		}
	}

	public void actionListenerEliminarRol(Integer id) {
		try {
			managerRol.eliminarRol(id);
			listaRol=managerRol.findAllRol();
			JSFUtil.crearMensajeInfo("Su género ha sido eliminado");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al eliminar");
		}

	}

	public List<Rol> getListaRol() {
		return listaRol;
	}

	public void setListaRol(List<Rol> listaRol) {
		this.listaRol = listaRol;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	

}
