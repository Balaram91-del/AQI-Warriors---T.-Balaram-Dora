package org.example.carbonfootprintcalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login {
    @FXML
    private Button cancelButton, loginButton;
    @FXML
    private Label loginResponseLabel;
    @FXML
    private TextField usernameTextfield;
    @FXML
    PasswordField passwordPasswordfield;

    public static String loggedInUsername;
    private Scene scene1;
    private Stage stage1;
    private Parent root1;
//    public void setLoginButton(ActionEvent event){
//        if(usernameTextfield.getText().isBlank() == true || passwordPasswordfield.getText().isBlank() == true)
//            loginResponseLabel.setText("Please Enter a Username & Password");
//        validateLogin();
//    }

    public void cancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    public void validateLogin(ActionEvent event) {
        databaseConnection connectNow = new databaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB == null) {
            loginResponseLabel.setText("Database connection failed!");
            return;
        }

        String verifyLogin = "SELECT count(1) FROM userData WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
            preparedStatement.setString(1, usernameTextfield.getText());
            preparedStatement.setString(2, passwordPasswordfield.getText());

            ResultSet queryResult = preparedStatement.executeQuery();

            if (queryResult.next() && queryResult.getInt(1) == 1) {
                loggedInUsername = usernameTextfield.getText();


                // **Switch to the new scene**
                FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

//                DashboardController dashboardController = new DashboardController();
//                dashboardController.displayUsername(loggedInUsername);

            } else {
                loginResponseLabel.setText("Invalid login. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
