package bodega.controller.admin;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import bodega.model.admin.ManagerGenero;
import bodega.model.admin.ManagerRol;
import bodega.model.admin.ManagerUsuario;
import bodega.model.entities.Genero;
import bodega.model.entities.Rol;
import bodega.model.entities.Usuario;
import sun.misc.BASE64Encoder;

@Named
@SessionScoped
public class BeanUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Usuario> listaUser;
	private List<Rol> listaRol;
	private List<Genero> listaGenero;
	private Integer idRol;
	private Integer idGenero;
	private UploadedFile uploadedFile;
	
	@EJB
	private ManagerUsuario managerUser;
	@EJB
	private ManagerGenero managerGenero;
	@EJB
	private ManagerRol managerRol;
	private Usuario user;

	@PostConstruct
	public void inicializar() {
		try {
			listaUser=managerUser.findAllUsuarios();
			listaRol=managerRol.findAllRol();
			listaGenero=managerGenero.findAllGeneros();
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}
	user=new Usuario();
	}

	public void actionListenerCargarUsuario(Usuario u) {
		try {
			user=new Usuario();
			Genero gen=managerGenero.findByIdGeneros(u.getGenero().getIdGenero());
			
			Rol rol=managerRol.findByIdRol(u.getRol().getIdRol());
			
			user.setApellidoUsuario(u.getApellidoUsuario());
			user.setCedulaUsuario(u.getCedulaUsuario());
			user.setClaveUsuario(u.getClaveUsuario());
			System.out.println("c-> "+u.getClaveUsuario());
			System.out.println("clave-->>" +user.getClaveUsuario());
			user.setCorreoUsuario(u.getCorreoUsuario());
			user.setDireccionUsuario(u.getDireccionUsuario());
			user.setFechaNacUsuario(u.getFechaNacUsuario());
			user.setRol(rol);
			user.setGenero(gen);
			setIdRol(u.getRol().getIdRol());
			setIdGenero(u.getGenero().getIdGenero());
			user.setImagenUsuario(u.getImagenUsuario());
			user.setNombreUsuario(u.getNombreUsuario());
			user.setTelefonoUsuario(u.getTelefonoUsuario());
			user.setIdUsuario(u.getIdUsuario());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionListenerActualizarUsuario() {
		try {
			BufferedImage image = null;
			if (uploadedFile != null && uploadedFile.getSize() > 0) {
				if (uploadedFile.getFileName().matches(".*\\.(png|jpeg|jpg|gif)$")) {
					image = ImageIO.read(uploadedFile.getInputstream());
				} else {
					JSFUtil.crearMensajeError("Seleccione una imagen con los siguientes formatos: png, jpeg, jpg, gif");
					return;
				}
			} else {
				System.out.println(System.getProperty("user.dir"));
				
				InputStream inputstream = new FileInputStream("d:\\foto\\foto.png");
				
				image = ImageIO.read(inputstream);
			}
			
			if (user.getNombreUsuario().length() > 0 && !user.getCedulaUsuario().isEmpty()&& !user.getCorreoUsuario().isEmpty()) {
				if (user.getCorreoUsuario().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
					String cod64 = encodeToString(image, "png");
						
				Usuario u=managerUser.findByIdUsuario(user.getIdUsuario());	
				u.setApellidoUsuario(user.getApellidoUsuario());
				u.setCedulaUsuario(user.getCedulaUsuario());
				u.setClaveUsuario(user.getClaveUsuario());
				u.setCorreoUsuario(user.getCorreoUsuario());
				u.setDireccionUsuario(user.getDireccionUsuario());
				u.setFechaNacUsuario(user.getFechaNacUsuario());
				
				
				u.setGenero(managerGenero.findByIdGeneros(idGenero));
				u.setImagenUsuario(cod64);
				u.setNombreUsuario(user.getNombreUsuario());
				
				System.out.println("-->genero"+u.getGenero().getTipoGenero());
				
				u.setRol(managerRol.findByIdRol(idRol));
				System.out.println("--> rol "+u.getRol().getTipoRol());
				u.setTelefonoUsuario(user.getTelefonoUsuario());
				managerUser.actualizarUsuario(u);
		        listaUser=managerUser.findAllUsuarios();
		
				JSFUtil.crearMensajeInfo("Actualizado con éxito");
				} else {
					JSFUtil.crearMensajeError("Debe ingresar un correo válido");
				}
			} else {
				JSFUtil.crearMensajeError("Debe ingresar todos los campos");
			}

		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al actualizar");
		}

	}

	public void limpiarUsuario() {
		
		user.setApellidoUsuario("");
		user.setCedulaUsuario("");
		user.setClaveUsuario("");
		user.setCorreoUsuario("");
		user.setDireccionUsuario("");
		user.setFechaNacUsuario(null);
		user.setGenero(null);
		user.setImagenUsuario("");
		user.setNombreUsuario("");
		user.setRol(null);
		user.setTelefonoUsuario("");
		}

	public void actionListenerInsertarUsuario() {
		try {
			BufferedImage image = null;
			if (uploadedFile != null && uploadedFile.getSize() > 0) {
				if (uploadedFile.getFileName().matches(".*\\.(png|jpeg|jpg|gif)$")) {
					image = ImageIO.read(uploadedFile.getInputstream());
				} else {
					JSFUtil.crearMensajeError("Seleccione una imagen con los siguientes formatos: png, jpeg, jpg, gif");
					return;
				}
			} else {
				System.out.println(System.getProperty("user.dir"));
				
				InputStream inputstream = new FileInputStream("d:\\foto\\foto.png");
				
				image = ImageIO.read(inputstream);
			}
			
			if (user.getNombreUsuario().length() > 0 && !user.getCedulaUsuario().isEmpty()&& !user.getCorreoUsuario().isEmpty()) {
				if (user.getCorreoUsuario().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
					String cod64 = encodeToString(image, "png");
					
					Usuario u=new Usuario();
					
					u.setApellidoUsuario(user.getApellidoUsuario());
					u.setCedulaUsuario(user.getCedulaUsuario());
					u.setClaveUsuario(user.getClaveUsuario());
					u.setCorreoUsuario(user.getCorreoUsuario());
					u.setDireccionUsuario(user.getDireccionUsuario());
					u.setFechaNacUsuario(user.getFechaNacUsuario());
					Genero gen=managerGenero.findByIdGeneros(idGenero);
					u.setGenero(gen);	
					Rol rol=managerRol.findByIdRol(idRol);
					u.setImagenUsuario(cod64);
					u.setNombreUsuario(user.getNombreUsuario());
					u.setRol(rol);
					u.setTelefonoUsuario(user.getTelefonoUsuario());
					managerUser.insertarUsuario(u);
				    listaUser=managerUser.findAllUsuarios();
					limpiarUsuario();
					
					JSFUtil.crearMensajeInfo("Insertado con éxito");

				} else {
					JSFUtil.crearMensajeError("Debe ingresar un correo válido");
				}
			} else {
				JSFUtil.crearMensajeError("Debe ingresar todos los campos");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al crear");
		}

	}

	public String encodeToString(BufferedImage image, String type) {
		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, type, bos);
			byte[] imageBytes = bos.toByteArray();
			BASE64Encoder encoder = new BASE64Encoder();
			imageString = encoder.encode(imageBytes);
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageString;
	}
	public void actionListenerEliminarUsuario(Integer id) {
		try {
			managerUser.eliminarUsuario(id);
			listaUser=managerUser.findAllUsuarios();
			JSFUtil.crearMensajeInfo("Su usuario ha sido eliminado");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al eliminar");
		}

	}

	public List<Usuario> getListaUser() {
		return listaUser;
	}

	public void setListaUser(List<Usuario> listaUser) {
		this.listaUser = listaUser;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public List<Rol> getListaRol() {
		return listaRol;
	}

	public void setListaRol(List<Rol> listaRol) {
		this.listaRol = listaRol;
	}

	public List<Genero> getListaGenero() {
		return listaGenero;
	}

	public void setListaGenero(List<Genero> listaGenero) {
		this.listaGenero = listaGenero;
	}

	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public Integer getIdGenero() {
		return idGenero;
	}

	public void setIdGenero(Integer idGenero) {
		this.idGenero = idGenero;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	

}
