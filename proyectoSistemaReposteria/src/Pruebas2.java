import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Pruebas2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear un panel rectangular inicialmente blanco
        Rectangle panel = new Rectangle(200, 100, Color.WHITE);

        // Animación para cambiar gradualmente el color del panel a verde
        FillTransition fillTransition = new FillTransition(Duration.seconds(3), panel, Color.WHITE, Color.GREEN);
        fillTransition.setOnFinished(event -> {
            // Una vez que el panel esté verde, crear un círculo con una palomita dentro
            Circle circle = new Circle(100, 50, 50, Color.GREEN);
            // Aquí podrías agregar la imagen de la palomita si tienes una
            // Circle circle = new Circle(100, 50, 50, new ImagePattern(new Image("ruta_de_la_imagen")));

            // Agregar el círculo al mismo contenedor que el panel rectangular
            ((Pane) panel.getParent()).getChildren().add(circle);
        });

        // Ejecutar la animación de cambio de color
        fillTransition.play();

        // Crear el contenedor principal y agregar el panel rectangular
        Pane root = new Pane(panel);
        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.setTitle("Animación JavaFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}