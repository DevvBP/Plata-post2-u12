package com.universidad.patrones.domain;

// VIOLACION INTENCIONAL: el dominio importa el controlador, rompiendo la Regla 1 de ArchUnit
import com.universidad.patrones.controller.PedidoController;
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
