package Controllers;

import Models.Nodo_Personaje;
import Models.Personaje;
import java.io.File;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Component_CharacterCard {
    
    @FXML
    private StackPane rootPane;
    
    @FXML
    private AnchorPane backgroundPane;
    
    @FXML
    private ImageView characterImageView;
    
    private Nodo_Personaje nodoPersonaje;
    private Runnable onCharacterClick;
    
    public void initialize() {
        // Aplicar clip circular a la imagen
        applyCircularClip();
    }
    
    public void setPersonaje(Nodo_Personaje nodoPersonaje) {
        this.nodoPersonaje = nodoPersonaje;
        if (nodoPersonaje != null && nodoPersonaje.getPersonaje() != null) {
            Personaje personaje = nodoPersonaje.getPersonaje();
            loadCharacterImage(personaje.getNombre());
            applyBackgroundColor(personaje.getColor());
        }
    }
    
    public Nodo_Personaje getPersonaje() {
        return nodoPersonaje;
    }
    
    public void setOnCharacterClick(Runnable onCharacterClick) {
        this.onCharacterClick = onCharacterClick;
        if (rootPane != null) {
            rootPane.setOnMouseClicked(e -> {
                if (onCharacterClick != null) {
                    onCharacterClick.run();
                }
            });
        }
    }
    
    public Node getRoot() {
        return rootPane;
    }
    
    private void loadCharacterImage(String nombrePersonaje) {
        try {
            // Mapear nombres a nombres de archivo
            String imageFileName = getImageFileName(nombrePersonaje);
            String imagePath = System.getProperty("user.dir") + "\\src\\Images\\Personajes\\" + 
                               getCharacterDirectoryName(nombrePersonaje) + "\\" + imageFileName;
            
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                characterImageView.setImage(image);
            } else {
                System.out.println("Imagen no encontrada: " + imagePath);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar imagen del personaje " + nombrePersonaje + ": " + e.getMessage());
        }
    }
    
    private String getImageFileName(String nombrePersonaje) {
        // Mapear nombres de personajes a nombres de archivo de logo
        switch (nombrePersonaje) {
            case "Hello Kitty":
                return "Kitty_Logo.jpg";
            case "Little Twin Stars":
                return "Little_Twin_Stars_logo_.jpg";
            case "Badtz-maru":
                return "Badtz-maru_Logo.jpg";
            case "My Melody":
                return "My_Melody_Logo.jpg";
            case "Chococat":
                return "Chococat_Logo.jpg";
            case "Cinnamoroll":
                return "Cinnamoroll_Logo.jpg";
            case "Keroppi":
                return "Keroppi_Logo.jpg";
            case "Kuromi":
                return "Kuromi_Logo.jpg";
            case "Pochacco":
                return "Pochacco_Logo.jpg";
            case "Pompompurin":
                return "Pompompurin_Logo.jpg";
            default:
                return nombrePersonaje.replace(" ", "_") + "_Logo.jpg";
        }
    }
    
    private String getCharacterDirectoryName(String nombrePersonaje) {
        // Mapear nombres de personajes a nombres de directorio
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
    
    private void applyBackgroundColor(String colorHex) {
        if (colorHex != null && !colorHex.isEmpty() && backgroundPane != null) {
            try {
                // Crear patrón de puntos (polka dots) usando CSS
                String backgroundColor = "#" + colorHex;
                Color baseColor = Color.web(backgroundColor);
                // Usar un color más claro para el fondo con puntos
                String lighterColor = "#" + colorHex + "80"; // Con transparencia
                
                backgroundPane.setStyle(
                    "-fx-background-color: " + backgroundColor + ";" +
                    "-fx-background-radius: 75px;" +
                    "-fx-border-radius: 75px;"
                );
                
                // Agregar patrón de puntos usando un patrón repetitivo
                // Esto se puede mejorar con un patrón SVG, pero por ahora usamos un color sólido
            } catch (Exception e) {
                System.out.println("Error al aplicar color de fondo: " + e.getMessage());
            }
        }
    }
    
    private void applyCircularClip() {
        if (characterImageView != null) {
            double radius = Math.min(characterImageView.getFitWidth(), characterImageView.getFitHeight()) / 2.0;
            Circle clip = new Circle(radius);
            clip.setCenterX(radius);
            clip.setCenterY(radius);
            characterImageView.setClip(clip);
        }
    }
    
    public static Component_CharacterCard create(Nodo_Personaje nodoPersonaje) {
        try {
            FXMLLoader loader = new FXMLLoader(Component_CharacterCard.class.getResource("/Views/Components/CharacterCard.fxml"));
            Node root = loader.load();
            Component_CharacterCard controller = loader.getController();
            controller.setPersonaje(nodoPersonaje);
            return controller;
        } catch (Exception e) {
            System.out.println("Error al crear CharacterCard: " + e.getMessage());
            return null;
        }
    }
}

