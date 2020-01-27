package bodega.model.dto;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;

import bodega.model.entities.Usuario;
import bodega.model.entities.UsuarioRol;

/*
import sistema_registro.model.admin.manager.ManagerUsuario;
import sistema_registro.model.bitacora.manager.ManagerAuditoria;
import sistema_registro.model.dto.LoginDTO;
import sistema_registro.model.entities.Usuario;
import sistema_registro.model.entities.UsuarioRol;
import sistema_registro.model.superadmin.manager.ManagerLogin;
*/
/**
 * Implementa la logica de manejo de usuarios y autenticacion
 */
@Stateless
@LocalBean
public class ManagerSeguridad {
	@EJB
	// private ManagerDAO managerDAO;
	private ManagerLogin managerLogin;

	// @EJB
	// private ManagerAuditoria managerAuditoria;

	public ManagerSeguridad() {

	}

	public LoginDTO accederSistemaRol(String correo, String clave, Integer ROL) throws Exception {
		// Usuario usuario=(Usuario)managerDAO.findById(Usuario.class, codigoUsuario);
		int idUsuario;
		List<Usuario> usuario = managerLogin.findUsuarioByCorreo(correo);
		idUsuario = usuario.get(0).getIdUsuario();
		if (usuario.size() == 0 || usuario == null) {
			throw new Exception("NO existe usuario con el correo ingresado");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usu", usuario.get(0));
			System.out.println("clave= " + usuario.get(0).getClaveUsuario());
			System.out.println("correo= " + usuario.get(0).getCorreoUsuario());
			System.out.println("usuario size =" + usuario.size());

			System.out.println("Id USUARIO ->> " + usuario.get(0).getIdUsuario());

		}
		
		if (!usuario.get(0).getClaveUsuario().equals(clave)) {
			System.out.println(" NO if = " + usuario.get(0).getClaveUsuario() + " = " + clave);
			throw new Exception("Contraseña incorrecta");
		}
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsuario(usuario.get(0).getNombreUsuario() + " " + usuario.get(0).getApellidoUsuario());
		loginDTO.setIdUsuario(usuario.get(0).getIdUsuario());
		
		
		System.out.println("idUsuario=" + usuario.get(0).getIdUsuario());
		
		List<UsuarioRol> rol = managerLogin.findUsuarioRol(usuario.get(0).getIdUsuario());
		System.out.println("rol progando '': "+rol.get(0).getRol().getTipoRol());
		for (UsuarioRol usuarioRol : rol) {
			System.out.println("roles con user"+usuarioRol.getUsuario().getCorreoUsuario()+"  rol-->"+usuarioRol.getRol().getTipoRol());
			System.out.println("RROL "+ROL);
			if (usuarioRol.getRol().getIdRol().equals(ROL.intValue())) {
				
				loginDTO.setTipoUsuario(rol);
				
				System.out.println("ROL -> "+ROL);
				System.out.println("compara "+rol.get(0).getRol().getIdRol());
				if (usuarioRol.getRol().getIdRol()== 1) {
					loginDTO.setRutaAcceso("/admin/home.xhtml");
					
				} else if (usuarioRol.getRol().getIdRol() == 2) {
					loginDTO.setRutaAcceso("/cliente/home.xhtml");
				
				}else if (usuarioRol.getRol().getIdRol() == 3) {
					loginDTO.setRutaAcceso("/proveedor/home.xhtml");
					
				}else if (usuarioRol.getRol().getIdRol() == 4) {
					loginDTO.setRutaAcceso("/super_admin/home.xhtml");
					
				}else {
					loginDTO.setRutaAcceso("/login.xhtml");
					
				}
				
				
			}else{
				System.out.println("no tiene asigando ese rol ");
			}
			/*else {
			
				throw new Exception("El usuario no tiene asigando ese rol");
			}
			*/
			
		}
		
		System.out.println("rol =" + rol.get(0).getRol().getTipoRol());
		System.out.println("loginDTO "+loginDTO.getTipoUsuario().get(0).getRol().getTipoRol());
		System.out.println("if = " + usuario.get(0).getClaveUsuario() + " = " + clave);
		
		
	
		loginDTO.setCodigoCorreo(usuario.get(0).getCorreoUsuario());


		return loginDTO;

	}

	public LoginDTO accederSistema(String correo, String clave) throws Exception {
		// Usuario usuario=(Usuario)managerDAO.findById(Usuario.class, codigoUsuario);
		int idUsuario;
		List<Usuario> usuario = managerLogin.findUsuarioByCorreo(correo);
		idUsuario = usuario.get(0).getIdUsuario();
		if (usuario.size() == 0) {
			throw new Exception("NO existe usuario con el correo ingresado");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usu", usuario.get(0));
			System.out.println("clave= " + usuario.get(0).getClaveUsuario());
			System.out.println("correo= " + usuario.get(0).getCorreoUsuario());
			System.out.println("usuario size =" + usuario.size());

			System.out.println("Id USUARIO ->> " + usuario.get(0).getIdUsuario());

		}
		System.out.println("idUsuario=" + usuario.get(0).getIdUsuario());
		List<UsuarioRol> rol = managerLogin.findUsuarioRol(usuario.get(0).getIdUsuario());
		if (rol.size() == 0) {
			throw new Exception("El usuario no tiene Rol");
		}
		System.out.println("rol =" + rol.get(0).getRol().getTipoRol());
		System.out.println("if = " + usuario.get(0).getClaveUsuario() + " = " + clave);
		if (!usuario.get(0).getClaveUsuario().equals(clave)) {
			System.out.println(" NO if = " + usuario.get(0).getClaveUsuario() + " = " + clave);
			throw new Exception("Contraseña incorrecta");
		}
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsuario(usuario.get(0).getNombreUsuario() + " " + usuario.get(0).getApellidoUsuario());
		loginDTO.setIdUsuario(usuario.get(0).getIdUsuario());
		loginDTO.setTipoUsuario(rol);
		loginDTO.setCodigoCorreo(usuario.get(0).getCorreoUsuario());

		// dependiendo del tipo de usuario, configuramos la ruta de acceso a las pags
		// web:
		System.out.println("rol sixe " + rol.size());
		// if(rol.size()==1) {
		if (rol.get(0).getRol().getIdRol() == 1)
			loginDTO.setRutaAcceso("/admin/home.xhtml");
	    else if (rol.get(0).getRol().getIdRol() == 2)
			loginDTO.setRutaAcceso("/cliente/home.xhtml");
		else if (rol.get(0).getRol().getIdRol() == 3)
			loginDTO.setRutaAcceso("/proveedor/home.xhtml");
		else if (rol.get(0).getRol().getIdRol() == 4)
			loginDTO.setRutaAcceso("/super_admin/home.xhtml");
		/*
		 * else if (rol.get(0).getRol().getIdRol() == 5)
		 * loginDTO.setRutaAcceso("/bitacora/home.xhtml");
		 */
		// managerAuditoria.crearEvento(usuario.get(0).getIdUsuario(), this.getClass(),
		// "accederSistema()", "ROL : "+ rol);
		/*
		 * }else{ loginDTO.setRutaAcceso("/login.xhtml"); }
		 */
		/*
		 * { loginDTO.setRutaAcceso("/tiposUsuarios/tipoUsuario.xhtml"); }
		 */
		return loginDTO;

	}

}
