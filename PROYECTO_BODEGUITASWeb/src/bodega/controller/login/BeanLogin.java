package bodega.controller.login;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import bodega.model.entities.Usuario;
import bodega.model.entities.UsuarioRol;
import bodega.controller.admin.JSFUtil;
import bodega.model.dto.LoginDTO;
import bodega.model.dto.ManagerLogin;
import bodega.model.dto.ManagerSeguridad;
import bodega.model.util.ModelUtil;
/*
import sistema_registro.admin.controller.JSFUtil;
import sistema_registro.model.dto.LoginDTO;
import sistema_registro.model.entities.Usuario;
import sistema_registro.model.entities.UsuarioRol;
import sistema_registro.model.bitacora.manager.ManagerAuditoria;
import sistema_registro.model.bitacora.manager.ModelUtil;
import sistema_registro.model.superadmin.manager.ManagerLogin;
import sistema_registro.model.superadmin.manager.ManagerSeguridad;
*/

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class BeanLogin implements Serializable {
	private static final long serialVersionUID = 1L;

	private String correo;
	private String password;
	private int idTipoUsuario;
	private List<UsuarioRol> tipoUsuario;
	private boolean acceso;
	private int idUsuario;
	@EJB
	private ManagerSeguridad managerSeguridad;
	@EJB
	private ManagerLogin managerAuditoria;
	@EJB
	private ManagerLogin managerLogin;
	
	private LoginDTO loginDTO;
	private boolean showDo = false;
	// private boolean showDo2=false;
	private boolean showUndo = false;

	@PostConstruct
	public void inicializar() {
		loginDTO = new LoginDTO();
	}

	public String accederSistema() {
		acceso = false;
		try {
			if (!correo.isEmpty()&&!password.isEmpty()) {
				if (correo.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
					loginDTO = managerSeguridad.accederSistemaRol(correo, password, idTipoUsuario);
					
					idUsuario=loginDTO.getIdUsuario();
					// verificamos el acceso del usuario:
					tipoUsuario = loginDTO.getTipoUsuario();
					// redireccion dependiendo del tipo de usuario:
					System.out.println("antes de crear el evento" + loginDTO.getRutaAcceso());
				//	managerAuditoria.crearEvento(correo, this.getClass(), "accederSistema", "Acceso a login");
					System.out.println("Redireccionado " + loginDTO.getRutaAcceso());
					return loginDTO.getRutaAcceso() + "?faces-redirect=true";
				} else {
					JSFUtil.crearMensajeError("ERROR : Debe ingresar un correo válido");
				}
				
			}else {
				JSFUtil.crearMensajeError("ERROR : Debe ingresar su correo y contraseña");
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeError("ERROR : "+e.getMessage());
		}
		return "";
	}

	public boolean validarRol1() throws Exception {
		showDo = false;

		FacesContext context = FacesContext.getCurrentInstance();
		Usuario us = (Usuario) context.getExternalContext().getSessionMap().get("usu");
		System.out.println("Entro y va a buscar el id " + us.getIdUsuario());
		List<UsuarioRol> ur = managerLogin.findUsuarioRol(us.getIdUsuario());
		for (int i = 0; i < ur.size(); i++) {
			if (ur.get(i).getRol().getIdRol() == 1) {
				System.out.println("rol =  " + ur.get(i).getRol().getIdRol());
				showDo = true;
			}
		}
		loginDTO.setRutaAcceso("/super_admin/home.xhtml");
		return showDo;
	}

	public boolean validarRol2() throws Exception {
		showDo = false;
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario us = (Usuario) context.getExternalContext().getSessionMap().get("usu");
		List<UsuarioRol> ur = managerLogin.findUsuarioRol(us.getIdUsuario());
		for (int i = 0; i < ur.size(); i++) {
			if (ur.get(i).getRol().getIdRol() == 2) {
				System.out.println("rol =  " + ur.get(i).getRol().getIdRol());
				showDo = true;
			}
		}
		loginDTO.setRutaAcceso("/admin/index.xhtml");
		return showDo;
	}

	public boolean validarRol3() throws Exception {
		showDo = false;
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario us = (Usuario) context.getExternalContext().getSessionMap().get("usu");
		List<UsuarioRol> ur = managerLogin.findUsuarioRol(us.getIdUsuario());
		for (int i = 0; i < ur.size(); i++) {
			if (ur.get(i).getRol().getIdRol() == 3) {
				System.out.println("rol =  " + ur.get(i).getRol().getIdRol());
				showDo = true;
			}
		}
		loginDTO.setRutaAcceso("/receptor/index.xhtml");
		return showDo;
	}
/*
	public boolean validarRol4() {
		showDo = false;
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario us = (Usuario) context.getExternalContext().getSessionMap().get("usu");
		List<UsuarioRol> ur = managerLogin.findUsuarioRol(us.getIdUsuario());
		for (int i = 0; i < ur.size(); i++) {
			if (ur.get(i).getRol().getIdRol() == 4) {
				showDo = true;
			}
		}
		loginDTO.setRutaAcceso("/proveedor/home.xhtml");
		return showDo;
	}

	public boolean validarRol5() {
		showDo = false;
		FacesContext context = FacesContext.getCurrentInstance();
		Usuario us = (Usuario) context.getExternalContext().getSessionMap().get("usu");
		List<UsuarioRol> ur = managerLogin.findUsuarioRol(us.getIdUsuario());
		for (int i = 0; i < ur.size(); i++) {
			if (ur.get(i).getRol().getIdRol() == 5) {
				showDo = true;
			}
		}
		loginDTO.setRutaAcceso("/bitacora/home.xhtml");
		return showDo;
	}
*/
	public String salirSistema() {
		System.out.println("salirSistema");
		try {
			if (loginDTO.getCodigoCorreo() != null) {
				/*managerAuditoria.crearEvento(loginDTO.getCodigoCorreo(), this.getClass(), "salirSistema",
						"Cerrar sesion");
						*/
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index.xhtml?faces-redirect=true";
	}

	public void actionVerificarLogin() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String requestPath = ec.getRequestPathInfo();
		// System.out.println("requestPath="+requestPath);
		// System.out.println("requestPath loginDTO= "+loginDTO.getRutaAcceso());
		try {
			// si no paso por login:
			if (loginDTO == null || ModelUtil.isEmpty(loginDTO.getRutaAcceso())) {
				System.out.println("entro a null loginDTO");
				ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
			} else {
				// validar las rutas de acceso:
				if (requestPath.contains("/super_admin") && loginDTO.getRutaAcceso().startsWith("/super_admin"))
					return;
				if (requestPath.contains("/cliente") && loginDTO.getRutaAcceso().startsWith("/cliente"))
					return;
				if (requestPath.contains("/proveedor") && loginDTO.getRutaAcceso().startsWith("/proveedor"))
					return;
				if (requestPath.contains("/admin") && loginDTO.getRutaAcceso().startsWith("/admin"))
					return;
				if (requestPath.contains("/bitacora") && loginDTO.getRutaAcceso().startsWith("/bitacora"))
					return;
				if (requestPath.contains("/tiposUsuario") && loginDTO.getRutaAcceso().startsWith("/tiposUsuario"))
					return;

				// caso contrario significa que hizo login pero intenta acceder a ruta no
				// permitida:
				System.out.println("entro al monton de IFF");
				ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UsuarioRol> getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(List<UsuarioRol> tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public boolean isAcceso() {
		return acceso;
	}

	public void setAcceso(boolean acceso) {
		this.acceso = acceso;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdTipoUsuario() {
		return idTipoUsuario;
	}

	public void setIdTipoUsuario(int idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}

	public LoginDTO getLoginDTO() {
		return loginDTO;
	}

	public void setLoginDTO(LoginDTO loginDTO) {
		this.loginDTO = loginDTO;
	}



}
