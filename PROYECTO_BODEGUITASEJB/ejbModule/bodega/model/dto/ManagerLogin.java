package bodega.model.dto;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bodega.model.admin.ManagerUsuario;
import bodega.model.entities.Usuario;
import bodega.model.entities.UsuarioRol;
/*
import sistema_registro.model.entities.Usuario;
import sistema_registro.model.entities.UsuarioRol;
*/
@Stateless
@LocalBean
public class ManagerLogin {
	@PersistenceContext
	private EntityManager em;
	@EJB private ManagerUsuario managerUser;
	
	public List<Usuario> findUsuarioByCorreo(String correo) {
		System.out.println("llego hasta aqui 3");
		String consulta = "select u from Usuario u where u.correoUsuario='" + correo + "'";

		// return em.find(Usuario.class, cedula);
		Query q = em.createQuery(consulta, Usuario.class);
		if (q.getResultList().size() > 0) {
			System.out.println("devolvio algun correo");
		}
		return q.getResultList();
	}
	
	public List<UsuarioRol> findUsuarioRol() {	
		String consulta = "SELECT u FROM UsuarioRol u";
		Query q = em.createQuery(consulta, UsuarioRol.class);
		return q.getResultList();
	}
	public List<UsuarioRol> findUsuarioRol(int idUsuario) {	
		String consulta = "SELECT u FROM UsuarioRol u where u.usuario.idUsuario="+idUsuario;
		Query q = em.createQuery(consulta, UsuarioRol.class);
		return q.getResultList();
	}
	public int encontrarRol(int idUsuario) {
		List<UsuarioRol> ur=findUsuarioRol();
		int a=0;
		System.out.println("size usuarioRol= "+ur.size());
		for (int i = 0; i < ur.size(); i++) {
			System.out.println("devolvio algun rol");
			System.out.println("if  "+ur.get(i).getUsuario().getIdUsuario()+" = "+ idUsuario);
			if(ur.get(i).getUsuario().getIdUsuario()==idUsuario) {
				a=ur.get(i).getRol().getIdRol();
				break;
			}
			
		  }
		
			return a;
		}
		
	
	
	
	
}
