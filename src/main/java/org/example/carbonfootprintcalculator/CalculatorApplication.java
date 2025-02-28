package org.example.carbonfootprintcalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class CalculatorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Scene scene = new Scene(root);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Carbon Footprint Calculator");
        stage.setWidth(1300);
        stage.setHeight(850);
        stage.setScene(scene);
        stage.setFullScreen(false);
        Image icon = new Image(getClass().getResourceAsStream("/image/carbon_footprint_calculator_icon.png"));
        stage.getIcons().add(icon);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}