package com.universidad.patrones.domain.strategy;

import com.universidad.patrones.domain.Pedido;

public interface DescuentoStrategy {

    double calcular(Pedido pedido);
}
