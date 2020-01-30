package bodega.controller.administrador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bodega.model.administrador.ManagerMedida;
import bodega.model.entities.Medida;

@Named
@SessionScoped
public class BeanMedida implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Medida> listaMedida;
	@EJB
	private ManagerMedida managerMedida;
	private Medida medida;

	@PostConstruct
	public void inicializar() {
		try {
			listaMedida = managerMedida.findAllMedidas();
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}

		medida= new Medida();
	}

	public void actionListenerCargarMedida(Medida med) {
		try {
			
			medida=new Medida();
			medida.setTipoMedida(med.getTipoMedida());
			medida.setIdMedida(med.getIdMedida());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionListenerActualizarMedida() {
		try {
			if (medida.getTipoMedida().length() > 0) {
				Medida m=managerMedida.findByIdMedida(medida.getIdMedida());
				m.setTipoMedida(medida.getTipoMedida());
				managerMedida.actualizarMedida(m);
				listaMedida=managerMedida.findAllMedidas();
				JSFUtil.crearMensajeInfo("Actualizado con �xito");
			} else {
				JSFUtil.crearMensajeError("Debe ingresar el tipo de medida"); 
			}

		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al actualizar");
		}

	}

	public void limpiarMedida() {
		medida.setTipoMedida("");
	}

	public void actionListenerInsertarMedida() {
		try {
			if (medida.getTipoMedida().length() > 0) {
				Medida m=new Medida();
				m.setTipoMedida(medida.getTipoMedida());
				managerMedida.insertarMedida(m);
				listaMedida=managerMedida.findAllMedidas();
				limpiarMedida();
				JSFUtil.crearMensajeInfo("Insertado con �xito");
			} else {
				JSFUtil.crearMensajeError("Debe ingresar todos los campos");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al crear");
		}

	}

	public void actionListenerEliminarMedida(Integer id) {
		try {
			managerMedida.eliminarMedida(id);
			listaMedida=managerMedida.findAllMedidas();
			JSFUtil.crearMensajeInfo("Su medida ha sido eliminada");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al eliminar");
		}

	}

	public List<Medida> getListaMedida() {
		return listaMedida;
	}

	public void setListaMedida(List<Medida> listaMedida) {
		this.listaMedida = listaMedida;
	}

	public Medida getMedida() {
		return medida;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}
	

}
