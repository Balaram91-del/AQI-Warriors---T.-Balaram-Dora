package org.example.carbonfootprintcalculator;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CFCalculator1 implements Initializable {

    public static double carbonFootprint,transportEmission,homesAndPetsEmission,energyEmission,goodsAndServices=0.01,Diet=0.01;
    @FXML
    private ComboBox<String> countryCombobox,dietCombobox,chickenCombobox,meatCombobox,
            dairyCombobox;
    @FXML
    private ComboBox<Integer> carsCombobox,bikesCombobox,longFlightsCombobox,
            shortFlightCombobox,catsCombobox,dogsCombobox;
    @FXML
    private TextField houseSize,busTravel,metroTravel,trainTravel,electricityBill,
            renewableBill,gasBill,
            clotheSpending,stuffSpending,serviceSpending;
    @FXML
    private Button showResult,calculate;

    private Scene scene1,scene2;
    private Stage stage1,stage2;
    private Parent root1,root2;

    HashMap<String,Integer> cfcDataInteger = new HashMap<>();
    HashMap<String,String> cfcDataString = new HashMap<>();
    public static HashMap<String,Double> dataForAi = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        countryCombobox.setItems(FXCollections.observableArrayList("India","USA","Canada","Germany","France","Japan"));
        carsCombobox.setItems(FXCollections.observableArrayList(0,1,2,3,4,5));
        bikesCombobox.setItems(FXCollections.observableArrayList(0,1,2,3,4,5));
        longFlightsCombobox.setItems(FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10));
        shortFlightCombobox.setItems(FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10));
        dietCombobox.setItems((FXCollections.observableArrayList("Vegetarian","Vegan","None")));
        chickenCombobox.setItems(FXCollections.observableArrayList("Couple times a week","Everyday","multiple times a day","Rarely"));
        meatCombobox.setItems(FXCollections.observableArrayList("Never","Rarely","Couple times a week","Everyday"));
        dairyCombobox.setItems(FXCollections.observableArrayList("Couple times a week","Everyday","Many times a day"));
        catsCombobox.setItems(FXCollections.observableArrayList(0,1,2,3,4,5));
        dogsCombobox.setItems(FXCollections.observableArrayList(0,1,2,3,4,5));
        showResult.setVisible(false);
    }

    @FXML
    public void getinfo(ActionEvent event){
        cfcDataString.put("Country",countryCombobox.getValue());
        cfcDataString.put("Diet",dietCombobox.getValue());
        cfcDataString.put("Dairy",dairyCombobox.getValue());
        cfcDataString.put("Meat",meatCombobox.getValue());
        cfcDataString.put("Chicken",chickenCombobox.getValue());
        cfcDataInteger.put("noOfCars",carsCombobox.getValue());
        cfcDataInteger.put("noOfBikes",bikesCombobox.getValue());
        cfcDataInteger.put("noOfShortFlights",carsCombobox.getValue());
        cfcDataInteger.put("noOfLongFlights",carsCombobox.getValue());
        cfcDataInteger.put("noOfCats",catsCombobox.getValue());
        cfcDataInteger.put("noOfDogs",dogsCombobox.getValue());
        showResult.setVisible(true);
        try {
            int houseSizeValueM = Integer.parseInt((houseSize.getText()).trim());
            cfcDataInteger.put("houseSize", houseSizeValueM);
            int busTravelValueM = Integer.parseInt(busTravel.getText());
            cfcDataInteger.put("busTravel", busTravelValueM);
            int metroTravelValueM = Integer.parseInt(metroTravel.getText());
            cfcDataInteger.put("metroTravel", metroTravelValueM);
            int trainTravelValueM = Integer.parseInt(trainTravel.getText());
            cfcDataInteger.put("trainTravel", trainTravelValueM);
            int electricityBillValueM = Integer.parseInt(electricityBill.getText());
            cfcDataInteger.put("electricityBill", electricityBillValueM);
            int gasBillValueM = Integer.parseInt(gasBill.getText());
            cfcDataInteger.put("gasBill", gasBillValueM);
            int renewableBillValueM = Integer.parseInt(renewableBill.getText());
            cfcDataInteger.put("metroTravel", renewableBillValueM);
            int clotheSpendingValueM = Integer.parseInt(clotheSpending.getText());
            cfcDataInteger.put("cloheSpending", clotheSpendingValueM);
            int stuffSpendingValueM = Integer.parseInt(stuffSpending.getText());
            cfcDataInteger.put("struffSpending", stuffSpendingValueM);
            int serviceSpendingValueM = Integer.parseInt(serviceSpending.getText());
            cfcDataInteger.put("serviceSpending", serviceSpendingValueM);
        } catch (NumberFormatException e) {
            System.err.println("Invalid house size input: " + houseSize.getText());
        }
        carbonFootprint = calculateCarbonFootprint()/1000;
        showResult.setVisible(true);
    }
    public double calculateCarbonFootprint(){
        double totalCarbonEmission = 0;
        if (cfcDataInteger == null) {
            System.out.println("No data available for carbon footprint calculation.");
            return 0;
        }

        Integer electricityBillValue = cfcDataInteger.getOrDefault("electricityBill", 0);
        Integer renewableBillValue = cfcDataInteger.getOrDefault("renewableBill", 0);
        Integer noOfCarsValue = cfcDataInteger.getOrDefault("noOfCars", 0);
        Integer trainTravelValue = cfcDataInteger.getOrDefault("trainTravel", 0);
        Integer busTravelValue = cfcDataInteger.getOrDefault("busTravel", 0);
        Integer metroTravelValue = cfcDataInteger.getOrDefault("metroTravel", 0);
        Integer noOfDogsValue = cfcDataInteger.getOrDefault("noOfDogs", 0);
        Integer noOfCatsValue = cfcDataInteger.getOrDefault("noOfCats", 0);
        Integer noOfShortFlightsValue = cfcDataInteger.getOrDefault("noOfShortFlights", 0);
        Integer noOfLongFlightsValue = cfcDataInteger.getOrDefault("noOfLongFlights", 0);
        Integer houseSizeValue = cfcDataInteger.getOrDefault("houseSize",0);
        Integer gasBillValue = cfcDataInteger.getOrDefault("gasBill",0);
        Integer noOfBikesValue = cfcDataInteger.getOrDefault("noOfBikes",0);

        double electricityEmission = Math.round(electricityBillValue*0.713 - renewableBillValue*0.713);
        totalCarbonEmission += electricityEmission;

        double carEmission = Math.round(noOfCarsValue*0.170);
        totalCarbonEmission += carEmission;

        double bikeEmission = Math.round(noOfBikesValue*0.038);
        totalCarbonEmission += bikeEmission;

        double flightEmission = Math.round(noOfLongFlightsValue*0.15 + noOfShortFlightsValue*0.245);
        totalCarbonEmission += flightEmission;


        return totalCarbonEmission;
    }

    public void moveToPreviousScene(ActionEvent event) throws IOException {
        root1 = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene1 = new Scene(root1);
        stage1.setScene(scene1);
        stage1.show();
    }

    public void nextScene(ActionEvent event) throws IOException{

    }

}
