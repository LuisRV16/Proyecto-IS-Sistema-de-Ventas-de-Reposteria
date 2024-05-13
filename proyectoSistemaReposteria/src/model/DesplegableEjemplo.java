package model;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DesplegableEjemplo extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear el VBox principal
        VBox vbox = new VBox();
        
        // Crear un TitledPane para el cliente 1
        TitledPane cliente1 = new TitledPane();
        cliente1.setText("Cliente 1 - Juan Pérez");
        HBox infoCompleta = new HBox();
        VBox cliente1Info = new VBox();

        Button boton = new Button("Boton de prueba");

        cliente1Info.getChildren().addAll(
                new Label("ID: 001"),
                new Label("Teléfono: 123-456-7890"),
                new Label("Correo electrónico: juan@example.com")
        );

        ScrollPane sp = new ScrollPane();

        VBox masInfo = new VBox();
        masInfo.getChildren().addAll(
                new Label("ID: 001"),
                new Label("Teléfono: 123-456-7890"),
                new Label("Correo electrónico: juan@example.com"),
                new Label("ID: 001"),
                new Label("Teléfono: 123-456-7890"),
                new Label("Correo electrónico: juan@example.com"),
                new Label("ID: 001"),
                new Label("Teléfono: 123-456-7890"),
                new Label("Correo electrónico: juan@example.com")
        );
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                if (masInfo.isVisible()) {
                    masInfo.setVisible(false);
                } else {
                    masInfo.setVisible(true);
                }
            }

        };
        boton.setOnAction(event);

        sp.setMaxHeight(100);
        sp.setMinWidth(300);

        sp.setContent(masInfo);

        cliente1Info.getChildren().add(boton);

        infoCompleta.getChildren().addAll(cliente1Info, sp);
        cliente1.setContent(infoCompleta);
        
        // Crear un TitledPane para el cliente 2
        TitledPane cliente2 = new TitledPane();
        cliente2.setText("Cliente 2 - María García");
        VBox cliente2Info = new VBox();
        cliente2Info.getChildren().addAll(
                new Label("ID: 002"),
                new Label("Teléfono: 987-654-3210"),
                new Label("Correo electrónico: maria@example.com")
        );
        cliente2.setContent(cliente2Info);
        
        // Agregar los TitledPane al VBox principal
        vbox.getChildren().addAll(cliente1, cliente2);
        
        // Crear la escena y mostrarla
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setTitle("Componente Desplegable");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}