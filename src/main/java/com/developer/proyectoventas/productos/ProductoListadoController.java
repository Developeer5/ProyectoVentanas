package com.developer.proyectoventas.productos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.bson.Document;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductoListadoController implements Initializable {

    @FXML private TableView<Producto> tblProductos;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, String> colDescripcion;

    private com.developer.proyectoventas.conexion.ConexionMongo ConexionMongo;
    private ObservableList<Producto> listaProductos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar las columnas de la tabla - IMPORTANTE: los nombres deben coincidir con los getters
        colNombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        colPrecio.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPrecio()));
        colDescripcion.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescripcion()));

        // Cargar los datos
        cargarProductos();
    }

    private void cargarProductos() {
        try {
            MongoDatabase db = ConexionMongo.getInstancia().getDatabase("ventasdb");
            MongoCollection<Document> coleccion = db.getCollection("productos");

            listaProductos = FXCollections.observableArrayList();

            // Contar documentos
            long count = coleccion.countDocuments();
            System.out.println("Total de productos en BD: " + count);

            // Recorrer todos los documentos de la colección
            for (Document doc : coleccion.find()) {
                System.out.println("Documento encontrado: " + doc.toJson());
                Producto producto = new Producto(
                        doc.getString("nombre"),
                        doc.getDouble("precio"),
                        doc.getString("descripcion")
                );
                listaProductos.add(producto);
            }

            System.out.println("Productos cargados: " + listaProductos.size());

            // Asignar la lista a la tabla
            tblProductos.setItems(listaProductos);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al cargar productos: " + e.getMessage());
        }
    }

    @FXML
    protected void btnActualizarClick() {
        cargarProductos();
        mostrarAlerta("Información", "Lista de productos actualizada.");
    }

    @FXML
    protected void btnCerrarClick(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    // Clase interna para representar un Producto
    public static class Producto {
        private String nombre;
        private Double precio;
        private String descripcion;

        public Producto(String nombre, Double precio, String descripcion) {
            this.nombre = nombre;
            this.precio = precio;
            this.descripcion = descripcion;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Double getPrecio() {
            return precio;
        }

        public void setPrecio(Double precio) {
            this.precio = precio;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
    }
}