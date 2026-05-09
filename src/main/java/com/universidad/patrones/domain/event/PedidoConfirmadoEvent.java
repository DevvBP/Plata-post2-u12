package com.universidad.patrones.domain.event;

import com.universidad.patrones.domain.Pedido;
import org.springframework.context.ApplicationEvent;

public class PedidoConfirmadoEvent extends ApplicationEvent {

    private final Pedido pedido;
    private final double total;

    public PedidoConfirmadoEvent(Object source, Pedido pedido, double total) {
        super(source);
        this.pedido = pedido;
        this.total = total;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public double getTotal() {
        return total;
    }
}
