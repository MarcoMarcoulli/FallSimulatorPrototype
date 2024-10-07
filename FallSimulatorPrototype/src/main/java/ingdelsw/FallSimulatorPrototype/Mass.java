package ingdelsw.FallSimulatorPrototype;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Mass {

    private Circle mass;  // Rappresentazione grafica della massa
    private Point2D position;  // Posizione corrente della massa
    private double velocity;  // Velocità della massa
    private double acceleration;  // Accelerazione della massa (es: gravità)

    // Costruttore
    public Mass(Point2D startPosition, double radius) {
        this.position = startPosition;
        this.mass = new Circle(startPosition.getX(), startPosition.getY(), radius, Color.RED);  // Cerchio che rappresenta la massa
        this.velocity = 0;
        this.acceleration = 9.81;  // Accelerazione gravitazionale (m/s^2)
    }

    // Getter per la posizione
    public Point2D getPosition() {
        return position;
    }

    // Setter per aggiornare la posizione
    public void setPosition(Point2D newPosition) {
        this.position = newPosition;
        mass.setCenterX(newPosition.getX());
        mass.setCenterY(newPosition.getY());
    }

    // Getter per la velocità
    public double getVelocity() {
        return velocity;
    }

    // Setter per aggiornare la velocità
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    // Getter per la rappresentazione grafica della massa (cerchio)
    public Circle getMassGraphic() {
        return mass;
    }

    // Getter per l'accelerazione
    public double getAcceleration() {
        return acceleration;
    }
}

