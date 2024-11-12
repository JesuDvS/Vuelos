package com.example.Vuelos;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

public class FlightApp extends Application {
    private TextArea flightInfoArea;
    private TextField airlineCodeField;
    private FlightService flightService;

    @Override
    public void init() {
        ConfigurableApplicationContext springContext = VuelosApplication.getSpringContext();
        flightService = springContext.getBean(FlightService.class);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Control de Programación de Vuelos + API de aviationstack");

        airlineCodeField = new TextField();
        airlineCodeField.setPromptText("Ingrese el código de la aerolínea");
        airlineCodeField.setMaxWidth(300);


        Button fetchScheduleButton = new Button("Obtener Programación de Vuelos");

        flightInfoArea = new TextArea();
        flightInfoArea.setEditable(false);
        flightInfoArea.setWrapText(true);
        flightInfoArea.setPrefRowCount(20);

        fetchScheduleButton.setOnAction(event -> {
            fetchScheduleButton.setDisable(true);
            flightInfoArea.appendText("Consultando vuelos...\n");
            fetchFlightSchedule();
            fetchScheduleButton.setDisable(false);
        });

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(
                new Label("Código de Aerolínea:"),
                airlineCodeField,
                fetchScheduleButton,
                flightInfoArea
        );

        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fetchFlightSchedule() {
        String airlineCode = airlineCodeField.getText().trim().toUpperCase();
        if (airlineCode.isEmpty()) {
            flightInfoArea.setText("Por favor ingrese un código de aerolínea.\n");
            return;
        }

        new Thread(() -> {
            try {
                String schedule = flightService.getFlightSchedule(airlineCode);

                // Actualizar la UI en el hilo principal
                Platform.runLater(() -> {
                    if (schedule.startsWith("Error")) {
                        flightInfoArea.appendText("Error en la consulta\n");
                    } else {
                        flightInfoArea.appendText("Datos obtenidos correctamente\n");
                    }
                    flightInfoArea.appendText(schedule + "\n");
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    flightInfoArea.appendText("Error: " + e.getMessage() + "\n");
                });
            }
        }).start();
    }

    @Override
    public void stop() {
        Platform.exit();
        VuelosApplication.getSpringContext().close();
    }
}