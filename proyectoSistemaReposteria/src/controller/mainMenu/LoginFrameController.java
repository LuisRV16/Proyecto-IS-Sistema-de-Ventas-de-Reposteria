package controller.mainMenu;

import java.io.IOException;
import java.sql.Connection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.SQLConnection;

public class LoginFrameController {

    @FXML
    private Button btnLogin;

    @FXML
    private CheckBox ckBoxShowPassword;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField lblUser;

    @FXML
    void goToMenu(ActionEvent event) {

        String user = lblUser.getText();
        String password = pwdPassword.isVisible() ? pwdPassword.getText() : txtPassword.getText();

        SQLConnection sqlCon = new SQLConnection();
        sqlCon.setUser(user);
        sqlCon.setPassword(password);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        
        if (user.isEmpty()) {
            alert.setContentText("Ingrese un usuario");
            alert.showAndWait();
            return;
        }

        if (password.isEmpty()) {
            alert.setContentText("Ingrese la contraseña");
            alert.showAndWait();
            return;
        }

        Connection con = sqlCon.getConnection();

        if (con == null) {
            alert.setContentText("Usuario y/o contraseña incorrectos");
            alert.showAndWait();
            return;
        }
        
        try {

            Stage stage = (Stage) btnLogin.getScene().getWindow(); // Obtener la ventana actual
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

    @FXML
    void showPassword(ActionEvent event) {
        if (pwdPassword.isVisible()) {
            pwdPassword.setVisible(false);
            txtPassword.setText(pwdPassword.getText());
            txtPassword.setVisible(true);
        } else {
            txtPassword.setVisible(false);
            pwdPassword.setText(txtPassword.getText());
            pwdPassword.setVisible(true);
        }
    }

}
