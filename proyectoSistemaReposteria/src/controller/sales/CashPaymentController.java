package controller.sales;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.UnaryOperator;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CashPaymentController {

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnPay;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblMsg;

    @FXML
    private Label lblReturn;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblTotal;

    @FXML
    private Pane panePayCom;

    @FXML
    private TextField txtAmount;

    private Connection con;

    private ArrayList<String> productosEnCarrito;

    private HashMap<String, Integer> cantidadProducto;

    private HashMap<String, Integer> existenciaProducto;

    private HashMap<String, Float> precioProducto;

    private HashMap<String, byte[]> imagenProducto;

    private float total;

    private String name;

    private String lastName1;
    
    private String lastName2;

    private Stage previousStage;

    private DecimalFormat df = new DecimalFormat("#0.00");

    private static final int FRAME_WIDTH = 230;
    private static final int FRAME_HEIGHT = 350;
    private static final Color FILL_COLOR = Color.GREEN;
    private static final Duration FILL_DURATION = Duration.seconds(1);

    public void setCon(Connection con) {
        this.con = con;
    }

    public void setProductosEnCarrito(ArrayList<String> productosEnCarrito) {
        this.productosEnCarrito = productosEnCarrito;
    }

    public void setCantidadProducto(HashMap<String, Integer> cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public void setExistenciaProducto(HashMap<String, Integer> existenciaProducto) {
        this.existenciaProducto = existenciaProducto;
    }

    public void setPrecioProducto(HashMap<String, Float> precioProducto) {
        this.precioProducto = precioProducto;
    }

    public void setImagenProducto(HashMap<String, byte[]> imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public void setPreviousStage(Stage previousStage) {
        this.previousStage = previousStage;
    }

    public void inic() {
        txtAmount.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) {
                return change; // Permitir campo vacío
            }
            try {
                Double.parseDouble(newText); // Intentar convertir a Double
                return change;
            } catch (NumberFormatException e) {
                return null; // Rechazar el cambio si no es un número válido
            }
        }));
        lblTotal.setText(lblTotal.getText() + df.format(total));
    }

    @FXML
    void calculateReturn(KeyEvent event) {
        lblMsg.setVisible(false);
        try {
            float amountToReturn;
            float amount = Float.parseFloat(txtAmount.getText());
            if (amount >= total) {
                amountToReturn = amount - total;
                lblReturn.setText("Cambio: $" + df.format(amountToReturn));
                btnPay.setDisable(false);
            } else {
                lblMsg.setText("Cantidad insuficiente.");
                lblReturn.setText("Cambio: $");
                lblMsg.setVisible(true);
                btnPay.setDisable(true);
            }
        } catch (NumberFormatException e) {
            lblMsg.setText("Ingrese la cantidad con la que se pagará.");
            lblMsg.setVisible(true);
            lblReturn.setText("Cambio: $");
            btnPay.setDisable(true);
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        ((Stage) btnGoBack.getScene().getWindow()).close();
        previousStage.show();
    }

    @FXML
    void pay(ActionEvent event) {
        double midX = panePayCom.getWidth()/2;
        double midY = panePayCom.getHeight()/2;

        Rectangle frame = new Rectangle(FRAME_WIDTH, FRAME_HEIGHT, Color.WHITE);
        Rectangle fill = new Rectangle(FRAME_WIDTH, 0, FILL_COLOR);

        panePayCom.getChildren().addAll(frame, fill);

        KeyValue keyValue = new KeyValue(fill.heightProperty(), FRAME_HEIGHT);
        KeyFrame keyFrame = new KeyFrame(FILL_DURATION, keyValue);

        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(1);

        fill.heightProperty().addListener((obs, oldValue, newValue) -> {
            fill.setTranslateY((FRAME_HEIGHT - newValue.doubleValue()) / 2);
        });

        timeline.play();

        timeline.setOnFinished(e -> {
            Polygon checkmark = new Polygon(
                    midX - 52.5, midY - 27.5, //1
                    midX - 17.5, midY + 12.5, //2 
                    midX + 67.5, midY - 67.5, //3
                    midX + 82.5, midY - 47.5, //4
                    midX - 17.5, midY + 42.5, //5
                    midX - 67.5, midY - 12.5  //6
            );
            checkmark.setFill(Color.WHITE);
            checkmark.setStroke(Color.GREEN);
            checkmark.setStrokeWidth(2);
            panePayCom.getChildren().add(checkmark);

            Circle circle = new Circle(122.5, 162.5, 90);
            circle.setFill(Color.TRANSPARENT);
            circle.setStroke(Color.WHITE);
            circle.setStrokeWidth(2);

            panePayCom.getChildren().add(circle);

            // Mostrar la ventana emergente después de agregar los elementos
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Compra terminada");
                alert.setHeaderText(null);
                alert.setContentText("Compra terminada.");
                alert.showAndWait();
            });

        });
    }

}
