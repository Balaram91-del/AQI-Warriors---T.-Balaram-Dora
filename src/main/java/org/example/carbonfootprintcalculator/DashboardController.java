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

import static org.example.carbonfootprintcalculator.Login.loggedInUsername;

public class DashboardController {

    private Stage stage1,stage2,stage3;
    private Scene scene1,scene2,scene3;
    private Parent root1,root2,root3;

    @FXML
    private Label welcomeText;
    @FXML
    Button signOutButton;
    @FXML
    Button cfCalculatorButton;
    @FXML
    Label usernameLabel;
    //private String loggedInUsername;

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
    public void OverviewAction(ActionEvent event) throws IOException{
        root2 = FXMLLoader.load(getClass().getResource("Overview1.fxml"));
        stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene2 = new Scene(root2);
        stage2.setScene(scene2);
        stage2.show();
    }

    public void goToCarbonPoints(ActionEvent event) throws IOException{
        root3 = FXMLLoader.load(getClass().getResource("carbonPoints.fxml"));
        stage3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene3 = new Scene(root3);
        stage3.setScene(scene3);
        stage3.show();
    }
//    public void displayUsername(String username){
//        usernameLabel.setText(username);
//    }
//public void setUsername(String username) {
//    this.(Login.loggedInUsername) = username;
//    if(usernameLabel != null){
//        usernameLabel.setText(Login.loggedInUsername); //Example of using the username.
//    }
}
