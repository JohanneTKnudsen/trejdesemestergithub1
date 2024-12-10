package com.example.demo;

// Importerer nødvendige JavaFX-klasser
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Klassen arver fra Application, hvilket gør det muligt at oprette en JavaFX-applikation
public class HelloApplication extends Application {

    // start() er JavaFX-applikationens hovedmetode, hvor brugergrænsefladen opsættes
    @Override
    public void start(Stage primaryStage) {

        // Layout: Vertikal boks (VBox) med 10 pixels mellemrum mellem komponenterne
        VBox root = new VBox(10);

        // Sætter en margin (indsætning) omkring indholdet for at give plads
        root.setPadding(new Insets(15));
        root.setId("root"); // Tilføj CSS-id

        // Opretter en scene, der indeholder VBox-layoutet og sætter scenens størrelse
        Scene scene = new Scene(root, 300, 250);

        // Indstiller vinduets titel
        primaryStage.setTitle("Tip beregner");

        // Tilføjer scenen til vinduet (Stage)
        primaryStage.setScene(scene);

        // Viser vinduet til brugeren
        primaryStage.show();

        // Tilføj CSS-styling fra filen "css.css"
        scene.getStylesheets().add(getClass().getResource("/css/css.css").toExternalForm());



        // Label til at vise teksten "Indtast regningsbeløb"
        Label billLabel = new Label("Indtast regningsbeløb:");
        billLabel.setId("bill-label"); // Tilføj CSS-id

        // Tekstfelt, hvor brugeren kan indtaste regningens beløb
        TextField billInput = new TextField();

        // Sætter en pladsmarker for tekstfeltet, der viser et eksempel (f.eks. 500)
        billInput.setPromptText("f.eks. 500");
        billInput.setId("bill-input"); // Tilføj CSS-id


        // Label til at beskrive sliderens funktion
        Label tipLabel = new Label("Vælg drikkepengeprocent:");
        tipLabel.setId("tip-label"); // Tilføj CSS-id

        // Slider til at vælge drikkepengeprocent (minimum: 0%, maksimum: 30%, standard: 15%)
        Slider tipSlider = new Slider(0, 30, 15);

        // Viser værdierne på slideren (f.eks. 0%, 5%, 10%, osv.)
        tipSlider.setShowTickLabels(true);

        // Viser markeringer på slideren for lettere navigation
        tipSlider.setShowTickMarks(true);

        // Sætter intervallet mellem de større markeringer (5% for hver større markering)
        tipSlider.setMajorTickUnit(5);
        tipSlider.setId("tip-slider"); // Tilføj CSS-id

        // Label til at vise den beregnede drikkepengesum
        Label tipAmountLabel = new Label("Drikkepenge: 0.00 kr");

        // Label til at vise det samlede beløb, inklusiv drikkepenge
        Label totalAmountLabel = new Label("Samlet beløb: 0.00 kr");
        totalAmountLabel.setId("total-amount-label"); // Tilføj CSS-id

        // Tilføjer en lytter til slideren, så den opdaterer beløb, når slideren justeres
        tipSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateAmounts(billInput, tipSlider, tipAmountLabel, totalAmountLabel);
        });

        // Tilføjer en lytter til tekstfeltet, så den opdaterer beløb, når brugeren indtaster noget nyt
        billInput.textProperty().addListener((observable, oldValue, newValue) -> {
            updateAmounts(billInput, tipSlider, tipAmountLabel, totalAmountLabel);
        });

     root.getChildren().add(billLabel);
     root.getChildren().add(billInput);
     root.getChildren().add(tipLabel);
     root.getChildren().add(tipSlider);
     root.getChildren().add(tipAmountLabel);
     root.getChildren().add(totalAmountLabel);

    }

    // Metode til at beregne og opdatere drikkepenge og samlet beløb
    private void updateAmounts(TextField billInput, Slider tipSlider, Label tipAmountLabel, Label totalAmountLabel) {
        try {

            // Læser regningens beløb fra tekstfeltet og konverterer det til et tal
            double billAmount = Double.parseDouble(billInput.getText());
            // Læser den valgte drikkepengeprocent fra slideren
            double tipPercent = tipSlider.getValue();
            // Beregner drikkepengebeløbet
            double tipAmount = billAmount * tipPercent / 100;
            // Beregner det samlede beløb (regning + drikkepenge)
            double totalAmount = billAmount + tipAmount;

            // Opdaterer labels med de beregnede værdier
            tipAmountLabel.setText(String.format("Drikkepenge: %.2f kr", tipAmount));
            totalAmountLabel.setText(String.format("Samlet beløb: %.2f kr", totalAmount));
        } catch (NumberFormatException e) {

            // Hvis der indtastes ugyldige data (f.eks. bogstaver i stedet for tal), nulstilles beløbene
            tipAmountLabel.setText("Drikkepenge: 0.00 kr");
            totalAmountLabel.setText("Samlet beløb: 0.00 kr");
        }
    }

    // Hovedmetode, der starter applikationen
    public static void main(String[] args) {
        // launch() er en metode fra Application-klassen, der starter JavaFX
        launch();
    }
}


