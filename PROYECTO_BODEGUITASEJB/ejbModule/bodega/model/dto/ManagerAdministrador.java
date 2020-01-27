package bodega.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sun.mail.handlers.text_html;
/*
import sistema_registro.model.admin.manager.ManagerUsuario;
import sistema_registro.model.entities.Administrador;
import sistema_registro.model.entities.Proveedor;
import sistema_registro.model.entities.Rol;
import sistema_registro.model.entities.SuperAdmin;
import sistema_registro.model.entities.Pyme;
import sistema_registro.model.entities.Usuario;
import sistema_registro.model.entities.UsuarioRol;

/**
 * Session Bean implementation class ManagerAdministrador
 */
@Stateless
@LocalBean
public class ManagerAdministrador {
	@PersistenceContext
	private EntityManager em;
	@EJB
	private ManagerAdministrador managerAdministrador;
	//@EJB
	//private ManagerUsuario managerUsuario;

	/**
	 * Default constructor.
	 */
	public ManagerAdministrador() {
		// TODO Auto-generated constructor stub
	}
/*
	public List<Usuario> findAllUsuarios() {
		String consulta = "select o from Usuario o order by o.apellidos";
		Query q = em.createQuery(consulta, Usuario.class);
		return q.getResultList();
	}

	public List<Administrador> findAllAdministradores() {
		String consulta = "Select p from Administrador p where p.usuario.estado='true' order by p.usuario.ultimaModificacion DESC";
		Query q = em.createQuery(consulta, Administrador.class);
		//if (q.getResultList().size() > 0) {
		//	System.out.println("devolvio algun administrador");
		//} else {
		//	System.out.println("no devolvio nada");
		//}
		return q.getResultList();
	}

//public List<Administrador> admini() {
	// String consulta = "select a from Administrador a";
	// Query q = em.createQuery(consulta, Administrador.class);

	// return q.getResultList();
//	}

	public List<UsuarioRol> UsuarioRolAdministrador() {
		/// String consulta = "select o from usuario o order by o.apellidos";
		String consulta = "SELECT u FROM UsuarioRol u";
		// String consulta = "select o from Usuario o where o.cedula='" + cedula + "'";
		Query q = em.createQuery(consulta, UsuarioRol.class);
		if (q.getResultList().size() > 0) {
		//	System.out.println("devolvio alguna cedula");
		}
		return q.getResultList();
	}

	public List<Pyme> findAllPymes() {
		/// String consulta = "select o from usuario o order by o.apellidos";
		String consulta = "select o from Pyme o order by o.nombre";
		Query q = em.createQuery(consulta, Pyme.class);

		return q.getResultList();
	}

	public Pyme findPymeById(Integer idPyme) {
		Pyme u = em.find(Pyme.class, idPyme);
		return u;
	}

	public int insertarPyme(Pyme pyme2, int nuevoIdPyme) {
		int d = 0;
		Pyme pyme3 = new Pyme();
		// pyme3.setIdPyme(nuevoIdPyme);
		pyme3.setNombre(pyme2.getNombre());
		pyme3.setDireccion(pyme2.getDireccion());

		// java.sql.Date sqlDate = new java.sql.Date(dat.getTime());

		pyme3.setFechaCreacion(new Timestamp(new Date().getTime()));

		pyme3.setUltimaModificacion(new Timestamp(new Date().getTime()));
		em.persist(pyme3);

	//	System.out.println("pyme intertado");
		List<Pyme> u = findUsuarioByNombreDireccion(pyme3.getNombre(), pyme3.getDireccion());

		for (int i = 0; i < u.size(); i++) {
			if (u.get(0) == null) {
			//	System.out.println("NO existe la pyme con el nombre y la direcion especificada.");
			}
			d = u.get(0).getIdPyme();

		}

		return d;
	}

	public List<Pyme> findUsuarioByNombreDireccion(String nombre, String direccion) {
		String consulta = "select o from Pyme o where o.nombre='" + nombre + "' and o.direccion='" + direccion + "'";
		Query q = em.createQuery(consulta, Pyme.class);
		if (q.getResultList().size() > 0) {
			System.out.println("devolvio algun nombre y direccion");
		}
		return q.getResultList();
	}

	public Usuario findSuperAdminById1(Integer idSuperAdmin) {
		Usuario u = em.find(Usuario.class, idSuperAdmin);
		return u;
	}

	public SuperAdmin findSuperAdminById(Integer idSuperAdmin) {
		SuperAdmin u = em.find(SuperAdmin.class, idSuperAdmin);
		return u;
	}

	public Rol findRolById(Integer idRol) {
		Rol u = em.find(Rol.class, idRol);
		return u;
	}

	public List<Usuario> findUsuarioByCedula(String cedula) {
		//System.out.println("llego hasta aqui 3");
		String consulta = "select o from Usuario o where o.cedula='" + cedula + "'";

		// return em.find(Usuario.class, cedula);
		Query q = em.createQuery(consulta, Usuario.class);
		if (q.getResultList().size() > 0) {
		//	System.out.println("devolvio alguna cedula");
		}
		return q.getResultList();
	}

	public List<Usuario> findUsuarioByCorreo(String correo) {
	//	System.out.println("llego hasta aqui 3");
		String consulta = "select o from Usuario o where o.correo='" + correo + "'";

		Query q = em.createQuery(consulta, Usuario.class);
		if (q.getResultList().size() > 0) {
		//	System.out.println("devolvio alguna cedula");
		}
		return q.getResultList();
	}

	public void insertarUsuario(Administrador ad, int pymeSeleccionado) throws Exception {
		Date fecha = new Date();
		ad.getUsuario().setFechaCreacion(new Timestamp(fecha.getTime()));
		ad.getUsuario().setUltimaModificacion(new Timestamp(fecha.getTime()));
		ad.getUsuario().setPassword(ad.getUsuario().getCedula());
		ad.getUsuario().setEstado(true);
		List<Usuario> users2 = managerAdministrador.findUsuarioByCorreo(ad.getUsuario().getCorreo());
		if (users2.size() > 0) {
			throw new Exception("El correo ya esta registrada.");
		}
		List<Usuario> users = managerAdministrador.findUsuarioByCedula(ad.getUsuario().getCedula());
		if (users.size() > 0) {
			if (users.get(0).getEstado() == true) {
				throw new Exception("La cedula ya esta registrado.");
			} else {
				ad.getUsuario().setIdUsuario(
						managerAdministrador.findUsuarioByCedula(ad.getUsuario().getCedula()).get(0).getIdUsuario());
				actualizarUsuario(ad, pymeSeleccionado);
				return;
			}
		}
		Pyme p = findPymeById(pymeSeleccionado);
		ad.getUsuario().setPyme(p);
		Usuario u = ad.getUsuario();
		em.persist(u);
		Usuario u1 = (Usuario) managerAdministrador.findUsuarioByCedula(u.getCedula()).get(0);
		ad.setIdAdministrador(u1.getIdUsuario());
		UsuarioRol ur = new UsuarioRol();
		ur.setUsuario(u1);
		Rol r = new Rol();
		r.setIdRol(2);
		ur.setRol(r);
		ur.setFechaCreacion(new Timestamp(fecha.getTime()));
		ur.setUltimaModificacion(new Timestamp(fecha.getTime()));
		em.persist(ur);
		em.persist(ad);
	}

	public Usuario findUsuarioById(int id) {
		return em.find(Usuario.class, id);
	}

	public void actualizarUsuario(Administrador a, int pymeSeleccionado) throws Exception {
		Usuario u = managerAdministrador.findUsuarioById(a.getUsuario().getIdUsuario());
		if (u == null) {
			throw new Exception("No existe el registro de la Pyme");
		}
		List<Usuario> users = managerAdministrador.findUsuarioByCorreo(a.getUsuario().getCorreo());
		for (Usuario usuario : users) {
			if (!usuario.getIdUsuario().equals(a.getUsuario().getIdUsuario())
					&& usuario.getCorreo().equals(a.getUsuario().getCorreo())) {
				throw new Exception("El correo ya esta registrado.");
			}
		}
		List<Usuario> users2 = managerAdministrador.findUsuarioByCedula(a.getUsuario().getCedula());
		for (Usuario usuario : users2) {
			if (!usuario.getIdUsuario().equals(a.getUsuario().getIdUsuario())
					&& usuario.getCedula().equals(a.getUsuario().getCedula())) {
				throw new Exception("La cédula ya esta registrada.");
			}
		}
		u.setCedula(a.getUsuario().getCedula());
		u.setNombres(a.getUsuario().getNombres());
		u.setApellidos(a.getUsuario().getApellidos());
		u.setDireccion(a.getUsuario().getDireccion());
		u.setCorreo(a.getUsuario().getCorreo());
		u.setTelefono(a.getUsuario().getTelefono());
		u.setCedula(a.getUsuario().getCedula());
		u.setFoto(a.getUsuario().getFoto());
		u.setPassword(a.getUsuario().getCedula());
		u.setEstado(true);
		u.setUltimaModificacion(new Timestamp(new Date().getTime()));
		Pyme p = findPymeById(pymeSeleccionado);
		u.setPyme(p);
		em.merge(u);
	}

	public void eliminarUsuario(int id) {
		Usuario u = managerUsuario.findUsuarioById(id);
		if (u != null) {
			u.setEstado(false);
			em.merge(u);
		}

	}

	public void actualizarUsuario4(Usuario usuario, int pymeSeleccionado) throws Exception {
		List<Usuario> u = findUsuarioByCedula(usuario.getCedula());
		Pyme pyme = new Pyme();
		pyme.setIdPyme(pymeSeleccionado);
		for (int i = 0; i < u.size(); i++) {
			if (u.get(0) == null) {
				throw new Exception("NO existe el usuario con la cedula especificada.");
			}
			u.get(0).setCedula(usuario.getCedula());
			u.get(0).setNombres(usuario.getNombres());
			u.get(0).setApellidos(usuario.getApellidos());
			u.get(0).setDireccion(usuario.getDireccion());
			u.get(0).setCorreo(usuario.getCorreo());
			u.get(0).setTelefono(usuario.getTelefono());
			u.get(0).setCelular(usuario.getCelular());

			u.get(0).setPyme(pyme);
			// System.out.println("HOla 1");
			em.merge(u.get(0));
		}

		// System.out.println("HOla 2");
	}

	public void actualizarClave(int id, String passwd) throws Exception {
		Usuario u = em.find(Usuario.class, id);
		if (u == null) {
			throw new Exception("NO existe el usuario.");
		}
		u.setPassword(passwd);
		em.merge(u);
	}

	public List<UsuarioRol> UsuarioRolAdmin() {
		/// String consulta = "select o from usuario o order by o.apellidos";
		String consulta = "SELECT u FROM UsuarioRol u";
		// String consulta = "select o from Usuario o where o.cedula='" + cedula + "'";
		Query q = em.createQuery(consulta, UsuarioRol.class);
		if (q.getResultList().size() > 0) {
			System.out.println("devolvio alguna cedula");
		}
		return q.getResultList();
	}

	public List<Usuario> comprobarAdministrador(List<Usuario> listaUsuario) {
		List<UsuarioRol> ur = UsuarioRolAdministrador();
		List<Usuario> u = new ArrayList<>();
		int v;
		System.out.println("tama0" + listaUsuario.size());

		for (int i = 0; i < listaUsuario.size(); i++) {
			for (int j = 0; j < listaUsuario.size(); j++) {
				// System.out.println("id=" + listaUsuario.get(i).getIdUsuario() + "ES IGUAL A "
				// + ur.get(j).getUsuario().getIdUsuario() + "ROL=" +
				// ur.get(j).getRol().getIdRol());
				if ((listaUsuario.get(i).getIdUsuario() == ur.get(j).getUsuario().getIdUsuario())
						&& (ur.get(j).getRol().getIdRol() == 2)) {
					System.out.println("id=" + listaUsuario.get(i).getIdUsuario() + "ES IGUALll "
							+ ur.get(i).getUsuario().getIdUsuario() + "ROL=" + ur.get(i).getRol().getIdRol());
					u.add(listaUsuario.get(i));
				}
			}
		}

		System.out.println("HOla 2");
		return u;
	}
*/
}
