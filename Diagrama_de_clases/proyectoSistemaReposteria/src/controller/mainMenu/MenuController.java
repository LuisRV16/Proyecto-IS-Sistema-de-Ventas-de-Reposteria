package controller.mainMenu;

import java.io.IOException;

import controller.client.MenuClienteController;
import controller.product.ProductController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.SQLConnection;

public class MenuController {

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnProducto;

    @FXML
    void goToClientes(ActionEvent event) {

        Stage stage = (Stage) btnClientes.getScene().getWindow(); // Obtener la ventana actual
        stage.close();

        SQLConnection con = new SQLConnection();
        con.setUser("sa");
        con.setPassword("sasa");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/client/menuCliente.fxml"));
        MenuClienteController controller = loader.getController();
        Parent root;
        try {
            root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void goToProduct(ActionEvent event) {
       
       
        try {
            Stage stage = (Stage) btnProducto.getScene().getWindow(); // Obtener la ventana actual
            stage.close();
    
            SQLConnection con = new SQLConnection();
            con.setUser("sa");
            con.setPassword("sasa");
    
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/product/product.fxml"));
            Parent root;
            root = loader.load();
            ProductController controller = loader.getController();
            controller.setCon(con.getConnection());
            controller.inic();
            
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
