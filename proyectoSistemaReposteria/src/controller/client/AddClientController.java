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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Estados;

public class AddClientController {
    @FXML
    private BorderPane ap;

    @FXML
    private Button cancelButton;

    @FXML
    private Label lblApellido1;

    @FXML
    private Label lblApellido2;

    @FXML
    private Label lblRFC;

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
    private TextField txtColonia;

    @FXML
    private TextField txtCorreo;

    @FXML
    private ComboBox<String> comboEstado;

    @FXML
    private ComboBox<String> comboCiudades;

    @FXML
    private TextField txtExterior;

    @FXML
    private TextField txtInterior;

    @FXML
    private TextField txtRFC;

    @FXML
    private TextField txtMaterno;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPaterno;

    @FXML
    private TextField txtTelefono;

    private final Estados[] enumEstados = Estados.values();

    private String[] estadosMexico = new String[32];

    private String[] municipiosAguascalientes = enumEstados[0].getMunicipios();
    private String[] municipiosBajaCalifornia = enumEstados[1].getMunicipios();
    private String[] municipiosBajaCaliforniaSur = enumEstados[2].getMunicipios();
    private String[] municipiosCampeche = enumEstados[3].getMunicipios();
    private String[] municipiosCDMX = enumEstados[4].getMunicipios();
    private String[] municipiosChiapas = enumEstados[5].getMunicipios();
    private String[] municipiosChihuahua = enumEstados[6].getMunicipios();
    private String[] municipiosCoahuila = enumEstados[7].getMunicipios();
    private String[] municipiosColima = enumEstados[8].getMunicipios();
    private String[] municipiosDurango = enumEstados[9].getMunicipios();
    private String[] municipiosEdoMexico = enumEstados[10].getMunicipios();
    private String[] municipiosGuanajuato = enumEstados[11].getMunicipios();
    private String[] municipiosGuerrero = enumEstados[12].getMunicipios();
    private String[] municipiosHidalgo = enumEstados[13].getMunicipios();
    private String[] municipiosJalisco = enumEstados[14].getMunicipios();
    private String[] municipiosMichoacan = enumEstados[15].getMunicipios();
    private String[] municipiosMorelos = enumEstados[16].getMunicipios();
    private String[] municipiosNayarit = enumEstados[17].getMunicipios();
    private String[] municipiosNuevoLeon = enumEstados[18].getMunicipios();
    private String[] municipiosOaxaca = enumEstados[19].getMunicipios();
    private String[] municipiosPuebla = enumEstados[20].getMunicipios();
    private String[] municipiosQueretaro = enumEstados[21].getMunicipios();
    private String[] municipiosQuintanaRoo = enumEstados[22].getMunicipios();
    private String[] municipiosSanLuisPotosi = enumEstados[23].getMunicipios();
    private String[] municipiosSinaloa = enumEstados[24].getMunicipios();
    private String[] municipiosSonora = enumEstados[25].getMunicipios();
    private String[] municipiosTabasco = enumEstados[26].getMunicipios();
    private String[] municipiosTamaulipas = enumEstados[27].getMunicipios();
    private String[] municipiosTlaxcala = enumEstados[28].getMunicipios();
    private String[] municipiosVeracruz = enumEstados[29].getMunicipios();
    private String[] municipiosYucatan = enumEstados[30].getMunicipios();
    private String[] municipiosZacatecas = enumEstados[31].getMunicipios();
    private Connection con;

    private String employeeName;

    private String employeeLastName1;

    private String employeeLastName2;

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

        for (int i = 0; i < enumEstados.length; i++)
            estadosMexico[i] = enumEstados[i].getNombre();

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

        comboEstado.getItems().setAll(estadosMexico); // Asignacion de estados al comboBox de estados

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
    }

    @FXML
    void enableCities(ActionEvent event) {
        String estado = comboEstado.getSelectionModel().getSelectedItem();
        String[] municipios = null;
        switch (estado) {
            case "Aguascalientes" -> municipios = municipiosAguascalientes;
            case "Baja California" -> municipios = municipiosBajaCalifornia;
            case "Baja California Sur" -> municipios = municipiosBajaCaliforniaSur;
            case "Campeche" -> municipios = municipiosCampeche;
            case "Ciudad de México" -> municipios = municipiosCDMX;
            case "Chiapas" -> municipios = municipiosChiapas;
            case "Chihuahua" -> municipios = municipiosChihuahua;
            case "Coahuila" -> municipios = municipiosCoahuila;
            case "Colima" -> municipios = municipiosColima;
            case "Durango" -> municipios = municipiosDurango;
            case "Estado de México" -> municipios = municipiosEdoMexico;
            case "Guanajuato" -> municipios = municipiosGuanajuato;
            case "Guerrero" -> municipios = municipiosGuerrero;
            case "Hidalgo" -> municipios = municipiosHidalgo;
            case "Jalisco" -> municipios = municipiosJalisco;
            case "Michoacán" -> municipios = municipiosMichoacan;
            case "Morelos" -> municipios = municipiosMorelos;
            case "Nayarit" -> municipios = municipiosNayarit;
            case "Nuevo León" -> municipios = municipiosNuevoLeon;
            case "Oaxaca" -> municipios = municipiosOaxaca;
            case "Puebla" -> municipios = municipiosPuebla;
            case "Querétaro" -> municipios = municipiosQueretaro;
            case "Quintana Roo" -> municipios = municipiosQuintanaRoo;
            case "San Luis Potosí" -> municipios = municipiosSanLuisPotosi;
            case "Sinaloa" -> municipios = municipiosSinaloa;
            case "Sonora" -> municipios = municipiosSonora;
            case "Tabasco" -> municipios = municipiosTabasco;
            case "Tamaulipas" -> municipios = municipiosTamaulipas;
            case "Tlaxcala" -> municipios = municipiosTlaxcala;
            case "Veracruz" -> municipios = municipiosVeracruz;
            case "Yucatán" -> municipios = municipiosYucatan;
            case "Zacatecas" -> municipios = municipiosZacatecas;
        }
        comboCiudades.getItems().setAll(municipios);
        comboCiudades.setDisable(false);
    }

    @FXML
    void addClient(ActionEvent event) {
        String nombre = txtNombre.getText();
        String apellido1 = txtPaterno.getText();
        String apellido2 = txtMaterno.getText();
        String rfc = txtRFC.getText();
        String telefono = txtTelefono.getText();
        String email = txtCorreo.getText();
        String calle = txtCalle.getText();
        String numInterior = txtInterior.getText();
        String numExterior = txtExterior.getText();
        String codPost = txtCP.getText();
        String colonia = txtColonia.getText();
        String ciudad = comboCiudades.getSelectionModel().getSelectedItem();
        if (ciudad == null) ciudad = "";
        String estado = comboEstado.getSelectionModel().getSelectedItem();
        if (estado == null) estado = "";

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");

        String sql = "{call addClient(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement statement = con.prepareCall(sql)) {
            statement.setString(1, nombre);
            statement.setString(2, apellido1);
            statement.setString(3, apellido2);
            statement.setString(4, rfc);
            statement.setString(5, telefono);
            statement.setString(6, email);
            statement.setString(7, calle);
            statement.setString(8, numInterior);
            statement.setString(9, numExterior);
            statement.setString(10, codPost);
            statement.setString(11, colonia);
            statement.setString(12, ciudad);
            statement.setString(13, estado);
            statement.registerOutParameter(14, Types.VARCHAR);

            statement.execute();

            String msg = statement.getString(14);
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Mensaje");
            System.out.println(msg);
            if (!msg.equals("Cliente insertado correctamente.")) {
                clean();
            }
            alert.setContentText(msg);
            alert.showAndWait();

            if (msg.equals("Cliente insertado correctamente.")) {
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
    void goBack(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/client/menuCliente.fxml"));
        Parent root = loader.load();

        MenuClienteController controller = loader.getController();
        controller.setCon(con);
        controller.inic();
        controller.setEmployeeName(employeeName);
        controller.setEmployeeLastName1(employeeLastName1);
        controller.setEmployeeLastName2(employeeLastName2);
        ap.getChildren().setAll(root);
    }

    private void clean() {
        // txtNombre.setText("");
        // txtPaterno.setText("");
        // txtMaterno.setText("");
        // txtRFC.setText("");
        // txtTelefono.setText("");
        // txtCorreo.setText("");
        // txtCalle.setText("");
        // txtInterior.setText("");
        // txtExterior.setText("");
        // txtCP.setText("");
        // txtColonia.setText("");
        // txtCiudad.setText("");
    }

}
