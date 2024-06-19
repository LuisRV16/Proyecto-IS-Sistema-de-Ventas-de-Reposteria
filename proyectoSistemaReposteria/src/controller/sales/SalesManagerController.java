package controller.sales;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Producto;

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
                String clientName = resultados.getString(4);
                String clientLastName1 = resultados.getString(5);
                String clientLastName2 = resultados.getString(6);
                if (clientLastName2 == null) clientLastName2 = "";
                String employeeName = resultados.getString(7);
                String employeeLastName1 = resultados.getString(8);
                String employeeLastName2 = resultados.getString(9);
                if (employeeLastName2 == null) employeeLastName2 = "";
                float subtotal = resultados.getFloat(10);
                float iva = resultados.getFloat(11);
                float total = resultados.getFloat(12);
                String paymentMethod = resultados.getString(13);

                TitledPane pane = new TitledPane();
                pane.setText(idSale + " | Fecha: " + date);
                pane.setAlignment(Pos.CENTER_LEFT);
                pane.setExpanded(false);

                HBox hbox = new HBox();

                VBox vboxSale = new VBox();
                vboxSale.setMinWidth(420);
                VBox vboxProducts = new VBox();
                vboxProducts.setMinWidth(520);
                
                vboxSale.getChildren().addAll(
                    new Label("Fecha de Venta: " + date),
                    new Label("Hora de Venta: " + time),
                    new Label("Empleado: " + employeeName + " " + employeeLastName1 + " " + employeeLastName2),
                    new Label("Cliente: " + clientName + " " + clientLastName1 + " " + clientLastName2),
                    new Label("Subtotal: $" + df.format(subtotal)),
                    new Label("IVA: $" + df.format(iva)),
                    new Label("Total: $" + df.format(total)),
                    new Label("Método de Pago: " + paymentMethod)
                );

                
                TableView<Producto> tableView = new TableView<>();

                // Crear las columnas
                TableColumn<Producto, String> columnaProducto = new TableColumn<>("Producto");
                columnaProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                columnaProducto.setStyle("-fx-alignment: CENTER;");
                columnaProducto.setPrefWidth(200); // Establecer el ancho preferido de la columna

                TableColumn<Producto, Double> columnaPrecio = new TableColumn<>("Precio");
                columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
                columnaPrecio.setStyle("-fx-alignment: CENTER;");
                columnaPrecio.setPrefWidth(100); // Establecer el ancho preferido de la columna

                TableColumn<Producto, Integer> columnaCantidad = new TableColumn<>("Cantidad");
                columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
                columnaCantidad.setStyle("-fx-alignment: CENTER;");
                columnaCantidad.setPrefWidth(100); // Establecer el ancho preferido de la columna

                TableColumn<Producto, Double> columnaSubtotal = new TableColumn<>("Subtotal");
                columnaSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
                columnaSubtotal.setStyle("-fx-alignment: CENTER;");
                columnaSubtotal.setPrefWidth(100); // Establecer el ancho preferido de la columna

                tableView.getColumns().add(columnaProducto);
                tableView.getColumns().add(columnaPrecio);
                tableView.getColumns().add(columnaCantidad);
                tableView.getColumns().add(columnaSubtotal);

                // Crear algunos datos de ejemplo
                ObservableList<Producto> products = FXCollections.observableArrayList();

                sql = "{call getSalesProducts(?)}";
                statement = con.prepareCall(sql);

                statement.setString(1, idSale);

                ResultSet res = statement.executeQuery();

                while (res.next()) {
                    String name = res.getString(1);
                    float price = res.getFloat(2);
                    int quantity = res.getInt(3);
                    float subtotalP = res.getFloat(4);

                    products.add(new Producto(name, "$"+df.format(price), quantity, "$"+df.format(subtotalP)));
                }

                tableView.setItems(products);

                vboxProducts.getChildren().add(tableView);

                hbox.getChildren().addAll(vboxSale, vboxProducts);

                pane.setContent(hbox);

                vboxSales.getChildren().add(pane);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
