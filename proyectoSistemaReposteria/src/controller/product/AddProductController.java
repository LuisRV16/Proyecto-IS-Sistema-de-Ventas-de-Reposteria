package controller.product;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class AddProductController {

    @FXML
    private AnchorPane ap;

    @FXML
    private Pane paneImage;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnGoBack;

    @FXML
    private ComboBox<String> comboTypeProduct;

    @FXML
    private ImageView imageView;

    @FXML
    private Pane pane;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtFinalPrice;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNormalPrice;

    @FXML
    private TextField txtStock;

    @FXML
    private TextField txtWeight;

    @FXML
    private TextArea txtaDescription;

    private Connection con;

    private String imagePath;

    public void setCon(Connection con) {
        this.con = con;
    }

    public void inic() {

        txtName.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 40) {
                return change;
            }
            return null;
        }));

        txtNormalPrice.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) {
                return change; // Permitir campo vacío
            }
            try {
                double newValue = Double.parseDouble(newText); // Intentar convertir a Double
                if (newValue >= 1.0 && newText.length() <= 11) { // Verificar que el valor sea mayor o igual a 1
                    return change;
                } else {
                    return null; // Rechazar el cambio si es menor que 1
                }
            } catch (NumberFormatException e) {
                return null; // Rechazar el cambio si no es un número válido
            }
        }));

        txtDesc.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) {
                return change; // Permitir campo vacío
            }
            try {
                int newValue = Integer.parseInt(newText); // Intentar convertir a Integer
                if (newValue >= 0 && newValue <= 100) {
                    return change;
                } else {
                    return null; // Rechazar el cambio si está fuera del rango
                }
            } catch (NumberFormatException e) {
                return null; // Rechazar el cambio si no es un número válido
            }
        }));

        txtWeight.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) {
                return change; // Permitir campo vacío
            }
            try {
                int newValue = Integer.parseInt(newText); // Intentar convertir a Integer
                if (newValue >= 0) {
                    return change;
                } else {
                    return null; // Rechazar el cambio si está fuera del rango
                }
            } catch (NumberFormatException e) {
                return null; // Rechazar el cambio si no es un número válido
            }
        }));

        txtStock.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) {
                return change; // Permitir campo vacío
            }
            try {
                int newValue = Integer.parseInt(newText); // Intentar convertir a Integer
                if (newValue >= 0) {
                    return change;
                } else {
                    return null; // Rechazar el cambio si está fuera del rango
                }
            } catch (NumberFormatException e) {
                return null; // Rechazar el cambio si no es un número válido
            }
        }));

        txtaDescription.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 400) {
                return change;
            }
            return null;
        }));

        Tooltip tooltip = new Tooltip("Haga click para cambiar la imagén.");

        tooltip.setShowDelay(Duration.millis(100));

        Tooltip.install(imageView, tooltip);

        pane.getChildren().set(0, imageView);

        comboTypeProduct.getItems().setAll("dulce", "pastel");

    }

    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione una imagén");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePath = selectedFile.toURI().toString();
            Image image = new Image(imagePath);
            imageView.setImage(image);
        }
    }

    private byte[] imageToByteArray(Image image) {
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    void add(ActionEvent event) throws SQLException, IOException {

        String msg;
        float normalPrice;
        float desc;
        int weight;
        int stock;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Mensaje");

        Image image = imageView.getImage();
        String sql = "{call addProduct(?,?,?,?,?,?,?,?,?)}";

        if (image == null) {
            msg = "Se requiere que haya seleccionado una imagén.";
            alert.setContentText(msg);
            alert.showAndWait();
            return;
        }

        byte[] imageBytes = imageToByteArray(image);
        String newName = txtName.getText();

        if (txtNormalPrice.getText().equals("")) {
            msg = "Ingrese un valor numérico para el precio Normal";
            alert.setContentText(msg);
            alert.showAndWait();
            return;
        } else {
            normalPrice = Float.parseFloat(txtNormalPrice.getText());
        }

        if (txtDesc.getText().equals("")) {
            msg = "Ingrese un valor numérico para el Descuento";
            alert.setContentText(msg);
            alert.showAndWait();
            return;
        } else {
            desc = Float.parseFloat(txtDesc.getText());
        }

        if (txtWeight.getText().equals("")) {
            msg = "Ingrese un valor numérico para el Peso del producto";
            alert.setContentText(msg);
            alert.showAndWait();
            return;
        } else {
            weight = Integer.parseInt(txtWeight.getText());
        }

        if (txtStock.getText().equals("")) {
            msg = "Ingrese un valor numérico para el Stock del producto";
            alert.setContentText(msg);
            alert.showAndWait();
            return;
        } else {
            stock = Integer.parseInt(txtStock.getText());
        }

        String type = comboTypeProduct.getSelectionModel().getSelectedItem();
        String description = txtaDescription.getText();

        CallableStatement statement = con.prepareCall(sql);

        statement.setString(1, newName);
        statement.setInt(2, weight);
        statement.setString(3, description);
        statement.setInt(4, stock);
        statement.setFloat(5, normalPrice);
        statement.setFloat(6, desc);
        statement.setString(7, type);
        statement.setBytes(8, imageBytes);
        statement.registerOutParameter(9, Types.VARCHAR);

        statement.execute();

        msg = statement.getString(9);
        alert.setContentText(msg);
        alert.showAndWait();

        if (msg.equals("El producto ha sido registrado exitosamente.")) {
            goBack();
        }

    }

    @FXML
    void addImage(MouseEvent event) {
        openFileChooser();
    }

    @FXML
    void calculateFinalPrice(KeyEvent event) {
        String normalPrice = txtNormalPrice.getText();
        String desc = txtDesc.getText();

        if (!normalPrice.equals("") && !desc.equals("")) {
            float price = Float.parseFloat(normalPrice) - Float.parseFloat(normalPrice) * (Float.parseFloat(desc)/100);
            txtFinalPrice.setText(price + "");
        } else {
            txtFinalPrice.setText("");
        }

    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        goBack();
    }

    private void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/product/ProductManager.fxml"));
        Parent root = loader.load();
        ProductManagerController controller = loader.getController();
        controller.setCon(con);
        controller.inic();
        ap.getChildren().setAll(root);
    }

}
