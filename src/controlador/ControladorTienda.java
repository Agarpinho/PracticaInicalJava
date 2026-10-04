package controlador;

import modelo.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorTienda {
    private Map<String, Producto> inventario = new HashMap<>();
    private Map<String, Usuario> usuarios = new HashMap<>();
    private List<Pedido> historialPedidos = new ArrayList<>();

    private Usuario usuarioSesion;
    private Carrito carritoActual;
    private int siguienteIdUsuario = 100;

    public ControladorTienda() {
        cargarDatosIniciales();
        GestorCSV.cargarProductos(inventario);
        GestorCSV.cargarUsuarios(usuarios);
    }

    private void cargarDatosIniciales() {
        Usuario admin = new UsuarioAdmin("U01", "Admin General", "admin@tienda.com", "admin123", true, "ADMINISTRADOR");
        Usuario cliente = new Usuario("U10", "Aga", "aga@email.com", "1234", true);

        usuarios.put(admin.getId(), admin);
        usuarios.put(cliente.getId(), cliente);

        Producto p1 = new ProductoFisico("P01", "Jamón Ibérico", 45.0, 10, Categoria.COMIDA, 2.5, 5.0);
        Producto p2 = new ProductoFisico("P02", "Auriculares Bluetooth", 29.99, 15, Categoria.ELECTRONICA, 0.3, 3.5);
        Producto p3 = new ProductoFisico("P03", "Camiseta Algodón", 15.0, 20, Categoria.ROPA, 0.2, 2.0);
        Producto p4 = new ProductoDigital("P04", "Antivirus Anual", 19.99, 50, Categoria.SOFTWARE, 150.0, "Licencia-LaVerasSiNoTeDuermes");

        inventario.put(p1.getId(), p1);
        inventario.put(p2.getId(), p2);
        inventario.put(p3.getId(), p3);
        inventario.put(p4.getId(), p4);

        this.usuarioSesion = null;
        this.carritoActual = new Carrito();
    }

    public boolean autenticarUsuario(String id, String password) {
        Usuario u = usuarios.get(id);
        if (u != null && u.isActivo() && u.getPassword().equals(password)) {
            this.usuarioSesion = u;
            this.carritoActual = new Carrito();
            return true;
        }
        return false;
    }

    public boolean cambiarSesionUsuario(String id) {
        Usuario u = usuarios.get(id);
        if (u != null && u.isActivo()) {
            this.usuarioSesion = u;
            return true;
        }
        return false;
    }

    public Map<String, Producto> getInventario() {
        return inventario;
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public Carrito getCarritoActual() {
        return carritoActual;
    }

    public List<Pedido> getHistorialPedidos() {
        return historialPedidos;
    }

    public boolean agregarProducto(Producto p) {
        if (inventario.containsKey(p.getId())) return false;
        inventario.put(p.getId(), p);
        GestorCSV.generarCSV(inventario);
        return true;
    }

    public boolean eliminarProducto(String id) {
        boolean eliminado = inventario.remove(id) != null;
        if (eliminado) {
            GestorCSV.generarCSV(inventario);
        }
        return eliminado;
    }

    public String generarSiguienteIdUsuario() {
        return "U" + (siguienteIdUsuario++);
    }

    public boolean agregarUsuario(Usuario u) {
        if (usuarios.containsKey(u.getId())) return false;
        usuarios.put(u.getId(), u);
        GestorCSV.generarCSVUsuarios(usuarios);
        return true;
    }

    public boolean desactivarUsuario(String id) {
        Usuario u = usuarios.get(id);
        if (u != null) {
            u.setActivo(false);
            GestorCSV.generarCSVUsuarios(usuarios);
            return true;
        }
        return false;
    }

    public List<Producto> filtrarPorCategoria(Categoria cat) {
        return inventario.values().stream()
                .filter(p -> p.getCategoria() == cat)
                .toList();
    }

    public List<Producto> buscarPorPrecioMaximo(double max) {
        return inventario.values().stream()
                .filter(p -> p.calcularPrecioFinal() <= max)
                .toList();
    }

    public boolean procesarPedido() {
        if (usuarioSesion == null || carritoActual.getProductos().isEmpty()) {
            return false;
        }

        for (Map.Entry<Producto, Integer> entry : carritoActual.getProductos().entrySet()) {
            Producto p = entry.getKey();
            int cantidadComprada = entry.getValue();
            p.setStock(p.getStock() - cantidadComprada);
        }

        GestorCSV.generarCSV(inventario);

        Pedido nuevoPedido = new Pedido("PED-" + (historialPedidos.size() + 1), usuarioSesion, carritoActual);
        historialPedidos.add(nuevoPedido);
        this.carritoActual = new Carrito();
        return true;
    }

    public String obtenerProductoMasVendido() {
        if (historialPedidos.isEmpty()) {
            return "Sin ventas registradas";
        }

        Map<Producto, Integer> contador = new HashMap<>();

        for (Pedido p : historialPedidos) {
            for (Map.Entry<Producto, Integer> entry : p.getCarrito().getProductos().entrySet()) {
                Producto prod = entry.getKey();
                int cant = entry.getValue();
                int acumulado = contador.getOrDefault(prod, 0);
                contador.put(prod, acumulado + cant);
            }
        }

        Producto masVendido = null;
        int maxCantidad = 0;

        for (Map.Entry<Producto, Integer> entry : contador.entrySet()) {
            if (entry.getValue() > maxCantidad) {
                maxCantidad = entry.getValue();
                masVendido = entry.getKey();
            }
        }

        if (masVendido != null) {
            return masVendido.getNombre() + " (" + maxCantidad + " uds)";
        }
        return "Sin ventas registradas";
    }

    public double obtenerTicketMedio() {
        if (historialPedidos.isEmpty()) return 0.0;
        double total = 0;
        for (Pedido p : historialPedidos) {
            total += p.getTotal();
        }
        return total / historialPedidos.size();
    }

    public String obtenerUsuarioConMasPedidos() {
        if (historialPedidos.isEmpty()) {
            return "Sin pedidos registrados";
        }

        Map<Usuario, Integer> contador = new HashMap<>();

        for (Pedido p : historialPedidos) {
            Usuario u = p.getUsuario();
            int acumulado = contador.getOrDefault(u, 0);
            contador.put(u, acumulado + 1);
        }

        Usuario topUsuario = null;
        int maxPedidos = 0;

        for (Map.Entry<Usuario, Integer> entry : contador.entrySet()) {
            if (entry.getValue() > maxPedidos) {
                maxPedidos = entry.getValue();
                topUsuario = entry.getKey();
            }
        }

        if (topUsuario != null) {
            return topUsuario.getNombre() + " (" + maxPedidos + " pedidos)";
        }
        return "Sin pedidos registrados";
    }
}