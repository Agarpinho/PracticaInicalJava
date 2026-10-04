package vista;

import controlador.ControladorTienda;
import modelo.Categoria;
import modelo.Producto;
import modelo.ProductoDigital;
import modelo.ProductoFisico;

import java.util.List;
import java.util.Scanner;

public class ProductoVista {
    private ControladorTienda controller;
    private Scanner scanner;

    public ProductoVista(ControladorTienda controller, Scanner scanner) {
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

    public void mostrarMenuCatalogo() {
        System.out.println("\n--- CATALOGO DE PRODUCTOS ---");
        System.out.println("1. Listar todos los productos");
        System.out.println("2. Filtrar por Categoria");
        System.out.println("3. Filtrar por precio máximo");
        System.out.print("Seleccione: ");

        int opcion = Integer.parseInt(scanner.nextLine());

        if (opcion == 1) {
            System.out.println("\n-- Lista completa --");
            for (Producto p : controller.getInventario().values()) {
                System.out.println(p);
            }
        } else if (opcion == 2) {
            System.out.println("\nCategorías disponibles:");
            for (Categoria c : Categoria.values()) {
                System.out.println("- " + c.name() + ": " + c.getDescripcion());
            }
            System.out.print("Ingrese categoría: ");
            try {
                Categoria cat = Categoria.valueOf(scanner.nextLine().toUpperCase());
                List<Producto> filtrados = controller.filtrarPorCategoria(cat);
                for (Producto p : filtrados) {
                    System.out.println(p);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Categoría no válida.");
            }
        } else if (opcion == 3) {
            System.out.print("Ingrese precio máximo: ");
            double max = Double.parseDouble(scanner.nextLine());
            List<Producto> filtrados = controller.buscarPorPrecioMaximo(max);
            for (Producto p : filtrados) {
                System.out.println(p);
            }
        }
    }

    public void mostrarMenuAdminProductos() {
        System.out.println("\n--- GESTIÓN DE PRODUCTOS (ADMIN) ---");
        System.out.println("1. Alta de Producto");
        System.out.println("2. Baja de Producto");
        System.out.print("Seleccione: ");
        int opcion = Integer.parseInt(scanner.nextLine());

        if (opcion == 1) {
            System.out.print("ID: ");
            String id = scanner.nextLine();
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Precio base: ");
            double precio = Double.parseDouble(scanner.nextLine());
            System.out.print("Stock: ");
            int stock = Integer.parseInt(scanner.nextLine());

            System.out.println("Categoría (ELECTRONICA, ROPA, LIBROS, SOFTWARE, COMIDA):");
            Categoria cat = Categoria.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Tipo (1. Físico / 2. Digital): ");
            int tipo = Integer.parseInt(scanner.nextLine());

            Producto nuevoProducto;
            if (tipo == 1) {
                System.out.print("Peso (Kg): ");
                double peso = Double.parseDouble(scanner.nextLine());
                System.out.print("Gastos de envío: ");
                double envio = Double.parseDouble(scanner.nextLine());
                nuevoProducto = new ProductoFisico(id, nombre, precio, stock, cat, peso, envio);
            } else {
                System.out.print("MB Descarga: ");
                double mb = Double.parseDouble(scanner.nextLine());
                System.out.print("Licencia: ");
                String licencia = scanner.nextLine();
                nuevoProducto = new ProductoDigital(id, nombre, precio, stock, cat, mb, licencia);
            }

            if (controller.agregarProducto(nuevoProducto)) {
                System.out.println("Producto registrado correctamente.");
            } else {
                System.out.println("Error: El ID ya existe.");
            }

        } else if (opcion == 2) {
            System.out.print("ID a dar de baja: ");
            String idBorrar = scanner.nextLine();
            if (controller.eliminarProducto(idBorrar)) {
                System.out.println("Producto eliminado.");
            } else {
                System.out.println("Producto no encontrado.");
            }
        }
    }
}