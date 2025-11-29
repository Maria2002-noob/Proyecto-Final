package Controllers;

import Models.DataStructures.Administrador_Singleton;
import Models.DataStructures.LanguageManager;
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
import javafx.scene.input.MouseEvent;
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
    private LanguageManager languageManager;
    
    private boolean inicializado = false;
    private Image primeraImagen;
    private Image segundaImagen;
    private String baseImagePath;
    private String productNum;
    
    public void initialize() {
        inicializado = true;
        languageManager = LanguageManager.getInstance();
                
        actualizarTextoBoton();
                
        if (nodoProducto != null) {
            actualizarDatosProducto();
        }
    }
    
    private void actualizarTextoBoton() {
        if (addToBagButton != null && languageManager != null) {
            addToBagButton.setText(languageManager.getString("button.add.to.bag"));
        }
    }
    
    public void actualizarPrecio() {
        if (nodoProducto != null && nodoProducto.getProducto() != null && productPriceLabel != null) {
            if (languageManager == null) {
                languageManager = LanguageManager.getInstance();
            }
            productPriceLabel.setText(languageManager.formatPrice(nodoProducto.getProducto().getPrecio()));
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
                if (languageManager == null) {
                    languageManager = LanguageManager.getInstance();
                }
                productPriceLabel.setText(languageManager.formatPrice(producto.getPrecio()));
            }
            
            if (productImageView != null) {
                loadProductImage(producto);
                configurarHoverImagen();
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
                        
            if (!identificacion.contains("-")) {
                System.out.println("Error: Formato de identificación inválido: " + identificacion);
                return;
            }
            
            productNum = identificacion.substring(identificacion.lastIndexOf("-") + 1);
            String characterDirName = getCharacterDirectoryName(personajeNombre);
            
            baseImagePath = System.getProperty("user.dir") + "\\src\\Images\\Personajes\\" + 
                           characterDirName + "\\Products\\Product - " + productNum + "\\";
                        
            primeraImagen = loadImageByNames(new String[]{
                "001-Product-" + productNum + ".jpg",
                "001-Product-" + productNum + ".JPG",
                "001-Product-" + productNum + ".jpeg",
                "001-Product-" + productNum + ".png",
                "Product-" + productNum + "-1.jpg",
                "001.jpg",
                "001.JPG",
                "001.png"
            });
                        
            segundaImagen = loadImageByNames(new String[]{
                "002-Product-" + productNum + ".jpg",
                "002-Product-" + productNum + ".JPG",
                "002-Product-" + productNum + ".jpeg",
                "002-Product-" + productNum + ".png",
                "Product-" + productNum + "-2.jpg",
                "002.jpg",
                "002.JPG",
                "002.png"
            });
                        
            if (primeraImagen != null && productImageView != null) {
                productImageView.setImage(primeraImagen);
            } else {
                System.out.println("Primera imagen no encontrada para producto: " + producto.getNombre() + 
                                 " (Ruta buscada: " + baseImagePath + ")");
            }
        } catch (Exception e) {
            System.out.println("Error al cargar imagen del producto " + 
                             (producto != null ? producto.getNombre() : "desconocido") + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private Image loadImageByNames(String[] imageNames) {
        for (String imageName : imageNames) {
            String imagePath = baseImagePath + imageName;
            File imageFile = new File(imagePath);
            if (imageFile.exists() && imageFile.isFile()) {
                try {
                    return new Image(imageFile.toURI().toString());
                } catch (Exception e) {
                    System.out.println("Error al cargar imagen " + imagePath + ": " + e.getMessage());
                }
            }
        }
        return null;
    }
    
    private void configurarHoverImagen() {
        if (rootPane == null || productImageView == null) {
            return;
        }
                
        final Image imagenDefault = primeraImagen;
                
        rootPane.setOnMouseEntered((MouseEvent e) -> {
            if (segundaImagen != null && productImageView != null) {
                productImageView.setImage(segundaImagen);
            }
        });
                
        rootPane.setOnMouseExited((MouseEvent e) -> {
            if (imagenDefault != null && productImageView != null) {
                productImageView.setImage(imagenDefault);
            }
        });
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
            loader.load();
            Component_ProductCard controller = loader.getController();
            
            if (controller != null) {                
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

