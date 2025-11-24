package Controllers;

import Models.DataStructures.Administrador_Singleton;
import Models.Nodo_Producto;
import Models.Producto;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Component_ProductCard {
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private ImageView productImageView;
    
    @FXML
    private Label productNameLabel;
    
    @FXML
    private Label productPriceLabel;
    
    @FXML
    private Button addToBagButton;
    
    private Nodo_Producto nodoProducto;
    private Runnable onAddToBag;
    
    private boolean inicializado = false;
    
    @FXML
    public void initialize() {
        inicializado = true;
        
        // Si ya tenemos un producto asignado, cargar sus datos ahora que los campos están inicializados
        if (nodoProducto != null) {
            actualizarDatosProducto();
        }
    }
    
    public void setProducto(Nodo_Producto nodoProducto) {
        this.nodoProducto = nodoProducto;
        if (inicializado) {
            actualizarDatosProducto();
        }
    }
    
    private void actualizarDatosProducto() {
        if (nodoProducto != null && nodoProducto.getProducto() != null) {
            Producto producto = nodoProducto.getProducto();
            
            if (productNameLabel != null) {
                productNameLabel.setText(producto.getNombre());
            }
            
            if (productPriceLabel != null) {
                productPriceLabel.setText(String.format("$%.2f", producto.getPrecio()));
            }
            
            if (productImageView != null) {
                loadProductImage(producto);
            }
        }
    }
    
    public Nodo_Producto getProducto() {
        return nodoProducto;
    }
    
    public void setOnAddToBag(Runnable onAddToBag) {
        this.onAddToBag = onAddToBag;
    }
    
    public Node getRoot() {
        return rootPane;
    }
    
    private void loadProductImage(Producto producto) {
        try {
            if (producto == null || producto.getPersonaje() == null || producto.getIdentificacion() == null) {
                System.out.println("Error: Producto o información incompleta para cargar imagen");
                return;
            }
            
            String personajeNombre = producto.getPersonaje().getNombre();
            String identificacion = producto.getIdentificacion();
            
            // Extraer el número del producto del ID (formato: "nombrePersonaje-X")
            if (!identificacion.contains("-")) {
                System.out.println("Error: Formato de identificación inválido: " + identificacion);
                return;
            }
            
            String productNum = identificacion.substring(identificacion.lastIndexOf("-") + 1);
            String characterDirName = getCharacterDirectoryName(personajeNombre);
            
            String baseImagePath = System.getProperty("user.dir") + "\\src\\Images\\Personajes\\" + 
                                   characterDirName + "\\Products\\Product - " + productNum + "\\";
            
            // Lista de posibles nombres de archivo de imagen
            String[] imageNames = {
                "001-Product-" + productNum + ".jpg",
                "001-Product-" + productNum + ".JPG",
                "001-Product-" + productNum + ".jpeg",
                "001-Product-" + productNum + ".png",
                "Product-" + productNum + "-1.jpg",
                "001.jpg",
                "001.JPG",
                "001.png"
            };
            
            boolean found = false;
            for (String imageName : imageNames) {
                String imagePath = baseImagePath + imageName;
                File imageFile = new File(imagePath);
                if (imageFile.exists() && imageFile.isFile()) {
                    try {
                        Image image = new Image(imageFile.toURI().toString());
                        if (productImageView != null) {
                            productImageView.setImage(image);
                            found = true;
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Error al cargar imagen " + imagePath + ": " + e.getMessage());
                    }
                }
            }
            
            if (!found) {
                System.out.println("Imagen no encontrada para producto: " + producto.getNombre() + 
                                 " (Ruta buscada: " + baseImagePath + ")");
            }
        } catch (Exception e) {
            System.out.println("Error al cargar imagen del producto " + 
                             (producto != null ? producto.getNombre() : "desconocido") + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private String getCharacterDirectoryName(String nombrePersonaje) {
        switch (nombrePersonaje) {
            case "Hello Kitty":
                return "Hello kitty";
            case "Little Twin Stars":
                return "Little Twin Stars";
            case "My Melody":
                return "My Melody";
            case "Badtz-maru":
                return "Badtz-maru";
            default:
                return nombrePersonaje;
        }
    }
    
    @FXML
    private void handleAddToBag(ActionEvent event) {
        if (nodoProducto != null && nodoProducto.getProducto() != null) {            
            if (Administrador_Singleton.getAdministrador().getUsuarioActual() == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Sesión requerida", 
                    "Debes iniciar sesión para agregar productos al carrito.");
                return;
            }
            
            // Aquí se agregará la lógica para agregar al carrito
            // Por ahora solo notificamos
            if (onAddToBag != null) {
                onAddToBag.run();
            } else {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Producto agregado", 
                    "El producto se ha agregado al carrito.");
            }
        }
    }
    
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public static Component_ProductCard create(Nodo_Producto nodoProducto) {
        try {
            if (nodoProducto == null || nodoProducto.getProducto() == null) {
                System.out.println("ADVERTENCIA: Intento de crear ProductCard con producto nulo");
                return null;
            }
            
            FXMLLoader loader = new FXMLLoader(Component_ProductCard.class.getResource("/Views/Components/ProductCard.fxml"));
            Node root = loader.load();
            Component_ProductCard controller = loader.getController();
            
            if (controller != null) {
                // Establecer el producto después de que el FXML esté completamente cargado
                controller.setProducto(nodoProducto);
                return controller;
            } else {
                System.out.println("ADVERTENCIA: El controlador es nulo después de cargar el FXML");
                return null;
            }
        } catch (IOException e) {
            System.out.println("Error al crear ProductCard: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.println("Error inesperado al crear ProductCard: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}

