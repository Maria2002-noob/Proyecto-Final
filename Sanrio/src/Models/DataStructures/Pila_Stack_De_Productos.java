package Models.DataStructures;

import Models.Nodo_Personaje;
import Models.Nodo_Producto;
import Models.Personaje;
import Models.Producto;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Pila_Stack_De_Productos {

    private final Stack<Producto> productos_en_carrito;
    private final Stack<Producto> producto_historial;
    private final Stack<Producto> productos_favoritos;

    public Pila_Stack_De_Productos() {
        this.productos_en_carrito = new Stack<>();
        this.producto_historial = new Stack<>();
        this.productos_favoritos = new Stack<>();
    }

    public Stack<Producto> getProductos_en_carrito() {
        return productos_en_carrito;
    }

    public Stack<Producto> getProducto_historial() {
        return producto_historial;
    }

    public Stack<Producto> getProductos_favoritos() {
        return productos_favoritos;
    }

    public void setPushProducto(Stack<Producto> stack, Producto producto) {
        if (!stack.contains(producto)) {
            stack.push(producto);
        } else {
            System.out.println("Ya se registró antes este producto.");
        }
    }

    public Producto obtenerProductoPorCorreoYNombre(Stack<Producto> stack, String correo, String identificacion) {
        for (Producto producto : stack) {
            if (producto.getCorreo_usuario().equals(correo) && identificacion.equals(producto.getIdentificacion())) {
                return producto;
            }
        }
        return null;
    }

    public void eliminarPorCorreoYIdntificacion(Stack<Producto> stack, String correo, String identificacion) {
        Producto producto = obtenerProductoPorCorreoYNombre(stack, correo, identificacion);
        if (!stack.isEmpty()) {
            if (producto != null && stack.remove(producto)) {
                JOptionPane.showMessageDialog(null, "Producto eliminado!");
            } else {
                JOptionPane.showMessageDialog(null, "El Producto no existe!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay Productos registrados!");
        }
    }

    public Stack<Producto> duplicarStack(Stack<Producto> stack) {
        return new Stack<Producto>() {
            {
                addAll(stack);
            }
        };
    }

    public void guardarEnArchivoTXT(Stack<Producto> stack, String nombre_archivo) {

        String url = System.getProperty("user.dir") + "\\src\\ArchivesTXT\\" + nombre_archivo;

        Path path = Paths.get(url);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), false))) {
            Stack<Producto> productos = stack;

            for (Producto producto : productos) {
                String nombrePersonaje = (producto.getPersonaje() != null) ? producto.getPersonaje().getNombre() : "NULL";
                writer.write(nombrePersonaje + ",; ");
                writer.write(producto.getIdentificacion() + ",; ");
                writer.write(producto.getCorreo_usuario() + ",; ");

                if (producto.getFecha_compra() == null) {
                    writer.write("NULL");
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    String date = formatter.format(producto.getFecha_compra());
                    writer.write(date);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            Logger.getLogger(Pila_Stack_De_Productos.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void cargarDesdeArchivoTXT(Stack<Producto> stack, String nombre_archivo) {

        String url = System.getProperty("user.dir") + "\\src\\ArchivesTXT\\" + nombre_archivo;

        Path path = Paths.get(url);

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {

            String linea;

            if (!stack.isEmpty()) {
                stack.clear();
            }

            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] atributos = linea.split(",; ");

                if (atributos.length < 3) {
                    System.out.println("Línea inválida en archivo: " + linea);
                    continue;
                }

                String nombre = atributos[0].trim();
                String identificacion = atributos[1].trim();
                String correo_usuario = atributos[2].trim();

                Nodo_Personaje nodo_per = Administrador_Singleton.getAdministrador().getLista_personajes().buscarNombre(nombre);
                Nodo_Producto nodo_pro = Administrador_Singleton.getAdministrador().getLista_productos().buscarIdentificacion(identificacion);

                if (nodo_per != null && nodo_pro != null) {
                    Personaje personaje = nodo_per.getPersonaje();
                    Producto producto = nodo_pro.getProducto();

                    Producto newProducto = new Producto(
                            personaje,
                            producto.getIdentificacion(),
                            producto.getNombre(),
                            producto.getCategoria(),
                            producto.getDescripcionIngles(),
                            producto.getDescripcionEspanol(),
                            producto.getPrecio()
                    );

                    if (atributos.length > 3 && !atributos[3].trim().equals("NULL")) {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                            LocalDateTime fecha_compra = LocalDateTime.parse(atributos[3].trim(), formatter);
                            newProducto.setFecha_compra(fecha_compra);
                        } catch (Exception e) {
                            System.out.println("Error al parsear fecha: " + atributos[3] + " - " + e.getMessage());
                        }
                    }

                    newProducto.setCorreo_usuario(correo_usuario);
                    System.out.println("Producto cargado: " + newProducto.getNombre() + " - Usuario: " + newProducto.getCorreo_usuario() + " - ID: " + newProducto.getIdentificacion());

                    setPushProducto(stack, newProducto);
                } else {
                    System.out.println("No se encontró personaje o producto para: " + nombre + " - " + identificacion);
                    if (nodo_per == null) {
                        System.out.println("  Personaje no encontrado: " + nombre);
                    }
                    if (nodo_pro == null) {
                        System.out.println("  Producto no encontrado: " + identificacion);
                    }
                }
            }
        } catch (IOException e) {
            Logger.getLogger(Pila_Stack_De_Productos.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void saveDataToFileTXTCartShop() {
        guardarEnArchivoTXT(productos_en_carrito, "Productos_Carrito.txt");
    }

    public void loadDataFromFileTXTCarShop() {
        cargarDesdeArchivoTXT(productos_en_carrito, "Productos_Carrito.txt");
    }

    public void saveDataToFileTXTHistory() {
        guardarEnArchivoTXT(producto_historial, "Historial.txt");
    }

    public void loadDataFromFileTXTHistory() {
        cargarDesdeArchivoTXT(producto_historial, "Historial.txt");
    }

    public void saveDataToFileTXTFavorites() {
        guardarEnArchivoTXT(productos_favoritos, "Productos_Favoritos.txt");
    }

    public void loadDataFromFileTXTFavorites() {
        cargarDesdeArchivoTXT(productos_favoritos, "Productos_Favoritos.txt");
    }

    public void loadDataFromFileTXT() {
        loadDataFromFileTXTCarShop();
        loadDataFromFileTXTFavorites();
        loadDataFromFileTXTHistory();
    }        
    
    public boolean estaEnCarrito(String identificacion, String correoUsuario) {
        return obtenerProductoPorCorreoYNombre(productos_en_carrito, correoUsuario, identificacion) != null;
    }
    
    public boolean estaEnWishlist(String identificacion, String correoUsuario) {
        return obtenerProductoPorCorreoYNombre(productos_favoritos, correoUsuario, identificacion) != null;
    }
    
    public Stack<Producto> obtenerProductosCarritoPorUsuario(String correoUsuario) {
        Stack<Producto> productosUsuario = new Stack<>();
        System.out.println("Buscando productos para usuario: " + correoUsuario);
        System.out.println("Total productos en carrito: " + productos_en_carrito.size());
        for (Producto producto : productos_en_carrito) {
            String correoProducto = producto.getCorreo_usuario();
            System.out.println("Producto: " + producto.getNombre() + " - Correo: " + correoProducto);
            if (correoProducto != null && correoProducto.equals(correoUsuario)) {
                productosUsuario.push(producto);
                System.out.println("Producto agregado a la lista del usuario");
            }
        }
        System.out.println("Productos encontrados para usuario: " + productosUsuario.size());
        return productosUsuario;
    }
    
    public Stack<Producto> obtenerProductosWishlistPorUsuario(String correoUsuario) {
        Stack<Producto> productosUsuario = new Stack<>();
        for (Producto producto : productos_favoritos) {
            String correoProducto = producto.getCorreo_usuario();
            // getCorreo_usuario() puede devolver "NULL" si es null o vacío
            if (correoProducto != null && !correoProducto.equals("NULL") && correoProducto.equals(correoUsuario)) {
                productosUsuario.push(producto);
            }
        }
        return productosUsuario;
    }
    
    public boolean eliminarDeCarrito(String identificacion, String correoUsuario) {
        Producto producto = obtenerProductoPorCorreoYNombre(productos_en_carrito, correoUsuario, identificacion);
        if (producto != null) {
            productos_en_carrito.remove(producto);
            saveDataToFileTXTCartShop();
            return true;
        }
        return false;
    }
    
    public boolean eliminarDeWishlist(String identificacion, String correoUsuario) {
        Producto producto = obtenerProductoPorCorreoYNombre(productos_favoritos, correoUsuario, identificacion);
        if (producto != null) {
            productos_favoritos.remove(producto);
            saveDataToFileTXTFavorites();
            return true;
        }
        return false;
    }
    
    public boolean moverDeWishlistACarrito(String identificacion, String correoUsuario) {
        Producto producto = obtenerProductoPorCorreoYNombre(productos_favoritos, correoUsuario, identificacion);
        if (producto != null) {            
            if (estaEnCarrito(identificacion, correoUsuario)) {
                return false;
            }            
            productos_favoritos.remove(producto);            
            producto.setCorreo_usuario(correoUsuario);
            setPushProducto(productos_en_carrito, producto);
            saveDataToFileTXTCartShop();
            saveDataToFileTXTFavorites();
            return true;
        }
        return false;
    }
    
    public boolean moverDeCarritoAWishlist(String identificacion, String correoUsuario) {
        Producto producto = obtenerProductoPorCorreoYNombre(productos_en_carrito, correoUsuario, identificacion);
        if (producto != null) {            
            if (estaEnWishlist(identificacion, correoUsuario)) {
                return false;
            }            
            productos_en_carrito.remove(producto);            
            producto.setCorreo_usuario(correoUsuario);
            setPushProducto(productos_favoritos, producto);            
            saveDataToFileTXTCartShop();
            saveDataToFileTXTFavorites();
            return true;
        }
        return false;
    }
    
    public boolean agregarAlCarrito(Producto producto, String correoUsuario) {
        if (producto == null || correoUsuario == null) {
            return false;
        }        
        if (estaEnCarrito(producto.getIdentificacion(), correoUsuario)) {
            return false;
        }        
        if (estaEnWishlist(producto.getIdentificacion(), correoUsuario)) {            
            return moverDeWishlistACarrito(producto.getIdentificacion(), correoUsuario);
        }        
        Producto nuevoProducto = new Producto(
            producto.getPersonaje(),
            producto.getIdentificacion(),
            producto.getNombre(),
            producto.getCategoria(),
            producto.getDescripcionIngles(),
            producto.getDescripcionEspanol(),
            producto.getPrecio()
        );
        nuevoProducto.setCorreo_usuario(correoUsuario);
        setPushProducto(productos_en_carrito, nuevoProducto);
        saveDataToFileTXTCartShop();
        return true;
    }
    
    public boolean agregarAWishlist(Producto producto, String correoUsuario) {
        if (producto == null || correoUsuario == null) {
            return false;
        }        
        if (estaEnWishlist(producto.getIdentificacion(), correoUsuario)) {
            return false;
        }        
        if (estaEnCarrito(producto.getIdentificacion(), correoUsuario)) {            
            return moverDeCarritoAWishlist(producto.getIdentificacion(), correoUsuario);
        }        
        Producto nuevoProducto = new Producto(
            producto.getPersonaje(),
            producto.getIdentificacion(),
            producto.getNombre(),
            producto.getCategoria(),
            producto.getDescripcionIngles(),
            producto.getDescripcionEspanol(),
            producto.getPrecio()
        );
        nuevoProducto.setCorreo_usuario(correoUsuario);
        setPushProducto(productos_favoritos, nuevoProducto);
        saveDataToFileTXTFavorites();
        return true;
    }
    
    public boolean moverCarritoAHistorial(String correoUsuario) {
        if (correoUsuario == null) {
            return false;
        }
        
        Stack<Producto> productosCarrito = obtenerProductosCarritoPorUsuario(correoUsuario);
        
        if (productosCarrito.isEmpty()) {
            return false;
        }
        
        LocalDateTime fechaCompra = LocalDateTime.now();
        
        for (Producto producto : productosCarrito) {
            Producto productoHistorial = new Producto(
                producto.getPersonaje(),
                producto.getIdentificacion(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getDescripcionIngles(),
                producto.getDescripcionEspanol(),
                producto.getPrecio()
            );
            productoHistorial.setCorreo_usuario(correoUsuario);
            productoHistorial.setFecha_compra(fechaCompra);
            
            setPushProducto(producto_historial, productoHistorial);
            productos_en_carrito.remove(producto);
        }
        
        saveDataToFileTXTHistory();
        saveDataToFileTXTCartShop();
        
        return true;
    }
}
