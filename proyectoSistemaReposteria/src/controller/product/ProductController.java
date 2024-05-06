package controller.product;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.UnaryOperator;

import controller.mainMenu.MenuController;
import controller.sales.ShopCartController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

public class ProductController {

    @FXML
    private Button btnGoToCart;

    @FXML
    private Button btnGoBack;

    @FXML
    private ComboBox<?> comboOrdenar;

    @FXML
    private FlowPane flowPaneProductos;

    @FXML
    private Label lblSubtotal;

    @FXML
    private ScrollPane spProductos;

    @FXML
    private TextField txtBuscar;

    @FXML
    private VBox vboxPreCart;

    private DecimalFormat df = new DecimalFormat("#.00");

    private Connection con;

    private ArrayList<String> productosEnCarrito = new ArrayList<>();

    private HashMap<String, Integer> cantidadProducto = new HashMap<>();

    private HashMap<String, Integer> existenciaProducto = new HashMap<>();

    private HashMap<String, Float> precioProducto = new HashMap<>();

    private HashMap<String, byte[]> imagenProducto = new HashMap<>();

    ObservableList<Pane> panels;

    private float subtotalF;

    private String employeeName;

    private String employeeLastName1;

    private String employeeLastName2;

    @FXML
    void goBack(ActionEvent event) {
        try {
            Stage stage = (Stage) btnGoBack.getScene().getWindow(); // Obtener la ventana actual
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainMenu/menu.fxml"));
            Parent root = loader.load();

            MenuController controller = loader.getController();
            controller.setCon(con);
            controller.setEmployeeName(employeeName);
            controller.setEmployeeLastName1(employeeLastName1);
            controller.setEmployeeLastName2(employeeLastName2);

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProductosEnCarrito(ArrayList<String> productosEnCarrito) {
        this.productosEnCarrito = productosEnCarrito;
    }

    public void setCantidadProducto(HashMap<String, Integer> cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
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

        panels = FXCollections.observableArrayList();
        btnGoToCart.setDisable(true);
        String sql = "{call getProduct}";

        try (CallableStatement statement = con.prepareCall(sql)) {
            ResultSet resultados = statement.executeQuery();

            while (resultados.next()) {
                byte[] datosImagen = resultados.getBytes(10);
                Image image = new Image(new ByteArrayInputStream(datosImagen));
                String resVarchar = resultados.getString(2);
                imagenProducto.put(resVarchar, datosImagen);

                float resFloat = resultados.getFloat(6);
                float descFloat = resultados.getFloat(7);

                float precioFinal = resultados.getFloat(8);
                precioProducto.put(resVarchar, precioFinal);

                int existencia = resultados.getInt(5);
                existenciaProducto.put(resVarchar, existencia);

                panels.add(setPane(creatPane(), image, resVarchar, resFloat, descFloat, precioFinal, existencia));
            }

            flowPaneProductos.getChildren().addAll(panels);
            resultados.close();

            if (!productosEnCarrito.isEmpty()) {
                for (String nombre : productosEnCarrito) {
                    int cantidad = cantidadProducto.get(nombre);
                    float precio = precioProducto.get(nombre);
                    addProductoCart(nombre, precio, cantidad, existenciaProducto.get(nombre));
                }
                btnGoToCart.setDisable(false);
            }

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
    void goToCart(ActionEvent event) {

        Stage stage = (Stage) btnGoToCart.getScene().getWindow(); // Obtener la ventana actual
        stage.close(); // Esconder la ventana actual

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

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Pane creatPane() {
        Pane panelProducto = new Pane();

        panelProducto.setMinWidth(200);
        panelProducto.setMinHeight(200);
        panelProducto.setMaxHeight(200);
        panelProducto.setMaxWidth(200);
        panelProducto.setBackground(
                new Background(new BackgroundFill(Color.BEIGE, new CornerRadii(10), new Insets(3))));

        ImageView imagen = new ImageView(); //0

        imagen.setFitWidth(100);
        imagen.setFitHeight(100);
        imagen.setLayoutX(50);
        imagen.setLayoutY(25);

        Label nombreProducto = new Label(); //1

        nombreProducto.setFont(new Font("Arial", 12));
        nombreProducto.setAlignment(Pos.CENTER);
        nombreProducto.setMinWidth(panelProducto.getMinWidth());
        nombreProducto.setLayoutX(0);
        nombreProducto.setLayoutY(125);

        Text precioNormal = new Text(); //2

        precioNormal.setStyle("-fx-strikethrough: true;");
        precioNormal.setFont(new Font("Arial", 12));
        precioNormal.setLayoutX(80);
        precioNormal.setLayoutY(161);

        Label descuento = new Label(); //3

        descuento.setFont(new Font("Arial", 15));
        descuento.setTextFill(Color.GREEN);
        descuento.setBackground(
                new Background(new BackgroundFill(Color.GREENYELLOW, new CornerRadii(7), Insets.EMPTY)));
        descuento.setLayoutX(150);
        descuento.setLayoutY(139);

        Label precioVenta = new Label(); //4

        precioVenta.setFont(new Font("Arial", 12));
        precioVenta.setLayoutX(10);
        precioVenta.setLayoutY(145);

        Label stock = new Label(); //5

        stock.setFont(new Font("Arial", 12));
        stock.setLayoutX(10);
        stock.setLayoutY(165);

        Button btnAgregar = new Button("Agregar"); //6

        btnAgregar.setBackground(
                new Background(new BackgroundFill(Color.AQUA, new CornerRadii(10), Insets.EMPTY)));
        btnAgregar.setLayoutX(115);
        btnAgregar.setLayoutY(165);

        panelProducto.getChildren().addAll(imagen, nombreProducto, precioNormal, descuento, precioVenta, stock, btnAgregar);

        return panelProducto;
    }

    private Pane setPane(Pane panelProd, Image image, String resVarchar, float resFloat, float descFloat, float precioFinal, int existencia) {

        ImageView iv = (ImageView) panelProd.getChildren().get(0);
        iv.setImage(image);
        panelProd.getChildren().set(0, iv);

        Label label = (Label) panelProd.getChildren().get(1);
        label.setText(resVarchar);
        if (resVarchar.length() > 25)
            label.setId("labelProductosXL");
        panelProd.getChildren().set(1, label);

        Text text = (Text) panelProd.getChildren().get(2);
        text.setText("$" + df.format(resFloat));
        text.setVisible(descFloat > 0 ? true : false);
        panelProd.getChildren().set(2, text);

        label = (Label) panelProd.getChildren().get(3);
        label.setText(((int) (descFloat * 100)) + "%");
        label.setVisible(descFloat > 0 ? true : false);
        panelProd.getChildren().set(3, label);

        label = (Label) panelProd.getChildren().get(4);
        label.setText("$" + df.format(precioFinal));
        panelProd.getChildren().set(4, label);

        label = (Label) panelProd.getChildren().get(5);
        label.setText("Stock: " + existencia);

        if (existencia == 0) {
            label.setTextFill(Color.BROWN);
            label.setBackground(
                    new Background(new BackgroundFill(Color.ORANGE, new CornerRadii(7), Insets.EMPTY)));
        } else {
            label.setTextFill(Color.BLACK);
            label.setBackground(
                    new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(7), Insets.EMPTY)));
        }

        panelProd.getChildren().set(5, label);

        Button btn = (Button) panelProd.getChildren().get(6);
        btn.setDisable(existencia == 0 ? true : false);

        EventHandler<ActionEvent> eventHandlerBtnAgregar = new EventHandler<ActionEvent>() {

            int cont = 0;

            @Override
            public void handle(ActionEvent arg0) {
                if (cont != existencia) {
                    addProductoCart(resVarchar, precioFinal, existencia);
                    if (btnGoToCart.isDisable())
                        btnGoToCart.setDisable(false);
                    cont ++;
                }
            }
            
        };

        btn.setOnAction(eventHandlerBtnAgregar);

        panelProd.getChildren().set(6, btn);

        return panelProd;
    }

    private void addProductoCart(String nombreProducto, float precio, int existencia) {

        DecimalFormat df = new DecimalFormat("##.00");

        Pane panelProduct = new Pane();
        if (!productosEnCarrito.contains(nombreProducto)) {
            productosEnCarrito.add(nombreProducto);
            cantidadProducto.put(nombreProducto, 1);

            panelProduct.setMinWidth(345);
            panelProduct.setMinHeight(100);
            panelProduct.setMaxHeight(100);
            panelProduct.setMaxWidth(345);
            panelProduct.setId("paneBackDeg");

            Label nombreProduct = new Label(nombreProducto);
            nombreProduct.setMinWidth(220);
            nombreProduct.setMinHeight(50);
            nombreProduct.setFont(new Font("Arial", 15));
            nombreProduct.setLayoutX(0);
            nombreProduct.setLayoutY(0);
            nombreProduct.setAlignment(Pos.CENTER);

            Button btnQuitar = new Button("-");
            btnQuitar.setMinWidth(35);
            btnQuitar.setMinHeight(35);
            btnQuitar.setFont(new Font("Arial", 16));
            btnQuitar.setLayoutX(220);
            btnQuitar.setLayoutY(5);

            EventHandler<ActionEvent> eventHandlerBtnQuitar = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    int cant = cantidadProducto.get(nombreProducto);
                    int index = productosEnCarrito.indexOf(nombreProducto);
                    subtotalF -= precio;

                    if (cant == 1) {
                        vboxPreCart.getChildren().remove(index);
                        productosEnCarrito.remove(nombreProducto);
                        cantidadProducto.remove(nombreProducto);

                        if (vboxPreCart.getChildren().isEmpty()) {
                            lblSubtotal.setText("Subtotal:");
                            btnGoToCart.setDisable(true);
                        }

                    } else {
                        cantidadProducto.replace(nombreProducto, cant - 1);
                        cant = cantidadProducto.get(nombreProducto);
                        float precioNuevo = precio * ((float) cant);

                        Pane panel = (Pane) vboxPreCart.getChildren().get(index);
                        Label lbl = (Label) panel.getChildren().get(2);
                        Label lbl2 = (Label) panel.getChildren().get(4);

                        lbl.setText(cant + "");
                        lbl2.setText(nombreProducto + " Subtotal: $" + df.format(precioNuevo));
                        panel.getChildren().set(2, lbl);
                        panel.getChildren().set(4, lbl2);
                        vboxPreCart.getChildren().set(index, panel);
                    }

                    if (vboxPreCart.getChildren().isEmpty())
                        lblSubtotal.setText("Subtotal:");
                    else
                        lblSubtotal.setText("Subtotal: $" + df.format(subtotalF));

                }

            };

            btnQuitar.setOnAction(eventHandlerBtnQuitar);

            Label cantidadProduct = new Label(cantidadProducto.get(nombreProducto) + "");
            cantidadProduct.setMinWidth(35);
            cantidadProduct.setMinHeight(35);
            cantidadProduct.setFont(new Font("Arial", 12));
            cantidadProduct.setLayoutX(255);
            cantidadProduct.setLayoutY(5);
            cantidadProduct.setAlignment(Pos.CENTER);

            Button btnAnadir = new Button("+");
            btnAnadir.setMinWidth(35);
            btnAnadir.setMinHeight(35);
            btnAnadir.setFont(new Font("Arial", 12));
            btnAnadir.setLayoutX(290);
            btnAnadir.setLayoutY(5);

            EventHandler<ActionEvent> eventHandlerBtnAnadir = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    int cant = cantidadProducto.get(nombreProducto);
                    if (cant != existencia) {
                        cantidadProducto.replace(nombreProducto, cant + 1);
                        cant = cantidadProducto.get(nombreProducto);

                        int index = productosEnCarrito.indexOf(nombreProducto);
                        float precioNuevo = precio * ((float) cant);
                        subtotalF += precioNuevo - precio * ((float) cant - 1);

                        Pane panel = (Pane) vboxPreCart.getChildren().get(index);
                        Label lbl = (Label) panel.getChildren().get(2);
                        Label lbl2 = (Label) panel.getChildren().get(4);

                        lbl.setText(cant + "");
                        lbl2.setText(nombreProducto + " Subtotal: $" + df.format(precioNuevo));
                        panel.getChildren().set(2, lbl);
                        panel.getChildren().set(4, lbl2);
                        vboxPreCart.getChildren().set(index, panel);

                        lblSubtotal.setText("Subtotal: $" + df.format(subtotalF));
                    }
                }

            };

            btnAnadir.setOnAction(eventHandlerBtnAnadir);

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

            subtotalF += precio;

        } else {
            int cant = cantidadProducto.get(nombreProducto);
            cantidadProducto.replace(nombreProducto, cant + 1);
            cant = cantidadProducto.get(nombreProducto);

            int index = productosEnCarrito.indexOf(nombreProducto);
            float precioNuevo = precio * ((float) cant);
            subtotalF += precioNuevo - precio * ((float) cant - 1);

            Pane panel = (Pane) vboxPreCart.getChildren().get(index);
            Label lbl = (Label) panel.getChildren().get(2);
            Label lbl2 = (Label) panel.getChildren().get(4);

            lbl.setText(cant + "");
            lbl2.setText(nombreProducto + " Subtotal: $" + df.format(precioNuevo));
            panel.getChildren().set(2, lbl);
            panel.getChildren().set(4, lbl2);
            vboxPreCart.getChildren().set(index, panel);
        }
        lblSubtotal.setText("Subtotal: $" + df.format(subtotalF));
    }

    private void addProductoCart(String nombreProducto, float precio, int cantidad, int existencia) {

        DecimalFormat df = new DecimalFormat("##.00");

        Pane panelProduct = new Pane();

        panelProduct.setMinWidth(345);
        panelProduct.setMinHeight(100);
        panelProduct.setMaxHeight(100);
        panelProduct.setMaxWidth(345);
        panelProduct.setId("paneBackDeg");

        Label nombreProduct = new Label(nombreProducto);
        nombreProduct.setMinWidth(220);
        nombreProduct.setMinHeight(50);
        nombreProduct.setFont(new Font("Arial", 15));
        nombreProduct.setLayoutX(0);
        nombreProduct.setLayoutY(0);
        nombreProduct.setAlignment(Pos.CENTER);

        Button btnQuitar = new Button("-");
        btnQuitar.setMinWidth(35);
        btnQuitar.setMinHeight(35);
        btnQuitar.setFont(new Font("Arial", 16));
        btnQuitar.setLayoutX(220);
        btnQuitar.setLayoutY(5);

        EventHandler<ActionEvent> eventHandlerBtnQuitar = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                int cant = cantidadProducto.get(nombreProducto);
                int index = productosEnCarrito.indexOf(nombreProducto);
                subtotalF -= precio;

                if (cant == 1) {
                    vboxPreCart.getChildren().remove(index);
                    productosEnCarrito.remove(nombreProducto);
                    cantidadProducto.remove(nombreProducto);

                    if (vboxPreCart.getChildren().isEmpty()) {
                        lblSubtotal.setText("Subtotal:");
                        btnGoToCart.setDisable(true);
                    }

                } else {
                    cantidadProducto.replace(nombreProducto, cant - 1);
                    cant = cantidadProducto.get(nombreProducto);
                    float precioNuevo = precio * ((float) cant);

                    Pane panel = (Pane) vboxPreCart.getChildren().get(index);
                    Label lbl = (Label) panel.getChildren().get(2);
                    Label lbl2 = (Label) panel.getChildren().get(4);

                    lbl.setText(cant + "");
                    lbl2.setText(nombreProducto + " Subtotal: $" + df.format(precioNuevo));
                    panel.getChildren().set(2, lbl);
                    panel.getChildren().set(4, lbl2);
                    vboxPreCart.getChildren().set(index, panel);
                }

                if (vboxPreCart.getChildren().isEmpty())
                    lblSubtotal.setText("Subtotal:");
                else
                    lblSubtotal.setText("Subtotal: $" + df.format(subtotalF));

            }

        };

        btnQuitar.setOnAction(eventHandlerBtnQuitar);

        Label cantidadProduct = new Label(cantidadProducto.get(nombreProducto) + "");
        cantidadProduct.setMinWidth(35);
        cantidadProduct.setMinHeight(35);
        cantidadProduct.setFont(new Font("Arial", 12));
        cantidadProduct.setLayoutX(255);
        cantidadProduct.setLayoutY(5);
        cantidadProduct.setAlignment(Pos.CENTER);

        Button btnAnadir = new Button("+");
        btnAnadir.setMinWidth(35);
        btnAnadir.setMinHeight(35);
        btnAnadir.setFont(new Font("Arial", 12));
        btnAnadir.setLayoutX(290);
        btnAnadir.setLayoutY(5);

        EventHandler<ActionEvent> eventHandlerBtnAnadir = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                int cant = cantidadProducto.get(nombreProducto);
                if (cant != existencia) {
                    cantidadProducto.replace(nombreProducto, cant + 1);
                    cant = cantidadProducto.get(nombreProducto);

                    int index = productosEnCarrito.indexOf(nombreProducto);
                    float precioNuevo = precio * ((float) cant);
                    subtotalF += precioNuevo - precio * ((float) cant - 1);

                    Pane panel = (Pane) vboxPreCart.getChildren().get(index);
                    Label lbl = (Label) panel.getChildren().get(2);
                    Label lbl2 = (Label) panel.getChildren().get(4);

                    lbl.setText(cant + "");
                    lbl2.setText(nombreProducto + " Subtotal: $" + df.format(precioNuevo));
                    panel.getChildren().set(2, lbl);
                    panel.getChildren().set(4, lbl2);
                    vboxPreCart.getChildren().set(index, panel);

                    lblSubtotal.setText("Subtotal: $" + df.format(subtotalF));
                }
            }
        };

        btnAnadir.setOnAction(eventHandlerBtnAnadir);

        Label subtotal = new Label(nombreProducto + " Subtotal: $" + df.format(precio * ((float) cantidad)));
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
        subtotalF += precio * ((float) cantidad);
        lblSubtotal.setText("Subtotal: $" + df.format(subtotalF));

    }

    @FXML
    void ordenamiento(ActionEvent event) {

    }


}
