package model;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        int primaryMon;
        Screen primary = Screen.getPrimary();
        for (int i = 0; i < Screen.getScreens().size(); i++) {
            if (Screen.getScreens().get(i).equals(primary)) {
                primaryMon = i;
                System.out.println("primary: " + i);
                break;
            }
        }
        
        //Screen screen2 = Screen.getScreens().get(primaryMon);
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(new Label("primary")));
        // tenemos que establecer la posición de la ventana en el monitor principal:
        //stage2.setX(screen2.getVisualBounds().getMinX());
        //stage2.setY(screen2.getVisualBounds().getMinY());
        stage2.setFullScreen(true);
        stage2.show();
        //Label label = new Label("monitor " + i);
        //stage.setScene(new Scene(label));
        System.out.println(Screen.getScreens().size());
        //Screen screen = Screen.getScreens().get(i); // i es el id del monitor

        // establecer la posición a uno de los "slave" -monitores:
        //stage.setX(screen.getVisualBounds().getMinX());
        //stage.setY(screen.getVisualBounds().getMinY());

        // establecer las dimensiones para el tamaño de la pantalla:
        //stage.setWidth(screen.getVisualBounds().getWidth());
        //stage.setHeight(screen.getVisualBounds().getHeight());

        // muestra el escenario sin decoraciones (barra de título y bordes de ventana):
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
