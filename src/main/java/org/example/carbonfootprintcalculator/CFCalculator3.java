package org.example.carbonfootprintcalculator;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CFCalculator3 implements Initializable {

    @FXML
    private BarChart<String, Number> barchart;
    @FXML
    private PieChart piechart;
    @FXML
    private Label resultLabel, aiSuggestionLabel;

    private Scene scene1,scene2;
    private Stage stage1,stage2;
    private Parent root1,root2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        //Bar Chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Monthly Carbon Footprint");
        series.getData().add(new XYChart.Data<>("USA", 1.525));
        series.getData().add(new XYChart.Data<>("UK", 0.558));
        series.getData().add(new XYChart.Data<>("You", CFCalculator1.carbonFootprint));
        series.getData().add(new XYChart.Data<>("India", 0.2));
        series.getData().add(new XYChart.Data<>("World", 0.408));
        series.getData().add(new XYChart.Data<>("China", 0.666));

        barchart.getData().add(series);

        //Pie chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Transport", CFCalculator1.transportEmission),
                new PieChart.Data("Energy", CFCalculator1.energyEmission),
                new PieChart.Data("Home & Pets", CFCalculator1.homesAndPetsEmission),
                new PieChart.Data("Goods & Services", CFCalculator1.goodsAndServices),
                new PieChart.Data("Diet", CFCalculator1.Diet));

        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(data.getName()," amount ",String.format("%.2f",data.getPieValue()), "Tons")
                ));
        piechart.setData(pieChartData);
    }

    public void displayResult(double result) {
        resultLabel.setText(String.format("Your Carbon Footprint is %.2f Tons", result));
    }

    public void anaysisAndSuggestion(ActionEvent event) throws IOException{
        try {
            aiSuggestionLabel.setText(aiSuggestions(CFCalculator1.dataForAi));
        }catch (Exception e){
            aiSuggestionLabel.setText("Error retrieving AI suggestions: " + e.getMessage());
            e.printStackTrace(); // For debugging
        }

    }

    //Method for AI Suggestions
    public static String aiSuggestions(HashMap<String, Double> data) {
        String apiUrl = "https://openrouter.ai/api/v1/chat/completions";
        String apiKey = "";
        JSONObject jsonData = new JSONObject(data);
        String prompt = "Analyze the following carbon footprint data of a month, categorized by cause and reason, and provide actionable suggestions for reducing it. Data: " + jsonData.toString() + ".  Please keep the suggestions concise and under 150 words, and focus on practical steps the person can take.";

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "deepseek/deepseek-r1:free");
        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", prompt);
        messages.put(message);
        requestBody.put("messages", messages);
        requestBody.put("top_p", 1);
        requestBody.put("temperature", 0.85);
        requestBody.put("repetition_penalty", 1);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();

            if (statusCode == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                JSONArray choices = jsonResponse.getJSONArray("choices");

                if (choices.length() > 0) {
                    return choices.getJSONObject(0).getJSONObject("message").getString("content");
                } else {
                    return "No response content found.";
                }
            } else {
                return "API Error: " + response.body();
            }

        } catch (IOException | InterruptedException e) {
            return "Error: " + e.getMessage();
        } catch (org.json.JSONException e){
            return "Invalid Json response: " + e.getMessage();
        }
    }

    public void switchToCF_Calculator1(ActionEvent event) throws IOException {
        root1 = FXMLLoader.load(getClass().getResource("CF_Calculator1.fxml"));
        stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene1 = new Scene(root1);
        stage1.setScene(scene1);
        stage1.show();
    }
    public void switchToCarbonPoints(ActionEvent event) throws IOException {
        root2 = FXMLLoader.load(getClass().getResource("carbonPoints.fxml"));
        stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene2 = new Scene(root2);
        stage2.setScene(scene2);
        stage2.show();
    }
}
