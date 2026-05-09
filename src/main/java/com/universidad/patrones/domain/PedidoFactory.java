package com.universidad.patrones.domain;

import org.springframework.stereotype.Component;

@Component
public class PedidoFactory {

    public Pedido crear(String tipoPedido, int cantidad, double precioUnitario) {
        return switch (tipoPedido.toUpperCase()) {
            case "EXPRESS"    -> new PedidoExpress(cantidad, precioUnitario);
            case "PROGRAMADO" -> new PedidoProgramado(cantidad, precioUnitario);
            default           -> new PedidoEstandar(cantidad, precioUnitario);
        };
    }
}
