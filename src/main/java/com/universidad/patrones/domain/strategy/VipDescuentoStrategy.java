package com.universidad.patrones.domain.strategy;

import com.universidad.patrones.domain.Pedido;
import org.springframework.stereotype.Component;

@Component
public class VipDescuentoStrategy implements DescuentoStrategy {

    @Override
    public double calcular(Pedido pedido) {
        int cantidad = pedido.getCantidad();
        double precio = pedido.getPrecioUnitario();
        if (cantidad > 10) {
            return precio * 0.30;
        }
        if (cantidad > 5) {
            return precio * 0.20;
        }
        return precio * 0.15;
    }
}
