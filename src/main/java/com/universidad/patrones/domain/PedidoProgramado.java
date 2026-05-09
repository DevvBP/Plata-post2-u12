package com.universidad.patrones.domain;

public class PedidoProgramado extends Pedido {

    public PedidoProgramado(int cantidad, double precioUnitario) {
        super("PROGRAMADO", cantidad, precioUnitario);
    }
}
