package modelo;

public class ProductoDigital extends Producto {
    private double tamanoDescargaMB;
    private String licencia;

    public ProductoDigital(String id, String nombre, double precio, int stock, Categoria categoria, double tamanoDescargaMB, String licencia) {
        super(id, nombre, precio, stock, categoria);
        this.tamanoDescargaMB = tamanoDescargaMB;
        this.licencia = licencia;
    }

    public double getTamanoDescargaMB() {
        return tamanoDescargaMB;
    }

    public void setTamanoDescargaMB(double tamanoDescargaMB) {
        this.tamanoDescargaMB = tamanoDescargaMB;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    @Override
    public double calcularPrecioFinal() {
        return getCategoria().aplicarImpuesto(getPrecio());
    }

    @Override
    public String toString() {
        return super.toString() + " | Digital [" + tamanoDescargaMB + " MB, Licencia: " + licencia + "]";
    }
}