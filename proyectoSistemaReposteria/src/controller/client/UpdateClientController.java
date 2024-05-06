package controller.client;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UpdateClientController {
    @FXML
    private BorderPane ap;

    @FXML
    private Button btnSaveButton;
    
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
    private Label lblRFC;

    @FXML
    private Label lblTelefono;

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
    private TextField txtRFC;

    @FXML
    private TextField txtTelefono;

    private String nombre;

    private String apellido1;

    private String apellido2;

    private String rfc;

    private Connection con;

    private String employeeName;

    private String employeeLastName1;

    private String employeeLastName2;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public void setCon(Connection con) {
        this.con = con;
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
        txtTelefono.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 15) {
                return change;
            }
            return null;
        }));
        txtPaterno.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 20) {
                return change;
            }
            return null;
        }));
        txtNombre.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 40) {
                return change;
            }
            return null;
        }));
        txtMaterno.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 20) {
                return change;
            }
            return null;
        }));
        txtRFC.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 13) {
                return change;
            }
            return null;
        }));
        txtInterior.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 5) {
                return change;
            }
            return null;
        }));
        txtExterior.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 6) {
                return change;
            }
            return null;
        }));
        txtEstado.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 30) {
                return change;
            }
            return null;
        }));
        txtCorreo.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 255) {
                return change;
            }
            return null;
        }));
        txtColonia.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 30) {
                return change;
            }
            return null;
        }));
        txtCiudad.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 30) {
                return change;
            }
            return null;
        }));
        txtCalle.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 30) {
                return change;
            }
            return null;
        }));
        txtCP.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 5) {
                return change;
            }
            return null;
        }));

        txtNombre.setText(nombre);
        txtPaterno.setText(apellido1);
        txtMaterno.setText(apellido2);

        String sql = "{call getClientByName(?, ?, ?)}";

        try (CallableStatement statement = con.prepareCall(sql)) {

            statement.setString(1, nombre);
            statement.setString(2, apellido1);
            statement.setString(3, apellido2);

            ResultSet resultados = statement.executeQuery();

            while (resultados.next()) {
                rfc = resultados.getString(1);
                String phone = resultados.getString(5);
                String email = resultados.getString(6);
                String street = resultados.getString(7);
                String interiorNumber = resultados.getString(8);
                String outdoorNumber = resultados.getString(9);
                String postalCode = resultados.getString(10);
                String colony = resultados.getString(11);
                String city = resultados.getString(12);
                String state = resultados.getString(13);

                txtNombre.setText(nombre);
                txtPaterno.setText(apellido1);
                txtMaterno.setText(apellido2);
                txtRFC.setText(rfc);
                txtTelefono.setText(phone);
                txtCorreo.setText(email);
                txtCalle.setText(street);
                txtInterior.setText(interiorNumber);
                txtExterior.setText(outdoorNumber);
                txtColonia.setText(colony);
                txtCP.setText(postalCode);
                txtCiudad.setText(city);
                txtEstado.setText(state);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        try {
            goBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void update(ActionEvent event) {        
        String sql = "{call updateClient(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setString(1, rfc);
            statement.setString(2, txtTelefono.getText());
            statement.setString(3, txtCorreo.getText());
            statement.setString(4, txtCalle.getText());
            statement.setString(5, txtInterior.getText());
            statement.setString(6, txtExterior.getText());
            statement.setString(7, txtCP.getText());
            statement.setString(8, txtColonia.getText());
            statement.setString(9, txtCiudad.getText());
            statement.setString(10, txtEstado.getText());
            statement.registerOutParameter(11, Types.VARCHAR);

            statement.execute();

            String msg = statement.getString(11);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Mensaje");
            alert.setContentText(msg);
            alert.showAndWait();

            if (msg.equals("Datos del cliente actualizados correctamente.")) {
                goBack();
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/client/menuCliente.fxml"));
        Parent root = loader.load();

        MenuClienteController controller = loader.getController();
        controller.setCon(con);
        controller.setEmployeeName(employeeName);
        controller.setEmployeeLastName1(employeeLastName1);
        controller.setEmployeeLastName2(employeeLastName2);
        controller.inic();
        ap.getChildren().setAll(root);
    }

}
