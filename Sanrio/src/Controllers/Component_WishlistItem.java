package Controllers;

import Models.DataStructures.LanguageManager;
import Models.Producto;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Component_WishlistItem {
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private ImageView productImageView;
    
    @FXML
    private Label productNameLabel;
    
    @FXML
    private Label productPriceLabel;
    
    @FXML
    private Label removeButton;
    
    @FXML
    private ImageView addToCartButton;
    
    private Producto producto;
    private Runnable onRemove;
    private Runnable onAddToCart;
    private LanguageManager languageManager;
    
    public void setProducto(Producto producto) {
        this.producto = producto;
        if (languageManager == null) {
            languageManager = LanguageManager.getInstance();
        }
        if (producto != null) {
            updateDisplay();
        }
    }
    
    public Producto getProducto() {
        return producto;
    }
    
    public void setOnRemove(Runnable onRemove) {
        this.onRemove = onRemove;
    }
    
    public void setOnAddToCart(Runnable onAddToCart) {
        this.onAddToCart = onAddToCart;
    }
    
    public void actualizarPrecio() {
        if (producto != null && productPriceLabel != null) {
            if (languageManager == null) {
                languageManager = LanguageManager.getInstance();
            }
            productPriceLabel.setText(languageManager.formatPrice(producto.getPrecio()));
        }
    }
    
    private void updateDisplay() {
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
            loadProductImage();
        }
    }
    
    private void loadProductImage() {
        try {
            if (producto == null || producto.getPersonaje() == null || producto.getIdentificacion() == null) {
                return;
            }
            
            String personajeNombre = producto.getPersonaje().getNombre();
            String identificacion = producto.getIdentificacion();
            
            if (!identificacion.contains("-")) {
                return;
            }
            
            String productNum = identificacion.substring(identificacion.lastIndexOf("-") + 1);
            String characterDirName = getCharacterDirectoryName(personajeNombre);
            
            String baseImagePath = System.getProperty("user.dir") + "\\src\\Images\\Personajes\\" + 
                                   characterDirName + "\\Products\\Product - " + productNum + "\\";
            
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
            
            for (String imageName : imageNames) {
                String imagePath = baseImagePath + imageName;
                File imageFile = new File(imagePath);
                if (imageFile.exists() && imageFile.isFile()) {
                    try {
                        Image image = new Image(imageFile.toURI().toString());
                        productImageView.setImage(image);
                        break;
                    } catch (Exception e) {
                        System.out.println("Error al cargar imagen " + imagePath + ": " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar imagen del producto: " + e.getMessage());
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
    private void handleRemove(MouseEvent event) {
        event.consume();
        if (onRemove != null) {
            onRemove.run();
        }
    }
    
    @FXML
    private void handleAddToCart(MouseEvent event) {
        event.consume();
        if (onAddToCart != null) {
            onAddToCart.run();
        }
    }
    
    public javafx.scene.Node getRoot() {
        return rootPane;
    }
}

