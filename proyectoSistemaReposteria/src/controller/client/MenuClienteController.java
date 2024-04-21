package controller.client;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MenuClienteController {

    @FXML
    private Button addButton;

    @FXML
    private Label lblNombrePersona;

    @FXML
    private Label lblNombrePersona1;

    @FXML
    private Button updateButton;

    @FXML
    private Button updateButton1;

    @FXML
    private VBox vboxClients;

    
    private Connection con;

    public void inic() {
        
        String sql = "{call getClient}";

        try (CallableStatement statement = con.prepareCall(sql)) {
            
            ResultSet resultados = statement.executeQuery();

            while (resultados.next()) {
                String nombre = resultados.getString(2);
                String apellido1 = resultados.getString(3);
                String apellido2 = resultados.getString(4);

                HBox panel = new HBox();
                panel.setMinWidth(560);
                panel.setMinHeight(115);
                panel.setAlignment(Pos.TOP_LEFT);

                Label lblNombre = new Label(nombre + " " + apellido1 + " " + apellido2);
                lblNombre.setFont(new Font("Arial", 20));
                lblNombre.setLayoutX(0);
                lblNombre.setLayoutY(0);

                panel.getChildren().add(lblNombre);
                HBox.setMargin(panel.getChildren().get(0), new Insets(35));
                panel.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                vboxClients.getChildren().add(panel);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setCon(Connection con) {
        this.con = con;
    }
}
