package controller.sales;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import controller.product.ProductController;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ShopCartController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnPagar;

    @FXML
    private Label lblIva;

    @FXML
    private Label lblSubtotal;

    @FXML
    private Label lblTotal;

    @FXML
    private ScrollPane scrollShopCar;

    @FXML
    private VBox vboxProducts;

    private Connection con;

    private ArrayList<String> productosEnCarrito;

    private HashMap<String, Integer> cantidadProducto;

    private HashMap<String, Integer> existenciaProducto;

    private HashMap<String, Float> precioProducto;

    private HashMap<String, byte[]> imagenProducto;

    private float iva;

    private float subtotalF;

    private float total;

    private String employeeName;

    private String employeeLastName1;

    private String employeeLastName2;

    public void inic() {
        vboxProducts.setSpacing(5);
        DecimalFormat df = new DecimalFormat("#####0.00");

        for (String nombre : productosEnCarrito) {

            int cantidad = cantidadProducto.get(nombre);
            float precio = precioProducto.get(nombre);
            byte[] imagen = imagenProducto.get(nombre);

            Pane panelProducto = new Pane();
            panelProducto.setPrefSize(1214, 150);
            panelProducto.setMinSize(1214, 150);

            ImageView image = new ImageView(new Image(new ByteArrayInputStream(imagen)));
            image.setLayoutX(15);
            image.setLayoutY(15);
            image.setFitWidth(120);
            image.setFitHeight(120);

            Label nombreProducto = new Label(nombre);
            nombreProducto.setLayoutX(150);
            nombreProducto.setLayoutY(0);
            nombreProducto.setMinWidth(400);
            nombreProducto.setMinHeight(150);
            nombreProducto.setId("labelSmallTitle");

            Label cantidadL = new Label("Cantidad");
            cantidadL.setLayoutX(550);
            cantidadL.setLayoutY(40);
            cantidadL.setAlignment(Pos.CENTER);
            cantidadL.setMinSize(105, 35);
            cantidadL.setPrefSize(105, 35);
            cantidadL.setId("labelSmallTitle");

            Button btnMin = new Button("-");
            btnMin.setLayoutX(550);
            btnMin.setLayoutY(75);
            btnMin.setPadding(new Insets(0,0,3,0));
            btnMin.setMinSize(35, 35);
            btnMin.setPrefSize(35, 35);

            Label cantidadN = new Label(cantidad + "");
            cantidadN.setId("labelSmallTitle");
            cantidadN.setMinSize(35, 35);
            cantidadN.setAlignment(Pos.CENTER);
            cantidadN.setLayoutX(585);
            cantidadN.setLayoutY(75);

            EventHandler<ActionEvent> eventHandlerBtnMin = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    int cant = cantidadProducto.get(nombre);
                    int index = productosEnCarrito.indexOf(nombre);

                    subtotalF -= precio;
                    iva = subtotalF * 0.16F;
                    total = subtotalF + iva;

                    if (cant == 1) {
                        vboxProducts.getChildren().remove(index);
                        productosEnCarrito.remove(nombre);
                        cantidadProducto.remove(nombre);
                    } else {
                        cantidadProducto.replace(nombre, cant - 1);
                        cant = cantidadProducto.get(nombre);
                        float precioNuevo = precio * ((float) cant);

                        Pane panel = (Pane) vboxProducts.getChildren().get(index);
                        Label lbl = (Label) panel.getChildren().get(3);
                        Label lbl2 = (Label) panel.getChildren().get(6);

                        lbl.setText(cant + "");
                        lbl2.setText("$" + df.format(precioNuevo));

                        panel.getChildren().set(3, lbl);
                        panel.getChildren().set(6, lbl2);

                        vboxProducts.getChildren().set(index, panel);
                    }

                    lblSubtotal.setText("Subtotal $" + df.format(subtotalF));
                    lblIva.setText("IVA $" + df.format(iva));
                    lblTotal.setText("Total $" + df.format(total));

                    if (cantidadProducto.isEmpty())
                        btnPagar.setDisable(true);
                }
            };

            btnMin.setOnAction(eventHandlerBtnMin);

            Button btnMas = new Button("+");
            btnMas.setMinSize(35, 35);
            btnMas.setPrefSize(35, 35);
            btnMas.setPadding(new Insets(0,1,1,0));
            btnMas.setLayoutX(620);
            btnMas.setLayoutY(75);

            EventHandler<ActionEvent> eventHandlerBtnMas = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    int cant = cantidadProducto.get(nombre);
                    int existencia = existenciaProducto.get(nombre);
                    if (cant != existencia) {
                        cantidadProducto.replace(nombre, cant + 1);
                        cant = cantidadProducto.get(nombre);

                        int index = productosEnCarrito.indexOf(nombre);
                        float precioNuevo = precio * ((float) cant);

                        subtotalF += precio;
                        iva = subtotalF * 0.16F;
                        total = subtotalF + iva;

                        Pane panel = (Pane) vboxProducts.getChildren().get(index);
                        Label lbl = (Label) panel.getChildren().get(3);
                        Label lbl2 = (Label) panel.getChildren().get(6);

                        lbl.setText(cant + "");
                        lbl2.setText("$" + df.format(precioNuevo));
                        panel.getChildren().set(3, lbl);
                        panel.getChildren().set(6, lbl2);
                        vboxProducts.getChildren().set(index, panel);

                        lblSubtotal.setText("Subtotal: $" + df.format(subtotalF));
                        lblIva.setText("IVA $" + df.format(iva));
                        lblTotal.setText("Total $" + df.format(total));
                    }
                }
            };

            btnMas.setOnAction(eventHandlerBtnMas);

            float subtotal = precio * (float) cantidad;
            subtotalF += subtotal;

            Label lblTotal = new Label("$" + df.format(subtotal));
            lblTotal.setMinWidth(275);
            lblTotal.setMinHeight(150);
            lblTotal.setAlignment(Pos.CENTER);
            lblTotal.setLayoutX(941);
            lblTotal.setLayoutY(0);
            lblTotal.setId("labelSmallTitle");
            
            Label lblPrecioU = new Label("$" + df.format(precio));
            lblPrecioU.setMinWidth(275);
            lblPrecioU.setMinHeight(150);
            lblPrecioU.setAlignment(Pos.CENTER);
            lblPrecioU.setLayoutX(666);
            lblPrecioU.setLayoutY(0);
            lblPrecioU.setId("labelSmallTitle");

            panelProducto.getChildren().addAll(image, nombreProducto, cantidadL, cantidadN, btnMin, btnMas, lblTotal, lblPrecioU);
            panelProducto.setId("paneBackDeg");

            vboxProducts.getChildren().addAll(panelProducto);
        }

        iva = subtotalF * 0.16F;
        total = subtotalF + iva;

        lblSubtotal.setText("Subtotal $" + df.format(subtotalF));
        lblIva.setText("IVA $" + df.format(iva));
        lblTotal.setText("Total $" + df.format(total));
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

    public void setExistenciaProducto(HashMap<String, Integer> existenciaProducto) {
        this.existenciaProducto = existenciaProducto;
    }

    public void setPrecioProducto(HashMap<String, Float> precioProducto) {
        this.precioProducto = precioProducto;
    }

    public void setImagenProducto(HashMap<String, byte[]> imagenProducto) {
        this.imagenProducto = imagenProducto;
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
    void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/product/product.fxml"));
            Parent root = loader.load();
            ProductController controller = loader.getController();

            controller.setCon(con);
            controller.setProductosEnCarrito(productosEnCarrito);
            controller.setCantidadProducto(cantidadProducto);
            controller.setEmployeeName(employeeName);
            controller.setEmployeeLastName1(employeeLastName1);
            controller.setEmployeeLastName2(employeeLastName2);
            controller.inic();

            anchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void goToPay(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sales/PaymentMethod.fxml"));
            Parent root = loader.load();
            PaymentMethodController controller = loader.getController();

            controller.setCon(con);
            controller.setProductosEnCarrito(productosEnCarrito);
            controller.setCantidadProducto(cantidadProducto);
            controller.setPrecioProducto(precioProducto);
            controller.setExistenciaProducto(existenciaProducto);
            controller.setImagenProducto(imagenProducto);
            controller.setSubtotalF(subtotalF);
            controller.setIva(iva);
            controller.setTotal(total);
            controller.setEmployeeName(employeeName);
            controller.setEmployeeLastName1(employeeLastName1);
            controller.setEmployeeLastName2(employeeLastName2);
            controller.inic();

            anchorPane.getChildren().setAll(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
