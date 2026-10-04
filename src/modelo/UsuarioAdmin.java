package modelo;

public class UsuarioAdmin extends Usuario {
    private String nivelAcceso;

    public UsuarioAdmin(String id, String nombre, String email, String password, boolean activo, String nivelAcceso) {
        super(id, nombre, email, password, activo);
        this.nivelAcceso = nivelAcceso;
    }

    public String getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(String nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    @Override
    public boolean esAdmin() {
        return true;
    }

    @Override
    public String toString() {
        return "ID: " + getId() + " | Nombre: " + getNombre() + " | Email: " + getEmail() +
                " | Rol: ADMINISTRADOR (" + nivelAcceso + ") | Estado: " + (isActivo() ? "Activo" : "Inactivo");
    }
}