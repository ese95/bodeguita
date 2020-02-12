package bodega.controller.admin;


import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bodega.model.admin.ManagerBitacora;
import bodega.model.admin.ManagerCategoria;
import bodega.model.entities.Categoria;

@Named
@SessionScoped
public class BeanCategoria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private List<Categoria>listaCategoria;
	@EJB
	private ManagerCategoria managerCategoria;
	
	@EJB
	private ManagerBitacora managerbit;
	private Categoria categoria;
	
	@PostConstruct
	public void inicializar()  {
		try {
			listaCategoria=managerCategoria.findAllCategorias();	
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}
	
	categoria=new Categoria();
	}	
	
	public void actionListenerCargarCategoria(Categoria cat) {
		try {
			categoria=new Categoria();
			categoria.setNombreCategoria(cat.getNombreCategoria());
			categoria.setEstadoCategoria(cat.getEstadoCategoria());
			categoria.setIdCategoria(cat.getIdCategoria());
			System.out.println("categoria*** "+categoria.getIdCategoria());
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void actionListenerActualizarCategoria() {
		try {
			if (categoria.getNombreCategoria().length()>0) {
				Categoria c=managerCategoria.findByIdCategoria(categoria.getIdCategoria());
				c.setNombreCategoria(categoria.getNombreCategoria());
				c.setEstadoCategoria(categoria.getEstadoCategoria());
				managerCategoria.actualizarCategoria(c);
				listaCategoria=managerCategoria.findAllCategorias();
				managerbit.crearEvento("actionListenerActualizarCategoria()", "Actualiza una categoria ");
				JSFUtil.crearMensajeInfo("Actualizado con �xito");
			} else {
				JSFUtil.crearMensajeError("Debe ingresar el nombre de la categor�a");
			}
			
			
		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al actualizar");
		}
		
	}
	
	public void limpiarCategoria() {
	categoria.setEstadoCategoria(null);	
	categoria.setNombreCategoria("");
	}
	public void actionListenerInsertarCategoria() {
		try {
			if (categoria.getNombreCategoria().length()>0) {
				Categoria c=new Categoria();
				c.setEstadoCategoria(categoria.getEstadoCategoria());
				c.setNombreCategoria(categoria.getNombreCategoria());
				managerCategoria.insertarCategoria(c);
				listaCategoria=managerCategoria.findAllCategorias();
				limpiarCategoria();
				managerbit.crearEvento("actionListenerInsertarCategoria()", "Inserta una categoria ");
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
	public void actionListenerEliminarCategoria(Integer id) {
		try {
			managerCategoria.eliminarCategoria(id);
			listaCategoria=managerCategoria.findAllCategorias();
			managerbit.crearEvento("actionListenerEliminarCategoria()", "Elimina una categoria ");
			JSFUtil.crearMensajeInfo("Su categor�a ha sido eliminada");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al eliminar");
		}
		
	}
	public List<Categoria> getListaCategoria() {
		return listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
	
	
}
