package com.universidad.patrones.infrastructure;

import org.springframework.stereotype.Service;

@Service
public class InventarioService {

    private int stock = 100;

    public boolean reservar(int cantidad) {
        if (cantidad > stock) {
            System.out.println("[INVENTARIO] Stock insuficiente. Disponible: " + stock);
            return false;
        }
        stock -= cantidad;
        System.out.println("[INVENTARIO] " + cantidad + " unidades reservadas. Stock restante: " + stock);
        return true;
    }

    public void restaurar(int cantidad) {
        stock += cantidad;
        System.out.println("[INVENTARIO] " + cantidad + " unidades restauradas. Stock actual: " + stock);
    }
}
