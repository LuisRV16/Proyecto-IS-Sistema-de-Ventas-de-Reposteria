package controller.sales;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import controller.mainMenu.Menu2Controller;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CashPaymentController {
    @FXML
    private AnchorPane ap;
    
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

    @FXML
    private Label lblIVA;

    @FXML
    private Label lblSubtotal;

    private Connection con;

    private ArrayList<String> productosEnCarrito;

    private HashMap<String, Integer> cantidadProducto;

    private HashMap<String, Float> precioProducto;

    private float iva;

    private float subtotal;

    private float total;

    private String clientName;

    private String clientLastName1;

    private String clientLastName2;

    private String employeeName;

    private String employeeLastName1;

    private String employeeLastName2;

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

    public void setPrecioProducto(HashMap<String, Float> precioProducto) {
        this.precioProducto = precioProducto;
    }
    
    public void setIva(float iva) {
        this.iva = iva;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientLastName1(String clientLastName1) {
        this.clientLastName1 = clientLastName1;
    }

    public void setClientLastName2(String clientLastName2) {
        this.clientLastName2 = clientLastName2;
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
                if (newText.length() <= 9) {
                    Float.parseFloat(newText); // Intentar convertir a Double
                    return change;                    
                } else {
                    return null;
                }
            } catch (NumberFormatException e) {
                return null; // Rechazar el cambio si no es un número válido
            }
        }));
        lblTotal.setText(lblTotal.getText() + df.format(total));
        lblSubtotal.setText(lblSubtotal.getText()+df.format(subtotal));
        lblIVA.setText(lblIVA.getText()+df.format(iva));
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

        String sql = "{call addSale(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try {

            CallableStatement statement = con.prepareCall(sql);

            statement.setString(1, clientName);
            statement.setString(2, clientLastName1);
            statement.setString(3, clientLastName2);
            statement.setString(4, employeeName);
            statement.setString(5, employeeLastName1);
            statement.setString(6, employeeLastName2);
            statement.setFloat(7, iva);
            statement.setFloat(8, subtotal);
            statement.setFloat(9, total);
            statement.setString(10, "efectivo");
            statement.registerOutParameter(11, Types.VARCHAR);

            statement.execute();

            String msg = statement.getString(11);
            String msg2;

            sql = "{call addSaleProduct(?, ?, ?, ?, ?)}";
            statement = con.prepareCall(sql);
            for (String nombreProducto : productosEnCarrito) {

                int cantidad = cantidadProducto.get(nombreProducto);
                float precio = precioProducto.get(nombreProducto);
                float subtotal = precio * (float) cantidad;

                statement.setString(1, nombreProducto);
                statement.setInt(2, cantidad);
                statement.setFloat(3, precio);
                statement.setFloat(4, subtotal);
                statement.registerOutParameter(5, Types.VARCHAR);

                statement.execute();

                msg2 = statement.getString(5);
            }

            statement.close();

            double midX = panePayCom.getWidth() / 2;
            double midY = panePayCom.getHeight() / 2;

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
                        midX - 52.5, midY - 27.5, // 1
                        midX - 17.5, midY + 12.5, // 2
                        midX + 67.5, midY - 67.5, // 3
                        midX + 82.5, midY - 47.5, // 4
                        midX - 17.5, midY + 42.5, // 5
                        midX - 67.5, midY - 12.5 // 6
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
                    alert.setContentText(msg);
                    alert.showAndWait();
                    //Regresa
                    Parent root;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainMenu/menu2.fxml"));
                    try {
                        root = loader.load();
                        Menu2Controller controller = loader.getController();
                        controller.setCon(con);
                        controller.setEmployeeName(employeeName);
                        controller.setEmployeeLastName1(employeeLastName1);
                        controller.setEmployeeLastName2(employeeLastName2);
                        controller.inic();
                        ap.getChildren().setAll(root);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });

            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
