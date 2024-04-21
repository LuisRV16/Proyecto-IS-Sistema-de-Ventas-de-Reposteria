package controller.product;

import java.io.ByteArrayInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ProductController {

    @FXML
    private ComboBox<?> comboOrdenar;

    @FXML
    private FlowPane flowPaneProductos;

    @FXML
    private ScrollPane spProductos;

    @FXML
    private TextField txtBuscar;

    @FXML
    private VBox vboxPreCart;

    private Connection con;

    public void inic() {
        String sql = "{call getProduct}";

        try (CallableStatement statement = con.prepareCall(sql)) {
            ResultSet resultados = statement.executeQuery();

            while (resultados.next()) {
                Pane panelProducto = new Pane();

                panelProducto.setMinWidth(200);
                panelProducto.setMinHeight(200);
                panelProducto.setBackground(new Background(new BackgroundFill(Color.BEIGE, new CornerRadii(10), new Insets(3))));

                byte[] datosImagen = resultados.getBytes(11);

                Image image = new Image(new ByteArrayInputStream(datosImagen));

                ImageView imagen = new ImageView();
                imagen.setImage(image);
                imagen.setFitWidth(100);
                imagen.setFitHeight(100);
                imagen.setLayoutX(50);
                imagen.setLayoutY(25);

                String resVarchar = resultados.getString(2);
                Label nombreProducto = new Label(resVarchar);

                nombreProducto.setFont(new Font("Arial", 12));
                nombreProducto.setAlignment(Pos.CENTER);
                nombreProducto.setMinWidth(panelProducto.getMinWidth());
                nombreProducto.setLayoutX(0);
                nombreProducto.setLayoutY(125);

                DecimalFormat df = new DecimalFormat("#.00");

                float resFloat = resultados.getFloat(6);
                Text precioNormal = new Text("$" + df.format(resFloat));
                precioNormal.setStyle("-fx-strikethrough: true;");
                precioNormal.setFont(new Font("Arial", 12));
                precioNormal.setLayoutX(80);
                precioNormal.setLayoutY(157);
                
                float descFloat = resultados.getFloat(7);
                Label descuento = new Label(((int)descFloat * 100) + "%");
                descuento.setFont(new Font("Arial", 15));
                descuento.setTextFill(Color.GREEN);
                descuento.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, new CornerRadii(7), Insets.EMPTY)));
                descuento.setLayoutX(150);
                descuento.setLayoutY(145);

                resFloat = resultados.getFloat(8);
                Label precioVenta = new Label("$" + df.format(resFloat));
                precioVenta.setFont(new Font("Arial", 12));
                precioVenta.setLayoutX(10);
                precioVenta.setLayoutY(145);

                // precioNormal.setVisible(descFloat > 0 ? true : false);
                // descuento.setVisible(descFloat > 0 ? true : false);

                int existencia = resultados.getInt(5);
                Label stock = new Label("Stock: " + existencia);
                stock.setFont(new Font("Arial", 12));
                stock.setLayoutX(10);
                stock.setLayoutY(165);

                if (existencia == 0) {
                    stock.setTextFill(Color.BROWN);
                    stock.setBackground(new Background(new BackgroundFill(Color.ORANGE, new CornerRadii(7), Insets.EMPTY)));
                } else {
                    stock.setTextFill(Color.BLACK);
                    stock.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(7), Insets.EMPTY)));
                }

                Button btnAgregar = new Button("Agregar");
                btnAgregar.setDisable(existencia == 0 ? true : false);
                btnAgregar.setBackground(new Background(new BackgroundFill(Color.AQUA, new CornerRadii(10), Insets.EMPTY)));
                btnAgregar.setLayoutX(115);
                btnAgregar.setLayoutY(165);

                EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {
                        addProductoCart(resVarchar);
                    }

                    private void addProductoCart(String nombreProducto) {
                        
                    }
                    
                };


                panelProducto.getChildren().add(imagen);
                panelProducto.getChildren().add(nombreProducto);
                panelProducto.getChildren().add(precioVenta);
                panelProducto.getChildren().add(precioNormal);
                panelProducto.getChildren().add(descuento);
                panelProducto.getChildren().add(stock);
                panelProducto.getChildren().add(btnAgregar);

                flowPaneProductos.getChildren().add(panelProducto);
            }

            resultados.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    @FXML
    void busqueda(ActionEvent event) {

    }

    @FXML
    void ordenamiento(ActionEvent event) {

    }

}
