package ingdelsw.FallSimulatorPrototype.Math;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

public class Circumference extends Curve {

    private double radius; // Raggio della circonferenza
    private double currentT;

    public Circumference(Point2D startPoint, Point2D endPoint, double radius) {
        super(startPoint, endPoint);
        this.radius = radius;
        this.currentT = 0;
    }

    // Metodo per disegnare il ramo di circonferenza sul pannello
    @Override
    public void drawCurve(Pane pane) {
        // Calcola il centro del cerchio
        Point2D center = calculateCircleCenter(startPoint, endPoint, radius);
        
        // Calcola l'angolo iniziale e l'ampiezza dell'arco
        double startAngle = calculateStartAngle(center, startPoint);
        double endAngle = calculateStartAngle(center, endPoint);
        double arcExtent = endAngle - startAngle;

        // Crea un arco
        Arc arc = new Arc(center.getX(), center.getY(), radius, radius, startAngle, arcExtent);
        arc.setType(ArcType.OPEN);
        arc.setStroke(Color.ORANGE);
        arc.setFill(null);

        pane.getChildren().add(arc);
    }

    // Calcolo del centro del cerchio (approssimativo per due punti e un raggio)
    private Point2D calculateCircleCenter(Point2D start, Point2D end, double radius) {
        // Calcola il punto medio tra start e end
        double midX = (start.getX() + end.getX()) / 2;
        double midY = (start.getY() + end.getY()) / 2;

        // Calcola la distanza tra start e end
        double distance = start.distance(end);

        // Calcola la distanza dal punto medio al centro del cerchio
        double centerOffset = Math.sqrt(radius * radius - (distance / 2) * (distance / 2));

        // Calcola il vettore ortogonale a start-end
        double directionX = end.getY() - start.getY();
        double directionY = -(end.getX() - start.getX());

        // Normalizza il vettore
        double length = Math.sqrt(directionX * directionX + directionY * directionY);
        directionX /= length;
        directionY /= length;

        // Trova le coordinate del centro del cerchio
        double centerX = midX + directionX * centerOffset;
        double centerY = midY + directionY * centerOffset;

        return new Point2D(centerX, centerY);
    }

    // Metodo per calcolare l'angolo iniziale dell'arco
    private double calculateStartAngle(Point2D center, Point2D point) {
        double deltaX = point.getX() - center.getX();
        double deltaY = point.getY() - center.getY();
        return Math.toDegrees(Math.atan2(deltaY, deltaX));
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

