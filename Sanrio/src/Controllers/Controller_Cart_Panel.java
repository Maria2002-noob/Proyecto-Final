package Controllers;

import Models.DataStructures.Administrador_Singleton;
import Models.DataStructures.LanguageManager;
import Models.DataStructures.Pila_Stack_De_Productos;
import Models.Producto;
import java.util.Stack;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class Controller_Cart_Panel {
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private Label cartTitleLabel;
    
    @FXML
    private Label closeButton;
    
    @FXML
    private VBox itemsContainer;
    
    @FXML
    private VBox emptyStateContainer;
    
    @FXML
    private Button checkoutButton;
    
    private Pila_Stack_De_Productos pilaProductos;
    private Administrador_Singleton administrador;
    private LanguageManager languageManager;
    private Runnable onClose;
    private Runnable onCheckout;
    
    public void initialize() {
        administrador = Administrador_Singleton.getAdministrador();
        pilaProductos = administrador.getPila_productos();
        languageManager = LanguageManager.getInstance();
                
        pilaProductos.loadDataFromFileTXTCarShop();
                
        loadCartItems();
    }
    
    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }
    
    public void setOnCheckout(Runnable onCheckout) {
        this.onCheckout = onCheckout;
    }
    
    private void loadCartItems() {
        if (administrador.getUsuarioActual() == null) {
            showEmptyState();
            return;
        }
        
        String correoUsuario = administrador.getUsuarioActual().getUsuario().getCorreo();
        Stack<Producto> productosCarrito = pilaProductos.obtenerProductosCarritoPorUsuario(correoUsuario);
        
        itemsContainer.getChildren().clear();
        
        if (productosCarrito.isEmpty()) {
            showEmptyState();
        } else {
            hideEmptyState();
            updateTitle(productosCarrito.size());
            
            if (checkoutButton != null) {
                checkoutButton.setVisible(true);
                checkoutButton.setManaged(true);
            }
            
            for (Producto producto : productosCarrito) {
                Component_CartItem item = createCartItem(producto);
                if (item != null) {
                    javafx.scene.Node root = item.getRoot();
                    root.getProperties().put("controller", item);
                    itemsContainer.getChildren().add(root);
                }
            }
        }
    }
    
    private Component_CartItem createCartItem(Producto producto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Components/CartItem.fxml"));
            loader.load();
            Component_CartItem controller = loader.getController();
            
            if (controller != null) {
                controller.setProducto(producto);
                controller.setOnRemove(() -> handleRemoveProduct(producto));
                controller.setOnAddToWishlist(() -> handleAddToWishlist(producto));
                return controller;
            }
        } catch (Exception e) {
            System.out.println("Error al crear item de carrito: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    private void handleRemoveProduct(Producto producto) {
        if (administrador.getUsuarioActual() == null) {
            return;
        }
        
        String correoUsuario = administrador.getUsuarioActual().getUsuario().getCorreo();
        pilaProductos.eliminarDeCarrito(producto.getIdentificacion(), correoUsuario);
        loadCartItems();
    }
    
    private void handleAddToWishlist(Producto producto) {
        if (administrador.getUsuarioActual() == null) {
            return;
        }
        
        String correoUsuario = administrador.getUsuarioActual().getUsuario().getCorreo();
        boolean movido = pilaProductos.moverDeCarritoAWishlist(producto.getIdentificacion(), correoUsuario);
        if (movido) {
            loadCartItems();
        }
    }
    
    private void updateTitle(int count) {
        if (cartTitleLabel != null) {
            cartTitleLabel.setText("Shopping Cart (" + count + ")");
        }
    }
    
    private void showEmptyState() {
        if (emptyStateContainer != null) {
            emptyStateContainer.setVisible(true);
        }
        if (itemsContainer != null) {
            itemsContainer.setVisible(false);
        }
        if (checkoutButton != null) {
            checkoutButton.setVisible(false);
            checkoutButton.setManaged(false);
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
    
    @FXML
    private void handleCheckout() {
        if (onCheckout != null) {
            onCheckout.run();
        }
    }
    
    public void refresh() {
        pilaProductos.loadDataFromFileTXTCarShop();
        loadCartItems();
        actualizarPreciosItems();
    }
    
    private void actualizarPreciosItems() {
        if (itemsContainer != null) {
            for (javafx.scene.Node node : itemsContainer.getChildren()) {
                Object controller = node.getProperties().get("controller");
                if (controller instanceof Component_CartItem) {
                    Component_CartItem item = (Component_CartItem) controller;
                    item.actualizarPrecio();
                }
            }
        }
    }
    
    public Node getRoot() {
        return rootPane;
    }
}

