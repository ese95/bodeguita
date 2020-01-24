package bodega.model.admin;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bodega.model.entities.Bitacora;
import bodega.model.manager.ManagerDAO;

/**
 * Session Bean implementation class ManagerBitacora
 */
@Stateless
@LocalBean
public class ManagerBitacora {

    /**
     * Default constructor. 
     */
	@PersistenceContext
	private EntityManager em;
@EJB
private ManagerDAO managerDAO;
	
    public ManagerBitacora() {
        // TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("unchecked")
	public List<Bitacora>findAllBitacora() throws Exception{
    	try {        	
        	return managerDAO.findAll(Bitacora.class);
		} catch (Exception e) {
			throw new Exception("No existe registro de bitacora");
		}
    	
    }
 
    public void insertarBitacora(Bitacora bit) throws Exception {
    	//managerDAO.insertar(cat);
    	em.merge(bit);
    }
    /*
    public Medida findByIdMedida(Integer id) {
    	
    	try {
			return (Medida) managerDAO.findById(Medida.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
    */
    
    
    
    ///////////////////////////////////////////
    

	/*public List<Bitacora> findAllBitacoras() {
		String consulta = "SELECT o FROM Bitacora o";
		Query q = em.createQuery(consulta, Bitacora.class);
		return q.getResultList();
	}
	
	public void crearEvento(String correo,Class clase,String metodo,String descripcion) throws Exception{
		Bitacora evento=new Bitacora();
		//cambio para probar git
		
	//	if(codigoUsuario==null||codigoUsuario==0)
		if(correo.length()==0)
			throw new Exception("Error auditoria: debe indicar el correo del usuario.");
		if(metodo==null||metodo.length()==0)
			throw new Exception("Error auditoria: debe indicar el metodo que genera el evento.");

		//Usuario usuario=(Usuario)managerDAO.findById(Usuario.class, correo);
		List <Usuario>usuario=managerDAO.findUsuarioByCedula(correo);
		if(usuario==null)
			throw new Exception("Error auditoria: no existe el usuario indicado.");

		/////obtener la ip 3 formas/////////////////
        InetAddress address = InetAddress.getLocalHost();
        String remoteAddr = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr(); 
        
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        
     //   System.out.println("IP Local :"+address.getHostAddress());
      //  System.out.println("ipAddress:" + ipAddress);
        
        
		evento.setUsuario(usuario.get(0));
		evento.setAccion(clase.getSimpleName()+"/"+metodo);
		evento.setDescripcion(descripcion);
		evento.setFecha(new Timestamp(new Date().getTime()));
		evento.setIp(remoteAddr);

		
		managerDAO.insertar(evento);
	}
    */
    
    
    
    
}