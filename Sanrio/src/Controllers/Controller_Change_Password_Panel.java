package Controllers;

import Models.DataStructures.Administrador_Singleton;
import Models.DataStructures.LanguageManager;
import Models.DataStructures.Lista_Doble_Usuarios;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class Controller_Change_Password_Panel {
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private Label titleLabel;
    
    @FXML
    private Label closeButton;
    
    @FXML
    private Label currentPasswordLabel;
    
    @FXML
    private Label newPasswordLabel;
    
    @FXML
    private Label confirmPasswordLabel;
    
    @FXML
    private PasswordField currentPasswordField;
    
    @FXML
    private PasswordField newPasswordField;
    
    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML
    private Button saveButton;
    
    @FXML
    private Button cancelButton;
    
    private Administrador_Singleton administrador;
    private LanguageManager languageManager;
    private Lista_Doble_Usuarios listaUsuarios;
    private Runnable onClose;
    
    public void initialize() {
        administrador = Administrador_Singleton.getAdministrador();
        languageManager = LanguageManager.getInstance();
        listaUsuarios = administrador.getLista_usuarios();
        
        actualizarTextos();
    }
    
    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }
    
    public void actualizarTextos() {
        if (titleLabel != null) {
            titleLabel.setText(languageManager.getString("change.password.title"));
        }
        if (currentPasswordLabel != null) {
            currentPasswordLabel.setText(languageManager.getString("change.password.current"));
        }
        if (newPasswordLabel != null) {
            newPasswordLabel.setText(languageManager.getString("change.password.new"));
        }
        if (confirmPasswordLabel != null) {
            confirmPasswordLabel.setText(languageManager.getString("change.password.confirm"));
        }
        if (saveButton != null) {
            saveButton.setText(languageManager.getString("change.password.save"));
        }
        if (cancelButton != null) {
            cancelButton.setText(languageManager.getString("change.password.cancel"));
        }
    }
    
    @FXML
    private void handleClose() {
        if (onClose != null) {
            onClose.run();
        }
    }
    
    @FXML
    private void handleCancel() {
        limpiarCampos();
        if (onClose != null) {
            onClose.run();
        }
    }
    
    @FXML
    private void handleSave() {
        if (administrador.getUsuarioActual() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                languageManager.getString("alert.error"), 
                languageManager.getString("alert.login.required"));
            return;
        }
        
        String currentPassword = currentPasswordField.getText().trim();
        String newPassword = newPasswordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();
        
        // Validar campos vacíos
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                languageManager.getString("alert.fields.incomplete"), 
                languageManager.getString("alert.please.complete"));
            return;
        }
        
        // Validar contraseña actual
        String correoUsuario = administrador.getUsuarioActual().getUsuario().getCorreo();
        String contrasenaActual = administrador.getUsuarioActual().getUsuario().getContrasena();
        
        if (!currentPassword.equals(contrasenaActual)) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                languageManager.getString("alert.password.incorrect"), 
                languageManager.getString("change.password.current.incorrect"));
            return;
        }
        
        // Validar longitud de nueva contraseña
        if (newPassword.length() < 6) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                languageManager.getString("alert.password.too.short"), 
                languageManager.getString("alert.password.min.length"));
            return;
        }
        
        // Validar que las contraseñas nuevas coincidan
        if (!newPassword.equals(confirmPassword)) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                languageManager.getString("alert.passwords.no.match"), 
                languageManager.getString("alert.passwords.verify"));
            return;
        }
        
        // Validar que la nueva contraseña sea diferente a la actual
        if (newPassword.equals(currentPassword)) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                languageManager.getString("change.password.same.password"), 
                languageManager.getString("change.password.same.password.msg"));
            return;
        }
        
        // Actualizar contraseña
        try {
            administrador.getUsuarioActual().getUsuario().setContrasena(newPassword);
            listaUsuarios.guardarEnArchivoDeTexto();
            
            mostrarAlerta(Alert.AlertType.INFORMATION, 
                languageManager.getString("change.password.success"), 
                languageManager.getString("change.password.success.msg"));
            
            limpiarCampos();
            if (onClose != null) {
                onClose.run();
            }
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, 
                languageManager.getString("alert.error"), 
                languageManager.getString("change.password.error"));
        }
    }
    
    private void limpiarCampos() {
        if (currentPasswordField != null) {
            currentPasswordField.clear();
        }
        if (newPasswordField != null) {
            newPasswordField.clear();
        }
        if (confirmPasswordField != null) {
            confirmPasswordField.clear();
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

