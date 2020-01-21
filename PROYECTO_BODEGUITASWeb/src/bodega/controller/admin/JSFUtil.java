package bodega.controller.admin;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JSFUtil {

	public static void crearMensajeInfo(String mensaje) {
		FacesMessage msj=new FacesMessage();
		msj.setSeverity(FacesMessage.SEVERITY_INFO);
		msj.setSummary(mensaje);
		FacesContext.getCurrentInstance().addMessage(null, msj);
	
	}
	
	public static void crearMensajeWarning(String mensaje) {
		FacesMessage msj=new FacesMessage();
		msj.setSeverity(FacesMessage.SEVERITY_WARN);
		msj.setSummary(mensaje);
		FacesContext.getCurrentInstance().addMessage(null, msj);
	
	}
	
	public static void crearMensajeError(String mensaje) {
		FacesMessage msj=new FacesMessage();
		msj.setSeverity(FacesMessage.SEVERITY_ERROR);
		msj.setSummary(mensaje);
		FacesContext.getCurrentInstance().addMessage(null, msj);
	
	}
}
