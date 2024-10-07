package ingdelsw.FallSimulatorPrototype;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Curve {
	ArrayList<Double> xPoints;
	ArrayList<Double> yPoints;
	
    // Metodo per calcolare e disegnare la spline cubica utilizzando Apache Commons Math
    public abstract void drawCurve(Pane pane);

}
