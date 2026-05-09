package com.universidad.patrones.domain;

public abstract class Pedido {

    private final String tipo;
    private final int cantidad;
    private final double precioUnitario;

    protected Pedido(String tipo, int cantidad, double precioUnitario) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public String getTipo() {
        return tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getTotal() {
        return cantidad * precioUnitario;
    }

    @Override
    public String toString() {
        return "Pedido[" + tipo + ", cantidad=" + cantidad + ", precio=" + precioUnitario + "]";
    }
}
