package ingdelsw.FallSimulatorPrototype;

import ingdelsw.FallSimulatorPrototype.Math.CubicSpline;
import ingdelsw.FallSimulatorPrototype.Math.Curve;
import ingdelsw.FallSimulatorPrototype.Math.Cycloid;
import ingdelsw.FallSimulatorPrototype.Math.Parabola;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Interface extends Application {

    private Pane drawingPane;
    private InputManager inputManager;
    private Curve selectedCurve;
    private Slider radiusSlider;  // Slider per selezionare il raggio

    @Override
    public void start(Stage primaryStage) {
        // Layout principale
        BorderPane root = new BorderPane();

        // Pannello di controllo (a sinistra)
        VBox controlPane = new VBox(10);

        // Slider per il raggio della circonferenza, inizialmente nascosto
        radiusSlider = new Slider(10, 100, 50);  // Valore minimo, massimo e predefinito
        radiusSlider.setShowTickLabels(true);
        radiusSlider.setShowTickMarks(true);
        radiusSlider.setVisible(false);  // Nascondi lo slider inizialmente

        // Aggiungi i pulsanti per le curve
        Button btnCubicSpline = new Button("Spline Cubica");
        Button btnCycloid = new Button("Cicloide");
        Button btnParabola = new Button("Parabola");
        Button btnCircle = new Button("Circonferenza");

        controlPane.getChildren().addAll(btnCubicSpline, btnCycloid, btnParabola, btnCircle);

        // Pulsanti per avviare la simulazione e cancellare i punti
        Button btnStartSimulation = new Button("Avvia Simulazione");
        Button btnClearPoints = new Button("Cancella Punti");

        // Posiziona i pulsanti in fondo al pannello di sinistra
        VBox.setVgrow(btnStartSimulation, Priority.ALWAYS);
        VBox.setVgrow(btnClearPoints, Priority.ALWAYS);
        controlPane.getChildren().addAll(btnStartSimulation, btnClearPoints);

        root.setLeft(controlPane);

        // Pannello di disegno (a destra)
        drawingPane = new Pane();
        drawingPane.setStyle("-fx-background-color: white;");
        root.setCenter(drawingPane);

        // Inizializzazione InputManager
        inputManager = new InputManager();

        // Gestione del click sul pannello di disegno
        drawingPane.setOnMouseClicked(this::handleMouseClick);

        // Eventi per i pulsanti delle curve
        btnCubicSpline.setOnAction(e -> {
            if (inputManager.getNumberOfPoints() >= 2) {
                radiusSlider.setVisible(false);  // Nascondi lo slider quando si seleziona una curva diversa
                selectedCurve = new CubicSpline(inputManager);
                selectedCurve.drawCurve(drawingPane);
            }
        });

        btnCycloid.setOnAction(e -> {
            if (inputManager.getNumberOfPoints() >= 2) {
                radiusSlider.setVisible(false);  // Nascondi lo slider quando si seleziona una curva diversa
                Point2D startPoint = inputManager.getPoints().get(0);
                Point2D endPoint = inputManager.getPoints().get(1);
                selectedCurve = new Cycloid(startPoint, endPoint);
                selectedCurve.drawCurve(drawingPane);
            }
        });

        btnParabola.setOnAction(e -> {
            if (inputManager.getNumberOfPoints() >= 2) {
                radiusSlider.setVisible(false);  // Nascondi lo slider quando si seleziona una curva diversa
                Point2D startPoint = inputManager.getPoints().get(0);
                Point2D endPoint = inputManager.getPoints().get(1);
                selectedCurve = new Parabola(startPoint, endPoint);
                selectedCurve.drawCurve(drawingPane);
            }
        });

        btnCircle.setOnAction(e -> {
            if (inputManager.getNumberOfPoints() >= 2) {
                radiusSlider.setVisible(true);  // Mostra lo slider quando si seleziona la circonferenza
                controlPane.getChildren().add(radiusSlider);  // Aggiungi lo slider al pannello se non già aggiunto
            }
        });

        // Evento per avviare la simulazione
        btnStartSimulation.setOnAction(e -> {
            if (inputManager.getNumberOfPoints() >= 2 && selectedCurve != null) {
                Point2D startPoint = inputManager.getPoints().get(0);
                Mass fallingMass = new Mass(startPoint, 10);  // Raggio della massa impostato a 10
                SimulationManager simulation = new SimulationManager(fallingMass, selectedCurve, drawingPane);
                simulation.startSimulation();
            }
        });

        // Evento per cancellare i punti
        btnClearPoints.setOnAction(e -> clearDrawing());

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
        inputManager.addPoint(new Point2D(x, y));
        Circle point = new Circle(x, y, 5, Color.RED);  // Visualizza il cerchiolino rosso
        drawingPane.getChildren().add(point);
    }

    // Cancella tutto dal pannello di disegno
    private void clearDrawing() {
        inputManager.clearPoints();
        drawingPane.getChildren().clear();
        selectedCurve = null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}






