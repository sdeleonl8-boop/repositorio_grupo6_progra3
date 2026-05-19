package Empresa;


class Usuario {
    String CDE;
    String puesto;
    String nombre;

    public Usuario(String CDE, String puesto, String nombre) {
        this.CDE = CDE;
        this.puesto = puesto;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "CDE: " + CDE +
               "\npuesto: " + puesto +
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

        // Comparar CDE
        if (usuario.CDE.compareTo(actual.usuario.CDE) < 0) {
            actual.izquierda = insertarRecursivo(actual.izquierda, usuario);
        } else if (usuario.CDE.compareTo(actual.usuario.CDE) > 0) {
            actual.derecha = insertarRecursivo(actual.derecha, usuario);
        }

        return actual;
    }

    // Buscar por CDE
    public Usuario buscar(String CDE) {
        Nodo resultado = buscarRecursivo(raiz, CDE
        );

        if (resultado != null) {
            return resultado.usuario;
        }

        return null;
    }

    private Nodo buscarRecursivo(Nodo actual, String CDE) {

        if (actual == null) {
            return null;
        }

        if (CDE.equals(actual.usuario.CDE)) {
            return actual;
        }

        if (CDE.compareTo(actual.usuario.CDE) < 0) {
            return buscarRecursivo(actual.izquierda, CDE);
        } else {
            return buscarRecursivo(actual.derecha, CDE);
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
    
    public void eliminar(String CDE) {
        raiz = eliminarRecursivo(raiz, CDE);
    }

    /**
     * Lógica recursiva para encontrar y eliminar el nodo.
     */
    private Nodo eliminarRecursivo(Nodo actual, String CDE) {
        if (actual == null) {
            return null; // El CDE no existe en el árbol
        }

        // 1. Navegar el árbol comparando el CDE
        if (CDE.compareTo(actual.usuario.CDE) < 0) {
            actual.izquierda = eliminarRecursivo(actual.izquierda, CDE);
        } else if (CDE.compareTo(actual.usuario.CDE) > 0) {
            actual.derecha = eliminarRecursivo(actual.derecha, CDE);
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
            actual.derecha = eliminarRecursivo(actual.derecha, actual.usuario.CDE);
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