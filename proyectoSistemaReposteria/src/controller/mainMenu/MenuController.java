package controller.mainMenu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import controller.client.MenuClienteController;
import controller.product.ProductController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnProducto;

    @FXML
    private Button btnClose;

    private String nombre;

    private String apellido1;

    private String apellido2;

    Connection con;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToClientes(ActionEvent event) {
        try {
            Stage stage = (Stage) btnClientes.getScene().getWindow(); // Obtener la ventana actual
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/client/menuCliente.fxml"));
            Parent root = loader.load();
            
            MenuClienteController controller = loader.getController();
            controller.setCon(con);
            controller.inic();
            
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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/product/product.fxml"));
            Parent root = loader.load();
            ProductController controller = loader.getController();
            controller.setCon(con);
            controller.inic();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


