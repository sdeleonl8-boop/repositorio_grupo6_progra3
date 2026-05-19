package transurbanobst;

class ArbolBinarioBusqueda {

    Nodo raiz;

    int saltos;

    // INSERTAR
    public void insertar(Usuario usuario) {

        raiz = insertarRecursivo(raiz, usuario);
    }

    private Nodo insertarRecursivo(
            Nodo actual,
            Usuario usuario) {

        if (actual == null) {

            return new Nodo(usuario);
        }

        if (usuario.noEmpleado.compareTo(
                actual.usuario.noEmpleado) < 0) {

            actual.izquierda =
                    insertarRecursivo(
                            actual.izquierda,
                            usuario
                    );
        }

        else if (usuario.noEmpleado.compareTo(
                actual.usuario.noEmpleado) > 0) {

            actual.derecha =
                    insertarRecursivo(
                            actual.derecha,
                            usuario
                    );
        }

        return actual;
    }

    // BUSCAR
    public Usuario buscar(String noEmpleado) {

        Nodo resultado =
                buscarRecursivo(raiz, noEmpleado);

        if (resultado != null) {
            return resultado.usuario;
        }

        return null;
    }

    private Nodo buscarRecursivo(
            Nodo actual,
            String noEmpleado) {

        if (actual == null) {
            return null;
        }

        if (noEmpleado.equals(
                actual.usuario.noEmpleado)) {

            return actual;
        }

        if (noEmpleado.compareTo(
                actual.usuario.noEmpleado) < 0) {

            return buscarRecursivo(
                    actual.izquierda,
                    noEmpleado
            );

        } else {

            return buscarRecursivo(
                    actual.derecha,
                    noEmpleado
            );
        }
    }

    // ELIMINAR
    public void eliminar(String noEmpleado) {

        raiz = eliminarRecursivo(
                raiz,
                noEmpleado
        );
    }

    private Nodo eliminarRecursivo(
            Nodo actual,
            String noEmpleado) {

        if (actual == null) {
            return null;
        }

        if (noEmpleado.compareTo(
                actual.usuario.noEmpleado) < 0) {

            actual.izquierda =
                    eliminarRecursivo(
                            actual.izquierda,
                            noEmpleado
                    );
        }

        else if (noEmpleado.compareTo(
                actual.usuario.noEmpleado) > 0) {

            actual.derecha =
                    eliminarRecursivo(
                            actual.derecha,
                            noEmpleado
                    );
        }

        else {

            if (actual.izquierda == null) {
                return actual.derecha;
            }

            else if (actual.derecha == null) {
                return actual.izquierda;
            }

            actual.usuario =
                    encontrarMinimo(
                            actual.derecha
                    );

            actual.derecha =
                    eliminarRecursivo(
                            actual.derecha,
                            actual.usuario.noEmpleado
                    );
        }

        return actual;
    }

    private Usuario encontrarMinimo(Nodo nodo) {

        Usuario minimo = nodo.usuario;

        while (nodo.izquierda != null) {

            minimo = nodo.izquierda.usuario;
            nodo = nodo.izquierda;
        }

        return minimo;
    }
}