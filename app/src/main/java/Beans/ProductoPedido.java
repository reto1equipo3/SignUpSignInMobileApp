/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Entidad clase JPA para datos relacionados entre pedido y productos.
 *
 * @author Leticia Garcia
 */
@XmlRootElement(name = "productos")
public class ProductoPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    private Pedido pedido;
    private ProductBean producto;
    private Integer cantidad;
    private Double precio;

    //Constructor
    public ProductoPedido() {
        this.pedido = new Pedido();
        this.producto = new ProductBean();
        // this.producto= new SimpleObjectProperty<>();
        this.cantidad = new Integer(0);
        this.precio = new Double(0.0);
        this.setPrecio(getCantidad() * this.getProducto().getPrecioUnidad());
    }

    //Getters and setters
    public ProductBean getProducto() {
        return producto;
    }

    public void setProducto(ProductBean producto) {
        this.setPrecio(getCantidad() * producto.getPrecioUnidad());
        this.producto = producto;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        this.setPrecio(cantidad * this.getProducto().getPrecioUnidad());

    }

    public Double getPrecio() {
        return this.precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {

        return precio.toString();
    }

}
