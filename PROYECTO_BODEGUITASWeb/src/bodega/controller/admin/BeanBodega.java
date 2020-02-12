package bodega.controller.admin;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bodega.model.admin.ManagerBitacora;
import bodega.model.admin.ManagerBodega;
import bodega.model.admin.ManagerMovimiento;
import bodega.model.admin.ManagerProducto;
import bodega.model.admin.ManagerPuntoVenta;
import bodega.model.admin.ManagerUsuario;
import bodega.model.entities.Bodega;
import bodega.model.entities.Movimiento;
import bodega.model.entities.Producto;
import bodega.model.entities.PuntoVenta;
import bodega.model.entities.Usuario;

@Named
@SessionScoped
public class BeanBodega implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Producto> listaProducto;
	private List<Bodega> listaBodega;
	private List<PuntoVenta> listaPuntoVenta;
	private List<Usuario> listaUsuario;
	@EJB
	private ManagerBodega managerBodega;
	
	@EJB
	private ManagerPuntoVenta managerPv;
	@EJB
	private ManagerBitacora managerbit;
	
	@EJB
	private ManagerUsuario managerUser;
	
	@EJB
	private ManagerProducto managerProducto;
	
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
			PuntoVenta puntoVenta=managerPv.findByIdPuntoVenta(bod.getPuntoVenta().getIdPtoVenta());
			//Usuario user=managerUser.findByIdUsuario(bod.getUsuario().getIdUsuario());
			
			//idUser=bod.getUsuario().getIdUsuario();
			idPuntoVenta=bod.getPuntoVenta().getIdPtoVenta();
			bodega.setDireccionBodega(bod.getDireccionBodega());
			bodega.setNombreBodega(bod.getNombreBodega());
			bodega.setIdBodega(bod.getIdBodega());
			bodega.setPuntoVenta(puntoVenta);
			
		//	bodega.setUsuario(user);
			
			
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
				//b.setUsuario(user);
				PuntoVenta pv=managerPv.findByIdPuntoVenta(idPuntoVenta);
				b.setPuntoVenta(pv);
				managerBodega.actualizarBodega(b);
				managerbit.crearEvento("actionListenerActualizarBodega()", "Actualiza la bodega ");
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
				//Usuario user=managerUser.findByIdUsuario(idUser);
				//b.setUsuario(user);
				PuntoVenta pv=managerPv.findByIdPuntoVenta(idPuntoVenta);
				b.setPuntoVenta(pv);
				managerBodega.insertarBodega(b);
				managerbit.crearEvento("actionListenerInsertarBodega()", "Inserta nueva bodega ");
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

	public void actionListenerEliminarBodega(Integer id) {
		try {
			
			managerBodega.eliminarBodega(id);
			listaBodega=managerBodega.findAllBodega();
			managerbit.crearEvento("actionListenerEliminarBodega()", "Elimina una bodega ");
			JSFUtil.crearMensajeInfo("Su punto bodega ha sido eliminada");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al eliminar: No se puede eliminar porque la bodega posee productos");
		}
	}

	public String  ver_productos_bodegas() {
		return "ver_productos_bodega";
	}
	
	 public List<Producto> ver_productos_bodega(Integer id) {
			 listaProducto=managerProducto.findWhereID_Bodega(id);
			
			 return listaProducto;
//		for (Movimiento m : listaMovimientos) {
//			System.out.println(m.getComentario());
//			System.out.println("---> " + m.getIdMovim());
//		}
//		return "ver_productos_bodega";
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

	

	public List<Producto> getListaProducto() {
		return listaProducto;
	}

	public void setListaProducto(List<Producto> listaProducto) {
		this.listaProducto = listaProducto;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}
	
	
}
