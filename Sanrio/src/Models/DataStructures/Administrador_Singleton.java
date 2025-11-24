package Models.DataStructures;

import Models.Nodo_Usuario;

public class Administrador_Singleton {
    
    private static Administrador_Singleton administrador;
    private final Lista_Doble_Usuarios lista_usuarios;
    private final Lista_Doble_Personajes lista_personajes;    
    private final Lista_Doble_Productos lista_productos;        
    private final Pila_Stack_De_Productos pila_productos;
    private Nodo_Usuario usuarioActual;
    
    public Administrador_Singleton() {
        this.lista_usuarios = new Lista_Doble_Usuarios();
        this.lista_personajes = new Lista_Doble_Personajes();
        this.lista_productos = new Lista_Doble_Productos();
        this.pila_productos = new Pila_Stack_De_Productos();
        this.usuarioActual = null;
    }

    public static Administrador_Singleton getAdministrador() {
        if (administrador == null) {
            administrador = new Administrador_Singleton();           
        }
        return administrador;
    }

    public Lista_Doble_Usuarios getLista_usuarios() {
        return lista_usuarios;
    }

    public Lista_Doble_Personajes getLista_personajes() {
        return lista_personajes;
    }

    public Lista_Doble_Productos getLista_productos() {
        return lista_productos;
    }
    
    public Pila_Stack_De_Productos getPila_productos() {
        return pila_productos;
    }
    
    public Nodo_Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    public void setUsuarioActual(Nodo_Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    
    public void inicializarDatos() {
        lista_personajes.cargarPersonajesDesdeArchivos();
        lista_productos.cargarProductosDesdeArchivos();
    }
}
