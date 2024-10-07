package ingdelsw.FallSimulatorPrototype;

import ingdelsw.FallSimulatorPrototype.Math.CubicSpline;
import ingdelsw.FallSimulatorPrototype.Math.Curve;
import ingdelsw.FallSimulatorPrototype.Math.Cycloid;
import ingdelsw.FallSimulatorPrototype.Math.Parabola;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Interface extends Application {

    private Pane drawingPane;
    private InputManager inputManager;
    private Curve selectedCurve;  // Curva selezionata dall'utente

    @Override
    public void start(Stage primaryStage) {
        // Layout principale
        BorderPane root = new BorderPane();

        // Pannello di controllo (a sinistra)
        VBox controlPane = new VBox(10);

        // ComboBox per selezionare la curva
        ComboBox<String> curveSelection = new ComboBox<>();
        curveSelection.getItems().addAll("Spline Cubica", "Cicloide", "Parabola");
        curveSelection.setValue("Spline Cubica");  // Default: Spline Cubica
        controlPane.getChildren().add(curveSelection);

        Button btnClear = new Button("Cancella Tutto");
        Button btnStartSimulation = new Button("Avvia Simulazione");
        controlPane.getChildren().addAll(btnClear, btnStartSimulation);
        root.setLeft(controlPane);

        // Pannello di disegno (a destra)
        drawingPane = new Pane();
        drawingPane.setStyle("-fx-background-color: white;");
        root.setCenter(drawingPane);

        // Inizializzazione InputManager
        inputManager = new InputManager();

        // Gestione del click sul pannello di disegno
        drawingPane.setOnMouseClicked(this::handleMouseClick);

        // Eventi per i pulsanti
        btnClear.setOnAction(e -> clearDrawing());

        // Listener per selezionare la curva
        curveSelection.setOnAction(e -> {
            String selected = curveSelection.getValue();
            if (selected.equals("Spline Cubica")) {
                if (inputManager.getPointCount() >= 2) {
                    selectedCurve = new CubicSpline(inputManager);
                    selectedCurve.drawCurve(drawingPane);
                }
            } else if (selected.equals("Cicloide")) {
                if (inputManager.getPointCount() >= 2) {
                    Point2D startPoint = inputManager.getPoints().get(0);
                    Point2D endPoint = inputManager.getPoints().get(1);
                    selectedCurve = new Cycloid(startPoint, endPoint);
                    selectedCurve.drawCurve(drawingPane);
                }
            } else if (selected.equals("Parabola")) {
                if (inputManager.getPointCount() >= 2) {
                    Point2D startPoint = inputManager.getPoints().get(0);
                    Point2D endPoint = inputManager.getPoints().get(1);
                    selectedCurve = new Parabola(startPoint, endPoint);
                    selectedCurve.drawCurve(drawingPane);
                }
            }
        });

        // Evento per avviare la simulazione
        btnStartSimulation.setOnAction(e -> {
            if (inputManager.getPointCount() >= 2 && selectedCurve != null) {  // Controlla che ci siano punti e una curva selezionata
                Point2D startPoint = inputManager.getPoints().get(0);
                
                // Crea la massa
                Mass fallingMass = new Mass(startPoint, 10);  // Raggio della massa impostato a 10

                // Crea il SimulationManager e avvia la simulazione
                SimulationManager simulation = new SimulationManager(fallingMass, selectedCurve, drawingPane);
                simulation.startSimulation();
            }
        });

        // Configura la scena
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Simulatore di Curve");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

 // Gestione dei click per selezionare i punti
    private void handleMouseClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        
        // Aggiungi il punto al gestore dell'input
        inputManager.addPoint(new Point2D(x, y));
        
        // Disegna un cerchiolino rosso sul pannello
        Circle point = new Circle(x, y, 5, Color.RED);  // Cerchiolino di raggio 5 con colore rosso
        drawingPane.getChildren().add(point);
    }

    // Cancella tutto dal pannello di disegno
    private void clearDrawing() {
        inputManager.clearPoints();
        drawingPane.getChildren().clear();
        selectedCurve = null;  // Resetta la curva selezionata
    }

    public static void main(String[] args) {
        launch(args);
    }
}



