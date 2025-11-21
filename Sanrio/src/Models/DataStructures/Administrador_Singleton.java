package Models.DataStructures;

public class Administrador_Singleton {
    
    private static Administrador_Singleton administrador;
    private final Lista_Doble_Usuarios lista_usuarios;
    private final Pila_Stack_De_Productos pila_productos;

    public Administrador_Singleton() {
        this.lista_usuarios = new Lista_Doble_Usuarios();
        this.pila_productos = new Pila_Stack_De_Productos();
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

    public Pila_Stack_De_Productos getPila_productos() {
        return pila_productos;
    }        
}
