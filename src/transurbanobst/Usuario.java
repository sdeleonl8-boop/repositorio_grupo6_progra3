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
    
    public void eliminar(String dpi) {
        raiz = eliminarRecursivo(raiz, dpi);
    }

    /**
     * Lógica recursiva para encontrar y eliminar el nodo.
     */
    private Nodo eliminarRecursivo(Nodo actual, String dpi) {
        if (actual == null) {
            return null; // El DPI no existe en el árbol
        }

        // 1. Navegar el árbol comparando el DPI
        if (dpi.compareTo(actual.usuario.dpi) < 0) {
            actual.izquierda = eliminarRecursivo(actual.izquierda, dpi);
        } else if (dpi.compareTo(actual.usuario.dpi) > 0) {
            actual.derecha = eliminarRecursivo(actual.derecha, dpi);
        } else {
            // ¡Nodo encontrado! Aplicar reglas de eliminación de BST

            // Caso A: El nodo no tiene hijos o solo tiene uno (Derecho)
            if (actual.izquierda == null) {
                return actual.derecha;
            } 
            // Caso B: El nodo solo tiene un hijo (Izquierdo)
            else if (actual.derecha == null) {
                return actual.izquierda;
            }

            // Caso C: El nodo tiene DOS hijos
            // Buscamos el sucesor inmediato (el nodo más pequeño en el subárbol derecho)
            actual.usuario = encontrarMinimo(actual.derecha);

            // Eliminamos el sucesor en el subárbol derecho
            actual.derecha = eliminarRecursivo(actual.derecha, actual.usuario.dpi);
        }

        return actual;
    }

    /**
     * Auxiliar para encontrar el nodo con el valor mínimo (el sucesor).
     */
    private Usuario encontrarMinimo(Nodo nodo) {
        Usuario min = nodo.usuario;
        while (nodo.izquierda != null) {
            min = nodo.izquierda.usuario;
            nodo = nodo.izquierda;
        }
        return min;
    }
}