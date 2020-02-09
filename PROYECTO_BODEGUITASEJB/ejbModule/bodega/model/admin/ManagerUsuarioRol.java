package bodega.model.admin;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bodega.model.entities.UsuarioRol;
import bodega.model.manager.ManagerDAO;

/**
 * Session Bean implementation class ManagerCategoria
 */
@Stateless
@LocalBean
public class ManagerUsuarioRol {

	/**
	 * Default constructor.
	 */
	@PersistenceContext
	private EntityManager em;
	@EJB
	private ManagerDAO managerDAO;

	public ManagerUsuarioRol() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioRol> findAllUsuarioRol() throws Exception {
		try {
			return managerDAO.findAll(UsuarioRol.class);
		} catch (Exception e) {
			throw new Exception("No existe registro de Usuario Rol");
		}

	}

	@SuppressWarnings("unchecked")
	public List<UsuarioRol> findAllUsuarioRolADM() throws Exception {
		try {
			// return managerDAO.findAll(UsuarioRol.class);
			return managerDAO.findWhere(UsuarioRol.class, "o.rol.idRol<=3", null);
		} catch (Exception e) {
			throw new Exception("No existe registro de Usuario Rol");
		}

	}

	public UsuarioRol findByIdUsuarioRol(Integer id) {

		try {
			return (UsuarioRol) managerDAO.findById(UsuarioRol.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void insertarUsuarioRol(UsuarioRol uRol) throws Exception {
		// managerDAO.insertar(cat);
		try {
//			for (UsuarioRol usuarioR : findAllUsuarioRol()) {
//				System.out.println("perero " + usuarioR.getRol().getIdRol().equals(uRol.getRol().getIdRol()));
//				System.out.println(usuarioR.getRol().getIdRol());
//				System.out.println(uRol.getRol().getIdRol());
//
//				System.out.println(
//						"segundo " + usuarioR.getUsuario().getIdUsuario().equals(uRol.getUsuario().getIdUsuario()));
//				System.out.println(usuarioR.getUsuario().getIdUsuario());
//				System.out.println(uRol.getUsuario().getIdUsuario());
//				System.out.println("-------------------------------------------");
//				if (!usuarioR.getRol().getIdRol().equals(uRol.getRol().getIdRol())) {
					em.merge(uRol);
//					break;				
//				} 
		} catch (Exception e) {
			throw new Exception("No se pudo ingresar el nuevo rol de usuario porque yá existe");
		}

	}

	public void actualizarUsuarioRol(UsuarioRol uRol) throws Exception {
		UsuarioRol ur = findByIdUsuarioRol(uRol.getIdUsuarioRol());
		if (ur == null) {
			throw new Exception("No existe Usuario Rol especificado");

		} else {
			ur.setRol(uRol.getRol());
			ur.setUsuario(uRol.getUsuario());

			managerDAO.actualizar(ur);
		}

	}

	public void eliminarUsuarioRol(Integer id) throws Exception {

		managerDAO.eliminar(UsuarioRol.class, id);

	}

}
