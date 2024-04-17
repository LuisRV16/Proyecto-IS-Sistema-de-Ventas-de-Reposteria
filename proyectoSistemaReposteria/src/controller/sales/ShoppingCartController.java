package controller.sales;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ShoppingCartController {
    Stage stage;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnProceedPay;

    @FXML
    private Button btnFullScreen;

    @FXML
    private Label lblFinalPrice;

    @FXML
    private Label lblPriceColumn;

    @FXML
    private Pane paneFinalPrice;

    @FXML
    private ScrollPane scrollShopCar;

    @FXML
    private VBox vboxProducts;

    @FXML
    void goBack(ActionEvent event) {

    }

    @FXML
    void proceedToPay(ActionEvent event) {

    }

    @FXML
    void fullScreen(ActionEvent event){
        ObservableList<Screen> screens = Screen.getScreens();//Get list of Screens
        Rectangle2D bounds = screens.get(0).getVisualBounds();
        iniciar(stage);
    }

    public void iniciar(Stage stage){
        stage.setFullScreen(true);
        this.stage = stage;
    }

    void fullStage(Stage stage){
        stage.setFullScreen(true);
    }
}
