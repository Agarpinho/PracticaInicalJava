package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Pedido {
    private String id;
    private Usuario usuario;
    private Carrito carrito;
    private LocalDateTime fecha;
    private double total;

    public Pedido(String id, Usuario usuario, Carrito carrito) {
        this.id = id;
        this.usuario = usuario;
        this.carrito = carrito;
        this.fecha = LocalDateTime.now();
        this.total = carrito.calcularTotal();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
        this.total = carrito.calcularTotal();
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }

    public String generarFactura() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        StringBuilder sb = new StringBuilder();

        sb.append("=========================================\n");
        sb.append("             FACTURA DE COMPRA           \n");
        sb.append("=========================================\n");
        sb.append("Nº Pedido: ").append(id).append("\n");
        sb.append("Fecha: ").append(fecha.format(formatter)).append("\n");
        sb.append("Cliente: ").append(usuario.getNombre()).append(" (").append(usuario.getEmail()).append(")\n");
        sb.append("-----------------------------------------\n");
        sb.append("PRODUCTOS:\n");

        if (carrito != null && !carrito.getProductos().isEmpty()) {
            for (Map.Entry<Producto, Integer> entry : carrito.getProductos().entrySet()) {
                Producto prod = entry.getKey();
                int cantidad = entry.getValue();
                double subtotal = prod.calcularPrecioFinal() * cantidad;

                sb.append(String.format(" - %s x%d : %.2f EUR (%.2f EUR/ud)\n",
                        prod.getNombre(), cantidad, subtotal, prod.calcularPrecioFinal()));
            }
        } else {
            sb.append(" (Sin productos)\n");
        }

        sb.append("-----------------------------------------\n");
        sb.append(String.format("TOTAL FACTURA: %.2f EUR\n", total));
        sb.append("=========================================\n");

        return sb.toString();
    }

    @Override
    public String toString() {
        return "Pedido ID: " + id + " | Cliente: " + usuario.getNombre() + " | Total: " + String.format("%.2f EUR", total);
    }
}