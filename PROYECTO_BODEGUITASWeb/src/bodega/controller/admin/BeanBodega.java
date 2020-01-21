package bodega.controller.admin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bodega.model.admin.ManagerBodega;
import bodega.model.admin.ManagerPuntoVenta;
import bodega.model.admin.ManagerUsuario;
import bodega.model.entities.Bodega;
import bodega.model.entities.PuntoVenta;
import bodega.model.entities.Usuario;

@Named
@SessionScoped
public class BeanBodega implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Bodega> listaBodega;
	private List<PuntoVenta> listaPuntoVenta;
	private List<Usuario> listaUsuario;
	@EJB
	private ManagerBodega managerBodega;
	
	@EJB
	private ManagerPuntoVenta managerPv;
	
	@EJB
	private ManagerUsuario managerUser;
	
	private Bodega bodega;
	private Usuario user;
	private PuntoVenta puntoVenta;
	private Integer idUser;
	private Integer idPuntoVenta;

	@PostConstruct
	public void inicializar() {
		try {
			listaBodega = managerBodega.findAllBodega();
			listaPuntoVenta=managerPv.findAllPuntoVenta();
			listaUsuario=managerUser.findAllUsuarios();
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}
		bodega = new Bodega();
	
	}

	public void actionListenerCargarBodega(Bodega bod) {
		try {
			bodega=new Bodega();
			puntoVenta=managerPv.findByIdPuntoVenta(bod.getPuntoVenta().getIdPtoVenta());
			user=managerUser.findByIdUsuario(bod.getUsuario().getIdUsuario());
			bodega.setDireccionBodega(bod.getDireccionBodega());
			bodega.setNombreBodega(bod.getNombreBodega());
			bodega.setIdBodega(bod.getIdBodega());
			bodega.setPuntoVenta(puntoVenta);
			bodega.setUsuario(user);
			setIdPuntoVenta(idPuntoVenta);
			setIdUser(idUser);
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeError("No se ha podido cargar su bodega");
		}
	}

	public void actionListenerActualizarBodega() {
		try {
			if (bodega.getNombreBodega().length() > 0 && !bodega.getDireccionBodega().isEmpty()) {
				Bodega b=managerBodega.findByIdBodega(bodega.getIdBodega());
				b.setDireccionBodega(bodega.getDireccionBodega());
				b.setNombreBodega(bodega.getNombreBodega());
				Usuario user=managerUser.findByIdUsuario(idUser);
				b.setUsuario(user);
				PuntoVenta pv=managerPv.findByIdPuntoVenta(idPuntoVenta);
				b.setPuntoVenta(pv);
				managerBodega.actualizarBodega(b);
				
				listaBodega=managerBodega.findAllBodega();
				JSFUtil.crearMensajeInfo("Actualizado con éxito");
				
			} else {
				JSFUtil.crearMensajeError("Debe ingresar todos los campos");
			}

		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al actualizar");
		}

	}

	public void limpiarBodega() {
		bodega.setDireccionBodega("");
		bodega.setNombreBodega("");
		
	}

	public void actionListenerInsertarBodega() {
		try {
			if (bodega.getNombreBodega().length() > 0 && !bodega.getDireccionBodega().isEmpty()) {
				Bodega b=new Bodega();
				b.setDireccionBodega(bodega.getDireccionBodega());
				b.setNombreBodega(bodega.getNombreBodega());
				Usuario user=managerUser.findByIdUsuario(idUser);
				b.setUsuario(user);
				PuntoVenta pv=managerPv.findByIdPuntoVenta(idPuntoVenta);
				b.setPuntoVenta(pv);
				managerBodega.insertarBodega(b);
				
				listaBodega=managerBodega.findAllBodega();
				limpiarBodega();
				JSFUtil.crearMensajeInfo("Insertado con éxito");
			} else {
				JSFUtil.crearMensajeError("Debe ingresar todos los campos");
			}
		} catch (Exception e) {
		
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al crear");
		}

	}

	public void actionListenerEliminarUsuario(Integer id) {
		try {
			managerBodega.eliminarBodega(id);
			listaBodega=managerBodega.findAllBodega();
			JSFUtil.crearMensajeInfo("Su punto bodega ha sido eliminada");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al eliminar");
		}
	}

	public List<Bodega> getListaBodega() {
		return listaBodega;
	}

	public void setListaBodega(List<Bodega> listaBodega) {
		this.listaBodega = listaBodega;
	}

	public Bodega getBodega() {
		return bodega;
	}

	public void setBodega(Bodega bodega) {
		this.bodega = bodega;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Integer getIdPuntoVenta() {
		return idPuntoVenta;
	}

	public void setIdPuntoVenta(Integer idPuntoVenta) {
		this.idPuntoVenta = idPuntoVenta;
	}

	public List<PuntoVenta> getListaPuntoVenta() {
		return listaPuntoVenta;
	}

	public void setListaPuntoVenta(List<PuntoVenta> listaPuntoVenta) {
		this.listaPuntoVenta = listaPuntoVenta;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}
	
	
}
