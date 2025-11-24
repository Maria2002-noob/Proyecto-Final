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
                writer.write(producto.getPersonaje() + ",; ");
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

                String[] atributos = linea.split(",; ");

                String nombre = atributos[0];
                String identificacion = atributos[1];
                String correo_usuario = atributos[2];

                Nodo_Personaje nodo_per = Administrador_Singleton.getAdministrador().getLista_personajes().buscarNombre(nombre);
                Nodo_Producto nodo_pro = Administrador_Singleton.getAdministrador().getLista_productos().buscarIdentificacion(identificacion);

                Personaje personaje = null;
                Producto producto = null;

                if (nodo_per != null && producto != null) {
                    personaje = nodo_per.getPersonaje();
                    producto = nodo_pro.getProducto();

                    Producto newProducto = new Producto(
                            personaje,
                            producto.getIdentificacion(),
                            producto.getNombre(),
                            producto.getCategoria(),
                            producto.getDescripcionIngles(),
                            producto.getDescripcionEspanol(),
                            producto.getPrecio()
                    );

                    if (!atributos[3].equals("NULL")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                        LocalDateTime fecha_compra = LocalDateTime.parse(atributos[0], formatter);
                        newProducto.setFecha_compra(fecha_compra);
                    }

                    newProducto.setCorreo_usuario(correo_usuario);

                    setPushProducto(stack, newProducto);
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
}
