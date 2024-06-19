package controller.mainMenu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import controller.client.MenuClienteController;
import controller.product.ProductController;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Menu2Controller {
    @FXML
    private BorderPane bp;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnMenu;

    @FXML
    private Button btnProducto;

    @FXML
    private AnchorPane center;

    @FXML
    private AnchorPane navList;

    private Connection con;

    private String employeeName;

    private String employeeLastName1;

    private String employeeLastName2;

    public void setCon(Connection con) {
        this.con = con;
    }

    @FXML
    void close(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login/login.fxml"));
        Parent root = loader.load();
        bp.getChildren().setAll(root);
    }
    
    @FXML
    void goToProduct(ActionEvent event) throws IOException {
        loadPage("/view/product/product.fxml");
        openMenu(event);
    }

    @FXML
    void goToClientes(ActionEvent event) throws IOException {
        loadPage("/view/client/menuCliente.fxml");
        openMenu(event);
    }

    @FXML
    void openMenu(ActionEvent event) {
        TranslateTransition openNav = new TranslateTransition(new Duration(350), navList);
        openNav.setToX(0);
        // TranslateTransition openNavCenter = new TranslateTransition(new Duration(350), center);
        // openNavCenter.setToX(navList.getWidth());

        TranslateTransition closeNav = new TranslateTransition(new Duration(350), navList);
        // TranslateTransition closeNavCenter = new TranslateTransition(new Duration(350), center);
        if (navList.getTranslateX() != 0) {
            openNav.play();
            // openNavCenter.play();
        } else {
            closeNav.setToX(-(navList.getWidth()));
            // closeNavCenter.setToX(0);
            closeNav.play();
            // closeNavCenter.play();
        }
    }

    public void loadPage(String page) throws IOException {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
        root = loader.load();
        switch (page) {
            case "/view/product/product.fxml" -> {
                ProductController controller = loader.getController();
                controller.setCon(con);
                controller.inic();
                controller.setEmployeeName(employeeName);
                controller.setEmployeeLastName1(employeeLastName1);
                controller.setEmployeeLastName2(employeeLastName2);
            }
            case "/view/client/menuCliente.fxml" -> {
                MenuClienteController controller = loader.getController();
                controller.setCon(con);
                controller.inic();
                controller.setEmployeeName(employeeName);
                controller.setEmployeeLastName1(employeeLastName1);
                controller.setEmployeeLastName2(employeeLastName2);
            }
        }
        center.getChildren().setAll(root);
    }

    public void inic() throws IOException {
        loadPage("/view/product/product.fxml");
        navList.setTranslateX(-250);
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
}
