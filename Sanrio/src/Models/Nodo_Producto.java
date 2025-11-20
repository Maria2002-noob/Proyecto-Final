package Models;

public class Nodo_Producto {
    
    private Nodo_Producto siguiente;
    private Producto producto;
    private Nodo_Producto anterior;

    public Nodo_Producto(Nodo_Producto siguiente, Producto producto, Nodo_Producto anterior) {
        this.siguiente = siguiente;
        this.producto = producto;
        this.anterior = anterior;
    }

    public Nodo_Producto getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo_Producto siguiente) {
        this.siguiente = siguiente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Nodo_Producto getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo_Producto anterior) {
        this.anterior = anterior;
    }        
}
