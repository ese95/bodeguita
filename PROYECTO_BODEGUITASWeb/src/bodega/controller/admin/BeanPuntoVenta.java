package bodega.controller.admin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bodega.model.admin.ManagerPuntoVenta;
import bodega.model.entities.PuntoVenta;

@Named
@SessionScoped
public class BeanPuntoVenta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<PuntoVenta> listapv;
	@EJB
	private ManagerPuntoVenta managerpv;
	private PuntoVenta puntoVenta;

	@PostConstruct
	public void inicializar() {
		try {
			listapv = managerpv.findAllPuntoVenta();
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}
		puntoVenta = new PuntoVenta();
	}

	public void actionListenerCargarPuntoVenta(PuntoVenta pv) {
		try {
			puntoVenta = new PuntoVenta();
			puntoVenta.setCorreoPtoVenta(pv.getCorreoPtoVenta());
			puntoVenta.setDireccionPtoVenta(pv.getDireccionPtoVenta());
			puntoVenta.setIdPtoVenta(pv.getIdPtoVenta());
			puntoVenta.setNombrePtoVenta(pv.getNombrePtoVenta());
			puntoVenta.setTelefonoPtoVenta(pv.getTelefonoPtoVenta());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionListenerActualizarPuntoVenta() {
		try {
			if (puntoVenta.getNombrePtoVenta().length() > 0 && !puntoVenta.getCorreoPtoVenta().isEmpty()) {
				if (puntoVenta.getCorreoPtoVenta().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
				PuntoVenta pv = managerpv.findByIdPuntoVenta(puntoVenta.getIdPtoVenta());
				pv.setCorreoPtoVenta(puntoVenta.getCorreoPtoVenta());
				pv.setDireccionPtoVenta(puntoVenta.getDireccionPtoVenta());
				pv.setNombrePtoVenta(puntoVenta.getNombrePtoVenta());
				pv.setTelefonoPtoVenta(puntoVenta.getTelefonoPtoVenta());
				managerpv.actualizarPuntoVenta(pv);
				listapv = managerpv.findAllPuntoVenta();
				JSFUtil.crearMensajeInfo("Actualizado con éxito");
				} else {
					JSFUtil.crearMensajeError("Debe ingresar un correo válido");
				}
			} else {
				JSFUtil.crearMensajeError("Debe ingresar el nombre del punto de venta");
			}

		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al actualizar");
		}

	}

	public void limpiarPuntoVenta() {
		puntoVenta.setCorreoPtoVenta("");
		puntoVenta.setDireccionPtoVenta("");
		puntoVenta.setNombrePtoVenta("");
		puntoVenta.setTelefonoPtoVenta("");
	}

	public void actionListenerInsertarPuntoVenta() {
		try {
			if (puntoVenta.getNombrePtoVenta().length() > 0 && !puntoVenta.getCorreoPtoVenta().isEmpty()) {
				if (puntoVenta.getCorreoPtoVenta().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {

					PuntoVenta pv = new PuntoVenta();
					pv.setCorreoPtoVenta(puntoVenta.getCorreoPtoVenta());
					pv.setDireccionPtoVenta(puntoVenta.getDireccionPtoVenta());
					pv.setNombrePtoVenta(puntoVenta.getNombrePtoVenta());
					pv.setTelefonoPtoVenta(puntoVenta.getTelefonoPtoVenta());
					managerpv.insertarPuntoVenta(pv);
					listapv = managerpv.findAllPuntoVenta();
					limpiarPuntoVenta();
					JSFUtil.crearMensajeInfo("Insertado con éxito");

				} else {
					JSFUtil.crearMensajeError("Debe ingresar un correo válido");
				}
			} else {
				JSFUtil.crearMensajeError("Debe ingresar todos los campos");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al crear");
		}

	}

	public void actionListenerEliminarPuntoVenta(Integer id) {
		try {
			managerpv.eliminarPuntoVenta(id);
			listapv = managerpv.findAllPuntoVenta();
			JSFUtil.crearMensajeInfo("Su punto de venta ha sido eliminado");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al eliminar");
		}

	}

	public List<PuntoVenta> getListapv() {
		return listapv;
	}

	public void setListapv(List<PuntoVenta> listapv) {
		this.listapv = listapv;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

}
