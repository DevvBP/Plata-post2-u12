package com.universidad.patrones.facade;

import com.universidad.patrones.domain.Pedido;
import com.universidad.patrones.domain.PedidoFactory;
import com.universidad.patrones.domain.event.PedidoConfirmadoEvent;
import com.universidad.patrones.domain.strategy.DescuentoStrategy;
import com.universidad.patrones.domain.strategy.NuevoClienteDescuentoStrategy;
import com.universidad.patrones.domain.strategy.RegularDescuentoStrategy;
import com.universidad.patrones.domain.strategy.VipDescuentoStrategy;
import com.universidad.patrones.infrastructure.InventarioService;
import com.universidad.patrones.infrastructure.PagoService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ProcesamientoPedidoFacade {

    private final PedidoFactory factory;
    private final VipDescuentoStrategy vipStrategy;
    private final RegularDescuentoStrategy regularStrategy;
    private final NuevoClienteDescuentoStrategy nuevoClienteStrategy;
    private final InventarioService inventarioService;
    private final PagoService pagoService;
    private final ApplicationEventPublisher publisher;

    public ProcesamientoPedidoFacade(
            PedidoFactory factory,
            VipDescuentoStrategy vipStrategy,
            RegularDescuentoStrategy regularStrategy,
            NuevoClienteDescuentoStrategy nuevoClienteStrategy,
            InventarioService inventarioService,
            PagoService pagoService,
            ApplicationEventPublisher publisher) {
        this.factory = factory;
        this.vipStrategy = vipStrategy;
        this.regularStrategy = regularStrategy;
        this.nuevoClienteStrategy = nuevoClienteStrategy;
        this.inventarioService = inventarioService;
        this.pagoService = pagoService;
        this.publisher = publisher;
    }

    public String procesar(String tipoPedido, String tipoCliente, int cantidad, double precio) {
        Pedido pedido = factory.crear(tipoPedido, cantidad, precio);

        DescuentoStrategy estrategia = resolverEstrategia(tipoCliente);
        double descuento = estrategia.calcular(pedido);
        double total = pedido.getTotal() - descuento;

        if (!inventarioService.reservar(cantidad)) {
            return "ERROR: Stock insuficiente";
        }

        if (!pagoService.procesar(total)) {
            inventarioService.restaurar(cantidad);
            return "ERROR: Pago fallido";
        }

        publisher.publishEvent(new PedidoConfirmadoEvent(this, pedido, total));

        return "OK | " + pedido + " | Total: $" + total + " | Descuento: $" + descuento;
    }

    private DescuentoStrategy resolverEstrategia(String tipoCliente) {
        return switch (tipoCliente.toUpperCase()) {
            case "VIP"   -> vipStrategy;
            case "NUEVO" -> nuevoClienteStrategy;
            default      -> regularStrategy;
        };
    }
}
