package gui;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;

public class Gui extends Application {

    public static void main(String[] args){launch(args);}

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Damage Calculator");
        GridPane gridPane = createGridPane();
        addControlls(gridPane);
        Scene scene = new Scene(gridPane,500,350);
        stage.setScene(scene);
        stage.show();
    }

    private GridPane createGridPane(){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40,40,40,40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        return gridPane;
    }

    private void addControlls(GridPane pane) {

        UnaryOperator<TextFormatter.Change> intFilter = change -> {
            String newText = change.getControlNewText();
            if(newText.matches("-?([1-9][0-9]*)?")){
                return change;
            }
            return null;
        };
        
        // Add main label
        Label mainLabel = new Label("Calculator");
        mainLabel.setFont(Font.font("Arial", 20));
        pane.add(mainLabel, 0, 0, 2, 1);
        GridPane.setHalignment(mainLabel, HPos.CENTER);
        GridPane.setMargin(mainLabel, new Insets(20, 0, 20, 0));

        // Add gamage Label
        Label damageLabel = new Label("Damage");
        pane.add(damageLabel, 0, 1);

        // Add gamage Text Field
        TextField damageField = new TextField();
        damageField.setPrefHeight(30);
        damageField.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),0,intFilter));
        pane.add(damageField, 1, 1);

        // Add armor Label
        Label armor = new Label("Armor");
        pane.add(armor, 0, 2);

        // Add armor Text Field
        TextField armorField = new TextField();
        armorField.setPrefHeight(30);
        armorField.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),0,intFilter));
        pane.add(armorField, 1, 2);

        // Add result Label
        Label resultLabel = new Label("Result");
        pane.add(resultLabel,0,3);

        // Add result Text Field
        TextField resultField = new TextField();
        resultField.setPrefHeight(30);
        resultField.setEditable(false);
        resultField.setFocusTraversable(false);
        pane.add(resultField,1,3);

        // Add Button Calculate
        Button buttonCalculate = new Button("Calculate");
        buttonCalculate.setPrefHeight(40);
        buttonCalculate.setDefaultButton(true);
        buttonCalculate.setPrefWidth(100);
        pane.add(buttonCalculate,0,4,2,1);
        GridPane.setHalignment(buttonCalculate, HPos.CENTER);
        GridPane.setMargin(buttonCalculate, new Insets(20, 10,20,10));

        // Add button Reset Fields Text
        Button buttonReset = new Button("Reset");
        buttonReset.setPrefHeight(20);
        buttonReset.setPrefWidth(50);
        pane.add(buttonReset,1,4,3,1);
        GridPane.setHalignment(buttonReset, HPos.RIGHT);


        buttonCalculate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int damage = Integer.parseInt(damageField.getText());
                int armor = Integer.parseInt(armorField.getText());
                double finalDamage = damage/(1+(armor/100f));
                resultField.setText(String.format("%.2f",finalDamage));
            }
        });

        buttonReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                damageField.setText("");
                armorField.setText("");
                resultField.setText("");
            }
        });
    }
}
