package Controllers;

import Models.*;
import Models.DataStructures.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller_Product_Detail_View implements Initializable {
    
    @FXML
    private VBox root;
    @FXML
    private ImageView mainProductImageView;
    @FXML
    private ImageView leftArrowButton;
    @FXML
    private ImageView rightArrowButton;
    @FXML
    private HBox thumbnailsContainer;
    @FXML
    private Label productTitleLabel;
    @FXML
    private Label productPriceLabel;
    @FXML
    private Button decreaseQuantityButton;
    @FXML
    private Label quantityLabel;
    @FXML
    private Button increaseQuantityButton;
    @FXML
    private Button addToBagButton;
    @FXML
    private Label characterNameLabel;
    @FXML
    private Label characterDescriptionLabel;
    @FXML
    private Label recommendedTitleLabel;
    @FXML
    private ScrollPane recommendedScrollPane;
    @FXML
    private FlowPane recommendedProductsFlowPane;
    @FXML
    private Label quantityLabelText;
    
    private Administrador_Singleton administrador;
    private LanguageManager languageManager;
    private Nodo_Producto productoActual;
    
    private List<Image> productImages;
    private List<ImageView> thumbnailViews;
    private int currentImageIndex = 0;
    private int quantity = 1;
    private String baseImagePath;
    private String productNum;
    
    private Runnable onBackToCatalog;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        administrador = Administrador_Singleton.getAdministrador();
        languageManager = LanguageManager.getInstance();
        
        productImages = new ArrayList<>();
        thumbnailViews = new ArrayList<>();
        
        actualizarTextos();
    }
    
    public void setProducto(Nodo_Producto nodoProducto) {
        this.productoActual = nodoProducto;
        if (productoActual != null && productoActual.getProducto() != null) {
            cargarInformacionProducto();
            cargarImagenesProducto();
            cargarProductosRecomendados();
        }
    }
    
    public void setOnBackToCatalog(Runnable callback) {
        this.onBackToCatalog = callback;
    }
    
    public void actualizarTextos() {
        if (addToBagButton != null) {
            addToBagButton.setText(languageManager.getString("button.add.to.bag"));
        }
        if (recommendedTitleLabel != null) {
            recommendedTitleLabel.setText(languageManager.getString("product.detail.you.might.like"));
        }
        if (quantityLabelText != null) {
            quantityLabelText.setText(languageManager.getString("product.detail.quantity"));
        }
        
        // Actualizar descripción del personaje si el producto está cargado
        if (productoActual != null && productoActual.getProducto() != null && 
            productoActual.getProducto().getPersonaje() != null) {
            Personaje personaje = productoActual.getProducto().getPersonaje();
            if (characterDescriptionLabel != null) {
                String descripcion;
                if ("es".equals(languageManager.getCurrentLanguage())) {
                    descripcion = personaje.getDescripcionEspanol();
                    if (descripcion == null || descripcion.isEmpty()) {
                        descripcion = personaje.getDescripcionIngles();
                    }
                } else {
                    descripcion = personaje.getDescripcionIngles();
                    if (descripcion == null || descripcion.isEmpty()) {
                        descripcion = personaje.getDescripcionEspanol();
                    }
                }
                characterDescriptionLabel.setText(descripcion);
            }
        }
    }
    
    public void actualizarPrecios() {
        if (productoActual != null && productoActual.getProducto() != null && productPriceLabel != null) {
            productPriceLabel.setText(languageManager.formatPrice(productoActual.getProducto().getPrecio()));
        }
        
        // Actualizar precios de productos recomendados
        if (recommendedProductsFlowPane != null) {
            for (Node node : recommendedProductsFlowPane.getChildren()) {
                if (node instanceof AnchorPane) {
                    AnchorPane card = (AnchorPane) node;
                    Object controller = card.getProperties().get("controller");
                    if (controller instanceof Component_ProductCard) {
                        Component_ProductCard productCard = (Component_ProductCard) controller;
                        productCard.actualizarPrecio();
                    }
                }
            }
        }
    }
    
    private void cargarInformacionProducto() {
        Producto producto = productoActual.getProducto();
        
        if (productTitleLabel != null) {
            productTitleLabel.setText(producto.getNombre());
        }
        
        if (productPriceLabel != null) {
            productPriceLabel.setText(languageManager.formatPrice(producto.getPrecio()));
        }
        
        if (producto.getPersonaje() != null) {
            Personaje personaje = producto.getPersonaje();
            
            if (characterNameLabel != null) {
                characterNameLabel.setText(personaje.getNombre());
            }
            
            if (characterDescriptionLabel != null) {
                String descripcion;
                if ("es".equals(languageManager.getCurrentLanguage())) {
                    descripcion = personaje.getDescripcionEspanol();
                    if (descripcion == null || descripcion.isEmpty()) {
                        descripcion = personaje.getDescripcionIngles();
                    }
                } else {
                    descripcion = personaje.getDescripcionIngles();
                    if (descripcion == null || descripcion.isEmpty()) {
                        descripcion = personaje.getDescripcionEspanol();
                    }
                }
                characterDescriptionLabel.setText(descripcion);
            }
        }
    }
    
    private void cargarImagenesProducto() {
        if (productoActual == null || productoActual.getProducto() == null) {
            return;
        }
        
        Producto producto = productoActual.getProducto();
        String personajeNombre = producto.getPersonaje().getNombre();
        String identificacion = producto.getIdentificacion();
        
        if (!identificacion.contains("-")) {
            return;
        }
        
        productNum = identificacion.substring(identificacion.lastIndexOf("-") + 1);
        String characterDirName = getCharacterDirectoryName(personajeNombre);
        
        baseImagePath = System.getProperty("user.dir") + "\\src\\Images\\Personajes\\" + 
                       characterDirName + "\\Products\\Product - " + productNum + "\\";
        
        productImages.clear();
        thumbnailViews.clear();
        thumbnailsContainer.getChildren().clear();
        
        // Buscar todas las imágenes disponibles (001, 002, 003, etc.)
        for (int i = 1; i <= 10; i++) {
            String[] imageNames = {
                String.format("%03d-Product-%s.jpg", i, productNum),
                String.format("%03d-Product-%s.JPG", i, productNum),
                String.format("%03d-Product-%s.jpeg", i, productNum),
                String.format("%03d-Product-%s.png", i, productNum),
                String.format("Product-%s-%d.jpg", productNum, i),
                String.format("%03d.jpg", i),
                String.format("%03d.JPG", i),
                String.format("%03d.png", i)
            };
            
            Image image = loadImageByNames(imageNames);
            if (image != null) {
                productImages.add(image);
                
                // Crear thumbnail
                ImageView thumbnail = new ImageView(image);
                thumbnail.setFitHeight(80);
                thumbnail.setFitWidth(80);
                thumbnail.setPreserveRatio(true);
                thumbnail.setPickOnBounds(true);
                thumbnail.getStyleClass().add("thumbnail-image");
                
                final int imageIndex = productImages.size() - 1;
                thumbnail.setOnMouseClicked(e -> mostrarImagen(imageIndex));
                
                thumbnailViews.add(thumbnail);
                thumbnailsContainer.getChildren().add(thumbnail);
            }
        }
        
        // Si no se encontraron imágenes, intentar con el método anterior
        if (productImages.isEmpty()) {
            String[] fallbackNames = {
                "001-Product-" + productNum + ".jpg",
                "001-Product-" + productNum + ".JPG",
                "001-Product-" + productNum + ".jpeg",
                "001-Product-" + productNum + ".png",
                "Product-" + productNum + "-1.jpg",
                "001.jpg",
                "001.JPG",
                "001.png"
            };
            
            Image image = loadImageByNames(fallbackNames);
            if (image != null) {
                productImages.add(image);
                
                ImageView thumbnail = new ImageView(image);
                thumbnail.setFitHeight(80);
                thumbnail.setFitWidth(80);
                thumbnail.setPreserveRatio(true);
                thumbnail.setPickOnBounds(true);
                thumbnail.getStyleClass().add("thumbnail-image");
                
                thumbnail.setOnMouseClicked(e -> mostrarImagen(0));
                thumbnailViews.add(thumbnail);
                thumbnailsContainer.getChildren().add(thumbnail);
            }
        }
        
        // Mostrar primera imagen
        if (!productImages.isEmpty()) {
            mostrarImagen(0);
        }
        
        // Actualizar visibilidad de flechas
        actualizarVisibilidadFlechas();
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
    
    private void mostrarImagen(int index) {
        if (index >= 0 && index < productImages.size() && mainProductImageView != null) {
            currentImageIndex = index;
            mainProductImageView.setImage(productImages.get(index));
            
            // Actualizar estilo de thumbnails
            for (int i = 0; i < thumbnailViews.size(); i++) {
                if (i == index) {
                    thumbnailViews.get(i).getStyleClass().add("thumbnail-selected");
                } else {
                    thumbnailViews.get(i).getStyleClass().remove("thumbnail-selected");
                }
            }
            
            actualizarVisibilidadFlechas();
        }
    }
    
    private void actualizarVisibilidadFlechas() {
        if (leftArrowButton != null) {
            leftArrowButton.setVisible(productImages.size() > 1 && currentImageIndex > 0);
        }
        if (rightArrowButton != null) {
            rightArrowButton.setVisible(productImages.size() > 1 && currentImageIndex < productImages.size() - 1);
        }
    }
    
    @FXML
    private void handlePreviousImage(MouseEvent event) {
        if (currentImageIndex > 0) {
            mostrarImagen(currentImageIndex - 1);
        }
        event.consume();
    }
    
    @FXML
    private void handleNextImage(MouseEvent event) {
        if (currentImageIndex < productImages.size() - 1) {
            mostrarImagen(currentImageIndex + 1);
        }
        event.consume();
    }
    
    @FXML
    private void handleDecreaseQuantity(javafx.event.ActionEvent event) {
        if (quantity > 1) {
            quantity--;
            if (quantityLabel != null) {
                quantityLabel.setText(String.valueOf(quantity));
            }
        }
    }
    
    @FXML
    private void handleIncreaseQuantity(javafx.event.ActionEvent event) {
        quantity++;
        if (quantityLabel != null) {
            quantityLabel.setText(String.valueOf(quantity));
        }
    }
    
    @FXML
    private void handleAddToBag(javafx.event.ActionEvent event) {
        if (administrador.getUsuarioActual() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(languageManager.getString("alert.error"));
            alert.setHeaderText(null);
            alert.setContentText(languageManager.getString("alert.login.required"));
            alert.showAndWait();
            return;
        }
        
        if (productoActual == null || productoActual.getProducto() == null) {
            return;
        }
        
        Producto producto = productoActual.getProducto();
        String correoUsuario = administrador.getUsuarioActual().getUsuario().getCorreo();
        Pila_Stack_De_Productos pilaProductos = administrador.getPila_productos();
        
        pilaProductos.loadDataFromFileTXTCarShop();
        pilaProductos.loadDataFromFileTXTFavorites();
        
        // Agregar producto múltiples veces según la cantidad
        boolean todosAgregados = true;
        for (int i = 0; i < quantity; i++) {
            boolean agregado = pilaProductos.agregarAlCarrito(producto, correoUsuario);
            if (!agregado && i == 0) {
                todosAgregados = false;
                break;
            }
        }
        
        if (todosAgregados) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(languageManager.getString("alert.success"));
            alert.setHeaderText(null);
            String message = quantity == 1 ? 
                languageManager.getString("alert.product.added.to.cart") :
                languageManager.getString("alert.product.added.to.cart") + " (" + quantity + " items)";
            alert.setContentText(message);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(languageManager.getString("alert.warning"));
            alert.setHeaderText(null);
            alert.setContentText(languageManager.getString("alert.product.already.in.cart"));
            alert.showAndWait();
        }
    }
    
    private void cargarProductosRecomendados() {
        if (productoActual == null || productoActual.getProducto() == null) {
            return;
        }
        
        Producto producto = productoActual.getProducto();
        String[] categorias = producto.getCategoria();
        
        if (categorias == null || categorias.length == 0) {
            return;
        }
        
        Lista_Doble_Productos listaProductos = administrador.getLista_productos();
        ObservableList<Nodo_Producto> todosLosProductos = listaProductos.getProductos();
        
        List<Nodo_Producto> productosRecomendados = new ArrayList<>();
        
        for (Nodo_Producto nodo : todosLosProductos) {
            if (nodo == null || nodo.getProducto() == null) {
                continue;
            }
            
            // Excluir el producto actual
            if (nodo.getProducto().getIdentificacion().equals(producto.getIdentificacion())) {
                continue;
            }
            
            // Verificar si comparte alguna categoría
            String[] categoriasNodo = nodo.getProducto().getCategoria();
            if (categoriasNodo != null && categoriasNodo.length > 0) {
                for (String categoria : categorias) {
                    for (String categoriaNodo : categoriasNodo) {
                        if (categoria.trim().equalsIgnoreCase(categoriaNodo.trim())) {
                            productosRecomendados.add(nodo);
                            break;
                        }
                    }
                    if (productosRecomendados.contains(nodo)) {
                        break;
                    }
                }
            }
            
            // Limitar a 15 productos
            if (productosRecomendados.size() >= 15) {
                break;
            }
        }
        
        // Cargar productos recomendados en el FlowPane
        recommendedProductsFlowPane.getChildren().clear();
        
        for (Nodo_Producto nodoProducto : productosRecomendados) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Component_ProductCard.class.getResource("/Views/Components/ProductCard.fxml"));
                AnchorPane productCard = loader.load();
                
                Component_ProductCard controller = loader.getController();
                if (controller != null) {
                    controller.setProducto(nodoProducto);
                    controller.setOnAddToBag(() -> agregarAlCarrito(nodoProducto));
                    controller.setOnImageClick(() -> mostrarDetallesProducto(nodoProducto));
                    
                    productCard.setPrefWidth(190);
                    productCard.setPrefHeight(300);
                    productCard.getProperties().put("controller", controller);
                    
                    recommendedProductsFlowPane.getChildren().add(productCard);
                }
            } catch (IOException e) {
                System.out.println("Error al cargar producto recomendado: " + e.getMessage());
            }
        }
    }
    
    private void mostrarDetallesProducto(Nodo_Producto nodoProducto) {
        // Cambiar el producto actual en la vista de detalles
        setProducto(nodoProducto);
    }
    
    private void agregarAlCarrito(Nodo_Producto nodoProducto) {
        if (administrador.getUsuarioActual() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(languageManager.getString("alert.error"));
            alert.setHeaderText(null);
            alert.setContentText(languageManager.getString("alert.login.required"));
            alert.showAndWait();
            return;
        }
        
        Producto producto = nodoProducto.getProducto();
        String correoUsuario = administrador.getUsuarioActual().getUsuario().getCorreo();
        Pila_Stack_De_Productos pilaProductos = administrador.getPila_productos();
        
        pilaProductos.loadDataFromFileTXTCarShop();
        pilaProductos.loadDataFromFileTXTFavorites();
        
        boolean agregado = pilaProductos.agregarAlCarrito(producto, correoUsuario);
        
        if (agregado) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(languageManager.getString("alert.success"));
            alert.setHeaderText(null);
            alert.setContentText(languageManager.getString("alert.product.added.to.cart"));
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(languageManager.getString("alert.warning"));
            alert.setHeaderText(null);
            alert.setContentText(languageManager.getString("alert.product.already.in.cart"));
            alert.showAndWait();
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
}
