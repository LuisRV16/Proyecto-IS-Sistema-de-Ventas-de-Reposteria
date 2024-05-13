package controller.sales;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SalesManagerController {

    @FXML
    private BorderPane ap;

    @FXML
    private VBox vboxSales;

    private Connection con;

    private DecimalFormat df = new DecimalFormat("##.00");

    public void setCon(Connection con) {
        this.con = con;
    }

    public void inic() {

        String sql = "{call getSales}";

        try {

            CallableStatement statement = con.prepareCall(sql);

            ResultSet resultados = statement.executeQuery();

            while (resultados.next()) {
                String idSale = resultados.getString(1);
                String date = resultados.getDate(2).toString();
                String time = resultados.getTime(3).toString();
                String employeeName = resultados.getString(4);
                String employeeLastName1 = resultados.getString(5);
                String employeeLastName2 = resultados.getString(6);
                if (employeeLastName2 == null) employeeLastName2 = "";
                String clientName = resultados.getString(7);
                String clientLastName1 = resultados.getString(8);
                String clientLastName2 = resultados.getString(9);
                if (clientLastName2 == null) clientLastName2 = "";
                float subtotal = resultados.getFloat(10);
                float iva = resultados.getFloat(11);
                float total = resultados.getFloat(12);
                String paymentMethod = resultados.getString(13);

                TitledPane pane = new TitledPane();
                pane.setText(idSale);
                // pane.setMinWidth(780);
                pane.setAlignment(Pos.CENTER_LEFT);
                pane.setExpanded(false);

                HBox hbox = new HBox();
                // hbox.setMinWidth(780);

                ScrollPane sp = new ScrollPane();
                sp.setVisible(false);

                VBox vboxSale = new VBox();
                vboxSale.setMinWidth(420);
                VBox vboxProducts = new VBox();
                vboxProducts.setMinWidth(420);

                Button btn = new Button("Ver más");

                EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {
                        sp.setVisible(sp.isVisible() ? false : true);
                    }
                    
                };

                btn.setOnAction(event);
                
                vboxSale.getChildren().addAll(
                    new Label("Fecha de Venta: " + date),
                    new Label("Hora de Venta: " + time),
                    new Label("Empleado: " + employeeName + " " + employeeLastName1 + " " + employeeLastName2),
                    new Label("Cliente: " + clientName + " " + clientLastName1 + " " + clientLastName2),
                    new Label("Subtotal: $" + df.format(subtotal)),
                    new Label("IVA: $" + df.format(iva)),
                    new Label("Total: $" + df.format(total)),
                    new Label("Método de Pago: " + paymentMethod),
                    btn
                );

                sql = "{call getSalesProducts(?)}";
                statement = con.prepareCall(sql);

                statement.setString(1, idSale);

                ResultSet res = statement.executeQuery();

                while (res.next()) {
                    String name = res.getString(1);
                    float price = res.getFloat(2);
                    int quantity = res.getInt(3);
                    float subtotalP = res.getFloat(4);

                    vboxProducts.getChildren().add(new Label("Producto: " + name + "    Precio: $" + df.format(price) +
                    "    Cantidad: " + quantity + "    Subtotal: $" + df.format(subtotalP)));
                }

                sp.setContent(vboxProducts);

                hbox.getChildren().addAll(vboxSale, sp);

                pane.setContent(hbox);

                vboxSales.getChildren().add(pane);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
