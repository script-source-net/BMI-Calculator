package net.scriptsource.bmicalculator;

import connector.DatabaseConnector;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class BMICalculator extends Application {
    private static final DecimalFormat df = new DecimalFormat("0.0");
    DatabaseConnector con = new DatabaseConnector();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BMICalculator.class.getResource("hello-view.fxml"));
        stage.setTitle("BMI-Calculator!");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        Text sceneTitle = new Text("BMI Calculator");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL,20));
        grid.add(sceneTitle, 0,0,2,1);

        Label lbl_firstName = new Label("Vorname");
        Label lbl_lastName = new Label("Nachname");
        Label lbl_height = new Label("Grösse in cm:");
        Label lbl_weight = new Label("Gewicht in KG:");
        Label lbl_age = new Label("Alter:");

        TextField txt_firstName = new TextField();
        TextField txt_lastName = new TextField();
        TextField txt_height = new TextField();
        TextField txt_weight = new TextField();
        TextField txt_age = new TextField();

        ChoiceBox cb_User = new ChoiceBox<>();
        cb_User.getItems().addAll("Gion Desax","Marco Rensch", "Urs Heusser");

        final Text actiontarget = new Text();

        Button btnCalculate = new Button("Calculate");
        Button btnCancel = new Button("Cancel");


        grid.add(lbl_firstName,0,1);
        grid.add(lbl_lastName,1,1);
        grid.add(lbl_height,2,1);
        grid.add(lbl_weight,3,1);
        grid.add(lbl_age,4,1);

        grid.add(cb_User,0,1);
        /*
        grid.add(txt_firstName,0,2);
        grid.add(txt_lastName,1,2);
         */
        grid.add(txt_height,2,2);
        grid.add(txt_weight,3,2);
        grid.add(txt_age,4,2);

        grid.add(btnCancel,0,3);
        grid.add(btnCalculate,4,3);

        grid.add(actiontarget,1,4);

        btnCancel.setCancelButton(true);

        btnCalculate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);

                double height = Double.parseDouble(txt_height.getText());
                double weight = Double.parseDouble(txt_weight.getText());
                double bmi = (weight / (height*height));


                String output = String.valueOf(System.out.printf("Dein BMI Wert ist %.1f.",bmi));
                actiontarget.setText("BMI: " +df.format(bmi));
                con.InsertData(txt_firstName.getText(),txt_lastName.getText(),Integer.parseInt(txt_age.getText()),height,weight);


                /*
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Return BMI-Wert");
                alert.setContentText("Der BMI-Wert von:" + txt_firstName.getText() + " " +
                        txt_lastName + " ist " + bmi);

                 */
            }
        });


        Scene scene = new Scene(grid,500,400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}