package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane footerPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Aplicar clip circular a las imágenes de personajes
        applyCircularClip(kuromiImageView);
        applyCircularClip(chococatImageView);
        applyCircularClip(badtzMaruImageView);
        applyCircularClip(twinStarsImageView);
        
        // Configurar el banner para que se ajuste al ancho disponible
        if (bannerImageView != null && footerPane != null) {
            bannerImageView.fitWidthProperty().bind(footerPane.widthProperty());
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
    
    public void handleLogin(ActionEvent event) {
        // TODO: Implementar lógica de login
    }
    
}
