package ingdelsw.FallSimulatorPrototype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class CubicSpline extends Curve {

	
		public void drawCurve(Pane pane) {
	        if (xPoints.size() < 2 || yPoints.size() < 2) {
	            System.out.println("Sono necessari almeno due punti per disegnare una spline.");
	            return;
	        }
	        // Crea una lista di oggetti Punto che contengono x e y
	        List<Point> punti = new ArrayList<>();
	        for (int i = 0; i < xPoints.size(); i++) {
	            punti.add(new Point(xPoints.get(i), yPoints.get(i)));
	        }

	        // Ordina i punti in base alle coordinate x in modo crescente
	        Collections.sort(punti, Comparator.comparingDouble(p -> p.x));

	        // Converti la lista di punti in due array separati di x e y
	        double[] xArray = punti.stream().mapToDouble(p -> p.x).toArray();
	        double[] yArray = punti.stream().mapToDouble(p -> p.y).toArray();

	        // Crea un interpolatore per spline cubiche
	        SplineInterpolator interpolator = new SplineInterpolator();
	        PolynomialSplineFunction spline = interpolator.interpolate(xArray, yArray);

	        // Disegna la curva usando linee segmentate, assicurandoti di stare nell'intervallo corretto
	        for (double x = xArray[0]; x <= xArray[xArray.length - 1]; x += 0.1) {
	            double x1 = x;
	            double y1 = spline.value(x1);
	            double x2 = x + 0.1;
	            
	            // Assicurati che x2 sia ancora nell'intervallo prima di valutare la spline
	            if (x2 > xArray[xArray.length - 1]) {
	                break; // Esci dal ciclo se x2 è fuori dall'intervallo
	            }

	            double y2 = spline.value(x2);

	            // Disegna una linea tra i punti calcolati
	            Line line = new Line(x1, y1, x2, y2);
	            line.setStroke(Color.BLUE);
	            pane.getChildren().add(line);
	        }
		}

}
