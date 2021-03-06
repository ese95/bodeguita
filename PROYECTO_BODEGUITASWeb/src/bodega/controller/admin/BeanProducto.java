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

import bodega.model.admin.ManagerProducto;
import bodega.model.admin.ManagerMedida;
import bodega.model.admin.ManagerBitacora;
import bodega.model.admin.ManagerBodega;
import bodega.model.admin.ManagerCategoria;
import bodega.model.entities.Producto;
import bodega.model.entities.Bodega;
import bodega.model.entities.Categoria;
import bodega.model.entities.Medida;
import sun.misc.BASE64Encoder;

@Named
@SessionScoped
public class BeanProducto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Producto> listaProducto;
	private List<Medida> listaMedida;
	private List<Categoria> listaCategoria;
	private List<Bodega>listaBodega;
	private Integer idBodega;
	private Integer idCategoria;
	private Integer idMedida;
	private UploadedFile uploadedFile;

	@EJB
	private ManagerProducto managerProd;
	@EJB
	private ManagerCategoria managerCat;
	@EJB
	private ManagerMedida managerMed;
	
	@EJB
	private ManagerBitacora managerbit;
	
	@EJB
	private ManagerBodega managerBodega;
	
	private Producto prod;

	@PostConstruct
	public void inicializar() {
		try {
			listaProducto = managerProd.findAllProductos();
			listaCategoria = managerCat.findAllCategorias();
			listaMedida = managerMed.findAllMedidas();
			listaBodega=managerBodega.findAllBodega();
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}
		prod = new Producto();
	}

	public void actionListenerCargarProducto(Producto p) {
		try {
			prod = new Producto();
			Medida med = managerMed.findByIdMedida(p.getMedida().getIdMedida());
			Categoria cat = managerCat.findByIdCategoria(p.getCategoria().getIdCategoria());
			Bodega bod=managerBodega.findByIdBodega(p.getBodega().getIdBodega());
			prod.setCantidadStockProducto(p.getCantidadStockProducto());
			prod.setCaracteristicasProducto(p.getCaracteristicasProducto());
			idCategoria = p.getCategoria().getIdCategoria();
			idMedida = p.getMedida().getIdMedida();
			idBodega=p.getBodega().getIdBodega();
			System.out.println("idbodega "+idBodega);
			prod.setIdProducto(p.getIdProducto());
			prod.setCategoria(cat);
			prod.setCostoProducto(p.getCostoProducto());
			prod.setDescripcionProducto(p.getDescripcionProducto());
			prod.setEstadoProducto(p.getEstadoProducto());
			prod.setImagenProducto(p.getImagenProducto());
			prod.setMedida(p.getMedida());
			prod.setNombreProducto(p.getNombreProducto());
			prod.setPrecioBaseProducto(p.getPrecioBaseProducto());
			prod.setBodega(p.getBodega());
			System.out.println("bodega "+prod.getBodega().getNombreBodega());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionListenerActualizarProducto() {
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

				InputStream inputstream = new FileInputStream("d:\\foto\\productos.png");

				image = ImageIO.read(inputstream);
			}

			if (prod.getNombreProducto().length() > 0 && prod.getPrecioBaseProducto().toString().length() > 0
					&& prod.getCostoProducto().toString().length() > 0
					&& prod.getEstadoProducto().toString().length() > 0
					&& prod.getCantidadStockProducto().toString().length() > 0) {
				if (prod.getPrecioBaseProducto().doubleValue() < prod.getCostoProducto().doubleValue()) {

					String cod64 = encodeToString(image, "png");

					Producto p = managerProd.findByIdProducto(prod.getIdProducto());
					p.setCantidadStockProducto(prod.getCantidadStockProducto());
					p.setCaracteristicasProducto(prod.getCaracteristicasProducto());
					p.setCostoProducto(prod.getCostoProducto());
					p.setDescripcionProducto(prod.getDescripcionProducto());
					p.setEstadoProducto(prod.getEstadoProducto());
					p.setImagenProducto(cod64);
					p.setCategoria(managerCat.findByIdCategoria(idCategoria));
					p.setBodega(managerBodega.findByIdBodega(idBodega));
					System.out.println("boddd"+p.getBodega().getNombreBodega());
					p.setMedida(managerMed.findByIdMedida(idMedida));
					p.setNombreProducto(prod.getNombreProducto());
					p.setPrecioBaseProducto(prod.getPrecioBaseProducto());

					managerProd.actualizarProducto(p);
					managerbit.crearEvento("actionListenerActualizarProducto()", "Actualiza un producto ");
					listaProducto = managerProd.findAllProductos();

					JSFUtil.crearMensajeInfo("Actualizado con �xito");
				} else {
					JSFUtil.crearMensajeError("El costo debe ser mayor que el precio base");
				}
			} else {
				JSFUtil.crearMensajeError("Debe ingresar todos los campos");
			}

		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al actualizar");
		}

	}

	public void limpiarProducto() {
		prod.setCantidadStockProducto(null);
		prod.setCaracteristicasProducto("");
		prod.setCategoria(null);
		prod.setCostoProducto(null);
		prod.setDescripcionProducto("");
		prod.setEstadoProducto(null);
		prod.setImagenProducto("");
		prod.setMedida(null);
		prod.setNombreProducto("");
		prod.setPrecioBaseProducto(null);
		prod.setBodega(null);
	}

	public void actionListenerInsertarProducto() {
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

				InputStream inputstream = new FileInputStream("d:\\foto\\productos.png");

				image = ImageIO.read(inputstream);
			}

			if (prod.getNombreProducto().length() > 0 && prod.getPrecioBaseProducto().toString().length() > 0
					&& prod.getCostoProducto().toString().length() > 0
					&& prod.getEstadoProducto().toString().length() > 0
					&& prod.getCantidadStockProducto().toString().length() > 0) {

				if (prod.getPrecioBaseProducto().doubleValue() < prod.getCostoProducto().doubleValue()) {
					String cod64 = encodeToString(image, "png");
					Producto p = new Producto();
					p.setCantidadStockProducto(prod.getCantidadStockProducto());
					p.setCaracteristicasProducto(prod.getCaracteristicasProducto());
					p.setCostoProducto(prod.getCostoProducto());
					p.setDescripcionProducto(prod.getDescripcionProducto());
					p.setEstadoProducto(prod.getEstadoProducto());
					p.setImagenProducto(cod64);
					p.setCategoria(managerCat.findByIdCategoria(idCategoria));
					p.setMedida(managerMed.findByIdMedida(idMedida));
					p.setBodega(managerBodega.findByIdBodega(idBodega));
					p.setNombreProducto(prod.getNombreProducto());
					p.setPrecioBaseProducto(prod.getPrecioBaseProducto());

					managerProd.insertarUsuario(p);
					listaProducto = managerProd.findAllProductos();
					limpiarProducto();
					managerbit.crearEvento("actionListenerInsertarProducto()", "Inserta un producto ");
					/*
					 * Genero gen=managerGenero.findByIdGeneros(idGenero); u.setGenero(gen); Rol
					 * rol=managerRol.findByIdRol(idRol); u.setImagenUsuario(cod64);
					 * u.setNombreUsuario(user.getNombreUsuario());
					 * 
					 * u.setTelefonoUsuario(user.getTelefonoUsuario());
					 * managerUser.insertarUsuario(u); listaUser=managerUser.findAllUsuarios();
					 * limpiarUsuario();
					 */
					JSFUtil.crearMensajeInfo("Insertado con �xito");

				} else {
					JSFUtil.crearMensajeError("El precio costo debe ser mayor que el precio base");
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

	public void actionListenerEliminarProducto(Integer id) {
		try {

			managerProd.eliminarProducto(id);
			listaProducto = managerProd.findAllProductos();
			managerbit.crearEvento("actionListenerEliminarProducto()", "Elimina un producto ");
			JSFUtil.crearMensajeInfo("Su producto ha sido eliminado");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al eliminar");
		}

	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public List<Producto> getListaProducto() {
		return listaProducto;
	}

	public void setListaProducto(List<Producto> listaProducto) {
		this.listaProducto = listaProducto;
	}

	public List<Medida> getListaMedida() {
		return listaMedida;
	}

	public void setListaMedida(List<Medida> listaMedida) {
		this.listaMedida = listaMedida;
	}

	public List<Categoria> getListaCategoria() {
		return listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Integer getIdMedida() {
		return idMedida;
	}

	public void setIdMedida(Integer idMedida) {
		this.idMedida = idMedida;
	}

	public Producto getProd() {
		return prod;
	}

	public void setProd(Producto prod) {
		this.prod = prod;
	}

	public List<Bodega> getListaBodega() {
		return listaBodega;
	}

	public void setListaBodega(List<Bodega> listaBodega) {
		this.listaBodega = listaBodega;
	}

	public Integer getIdBodega() {
		return idBodega;
	}

	public void setIdBodega(Integer idBodega) {
		this.idBodega = idBodega;
	}
	

}
