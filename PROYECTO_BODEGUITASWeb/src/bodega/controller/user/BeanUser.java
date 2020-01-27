package bodega.controller.user;

import java.io.Serializable;


import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;



import bodega.controller.admin.JSFUtil;



@Named
@SessionScoped
public class BeanUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@PostConstruct
	public void inicializar() {
		try {
//			listaUser = managerUser.findAllUsuarios();
//			listaRol = managerRol.findAllRol();
//			listaGenero = managerGenero.findAllGeneros();
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}

	}

	

	public String irLogin() {
		return "login.xhtml?faces-redirect=true";
	}
	
	public String irInicio() {
		return "index.xhtml?faces-redirect=true";
	}

	

}
