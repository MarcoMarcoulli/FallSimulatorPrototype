package ingdelsw.FallSimulatorPrototype.Math;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Cycloid extends Curve {

    private double radius; // Raggio del cerchio generatore della cicloide
    private double cycles; // Numero di cicli della cicloide
    private double currentT;

    public Cycloid(Point2D startPoint, Point2D endPoint) {
        super(startPoint, endPoint);
        calculateCycloidParameters();
        this.currentT = 0;
    }

    // Calcolo del raggio e del numero di cicli della cicloide
    private void calculateCycloidParameters() {
        // Calcola la distanza orizzontale (deltaX) tra il punto di partenza e il punto di arrivo
        double deltaX = endPoint.getX() - startPoint.getX();

        // Calcola la distanza verticale (deltaY) tra il punto di partenza e il punto di arrivo
        double deltaY = startPoint.getY() - endPoint.getY();

        // Il raggio della cicloide è proporzionale all'altezza del moto
        radius = deltaY / (2 * Math.PI); // Relazione tra l'altezza e il raggio per una cicloide

        // Numero di cicli: il numero di giri del cerchio generatore che copre la distanza orizzontale
        cycles = deltaX / (2 * Math.PI * radius);
    }

    // Metodo per disegnare la cicloide sul pannello
    @Override
    public void drawCurve(Pane pane) {
        int steps = 100; // Numero di punti per approssimare la curva
        double deltaT = (2 * Math.PI * cycles) / steps; // Passo di incremento per la cicloide

        // Disegna la cicloide in piccoli segmenti
        for (int i = 0; i < steps; i++) {
            // Parametro t varia tra 0 e 2π * numero di cicli
            double t1 = i * deltaT;
            double t2 = (i + 1) * deltaT;

            // Calcolo delle coordinate della cicloide in base al parametro t
            Point2D point1 = calculateCycloidPoint(t1);
            Point2D point2 = calculateCycloidPoint(t2);

            // Disegna una linea tra i due punti successivi
            Line line = new Line(point1.getX(), point1.getY(), point2.getX(), point2.getY());
            line.setStroke(Color.GREEN); // Colore della cicloide
            pane.getChildren().add(line);
        }
    }

    // Metodo per calcolare il punto sulla cicloide dato il parametro t
    private Point2D calculateCycloidPoint(double t) {
        // Coordinate parametriche della cicloide
        double x = radius * (t - Math.sin(t));
        double y = radius * (1 - Math.cos(t));

        // Trasla la cicloide per farla partire dal punto di partenza
        double translatedX = startPoint.getX() + x;
        double translatedY = startPoint.getY() - y; // Si sottrae perché Y cresce verso il basso

        return new Point2D(translatedX, translatedY);
    }
    
    @Override
    public Point2D getNextPosition(Point2D currentPosition, double distance) {
        currentT += 0.05;  // Incremento di t

        // Calcola la prossima posizione lungo la cicloide
        double x = radius * (currentT - Math.sin(currentT));
        double y = radius * (1 - Math.cos(currentT));

        // Traslazione per partire dal punto iniziale
        double translatedX = startPoint.getX() + x;
        double translatedY = startPoint.getY() - y;

        return new Point2D(translatedX, translatedY);
    }
}

