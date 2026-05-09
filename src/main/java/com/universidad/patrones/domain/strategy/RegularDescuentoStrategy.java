package com.universidad.patrones.domain.strategy;

import com.universidad.patrones.domain.Pedido;
import org.springframework.stereotype.Component;

@Component
public class RegularDescuentoStrategy implements DescuentoStrategy {

    @Override
    public double calcular(Pedido pedido) {
        int cantidad = pedido.getCantidad();
        double precio = pedido.getPrecioUnitario();
        if (cantidad > 20) {
            return precio * 0.10;
        }
        if (cantidad > 10) {
            return precio * 0.05;
        }
        return 0;
    }
}
