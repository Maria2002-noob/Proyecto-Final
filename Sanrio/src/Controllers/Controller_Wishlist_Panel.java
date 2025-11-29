package Controllers;

import Models.DataStructures.Administrador_Singleton;
import Models.DataStructures.LanguageManager;
import Models.DataStructures.Pila_Stack_De_Productos;
import Models.Producto;
import java.util.Stack;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class Controller_Wishlist_Panel {
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private Label wishlistTitleLabel;
    
    @FXML
    private Label closeButton;
    
    @FXML
    private VBox itemsContainer;
    
    @FXML
    private VBox emptyStateContainer;
    
    private Pila_Stack_De_Productos pilaProductos;
    private Administrador_Singleton administrador;
    private LanguageManager languageManager;
    private Runnable onClose;
    
    public void initialize() {
        administrador = Administrador_Singleton.getAdministrador();
        pilaProductos = administrador.getPila_productos();
        languageManager = LanguageManager.getInstance();
                
        pilaProductos.loadDataFromFileTXTFavorites();
                
        loadWishlistItems();
    }
    
    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }
    
    private void loadWishlistItems() {
        if (administrador.getUsuarioActual() == null) {
            showEmptyState();
            return;
        }
        
        String correoUsuario = administrador.getUsuarioActual().getUsuario().getCorreo();
        Stack<Producto> productosWishlist = pilaProductos.obtenerProductosWishlistPorUsuario(correoUsuario);
        
        itemsContainer.getChildren().clear();
        
        if (productosWishlist.isEmpty()) {
            showEmptyState();
        } else {
            hideEmptyState();
            updateTitle(productosWishlist.size());
            
            for (Producto producto : productosWishlist) {
                Component_WishlistItem item = createWishlistItem(producto);
                if (item != null) {
                    javafx.scene.Node root = item.getRoot();
                    root.getProperties().put("controller", item);
                    itemsContainer.getChildren().add(root);
                }
            }
        }
    }
    
    private Component_WishlistItem createWishlistItem(Producto producto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Components/WishlistItem.fxml"));
            loader.load();
            Component_WishlistItem controller = loader.getController();
            
            if (controller != null) {
                controller.setProducto(producto);
                controller.setOnRemove(() -> handleRemoveProduct(producto));
                controller.setOnAddToCart(() -> handleAddToCart(producto));
                return controller;
            }
        } catch (Exception e) {
            System.out.println("Error al crear item de wishlist: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    private void handleRemoveProduct(Producto producto) {
        if (administrador.getUsuarioActual() == null) {
            return;
        }
        
        String correoUsuario = administrador.getUsuarioActual().getUsuario().getCorreo();
        pilaProductos.eliminarDeWishlist(producto.getIdentificacion(), correoUsuario);
        loadWishlistItems();
    }
    
    private void handleAddToCart(Producto producto) {
        if (administrador.getUsuarioActual() == null) {
            return;
        }
        
        String correoUsuario = administrador.getUsuarioActual().getUsuario().getCorreo();
        boolean movido = pilaProductos.moverDeWishlistACarrito(producto.getIdentificacion(), correoUsuario);
        if (movido) {
            loadWishlistItems();
        }
    }
    
    private void updateTitle(int count) {
        if (wishlistTitleLabel != null) {
            wishlistTitleLabel.setText("Wishlist (" + count + ")");
        }
    }
    
    private void showEmptyState() {
        if (emptyStateContainer != null) {
            emptyStateContainer.setVisible(true);
        }
        if (itemsContainer != null) {
            itemsContainer.setVisible(false);
        }
        updateTitle(0);
    }
    
    private void hideEmptyState() {
        if (emptyStateContainer != null) {
            emptyStateContainer.setVisible(false);
        }
        if (itemsContainer != null) {
            itemsContainer.setVisible(true);
        }
    }
    
    @FXML
    private void handleClose(MouseEvent event) {
        event.consume();
        if (onClose != null) {
            onClose.run();
        }
    }
    
    public void refresh() {
        pilaProductos.loadDataFromFileTXTFavorites();
        loadWishlistItems();
        actualizarPreciosItems();
    }
    
    private void actualizarPreciosItems() {
        if (itemsContainer != null) {
            for (javafx.scene.Node node : itemsContainer.getChildren()) {
                Object controller = node.getProperties().get("controller");
                if (controller instanceof Component_WishlistItem) {
                    Component_WishlistItem item = (Component_WishlistItem) controller;
                    item.actualizarPrecio();
                }
            }
        }
    }
    
    public Node getRoot() {
        return rootPane;
    }
}

