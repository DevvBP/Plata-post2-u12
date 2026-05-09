package com.universidad.patrones.domain;

public class PedidoExpress extends Pedido {

    public PedidoExpress(int cantidad, double precioUnitario) {
        super("EXPRESS", cantidad, precioUnitario);
    }
}
