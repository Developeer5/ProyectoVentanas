package com.developer.proyectoventas.productos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;


public class ProductoController {
    @FXML private Label lblMensaje;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextArea txtDescripcion;
    private com.developer.proyectoventas.conexion.ConexionMongo ConexionMongo;


    @FXML
    protected void btnGuardarClick(){
        lblMensaje.setText("");

        try {
            MongoDatabase db = ConexionMongo.getInstancia().getDatabase("ventasdb");

            //definimos la colección
            MongoCollection<Document> coleccion = db.getCollection("productos");

            //crea el documento en formato JSON
            Document producto = new Document();
            producto.append("nombre", txtNombre.getText());
            producto.append("precio", Double.parseDouble(txtPrecio.getText()));
            producto.append("descripcion", txtDescripcion.getText());

            //guardar en la BDD
            coleccion.insertOne(producto);
            mostrarAlerta("Mensaje de confirmación", "Registro guardado con éxito.");
            lblMensaje.setText("Producto guardado con éxito!!!");
        } catch (Exception e){
            lblMensaje.setText(e.getMessage());
            mostrarAlerta("Error", e.getMessage());
        }

    }

    @FXML
    protected void btnNuevoClick(){
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("0");
        txtNombre.requestFocus();//ubica el cursor en este campo
    }

    @FXML
    protected void btnCerrarClick(Event event) {
        //Obtenemos en stage actual de donde se lanza la orden de cerrar
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close(); //Cierra la ventana
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();

    }
}