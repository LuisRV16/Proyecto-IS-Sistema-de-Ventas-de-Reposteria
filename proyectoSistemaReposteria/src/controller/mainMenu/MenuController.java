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

    private String employeeName;

    private String employeeLastName1;

    private String employeeLastName2;

    Connection con;

    public void setCon(Connection con) {
        this.con = con;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeeLastName1(String employeeLastName1) {
        this.employeeLastName1 = employeeLastName1;
    }

    public void setEmployeeLastName2(String employeeLastName2) {
        this.employeeLastName2 = employeeLastName2;
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
            controller.setEmployeeName(employeeName);
            controller.setEmployeeLastName1(employeeLastName1);
            controller.setEmployeeLastName2(employeeLastName2);
            
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/product/product.fxml"));
            Parent root = loader.load();
            ProductController controller = loader.getController();

            controller.setCon(con);
            controller.inic();
            controller.setEmployeeName(employeeName);
            controller.setEmployeeLastName1(employeeLastName1);
            controller.setEmployeeLastName2(employeeLastName2);
            
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();

            Stage stage = (Stage) btnProducto.getScene().getWindow(); // Obtener la ventana actual
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


