package controller.client;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.mainMenu.MenuController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuClienteController {

    @FXML
    private Button addButton;

    @FXML
    private Button btnGoBack;

    @FXML
    private VBox vboxClients;

    private Connection con;

    @FXML
    void goToAddCustomer(ActionEvent event) {
        try {
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/client/agregaCliente.fxml"));
            Parent root = loader.load();

            AddClientController controller = loader.getController();
            controller.setCon(con);

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        try {
            Stage stage = (Stage) btnGoBack.getScene().getWindow(); // Obtener la ventana actual
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainMenu/menu.fxml"));
            Parent root = loader.load();

            MenuController controller = loader.getController();
            controller.setCon(con);

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inic() {

        String sql = "{call getClient}";

        try (CallableStatement statement = con.prepareCall(sql)) {

            ResultSet resultados = statement.executeQuery();

            while (resultados.next()) {
                String nombre = resultados.getString(2);
                String apellido1 = resultados.getString(3);
                String apellido2 = resultados.getString(4);

                HBox panel = new HBox();
                panel.setMinWidth(540);
                panel.setMinHeight(115);
                panel.setAlignment(Pos.CENTER_LEFT);
                panel.setFillHeight(true);
                panel.setId("paneBackDeg");

                Label lblNombre = new Label(nombre + " " + apellido1 + " " + apellido2);
                lblNombre.setFont(new Font("Berlin Sans FB", 26));
                lblNombre.setGraphicTextGap(4);
                lblNombre.setMinWidth(300);

                Button btnUpdate = new Button("Actualizar");
                btnUpdate.setFont(new Font("Bodoni MT", 19));
                btnUpdate.setGraphicTextGap(4);
                btnUpdate.setAlignment(Pos.CENTER_LEFT);

                EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {

                        try {
                            Stage stage = (Stage) btnUpdate.getScene().getWindow();
                            stage.close();

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/client/actualizaCliente.fxml"));
                            Parent root = loader.load();

                            UpdateClientController controller = loader.getController();
                            controller.setCon(con);
                            controller.setNombre(nombre);
                            controller.setApellido1(apellido1);
                            controller.setApellido2(apellido2);

                            stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                };

                btnUpdate.setOnAction(eventHandler);

                panel.getChildren().add(lblNombre);
                panel.getChildren().add(btnUpdate);
                HBox.setMargin(panel.getChildren().get(0), new Insets(35));
                HBox.setMargin(panel.getChildren().get(1), new Insets(30));
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
