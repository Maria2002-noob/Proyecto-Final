package Models.DataStructures;

import Models.Nodo_Personaje;
import Models.Personaje;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class Lista_Doble_Personajes {
    
    private Nodo_Personaje cabeza;

    public Lista_Doble_Personajes() {
        this.cabeza = null;
    }

    public Nodo_Personaje getCabeza() {
        return cabeza;
    }

    public void setCabeza(Nodo_Personaje cabeza) {
        this.cabeza = cabeza;
    }

    public void vaciarLista() {
        cabeza = null;
    }

    public void mostrarAlerta(Alert.AlertType alertType, String tit, String mj) {
        Alert a = new Alert(alertType);
        a.setTitle(tit);
        a.setContentText(mj);
        a.showAndWait();
    }

    public Nodo_Personaje buscarNombre(String nombre) {
        if (cabeza == null) {
            return null;
        } else {
            Nodo_Personaje nodo = getCabeza();
            while (nodo != null) {
                if (nodo.getPersonaje().getNombre().equalsIgnoreCase(nombre)) {
                    return nodo;
                } else {
                    nodo = nodo.getSiguiente();
                }
            }
            return null;
        }
    }

    public ObservableList<Nodo_Personaje> getPersonajes() {
        ObservableList<Nodo_Personaje> todos = FXCollections.observableArrayList();
        if (cabeza == null) {
            return todos;
        }

        Nodo_Personaje actual = cabeza;

        while (actual != null) {
            todos.add(actual);
            actual = actual.getSiguiente();
        }

        return todos;
    }     

    public Nodo_Personaje getUltimo() {

        if (cabeza == null) {
            return null;
        } else {
            Nodo_Personaje aux = cabeza;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            return aux;
        }
    }
    
    public void agregarUsuario(String nombre, String descripcionIngles, String descripcionEspanol, String color) {

        Personaje personaje = new Personaje(nombre, descripcionIngles, descripcionEspanol, color);  
        
        Nodo_Personaje newUser = new Nodo_Personaje(personaje);

        if (newUser != null) {
            if (cabeza == null) {
                cabeza = newUser;
            } else {
                Nodo_Personaje ultimo = getUltimo();
                ultimo.setSiguiente(newUser);
                newUser.setAnterior(ultimo);
            }
        }
    }
    
    public void cargarPersonajesDesdeArchivos() {
        String[] nombresPersonajes = {
            "Badtz-maru",
            "Chococat",
            "Cinnamoroll",
            "Hello kitty",
            "Keroppi",
            "Kuromi",
            "Little Twin Stars",
            "My Melody",
            "Pochacco",
            "Pompompurin"
        };
        
        String basePath = System.getProperty("user.dir") + "\\src\\Images\\Personajes\\";
        
        vaciarLista();
        
        for (String nombrePersonaje : nombresPersonajes) {
            try {
                String dataPath = basePath + nombrePersonaje + "\\Data.txt";
                Path archivo = Paths.get(dataPath);
                
                if (Files.exists(archivo)) {
                    BufferedReader reader = new BufferedReader(new FileReader(archivo.toFile()));
                    
                    String nombre = "";
                    String descripcionIngles = "";
                    String descripcionEspanol = "";
                    String color = "";
                    
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        if (linea.startsWith("nombre:")) {
                            nombre = linea.substring("nombre:".length()).trim();
                        } else if (linea.startsWith("descripcionIngles:")) {
                            descripcionIngles = linea.substring("descripcionIngles:".length()).trim();
                        } else if (linea.startsWith("descripcionEspanol:")) {
                            descripcionEspanol = linea.substring("descripcionEspanol:".length()).trim();
                        } else if (linea.startsWith("color:")) {
                            color = linea.substring("color:".length()).trim();
                        }
                    }
                    
                    reader.close();
                    
                    if (!nombre.isEmpty()) {
                        agregarUsuario(nombre, descripcionIngles, descripcionEspanol, color);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al cargar personaje " + nombrePersonaje + ": " + e.getMessage());
            }
        }
    }          
}
