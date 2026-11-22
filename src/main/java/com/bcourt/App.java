
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


    // ==========================================
    // START APPLICATION
    // ==========================================

    @Override
    public void start(Stage stage) throws IOException {

        primaryStage = stage;

        // Membuka halaman Dashboard Admin
        Parent root = loadFXML(
            "/com/bcourt/admin/dashboard-admin.fxml"
        );

        scene = new Scene(root);

        stage.setTitle("B-Court");
        stage.setScene(scene);
        stage.setResizable(false);

        // Menyesuaikan ukuran jendela dengan halaman Login
        stage.sizeToScene();

        stage.show();
    }


    // ==========================================
    // SET ROOT / PINDAH HALAMAN
    // ==========================================

    public static void setRoot(String fxml) throws IOException {

        Parent root = loadFXML(fxml);

        scene.setRoot(root);

        // Menyesuaikan ukuran jendela dengan halaman baru
        if (primaryStage != null) {
            primaryStage.sizeToScene();
        }
    }


    // ==========================================
    // LOAD FXML
    // ==========================================

    private static Parent loadFXML(String fxml) throws IOException {

        URL fxmlLocation = App.class.getResource(fxml);

        if (fxmlLocation == null) {

            throw new IOException(
                "File FXML tidak ditemukan: " + fxml
            );
        }

        FXMLLoader fxmlLoader = new FXMLLoader(
            fxmlLocation
        );

        return fxmlLoader.load();
    }


    // ==========================================
    // MAIN
    // ==========================================

    public static void main(String[] args) {

        launch();
    }

}
