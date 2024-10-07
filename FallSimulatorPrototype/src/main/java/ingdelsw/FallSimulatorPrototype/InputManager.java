package ingdelsw.FallSimulatorPrototype;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class InputManager {
    private final List<Double> xPoints; // Lista per memorizzare le coordinate x dei punti inseriti
    private final List<Double> yPoints; // Lista per memorizzare le coordinate y dei punti inseriti
    private final Pane drawingPane; // Pannello dove si disegnano i punti e le curve
    private final Button resetButton; // Pulsante per resettare i punti
    private final Button drawButton; // Pulsante per disegnare la spline cubica
    private Curve curveDrawer; // Oggetto Curves per disegnare le curve

    public InputManager(Pane drawingPane, Button resetButton, Button drawButton) {
        this.drawingPane = drawingPane;
        this.resetButton = resetButton;
        this.drawButton = drawButton;
        this.xPoints = new ArrayList<>();
        this.yPoints = new ArrayList<>();
        this.curveDrawer = new CubicSpline();

        setupListeners(); // Imposta gli ascoltatori di eventi per i pulsanti e il pannello di disegno
    }

    // Imposta gli ascoltatori di eventi
    private void setupListeners() {
        // Gestisci il clic del mouse per aggiungere punti
        drawingPane.addEventFilter(MouseEvent.MOUSE_CLICKED, this::handleMouseClick);

        // Pulsante per resettare i punti
        resetButton.setOnAction(event -> resetPoints());

        // Pulsante per disegnare la spline cubica
        drawButton.setOnAction(event -> drawCubicSpline());

        // Aggiungi ascoltatore per il tasto "Enter"
        drawingPane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                drawCubicSpline(); // Disegna la curva quando si preme "Enter"
                event.consume(); // Evita che l'evento sia propagato ad altri ascoltatori
            }
        });
    }

    // Gestisce il clic del mouse sul pannello di disegno
    private void handleMouseClick(MouseEvent event) {
        double x = event.getX(); // Ottiene la coordinata x del clic del mouse
        double y = event.getY(); // Ottiene la coordinata y del clic del mouse

        // Aggiungi il punto cliccato alla lista dei punti
        if (isValidPoint(x, y)) {
            xPoints.add(x);
            yPoints.add(y);

            // Disegna un cerchio nel punto cliccato
            Circle point = new Circle(x, y, 4, Color.RED); // Crea un cerchio di raggio 4 con colore rosso
            drawingPane.getChildren().add(point); // Aggiunge il cerchio al pannello di disegno
        }
    }

    // Metodo per resettare i punti
    private void resetPoints() {
        xPoints.clear(); // Rimuove tutti i punti dalla lista delle coordinate x
        yPoints.clear(); // Rimuove tutti i punti dalla lista delle coordinate y
        drawingPane.getChildren().clear(); // Rimuove tutti gli elementi dal pane (punti e curve)
    }

    // Metodo per disegnare la spline cubica
    private void drawCubicSpline() {
        if (xPoints.size() >= 2 && yPoints.size() >= 2) { // Verifica che ci siano almeno 2 punti
            curveDrawer.drawCubicSpline(drawingPane, xPoints, yPoints); // Disegna la spline cubica
        } else {
            System.out.println("Inserisci almeno 2 punti per disegnare una curva.");
        }
    }

    // Metodo per verificare se un punto è valido in base ai criteri specificati
    private boolean isValidPoint(double x, double y) {
        if (xPoints.isEmpty()) {
            return true; // Primo punto è sempre valido
        } else if (xPoints.size() == 1) {
            // Secondo punto: y deve essere maggiore del primo punto
            if (y > yPoints.get(0)) {
                return true;
            } else {
                showError("Il secondo punto deve avere la coordinata y maggiore del primo.");
                return false;
            }
        } else {
            // Punti successivi: x deve essere compreso tra il primo e il secondo punto
            double xMin = Math.min(xPoints.get(0), xPoints.get(1));
            double xMax = Math.max(xPoints.get(0), xPoints.get(1));
            if (x >= xMin && x <= xMax) {
                return true;
            } else {
                showError("I punti successivi devono avere coordinata x compresa tra il primo e il secondo punto.");
                return false;
            }
        }
    }

    // Metodo per mostrare un messaggio di errore (puoi modificarlo per visualizzare graficamente il messaggio)
    private void showError(String message) {
        System.out.println(message); // Semplice output a console (da sostituire con un messaggio grafico se necessario)
    }
}
