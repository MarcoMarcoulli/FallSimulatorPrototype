package ingdelsw.FallSimulatorPrototype.Math;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public abstract class Curve {
    protected Point2D startPoint;
    protected Point2D endPoint;

    // Costruttore
    public Curve(Point2D startPoint, Point2D endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    // Metodo per disegnare la curva, da implementare nelle sottoclassi
    public abstract void drawCurve(Pane pane);
    
    // Metodo astratto per calcolare la prossima posizione lungo la curva
    public abstract Point2D getNextPosition(Point2D currentPosition, double distance);

    // Getter per il punto di partenza
    public Point2D getStartPoint() {
        return startPoint;
    }

    // Getter per il punto di arrivo
    public Point2D getEndPoint() {
        return endPoint;
    }
}

