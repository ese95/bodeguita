package bodega.controller.admin;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import bodega.model.admin.ManagerBitacora;
import bodega.model.admin.ManagerBodega;
import bodega.model.admin.ManagerMovimiento;
import bodega.model.admin.ManagerProducto;
import bodega.model.entities.Bodega;
import bodega.model.entities.Producto;
import bodega.model.entities.Movimiento;
import bodega.model.entities.TipoDocumento;

import sun.misc.BASE64Encoder;

@Named
@SessionScoped
public class BeanMovimientos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Movimiento> listaMovimientos;
	private List<Bodega> listaBodega;
	private List<Producto> listaProducto;
	private Integer idDocumento;
	private Integer idBodega;
	private Integer idProducto;

	private UploadedFile uploadedFile;

	@EJB
	private ManagerMovimiento managerMovimiento;

	@EJB
	private ManagerBitacora managerbit;

	@EJB
	private ManagerProducto managerProducto;
	@EJB
	private ManagerBodega managerBodega;
	private Movimiento mov;

	@PostConstruct
	public void inicializar() {
		try {
			listaBodega = managerBodega.findAllBodega();
			listaMovimientos = managerMovimiento.findAllMovimientos();
			listaProducto = managerProducto.findAllProductos();
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}
		mov = new Movimiento();
	}

	public void actionListenerCargarMovimiento(Movimiento m) {
		try {

			mov = new Movimiento();
			Producto p = managerProducto.findByIdProducto(m.getProducto().getIdProducto());
			Bodega b = managerBodega.findByIdBodega(m.getBodega().getIdBodega());
			TipoDocumento d = managerMovimiento.findByIdTipoDocumento(m.getTipoDocumento().getIdDocumento());// puede
																												// dar
																												// error
																												// pilas
			mov.setFechaMovim(new Date());
			mov.setCantidadMovim(m.getCantidadMovim());
			mov.setComentario(m.getComentario());
			mov.setPrecioBaseMovim(m.getPrecioBaseMovim());
			mov.setCostoMovim(m.getCostoMovim());
			mov.setIdMovim(m.getIdMovim());
			mov.setBodega(b);
			mov.setProducto(p);
			mov.setTipoDocumento(d);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionListenerActualizarMovimiento() {
		try {

			if (mov.getCantidadMovim().toString().length() > 0) {

				Movimiento m = managerMovimiento.findByIdMovimiento(mov.getIdMovim());
				Producto p = managerProducto.findByIdProducto(mov.getProducto().getIdProducto());
				Bodega b = managerBodega.findByIdBodega(mov.getBodega().getIdBodega());
				TipoDocumento d = managerMovimiento.findByIdTipoDocumento(mov.getTipoDocumento().getIdDocumento());

				m.setFechaMovim(new Date());
				m.setCantidadMovim(mov.getCantidadMovim());
				m.setComentario(mov.getComentario());
				m.setPrecioBaseMovim(mov.getPrecioBaseMovim());
				m.setCostoMovim(mov.getCostoMovim());
				m.setBodega(b);
				m.setProducto(p);
				m.setTipoDocumento(d);

				managerMovimiento.actualizarMovimiento(m);
				listaMovimientos = managerMovimiento.findAllMovimientos();
				managerbit.crearEvento("actionListenerActualizarMovimiento()", "actualiza un movimineto ");

				JSFUtil.crearMensajeInfo("Actualizado con éxito");

			} else {
				JSFUtil.crearMensajeError("Debe ingresar todos los campos");
			}

		} catch (Exception e) {
			JSFUtil.crearMensajeError("Error al actualizar");
		}

	}

	@SuppressWarnings("unchecked")
	public void ObtenerProductos(Integer id) {
		listaProducto = new ArrayList<Producto>();
		try {
			System.out.println("El id " + id);
			if (id.intValue()!=0) {
				listaProducto = managerProducto.findByIdListaProd(id);
				for (Producto bodega : listaProducto) {
					System.out.println("prod -. " + bodega.getNombreProducto());
				}
			}
			
		} catch (Exception e) {
			System.out.println("ERRORRRRRRR : " + e.getMessage());
			e.printStackTrace();
		}

	}

	public void limpiarMovimiento() {

		mov.setFechaMovim(null);
		mov.setCantidadMovim(null);
		mov.setComentario("");
		mov.setPrecioBaseMovim(null);
		mov.setCostoMovim(null);

	}

	public void actionListenerInsertarMovimiento() {
		try {

			if (mov.getCantidadMovim().toString().length() > 0) {
				Movimiento m = new Movimiento();
				int cantidad = 0;
				// m.setFechaMovim(mov.getFechaMovim());
				Producto pro = managerProducto.findByIdProducto(idProducto);

				m.setComentario(mov.getComentario());
				m.setPrecioBaseMovim(mov.getPrecioBaseMovim());
				m.setCostoMovim(mov.getCostoMovim());
				m.setFechaMovim(new Date());
				m.setBodega(managerBodega.findByIdBodega(idBodega));

				m.setTipoDocumento(managerMovimiento.findByIdTipoDocumento(idDocumento));
				if (idDocumento == 1) {
					// factura -10
					m.setCantidadMovim(mov.getCantidadMovim());
					cantidad = pro.getCantidadStockProducto() - mov.getCantidadMovim();
					System.out.println("cantidad " + cantidad);
					if (cantidad >= 10) {
						pro.setCantidadStockProducto(cantidad);
						managerProducto.actualizarProducto(pro);
						m.setProducto(pro);

						managerMovimiento.insertarMovimiento(m);
						listaMovimientos = managerMovimiento.findAllMovimientos();
						limpiarMovimiento();
						managerbit.crearEvento("actionListenerInsertarMovimiento()",
								"actualiza un movimiento con documento ´factura´ ");
						JSFUtil.crearMensajeInfo("Insertado con éxito");

					} else {
						JSFUtil.crearMensajeError(
								"no se puede realizar la transacción: Debido a que la cantidad excede al STOCK minímo");
					}

				} else if (idDocumento == 2) {
					// orden compra +10
					m.setCantidadMovim(mov.getCantidadMovim());
					cantidad = pro.getCantidadStockProducto() + mov.getCantidadMovim();
					System.out.println("cantidad " + cantidad);
					pro.setCantidadStockProducto(cantidad);
					managerProducto.actualizarProducto(pro);
					m.setProducto(pro);

					managerMovimiento.insertarMovimiento(m);
					listaMovimientos = managerMovimiento.findAllMovimientos();
					limpiarMovimiento();

					JSFUtil.crearMensajeInfo("Insertado con éxito");
					managerbit.crearEvento("actionListenerInsertarMovimiento()",
							"actualiza un movimiento con documento ´orden de compra´ ");

				} else {
					// nota de venta -10
					m.setCantidadMovim(mov.getCantidadMovim());
					cantidad = pro.getCantidadStockProducto() - mov.getCantidadMovim();
					System.out.println("cantidad " + cantidad);
					if (cantidad >= 10) {
						pro.setCantidadStockProducto(cantidad);
						managerProducto.actualizarProducto(pro);
						m.setProducto(pro);

						managerMovimiento.insertarMovimiento(m);
						listaMovimientos = managerMovimiento.findAllMovimientos();
						limpiarMovimiento();

						JSFUtil.crearMensajeInfo("Insertado con éxito");
						managerbit.crearEvento("actionListenerInsertarMovimiento()",
								"actualiza un movimiento con documento 'Nota de venta´ ");

					} else {
						JSFUtil.crearMensajeError(
								"no se puede realizar la transacción: Debido a que la cantidad excede al STOCK minímo");
					}
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

	public void actionListenerEliminarMovimineto(Integer id) {
		try {
			managerMovimiento.eliminarMovimiento(id);
			listaMovimientos = managerMovimiento.findAllMovimientos();
			managerbit.crearEvento("actionListenerEliminarMovimineto()", "elimina un movimiento  ");

			JSFUtil.crearMensajeInfo("Su movimiento ha sido eliminado");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFUtil.crearMensajeError("Error al eliminar");
		}

	}

	public List<Movimiento> getListaMovimientos() {
		return listaMovimientos;
	}

	public void setListaMovimientos(List<Movimiento> listaMovimientos) {
		this.listaMovimientos = listaMovimientos;
	}

	public List<Bodega> getListaBodega() {
		return listaBodega;
	}

	public void setListaBodega(List<Bodega> listaBodega) {
		this.listaBodega = listaBodega;
	}

	public List<Producto> getListaProducto() {
		return listaProducto;
	}

	public void setListaProducto(List<Producto> listaProducto) {
		this.listaProducto = listaProducto;
	}

	public Integer getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}

	public Integer getIdBodega() {
		return idBodega;
	}

	public void setIdBodega(Integer idBodega) {
		this.idBodega = idBodega;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Movimiento getMov() {
		return mov;
	}

	public void setMov(Movimiento mov) {
		this.mov = mov;
	}

}
