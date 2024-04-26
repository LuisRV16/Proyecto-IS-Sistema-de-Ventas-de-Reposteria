package controller.client;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddClientController {

    @FXML
    private Button cancelButton;

    @FXML
    private Label lblApellido1;

    @FXML
    private Label lblApellido2;

    @FXML
    private Label lblCP;

    @FXML
    private Label lblCalle;

    @FXML
    private Label lblCiudad;

    @FXML
    private Label lblColonia;

    @FXML
    private Label lblCorreo;

    @FXML
    private Label lblEstado;

    @FXML
    private Label lblExterior;

    @FXML
    private Label lblInterior;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblTelefono;

    @FXML
    private Button saveButton;

    @FXML
    private TextField txtCP;

    @FXML
    private TextField txtCalle;

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtColonia;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtEstado;

    @FXML
    private TextField txtExterior;

    @FXML
    private TextField txtInterior;

    @FXML
    private TextField txtMaterno;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPaterno;

    @FXML
    private TextField txtTelefono;

    Connection con;

    public void setCon(Connection con) {
        this.con = con;
    }

    @FXML
    void addClient(ActionEvent event) {
        String nombre = txtNombre.getText();
        String apellido1 = txtPaterno.getText();
        String apellido2 = txtMaterno.getText();
        String telefono = txtTelefono.getText();
        String email = txtCorreo.getText();
        String calle = txtCalle.getText();
        String numInterior = txtInterior.getText();
        String numExterior = txtExterior.getText();
        String codPost = txtCP.getText();
        String colonia = txtColonia.getText();
        String ciudad = txtCiudad.getText();
        String estado = txtEstado.getText();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");

        if (nombre.isEmpty()) {
            alert.setContentText("Ingrese un nombre en el campo de texto");
            alert.showAndWait();
            return;
        } else if (apellido1.isEmpty()) {
            alert.setContentText("Ingrese un apellido paterno en el campo de texto");
            alert.showAndWait();
            return;
        } else if (telefono.isEmpty()) {
            alert.setContentText("Ingrese un numero de telefono en el campo de texto");
            alert.showAndWait();
            return;
        } else if (email.isEmpty()) {
            alert.setContentText("Ingrese un correo en el campo de texto");
            alert.showAndWait();
            return;
        } else if (numInterior.isEmpty()) {
            numExterior = "S/N";
        } else if (numExterior.isEmpty()) {
            alert.setContentText("Ingrese un numero exterior en el campo de texto");
            alert.showAndWait();
            return;
        } else if (codPost.isEmpty()) {
            alert.setContentText("Ingrese un codigo postal en el campo de texto");
            alert.showAndWait();
            return;
        } else if (colonia.isEmpty()) {
            alert.setContentText("Ingrese una colonia en el campo de texto");
            alert.showAndWait();
            return;
        } else if (ciudad.isEmpty()) {
            alert.setContentText("Ingrese una ciudad en el campo de texto");
            alert.showAndWait();
            return;
        } else if (estado.isEmpty()) {
            alert.setContentText("Ingrese un estado en el campo de texto");
            alert.showAndWait();
            return;
        }

        String sql = "{call addClient(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setString(1, nombre);
            statement.setString(2, apellido1);
            statement.setString(3, apellido2);
            statement.setString(4, telefono);
            statement.setString(5, email);
            statement.setString(6, calle);
            statement.setString(7, numInterior);
            statement.setString(8, numExterior);
            statement.setString(9, codPost);
            statement.setString(10, colonia);
            statement.setString(11, ciudad);
            statement.setString(12, estado);
            statement.registerOutParameter(13, Types.VARCHAR);

            statement.execute();

            String msg = statement.getString(13);
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Mensaje");
            if (msg.equals("Ha ocurrido un error al insertar el cliente con ID aleatorio.")
                || msg.equals("El cliente ya existe.")) {
                clean();
            }
            alert.setContentText(msg);
            alert.showAndWait();

            if (msg.equals("Cliente insertado correctamente con ID aleatorio.")) {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("¿Desea limpiar los campos de texto?");

                // Agrega los botones de Sí y No a la alerta
                alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

                // Muestra la alerta y espera a que el usuario seleccione una opción
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        // El usuario seleccionó "Sí"
                        clean();
                    }
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void goBack(ActionEvent event) {
        try {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/client/menuCliente.fxml"));
            Parent root = loader.load();

            MenuClienteController controller = loader.getController();
            controller.setCon(con);
            controller.inic();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clean() {
        txtNombre.setText("");
        txtPaterno.setText("");
        txtMaterno.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtCalle.setText("");
        txtInterior.setText("");
        txtExterior.setText("");
        txtCP.setText("");
        txtColonia.setText("");
        txtCiudad.setText("");
        txtEstado.setText("");
    }

}
