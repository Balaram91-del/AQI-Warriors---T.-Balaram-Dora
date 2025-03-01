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
import java.sql.Connection;
import java.util.HashMap;
import java.util.ResourceBundle;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class CFCalculator1 implements Initializable {

    public static double carbonFootprint,transportEmission,homesAndPetsEmission,energyEmission,goodsAndServices=0.01,Diet=0.01;
    @FXML
    private ComboBox<String> countryCombobox,dietCombobox,chickenCombobox,meatCombobox,
            dairyCombobox;
    @FXML
    private ComboBox<Integer> longFlightsCombobox,
            shortFlightCombobox,catsCombobox,dogsCombobox,monthCombobox,yearCombobox;
    @FXML
    private TextField houseSize,busTravel,metroTravel,trainTravel,electricityBill,
            renewableBill,gasBill,
            clotheSpending,stuffSpending,serviceSpending,carTextfield,bikeTextfield;
    @FXML
    private Button showResult,calculate;

    private Scene scene1,scene2;
    private Stage stage1,stage2;
    private Parent root1,root2;

    HashMap<String,Integer> cfcDataInteger = new HashMap<>();
    HashMap<String,String> cfcDataString = new HashMap<>();
    public static HashMap<String,Double> dataForAi = new HashMap<>();
    public static HashMap<YearMonth,String> monthData= new HashMap<>();
    public static int year;
    public static int month;
    final public static double avgCarbonFootprintPerCapitaPerMonthInIndia = 166;
    public static double carbonCredits = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        countryCombobox.setItems(FXCollections.observableArrayList("India","USA","Canada","Germany","France","Japan"));
        //carsCombobox.setItems(FXCollections.observableArrayList(0,1,2,3,4,5));
        //bikesCombobox.setItems(FXCollections.observableArrayList(0,1,2,3,4,5));
        longFlightsCombobox.setItems(FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10));
        shortFlightCombobox.setItems(FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10));
        dietCombobox.setItems((FXCollections.observableArrayList("Vegetarian","Vegan","None")));
        chickenCombobox.setItems(FXCollections.observableArrayList("Couple times a week","Everyday","multiple times a day","Rarely"));
        meatCombobox.setItems(FXCollections.observableArrayList("Never","Rarely","Couple times a week","Everyday"));
        dairyCombobox.setItems(FXCollections.observableArrayList("Couple times a week","Everyday","Many times a day"));
        catsCombobox.setItems(FXCollections.observableArrayList(0,1,2,3,4,5));
        dogsCombobox.setItems(FXCollections.observableArrayList(0,1,2,3,4,5));
        monthCombobox.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12));
        yearCombobox.setItems(FXCollections.observableArrayList(2024,2025,2026,2027));
        showResult.setVisible(false);
    }

    @FXML
    public void getinfo(ActionEvent event){
        cfcDataString.put("Country",countryCombobox.getValue());
        cfcDataString.put("Diet",dietCombobox.getValue());
        cfcDataString.put("Dairy",dairyCombobox.getValue());
        cfcDataString.put("Meat",meatCombobox.getValue());
        cfcDataString.put("Chicken",chickenCombobox.getValue());
        //cfcDataInteger.put("travelledByCar",carTextfield.getText());
        //cfcDataInteger.put("travelledByBike",bikesCombobox.getValue());
        //cfcDataInteger.put("noOfShortFlights",carsCombobox.getValue());
        //cfcDataInteger.put("noOfLongFlights",carsCombobox.getValue());
        cfcDataInteger.put("noOfCats",catsCombobox.getValue());
        cfcDataInteger.put("noOfDogs",dogsCombobox.getValue());
        showResult.setVisible(true);
        year = yearCombobox.getValue();
        month = monthCombobox.getValue();
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
            int travelledByCar = Integer.parseInt(carTextfield.getText());
            cfcDataInteger.put("travelledByCar",travelledByCar);
            int travelledByBike = Integer.parseInt(bikeTextfield.getText());
            cfcDataInteger.put("travelledByCar",travelledByCar);
        } catch (NumberFormatException e) {
            System.err.println("Invalid house size input: " + houseSize.getText());
        }

        carbonFootprint = calculateCarbonFootprint()/1000;

        //calculating carbon credits

        carbonCredits = Math.round(avgCarbonFootprintPerCapitaPerMonthInIndia-(carbonFootprint)*1000)/10;

        // Create database connection instance
        databaseConnection databaseConnection1 = new databaseConnection();
        Connection conn = databaseConnection1.getConnection();

        if (conn != null) {
            carbonFootprintDB.insertCarbonFootprint(Login.loggedInUsername, month, year,carbonFootprint,carbonCredits);
        }
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
        Integer travelledByCarValue = cfcDataInteger.getOrDefault("noOfCars", 0);
        Integer trainTravelValue = cfcDataInteger.getOrDefault("trainTravel", 0);
        Integer busTravelValue = cfcDataInteger.getOrDefault("busTravel", 0);
        Integer metroTravelValue = cfcDataInteger.getOrDefault("metroTravel", 0);
        Integer noOfDogsValue = cfcDataInteger.getOrDefault("noOfDogs", 0);
        Integer noOfCatsValue = cfcDataInteger.getOrDefault("noOfCats", 0);
        Integer noOfShortFlightsValue = cfcDataInteger.getOrDefault("noOfShortFlights", 0);
        Integer noOfLongFlightsValue = cfcDataInteger.getOrDefault("noOfLongFlights", 0);
        Integer houseSizeValue = cfcDataInteger.getOrDefault("houseSize",0);
        Integer gasBillValue = cfcDataInteger.getOrDefault("gasBill",0);
        Integer travelledByBikeValue = cfcDataInteger.getOrDefault("noOfBikes",0);
        Integer clotheExpense = cfcDataInteger.getOrDefault("clotheSpending",0);

        double electricityEmission = Math.round(electricityBillValue*0.713 - renewableBillValue*0.713);
        totalCarbonEmission += electricityEmission;

        double carEmission = Math.round(travelledByCarValue*0.170);
        totalCarbonEmission += carEmission;

        double bikeEmission = Math.round(travelledByBikeValue*0.038);
        totalCarbonEmission += bikeEmission;

        double flightEmission = Math.round(noOfLongFlightsValue*0.15*4000 + noOfShortFlightsValue*0.245*500);
        totalCarbonEmission += flightEmission;

        // Train Emissions
        double trainEmission = Math.round(trainTravelValue * 0.0115);
        totalCarbonEmission += trainEmission;

        // Bus Emissions
        double busEmission = Math.round(busTravelValue * 0.076*51);
        totalCarbonEmission += busEmission;

        // Metro Emissions
        double metroEmission = Math.round(metroTravelValue * 0.025*51);
        totalCarbonEmission += metroEmission;

        // Pet Emissions (assuming standard diet factor)
        double dietFactor = 1.0;
        double petEmission = Math.round(noOfDogsValue * 64 * dietFactor + noOfCatsValue * 25 * dietFactor);
        totalCarbonEmission += petEmission;

        //Transport Emission
        transportEmission = (carEmission+trainEmission+busEmission+metroEmission+bikeEmission+flightEmission)/1000;

        //house construction emission
        double houseEmission = Math.round(houseSizeValue * 6);
        totalCarbonEmission += houseEmission;

        //gas emission
        double gasEmission = (gasBillValue*2.983);
        totalCarbonEmission += gasEmission;

        //clothe emission
        double clotheEmission = Math.round(clotheExpense*300);

        //Energy Emission
        energyEmission = (gasEmission+electricityEmission)/1000;

        //Homes & pets

        homesAndPetsEmission = (petEmission)/1000;

        //add the emission to the hashmap
        dataForAi.put("Emission from electricity usage",electricityEmission);
        dataForAi.put("Renewable energy generated",(double)renewableBillValue);
        dataForAi.put("Emission from car usage",carEmission);
        dataForAi.put("Emission from motorcycle usage",bikeEmission);
        dataForAi.put("Emission from cooking gas usage",electricityEmission);
        dataForAi.put("Emission from pets",petEmission);
        dataForAi.put("Emission from flight usage",flightEmission);
        dataForAi.put("Emission from public transport usage",metroEmission+busEmission+trainEmission);

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
        URL fxmlLocation = getClass().getResource("CF_Calculator3.fxml");
        if (fxmlLocation == null) {
            System.err.println("❌ FXML file not found: CF_Calculator2.fxml");
            // Add more debug information.
            System.err.println("Current Classpath: " + System.getProperty("java.class.path"));
            return; // Exit the method to prevent further errors
        }
        System.out.println("FXML Location: " + fxmlLocation);
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Parent root2 = fxmlLoader.load();
        CFCalculator3 cfCalculator3 = fxmlLoader.getController();
        cfCalculator3.displayResult(carbonFootprint);
        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene2 = new Scene(root2);
        stage2.setScene(scene2);
        stage2.show();
    }

}
