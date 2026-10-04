package modelo;

public class ProductoFisico extends Producto {
    private double pesoKg;
    private double gastosEnvio;

    public ProductoFisico(String id, String nombre, double precio, int stock, Categoria categoria, double pesoKg, double gastosEnvio) {
        super(id, nombre, precio, stock, categoria);
        this.pesoKg = pesoKg;
        this.gastosEnvio = gastosEnvio;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(double pesoKg) {
        this.pesoKg = pesoKg;
    }

    public double getGastosEnvio() {
        return gastosEnvio;
    }

    public void setGastosEnvio(double gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }

    @Override
    public double calcularPrecioFinal() {
        double precioConIva = getCategoria().aplicarImpuesto(getPrecio());
        return precioConIva + gastosEnvio;
    }

    @Override
    public String toString() {
        return super.toString() + " | Físico [Peso: " + pesoKg + "kg, Envío: " + gastosEnvio + "€]";
    }
}