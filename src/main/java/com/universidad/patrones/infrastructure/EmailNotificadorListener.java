package com.universidad.patrones.infrastructure;

import com.universidad.patrones.domain.event.PedidoConfirmadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificadorListener {

    @EventListener
    public void onPedidoConfirmado(PedidoConfirmadoEvent evento) {
        System.out.println("[EMAIL] Pedido " + evento.getPedido().getTipo()
                + " confirmado. Total: $" + evento.getTotal());
    }
}
