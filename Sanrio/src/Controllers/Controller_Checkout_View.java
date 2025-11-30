package Controllers;

import Models.DataStructures.Administrador_Singleton;
import Models.DataStructures.LanguageManager;
import Models.DataStructures.Pila_Stack_De_Productos;
import Models.Producto;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller_Checkout_View implements Initializable {
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private Label checkoutTitleLabel;
    
    @FXML
    private Label paymentMethodsLabel;
    
    @FXML
    private HBox creditCardOption;
    
    @FXML
    private Label creditCardLabel;
    
    @FXML
    private HBox nequiOption;
    
    @FXML
    private Label nequiLabel;
    
    @FXML
    private HBox daviPlataOption;
    
    @FXML
    private Label daviPlataLabel;
    
    @FXML
    private VBox creditCardDetailsContainer;
    
    @FXML
    private Label cardDetailsLabel;
    
    @FXML
    private TextField cardNumberField;
    
    @FXML
    private TextField expirationField;
    
    @FXML
    private TextField cvvField;
    
    @FXML
    private VBox nequiDetailsContainer;
    
    @FXML
    private Label accountDetailsLabel;
    
    @FXML
    private TextField accountHolderField;
    
    @FXML
    private TextField accountNumberField;
    
    @FXML
    private TextField dynamicKeyField;
    
    @FXML
    private VBox daviPlataDetailsContainer;
    
    @FXML
    private Label daviPlataAccountDetailsLabel;
    
    @FXML
    private TextField daviPlataAccountHolderField;
    
    @FXML
    private TextField daviPlataAccountNumberField;
    
    @FXML
    private TextField daviPlataPaymentCodeField;
    
    @FXML
    private Label summaryTitleLabel;
    
    @FXML
    private Label closeButton;
    
    
    @FXML
    private VBox productsContainer;
    
    @FXML
    private Label priceLabel;
    
    @FXML
    private Label priceValueLabel;
    
    @FXML
    private Label totalLabel;
    
    @FXML
    private Label totalValueLabel;
    
    @FXML
    private Button completePurchaseButton;
    
    private Administrador_Singleton administrador;
    private LanguageManager languageManager;
    private Pila_Stack_De_Productos pilaProductos;
    private Runnable onClose;
    
    private String selectedPaymentMethod = "Nequi"; // Por defecto Nequi está seleccionado
    private Map<String, Integer> productosConCantidad;
    private float total;
    
    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle rb) {
        administrador = Administrador_Singleton.getAdministrador();
        languageManager = LanguageManager.getInstance();
        pilaProductos = administrador.getPila_productos();
        
        productosConCantidad = new HashMap<>();
        
        pilaProductos.loadDataFromFileTXTCarShop();
        
        cargarProductos();
        calcularTotal();
        actualizarTextos();
        configurarMetodoPagoInicial();
    }
    
    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }
    
    private void configurarMetodoPagoInicial() {
        mostrarCamposMetodoPago("Nequi");
        actualizarSeleccionMetodoPago();
    }
    
    private void cargarProductos() {
        if (administrador.getUsuarioActual() == null) {
            return;
        }
        
        String correoUsuario = administrador.getUsuarioActual().getUsuario().getCorreo();
        Stack<Producto> productosCarrito = pilaProductos.obtenerProductosCarritoPorUsuario(correoUsuario);
        
        productsContainer.getChildren().clear();
        productosConCantidad.clear();
                
        for (Producto producto : productosCarrito) {
            String identificacion = producto.getIdentificacion();
            productosConCantidad.put(identificacion, 
                productosConCantidad.getOrDefault(identificacion, 0) + 1);
        }
                
        for (Map.Entry<String, Integer> entry : productosConCantidad.entrySet()) {
            String identificacion = entry.getKey();
            int cantidad = entry.getValue();
            
            Producto producto = null;
            for (Producto p : productosCarrito) {
                if (p.getIdentificacion().equals(identificacion)) {
                    producto = p;
                    break;
                }
            }
            
            if (producto != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Components/CheckoutItem.fxml"));
                    AnchorPane itemPane = loader.load();
                    Component_CheckoutItem controller = loader.getController();
                    
                    if (controller != null) {
                        controller.setProducto(producto, cantidad);
                        itemPane.getProperties().put("controller", controller);
                        productsContainer.getChildren().add(itemPane);
                    }
                } catch (IOException e) {
                    System.out.println("Error al cargar item de checkout: " + e.getMessage());                    
                }
            }
        }
    }
    
    private void calcularTotal() {
        total = 0.0f;
        
        if (administrador.getUsuarioActual() == null) {
            return;
        }
        
        String correoUsuario = administrador.getUsuarioActual().getUsuario().getCorreo();
        Stack<Producto> productosCarrito = pilaProductos.obtenerProductosCarritoPorUsuario(correoUsuario);
        
        for (Producto producto : productosCarrito) {
            total += producto.getPrecio();
        }
        
        actualizarLabelsTotales();
    }
    
    private void actualizarLabelsTotales() {
        if (priceValueLabel != null) {
            priceValueLabel.setText(languageManager.formatPrice(total));
        }
        if (totalValueLabel != null) {
            totalValueLabel.setText(languageManager.formatPrice(total));
        }
    }
    
    @FXML
    private void handleCreditCardSelection(MouseEvent event) {
        selectedPaymentMethod = "CreditCard";
        mostrarCamposMetodoPago("CreditCard");
        actualizarSeleccionMetodoPago();
        event.consume();
    }
    
    @FXML
    private void handleNequiSelection(MouseEvent event) {
        selectedPaymentMethod = "Nequi";
        mostrarCamposMetodoPago("Nequi");
        actualizarSeleccionMetodoPago();
        event.consume();
    }
    
    @FXML
    private void handleDaviPlataSelection(MouseEvent event) {
        selectedPaymentMethod = "DaviPlata";
        mostrarCamposMetodoPago("DaviPlata");
        actualizarSeleccionMetodoPago();
        event.consume();
    }
    
    private void mostrarCamposMetodoPago(String metodo) {
        // Ocultar todos los contenedores primero
        if (creditCardDetailsContainer != null) {
            creditCardDetailsContainer.setVisible(false);
            creditCardDetailsContainer.setManaged(false);
        }
        if (nequiDetailsContainer != null) {
            nequiDetailsContainer.setVisible(false);
            nequiDetailsContainer.setManaged(false);
        }
        if (daviPlataDetailsContainer != null) {
            daviPlataDetailsContainer.setVisible(false);
            daviPlataDetailsContainer.setManaged(false);
        }
        
        // Mostrar solo el contenedor del método seleccionado
        switch (metodo) {
            case "CreditCard":
                if (creditCardDetailsContainer != null) {
                    creditCardDetailsContainer.setVisible(true);
                    creditCardDetailsContainer.setManaged(true);
                }
                break;
            case "Nequi":
                if (nequiDetailsContainer != null) {
                    nequiDetailsContainer.setVisible(true);
                    nequiDetailsContainer.setManaged(true);
                }
                break;
            case "DaviPlata":
                if (daviPlataDetailsContainer != null) {
                    daviPlataDetailsContainer.setVisible(true);
                    daviPlataDetailsContainer.setManaged(true);
                }
                break;
        }
    }
    
    private void actualizarSeleccionMetodoPago() {
        // Remover clase de selección de todos los métodos
        if (creditCardOption != null) {
            creditCardOption.getStyleClass().remove("payment-method-selected");
        }
        if (nequiOption != null) {
            nequiOption.getStyleClass().remove("payment-method-selected");
        }
        if (daviPlataOption != null) {
            daviPlataOption.getStyleClass().remove("payment-method-selected");
        }
        
        // Agregar clase de selección al método actual
        switch (selectedPaymentMethod) {
            case "CreditCard":
                if (creditCardOption != null && !creditCardOption.getStyleClass().contains("payment-method-selected")) {
                    creditCardOption.getStyleClass().add("payment-method-selected");
                }
                break;
            case "Nequi":
                if (nequiOption != null && !nequiOption.getStyleClass().contains("payment-method-selected")) {
                    nequiOption.getStyleClass().add("payment-method-selected");
                }
                break;
            case "DaviPlata":
                if (daviPlataOption != null && !daviPlataOption.getStyleClass().contains("payment-method-selected")) {
                    daviPlataOption.getStyleClass().add("payment-method-selected");
                }
                break;
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
    private void handleCompletePurchase() {
        if (administrador.getUsuarioActual() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                languageManager.getString("alert.error"), 
                languageManager.getString("alert.login.required"));
            return;
        }
        
        // Validar campos obligatorios según método de pago
        boolean camposCompletos = false;
        
        if ("CreditCard".equals(selectedPaymentMethod)) {
            camposCompletos = !cardNumberField.getText().trim().isEmpty() &&
                             !expirationField.getText().trim().isEmpty() &&
                             !cvvField.getText().trim().isEmpty();
        } else if ("Nequi".equals(selectedPaymentMethod)) {
            camposCompletos = !accountHolderField.getText().trim().isEmpty() &&
                             !accountNumberField.getText().trim().isEmpty() &&
                             !dynamicKeyField.getText().trim().isEmpty();
        } else if ("DaviPlata".equals(selectedPaymentMethod)) {
            camposCompletos = !daviPlataAccountHolderField.getText().trim().isEmpty() &&
                             !daviPlataAccountNumberField.getText().trim().isEmpty() &&
                             !daviPlataPaymentCodeField.getText().trim().isEmpty();
        }
        
        if (!camposCompletos) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                languageManager.getString("alert.fields.incomplete"), 
                languageManager.getString("checkout.fields.required"));
            return;
        }
        
        // Mover productos del carrito al historial
        String correoUsuario = administrador.getUsuarioActual().getUsuario().getCorreo();
        boolean movido = pilaProductos.moverCarritoAHistorial(correoUsuario);
        
        if (movido) {
            mostrarAlerta(Alert.AlertType.INFORMATION, 
                languageManager.getString("alert.success"), 
                languageManager.getString("checkout.success"));
            
            // Cerrar checkout y volver al catálogo
            if (onClose != null) {
                onClose.run();
            }
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, 
                languageManager.getString("alert.error"), 
                languageManager.getString("checkout.error"));
        }
    }
    
    public void actualizarTextos() {
        if (checkoutTitleLabel != null) {
            checkoutTitleLabel.setText(languageManager.getString("checkout.title"));
        }
        if (paymentMethodsLabel != null) {
            paymentMethodsLabel.setText(languageManager.getString("checkout.payment.methods"));
        }
        if (creditCardLabel != null) {
            creditCardLabel.setText(languageManager.getString("checkout.credit.card"));
        }
        if (nequiLabel != null) {
            nequiLabel.setText(languageManager.getString("checkout.nequi"));
        }
        if (daviPlataLabel != null) {
            daviPlataLabel.setText(languageManager.getString("checkout.daviplata"));
        }
        if (cardDetailsLabel != null) {
            cardDetailsLabel.setText(languageManager.getString("checkout.card.details"));
        }
        if (cardNumberField != null) {
            cardNumberField.setPromptText(languageManager.getString("checkout.card.number"));
        }
        if (expirationField != null) {
            expirationField.setPromptText(languageManager.getString("checkout.expiration"));
        }
        if (cvvField != null) {
            cvvField.setPromptText(languageManager.getString("checkout.cvv"));
        }
        if (accountDetailsLabel != null) {
            accountDetailsLabel.setText(languageManager.getString("checkout.account.details"));
        }
        if (accountHolderField != null) {
            accountHolderField.setPromptText(languageManager.getString("checkout.account.holder"));
        }
        if (accountNumberField != null) {
            accountNumberField.setPromptText(languageManager.getString("checkout.account.number"));
        }
        if (dynamicKeyField != null) {
            dynamicKeyField.setPromptText(languageManager.getString("checkout.dynamic.key"));
        }
        if (daviPlataAccountDetailsLabel != null) {
            daviPlataAccountDetailsLabel.setText(languageManager.getString("checkout.account.details"));
        }
        if (daviPlataAccountHolderField != null) {
            daviPlataAccountHolderField.setPromptText(languageManager.getString("checkout.account.holder"));
        }
        if (daviPlataAccountNumberField != null) {
            daviPlataAccountNumberField.setPromptText(languageManager.getString("checkout.account.number"));
        }
        if (daviPlataPaymentCodeField != null) {
            daviPlataPaymentCodeField.setPromptText(languageManager.getString("checkout.payment.code"));
        }
        if (summaryTitleLabel != null) {
            summaryTitleLabel.setText(languageManager.getString("checkout.summary.title"));
        }
        if (priceLabel != null) {
            priceLabel.setText(languageManager.getString("checkout.price"));
        }
        if (totalLabel != null) {
            totalLabel.setText(languageManager.getString("checkout.total"));
        }
        if (completePurchaseButton != null) {
            completePurchaseButton.setText(languageManager.getString("checkout.complete.purchase"));
        }
        
        // Actualizar precios
        actualizarPrecios();
    }
    
    public void actualizarPrecios() {
        calcularTotal();
        
        // Actualizar precios de items
        if (productsContainer != null) {
            for (javafx.scene.Node node : productsContainer.getChildren()) {
                if (node instanceof AnchorPane) {
                    AnchorPane itemPane = (AnchorPane) node;
                    Object controller = itemPane.getProperties().get("controller");
                    if (controller instanceof Component_CheckoutItem) {
                        Component_CheckoutItem item = (Component_CheckoutItem) controller;
                        item.actualizarPrecio();
                    }
                }
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
}

