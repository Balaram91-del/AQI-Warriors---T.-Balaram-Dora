package org.example.carbonfootprintcalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CarbonPoints implements Initializable {
    private Scene scene1,scene2;
    private Stage stage1,stage2;
    private Parent root1,root2;

    @FXML
    private BarChart<String, Number> barchartCarbonPoints;
    @FXML
    private AnchorPane overallCreditsAnchor,monthlyCreditsAnchor,yearlyCreditsAnchor;
    @FXML
    private Label overall1,overall2,overall4,overall3,year1,year2,year3,year4,monthly1,monthly2,monthly4,monthly3,certificateLabel;
    @FXML
    private Button certificateButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Monthly Carbon Footprint");
        series.getData().add(new XYChart.Data<>("USA", 1.525));
        series.getData().add(new XYChart.Data<>("UK", 0.558));
        series.getData().add(new XYChart.Data<>("You", CFCalculator1.carbonFootprint));
        series.getData().add(new XYChart.Data<>("India", 0.2));
        series.getData().add(new XYChart.Data<>("World", 0.408));
        series.getData().add(new XYChart.Data<>("China", 0.666));

        barchartCarbonPoints.getData().add(series);
        overallCreditsAnchor.setVisible(false);
        yearlyCreditsAnchor.setVisible(false);
        monthlyCreditsAnchor.setVisible(false);
        overall1.setVisible(false);
        overall2.setVisible(false);
        overall3.setVisible(false);
        overall4.setVisible(false);
        year1.setVisible(false);
        year2.setVisible(false);
        year3.setVisible(false);
        year4.setVisible(false);
        monthly1.setVisible(false);
        monthly2.setVisible(false);
        monthly3.setVisible(false);
        monthly4.setVisible(false);
        certificateLabel.setVisible(false);
        certificateButton.setVisible(false);

    }
    public void moveToPreviousScene(ActionEvent event) throws IOException {
        root1 = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene1 = new Scene(root1);
        stage1.setScene(scene1);
        stage1.show();
    }
    public void actionOnButtonClick(ActionEvent event) {
        System.out.println("Button clicked! Updating UI...");

        // Ensure database calls return values
        int totalCredits = databaseConnection.getTotalCredits();
        int yearlyCredits = databaseConnection.getYearlyCredits(CFCalculator1.year);
        int monthlyCredits = databaseConnection.getMonthlyCredits(CFCalculator1.month, CFCalculator1.year);

        System.out.println("Total Credits: " + totalCredits);
        System.out.println("Yearly Credits: " + yearlyCredits);
        System.out.println("Monthly Credits: " + monthlyCredits);

        // Update labels
        overall2.setText(Integer.toString(totalCredits));
        year2.setText(Integer.toString(yearlyCredits));
        monthly2.setText(Integer.toString(monthlyCredits));

        // Show elements
        overallCreditsAnchor.setVisible(true);
        yearlyCreditsAnchor.setVisible(true);
        monthlyCreditsAnchor.setVisible(true);

        overall1.setVisible(true);
        overall2.setVisible(true);
        overall3.setVisible(true);
        overall4.setVisible(true);

        year1.setVisible(true);
        year2.setVisible(true);
        year3.setVisible(true);
        year4.setVisible(true);

        monthly1.setVisible(true);
        monthly2.setVisible(true);
        monthly3.setVisible(true);
        monthly4.setVisible(true);

        // Show certificate if criteria met
        if (CFCalculator1.carbonFootprint <= 0.190) {
            certificateLabel.setVisible(true);
            certificateButton.setVisible(true);
        }

        System.out.println("UI Updated Successfully.");
    }

    public void takeToCertificate(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("certificate.fxml"));
        root2 = loader.load(); // Load the FXML file properly
        stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene2 = new Scene(root2);
        stage2.setScene(scene2);
        stage2.show();

        // Get the controller from FXMLLoader, not by creating a new instance
        Certificate certificate = loader.getController();
        certificate.Displayname(Login.loggedInUsername); // Set the username
    }


}
