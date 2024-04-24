package controller.mainMenu;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.SQLConnection;

public class LoginFrameController {

    @FXML
    private Button btnLogin;

    @FXML
    void goToMenu(ActionEvent event) {
        Stage stage = (Stage) btnLogin.getScene().getWindow(); // Obtener la ventana actual
        stage.close();
        
        SQLConnection con = new SQLConnection();
        con.setUser("sa");
        con.setPassword("sasa");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainMenu/menu.fxml"));
        MenuController controller = loader.getController();
        Parent root;
        try {
            root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Manda la conexion a la base de datos
        // Inicializa todos los componentes requeridos
    }

}
