package org.example.carbonfootprintcalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    private Stage stage1;
    private Scene scene1;
    private Parent root1;

    @FXML
    private Label welcomeText;
    @FXML
    Button signOutButton;
    @FXML
    Button cfCalculatorButton;
    public void signOutAction(ActionEvent event) throws IOException{
        Stage stage = (Stage) signOutButton.getScene().getWindow();
        stage.close();
    }
    public void CfCalculatorAction(ActionEvent event) throws IOException{
        root1 = FXMLLoader.load(getClass().getResource("CF_Calculator1.fxml"));
        stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene1 = new Scene(root1);
        stage1.setScene(scene1);
        stage1.show();
    }
}