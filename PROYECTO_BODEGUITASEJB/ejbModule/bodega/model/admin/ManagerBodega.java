package bodega.model.admin;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import bodega.model.entities.Bodega;
import bodega.model.manager.ManagerDAO;

/**
 * Session Bean implementation class ManagerCategoria
 */
@Stateless
@LocalBean
public class ManagerBodega {

    /**
     * Default constructor. 
     */
	@PersistenceContext
	private EntityManager em;
@EJB
private ManagerDAO managerDAO;
	
    public ManagerBodega() {
        // TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
	public List<Bodega>findAllBodega() throws Exception{
    	try {        	
        	return managerDAO.findAll(Bodega.class);
		} catch (Exception e) {
			throw new Exception("No existe registro de bodega");
		}
    	
    }
 
    public Bodega findByIdBodega(Integer id) {
    	
    	try {
			return (Bodega) managerDAO.findById(Bodega.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
    }
    
    public void insertarBodega(Bodega bod) throws Exception {
    	//managerDAO.insertar(cat);
    	em.merge(bod);
    }
    
    public void actualizarBodega(Bodega bodega)throws Exception{
    	Bodega bod=findByIdBodega(bodega.getIdBodega());
    	if (bod==null) {
			throw new Exception("No existe bodega especificada");
			
		} else {
			bod.setDireccionBodega(bodega.getDireccionBodega());
			bod.setMovimientos(bodega.getMovimientos());
			bod.setNombreBodega(bodega.getNombreBodega());
			bod.setPuntoVenta(bodega.getPuntoVenta());
			
			managerDAO.actualizar(bod);
		}
    	
    }
    
    public void eliminarBodega(Integer id) throws Exception {
    	
			managerDAO.eliminar(Bodega.class, id);
	
    }
    
}
