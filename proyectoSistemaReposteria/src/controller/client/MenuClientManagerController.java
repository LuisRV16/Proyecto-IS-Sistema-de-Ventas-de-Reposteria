package controller.client;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MenuClientManagerController {

    @FXML
    private BorderPane ap;

    @FXML
    private VBox vboxClients;

    private Connection con;

    public void setCon(Connection con) {
        this.con = con;
    }

    public void inic() {

        String sql = "{call getClient}";

        try (CallableStatement statement = con.prepareCall(sql)) {

            ResultSet resultados = statement.executeQuery();

            while (resultados.next()) {
                String rfc = resultados.getString(1);
                String nombre = resultados.getString(2);
                String apellido1 = resultados.getString(3);
                String apellido2 = (resultados.getString(4) != null ? resultados.getString(4) : "" );
                String telefono = resultados.getString(5);
                String correo = resultados.getString(6);
                String calle = resultados.getString(7);
                String numeroInterior = resultados.getString(8);
                String numeroExterior = resultados.getString(9);
                String cp = resultados.getString(10);
                String colonia = resultados.getString(11);
                String ciudad = resultados.getString(12);
                String estado = resultados.getString(13);

                if (!nombre.equals("Generico")) {
                    TitledPane panel  = new TitledPane();
                    panel.setMinWidth(540);
                    panel.setAlignment(Pos.CENTER_LEFT);
                    panel.setExpanded(false);
                    // panel.setId("paneBackDeg");

                    panel.setText(nombre + " " + apellido1 + " " + apellido2);
                    VBox dataClient = new VBox();
                    dataClient.getChildren().addAll(
                        new Label("RFC: " + rfc),
                        new Label("Telefóno: " + telefono),
                        new Label("Correo: " + correo),
                        new Label("Calle: " + calle),
                        new Label("No. Interior: " + numeroInterior),
                        new Label("No. Exterior: " + numeroExterior),
                        new Label("Código Postal: " + cp),
                        new Label("Colonia: " + colonia),
                        new Label("Ciudad: " + ciudad),
                        new Label("Estado: " + estado)
                    );

                    panel.setContent(dataClient);
                    vboxClients.getChildren().add(panel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
