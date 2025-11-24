package Models;

import java.time.LocalDateTime;

public class Producto {
    
    private Personaje personaje;
    private String identificacion;
    private String nombre;
    private String[] categoria;
    private String descripcionIngles;
    private String descripcionEspanol;
    private float precio;
    private String correo_usuario;
    private LocalDateTime fecha_compra;
    
    public Producto() {
    }

    public Producto(Personaje personaje, String identificacion, String nombre, String[] categoria, String descripcionIngles, String descripcionEspanol, float precio) {
        this.personaje = personaje;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcionIngles = descripcionIngles;
        this.descripcionEspanol = descripcionEspanol;
        this.precio = precio;
    }
    
    public Personaje getPersonaje() {
        return personaje;
    }

    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String[] getCategoria() {
        return categoria;
    }

    public void setCategoria(String[] categoria) {
        this.categoria = categoria;
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

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }        

    public String getCorreo_usuario() {
        return (correo_usuario == null || correo_usuario.isEmpty()) ? "NULL" : correo_usuario;
    }

    public void setCorreo_usuario(String correo_usuario) {
        this.correo_usuario = correo_usuario;
    }        

    public LocalDateTime getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(LocalDateTime fecha_compra) {
        this.fecha_compra = fecha_compra;
    }      
}
