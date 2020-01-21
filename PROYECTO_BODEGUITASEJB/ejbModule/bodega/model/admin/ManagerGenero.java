package bodega.model.admin;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import bodega.model.entities.Genero;
import bodega.model.manager.ManagerDAO;

/**
 * Session Bean implementation class ManagerCategoria
 */
@Stateless
@LocalBean
public class ManagerGenero {

    /**
     * Default constructor. 
     */
	@PersistenceContext
	private EntityManager em;
@EJB
private ManagerDAO managerDAO;
	
    public ManagerGenero() {
        // TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
	public List<Genero>findAllGeneros() throws Exception{
    	try {        	
        	return managerDAO.findAll(Genero.class);
		} catch (Exception e) {
			throw new Exception("No existe registro de categorias");
		}
    	
    }
 
    public Genero findByIdGeneros(Integer id) {
    	
    	try {
			return (Genero) managerDAO.findById(Genero.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    	
    }
    
    public void insertarGenero(Genero gen) throws Exception {
    	//managerDAO.insertar(cat);
    	em.merge(gen);
    }
    
    public void actualizarGenero(Genero gen)throws Exception{
    	Genero g=findByIdGeneros(gen.getIdGenero());
    	if (g==null) {
			throw new Exception("No existe la género especificado");
			
		} else {
			g.setTipoGenero(gen.getTipoGenero());
			managerDAO.actualizar(g);
		}
    	
    }
    
    public void eliminarGenero(Integer id) throws Exception {
    	
			managerDAO.eliminar(Genero.class, id);
    }
    
}
