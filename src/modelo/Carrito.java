package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Carrito {
    private Map<Producto, Integer> productos = new HashMap<>();

    public Carrito() {}

    public Map<Producto, Integer> getProductos() {
        return productos;
    }

    public void setProductos(Map<Producto, Integer> productos) {
        this.productos = productos;
    }

    public boolean agregarProducto(Producto producto, int cantidad) {
        int cantidadActual = productos.getOrDefault(producto, 0);
        if (cantidadActual + cantidad > producto.getStock()) {
            return false;
        }
        productos.put(producto, cantidadActual + cantidad);
        return true;
    }

    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }

    public double calcularTotal() {
        double total = 0.0;

        ArrayList<Producto> listaProductos = new ArrayList<>(productos.keySet());

        for (int i = 0; i < listaProductos.size(); i++) {
            Producto prod = listaProductos.get(i);
            int cantidad = productos.get(prod);

            total += prod.calcularPrecioFinal() * cantidad;
        }

        return total;
    }

    public void vaciar() {
        productos.clear();
    }
}