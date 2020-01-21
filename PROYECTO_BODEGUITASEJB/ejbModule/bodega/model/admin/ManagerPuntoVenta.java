package bodega.model.admin;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import bodega.model.entities.PuntoVenta;
import bodega.model.manager.ManagerDAO;

/**
 * Session Bean implementation class ManagerCategoria
 */
@Stateless
@LocalBean
public class ManagerPuntoVenta {

    /**
     * Default constructor. 
     */
	@PersistenceContext
	private EntityManager em;
@EJB
private ManagerDAO managerDAO;
	
    public ManagerPuntoVenta() {
        // TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
	public List<PuntoVenta>findAllPuntoVenta() throws Exception{
    	try {        	
        	return managerDAO.findAll(PuntoVenta.class);
		} catch (Exception e) {
			throw new Exception("No existe registro de punto de venta");
		}
    	
    }
 
    public PuntoVenta findByIdPuntoVenta(Integer id) {
    	
    	try {
			return (PuntoVenta) managerDAO.findById(PuntoVenta.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
    }
    
    public void insertarPuntoVenta(PuntoVenta puntoV) throws Exception {
    	//managerDAO.insertar(cat);
    	em.merge(puntoV);
    }
    
    public void actualizarPuntoVenta(PuntoVenta puntoV)throws Exception{
    	PuntoVenta pv=findByIdPuntoVenta(puntoV.getIdPtoVenta());
    	if (pv==null) {
			throw new Exception("No existe el punto de venta especificado");
			
		} else {
			pv.setNombrePtoVenta(puntoV.getNombrePtoVenta());
			pv.setCorreoPtoVenta(puntoV.getCorreoPtoVenta());
			pv.setDireccionPtoVenta(puntoV.getDireccionPtoVenta());
			pv.setTelefonoPtoVenta(puntoV.getTelefonoPtoVenta());
			
			managerDAO.actualizar(pv);
		}
    	
    }
    
    public void eliminarPuntoVenta(Integer id) throws Exception {
    	
			managerDAO.eliminar(PuntoVenta.class, id);
	
    }
    
}
