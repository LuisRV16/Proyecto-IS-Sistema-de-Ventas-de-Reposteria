package controller.product;

<<<<<<< HEAD
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
=======
import java.io.ByteArrayInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
>>>>>>> 4e3f8fa425f28709c69ffdb3a3b81c20943ab2b4

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

<<<<<<< HEAD
=======
    private Connection con;

    private ArrayList<String> productosEnCarrito;

    private HashMap<String, Integer> cantidadProducto;

    public void inic() {

        productosEnCarrito = new ArrayList<>();
        cantidadProducto = new HashMap<>();

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

                float precioFinal = resultados.getFloat(8);
                Label precioVenta = new Label("$" + df.format(precioFinal));
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

                EventHandler<ActionEvent> eventHandlerBtnAgregar = new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {
                        addProductoCart(resVarchar, precioFinal);
                    }

                    private void addProductoCart(String nombreProducto, float precio) {
                        Pane panelProduct = new Pane();
                        if (!productosEnCarrito.contains(nombreProducto)) {
                            productosEnCarrito.add(nombreProducto);
                            cantidadProducto.put(nombreProducto, 1);

                            panelProduct.setMinWidth(366);
                            panelProduct.setMinHeight(100);

                            Label nombreProduct = new Label(nombreProducto);
                            nombreProduct.setMinWidth(237);
                            nombreProduct.setMinHeight(50);
                            nombreProduct.setFont(new Font("Arial", 15));
                            nombreProduct.setLayoutX(0);
                            nombreProduct.setLayoutY(0);
                            nombreProduct.setAlignment(Pos.CENTER);

                            Button btnQuitar = new Button("-");
                            btnQuitar.setMinWidth(43);
                            btnQuitar.setMinHeight(35);
                            btnQuitar.setFont(new Font("Arial", 16));
                            btnQuitar.setLayoutX(237);
                            btnQuitar.setLayoutY(8);

                            Label cantidadProduct = new Label(cantidadProducto.get(nombreProducto)+"");
                            cantidadProduct.setMinWidth(43);
                            cantidadProduct.setMinHeight(35);
                            cantidadProduct.setFont(new Font("Arial", 12));
                            cantidadProduct.setLayoutX(280);
                            cantidadProduct.setLayoutY(8);
                            cantidadProduct.setAlignment(Pos.CENTER);

                            Button btnAnadir = new Button("+");
                            btnAnadir.setMinWidth(43);
                            btnAnadir.setMinHeight(35);
                            btnAnadir.setFont(new Font("Arial", 12));
                            btnAnadir.setLayoutX(323);
                            btnAnadir.setLayoutY(8);

                            Label subtotal = new Label(nombreProducto + " Subtotal: $" + df.format(precio));
                            subtotal.setMinWidth(366);
                            subtotal.setMinHeight(50);
                            subtotal.setLayoutX(0);
                            subtotal.setLayoutY(50);
                            subtotal.setFont(new Font("Arial", 12));
                            subtotal.setAlignment(Pos.CENTER);

                            panelProduct.getChildren().add(nombreProduct);
                            panelProduct.getChildren().add(btnQuitar);
                            panelProduct.getChildren().add(cantidadProduct);
                            panelProduct.getChildren().add(btnAnadir);
                            panelProduct.getChildren().add(subtotal);

                            vboxPreCart.getChildren().add(panelProduct);
                        } else {
                            int cant = cantidadProducto.get(nombreProducto);
                            cantidadProducto.replace(nombreProducto, cant + 1);
                            cant = cantidadProducto.get(nombreProducto);

                            int index = vboxPreCart.getChildren().indexOf(panelProduct);
                            Pane panel = (Pane)vboxPreCart.getChildren().get(index);
                            Label lbl = (Label)panel.getChildren().get(2);
                            vboxPreCart.getChildren().set(index, btnAgregar);
                        }

                    }
                    
                };

                btnAgregar.setOnAction(eventHandlerBtnAgregar);

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

>>>>>>> 4e3f8fa425f28709c69ffdb3a3b81c20943ab2b4
    @FXML
    void busqueda(ActionEvent event) {

    }

    @FXML
    void ordenamiento(ActionEvent event) {

    }

}
