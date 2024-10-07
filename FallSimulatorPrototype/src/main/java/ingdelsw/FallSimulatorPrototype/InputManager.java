package ingdelsw.FallSimulatorPrototype;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;

public class InputManager {
    private List<Point2D> points;

    public InputManager() {
        // Inizializza la lista dei punti
        points = new ArrayList<>();
    }

    // Metodo per aggiungere un punto alla lista con controlli sulle coordinate
    // Ritorna null se il punto è valido, altrimenti ritorna un messaggio d'errore
    public String addPoint(Point2D point) {
        if (points.isEmpty()) {
            // Se non ci sono punti, aggiungiamo direttamente il punto di partenza
            points.add(point);
            return null;
        } else if (points.size() == 1) {
            // Se c'è solo il punto di partenza, verifica la condizione Y del punto di arrivo
            Point2D startPoint = points.get(0);
            if (point.getY() > startPoint.getY()) {
                points.add(point);
                return null;
            } else {
                return "Errore: Il punto di arrivo deve avere una Y maggiore del punto di partenza.";
            }
        } else {
            // Verifica che i punti intermedi abbiano X compresa tra quella di start e end
            Point2D startPoint = points.get(0);
            Point2D endPoint = points.get(1);
            
            if (point.getX() > startPoint.getX() && point.getX() < endPoint.getX()) {
                points.add(point);
                return null;
            } else {
                return "Errore: I punti intermedi devono avere una X compresa tra il punto di partenza e il punto di arrivo.";
            }
        }
    }

    // Metodo per ottenere il numero di punti
    public int getNumberOfPoints() {
        return points.size();
    }

    // Metodo per ottenere l'ultimo punto inserito
    public Point2D getLastPoint(int offsetFromLast) {
        return points.get(points.size() - offsetFromLast);
    }

    // Metodo per ottenere la lista completa dei punti
    public List<Point2D> getPoints() {
        return points;
    }

    // Metodo per cancellare tutti i punti inseriti
    public void clearPoints() {
        points.clear();
    }
}
