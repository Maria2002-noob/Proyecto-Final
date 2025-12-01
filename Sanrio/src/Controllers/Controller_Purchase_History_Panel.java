package Controllers;

import Models.DataStructures.Administrador_Singleton;
import Models.DataStructures.LanguageManager;
import Models.DataStructures.Pila_Stack_De_Productos;
import Models.Producto;
import java.util.Stack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class Controller_Purchase_History_Panel {
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private Label titleLabel;
    
    @FXML
    private Label closeButton;
    
    @FXML
    private TableView<PurchaseHistoryItem> historyTableView;
    
    @FXML
    private TableColumn<PurchaseHistoryItem, String> productNumberColumn;
    
    @FXML
    private TableColumn<PurchaseHistoryItem, String> productNameColumn;
    
    @FXML
    private TableColumn<PurchaseHistoryItem, String> productPriceColumn;
    
    @FXML
    private TableColumn<PurchaseHistoryItem, String> buyerEmailColumn;
    
    @FXML
    private VBox emptyStateContainer;
    
    @FXML
    private Label emptyStateLabel;
    
    private Pila_Stack_De_Productos pilaProductos;
    private Administrador_Singleton administrador;
    private LanguageManager languageManager;
    private Runnable onClose;
    private ObservableList<PurchaseHistoryItem> historyItems;
    
    public void initialize() {
        administrador = Administrador_Singleton.getAdministrador();
        pilaProductos = administrador.getPila_productos();
        languageManager = LanguageManager.getInstance();
        
        historyItems = FXCollections.observableArrayList();
        historyTableView.setItems(historyItems);
        
        // Configurar columnas
        productNumberColumn.setCellValueFactory(new PropertyValueFactory<>("productNumber"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        buyerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("buyerEmail"));
        
        pilaProductos.loadDataFromFileTXTHistory();
        
        cargarHistorial();
    }
    
    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }
    
    private void cargarHistorial() {
        if (administrador.getUsuarioActual() == null) {
            showEmptyState();
            return;
        }
                
        Stack<Producto> productosHistorial = pilaProductos.getProducto_historial();
        
        historyItems.clear();
        
        if (productosHistorial.isEmpty()) {
            showEmptyState();
        } else {
            hideEmptyState();
            
            int contador = 1;
            for (Producto producto : productosHistorial) {
                String productNumber = producto.getIdentificacion();
                String productName = producto.getNombre();
                String productPrice = languageManager.formatPrice(producto.getPrecio());
                String buyerEmail = producto.getCorreo_usuario();
                
                PurchaseHistoryItem item = new PurchaseHistoryItem(productNumber, productName, productPrice, buyerEmail);
                historyItems.add(item);
                contador++;
            }
        }
    }     
    
    private void showEmptyState() {
        if (emptyStateContainer != null) {
            emptyStateContainer.setVisible(true);
        }
        if (historyTableView != null) {
            historyTableView.setVisible(false);
        }
    }
    
    private void hideEmptyState() {
        if (emptyStateContainer != null) {
            emptyStateContainer.setVisible(false);
        }
        if (historyTableView != null) {
            historyTableView.setVisible(true);
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
        pilaProductos.loadDataFromFileTXTHistory();
        cargarHistorial();
    }
    
    public void actualizarPrecios() {
        // Recargar el historial para actualizar precios con la moneda correcta
        cargarHistorial();
    }
    
    public void actualizarTextos() {
        if (titleLabel != null) {
            titleLabel.setText(languageManager.getString("purchase.history.title"));
        }
        
        if (productNumberColumn != null) {
            productNumberColumn.setText(languageManager.getString("purchase.history.product.number"));
        }
        
        if (productNameColumn != null) {
            productNameColumn.setText(languageManager.getString("purchase.history.product.name"));
        }
        
        if (productPriceColumn != null) {
            productPriceColumn.setText(languageManager.getString("purchase.history.product.price"));
        }
        
        if (buyerEmailColumn != null) {
            buyerEmailColumn.setText(languageManager.getString("purchase.history.buyer.email"));
        }
        
        if (emptyStateLabel != null) {
            emptyStateLabel.setText(languageManager.getString("purchase.history.empty"));
        }
    }
    
    
    public Node getRoot() {
        return rootPane;
    }
    
    // Clase interna para los datos de la tabla
    public static class PurchaseHistoryItem {
        private final String productNumber;
        private final String productName;
        private final String productPrice;
        private final String buyerEmail;
        
        public PurchaseHistoryItem(String productNumber, String productName, String productPrice, String buyerEmail) {
            this.productNumber = productNumber;
            this.productName = productName;
            this.productPrice = productPrice;
            this.buyerEmail = buyerEmail;
        }
        
        public String getProductNumber() {
            return productNumber;
        }
        
        public String getProductName() {
            return productName;
        }
        
        public String getProductPrice() {
            return productPrice;
        }
        
        public String getBuyerEmail() {
            return buyerEmail;
        }
    }
}

