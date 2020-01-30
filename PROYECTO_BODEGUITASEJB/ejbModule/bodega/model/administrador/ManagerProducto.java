package bodega.model.administrador;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import bodega.model.entities.Producto;
import bodega.model.manager.ManagerDAO;

/**
 * Session Bean implementation class ManagerCategoria
 */
@Stateless
@LocalBean
public class ManagerProducto {

    /**
     * Default constructor. 
     */
	@PersistenceContext
	private EntityManager em;
@EJB
private ManagerDAO managerDAO;
	
    public ManagerProducto() {
        // TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
	public List<Producto>findAllProductos() throws Exception{
    	try {        	
        	return managerDAO.findAll(Producto.class);
		} catch (Exception e) {
			throw new Exception("No existe registro productos");
		}
    	
    }
 
    public Producto findByIdProducto(Integer id) {
    	
    	try {
			return (Producto) managerDAO.findById(Producto.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
    }
    
    public void insertarUsuario(Producto p) throws Exception {
    	//managerDAO.insertar(cat);
    	em.merge(p);
    }
    
    public void actualizarProducto(Producto prod)throws Exception{
    	Producto p=findByIdProducto(prod.getIdProducto());
    	if (p==null) {
			throw new Exception("No existe el producto especificado");
			
		} else {
			p.setCantidadStockProducto(prod.getCantidadStockProducto());
			p.setCaracteristicasProducto(prod.getCaracteristicasProducto());
			p.setCategoria(prod.getCategoria());
			p.setCostoProducto(prod.getCostoProducto());
			p.setDescripcionProducto(prod.getDescripcionProducto());
			p.setEstadoProducto(prod.getEstadoProducto());
			p.setImagenProducto(prod.getImagenProducto());
			p.setMedida(prod.getMedida());
			p.setNombreProducto(prod.getNombreProducto());
			p.setPrecioBaseProducto(prod.getPrecioBaseProducto());
			managerDAO.actualizar(p);
		}
    	
    }
    
    public void eliminarProducto(Integer id) throws Exception {
    	
			managerDAO.eliminar(Producto.class, id);
    }
    
}
