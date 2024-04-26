package controller.client;

import java.io.IOException;
import java.sql.Connection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateClientController {

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

    private String nombre;

    private String apellido1;

    private String apellido2;

    private Connection con;

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

    @FXML
    void update(ActionEvent event) {
        System.out.println(nombre + apellido1 + apellido2);
    }

}
