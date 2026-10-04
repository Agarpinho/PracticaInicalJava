package controlador;

import modelo.*;

import java.io.*;
import java.util.Map;

public class GestorCSV {
    private static final String ARCHIVO_PRODUCTOS = "productos.csv";
    private static final String ARCHIVO_USUARIOS = "usuarios.csv";

    public static void generarCSV(Map<String, Producto> inventario) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_PRODUCTOS))) {
            writer.println("TIPO;ID;NOMBRE;PRECIO;STOCK;CATEGORIA;EXTRA1;EXTRA2");

            for (Producto p : inventario.values()) {
                if (p instanceof ProductoFisico) {
                    ProductoFisico pf = (ProductoFisico) p;
                    writer.println("FISICO;" + pf.getId() + ";" + pf.getNombre() + ";" +
                            pf.getPrecio() + ";" + pf.getStock() + ";" +
                            pf.getCategoria().name() + ";" + pf.getPesoKg() + ";" + pf.getGastosEnvio());
                } else if (p instanceof ProductoDigital) {
                    ProductoDigital pd = (ProductoDigital) p;
                    writer.println("DIGITAL;" + pd.getId() + ";" + pd.getNombre() + ";" +
                            pd.getPrecio() + ";" + pd.getStock() + ";" +
                            pd.getCategoria().name() + ";" + pd.getTamanoDescargaMB() + ";" + pd.getLicencia());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al guardar productos en el CSV: " + e.getMessage());
        }
    }

    public static void cargarProductos(Map<String, Producto> inventario) {
        File archivo = new File(ARCHIVO_PRODUCTOS);
        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea = reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length < 8) continue;

                String tipo = datos[0];
                String id = datos[1];
                String nombre = datos[2];
                double precio = Double.parseDouble(datos[3]);
                int stock = Integer.parseInt(datos[4]);
                Categoria categoria = Categoria.valueOf(datos[5]);

                if (tipo.equals("FISICO")) {
                    double peso = Double.parseDouble(datos[6]);
                    double envio = Double.parseDouble(datos[7]);
                    inventario.put(id, new ProductoFisico(id, nombre, precio, stock, categoria, peso, envio));
                } else if (tipo.equals("DIGITAL")) {
                    double mb = Double.parseDouble(datos[6]);
                    String licencia = datos[7];
                    inventario.put(id, new ProductoDigital(id, nombre, precio, stock, categoria, mb, licencia));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar productos desde el CSV: " + e.getMessage());
        }
    }

    public static void generarCSVUsuarios(Map<String, Usuario> usuarios) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_USUARIOS))) {
            writer.println("ROL;ID;NOMBRE;EMAIL;PASSWORD;ACTIVO;NIVEL_ACCESO");

            for (Usuario u : usuarios.values()) {
                if (u instanceof UsuarioAdmin) {
                    UsuarioAdmin admin = (UsuarioAdmin) u;
                    writer.println("ADMIN;" + admin.getId() + ";" + admin.getNombre() + ";" +
                            admin.getEmail() + ";" + admin.getPassword() + ";" +
                            admin.isActivo() + ";" + admin.getNivelAcceso());
                } else {
                    writer.println("CLIENTE;" + u.getId() + ";" + u.getNombre() + ";" +
                            u.getEmail() + ";" + u.getPassword() + ";" +
                            u.isActivo() + ";N/A");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios en el CSV: " + e.getMessage());
        }
    }

    public static void cargarUsuarios(Map<String, Usuario> usuarios) {
        File archivo = new File(ARCHIVO_USUARIOS);
        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea = reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length < 7) continue;

                String rol = datos[0];
                String id = datos[1];
                String nombre = datos[2];
                String email = datos[3];
                String password = datos[4];
                boolean activo = Boolean.parseBoolean(datos[5]);

                if (rol.equals("ADMIN")) {
                    String nivelAcceso = datos[6];
                    usuarios.put(id, new UsuarioAdmin(id, nombre, email, password, activo, nivelAcceso));
                } else {
                    usuarios.put(id, new Usuario(id, nombre, email, password, activo));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios desde el CSV: " + e.getMessage());
        }
    }
}