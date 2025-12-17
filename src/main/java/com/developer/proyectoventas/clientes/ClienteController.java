package com.developer.proyectoventas.clientes;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

import java.net.URL;
import java.util.ResourceBundle;


public class ClienteController implements Initializable {
    @FXML private TextField txtDocumento;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDireccion;
    @FXML private ComboBox<String> comboGenero;
    private com.developer.proyectoventas.conexion.ConexionMongo ConexionMongo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboGenero.getItems().addAll("Masculino", "Femenino");
    }

    @FXML
    protected void btnGuardarClick() {
        try {
            MongoDatabase db = ConexionMongo.getInstancia().getDatabase("ventasdb");

            //definimos la colección
            MongoCollection<Document> coleccion = db.getCollection("Clientes");

            //crea el documento en formato JSON
            Document clientes = new Document();
            clientes.append("id", txtDocumento.getText());
            clientes.append("apellidos", (txtApellidos.getText()));
            clientes.append("nombres", txtNombre.getText());
            clientes.append("direccion",txtDireccion.getText());
            clientes.append("genero", comboGenero.getValue());

            //guardar en la BDD
            coleccion.insertOne(clientes);
            mostrarAlerta("Mensaje de confirmación", "Registro guardado con éxito.");

        } catch (Exception e) {

            mostrarAlerta("Error", e.getMessage());

        }
    }
    @FXML
    protected void btnNuevoClick () {
        txtDocumento.setText("");
        txtApellidos.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        comboGenero.setValue("null");
        txtDocumento.requestFocus();//ubica el cursor en este campo
    }

    @FXML
    protected void btnCerrarClick (Event event){
        //Obtenemos en stage actual de donde se lanza la orden de cerrar
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close(); //Cierra la ventana
    }

    private void mostrarAlerta (String titulo, String mensaje){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();

    }


}
