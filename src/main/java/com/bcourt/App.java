package com.bcourt;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;

    // START APPLICATION
    @Override
    public void start(Stage stage) throws IOException {

        primaryStage = stage;

        // Halaman pertama adalah Login
        Parent root = loadFXML("login");

        scene = new Scene(root);

        stage.setTitle("B-Court");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }

    // PINDAH HALAMAN
    public static void setRoot(String fxml) throws IOException {

        Parent root = loadFXML(fxml);
        scene.setRoot(root);

        if (primaryStage != null) {
            primaryStage.sizeToScene();
        }
    }

    // LOAD FXML
    private static Parent loadFXML(String fxml) throws IOException {

        String filePath = fxml.endsWith(".fxml")
                ? fxml
                : fxml + ".fxml";

        URL fxmlLocation;

        if (filePath.startsWith("/")) {
            fxmlLocation = App.class.getResource(filePath);
        } else {
            fxmlLocation = App.class.getResource(
                "/com/bcourt/" + filePath
            );
        }

        if (fxmlLocation == null) {
            throw new IOException(
                "File FXML tidak ditemukan: " + filePath
            );
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);

        return loader.load();
    }

    // MAIN
    public static void main(String[] args) {
        launch();
    }
}