package transurbanobst;

class Usuario {

    String noEmpleado;
    String nombreCompleto;
    String puesto;

    public Usuario(String noEmpleado,
                   String nombreCompleto,
                   String puesto) {

        this.noEmpleado = noEmpleado;
        this.nombreCompleto = nombreCompleto;
        this.puesto = puesto;
    }

    @Override
    public String toString() {

        return "No. Empleado: " + noEmpleado
                + "\nNombre: " + nombreCompleto
                + "\nPuesto: " + puesto;
    }
}