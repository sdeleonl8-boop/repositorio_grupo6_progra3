package Empresa;

class Nodo {
    Usuario usuario;
    Nodo izquierda;
    Nodo derecha;

    public Nodo(Usuario usuario) {
        this.usuario = usuario;
        izquierda = null;
        derecha = null;
    }
}