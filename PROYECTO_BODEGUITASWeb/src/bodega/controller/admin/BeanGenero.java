package bodega.controller.admin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bodega.model.admin.ManagerGenero;
import bodega.model.entities.Genero;

@Named
@SessionScoped
public class BeanGenero implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Genero> listaGenero;
	@EJB
	private ManagerGenero managerGenero;
	private Genero genero;

	@PostConstruct
	public void inicializar() {
		try {
			listaGenero = managerGenero.findAllGeneros();
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}

		genero = new Genero();
	}

	public void actionListenerCargarGenero(Genero gen) {
		try {
			genero = new Genero();
			genero.setIdGenero(gen.getIdGenero());
			genero.setTipoGenero(gen.getTipoGenero());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionListenerActualizarGenero() {
		try {
			if (genero.getTipoGenero().length() > 0) {
				Genero g = managerGenero.findByIdGeneros(genero.getIdGenero());
				g.setTipoGenero(genero.getTipoGenero());
				
				managerGenero.actualizarGenero(g);
				listaGenero = managerGenero.findAllGeneros();
				JSFUtil.crearMensajeInfo("Actualizado con éxito");
			} else {
				JSFUtil.crearMensajeError("Debe ingresar el tipo de género");
			}

		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al actualizar");
		}

	}

	public void limpiarGenero() {
		genero.setTipoGenero("");
	}

	public void actionListenerInsertarGenero() {
		try {
			if (genero.getTipoGenero().length() > 0) {
				Genero g = new Genero();
				g.setTipoGenero(genero.getTipoGenero());
				managerGenero.insertarGenero(g);
				listaGenero = managerGenero.findAllGeneros();
				limpiarGenero();
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

	public void actionListenerEliminarGenero(Integer id) {
		try {
			managerGenero.eliminarGenero(id);
			listaGenero = managerGenero.findAllGeneros();
			JSFUtil.crearMensajeInfo("Su género ha sido eliminado");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al eliminar");
		}

	}

	public List<Genero> getListaGenero() {
		return listaGenero;
	}

	public void setListaGenero(List<Genero> listaGenero) {
		this.listaGenero = listaGenero;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	

}
