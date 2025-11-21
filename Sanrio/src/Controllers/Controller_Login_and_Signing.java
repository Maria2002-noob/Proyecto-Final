package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class Controller_Login_and_Signing implements Initializable {

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
        applyCircularClip(kuromiImageView);
        applyCircularClip(chococatImageView);
        applyCircularClip(badtzMaruImageView);
        applyCircularClip(twinStarsImageView);                
        applyCircularClip(helloKittyImageView);
        applyCircularClip(cinnamorollImageView);
        applyCircularClip(pochaccoImageView);
                
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
    public void handleLogin(ActionEvent event) {
        
    }
    
    @FXML
    public void handleToggleView(MouseEvent event) {
        if (scrollContent != null && scrollContentRegister != null) {
            boolean isLoginVisible = scrollContent.isVisible();
            scrollContent.setVisible(!isLoginVisible);
            scrollContent.setManaged(!isLoginVisible);
            scrollContentRegister.setVisible(isLoginVisible);
            scrollContentRegister.setManaged(isLoginVisible);
        }
    }
    
    @FXML
    public void handleCancel(ActionEvent event) {        
        if (scrollContent != null && scrollContentRegister != null) {
            scrollContent.setVisible(true);
            scrollContent.setManaged(true);
            scrollContentRegister.setVisible(false);
            scrollContentRegister.setManaged(false);
        }
    }
    
    @FXML
    public void handleAccept(ActionEvent event) {
        // TODO: Implementar lógica de registro
    }
    
}
