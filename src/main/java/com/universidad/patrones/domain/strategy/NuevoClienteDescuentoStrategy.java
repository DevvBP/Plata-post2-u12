package com.universidad.patrones.domain.strategy;

import com.universidad.patrones.domain.Pedido;
import org.springframework.stereotype.Component;

@Component
public class NuevoClienteDescuentoStrategy implements DescuentoStrategy {

    @Override
    public double calcular(Pedido pedido) {
        return pedido.getPrecioUnitario() * 0.05;
    }
}
