package controller.mainMenu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import controller.client.MenuClientManagerController;
import controller.client.MenuClienteController;
import controller.product.ProductController;
import controller.product.ProductManagerController;
import controller.sales.SalesManagerController;
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

public class MenuManagerController {

    @FXML
    private BorderPane bp;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnClients;

    @FXML
    private Button btnMenu;

    @FXML
    private Button btnProducts;

    @FXML
    private Button btnSales;

    @FXML
    private AnchorPane center;

    @FXML
    private AnchorPane navList;

    private Connection con;

    public void setCon(Connection con) {
        this.con = con;
    }

    public void inic() throws IOException {
        loadPage("/view/client/menuClientsManager.fxml");
        navList.setTranslateX(-250);
    }

    @FXML
    void close(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login/login.fxml"));
        Parent root = loader.load();
        bp.getChildren().setAll(root);
    }

    @FXML
    void goToClients(ActionEvent event) throws IOException {
        loadPage("/view/client/menuClientsManager.fxml");
    }

    @FXML
    void goToProducts(ActionEvent event) throws IOException {
        loadPage("/view/product/ProductManager.fxml");
    }

    @FXML
    void goToSales(ActionEvent event) throws IOException {
        loadPage("/view/sales/SalesManager.fxml");
    }

    @FXML
    void openMenu(ActionEvent event) {
        TranslateTransition openNav = new TranslateTransition(new Duration(350), navList);
        openNav.setToX(0);
        TranslateTransition openNavCenter = new TranslateTransition(new Duration(350), center);
        openNavCenter.setToX(navList.getWidth());

        TranslateTransition closeNav = new TranslateTransition(new Duration(350), navList);
        TranslateTransition closeNavCenter = new TranslateTransition(new Duration(350), center);
        if (navList.getTranslateX() != 0) {
            openNav.play();
            openNavCenter.play();
        } else {
            closeNav.setToX(-(navList.getWidth()));
            closeNavCenter.setToX(0);
            closeNav.play();
            closeNavCenter.play();
        }
    }

    public void loadPage(String page) throws IOException {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
        root = loader.load();
        switch (page) {
            case "/view/sales/SalesManager.fxml" -> {
                SalesManagerController controller = loader.getController();
                controller.setCon(con);
                controller.inic();
            }
            case "/view/client/menuClientsManager.fxml" -> {
                MenuClientManagerController controller = loader.getController();
                controller.setCon(con);
                controller.inic();
            }
            case "/view/product/ProductManager.fxml" -> {
                ProductManagerController controller = loader.getController();
                controller.setCon(con);
                controller.inic();
            }
        }
        center.getChildren().setAll(root);
    }

}
