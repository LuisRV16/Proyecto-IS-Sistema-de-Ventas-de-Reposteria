package controller.sales;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class CardPaymentController {

    @FXML
    private Pane pane;

    public void inic() {
        // Crear una imagen de la terminal de tarjeta y la tarjeta de banco
        Image terminalImage = new Image("/images/terminal.png");
        ImageView terminalImageView = new ImageView(terminalImage);
        terminalImageView.setFitWidth(150); // Ajustar el tamaño de la terminal
        terminalImageView.setPreserveRatio(true);

        Image tarjetaImage = new Image("/images/tarjeta.png");
        ImageView tarjetaImageView = new ImageView(tarjetaImage);
        tarjetaImageView.setFitWidth(100); // Ajustar el tamaño de la tarjeta
        tarjetaImageView.setPreserveRatio(true);

        terminalImageView.setLayoutX(425);
        terminalImageView.setLayoutY(200);
        // Definir la posición inicial de la tarjeta (abajo de la terminal)
        tarjetaImageView.setLayoutX(450);
        tarjetaImageView.setLayoutY(375);

        Label label = new Label("Ingrese su Tarjeta");
        label.setFont(new Font("System", 20));
        label.setLayoutX(420);
        label.setLayoutY(475);

        // Crear una animación para mover la tarjeta hacia arriba y abajo
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), tarjetaImageView);
        translateTransition.setByY(-25); // Mover hacia arriba 100 unidades
        translateTransition.setAutoReverse(true); // Habilitar el movimiento de regreso
        translateTransition.setCycleCount(Animation.INDEFINITE); // Repetir indefinidamente

        // Comenzar la animación
        translateTransition.play();

        pane.getChildren().setAll(terminalImageView, tarjetaImageView, label);
    }

}
