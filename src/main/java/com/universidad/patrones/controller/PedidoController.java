package com.universidad.patrones.controller;

import com.universidad.patrones.facade.ProcesamientoPedidoFacade;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final ProcesamientoPedidoFacade facade;

    public PedidoController(ProcesamientoPedidoFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/procesar")
    public String procesarPedido(
            @RequestParam String tipoPedido,
            @RequestParam String tipoCliente,
            @RequestParam int cantidad,
            @RequestParam double precio) {
        return facade.procesar(tipoPedido, tipoCliente, cantidad, precio);
    }
}
