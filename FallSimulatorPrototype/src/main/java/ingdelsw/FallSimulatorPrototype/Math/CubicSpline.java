package ingdelsw.FallSimulatorPrototype.Math;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import ingdelsw.FallSimulatorPrototype.InputManager;

public class CubicSpline extends Curve {
    private PolynomialSplineFunction splineX; // Spline per le coordinate X
    private PolynomialSplineFunction splineY; // Spline per le coordinate Y
    
    private double currentT;  // Valore corrente del parametro t
    private double stepSize;  // Passo per incrementare t

    // Costruttore
    public CubicSpline(InputManager inputManager) {
        super(inputManager.getPoints().get(0), inputManager.getPoints().get(1));
        calculateSpline(inputManager);
        this.currentT = 0;
    }

    // Calcolo della spline cubica utilizzando Apache Commons Math
    private void calculateSpline(InputManager inputManager) {
        int n = inputManager.getPointCount();
        double[] t = new double[n];  // Parametro t per interpolare
        double[] x = new double[n];  // Coordinate X dei punti
        double[] y = new double[n];  // Coordinate Y dei punti

        // Inizializza i valori di t, x, y
        for (int i = 0; i < n; i++) {
            t[i] = i; // Usa un parametro t incrementale
            Point2D point = inputManager.getPoints().get(i);
            x[i] = point.getX();
            y[i] = point.getY();
        }

        // Calcola le spline cubiche per X e Y
        SplineInterpolator interpolator = new SplineInterpolator();
        splineX = interpolator.interpolate(t, x);
        splineY = interpolator.interpolate(t, y);
    }

    // Disegna la spline cubica sul pannello
    @Override
    public void drawCurve(Pane pane) {
        int steps = 100; // Numero di segmenti per approssimare la curva
        double stepSize = (splineX.getN() - 1) / (double) steps;

        for (int i = 0; i < steps; i++) {
            // Calcola due punti consecutivi sulla spline
            double t1 = i * stepSize;
            double t2 = (i + 1) * stepSize;

            Point2D point1 = new Point2D(splineX.value(t1), splineY.value(t1));
            Point2D point2 = new Point2D(splineX.value(t2), splineY.value(t2));

            // Disegna una linea tra i due punti
            Line line = new Line(point1.getX(), point1.getY(), point2.getX(), point2.getY());
            line.setStroke(Color.BLUE);
            pane.getChildren().add(line);
        }
    }
    
    @Override
    public Point2D getNextPosition(Point2D currentPosition, double distance) {
        // Incrementa t per avanzare lungo la spline
        currentT += stepSize;

        // Calcola la prossima posizione usando le funzioni spline
        double x = splineX.value(currentT);
        double y = splineY.value(currentT);

        return new Point2D(x, y);
    }
}
