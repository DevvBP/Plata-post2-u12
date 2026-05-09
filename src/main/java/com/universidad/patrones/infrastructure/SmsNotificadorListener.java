package com.universidad.patrones.infrastructure;

import com.universidad.patrones.domain.event.PedidoConfirmadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificadorListener {

    @EventListener
    public void onPedidoConfirmado(PedidoConfirmadoEvent evento) {
        System.out.println("[SMS] Pedido confirmado. Total: $" + evento.getTotal());
    }
}
