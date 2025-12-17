package com.developer.proyectoventas.conexion;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class ConexionMongo {
    private static MongoClient mongoClient;

    //constructor
    private ConexionMongo() {

    }

    public static MongoClient getInstancia() {
        if(mongoClient == null) {
            String connectionString = "mongodb://localhost:27017/ventasdb?retryWrites=true&w=majority";

            ConnectionString connString = new ConnectionString(connectionString);

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connString)
                    .build();

            mongoClient = MongoClients.create(settings);
        }
        return mongoClient;
    }
}
