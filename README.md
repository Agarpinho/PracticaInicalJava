# Práctica inicial Java OPTATIVA

Bienvenido al Sistema de Gestión de Tienda Online, una aplicación de consola desarrollada en Java puro y estructurada bajo el patrón de arquitectura MVC (Modelo-Vista-Controlador).

---

## Características Principales

* **Arquitectura MVC**: Separación estricta de responsabilidades dividida en paquetes independientes (`modelo`, `vista` y `controlador`) para un código modular y mantenible.
* **Control de Acceso y Roles**: Menús adaptativos según el perfil en sesión (Administrador o Cliente) con restricciones de seguridad para la alta de usuarios.
* **Modelado POO Avanzado**: Gestión de catálogo polimórfico (`ProductoFisico` y `ProductoDigital`) con cálculo dinámico de impuestos (IVA) y gastos de envío según la categoría.
* **Carrito y Procesamiento de Pedidos**: Funcionalidad completa de cesta de la compra que valida el stock en tiempo real y genera facturas detalladas por pantalla.
* **Persistencia en Ficheros CSV**: Carga y guardado automático de usuarios e inventario mediante lectura y escritura en archivos CSV delimitados por punto y coma.
* **Interfaz de Consola Formateada**: Experiencia de usuario mejorada en la terminal mediante el uso de códigos de color ANSI (resaltado verde unificado para la selección de opciones).

---

## Módulos y Extras Desarrollados

1. **Gestión Restringida de Usuarios (Seguridad por Rol)**:
   * El panel de administración permite dar de alta a nuevos usuarios registrados de tipo cliente con asignación automática de ID secuencial (`U100`, `U101`, etc.) y desactivar cuentas existentes.
   * **Restricción de seguridad**: Por diseño del sistema, un usuario Administrador no puede crear a otros administradores desde la interfaz.

2. **Gestión y Cambio Dinámico de Sesión**:
   * Posibilidad de cambiar de usuario activo en tiempo real verificando credenciales, estado del usuario (activo/inactivo) y refrescando el carrito correspondiente.

3. **Módulo de Estadísticas de Ventas**:
   * Análisis en tiempo real del historial de pedidos para obtener métricas clave del negocio:
     * **Producto más vendido**: Identifica el artículo con mayor volumen de unidades comercializadas.
     * **Ticket medio**: Calcula el valor promedio en euros por compra realizada.
     * **Cliente TOP**: Determina el usuario registrado con mayor número de pedidos completados.

4. **Control Automático y Descuento de Stock**:
   * Al procesar una compra, el sistema descuenta automáticamente las unidades adquiridas del inventario en memoria y actualiza la persistencia en el archivo `productos.csv`.

5. **Consultas Avanzadas con Java Stream API**:
   * Búsqueda y filtrado del catálogo por categoría específica (`ELECTRONICA`, `ROPA`, `LIBROS`, `SOFTWARE`, `COMIDA`) y filtrado por límite de precio máximo final.

---

## Estructura del Proyecto

```text
src/
├── Main.java
├── controlador/
│   ├── ControladorTienda.java
│   └── GestorCSV.java
├── modelo/
│   ├── Carrito.java
│   ├── Categoria.java
│   ├── Pedido.java
│   ├── Producto.java
│   ├── ProductoDigital.java
│   ├── ProductoFisico.java
│   ├── Usuario.java
│   └── UsuarioAdmin.java
└── vista/
    ├── CarritoVista.java
    ├── ProductoVista.java
    └── UsuarioVista.java
