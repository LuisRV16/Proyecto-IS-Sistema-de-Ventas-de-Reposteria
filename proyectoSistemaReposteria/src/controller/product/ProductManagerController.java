package controller.product;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ProductManagerController {

    @FXML
    private Button addProduct;

    @FXML
    private BorderPane ap;

    @FXML
    private VBox vboxProducts;

    private Connection con;

    DecimalFormat df = new DecimalFormat("########0.00");
    
    public void setCon(Connection con) {
        this.con = con;
    }

    public void inic() {

        String sql = "{call getProduct}";

        try (CallableStatement statement = con.prepareCall(sql)) {
            ResultSet resultados = statement.executeQuery();

            while (resultados.next()) {

                Pane pane = new Pane();
                pane.setMinSize(740, 200);

                String id = resultados.getString(1);

                byte[] datosImagen = resultados.getBytes(10);
                Image image = new Image(new ByteArrayInputStream(datosImagen));

                ImageView imageView = new ImageView(image);
                imageView.setLayoutX(25);
                imageView.setLayoutY(25);
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);

                String resVarchar = resultados.getString(2);
                Label lblName = new Label(resVarchar);
                lblName.setLayoutX(200);
                lblName.setLayoutY(40);
                lblName.setMinWidth(400);

                int stock = resultados.getInt(5);
                Label lblStock = new Label("Existencia: " + stock);
                lblStock.setLayoutX(550);
                lblStock.setLayoutY(40);

                float resFloat = resultados.getFloat(6);
                Label lblNormalPrice = new Label("Precio Normal: $" + df.format(resFloat));
                lblNormalPrice.setLayoutX(200);
                lblNormalPrice.setLayoutY(100);

                float descFloat = resultados.getFloat(7);
                Label lblDesc = new Label("Descuento: " + ((int) descFloat * 100) + "%");
                lblDesc.setLayoutX(400);
                lblDesc.setLayoutY(100);

                float precioFinal = resultados.getFloat(8);
                Label lblFinalPrice = new Label("Precio Final: $" + df.format(precioFinal));
                lblFinalPrice.setLayoutX(550);
                lblFinalPrice.setLayoutY(100);

                String productType = resultados.getString(9);
                String firstLetter = productType.charAt(0)+""; 
                productType = firstLetter.toUpperCase() + productType.substring(1);
                Label lblType = new Label("Tipo de Producto: " + productType);
                lblType.setLayoutX(200);
                lblType.setLayoutY(160);

                Button btnUpdate = new Button("Actualizar");
                btnUpdate.setLayoutX(600);
                btnUpdate.setLayoutY(150);

                EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/product/UpdateProduct.fxml"));
                            Parent root = loader.load();

                            UpdateProductController controller = loader.getController();
                            controller.setCon(con);
                            controller.setId(id);
                            controller.setName(resVarchar);
                            controller.inic();

                            ap.getChildren().setAll(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    
                };

                btnUpdate.setOnAction(event);

                pane.getChildren().addAll(
                    imageView,
                    lblName,
                    lblStock,
                    lblNormalPrice,
                    lblDesc,
                    lblFinalPrice,
                    lblType,
                    btnUpdate
                );

                vboxProducts.getChildren().add(pane);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void goToAddProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/product/AddProduct.fxml"));
        Parent root = loader.load();

        AddProductController controller = loader.getController();
        controller.setCon(con);
        controller.inic();

        ap.getChildren().setAll(root);
    }

}
