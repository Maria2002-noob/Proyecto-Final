package Models;

public class Nodo_Personaje {
    
    private Personaje personaje;
    private Nodo_Personaje siguiente;
    private Nodo_Personaje anterior;

    public Nodo_Personaje(Personaje personaje) {
        this.personaje = personaje;
        this.siguiente = null;
        this.anterior = null;
    }

    public Personaje getPersonaje() {
        return personaje;
    }

    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }

    public Nodo_Personaje getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo_Personaje siguiente) {
        this.siguiente = siguiente;
    }

    public Nodo_Personaje getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo_Personaje anterior) {
        this.anterior = anterior;
    }        
}
