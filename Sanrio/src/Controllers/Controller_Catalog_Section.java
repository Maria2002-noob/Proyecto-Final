package Controllers;

import Models.DataStructures.Administrador_Singleton;
import Models.DataStructures.Lista_Doble_Personajes;
import Models.DataStructures.Lista_Doble_Productos;
import Models.Nodo_Personaje;
import Models.Nodo_Producto;
import Models.Personaje;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller_Catalog_Section implements Initializable {
    
    private Lista_Doble_Personajes listaPersonajes;
    private Lista_Doble_Productos listaProductos;
    private Administrador_Singleton administrador;
    
    private ObservableList<Nodo_Producto> todosLosProductos;
    private List<Nodo_Producto> productosFiltrados;
    private Nodo_Personaje personajeFiltroActual;
    private int indiceCargaProductos = 0;
    private static final int PRODUCTOS_POR_LOTE = 20;
    private boolean cargandoProductos = false;
    
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane headerPane;
    @FXML
    private HBox purchaseHistoryBox;
    @FXML
    private Label purchaseHistoryLabel;
    @FXML
    private Label wishlistLabel;
    @FXML
    private Label myAccountLabel;
    @FXML
    private Label cartLabel;
    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private VBox mainContent;
    @FXML
    private FlowPane charactersFlowPane;
    @FXML
    private FlowPane productsFlowPane;
    @FXML
    private AnchorPane footerPane;
    @FXML
    private ImageView bannerImageView;
    @FXML
    private ImageView logoImageView;
    @FXML
    private HBox myAccountBox;
    @FXML
    private VBox accountDropdownMenu;
    @FXML
    private Label userEmailLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        administrador = Administrador_Singleton.getAdministrador();
                        
        if (administrador.getLista_personajes().getCabeza() == null) {
            System.out.println("Inicializando datos...");
            administrador.inicializarDatos();
        }
        
        listaPersonajes = administrador.getLista_personajes();
        listaProductos = administrador.getLista_productos();
                
        ObservableList<Nodo_Personaje> personajesTest = listaPersonajes.getPersonajes();
        System.out.println("Personajes cargados: " + personajesTest.size());
                
        todosLosProductos = listaProductos.getProductos();
        System.out.println("Productos en lista: " + todosLosProductos.size());
        
        configurarUIPorRol();
        
        configurarEmailUsuario();
                
        cargarPersonajes();
                
        productosFiltrados = new ArrayList<>(todosLosProductos);
        System.out.println("Productos filtrados iniciales: " + productosFiltrados.size());
                
        if (productsFlowPane != null) {
            productsFlowPane.setVisible(true);
            productsFlowPane.setManaged(true);
        }
                
        if (productosFiltrados.isEmpty()) {
            System.out.println("ADVERTENCIA CRÍTICA: La lista de productos está vacía. Verificar carga de datos.");
        } else {
            cargarSiguienteLoteProductos();
        }
                
        configurarScrollInfinito();
                
        configurarBindResponsive();
    }
    
    private void configurarUIPorRol() {
        if (administrador.getUsuarioActual() != null) {
            String roll = administrador.getUsuarioActual().getUsuario().getRoll();
            if ("administrador".equalsIgnoreCase(roll)) {
                if (purchaseHistoryBox != null) {
                    purchaseHistoryBox.setVisible(true);
                    purchaseHistoryBox.setManaged(true);
                }
            } else {
                if (purchaseHistoryBox != null) {
                    purchaseHistoryBox.setVisible(false);
                    purchaseHistoryBox.setManaged(false);
                }
            }
        } else {
            if (purchaseHistoryBox != null) {
                purchaseHistoryBox.setVisible(false);
                purchaseHistoryBox.setManaged(false);
            }
        }
    }
    
    private void cargarPersonajes() {
        ObservableList<Nodo_Personaje> personajes = listaPersonajes.getPersonajes();
        for (Nodo_Personaje nodoPersonaje : personajes) {
            Component_CharacterCard card = Component_CharacterCard.create(nodoPersonaje);
            if (card != null) {
                card.setOnCharacterClick(() -> filtrarPorPersonaje(nodoPersonaje));
                charactersFlowPane.getChildren().add(card.getRoot());
            }
        }
    }
    
    private void filtrarPorPersonaje(Nodo_Personaje nodoPersonaje) {
        personajeFiltroActual = nodoPersonaje;
        Personaje personaje = nodoPersonaje.getPersonaje();
        
        productosFiltrados.clear();
        for (Nodo_Producto nodoProducto : todosLosProductos) {
            if (nodoProducto.getProducto().getPersonaje().getNombre().equals(personaje.getNombre())) {
                productosFiltrados.add(nodoProducto);
            }
        }
                
        productsFlowPane.getChildren().clear();
        indiceCargaProductos = 0;
        cargarSiguienteLoteProductos();
    }
    
    private void cargarSiguienteLoteProductos() {
        if (cargandoProductos) {
            return;
        }
        
        if (productosFiltrados == null || productosFiltrados.isEmpty()) {
            System.out.println("ADVERTENCIA: No hay productos para cargar. Lista vacía.");
            return;
        }
        
        cargandoProductos = true;
        
        int fin = Math.min(indiceCargaProductos + PRODUCTOS_POR_LOTE, productosFiltrados.size());
        System.out.println("Cargando productos del índice " + indiceCargaProductos + " al " + fin);
        
        int productosAgregados = 0;
        for (int i = indiceCargaProductos; i < fin; i++) {
            try {
                Nodo_Producto nodoProducto = productosFiltrados.get(i);
                if (nodoProducto != null && nodoProducto.getProducto() != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(Component_ProductCard.class.getResource("/Views/Components/ProductCard.fxml"));
                        AnchorPane productCard = loader.load();
                        
                        Component_ProductCard controller = loader.getController();
                        if (controller != null) {
                            controller.setProducto(nodoProducto);
                            controller.setOnAddToBag(() -> agregarAlCarrito(nodoProducto));
                            
                            productCard.setPrefWidth(190);
                            productCard.setPrefHeight(300);
                            
                            productsFlowPane.getChildren().add(productCard);
                            productosAgregados++;
                        } else {
                            System.out.println("ADVERTENCIA: Controlador nulo para el producto: " + nodoProducto.getProducto().getNombre());
                        }
                    } catch (IOException ioEx) {
                        System.out.println("Error de IO al cargar ProductCard: " + ioEx.getMessage());
                        ioEx.printStackTrace();
                    }
                } else {
                    System.out.println("ADVERTENCIA: Nodo de producto nulo en índice " + i);
                }
            } catch (Exception e) {
                System.out.println("Error al crear card de producto en índice " + i + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        System.out.println("Productos agregados al FlowPane: " + productosAgregados);
        indiceCargaProductos = fin;
        cargandoProductos = false;
    }
    
    private void configurarScrollInfinito() {
        mainScrollPane.setOnScroll((ScrollEvent event) -> {
            if (event.getDeltaY() > 0) {                
                double scrollPos = mainScrollPane.getVvalue();
                if (scrollPos >= 0.8 && indiceCargaProductos < productosFiltrados.size()) {
                    cargarSiguienteLoteProductos();
                }
            }
        });
               
        mainScrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() >= 0.8 && indiceCargaProductos < productosFiltrados.size() && !cargandoProductos) {
                cargarSiguienteLoteProductos();
            }
        });
    }
    
    private void configurarBindResponsive() {        
        if (mainScrollPane != null && productsFlowPane != null) {
            Platform.runLater(() -> {
                productsFlowPane.prefWidthProperty().bind(mainScrollPane.widthProperty().subtract(40));
            });
        }
        
        if (bannerImageView != null && footerPane != null) {
            bannerImageView.fitWidthProperty().bind(footerPane.widthProperty());
        }
    }
    
    private void agregarAlCarrito(Nodo_Producto nodoProducto) {      
        System.out.println("Agregando al carrito: " + nodoProducto.getProducto().getNombre());
    }
    
    @FXML
    private void handlePurchaseHistory(MouseEvent event) {        
        System.out.println("Mostrar historial de compras");
    }
    
    @FXML
    private void handleWishlist(MouseEvent event) {        
        System.out.println("Mostrar wishlist");
    }
    
    @FXML
    private void handleMyAccount(MouseEvent event) {
        // Toggle del menú desplegable
        if (accountDropdownMenu != null && myAccountBox != null) {
            boolean isVisible = accountDropdownMenu.isVisible();
            
            if (!isVisible) {
                // Configurar el cierre del menú la primera vez que se abre
                if (root != null && root.getScene() != null) {
                    configurarCerrarMenuAlClickFuera();
                }
                
                // Calcular posición del menú basada en la posición del botón
                Platform.runLater(() -> {
                    try {
                        if (myAccountBox.getScene() != null) {
                            javafx.geometry.Bounds buttonBounds = myAccountBox.localToScene(myAccountBox.getBoundsInLocal());
                            double buttonX = buttonBounds.getMinX();
                            double buttonWidth = buttonBounds.getWidth();
                            double menuWidth = accountDropdownMenu.getPrefWidth();
                            
                            // Posicionar el menú debajo del botón, alineado a la derecha
                            accountDropdownMenu.setLayoutX(buttonX + buttonWidth - menuWidth);
                            accountDropdownMenu.setLayoutY(80); // Debajo del header
                            
                            // Asegurar que el menú esté por encima de otros elementos
                            accountDropdownMenu.toFront();
                        }
                    } catch (Exception e) {
                        System.out.println("Error al posicionar el menú: " + e.getMessage());
                    }
                });
            }
            
            accountDropdownMenu.setVisible(!isVisible);
            accountDropdownMenu.setManaged(!isVisible);
            if (!isVisible) {
                accountDropdownMenu.toFront();
            }
        }
        event.consume(); // Prevenir propagación del evento
    }
    
    @FXML
    private void handleChangePassword(MouseEvent event) {
        System.out.println("Cambiar contraseña");
        ocultarMenu();
    }
    
    @FXML
    private void handleTranslate(MouseEvent event) {
        System.out.println("Traducir");
        ocultarMenu();
    }
    
    @FXML
    private void handleLogout(MouseEvent event) {
        ocultarMenu();
        realizarLogout();
    }
    
    private void ocultarMenu() {
        if (accountDropdownMenu != null) {
            accountDropdownMenu.setVisible(false);
            accountDropdownMenu.setManaged(false);
        }
    }
    
    private void configurarEmailUsuario() {
        try {
            if (administrador != null && administrador.getUsuarioActual() != null && userEmailLabel != null) {
                String correo = administrador.getUsuarioActual().getUsuario().getCorreo();
                if (correo != null && !correo.isEmpty()) {
                    userEmailLabel.setText(correo);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al configurar el email del usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void configurarCerrarMenuAlClickFuera() {
        // Cerrar el menú cuando se hace clic en cualquier parte de la escena
        try {
            if (root != null && root.getScene() != null) {
                root.getScene().setOnMouseClicked((MouseEvent event) -> {
                    try {
                        if (accountDropdownMenu != null && accountDropdownMenu.isVisible()) {
                            // Obtener las coordenadas del clic en la escena
                            double sceneX = event.getSceneX();
                            double sceneY = event.getSceneY();
                            
                            // Verificar si el clic fue fuera del menú y del botón
                            boolean clickEnMenu = false;
                            boolean clickEnBoton = false;
                            
                            if (accountDropdownMenu.getScene() != null) {
                                javafx.geometry.Bounds menuBounds = accountDropdownMenu.localToScene(accountDropdownMenu.getBoundsInLocal());
                                clickEnMenu = menuBounds.contains(sceneX, sceneY);
                            }
                            
                            if (myAccountBox != null && myAccountBox.getScene() != null) {
                                javafx.geometry.Bounds buttonBounds = myAccountBox.localToScene(myAccountBox.getBoundsInLocal());
                                clickEnBoton = buttonBounds.contains(sceneX, sceneY);
                            }
                            
                            if (!clickEnMenu && !clickEnBoton) {
                                ocultarMenu();
                            }
                        }
                    } catch (Exception e) {
                        // Si hay error, simplemente ocultar el menú
                        if (accountDropdownMenu != null) {
                            ocultarMenu();
                        }
                    }
                });
            }
        } catch (Exception e) {
            System.out.println("Error al configurar el cierre del menú: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void realizarLogout() {
        try {
            // Limpiar el usuario actual
            administrador.setUsuarioActual(null);
            
            // Cargar la ventana de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Login_and_Signing.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo cerrar sesión: " + e.getMessage());
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleCart(MouseEvent event) {        
        System.out.println("Mostrar carrito");
    }
    
    @FXML
    private void handleLogoClick(MouseEvent event) {
        restablecerFiltro();
    }
    
    private void restablecerFiltro() {
        personajeFiltroActual = null;
        
        // Restablecer la lista de productos filtrados a todos los productos
        productosFiltrados.clear();
        productosFiltrados.addAll(todosLosProductos);
        
        // Limpiar el FlowPane y recargar todos los productos
        productsFlowPane.getChildren().clear();
        indiceCargaProductos = 0;
        cargarSiguienteLoteProductos();
        
        System.out.println("Filtro restablecido. Mostrando todos los productos: " + productosFiltrados.size());
    }
}
