package Models.DataStructures;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class LanguageManager {
    
    private static LanguageManager instance;
    private Locale currentLocale;
    private ResourceBundle resourceBundle;
    
    private LanguageManager() {        
        currentLocale = new Locale("es", "ES");
        resourceBundle = loadResourceBundle("Resources.messages", currentLocale);
    }
    
    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }
    
    public void setLanguage(String language) {
        if ("en".equalsIgnoreCase(language)) {
            currentLocale = new Locale("en", "US");
        } else {
            currentLocale = new Locale("es", "ES");
        }
        resourceBundle = loadResourceBundle("Resources.messages", currentLocale);
    }
    
    /**
     * Carga un ResourceBundle con codificación UTF-8 para soportar caracteres especiales
     * como acentos, ñ, etc.
     * 
     * IMPORTANTE: Los archivos .properties deben estar guardados en UTF-8
     * para que los caracteres especiales se rendericen correctamente.
     */
    private ResourceBundle loadResourceBundle(String baseName, Locale locale) {
        ClassLoader classLoader = LanguageManager.class.getClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        
        // Construir el nombre del archivo de recursos
        String resourceName = baseName.replace('.', '/') + "_" + locale.getLanguage() + ".properties";
        InputStream stream = classLoader.getResourceAsStream(resourceName);
        
        if (stream == null) {
            // Intentar sin el sufijo de idioma (archivo por defecto)
            resourceName = baseName.replace('.', '/') + ".properties";
            stream = classLoader.getResourceAsStream(resourceName);
        }
        
        if (stream != null) {
            try {
                // Leer el archivo con codificación UTF-8
                // Usar try-with-resources para asegurar el cierre correcto
                try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                    return new PropertyResourceBundle(reader);
                }
            } catch (IOException e) {
                System.err.println("Error al leer el archivo de recursos con UTF-8: " + e.getMessage());
                e.printStackTrace();
                // Fallback a ResourceBundle estándar
                return ResourceBundle.getBundle(baseName, locale);
            }
        } else {
            System.err.println("No se pudo encontrar el archivo de recursos: " + resourceName);
            // Fallback a ResourceBundle estándar
            return ResourceBundle.getBundle(baseName, locale);
        }
    }
    
    public String getCurrentLanguage() {
        return currentLocale.getLanguage();
    }
    
    public String getString(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (Exception e) {
            System.out.println("Clave no encontrada: " + key);
            return key;
        }
    }
    
    public String getString(String key, Object... args) {
        try {
            String message = resourceBundle.getString(key);
            return String.format(message, args);
        } catch (Exception e) {
            System.out.println("Clave no encontrada: " + key);
            return key;
        }
    }
    
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
    
    public Locale getCurrentLocale() {
        return currentLocale;
    }
    
    /**
     * Formatea el precio según el idioma actual.
     * Si el idioma es español, convierte a pesos colombianos (COP) usando la tasa 1 USD = 3800 COP.
     * Si el idioma es inglés, muestra en dólares (USD).
     * 
     * @param precioUSD El precio en dólares
     * @return String formateado con el precio y símbolo de moneda
     */
    public String formatPrice(float precioUSD) {
        String language = getCurrentLanguage();
        if ("es".equals(language)) {
            // Convertir a pesos colombianos: 1 USD = 3800 COP
            float precioCOP = precioUSD * 3800.0f;
            // Formatear con separador de miles
            return String.format("$%,.0f COP", precioCOP);
        } else {
            // Mostrar en dólares
            return String.format("$%.2f USD", precioUSD);
        }
    }
}

