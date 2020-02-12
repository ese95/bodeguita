package bodega.model.admin;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import bodega.model.entities.Movimiento;
import bodega.model.entities.TipoDocumento;
import bodega.model.manager.ManagerDAO;

/**
 * Session Bean implementation class ManagerCategoria
 */
@Stateless
@LocalBean
public class ManagerMovimiento {

    /**
     * Default constructor. 
     */
	@PersistenceContext
	private EntityManager em;
@EJB
private ManagerDAO managerDAO;
	
    public ManagerMovimiento() {
        // TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
	public List<Movimiento>findAllMovimientos() throws Exception{
    	try {        	
        	return managerDAO.findAll(Movimiento.class);
		} catch (Exception e) {
			throw new Exception("No existe registro usuarios");
		}
    	
    }
 
    public Movimiento findByIdMovimiento(Integer id) {
    	
    	try {
			return (Movimiento) managerDAO.findById(Movimiento.class, id);
			//managerDAO.findWhere(clase, pClausulaWhere, pOrderBy)
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
    }
    
   
    
public TipoDocumento findByIdTipoDocumento(Integer id) {
    	
    	try {
			return (TipoDocumento) managerDAO.findById(TipoDocumento.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
    }
    
 /*public Usuario findWhereCorreoUsuario(String correo) {
    	
    	try {
			return (Usuario) managerDAO.findById(Usuario.class, id);
			managerDAO.findWhere(clase, pClausulaWhere, pOrderBy)
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
    }*/
    public void insertarMovimiento(Movimiento mov) throws Exception {
    	//managerDAO.insertar(cat);
    	em.merge(mov);
    }
    
    public void actualizarMovimiento(Movimiento mov)throws Exception{
    	Movimiento m=findByIdMovimiento(mov.getIdMovim());
    	if (m==null) {
			throw new Exception("No existe movimiento especificado");
			
		} else {
			m.setFechaMovim(mov.getFechaMovim());
			m.setCantidadMovim(mov.getCantidadMovim());
			m.setComentario(mov.getComentario());
			m.setCostoMovim(mov.getCostoMovim());
			m.setPrecioBaseMovim(mov.getPrecioBaseMovim());
			m.setBodega(mov.getBodega());
			m.setProducto(mov.getProducto());
			m.setTipoDocumento(mov.getTipoDocumento());
			
			
			managerDAO.actualizar(m);
		}
    	
    }
    
    public void eliminarMovimiento(Integer id) throws Exception {
    	
			managerDAO.eliminar(Movimiento.class, id);
    }
    
    
    
    
}
