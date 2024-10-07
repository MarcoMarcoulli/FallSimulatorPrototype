package ingdelsw.FallSimulatorPrototype;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Interface extends Application{
	// Pannello per il disegno dei punti e delle curve
    private Pane drawingPane;
    // Gestore degli input utente, responsabile della gestione dei clic e dell'interazione con la GUI
    private InputManager inputManager;

    @Override
    public void start(Stage primaryStage) {
        // Configurazione del layout principale della finestra, utilizza un BorderPane
        BorderPane root = new BorderPane();

        // Configurazione del pannello di sinistra, contiene pulsanti e istruzioni
        VBox leftPane = createLeftPane();
        root.setLeft(leftPane);  // Aggiunge il pannello di sinistra al lato sinistro del BorderPane

        // Configurazione del pannello di destra per il disegno, dove l'utente clicca per aggiungere punti
        drawingPane = new Pane();
        drawingPane.setPrefSize(600, 600);  // Imposta le dimensioni preferite del pannello di disegno
        drawingPane.setStyle("-fx-border-color: black;");  // Aggiunge un bordo al pannello per separarlo visivamente
        root.setCenter(drawingPane);  // Aggiunge il pannello di disegno al centro del BorderPane

        // Creazione della scena con dimensioni 800x600 e aggiunta del root BorderPane
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Fall along curves Simulator");  // Imposta il titolo della finestra
        primaryStage.setScene(scene);  // Imposta la scena principale
        primaryStage.show();  // Mostra la finestra principale

        // Inizializzazione dell'InputManager con il pannello di disegno e i pulsanti
        inputManager = new InputManager(drawingPane, getResetButton(), getDrawButton());
    }

    // Metodo per creare il pannello di sinistra con i pulsanti e le istruzioni
    private VBox createLeftPane() {
        VBox leftPane = new VBox(10);  // Layout verticale con spaziatura di 10px tra i nodi
        leftPane.setPadding(new Insets(10));  // Imposta un padding interno di 10px
        leftPane.setPrefWidth(200);  // Imposta la larghezza preferita a 200px
        leftPane.setStyle("-fx-border-color: black;");  // Aggiunge un bordo al pannello

        // Creazione dell'etichetta per le istruzioni
        Label instructionsLabel = new Label("Inserisci i punti cliccando\nsul pannello a destra.");
        instructionsLabel.setWrapText(true);  // Consente il testo su più righe

        // Creazione dei pulsanti per resettare i punti e disegnare la spline
        Button resetButton = new Button("Resetta Punti");
        Button drawButton = new Button("Fine Immissione");

        // Salva i pulsanti nei rispettivi campi in modo che possano essere usati in InputManager
        setResetButton(resetButton);
        setDrawButton(drawButton);

        // Allineamento al top e aggiunta dei componenti al pannello di sinistra
        leftPane.setAlignment(Pos.TOP_CENTER);
        leftPane.getChildren().addAll(instructionsLabel, resetButton, drawButton);
        return leftPane;  // Restituisce il pannello configurato
    }

    // Metodo per memorizzare il pulsante reset in un campo per essere usato in InputManager
    private Button resetButton;
    private void setResetButton(Button resetButton) {
        this.resetButton = resetButton;
    }
    private Button getResetButton() {
        return this.resetButton;
    }

    // Metodo per memorizzare il pulsante draw in un campo per essere usato in InputManager
    private Button drawButton;
    private void setDrawButton(Button drawButton) {
        this.drawButton = drawButton;
    }
    private Button getDrawButton() {
        return this.drawButton;
    }

    public static void main(String[] args) {
        // Metodo main che avvia l'applicazione JavaFX
        launch(args);
    }

	

}
