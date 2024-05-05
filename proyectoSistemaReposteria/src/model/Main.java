package model;

import java.io.IOException;

import controller.mainMenu.LoginFrameController;
import controller.product.ProductController;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new
        FXMLLoader(getClass().getResource("/view/login/login.fxml"));
        Parent root = loader.load();

        // Inicializa todos los componentes requeridos
        Scene scene = new Scene(root);
        stage.setTitle("Prueba");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
    }

    // @Override
    // public void start(Stage primaryStage) {
    // // Crear una imagen de la terminal de tarjeta y la tarjeta de banco
    // Image terminalImage = new Image("/images/terminal.png");
    // ImageView terminalImageView = new ImageView(terminalImage);
    // terminalImageView.setFitWidth(150); // Ajustar el tamaño de la terminal
    // terminalImageView.setPreserveRatio(true);

    // Image tarjetaImage = new Image("/images/tarjeta.png");
    // ImageView tarjetaImageView = new ImageView(tarjetaImage);
    // tarjetaImageView.setFitWidth(100); // Ajustar el tamaño de la tarjeta
    // tarjetaImageView.setPreserveRatio(true);

    // terminalImageView.setLayoutX(75);
    // terminalImageView.setLayoutY(50);
    // // Definir la posición inicial de la tarjeta (abajo de la terminal)
    // tarjetaImageView.setLayoutX(100);
    // tarjetaImageView.setLayoutY(225);

    // Label label = new Label("Ingrese su Tarjeta");
    // label.setFont(new Font("System", 20));
    // label.setLayoutX(75);
    // label.setLayoutY(325);

    // // Crear una animación para mover la tarjeta hacia arriba y abajo
    // TranslateTransition translateTransition = new
    // TranslateTransition(Duration.seconds(1), tarjetaImageView);
    // translateTransition.setByY(-25); // Mover hacia arriba 100 unidades
    // translateTransition.setAutoReverse(true); // Habilitar el movimiento de
    // regreso
    // translateTransition.setCycleCount(Animation.INDEFINITE); // Repetir
    // indefinidamente

    // // Comenzar la animación
    // translateTransition.play();

    // // Crear el contenedor principal y agregar las imágenes
    // Pane root = new Pane(terminalImageView, tarjetaImageView, label);
    // primaryStage.setScene(new Scene(root, 400, 400));
    // primaryStage.setTitle("Animación de Tarjeta Insertada");
    // primaryStage.show();
    // }

    public static void main(String[] args) {
        launch(args);
    }

}
