package com.developer.proyectoventas.clientes;

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

public class ClienteListadoController implements Initializable {

    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, String> colDocumento;
    @FXML private TableColumn<Cliente, String> colApellidos;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colDireccion;
    @FXML private TableColumn<Cliente, String> colGenero;

    private com.developer.proyectoventas.conexion.ConexionMongo ConexionMongo;
    private ObservableList<Cliente> listaClientes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar las columnas de la tabla
        colDocumento.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDocumento()));
        colApellidos.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getApellidos()));
        colNombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        colDireccion.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDireccion()));
        colGenero.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getGenero()));

        // Cargar los datos
        cargarClientes();
    }

    private void cargarClientes() {
        try {
            MongoDatabase db = ConexionMongo.getInstancia().getDatabase("ventasdb");
            MongoCollection<Document> coleccion = db.getCollection("Clientes");

            listaClientes = FXCollections.observableArrayList();

            // Contar documentos
            long count = coleccion.countDocuments();
            System.out.println("Total de clientes en BD: " + count);

            // Recorrer todos los documentos de la colección
            for (Document doc : coleccion.find()) {
                System.out.println("Documento encontrado: " + doc.toJson());
                Cliente cliente = new Cliente(
                        doc.getString("id"),
                        doc.getString("apellidos"),
                        doc.getString("nombres"),
                        doc.getString("direccion"),
                        doc.getString("genero")
                );
                listaClientes.add(cliente);
            }

            System.out.println("Clientes cargados: " + listaClientes.size());

            // Asignar la lista a la tabla
            tblClientes.setItems(listaClientes);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al cargar clientes: " + e.getMessage());
        }
    }

    @FXML
    protected void btnActualizarClick() {
        cargarClientes();
        mostrarAlerta("Información", "Lista de clientes actualizada.");
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

    // Clase interna para representar un Cliente
    public static class Cliente {
        private String documento;
        private String apellidos;
        private String nombre;
        private String direccion;
        private String genero;

        public Cliente(String documento, String apellidos, String nombre, String direccion, String genero) {
            this.documento = documento;
            this.apellidos = apellidos;
            this.nombre = nombre;
            this.direccion = direccion;
            this.genero = genero;
        }

        public String getDocumento() {
            return documento;
        }

        public void setDocumento(String documento) {
            this.documento = documento;
        }

        public String getApellidos() {
            return apellidos;
        }

        public void setApellidos(String apellidos) {
            this.apellidos = apellidos;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getGenero() {
            return genero;
        }

        public void setGenero(String genero) {
            this.genero = genero;
        }
    }
}