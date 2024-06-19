package model;

import java.io.IOException;

import controller.mainMenu.LoginFrameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login/login.fxml"));
        Parent root = loader.load();

        // Inicializa todos los componentes requeridos
        LoginFrameController controller = loader.getController();
        controller.inic();
        
        Scene scene = new Scene(root);
        stage.setTitle("Prueba");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
