package Controllers;

import Models.*;
import Models.DataStructures.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Controller_Catalog_Section implements Initializable {
    
    private Lista_Doble_Personajes listaPersonajes;
    private Lista_Doble_Productos listaProductos;
    private Administrador_Singleton administrador;
    private LanguageManager languageManager;
    
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
    @FXML
    private VBox charactersSection;
    @FXML
    private AnchorPane characterInfoPane;
    @FXML
    private Label characterNameLabel;
    @FXML
    private Label characterDescriptionLabel;
    @FXML
    private ImageView characterPersonajeImageView;
    @FXML
    private Label favoriteTitleLabel;
    @FXML
    private Label translateLabel;
    @FXML
    private Label changePasswordLabel;
    @FXML
    private Label logoutLabel;
    
    private AnchorPane wishlistPanel;
    private AnchorPane cartPanel;
    private Controller_Wishlist_Panel wishlistController;
    private Controller_Cart_Panel cartController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        administrador = Administrador_Singleton.getAdministrador();
        languageManager = LanguageManager.getInstance();
                        
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
        
        actualizarTextos();
                
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
                
        mostrarInformacionPersonaje(personaje);
               
        if (charactersSection != null) {
            charactersSection.setVisible(false);
            charactersSection.setManaged(false);
        }
        if (characterInfoPane != null) {
            characterInfoPane.setVisible(true);
            characterInfoPane.setManaged(true);
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
                    }
                } else {
                    System.out.println("ADVERTENCIA: Nodo de producto nulo en índice " + i);
                }
            } catch (Exception e) {
                System.out.println("Error al crear card de producto en índice " + i + ": " + e.getMessage());               
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
        
        if (mainScrollPane != null && characterInfoPane != null) {
            Platform.runLater(() -> {
                characterInfoPane.prefWidthProperty().bind(mainScrollPane.widthProperty().subtract(100));
            });
        }
        
        if (bannerImageView != null && footerPane != null) {
            bannerImageView.fitWidthProperty().bind(footerPane.widthProperty());
        }
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
                     
            if (cartPanel != null && cartPanel.isVisible() && cartController != null) {
                cartController.refresh();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(languageManager.getString("alert.warning"));
            alert.setHeaderText(null);
            alert.setContentText(languageManager.getString("alert.product.already.in.cart"));
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handlePurchaseHistory(MouseEvent event) {        
        System.out.println("Mostrar historial de compras");
    }
    
    @FXML
    private void handleWishlist(MouseEvent event) {
        if (administrador.getUsuarioActual() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(languageManager.getString("alert.error"));
            alert.setHeaderText(null);
            alert.setContentText(languageManager.getString("alert.login.required"));
            alert.showAndWait();
            return;
        }
        
        try {
            if (wishlistPanel == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Wishlist_Panel.fxml"));
                wishlistPanel = loader.load();
                wishlistController = loader.getController();
                wishlistController.setOnClose(() -> closeWishlistPanel());
                                
                if (root != null && root.getScene() != null) {
                    double sceneWidth = root.getScene().getWidth();
                    double panelWidth = wishlistPanel.getPrefWidth();
                    wishlistPanel.setLayoutX((sceneWidth - panelWidth) / 2);
                    wishlistPanel.setLayoutY(100);
                }
                
                root.getChildren().add(wishlistPanel);
                wishlistPanel.toFront();
            }
            
            wishlistPanel.setVisible(true);
            wishlistPanel.setManaged(true);
            wishlistPanel.toFront();
                        
            if (wishlistController != null) {
                wishlistController.refresh();
            }
                        
            configurarCerrarPanelAlClickFuera(wishlistPanel, () -> closeWishlistPanel());
            
        } catch (IOException e) {
            System.out.println("Error al cargar panel de wishlist: " + e.getMessage());
            e.printStackTrace();
        }
        event.consume();
    }
    
    private void closeWishlistPanel() {
        if (wishlistPanel != null) {
            wishlistPanel.setVisible(false);
            wishlistPanel.setManaged(false);
        }
    }
    
    @FXML
    private void handleMyAccount(MouseEvent event) {        
        if (accountDropdownMenu != null && myAccountBox != null) {
            boolean isVisible = accountDropdownMenu.isVisible();
            
            if (!isVisible) {                
                if (root != null && root.getScene() != null) {
                    configurarCerrarMenuAlClickFuera();
                }
                                
                Platform.runLater(() -> {
                    try {
                        if (myAccountBox.getScene() != null) {
                            javafx.geometry.Bounds buttonBounds = myAccountBox.localToScene(myAccountBox.getBoundsInLocal());
                            double buttonX = buttonBounds.getMinX();
                            double buttonWidth = buttonBounds.getWidth();
                            double menuWidth = accountDropdownMenu.getPrefWidth();
                                                       
                            accountDropdownMenu.setLayoutX(buttonX + buttonWidth - menuWidth);
                            accountDropdownMenu.setLayoutY(80);
                                                        
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
        event.consume();
    }
    
    @FXML
    private void handleChangePassword(MouseEvent event) {
        System.out.println("Cambiar contraseña");
        ocultarMenu();
    }
    
    @FXML
    private void handleTranslate(MouseEvent event) {        
        String currentLang = languageManager.getCurrentLanguage();
        if ("es".equals(currentLang)) {
            languageManager.setLanguage("en");
        } else {
            languageManager.setLanguage("es");
        }
                
        actualizarTextos();
                
        if (personajeFiltroActual != null && personajeFiltroActual.getPersonaje() != null) {
            mostrarInformacionPersonaje(personajeFiltroActual.getPersonaje());
        }
                
        actualizarTextosBotonesProductos();
        actualizarPreciosProductos();
        
        ocultarMenu();
                
        String langName = "es".equals(languageManager.getCurrentLanguage()) ? "Español" : "English";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(languageManager.getString("language.changed"));
        alert.setHeaderText(null);
        alert.setContentText(languageManager.getString("language.changed.to", langName));
        alert.showAndWait();
    }
    
    private void actualizarTextos() {
        if (purchaseHistoryLabel != null) {
            purchaseHistoryLabel.setText(languageManager.getString("nav.purchase.history"));
        }
        if (wishlistLabel != null) {
            wishlistLabel.setText(languageManager.getString("nav.wishlist"));
        }
        if (myAccountLabel != null) {
            myAccountLabel.setText(languageManager.getString("nav.my.account"));
        }
        if (cartLabel != null) {
            cartLabel.setText(languageManager.getString("nav.cart"));
        }
        if (translateLabel != null) {
            translateLabel.setText(languageManager.getString("account.translate"));
        }
        if (favoriteTitleLabel != null) {
            favoriteTitleLabel.setText(languageManager.getString("catalog.favorite.title"));
        }
        if (changePasswordLabel != null) {
            changePasswordLabel.setText(languageManager.getString("account.change.password"));
        }
        if (logoutLabel != null) {
            logoutLabel.setText(languageManager.getString("account.logout"));
        }
    }
    
    private void actualizarTextosBotonesProductos() {       
        if (productsFlowPane != null) {
            for (javafx.scene.Node node : productsFlowPane.getChildren()) {
                if (node instanceof AnchorPane) {
                    AnchorPane card = (AnchorPane) node;                    
                    javafx.scene.control.Button button = (javafx.scene.control.Button) 
                        card.lookup("#addToBagButton");
                    if (button != null) {
                        button.setText(languageManager.getString("button.add.to.bag"));
                    } else {                        
                        button = buscarBotonRecursivo(card);
                        if (button != null) {
                            button.setText(languageManager.getString("button.add.to.bag"));
                        }
                    }
                }
            }
        }
    }
    
    private void actualizarPreciosProductos() {
        if (productsFlowPane != null) {
            for (javafx.scene.Node node : productsFlowPane.getChildren()) {
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
                
        if (wishlistController != null && wishlistPanel != null && wishlistPanel.isVisible()) {
            wishlistController.refresh();
        }
                
        if (cartController != null && cartPanel != null && cartPanel.isVisible()) {
            cartController.refresh();
        }
    }
    
    private Button buscarBotonRecursivo(javafx.scene.Parent parent) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (node instanceof javafx.scene.control.Button) {
                Button button = (Button) node;                
                if (button.getId() != null && button.getId().equals("addToBagButton")) {
                    return button;
                }
                
                if (button.getAccessibleText() != null && button.getAccessibleText().contains("addToBag")) {
                    return button;
                }
            } else if (node instanceof javafx.scene.Parent) {
                Button found = buscarBotonRecursivo((Parent) node);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
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
        }
    }
    
    private void configurarCerrarMenuAlClickFuera() {        
        try {
            if (root != null && root.getScene() != null) {
                root.getScene().setOnMouseClicked((MouseEvent event) -> {
                    try {
                        if (accountDropdownMenu != null && accountDropdownMenu.isVisible()) {                            
                            double sceneX = event.getSceneX();
                            double sceneY = event.getSceneY();
                                                        
                            boolean clickEnMenu = false;
                            boolean clickEnBoton = false;
                            
                            if (accountDropdownMenu.getScene() != null) {
                                Bounds menuBounds = accountDropdownMenu.localToScene(accountDropdownMenu.getBoundsInLocal());
                                clickEnMenu = menuBounds.contains(sceneX, sceneY);
                            }
                            
                            if (myAccountBox != null && myAccountBox.getScene() != null) {
                                Bounds buttonBounds = myAccountBox.localToScene(myAccountBox.getBoundsInLocal());
                                clickEnBoton = buttonBounds.contains(sceneX, sceneY);
                            }
                            
                            if (!clickEnMenu && !clickEnBoton) {
                                ocultarMenu();
                            }
                        }
                    } catch (Exception e) {                      
                        if (accountDropdownMenu != null) {
                            ocultarMenu();
                        }
                    }
                });
            }
        } catch (Exception e) {
            System.out.println("Error al configurar el cierre del menú: " + e.getMessage());
        }
    }
    
    private void realizarLogout() {
        try {            
            administrador.setUsuarioActual(null);
                        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Login_and_Signing.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(languageManager.getString("alert.error"));
            alert.setHeaderText(null);
            alert.setContentText(languageManager.getString("alert.logout.error"));
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleCart(MouseEvent event) {
        if (administrador.getUsuarioActual() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(languageManager.getString("alert.error"));
            alert.setHeaderText(null);
            alert.setContentText(languageManager.getString("alert.login.required"));
            alert.showAndWait();
            return;
        }
        
        try {
            if (cartPanel == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Cart_Panel.fxml"));
                cartPanel = loader.load();
                cartController = loader.getController();
                cartController.setOnClose(() -> closeCartPanel());
                                
                if (root != null && root.getScene() != null) {
                    double sceneWidth = root.getScene().getWidth();
                    double panelWidth = cartPanel.getPrefWidth();
                    cartPanel.setLayoutX((sceneWidth - panelWidth) / 2);
                    cartPanel.setLayoutY(100);
                }
                
                root.getChildren().add(cartPanel);
                cartPanel.toFront();
            }
            
            cartPanel.setVisible(true);
            cartPanel.setManaged(true);
            cartPanel.toFront();
                        
            if (cartController != null) {
                cartController.refresh();
            }
                        
            configurarCerrarPanelAlClickFuera(cartPanel, () -> closeCartPanel());
            
        } catch (IOException e) {
            System.out.println("Error al cargar panel de carrito: " + e.getMessage());            
        }
        event.consume();
    }
    
    private void closeCartPanel() {
        if (cartPanel != null) {
            cartPanel.setVisible(false);
            cartPanel.setManaged(false);
        }
    }
    
    private void configurarCerrarPanelAlClickFuera(AnchorPane panel, Runnable onClose) {
        try {
            if (root != null && root.getScene() != null) {
                root.getScene().setOnMouseClicked((MouseEvent event) -> {
                    try {
                        if (panel != null && panel.isVisible()) {
                            double sceneX = event.getSceneX();
                            double sceneY = event.getSceneY();
                            
                            if (panel.getScene() != null) {
                                Bounds panelBounds = panel.localToScene(panel.getBoundsInLocal());
                                if (!panelBounds.contains(sceneX, sceneY)) {
                                    onClose.run();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error al cerrar panel: " + e.getMessage());
                    }
                });
            }
        } catch (Exception e) {
            System.out.println("Error al configurar cierre del panel: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleLogoClick(MouseEvent event) {
        restablecerFiltro();
    }
    
    private void restablecerFiltro() {
        personajeFiltroActual = null;
                
        if (characterInfoPane != null) {
            characterInfoPane.setVisible(false);
            characterInfoPane.setManaged(false);
        }
        if (charactersSection != null) {
            charactersSection.setVisible(true);
            charactersSection.setManaged(true);
        }
                
        productosFiltrados.clear();
        productosFiltrados.addAll(todosLosProductos);
                
        productsFlowPane.getChildren().clear();
        indiceCargaProductos = 0;
        cargarSiguienteLoteProductos();
        
        System.out.println("Filtro restablecido. Mostrando todos los productos: " + productosFiltrados.size());
    }
    
    private void mostrarInformacionPersonaje(Personaje personaje) {
        if (personaje == null) {
            return;
        }
                
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
                
        if (characterInfoPane != null && personaje.getColor() != null && !personaje.getColor().isEmpty()) {
            String colorHex = "#" + personaje.getColor();
            characterInfoPane.setStyle("-fx-background-color: " + colorHex + 
                "; -fx-background-radius: 20px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 15, 0, 0, 3);");
        }
               
        cargarImagenPersonaje(personaje.getNombre());
                
        Platform.runLater(() -> {
            configurarHoverImagenPersonaje(personaje);
        });
    }
    
    private void cargarImagenPersonaje(String nombrePersonaje) {
        if (characterPersonajeImageView == null || nombrePersonaje == null) {
            return;
        }
        
        try {
            String characterDirName = getCharacterDirectoryName(nombrePersonaje);
            String imageFileName = getPersonajeImageFileName(nombrePersonaje);
            String imagePath = System.getProperty("user.dir") + "\\src\\Images\\Personajes\\" + 
                               characterDirName + "\\" + imageFileName;
            
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                characterPersonajeImageView.setImage(image);
            } else {
                System.out.println("Imagen del personaje no encontrada: " + imagePath);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar imagen del personaje " + nombrePersonaje + ": " + e.getMessage());
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
    
    private String getPersonajeImageFileName(String nombrePersonaje) {
        switch (nombrePersonaje) {
            case "Hello Kitty":
                return "Hello_Kitty_Personaje.jpg";
            case "Cinnamoroll":
                return "Cinnamoroll_Personaje.jpg";
            case "My Melody":
                return "My_Melody_Personaje.jpg";
            case "Kuromi":
                return "Kuromi_Personaje.jpg";
            case "Keroppi":
                return "Keroppi_Personaje.jpg";
            case "Pompompurin":
                return "Pompompurin_Personaje.jpg";
            case "Chococat":
                return "Chococat_Personaje.jpg";
            case "Pochacco":
                return "Pochacco_Personaje.jpg";
            case "Little Twin Stars":
                return "Little_Twin_Stars_Personaje.jpg";
            case "Badtz-maru":
                return "Badtz-maru_Personaje.jpg";
            default:
                return nombrePersonaje.replace(" ", "_") + "_Personaje.jpg";
        }
    }
    
    private void configurarHoverImagenPersonaje(Personaje personaje) {
        if (characterPersonajeImageView == null || characterInfoPane == null || personaje == null) {
            return;
        }
        
        final double originalWidth = 150.0;
        final double originalHeight = 150.0;
        final String colorHex = "#" + personaje.getColor();
                
        characterPersonajeImageView.setFitWidth(originalWidth);
        characterPersonajeImageView.setFitHeight(originalHeight);
                
        characterInfoPane.setOnMouseEntered(e -> {
            if (characterPersonajeImageView != null) {
                characterPersonajeImageView.setFitWidth(originalWidth * 1.2);
                characterPersonajeImageView.setFitHeight(originalHeight * 1.2);
            }        
            if (characterInfoPane != null) {
                characterInfoPane.setStyle("-fx-background-color: " + colorHex + 
                    "; -fx-background-radius: 20px; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 20, 0, 0, 5);");
            }
        });
                
        characterInfoPane.setOnMouseExited(e -> {
            if (characterPersonajeImageView != null) {
                characterPersonajeImageView.setFitWidth(originalWidth);
                characterPersonajeImageView.setFitHeight(originalHeight);
            }        
            if (characterInfoPane != null) {
                characterInfoPane.setStyle("-fx-background-color: " + colorHex + 
                    "; -fx-background-radius: 20px; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 15, 0, 0, 3);");
            }
        });
    }
}
