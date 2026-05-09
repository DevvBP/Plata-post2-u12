package com.universidad.patrones.domain;

public class PedidoEstandar extends Pedido {

    public PedidoEstandar(int cantidad, double precioUnitario) {
        super("ESTANDAR", cantidad, precioUnitario);
    }
}
