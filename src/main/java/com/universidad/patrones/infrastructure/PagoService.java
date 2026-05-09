package com.universidad.patrones.infrastructure;

import org.springframework.stereotype.Service;

@Service
public class PagoService {

    public boolean procesar(double total) {
        if (total <= 0) {
            System.out.println("[PAGO] Total invalido: " + total);
            return false;
        }
        System.out.println("[PAGO] Procesando cobro de $" + total);
        System.out.println("[PAGO] Cargo aplicado exitosamente.");
        return true;
    }

    public void reembolsar(double total) {
        System.out.println("[PAGO] Reembolso de $" + total + " iniciado.");
    }
}
