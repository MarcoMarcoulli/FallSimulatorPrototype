package ingdelsw.FallSimulatorPrototype.Math;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Parabola extends Curve {

    private double a;  // Coefficiente della parabola
    private double h;  // Ascissa del vertice (x del punto di partenza)
    private double k;  // Ordinata del vertice (y del punto di partenza)
    private double currentX;

    public Parabola(Point2D startPoint, Point2D endPoint) {
        super(startPoint, endPoint);
        calculateParabolaParameters();
        this.currentX = startPoint.getX();
    }

    // Calcolo dei parametri della parabola
    private void calculateParabolaParameters() {
        h = startPoint.getX();  // Il vertice della parabola ha ascissa pari alla x del punto di partenza
        k = startPoint.getY();  // Il vertice ha ordinata pari alla y del punto di partenza

        // Calcolo del coefficiente 'a' usando il punto di arrivo (endPoint)
        // Equazione della parabola con asse orizzontale: y = a * (x - h)^2 + k
        // Sostituendo il punto di arrivo (endPoint.getX(), endPoint.getY()) si ottiene:
        double xEnd = endPoint.getX();
        double yEnd = endPoint.getY();
        a = (yEnd - k) / Math.pow(xEnd - h, 2);
    }

    // Metodo per disegnare la parabola sul pannello
    @Override
    public void drawCurve(Pane pane) {
        int steps = 100;  // Numero di segmenti per approssimare la curva
        double startX = startPoint.getX();
        double endX = endPoint.getX();
        double stepSize = (endX - startX) / steps;

        for (int i = 0; i < steps; i++) {
            double x1 = startX + i * stepSize;
            double x2 = startX + (i + 1) * stepSize;

            // Calcola i punti corrispondenti sulla parabola
            double y1 = a * Math.pow(x1 - h, 2) + k;
            double y2 = a * Math.pow(x2 - h, 2) + k;

            // Disegna una linea tra i due punti successivi
            Line line = new Line(x1, y1, x2, y2);
            line.setStroke(Color.PURPLE);
            pane.getChildren().add(line);
        }
    }
    
    @Override
    public Point2D getNextPosition(Point2D currentPosition, double distance) {
        currentX += distance;  // Aggiorna la posizione X

        // Calcola la nuova coordinata Y
        double y = a * Math.pow(currentX - h, 2) + k;

        return new Point2D(currentX, y);
    }
}

