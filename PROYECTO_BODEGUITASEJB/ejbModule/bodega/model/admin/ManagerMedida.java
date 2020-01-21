package bodega.model.admin;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import bodega.model.entities.Medida;
import bodega.model.manager.ManagerDAO;

/**
 * Session Bean implementation class ManagerCategoria
 */
@Stateless
@LocalBean
public class ManagerMedida {

    /**
     * Default constructor. 
     */
	@PersistenceContext
	private EntityManager em;
@EJB
private ManagerDAO managerDAO;
	
    public ManagerMedida() {
        // TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
	public List<Medida>findAllMedidas() throws Exception{
    	try {        	
        	return managerDAO.findAll(Medida.class);
		} catch (Exception e) {
			throw new Exception("No existe registro de medidas");
		}
    	
    }
 
    public Medida findByIdMedida(Integer id) {
    	
    	try {
			return (Medida) managerDAO.findById(Medida.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    
    public void insertarMedida(Medida med) throws Exception {
    	//managerDAO.insertar(cat);
    	em.merge(med);
    }
    
    public void actualizarMedida(Medida med)throws Exception{
    	Medida m=findByIdMedida(med.getIdMedida());
    	if (m==null) {
			throw new Exception("No existe medida especificada");
			
		} else {
			m.setTipoMedida(med.getTipoMedida());
			managerDAO.actualizar(m);
		}
    	
    }
    
    public void eliminarMedida(Integer id) throws Exception {
    	
			managerDAO.eliminar(Medida.class, id);
    }
    
}
