package Models.DataStructures;

import Models.Nodo_Usuario;
import Models.Usuario;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Lista_Doble_Usuarios {

    private Nodo_Usuario cabeza;

    public Lista_Doble_Usuarios() {
        this.cabeza = null;
    }

    public Nodo_Usuario getCabeza() {
        return cabeza;
    }

    public void setCabeza(Nodo_Usuario cabeza) {
        this.cabeza = cabeza;
    }

    public void vaciarLista() {
        cabeza = null;
    }

    public void mostrarAlerta(Alert.AlertType alertType, String tit, String mj) {
        Alert a = new Alert(alertType);
        a.setTitle(tit);
        a.setContentText(mj);
        a.showAndWait();
    }

    public Nodo_Usuario buscarCorreo(String correo) {
        if (cabeza == null) {
            return null;
        } else {
            Nodo_Usuario aux = getCabeza();
            while (aux != null) {
                if (aux.getUsuario().getCorreo().equalsIgnoreCase(correo)) {
                    return aux;
                } else {
                    aux = aux.getSiguiente();
                }
            }
            return null;
        }
    }

    public ObservableList<Nodo_Usuario> getUsuariors() {
        ObservableList<Nodo_Usuario> todos = FXCollections.observableArrayList();
        if (cabeza == null) {
            return todos;
        }

        Nodo_Usuario actual = cabeza;

        do {
            todos.add(actual);
            actual = actual.getSiguiente();

        } while (actual != null && actual != cabeza);

        return todos;
    }

    public Nodo_Usuario getUltimo() {

        if (cabeza == null) {
            return null;
        } else {
            Nodo_Usuario aux = cabeza;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            return aux;
        }
    }

    public Nodo_Usuario crearUsuario(TextField txtNombre, TextField txtIdentificacion, TextField txtCorreo, PasswordField txtContrasena, String roll) {

        Nodo_Usuario buscar = buscarCorreo(txtCorreo.getText());

        try {

            if (buscar != null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Importante..!", "Ya existe un usuario con este correo.");
                return null;
            } else {
                Usuario usuario = new Usuario(txtNombre.getText(), Integer.parseInt(txtIdentificacion.getText()), txtCorreo.getText(), txtContrasena.getText(), roll);
                Nodo_Usuario nuevoUsuario = new Nodo_Usuario(usuario);

                mostrarAlerta(Alert.AlertType.CONFIRMATION, "Dialogo de confirmación", "Registro realizado con exito...!\n"
                        + "Felicidades...! ya haces parte de nuestros usuario :)");
                txtNombre.setText("");
                txtIdentificacion.setText("");
                txtCorreo.setText("");
                txtContrasena.setText("");
                return nuevoUsuario;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void agregarUsuario(String nombre, int identificacion, String correo, String contrasena, String roll) {

        Usuario usuario = new Usuario(nombre, identificacion, correo, contrasena, roll);
        Nodo_Usuario newUser = new Nodo_Usuario(usuario);

        if (newUser != null) {
            if (cabeza == null) {
                cabeza = newUser;
            } else {
                Nodo_Usuario ultimo = getUltimo();
                ultimo.setSiguiente(newUser);
                newUser.setAnterior(ultimo);
            }
        }
    }

    public void agregarUsuario(TextField txtNombre, TextField txtIdentificacion, TextField txtCorreo, PasswordField txtContrasena, String roll) {

        Nodo_Usuario nuevoUsuario = crearUsuario(txtNombre, txtIdentificacion, txtCorreo, txtContrasena, roll);

        if (nuevoUsuario != null) {
            if (getCabeza() == null) {
                setCabeza(nuevoUsuario);
            } else {
                nuevoUsuario.setSiguiente(getCabeza());
                getCabeza().setAnterior(nuevoUsuario);
                setCabeza(nuevoUsuario);
            }
        }
    }

    public void guardarEnArchivoDeTexto() {

        String direccion = System.getProperty("user.dir") + "\\src\\ArchivesTXT\\Usuarios.txt";

        Path archivo = Paths.get(direccion);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo.toFile(), false))) {
            Nodo_Usuario actual = cabeza;

            while (actual != null) {

                writer.write(actual.getUsuario().getNombre() + ", ");
                writer.write(actual.getUsuario().getIdentificacion() + ", ");
                writer.write(actual.getUsuario().getCorreo()+ ", ");
                writer.write(actual.getUsuario().getContrasena() + ", ");
                writer.write(actual.getUsuario().getRoll());
                
                writer.newLine();

                actual = actual.getSiguiente();
            }
        } catch (IOException e) {
            System.out.println("A ocurrido un error: " + e.getMessage());
        }
    }

    public void cargarDesdeArchivoDeTexto() {

        String direction = System.getProperty("user.dir") + "\\src\\ArchivesTXT\\Usuarios.txt";

        Path archivo = Paths.get(direction);

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo.toFile()))) {

            String linea;

            vaciarLista();

            while ((linea = reader.readLine()) != null) {

                String[] atributes = linea.split(", ");
                
                String nombre = atributes[0];
                int identificacion = Integer.parseInt(atributes[1]);
                String correo = atributes[2];
                String contrasena = atributes[3];
                String roll = atributes[4];

                agregarUsuario(nombre, identificacion, correo, contrasena, roll);
            }
        } catch (IOException e) {
            System.out.println("A ocurrido un error: " + e.getMessage());
        }
    }
}
