module com.developer.proyectoventas {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires jdk.jfr;
    requires java.desktop;
    requires org.mongodb.bson;
    requires javafx.base;
    requires javafx.graphics;


    opens com.developer.proyectoventas to javafx.fxml;
    opens com.developer.proyectoventas.productos to javafx.fxml;
    opens com.developer.proyectoventas.clientes to javafx.fxml;
    opens com.developer.proyectoventas.listado to javafx.fxml;
    exports com.developer.proyectoventas;

}