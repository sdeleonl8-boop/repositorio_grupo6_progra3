package transurbanobst;


class Usuario {
    String dpi;
    String nit;
    String nombre;

    public Usuario(String dpi, String nit, String nombre) {
        this.dpi = dpi;
        this.nit = nit;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "DPI: " + dpi +
               "\nNIT: " + nit +
               "\nNombre: " + nombre;
    }
}


class ArbolBinarioBusqueda {

    Nodo raiz;

    // Insertar usuario
    public void insertar(Usuario usuario) {
        raiz = insertarRecursivo(raiz, usuario);
    }

    private Nodo insertarRecursivo(Nodo actual, Usuario usuario) {

        if (actual == null) {
            return new Nodo(usuario);
        }

        // Comparar DPI
        if (usuario.dpi.compareTo(actual.usuario.dpi) < 0) {
            actual.izquierda = insertarRecursivo(actual.izquierda, usuario);
        } else if (usuario.dpi.compareTo(actual.usuario.dpi) > 0) {
            actual.derecha = insertarRecursivo(actual.derecha, usuario);
        }

        return actual;
    }

    // Buscar por DPI
    public Usuario buscar(String dpi) {
        Nodo resultado = buscarRecursivo(raiz, dpi);

        if (resultado != null) {
            return resultado.usuario;
        }

        return null;
    }

    private Nodo buscarRecursivo(Nodo actual, String dpi) {

        if (actual == null) {
            return null;
        }

        if (dpi.equals(actual.usuario.dpi)) {
            return actual;
        }

        if (dpi.compareTo(actual.usuario.dpi) < 0) {
            return buscarRecursivo(actual.izquierda, dpi);
        } else {
            return buscarRecursivo(actual.derecha, dpi);
        }
    }

    // Mostrar en orden
    public void mostrarInOrden() {
        inOrden(raiz);
    }

    private void inOrden(Nodo nodo) {
        if (nodo != null) {
            inOrden(nodo.izquierda);
            System.out.println("-------------------");
            System.out.println(nodo.usuario);
            inOrden(nodo.derecha);
        }
    }
}