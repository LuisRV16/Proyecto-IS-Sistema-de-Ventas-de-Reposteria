package model;

import java.io.IOException;

import controller.client.MenuClienteController;
import controller.product.ProductController;
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

    // @Override
    // public void start(Stage stage) throws IOException {
    //     ObservableList<Screen> screenSizes = Screen.getScreens();
    //     Stage secondStage = new Stage();
    //     Parent root = FXMLLoader.load(getClass().getResource("/view/client/agregaCliente.fxml"));
    //     Parent root2 = FXMLLoader.load(getClass().getResource("/view/sales/ShopCart.fxml"));
    //     Scene scene = new Scene(root);
    //     // Scene scene2 = new Scene(root2);
    //     stage.setTitle("1");
    //     stage.setScene(scene);
    //     secondStage.setTitle("2");
    //     // secondStage.setScene(scene2);
    //     stage.setResizable(false);
    //     if (screenSizes.size() > 1) {
    //         Rectangle2D bounds = screenSizes.get(1).getVisualBounds();
    //         secondStage.setX(bounds.getMinX());
    //         secondStage.setY(bounds.getMinY());
    //         secondStage.setMaximized(true);
    //         secondStage.setResizable(false);
    //         stage.setMaximized(true);
    //         stage.setResizable(false);
    //     }else{
    //         secondStage.setResizable(true);
    //     }
    //     stage.initStyle(StageStyle.UNDECORATED);
    //     stage.setMaximized(true);
        
    //     // secondStage.show();
    //     stage.show();
    // }

    // @Override
    // public void start(Stage stage) throws IOException {

    //     SQLConnection con = new SQLConnection();
    //     con.setUser("sa");
    //     con.setPassword("sasa");

    //     FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/product/product.fxml"));
    //     Parent root = loader.load();
    //     ProductController controller = loader.getController();
    //     // Manda la conexion a la base de datos
    //     controller.setCon(con.getConnection());
    //     controller.inic();
    //     // Inicializa todos los componentes requeridos
    //     Scene scene = new Scene(root);
    //     stage.setTitle("Prueba");
    //     stage.setScene(scene);
    //     stage.setResizable(false);
    //     stage.show();
    // }

    // public static void main(String[] args) {
    //     launch(args);  
    // }

    @Override
    public void start(Stage stage) throws IOException {

        SQLConnection con = new SQLConnection();
        con.setUser("sa");
        con.setPassword("sasa");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/client/menuCliente.fxml"));
        Parent root = loader.load();
        MenuClienteController controller = loader.getController();
        // Manda la conexion a la base de datos
        controller.setCon(con.getConnection());
        controller.inic();
        // Inicializa todos los componentes requeridos
        Scene scene = new Scene(root);
        stage.setTitle("Prueba");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);  
    }

}

