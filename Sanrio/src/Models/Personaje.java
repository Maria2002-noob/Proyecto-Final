package Models;

public class Personaje {

    private String nombre;
    private String descripcionIngles;
    private String descripcionEspanol;
    private String color;

    public Personaje() {
    }

    public Personaje(String name, String descripcionIngles, String descripcionEspanol, String color) {
        this.nombre = name;
        this.descripcionIngles = descripcionIngles;
        this.descripcionEspanol = descripcionEspanol;
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionIngles() {
        return descripcionIngles;
    }

    public void setDescripcionIngles(String descripcionIngles) {
        this.descripcionIngles = descripcionIngles;
    }

    public String getDescripcionEspanol() {
        return descripcionEspanol;
    }

    public void setDescripcionEspanol(String descripcionEspanol) {
        this.descripcionEspanol = descripcionEspanol;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
