package Beans;

import java.io.Serializable;

public enum EstadoPedido implements Serializable {

    /**
     * Cuando el pedido esta tramitado por el cliente.
     */
    TRAMITADO,
    /**
     * Cuando el pedido ha sido preparado por el empleado.
     */
    PREPARADO,
    /**
     * Cuando el pedido ha sido enviado.
     */
    ENVIADO;

}
