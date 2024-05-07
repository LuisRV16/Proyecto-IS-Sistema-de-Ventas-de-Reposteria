package controller.sales;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import controller.client.UpdateClientController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PaymentMethodController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnCardPayment;

    @FXML
    private Button btnCashPayment;

    @FXML
    private Button btnGoBack;

    @FXML
    private Label lblClient;

    @FXML
    private Label lblVboxClients;

    @FXML
    private VBox vboxClients;

    private Connection con;

    private ArrayList<String> productosEnCarrito;

    private HashMap<String, Integer> cantidadProducto;

    private HashMap<String, Integer> existenciaProducto;

    private HashMap<String, Float> precioProducto;

    private HashMap<String, byte[]> imagenProducto;

    private Button btnSelected = new Button();

    private float iva;

    private float subtotalF;

    private float total;

    private String clientName;

    private String clientLastName1;

    private String clientLastName2;

    private String employeeName;

    private String employeeLastName1;

    private String employeeLastName2;

    @FXML
    void cardPayment(ActionEvent event) {

    }

    @FXML
    void cashPayment(ActionEvent event) {
        Stage stage = (Stage) btnCashPayment.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sales/CashPayment.fxml"));
            Parent root = loader.load();

            CashPaymentController controller = loader.getController();
            controller.setCon(con);
            controller.setProductosEnCarrito(productosEnCarrito);
            controller.setCantidadProducto(cantidadProducto);
            controller.setExistenciaProducto(existenciaProducto);
            controller.setPrecioProducto(precioProducto);
            controller.setImagenProducto(imagenProducto);
            controller.setIva(iva);
            controller.setSubtotal(subtotalF);
            controller.setTotal(total);
            controller.setClientName(clientName);
            controller.setClientLastName1(clientLastName1);
            controller.setClientLastName2(clientLastName2);
            controller.setEmployeeName(employeeName);
            controller.setEmployeeLastName1(employeeLastName1);
            controller.setEmployeeLastName2(employeeLastName2);
            controller.setPreviousStage(stage);
            controller.inic();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sales/ShopCart.fxml"));
            Parent root = loader.load();

            ShopCartController controller = loader.getController();
            controller.setCon(con);
            controller.setProductosEnCarrito(productosEnCarrito);
            controller.setCantidadProducto(cantidadProducto);
            controller.setExistenciaProducto(existenciaProducto);
            controller.setPrecioProducto(precioProducto);
            controller.setImagenProducto(imagenProducto);
            controller.setEmployeeName(employeeName);
            controller.setEmployeeLastName1(employeeLastName1);
            controller.setEmployeeLastName2(employeeLastName2);
            controller.inic();

            anchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void setExistenciaProducto(HashMap<String, Integer> existenciaProducto) {
        this.existenciaProducto = existenciaProducto;
    }

    public void setImagenProducto(HashMap<String, byte[]> imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public void setSubtotalF(float subtotalF) {
        this.subtotalF = subtotalF;
    }

    public void setTotal(float total) {
        this.total = total;
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

    public void inic() {

        Button btnGeneric = new Button("Cliente no especificado");
        btnGeneric.setMinWidth(485);
        btnGeneric.setMinHeight(50);
        btnGeneric.setFont(new Font("Arial", 25));
        btnGeneric.setAlignment(Pos.CENTER);

        EventHandler<ActionEvent> eventH = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                clientName = "Generico";
                clientLastName1 = "Gen";
                
                lblClient.setText(btnGeneric.getText());
                btnCashPayment.setDisable(false);
                btnCardPayment.setDisable(false);

                btnSelected.setDisable(false);

                btnGeneric.setDisable(true);

                btnSelected = btnGeneric;
            }
        };

        btnGeneric.setOnAction(eventH);

        vboxClients.getChildren().add(btnGeneric);

        String sql = "{call getClient}";

        try (CallableStatement statement = con.prepareCall(sql)) {

            ResultSet resultados = statement.executeQuery();

            while (resultados.next()) {
                String nombre = resultados.getString(2);
                String apellido1 = resultados.getString(3);
                String apellido2 = resultados.getString(4);

                if (!nombre.equals("Generico")) {

                    Button btnClient = new Button(nombre + " " + apellido1 + " " + apellido2);
                    btnClient.setMinWidth(485);
                    btnClient.setMinHeight(50);
                    btnClient.setFont(new Font("Arial", 25));
                    btnClient.setAlignment(Pos.CENTER);

                    EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            clientName = nombre;
                            clientLastName1 = apellido1;
                            clientLastName2 = apellido2;

                            lblClient.setText(nombre + " " + apellido1 + " " + apellido2);
                            btnCashPayment.setDisable(false);
                            btnCardPayment.setDisable(false);

                            btnSelected.setDisable(false);

                            btnClient.setDisable(true);
                
                            btnSelected = btnClient;
                        }
                    };

                    btnClient.setOnAction(eventHandler);

                    vboxClients.getChildren().add(btnClient);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
