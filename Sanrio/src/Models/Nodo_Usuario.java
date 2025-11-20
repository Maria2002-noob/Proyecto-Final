package Models;

public class Nodo_Usuario {
    
    private Nodo_Usuario siguiente;
    private Usuario usuario;
    private Nodo_Usuario anterior;

    public Nodo_Usuario(Usuario usuario) {
        this.siguiente = null;
        this.usuario = usuario;
        this.anterior = null;
    }

    public Nodo_Usuario getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo_Usuario siguiente) {
        this.siguiente = siguiente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Nodo_Usuario getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo_Usuario anterior) {
        this.anterior = anterior;
    }        
}
