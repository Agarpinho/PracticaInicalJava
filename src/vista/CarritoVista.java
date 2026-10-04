package vista;

import controlador.ControladorTienda;
import modelo.Carrito;
import modelo.Producto;

import java.util.Map;
import java.util.Scanner;

public class CarritoVista {
    private ControladorTienda controller;
    private Scanner scanner;

    public CarritoVista(ControladorTienda controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public ControladorTienda getController() {
        return controller;
    }

    public void setController(ControladorTienda controller) {
        this.controller = controller;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrarMenuCarrito() {
        System.out.println("\n--- GESTIÓN DEL CARRITO ---");
        System.out.println("1. Añadir producto al carrito");
        System.out.println("2. Quitar producto del carrito");
        System.out.println("3. Ver carrito actual");
        System.out.print("Seleccione: ");

        int opcion = Integer.parseInt(scanner.nextLine());
        Carrito carrito = controller.getCarritoActual();

        if (opcion == 1) {
            System.out.print("ID del producto: ");
            String id = scanner.nextLine();
            Producto p = controller.getInventario().get(id);

            if (p == null) {
                System.out.println("El producto no existe.");
                return;
            }

            System.out.print("Cantidad: ");
            int cantidad = Integer.parseInt(scanner.nextLine());

            if (carrito.agregarProducto(p, cantidad)) {
                System.out.println("Producto añadido al carrito.");
            } else {
                System.out.println("No hay suficiente stock.");
            }

        } else if (opcion == 2) {
            System.out.print("ID del producto a quitar: ");
            String idQuitar = scanner.nextLine();
            Producto pQuitar = controller.getInventario().get(idQuitar);

            if (pQuitar != null) {
                carrito.eliminarProducto(pQuitar);
                System.out.println("Producto eliminado del carrito.");
            } else {
                System.out.println("El producto no existe.");
            }

        } else if (opcion == 3) {
            System.out.println("\n--- CONTENIDO DEL CARRITO ---");
            Map<Producto, Integer> productos = carrito.getProductos();

            if (productos.isEmpty()) {
                System.out.println("El carrito está vacío.");
            } else {
                for (Map.Entry<Producto, Integer> linea : productos.entrySet()) {
                    Producto prod = linea.getKey();
                    int cant = linea.getValue();
                    double subtotal = prod.calcularPrecioFinal() * cant;
                    System.out.println(prod.getNombre() + " x" + cant + " = " + String.format("%.2f", subtotal) + " EUR");
                }
                System.out.println("-----------------------------");
                System.out.println("TOTAL: " + String.format("%.2f", carrito.calcularTotal()) + " EUR");
            }
        }
    }
}