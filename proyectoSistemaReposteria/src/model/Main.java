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

    // @Override
    // public void start(Stage stage) throws IOException {
    // ObservableList<Screen> screenSizes = Screen.getScreens();
    // Stage secondStage = new Stage();
    // Parent root =
    // FXMLLoader.load(getClass().getResource("/view/client/agregaCliente.fxml"));
    // Parent root2 =
    // FXMLLoader.load(getClass().getResource("/view/sales/ShopCart.fxml"));
    // Scene scene = new Scene(root);
    // // Scene scene2 = new Scene(root2);
    // stage.setTitle("1");
    // stage.setScene(scene);
    // secondStage.setTitle("2");
    // // secondStage.setScene(scene2);
    // stage.setResizable(false);
    // if (screenSizes.size() > 1) {
    // Rectangle2D bounds = screenSizes.get(1).getVisualBounds();
    // secondStage.setX(bounds.getMinX());
    // secondStage.setY(bounds.getMinY());
    // secondStage.setMaximized(true);
    // secondStage.setResizable(false);
    // stage.setMaximized(true);
    // stage.setResizable(false);
    // }else{
    // secondStage.setResizable(true);
    // }
    // stage.initStyle(StageStyle.UNDECORATED);
    // stage.setMaximized(true);

    // // secondStage.show();
    // stage.show();
    // }

    // @Override
    // public void start(Stage stage) throws IOException {

    // SQLConnection con = new SQLConnection();
    // con.setUser("sa");
    // con.setPassword("sasa");

    // FXMLLoader loader = new
    // FXMLLoader(getClass().getResource("/view/product/product.fxml"));
    // Parent root = loader.load();
    // ProductController controller = loader.getController();
    // // Manda la conexion a la base de datos
    // controller.setCon(con.getConnection());
    // controller.inic();
    // // Inicializa todos los componentes requeridos
    // Scene scene = new Scene(root);
    // stage.setTitle("Prueba");
    // stage.setScene(scene);
    // stage.setResizable(false);
    // stage.show();
    // }

    // public static void main(String[] args) {
    // launch(args);
    // }

    // @Override
    // public void start(Stage stage) throws IOException {

    // FXMLLoader loader = new
    // FXMLLoader(getClass().getResource("/view/login/login.fxml"));
    // Parent root = loader.load();

    // // Inicializa todos los componentes requeridos
    // Scene scene = new Scene(root);
    // stage.setTitle("Prueba");
    // stage.setScene(scene);
    // stage.setResizable(true);
    // stage.show();
    private static final int FRAME_WIDTH = 200;
    private static final int FRAME_HEIGHT = 400;
    private static final Color FILL_COLOR = Color.GREEN;
    private static final Duration FILL_DURATION = Duration.seconds(1);

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();

        Rectangle frame = new Rectangle(FRAME_WIDTH, FRAME_HEIGHT, Color.WHITE);
        Rectangle fill = new Rectangle(FRAME_WIDTH, 0, FILL_COLOR);

        root.getChildren().addAll(frame, fill);

        KeyValue keyValue = new KeyValue(fill.heightProperty(), FRAME_HEIGHT);
        KeyFrame keyFrame = new KeyFrame(FILL_DURATION, keyValue);

        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(1);

        fill.heightProperty().addListener((obs, oldValue, newValue) -> {
            fill.setTranslateY((FRAME_HEIGHT - newValue.doubleValue()) / 2);
        });

        Scene scene = new Scene(root, FRAME_WIDTH, FRAME_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gradual Vertical Fill Frame");
        primaryStage.show();

        timeline.play();

        timeline.setOnFinished(e -> {
            Polygon checkmark = new Polygon(
                    0, 0,
                    35, 40,
                    120, -40,
                    135, -20,
                    35, 70,
                    -15, 15
            );
            checkmark.setFill(Color.WHITE);
            checkmark.setStroke(Color.GREEN);
            checkmark.setStrokeWidth(2);
            root.getChildren().add(checkmark);

            Circle circle = new Circle(40, 10, 90);
            circle.setFill(Color.TRANSPARENT);
            circle.setStroke(Color.WHITE);
            circle.setStrokeWidth(2);

            root.getChildren().add(circle);
        });

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
