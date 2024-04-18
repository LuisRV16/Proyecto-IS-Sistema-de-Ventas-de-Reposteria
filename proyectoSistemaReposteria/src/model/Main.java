package model;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        ObservableList<Screen> screenSizes = Screen.getScreens();
        Stage secondStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/client/agregaCliente.fxml"));
        Parent root2 = FXMLLoader.load(getClass().getResource("/view/sales/ShopCart.fxml"));
        Scene scene = new Scene(root);
        Scene scene2 = new Scene(root2);
        stage.setTitle("1");
        stage.setScene(scene);
        secondStage.setTitle("2");
        secondStage.setScene(scene2);
        stage.setResizable(false);
        if (screenSizes.size() > 1) {
            Rectangle2D bounds = screenSizes.get(1).getVisualBounds();
            secondStage.setX(bounds.getMinX());
            secondStage.setY(bounds.getMinY());
            secondStage.setMaximized(true);
            secondStage.setResizable(false);
            stage.setMaximized(true);
            stage.setResizable(false);
        }else{
            secondStage.setResizable(true);
        }
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        
        // secondStage.show();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);  
    }

}
