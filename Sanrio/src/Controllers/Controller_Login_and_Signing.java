package Controllers;

import Models.DataStructures.Administrador_Singleton;
import Models.DataStructures.Lista_Doble_Personajes;
import Models.DataStructures.Lista_Doble_Productos;
import Models.DataStructures.Lista_Doble_Usuarios;
import Models.DataStructures.Pila_Stack_De_Productos;
import Models.Nodo_Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller_Login_and_Signing implements Initializable {

    private Lista_Doble_Usuarios Lista_Usuarios = Administrador_Singleton.getAdministrador().getLista_usuarios();
    private Lista_Doble_Personajes Lista_Personajes = Administrador_Singleton.getAdministrador().getLista_personajes();
    private Lista_Doble_Productos Lista_Productos = Administrador_Singleton.getAdministrador().getLista_productos();    
    private Pila_Stack_De_Productos pilas_producto = Administrador_Singleton.getAdministrador().getPila_productos();
        
    private int clicksHelloKitty = 0;
    private int clicksCinnamoroll = 0;
    private int clicksPochacco = 0;
    private boolean adminModeActivado = false;
    private boolean adminModeMostrado = false;
    private Timeline adminModeTimer = null;
        
    @FXML
    private TextField userTextField;
    @FXML
    private PasswordField passwordTextField;
        
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField identificacionTextField;
    @FXML
    private TextField correoTextField;
    @FXML
    private PasswordField passwordRegisterTextField;
    @FXML
    private PasswordField confirmPasswordTextField;
    
    @FXML
    private ImageView kuromiImageView;    
    @FXML
    private ImageView chococatImageView;   
    @FXML
    private ImageView badtzMaruImageView;    
    @FXML
    private ImageView twinStarsImageView;   
    @FXML
    private ImageView bannerImageView;    
    @FXML
    private ImageView helloKittyImageView;
    @FXML
    private ImageView cinnamorollImageView;
    @FXML
    private ImageView pochaccoImageView;
    @FXML
    private AnchorPane footerPane;    
    @FXML
    private ScrollPane scrollContent;
    @FXML
    private GridPane content;
    @FXML
    private AnchorPane root;
    @FXML
    private ScrollPane scrollContentRegister;
    @FXML
    private GridPane contentRegister;
    @FXML
    private StackPane viewContainer;
    @FXML
    private Label signInLabel;    

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        Lista_Usuarios.cargarDesdeArchivoDeTexto();                
        
        applyCircularClip(kuromiImageView);
        applyCircularClip(chococatImageView);
        applyCircularClip(badtzMaruImageView);
        applyCircularClip(twinStarsImageView);                
        applyCircularClip(helloKittyImageView);
        applyCircularClip(cinnamorollImageView);
        applyCircularClip(pochaccoImageView);
                
        if (helloKittyImageView != null) {
            helloKittyImageView.setOnMouseClicked(this::handleHelloKittyClick);
        }
        if (cinnamorollImageView != null) {
            cinnamorollImageView.setOnMouseClicked(this::handleCinnamorollClick);
        }
        if (pochaccoImageView != null) {
            pochaccoImageView.setOnMouseClicked(this::handlePochaccoClick);
        }
                
        if (content != null && scrollContent != null) {
            Platform.runLater(() -> {                
                content.prefWidthProperty().bind(scrollContent.widthProperty());
                content.prefHeightProperty().bind(scrollContent.heightProperty());
            });
        }
                
        if (contentRegister != null && scrollContentRegister != null) {
            Platform.runLater(() -> {                
                contentRegister.prefWidthProperty().bind(scrollContentRegister.widthProperty());
                contentRegister.prefHeightProperty().bind(scrollContentRegister.heightProperty());
            });
        }
                        
        if (bannerImageView != null && footerPane != null) {
            bannerImageView.fitWidthProperty().bind(footerPane.widthProperty());
        }
                
        if (signInLabel != null) {
            signInLabel.setStyle("-fx-cursor: hand;");
        }
    }
        
    private void handleHelloKittyClick(MouseEvent event) {
        clicksHelloKitty++;
        verificarAdminMode();
    }
    
    private void handleCinnamorollClick(MouseEvent event) {
        clicksCinnamoroll++;
        verificarAdminMode();
    }
    
    private void handlePochaccoClick(MouseEvent event) {
        clicksPochacco++;
        verificarAdminMode();
    }
    
    private void verificarAdminMode() {
        boolean modoAdmin = isAdminMode();
                
        if (modoAdmin && !adminModeActivado && !adminModeMostrado) {
            adminModeActivado = true;
            adminModeMostrado = true;
            mostrarMensajeAdminMode();
            iniciarTimerAdminMode();
        }
    }
    
    private void mostrarMensajeAdminMode() {
        Platform.runLater(() -> {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Modo Administrador Activado", 
                "¡Se ha activado el registro de administrador\n" +
                "Tienes 10 segundos para completar tu registro como administrador.");
        });
    }
    
    private void iniciarTimerAdminMode() {        
        if (adminModeTimer != null) {
            adminModeTimer.stop();
        }
                
        adminModeTimer = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
            if (adminModeActivado) {
                adminModeActivado = false;
                resetClickCounters();
                Platform.runLater(() -> {
                    mostrarAlerta(Alert.AlertType.WARNING, "Tiempo Agotado", 
                        "El tiempo para registrar un administrador ha expirado.\n" +
                        "Vuelve a activar el modo administrador si deseas intentarlo de nuevo.");
                });
            }
        }));
        adminModeTimer.setCycleCount(1);
        adminModeTimer.play();
    }
    
    private boolean isAdminMode() {
        return clicksHelloKitty >= 2 && clicksCinnamoroll >= 1 && clicksPochacco >= 1;
    }
    
    private void resetClickCounters() {
        clicksHelloKitty = 0;
        clicksCinnamoroll = 0;
        clicksPochacco = 0;
        adminModeActivado = false;
        adminModeMostrado = false;
        if (adminModeTimer != null) {
            adminModeTimer.stop();
            adminModeTimer = null;
        }
    }
    
    private void applyCircularClip(ImageView imageView) {
        if (imageView != null) {
            double radius = Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2.0;
            Circle clip = new Circle(radius);
            clip.setCenterX(radius);
            clip.setCenterY(radius);
            imageView.setClip(clip);
        }
    }
    
    @FXML
    public void handleToggleView(MouseEvent event) {
        if (scrollContent != null && scrollContentRegister != null) {
            boolean isLoginVisible = scrollContent.isVisible();
            scrollContent.setVisible(!isLoginVisible);
            scrollContent.setManaged(!isLoginVisible);
            scrollContentRegister.setVisible(isLoginVisible);
            scrollContentRegister.setManaged(isLoginVisible);
                        
            resetClickCounters();
        }
    }
    
    @FXML
    public void handleCancel(ActionEvent event) {        
        if (scrollContent != null && scrollContentRegister != null) {
            scrollContent.setVisible(true);
            scrollContent.setManaged(true);
            scrollContentRegister.setVisible(false);
            scrollContentRegister.setManaged(false);            
            resetClickCounters();
        }
    }
    
    @FXML
    public void handleAccept(ActionEvent event) {
        if (nombreTextField.getText().trim().isEmpty() ||
            identificacionTextField.getText().trim().isEmpty() ||
            correoTextField.getText().trim().isEmpty() ||
            passwordRegisterTextField.getText().trim().isEmpty() ||
            confirmPasswordTextField.getText().trim().isEmpty()) {
            
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", 
                "Por favor, complete todos los campos.");
            return;
        }
                
        if (!validarCorreo(correoTextField.getText().trim())) {
            mostrarAlerta(Alert.AlertType.WARNING, "Correo inválido", 
                "Por favor, ingrese un correo electrónico válido.");
            return;
        }
                
        int identificacion;
        try {
            identificacion = Integer.parseInt(identificacionTextField.getText().trim());
            if (identificacion <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Identificación inválida", 
                "La identificación debe ser un número entero positivo.");
            return;
        }
        
        String password = passwordRegisterTextField.getText().trim();
        String confirmPassword = confirmPasswordTextField.getText().trim();
        
        if (!password.equals(confirmPassword)) {
            mostrarAlerta(Alert.AlertType.WARNING, "Contraseñas no coinciden", 
                "Las contraseñas ingresadas no coinciden. Por favor, verifique.");
            return;
        }
                
        if (password.length() < 6) {
            mostrarAlerta(Alert.AlertType.WARNING, "Contraseña muy corta", 
                "La contraseña debe tener al menos 6 caracteres.");
            return;
        }
                
        String roll = adminModeActivado ? "administrador" : "cliente";
                
        if (Lista_Usuarios.buscarCorreo(correoTextField.getText().trim()) != null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Correo ya registrado", 
                "Ya existe un usuario con este correo electrónico.");
            return;
        }
                
        try {
            Lista_Usuarios.agregarUsuario(
                nombreTextField.getText().trim(),
                identificacion,
                correoTextField.getText().trim(),
                password,
                roll
            );
                        
            Lista_Usuarios.guardarEnArchivoDeTexto();
                        
            nombreTextField.clear();
            identificacionTextField.clear();
            correoTextField.clear();
            passwordRegisterTextField.clear();
            confirmPasswordTextField.clear();
                        
            resetClickCounters();
                        
            String mensaje = roll.equals("administrador") 
                ? "¡Felicidades! Te has registrado como administrador."
                : "¡Felicidades! Ya haces parte de nuestros usuarios :)";
            
            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso", 
                "Registro realizado con éxito.\n" + mensaje);
                        
            mostrarVistaLogin();
            
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error en el registro", 
                "Ocurrió un error al registrar el usuario: " + e.getMessage());
        }
    }
    
    @FXML
    public void handleLogin(ActionEvent event) {        
        if (userTextField.getText().trim().isEmpty() || 
            passwordTextField.getText().trim().isEmpty()) {
            
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", 
                "Por favor, ingrese su correo y contraseña.");
            return;
        }
        
        String correo = userTextField.getText().trim();
        String contrasena = passwordTextField.getText().trim();
                
        Nodo_Usuario nodoUsuario = Lista_Usuarios.buscarCorreo(correo);
        
        if (nodoUsuario == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Usuario no encontrado", 
                "No existe un usuario registrado con este correo.");
            return;
        }
                
        if (!nodoUsuario.getUsuario().getContrasena().equals(contrasena)) {
            mostrarAlerta(Alert.AlertType.WARNING, "Contraseña incorrecta", 
                "La contraseña ingresada es incorrecta.");
            return;
        }
                        
        Administrador_Singleton.getAdministrador().setUsuarioActual(nodoUsuario);
        
        userTextField.clear();
        passwordTextField.clear();
                
        String nombreUsuario = nodoUsuario.getUsuario().getNombre();
        String roll = nodoUsuario.getUsuario().getRoll();
        mostrarAlerta(Alert.AlertType.INFORMATION, "Bienvenido", 
            "¡Bienvenido " + nombreUsuario + "!\nRol: " + roll);
                
        cargarVentanaCatalogo();
    }
    
    private void mostrarVistaLogin() {
        if (scrollContent != null && scrollContentRegister != null) {
            scrollContent.setVisible(true);
            scrollContent.setManaged(true);
            scrollContentRegister.setVisible(false);
            scrollContentRegister.setManaged(false);
        }
    }
    
    private void cargarVentanaCatalogo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Catalog_Section.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", 
                "No se pudo cargar la ventana del catálogo: " + e.getMessage());
        }
    }
    
    private boolean validarCorreo(String correo) {
        String patron = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(patron);
        return pattern.matcher(correo).matches();
    }
    
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
}
