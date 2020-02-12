package bodega.model.admin;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

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
    
    
//    @SuppressWarnings("rawtypes")
//  	private void generarLog(long idTipoEvento,Credencial credencial,Class clase,String metodo,String detalle){
//      	Bitacora nuevoEvento;
//      	nuevoEvento=new Bitacora();
//      	LogTiposEvento tipoEvento=null;
//      	Date fecha=new Date();
//      	
//      	//buscamos el tipo de evento correspondiente:
//      	try {
//  			tipoEvento=findByIdLogTiposEvento(idTipoEvento);
//  			if(tipoEvento==null)
//  				System.out.println("ERROR LOG NO EXISTE TIPO EVENTO: "+idTipoEvento);
//  		} catch (Exception e1) {
//  			System.out.println("ERROR BUSCANDO LOG TIPO EVENTO: "+e1.getMessage());
//  		}
//      	
//      	nuevoEvento.setDireccionIp(credencial.getDireccionIP());
//      	nuevoEvento.setFechaEvento(fecha);
//      	nuevoEvento.setIdUsuario(new BigDecimal(credencial.getIdUsuario()));
//      	nuevoEvento.setMensaje(detalle);
//      	nuevoEvento.setMetodo(clase.getCanonicalName()+"["+ metodo +"]");
//      	nuevoEvento.setLogTiposEvento(tipoEvento);
//      	
//      	try {
//  			managerDAO.insertar(nuevoEvento);
//  		} catch (Exception e) {
//  			System.out.println("ERROR LOG: "+e.getMessage());
//  		}
//      }
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
    

//	public List<Bitacora> findAllBitacoras() {
//		String consulta = "SELECT o FROM Bitacora o";
//		Query q = em.createQuery(consulta, Bitacora.class);
//		return q.getResultList();
//	}
	
	public void crearEvento(String metodo,String descripcion) throws Exception{
		Bitacora evento=new Bitacora();
		//cambio para probar git
		
	//	if(codigoUsuario==null||codigoUsuario==0)
//		if(correo.length()==0)
//			throw new Exception("Error auditoria: debe indicar el correo del usuario.");
//		if(metodo==null||metodo.length()==0)
//			throw new Exception("Error auditoria: debe indicar el metodo que genera el evento.");

		//Usuario usuario=(Usuario)managerDAO.findById(Usuario.class, correo);
//		List <Usuario>usuario=managerDAO.findUsuarioByCedula(correo);
//		if(usuario==null)
//			throw new Exception("Error auditoria: no existe el usuario indicado.");

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
        
        
		evento.setAccion(metodo);
		//evento.setAccion(clase.getSimpleName()+"/"+metodo);
		evento.setDescripcion(descripcion);
		evento.setFecha(new Date());
		evento.setIp(remoteAddr);
		
		managerDAO.insertar(evento);
	}
    
    
    
    
    
}
