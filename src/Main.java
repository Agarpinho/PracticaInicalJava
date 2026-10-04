import controlador.ControladorTienda;
import modelo.Pedido;
import modelo.Usuario;
import vista.CarritoVista;
import vista.ProductoVista;
import vista.UsuarioVista;

import java.util.List;
import java.util.Scanner;

public class Main {

    //COLORES
    public static final String RESET = "\u001B[0m";
    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AZUL = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";

    public static void main(String[] args) {
        ControladorTienda controller = new ControladorTienda();
        Scanner scanner = new Scanner(System.in);

        ProductoVista productoVista = new ProductoVista(controller, scanner);
        UsuarioVista usuarioVista = new UsuarioVista(controller, scanner);
        CarritoVista carritoVista = new CarritoVista(controller, scanner);

        int opcionInicio = -1;

        do {
            mostrarMenuInicio();
            if (scanner.hasNextInt()) {
                opcionInicio = Integer.parseInt(scanner.nextLine());

                if (opcionInicio == 1) {
                    if (iniciarSesion(controller, scanner)) {
                        ejecutarMenuApp(controller, scanner, productoVista, usuarioVista, carritoVista);
                    }
                } else if (opcionInicio != 0) {
                    System.out.println(ROJO + "Opción no válida." + RESET);
                }
            } else {
                System.out.println(ROJO + "Debe ingresar un número entero válido." + RESET);
                scanner.nextLine();
            }
        } while (opcionInicio != 0);

        System.out.println(AZUL + "\n¡Gracias por utilizar la aplicación! Hasta pronto." + RESET);
        scanner.close();
    }

    private static void mostrarMenuInicio() {
        System.out.println(AZUL + "\n-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_" + RESET);
        System.out.println(AZUL + "       BIENVENIDO A LA TIENDA     " + RESET);
        System.out.println(AZUL + "-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_" + RESET);
        System.out.println("1. Entrar a la aplicación");
        System.out.println("0. Salir de la aplicación");
        System.out.print(VERDE + "Seleccione una opción: " + RESET);
    }

    private static boolean iniciarSesion(ControladorTienda controller, Scanner scanner) {
        System.out.println(CYAN + "\n-_-_-_ INICIO DE SESIÓN -_-_-_" + RESET);
        System.out.print("Ingrese ID de usuario (Ej: U01 [Admin] / U10 [Cliente]): ");
        String idUsuario = scanner.nextLine();

        System.out.print("Ingrese contraseña (Ej: admin123 / 1234): ");
        String password = scanner.nextLine();

        if (controller.autenticarUsuario(idUsuario, password)) {
            Usuario u = controller.getUsuarioSesion();
            System.out.println(VERDE + "¡Bienvenido/a, " + u.getNombre() + "!" + RESET);
            return true;
        } else {
            System.out.println(ROJO + "Error: Usuario o contraseña incorrectos." + RESET);
            return false;
        }
    }

    private static void ejecutarMenuApp(ControladorTienda controller, Scanner scanner,
                                        ProductoVista productoVista, UsuarioVista usuarioVista, CarritoVista carritoVista) {
        int opcion = -1;
        do {
            Usuario usuario = controller.getUsuarioSesion();
            boolean esAdmin = usuario != null && usuario.esAdmin();

            if (esAdmin) {
                mostrarMenuAdmin(usuario);
            } else {
                mostrarMenuCliente(usuario);
            }

            if (scanner.hasNextInt()) {
                opcion = Integer.parseInt(scanner.nextLine());
                procesarOpcionApp(opcion, esAdmin, controller, scanner, productoVista, usuarioVista, carritoVista);
            } else {
                System.out.println(ROJO + "Debe ingresar un número entero válido." + RESET);
                scanner.nextLine();
            }

        } while (opcion != 0);
    }

    private static void mostrarMenuCliente(Usuario usuario) {
        System.out.println(ROJO + "\n-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_" + RESET);
        System.out.println(ROJO + "           MENÚ CLIENTE           " + RESET);
        System.out.println(ROJO + "-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_" + RESET);
        System.out.println(AZUL + "Usuario activo: " + usuario.getNombre() + " [CLIENTE]" + RESET);
        System.out.println("1. Ver catálogo de productos");
        System.out.println("2. Gestionar carrito");
        System.out.println("3. Procesar pedido / Generar factura");
        System.out.println("0. Cerrar sesión y volver al inicio");
        System.out.print(VERDE + "Seleccione una opción: " + RESET);
    }

    private static void mostrarMenuAdmin(Usuario usuario) {
        System.out.println(VERDE + "\n-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_" + RESET);
        System.out.println(VERDE + "       PANEL ADMINISTRADOR        " + RESET);
        System.out.println(VERDE + "-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_" + RESET);
        System.out.println(AZUL + "Usuario activo: " + usuario.getNombre() + " [ADMINISTRADOR]" + RESET);
        System.out.println("1. Ver catálogo de productos");
        System.out.println("2. Gestionar carrito");
        System.out.println("3. Procesar pedido / Generar factura");
        System.out.println("4. Gestión de productos (Alta/Baja)");
        System.out.println("5. Gestión de usuarios");
        System.out.println("6. Ver estadísticas de ventas");
        System.out.println("7. Historial global de pedidos");
        System.out.println("0. Cerrar sesión y volver al inicio");
        System.out.print(VERDE + "Seleccione una opción: " + RESET);
    }

    private static void procesarOpcionApp(int opcion, boolean esAdmin, ControladorTienda controller, Scanner scanner,
                                          ProductoVista productoVista, UsuarioVista usuarioVista, CarritoVista carritoVista) {

        if (opcion == 1) {
            productoVista.mostrarMenuCatalogo();
        } else if (opcion == 2) {
            carritoVista.mostrarMenuCarrito();
        } else if (opcion == 3) {
            if (controller.procesarPedido()) {
                List<Pedido> pedidos = controller.getHistorialPedidos();
                Pedido ultimo = pedidos.get(pedidos.size() - 1);
                System.out.println(CYAN + "\n" + ultimo.generarFactura() + RESET);
            } else {
                System.out.println(ROJO + "No se pudo procesar el pedido. Compruebe el carrito." + RESET);
            }
        } else if (esAdmin && opcion == 4) {
            productoVista.mostrarMenuAdminProductos();
        } else if (esAdmin && opcion == 5) {
            usuarioVista.mostrarMenuAdminUsuarios();
        } else if (esAdmin && opcion == 6) {
            System.out.println(VERDE + "\n-_-_-_ ESTADÍSTICAS -_-_-_" + RESET);
            System.out.println("Producto más vendido: " + controller.obtenerProductoMasVendido());
            System.out.println("Ticket medio: " + String.format("%.2f EUR", controller.obtenerTicketMedio()));
            System.out.println("Cliente con más pedidos: " + controller.obtenerUsuarioConMasPedidos());
        } else if (esAdmin && opcion == 7) {
            System.out.println(VERDE + "\n-_-_-_ HISTORIAL GLOBAL DE PEDIDOS -_-_-_" + RESET);
            for (Pedido p : controller.getHistorialPedidos()) {
                System.out.println(p.generarFactura());
            }
        } else if (opcion == 0) {
            System.out.println(AZUL + "Cerrando sesión..." + RESET);
            controller.setUsuarioSesion(null);
        } else {
            System.out.println(ROJO + "Opción no disponible para su rol." + RESET);
        }
    }
}