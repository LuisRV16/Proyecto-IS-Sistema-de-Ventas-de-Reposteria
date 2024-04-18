package model;

import java.io.IOException;

import controller.sales.ShoppingCartController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // ObservableList<Screen> screenSizes = Screen.getScreens();
        // screenSizes.forEach(screen -> {
        //     System.out.println(screen.getBounds());
        // System.out.println("???");
        // });

        Parent root = FXMLLoader.load(getClass().getResource("/view/client/agregaCliente.fxml"));
        // Parent root2 = FXMLLoader.load(getClass().getResource("/view/sales/ShoppingCart.fxml"));
        Scene scene = new Scene(root);
        // Scene scene2 = new Scene(root2);
        stage.setTitle("1");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();

        // if (screenSizes.size() > 1) {
        //     Rectangle2D bounds = screenSizes.get(1).getVisualBounds();
            
        //     Stage secondStage = new Stage();
        //     secondStage.setTitle("2");
        //     secondStage.setScene(scene2);
        //     secondStage.setX(bounds.getMinX());
        //     secondStage.setY(bounds.getMinY());
        //     secondStage.setMaximized(true);
        //     secondStage.setResizable(false);
        //     secondStage.show();
        // }

    }

    public static void main(String[] args) {
        System.out.println("Hol");
        launch(args);
        
    }

}
