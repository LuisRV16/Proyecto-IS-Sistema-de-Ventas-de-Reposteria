package controller.sales;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CardPaymentController {

    @FXML
    private Label lblPIN;

    @FXML
    private Label lblProcessing;

    @FXML
    private Pane pane;

    @FXML
    private SVGPath svg1;

    @FXML
    private SVGPath svg2;

    @FXML
    private SVGPath svg3;

    @FXML
    private SVGPath svg4;

    @FXML
    private SVGPath svg5;

    @FXML
    private SVGPath svg6;

    private double midX;

    private double midY;

    private Connection con;

    private ArrayList<String> productosEnCarrito;

    private HashMap<String, Integer> cantidadProducto;

    private HashMap<String, Float> precioProducto;

    private float iva;

    private float subtotal;

    private float total;

    private String cardType;

    private String clientName;

    private String clientLastName1;

    private String clientLastName2;

    private String employeeName;

    private String employeeLastName1;

    private String employeeLastName2;

    public void inic() {

        midX = pane.getWidth() / 2;
        midY = pane.getHeight() / 2;

        Image terminalImage = new Image("/images/terminal.png");
        ImageView terminalImageView = new ImageView(terminalImage);
        terminalImageView.setFitWidth(150);
        terminalImageView.setPreserveRatio(true);
        terminalImageView.setLayoutX(425);
        terminalImageView.setLayoutY(200);
    
        Image tarjetaImage = new Image("/images/tarjeta.png");
        ImageView tarjetaImageView = new ImageView(tarjetaImage);
        tarjetaImageView.setFitWidth(100);
        tarjetaImageView.setPreserveRatio(true);
        tarjetaImageView.setLayoutX(450);
        tarjetaImageView.setLayoutY(375);
    
        Label label = new Label("Ingrese su Tarjeta");
        label.setFont(new Font("System", 20));
        label.setLayoutX(420);
        label.setLayoutY(475);
    
        pane.getChildren().setAll(terminalImageView, tarjetaImageView, label);
    
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), tarjetaImageView);
        translateTransition.setByY(-25);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.play();
    
        Timeline timelineBloque1 = new Timeline(
            new KeyFrame(Duration.seconds(4.5), e -> {
                translateTransition.stop();

                terminalImageView.setVisible(false);
                tarjetaImageView.setVisible(false);
                label.setVisible(false);

                svg1.setVisible(true);
                svg2.setVisible(true);
                svg3.setVisible(true);
                svg4.setVisible(true);
                svg5.setVisible(true);
                svg6.setVisible(true);
                lblPIN.setVisible(true);

                pane.getChildren().setAll(svg1, svg2, svg3, svg4, svg5, svg6, lblPIN);

                startColorsAnimation();
            })
        );
        timelineBloque1.play();
    }
    
    private void startColorsAnimation() {
        svg1.setFill(Color.WHITE);
        svg2.setFill(Color.WHITE);
        svg3.setFill(Color.WHITE);
        svg4.setFill(Color.WHITE);
        svg5.setFill(Color.WHITE);
        svg6.setFill(Color.WHITE);
    
        Timeline timelineChangeColors = new Timeline(charge());
        timelineChangeColors.setCycleCount(3);
        timelineChangeColors.play();

        Timeline timelineFinal = new Timeline(
            new KeyFrame(Duration.seconds(9), e -> {
                timelineChangeColors.stop();
                Polygon checkmark = new Polygon(
                    midX - 52.5, midY - 27.5, // 1
                    midX - 17.5, midY + 12.5, // 2
                    midX + 67.5, midY - 67.5, // 3
                    midX + 82.5, midY - 47.5, // 4
                    midX - 17.5, midY + 42.5, // 5
                    midX - 67.5, midY - 12.5 // 6
                );
                checkmark.setFill(Color.WHITE);
                checkmark.setStroke(Color.WHITE);
                checkmark.setStrokeWidth(2);

                Circle circle = new Circle(510, 330, 90);
                circle.setFill(Color.GREEN);
                circle.setStroke(Color.GREEN);
                circle.setStrokeWidth(2);

                pane.getChildren().setAll(circle, checkmark);

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
                    statement.setString(10, "tarjeta de " + cardType);
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
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }),
            new KeyFrame(Duration.seconds(12), e -> {
                ((Stage)pane.getScene().getWindow()).close();
            })
        );

        timelineFinal.play();
    }

    private KeyFrame[] charge() {
        return new KeyFrame[]{
            new KeyFrame(Duration.seconds(0.5), new KeyValue(svg1.fillProperty(), Color.BLACK)),
            new KeyFrame(Duration.seconds(1.0), new KeyValue(svg2.fillProperty(), Color.BLACK),
                                                new KeyValue(svg1.fillProperty(), Color.WHITE)),
            new KeyFrame(Duration.seconds(1.5), new KeyValue(svg3.fillProperty(), Color.BLACK),
                                                new KeyValue(svg2.fillProperty(), Color.WHITE)),
            new KeyFrame(Duration.seconds(2.0), new KeyValue(svg4.fillProperty(), Color.BLACK),
                                                new KeyValue(svg3.fillProperty(), Color.WHITE)),
            new KeyFrame(Duration.seconds(2.5), new KeyValue(svg5.fillProperty(), Color.BLACK),
                                                new KeyValue(svg4.fillProperty(), Color.WHITE)),
            new KeyFrame(Duration.seconds(3.0), new KeyValue(svg6.fillProperty(), Color.BLACK),
                                                new KeyValue(svg5.fillProperty(), Color.WHITE)),
            new KeyFrame(Duration.seconds(3.0), e -> {lblPIN.setVisible(false);
                                                      lblProcessing.setVisible(true);
                                                      pane.getChildren().set(6, lblProcessing);})
        };
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
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

}
