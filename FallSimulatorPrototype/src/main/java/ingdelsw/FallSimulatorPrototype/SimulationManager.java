package ingdelsw.FallSimulatorPrototype;

import ingdelsw.FallSimulatorPrototype.Math.Curve;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public class SimulationManager {

    private Mass mass;  // Oggetto in caduta
    private Curve curve;  // Curva lungo cui la massa cade
    private Pane simulationPane;  // Pannello di simulazione

    private AnimationTimer timer;  // Timer per animare la simulazione

    public SimulationManager(Mass mass, Curve curve, Pane simulationPane) {
        this.mass = mass;
        this.curve = curve;
        this.simulationPane = simulationPane;

        // Aggiungi la massa grafica al pannello
        this.simulationPane.getChildren().add(mass.getMassGraphic());
    }

    // Metodo per avviare la simulazione
    public void startSimulation() {
        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate > 0) {
                    double elapsedTime = (now - lastUpdate) / 1_000_000_000.0;  // Tempo trascorso in secondi
                    updatePosition(elapsedTime);
                }
                lastUpdate = now;
            }
        };
        timer.start();
    }

    // Metodo per aggiornare la posizione della massa
    private void updatePosition(double elapsedTime) {
        // Aggiorna la velocità in base all'accelerazione
        double newVelocity = mass.getVelocity() + mass.getAcceleration() * elapsedTime;
        mass.setVelocity(newVelocity);

        // Calcola la distanza percorsa lungo la curva
        double distance = newVelocity * elapsedTime;

        // Ottieni la nuova posizione lungo la curva
        Point2D newPosition = curve.getNextPosition(mass.getPosition(), distance);

        // Aggiorna la posizione della massa
        mass.setPosition(newPosition);

        // Ferma la simulazione se la massa raggiunge il punto finale della curva
        if (newPosition.equals(curve.getEndPoint())) {
            stopSimulation();
        }
    }

    // Metodo per fermare la simulazione
    public void stopSimulation() {
        if (timer != null) {
            timer.stop();
        }
    }
}

