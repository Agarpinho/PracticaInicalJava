package modelo;

public enum Categoria {
    ELECTRONICA("Dispositivos electrónicos y gadgets", 0.21),
    ROPA("Prendas de vestir y accesorios", 0.21),
    LIBROS("Libros físicos y ebooks", 0.21),
    SOFTWARE("Licencias y contenido digital", 0.21),
    COMIDA("Productos de alimentación", 0.10);

    private final String descripcion;
    private final double tipoIva;

    Categoria(String descripcion, double tipoIva) {
        this.descripcion = descripcion;
        this.tipoIva = tipoIva;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getTipoIva() {
        return tipoIva;
    }

    public double aplicarImpuesto(double precioBase) {
        return precioBase * (1 + tipoIva);
    }
}