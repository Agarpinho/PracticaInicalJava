package vista;

import controlador.ControladorTienda;
import modelo.Usuario;

import java.util.Scanner;

public class UsuarioVista {
    private ControladorTienda controller;
    private Scanner scanner;

    public UsuarioVista(ControladorTienda controller, Scanner scanner) {
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

    public void cambiarUsuarioSesion() {
        System.out.println("\n--- USUARIOS REGISTRADOS ---");
        for (Usuario u : controller.getUsuarios().values()) {
            System.out.println(u);
        }

        System.out.print("Ingrese el ID del usuario al que desea cambiar: ");
        String id = scanner.nextLine();

        if (controller.cambiarSesionUsuario(id)) {
            System.out.println("Sesión cambiada con éxito.");
        } else {
            System.out.println("Error: El usuario no existe o está inactivo.");
        }
    }

    public void mostrarMenuAdminUsuarios() {
        System.out.println("\n--- GESTIÓN DE USUARIOS (ADMIN) ---");
        System.out.println("1. Alta Usuario Normal");
        System.out.println("2. Desactivar Usuario");
        System.out.print("Seleccione: ");
        int opcion = Integer.parseInt(scanner.nextLine());

        if (opcion == 1) {
            String id = controller.generarSiguienteIdUsuario();
            System.out.println("ID asignado automáticamente: " + id);
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Contraseña: ");
            String password = scanner.nextLine();

            Usuario nuevoUsuario = new Usuario(id, nombre, email, password, true);

            if (controller.agregarUsuario(nuevoUsuario)) {
                System.out.println("Usuario registrado con éxito.");
            } else {
                System.out.println("Error: ID duplicado.");
            }
        } else if (opcion == 2) {
            System.out.print("ID del usuario a desactivar: ");
            String idDesactivar = scanner.nextLine();
            if (controller.desactivarUsuario(idDesactivar)) {
                System.out.println("Usuario desactivado.");
            } else {
                System.out.println("Usuario no encontrado.");
            }
        }
    }
}