package org.example.carbonfootprintcalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Certificate {

    private Scene scene1,scene2;
    private Stage stage1,stage2;
    private Parent root1,root2;

    @FXML
    private Label nameLabel;
    public void Displayname(String username){
        nameLabel.setText(username);
    }

    public void moveToPreviousScene(ActionEvent event) throws IOException {
        root1 = FXMLLoader.load(getClass().getResource("carbonPoints.fxml"));
        stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene1 = new Scene(root1);
        stage1.setScene(scene1);
        stage1.show();
    }

}
