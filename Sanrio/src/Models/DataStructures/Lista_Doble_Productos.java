package Models.DataStructures;

import Models.Nodo_Personaje;
import Models.Nodo_Producto;
import Models.Personaje;
import Models.Producto;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class Lista_Doble_Productos {

    private Nodo_Producto cabeza;

    public Lista_Doble_Productos() {
        this.cabeza = null;
    }

    public Nodo_Producto getCabeza() {
        return cabeza;
    }

    public void setCabeza(Nodo_Producto cabeza) {
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

    public Nodo_Producto buscarIdentificacion(String identificacion) {
        if (cabeza == null) {
            return null;
        } else {
            Nodo_Producto nodo = getCabeza();
            while (nodo != null) {
                if (nodo.getProducto().getIdentificacion() != null && 
                    nodo.getProducto().getIdentificacion().equals(identificacion)) {
                    return nodo;
                } else {
                    nodo = nodo.getSiguiente();
                }
            }
            return null;
        }
    }

    public ObservableList<Nodo_Producto> getProductos() {
        ObservableList<Nodo_Producto> todos = FXCollections.observableArrayList();
        if (cabeza == null) {
            return todos;
        }

        Nodo_Producto actual = cabeza;

        while (actual != null) {
            todos.add(actual);
            actual = actual.getSiguiente();
        }

        return todos;
    }

    public Nodo_Producto getUltimo() {

        if (cabeza == null) {
            return null;
        } else {
            Nodo_Producto aux = cabeza;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            return aux;
        }
    }

    public void agregarProducto(Producto producto) {
        if (producto == null) {
            return;
        }
        
        Nodo_Producto newProducto = new Nodo_Producto(producto);

        if (newProducto != null) {
            if (cabeza == null) {
                cabeza = newProducto;
            } else {
                Nodo_Producto ultimo = getUltimo();
                ultimo.setSiguiente(newProducto);
                newProducto.setAnterior(ultimo);
            }
        }
    }
    
    public void cargarProductosDesdeArchivos() {
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
        
        Lista_Doble_Personajes listaPersonajes = Administrador_Singleton.getAdministrador().getLista_personajes();
        
        // Verificar que los personajes estén cargados primero
        if (listaPersonajes.getCabeza() == null) {
            System.out.println("ADVERTENCIA: Los personajes no están cargados. Cargando personajes primero...");
            listaPersonajes.cargarPersonajesDesdeArchivos();
        }
        
        int productosCargados = 0;
        
        for (String nombrePersonajeDir : nombresPersonajes) {
            String productsPath = basePath + nombrePersonajeDir + "\\Products\\";
            Path productsDir = Paths.get(productsPath);
            
            if (Files.exists(productsDir) && Files.isDirectory(productsDir)) {
                try {
                    // Usar try-with-resources para cerrar el stream correctamente
                    List<Path> productDirs = new ArrayList<>();
                    try (Stream<Path> stream = Files.list(productsDir)) {
                        stream.filter(Files::isDirectory)
                              .filter(path -> path.getFileName().toString().startsWith("Product - "))
                              .forEach(productDirs::add);
                    }
                    
                    // Procesar cada directorio de producto
                    for (Path productDir : productDirs) {
                        try {
                            String dataPath = productDir.toString() + "\\Data.txt";
                            Path dataFile = Paths.get(dataPath);
                            
                            if (Files.exists(dataFile)) {
                                try (BufferedReader reader = new BufferedReader(new FileReader(dataFile.toFile()))) {
                                            
                                            String nombre = "";
                                            String categoriaStr = "";
                                            String descripcionIngles = "";
                                            String descripcionEspanol = "";
                                            float precio = 0.0f;
                                            
                                            String linea;
                                            while ((linea = reader.readLine()) != null) {
                                                if (linea.startsWith("nombre:")) {
                                                    nombre = linea.substring("nombre:".length()).trim();
                                                } else if (linea.startsWith("categoria:")) {
                                                    categoriaStr = linea.substring("categoria:".length()).trim();
                                                } else if (linea.startsWith("descripcionIngles:")) {
                                                    descripcionIngles = linea.substring("descripcionIngles:".length()).trim();
                                                } else if (linea.startsWith("descripcionEspanol:")) {
                                                    descripcionEspanol = linea.substring("descripcionEspanol:".length()).trim();
                                                } else if (linea.startsWith("precio:")) {
                                                    try {
                                                        String precioStr = linea.substring("precio:".length()).trim();
                                                        if (!precioStr.isEmpty()) {
                                                            precio = Float.parseFloat(precioStr);
                                                        }
                                                    } catch (NumberFormatException e) {
                                                        precio = 0.0f;
                                                    }
                                                }
                                            }
                                            
                                            // Validar que el producto tenga datos válidos (no vacío)
                                            if (!nombre.isEmpty() && nombre.length() > 0 && precio > 0) {
                                                // Extraer número del producto del nombre del directorio
                                                String productNumStr = productDir.getFileName().toString().replace("Product - ", "").trim();
                                                
                                                // Buscar el personaje leyendo su Data.txt primero
                                                String personajeDataPath = basePath + nombrePersonajeDir + "\\Data.txt";
                                                String nombrePersonaje = "";
                                                try (BufferedReader personajeReader = new BufferedReader(new FileReader(personajeDataPath))) {
                                                    String personajeLinea;
                                                    while ((personajeLinea = personajeReader.readLine()) != null) {
                                                        if (personajeLinea.startsWith("nombre:")) {
                                                            nombrePersonaje = personajeLinea.substring("nombre:".length()).trim();
                                                            break;
                                                        }
                                                    }
                                                } catch (IOException e) {
                                                    System.out.println("Error al leer personaje " + nombrePersonajeDir + ": " + e.getMessage());
                                                    nombrePersonaje = nombrePersonajeDir;
                                                }
                                                
                                                // Buscar el personaje por el nombre real
                                                Nodo_Personaje nodoPersonaje = listaPersonajes.buscarNombre(nombrePersonaje);
                                                
                                                // Si no se encuentra, intentar con el nombre del directorio
                                                if (nodoPersonaje == null) {
                                                    nodoPersonaje = listaPersonajes.buscarNombre(nombrePersonajeDir);
                                                }
                                                
                                                if (nodoPersonaje != null && nodoPersonaje.getPersonaje() != null) {
                                                    Personaje personaje = nodoPersonaje.getPersonaje();
                                                    
                                                    // Generar ID como "nombrePersonaje-X"
                                                    String identificacion = personaje.getNombre() + "-" + productNumStr;
                                                    
                                                    // Convertir categoría a array
                                                    String[] categoria = categoriaStr.isEmpty() ? new String[0] : 
                                                        categoriaStr.split(",");
                                                    for (int i = 0; i < categoria.length; i++) {
                                                        categoria[i] = categoria[i].trim();
                                                    }
                                                    
                                                    // Crear producto
                                                    Producto producto = new Producto(
                                                        personaje,
                                                        identificacion,
                                                        nombre,
                                                        categoria,
                                                        descripcionIngles,
                                                        descripcionEspanol,
                                                        precio
                                                    );
                                                    
                                                    agregarProducto(producto);
                                                    productosCargados++;
                                                } else {
                                                    System.out.println("ADVERTENCIA: No se encontró el personaje para el producto: " + nombre + " (Directorio: " + nombrePersonajeDir + ", Nombre buscado: " + nombrePersonaje + ")");
                                                }
                                            }
                                        } catch (IOException e) {
                                            System.out.println("Error al leer producto en " + dataPath + ": " + e.getMessage());
                                        }
                                    }
                            } catch (Exception e) {
                                System.out.println("Error al procesar directorio de producto " + productDir + ": " + e.getMessage());
                                e.printStackTrace();
                            }
                    }
                } catch (IOException e) {
                    System.out.println("Error al listar productos de " + nombrePersonajeDir + ": " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("ADVERTENCIA: Directorio de productos no encontrado: " + productsPath);
            }
        }
        
        System.out.println("Total de productos cargados: " + productosCargados);
    }
}
