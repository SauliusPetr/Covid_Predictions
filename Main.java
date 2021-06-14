package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.event.ActionEvent;
import java.io.FileNotFoundException;



public class Main extends Application {

    final int LABEL_SIZE = 100;
    final int MAX_DAYS = 11;

    public float Xmean,Ymean, predictedCases;

    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();

    private static final Insets INSETS = new Insets(1, 1, 1, 1);
    public static int arrayForX[];
    public static String country;
    public static String compare;
    public static int weeksAhead = 1;
    public static String data;


    final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

    VBox leftPanel = new VBox();
    VBox bottomPanel = new VBox();
    ComboBox countrySelection = new ComboBox();
    ComboBox compareWith = new ComboBox();
    ComboBox days = new ComboBox();
    ComboBox dataOption = new ComboBox();

    BorderPane borderPane = new BorderPane();

    @Override
    public void start(Stage stage){

        stage.setTitle("Team 26");

        ButtonPanel();

        //representing the contents
        Scene scene = new Scene(borderPane, 1000, 700);
        borderPane.setCenter(lineChart);
        stage.setScene(scene);
        stage.show();
    }

    //once apply button is pressed  Display graph is called and shows the graph
    EventHandler<ActionEvent> applyPressed = new EventHandler<>() {
        public void handle(ActionEvent e) {
            try {
                weeksAhead = (Integer) days.getValue();
                switch (data) {
                    case "Cases":
                        csvFileData casesCSV = new csvFileData("C:\\Users\\USER\\OneDrive\\My Documents\\2ndYearUni\\CE291Team26\\data_2020-Oct-22.csv"); //*change file location to match.
                        casesCSV.readCSV();
                        csvFileData compCasesCSV = new csvFileData("C:\\Users\\USER\\OneDrive\\My Documents\\2ndYearUni\\CE291Team26\\data_2020-Oct-22.csv"); //*
                        if (compare != "None") {
                            DisplayGraph(compare, weeksAhead, compCasesCSV, casesCSV.dataStrings, false);
                        }

                        DisplayGraph(country, weeksAhead, casesCSV, casesCSV.dataStrings, false);
                        DataPanel();
                        break;
                    case "Deaths":
                        csvFileData deathsCSV = new csvFileData("C:\\Users\\USER\\OneDrive\\My Documents\\2ndYearUni\\CE291Team26\\data_2021-Mar-11.csv"); //*
                        deathsCSV.readCSV();
                        csvFileData compDeathsCSV = new csvFileData("C:\\Users\\USER\\OneDrive\\My Documents\\2ndYearUni\\CE291Team26\\data_2021-Mar-11.csv"); //*
                        if (compare != "None") {
                            DisplayGraph(compare, weeksAhead, compDeathsCSV, deathsCSV.dataStrings, false);
                        }

                        DisplayGraph(country, weeksAhead, deathsCSV, deathsCSV.dataStrings, false);
                        DataPanel();
                        break;
                }
            } catch (FileNotFoundException f) {
                System.out.println("ERROR: The File Could Not be Found");
            }
        }
    };


    EventHandler<ActionEvent> avePress = new EventHandler<>() {
        public void handle(ActionEvent e)
        {
            try {
                weeksAhead = (Integer) days.getValue();
                switch (data) {
                    case "Cases":
                        csvFileData casesCSV = new csvFileData("C:\\Users\\USER\\OneDrive\\My Documents\\2ndYearUni\\CE291Team26\\data_2020-Oct-22.csv"); //*
                        casesCSV.readCSV();
                        csvFileData compCasesCSV = new csvFileData("C:\\Users\\USER\\OneDrive\\My Documents\\2ndYearUni\\CE291Team26\\data_2020-Oct-22.csv"); //*
                        if (compare != "None") {
                            DisplayGraph(compare, weeksAhead, compCasesCSV, casesCSV.dataStrings, true);
                        }

                        DisplayGraph(country, weeksAhead, casesCSV, casesCSV.dataStrings, true);
                        DataPanel();
                        break;
                    case "Deaths":
                        csvFileData deathsCSV = new csvFileData("C:\\Users\\USER\\OneDrive\\My Documents\\2ndYearUni\\CE291Team26\\data_2021-Mar-11.csv"); //*
                        deathsCSV.readCSV();
                        csvFileData compDeathsCSV = new csvFileData("C:\\Users\\USER\\OneDrive\\My Documents\\2ndYearUni\\CE291Team26\\data_2021-Mar-11.csv"); //*
                        if (compare != "None") {
                            DisplayGraph(compare, weeksAhead, compDeathsCSV, deathsCSV.dataStrings, true);
                        }

                        DisplayGraph(country, weeksAhead, deathsCSV, deathsCSV.dataStrings, true);
                        DataPanel();
                        break;
                }  
            } catch (FileNotFoundException f) {
                System.out.println("ERROR: The File Could Not be Found");
            }

        }
    };

    public static void main(String[] args){
        launch(args);
    }

    //Method that creates a button
    public Button createButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(200);
        button.setMaxHeight(100);
        button.setMinWidth(150);
        button.setPrefSize(200, 100);
        //button.setLayoutY(y);
        BorderPane.setMargin(button, INSETS);
        BorderPane.setAlignment(button, Pos.CENTER);
        return button;
    }

    //Method that creates a label
    public Label createlabel(String text, int size) {
        Label label = new Label(text);
        label.setPrefSize(200,200);
        BorderPane.setAlignment(label, Pos.BASELINE_LEFT);
        return label;
    }

    //Method that displays the actual graph
    public void DisplayGraph(String country, int weeksAhead, csvFileData myCSV, String[][] myArray, boolean isAverage) throws FileNotFoundException {



        //defining the axes
        xAxis.setLabel("Number of Days");
        yAxis.setLabel("Number cumCases");

        //creating the chart
        lineChart.setTitle("Cumulative number of covid cases");


        XYChart.Series numCumCases = new XYChart.Series();

        //Legend
        numCumCases.setName("Projected cases " + weeksAhead + " weeks in the future.");

        //passing a the country that we have selected to read all the lines associated with it
        //myCSV.readCSV(country);
        myCSV.createArray(country, myArray);

        //Should average out the data based on population size
        //Makes comparisons more accurate
        if(isAverage)
        {
            if(country.equals("England"))
            {
                for(int i=0; i<myCSV.numberCases.length; i++)
                {
                    myCSV.numberCases[i] /= 297;
                }
            }
            else if(country.equals("Wales"))
            {
                for(int i=0; i<myCSV.numberCases.length; i++)
                {
                    myCSV.numberCases[i] /= 17;
                }
            }
            else if(country.equals("Scotland"))
            {
                for(int i=0; i<myCSV.numberCases.length; i++)
                {
                    myCSV.numberCases[i] /= 29;
                }
            }
            else if(country.equals("Northern Ireland"))
            {
                for(int i=0; i<myCSV.numberCases.length; i++)
                {
                    myCSV.numberCases[i] /= 10;
                }
            }
        }


        //array for storing days
        arrayForX = new int[myCSV.numberCases.length];

        //adding data to number of cumulative cases
        int j = 0;
        for (int i = myCSV.numberCases.length - 1; i >= 0; i--) {
            numCumCases.getData().add(new XYChart.Data(j, myCSV.numberCases[i]));
            j += 1;
        }

        XYChart.Series numCumDeaths = new XYChart.Series();
        numCumDeaths.setName(country);

        //adding data to number of cumulative deaths and string days into an array
        j = 0;
        for (int i = myCSV.numberCases.length - 1; i >= 0; i--) {
            numCumDeaths.getData().add(new XYChart.Data(j, myCSV.numberCases[i]));
            arrayForX[i] = j;
            j += 1;
        }

        //Calling regression predict class and predicting number of weeks that user chose by giving parameters
        //and adding calculated new generated info into the graph
        regressionPredict rp = new regressionPredict();
        if (weeksAhead != 0) {
            numCumCases.getData().add(new XYChart.Data(myCSV.numberCases.length + (weeksAhead * 7), (rp.regressionPredict(myCSV.numberCases, arrayForX, arrayForX[0], myCSV.numberCases[0], myCSV.numberCases.length + (weeksAhead * 7)))));
        }

        Xmean = rp.xMean;

        Ymean = rp.yMean;
        predictedCases = rp.predict;
        lineChart.getData().add(numCumCases);
        lineChart.getData().add(numCumDeaths);

    }

    //adding labels ,combo boxes , buttons to the panel

    public void ButtonPanel() {
        Label selectLabel = createlabel("Select Country: ", LABEL_SIZE);
        //adding names to combo boxes
        countrySelection.getItems().add("England");
        countrySelection.getItems().add("Scotland");
        countrySelection.getItems().add("Wales");
        countrySelection.getItems().add("Northern Ireland");

        //gets selected country once chosen in combo box
        countrySelection.setOnAction((event) -> {
            Object countrySelected = countrySelection.getSelectionModel().getSelectedItem();
            country = (String) countrySelected;
        });

        Label compareLabel = createlabel("Compare To: ", LABEL_SIZE);
        //adding names to combo boxes
        compareWith.getItems().add("None");
        compareWith.getItems().add("England");
        compareWith.getItems().add("Scotland");
        compareWith.getItems().add("Wales");
        compareWith.getItems().add("Northern Ireland");

        compareWith.setOnAction((event) -> {
            Object comparreSelect = compareWith.getSelectionModel().getSelectedItem();
            compare = (String) comparreSelect;
        });

        Label dataLabel = createlabel("Data to Show: ", LABEL_SIZE);
        //adding names to combo boxes
        dataOption.getItems().add("Cases");
        dataOption.getItems().add("Deaths");

        //gets selected data to display, will determine which csv file is read
        dataOption.setOnAction((event) -> {
            Object dataSelected = dataOption.getSelectionModel().getSelectedItem();
            data = (String) dataSelected;
        });

        Label weeksAheadLabel = createlabel("Weeks to Predict:", LABEL_SIZE);

        for (int i = 0; i < MAX_DAYS; i++) {
            days.getItems().add(i);
        }

        leftPanel.setSpacing(5);

        Button apply = createButton("Apply");
        leftPanel.setMargin(apply, INSETS);

        Button ave = createButton("Average");
        leftPanel.setMargin(ave, INSETS);

        ObservableList buttonList = leftPanel.getChildren();
        buttonList.addAll(selectLabel, countrySelection, compareLabel, compareWith, dataLabel, dataOption, weeksAheadLabel, days, apply, ave);
        borderPane.setLeft(leftPanel);

        apply.setOnAction(applyPressed);
        ave.setOnAction(avePress);
    }

    //Displays the average of X,Y means and predicted cases
    public void DataPanel() {
        Label averageLabelX = createlabel("Average mean of X: " + Xmean, LABEL_SIZE);
        Label averageLabelY = createlabel("Average mean of Y: " + Ymean, LABEL_SIZE);
        Label averageLabelP = createlabel("Average predicted cases: "+ predictedCases, LABEL_SIZE);
        ObservableList dataList = bottomPanel.getChildren();
        dataList.addAll(averageLabelX,averageLabelY,averageLabelP);
        borderPane.setLeft(bottomPanel);
    }

}
