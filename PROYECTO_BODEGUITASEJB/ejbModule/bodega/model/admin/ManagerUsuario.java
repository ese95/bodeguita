package bodega.model.admin;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import bodega.model.entities.Usuario;
import bodega.model.manager.ManagerDAO;

/**
 * Session Bean implementation class ManagerCategoria
 */
@Stateless
@LocalBean
public class ManagerUsuario {

    /**
     * Default constructor. 
     */
	@PersistenceContext
	private EntityManager em;
@EJB
private ManagerDAO managerDAO;
	
    public ManagerUsuario() {
        // TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
	public List<Usuario>findAllUsuarios() throws Exception{
    	try {        	
        	return managerDAO.findAll(Usuario.class);
		} catch (Exception e) {
			throw new Exception("No existe registro usuarios");
		}
    	
    }
 
    public Usuario findByIdUsuario(Integer id) {
    	
    	try {
			return (Usuario) managerDAO.findById(Usuario.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
    }
    
    public void insertarUsuario(Usuario us) throws Exception {
    	//managerDAO.insertar(cat);
    	em.merge(us);
    }
    
    public void actualizarUsuario(Usuario usuario)throws Exception{
    	Usuario u=findByIdUsuario(usuario.getIdUsuario());
    	if (u==null) {
			throw new Exception("No existe el punto de venta especificado");
			
		} else {
			u.setApellidoUsuario(usuario.getApellidoUsuario());
			u.setCedulaUsuario(usuario.getCedulaUsuario());
			u.setClaveUsuario(usuario.getClaveUsuario());
			u.setCorreoUsuario(usuario.getCorreoUsuario());
			u.setDireccionUsuario(usuario.getDireccionUsuario());
			u.setFechaNacUsuario(usuario.getFechaNacUsuario());
			u.setGenero(usuario.getGenero());
			u.setImagenUsuario(usuario.getImagenUsuario());
			u.setNombreUsuario(usuario.getNombreUsuario());
			u.setRol(usuario.getRol());
			u.setTelefonoUsuario(usuario.getTelefonoUsuario());
						
			managerDAO.actualizar(u);
		}
    	
    }
    
    public void eliminarUsuario(Integer id) throws Exception {
    	
			managerDAO.eliminar(Usuario.class, id);
    }
    
}
