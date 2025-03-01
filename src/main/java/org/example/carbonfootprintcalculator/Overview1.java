package org.example.carbonfootprintcalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Overview1 {
        private Scene scene1;
        private Stage stage1;
        private Parent root1;

        public void swichToMainScene(ActionEvent event) throws IOException {
            root1 = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene1 = new Scene(root1);
            stage1.setScene(scene1);
            stage1.show();
        }

}
