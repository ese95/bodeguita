package bodega.model.admin;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bodega.model.entities.Categoria;
import bodega.model.manager.ManagerDAO;

/**
 * Session Bean implementation class ManagerCategoria
 */
@Stateless
@LocalBean
public class ManagerCategoria {

    /**
     * Default constructor. 
     */
	@PersistenceContext
	private EntityManager em;
@EJB
private ManagerDAO managerDAO;
	
    public ManagerCategoria() {
        // TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
	public List<Categoria>findAllCategorias() throws Exception{
    	try {        	
        	return managerDAO.findAll(Categoria.class);
		} catch (Exception e) {
			throw new Exception("No existe registro de categorias");
		}
    	
    }
 
    public Categoria findByIdCategoria(Integer id) {
    	
    	try {
			return (Categoria) managerDAO.findById(Categoria.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    	
    }
    
    public void insertarCategoria(Categoria cat) throws Exception {
    	//managerDAO.insertar(cat);
    	em.merge(cat);
    }
    
    public void actualizarCategoria(Categoria cat)throws Exception{
    	Categoria c=findByIdCategoria(cat.getIdCategoria());
    	if (c==null) {
			throw new Exception("No existe la categoría especificada");
			
		} else {
			c.setNombreCategoria(cat.getNombreCategoria());
			c.setEstadoCategoria(cat.getEstadoCategoria());
			managerDAO.actualizar(c);
		}
    	
    }
    
    public void eliminarCategoria(Integer id) throws Exception {
    	
			managerDAO.eliminar(Categoria.class, id);
	
    }
    
}
