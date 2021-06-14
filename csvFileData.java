package sample;
import java.io.*;
import java.util.*;

public class csvFileData {
    private String filename;
    public csvFileData (String filename) {
        this.filename = filename;

    }

    static int countLine = 0;
    public String linesOfData[];
    public String dataStrings[][];
    public int numberCases[];
    public String dateStringArray[][];
    public int dateArray[];
    public int countPerNation;
    public int casesPerDay[];
    public boolean tried = false;

    public void createArray(String country, String[][] countryArray)
    {
        int i=0;
        for (i = countryArray.length - 1; i >= 0; i--) {
            if (countryArray[i][1].equals(country)) {
                countPerNation++;
            }
        }

        numberCases = new int[countPerNation];
        casesPerDay = new int[countPerNation];


        //adding number of cases to the numberCases array
        int j = 0;
        for (i = 0; i < countryArray.length; i++) {
            if (countryArray[i][1].equals(country)) {
                numberCases[j] = Integer.parseInt(countryArray[i][5]);
                j++;
            }
        }

        dateArray = new int[countPerNation];
        dateStringArray = new String[countPerNation][3];

        //adding cases per day
        j = 0;
        for (i = 0; i < numberCases.length; i++) {
            if (countryArray[i][1].equals(country)) {
                casesPerDay[j] = Integer.parseInt(countryArray[i][4]);
                j++;
            }
        }

    }

    //Read the CSV file
    public String[][] readCSV () throws FileNotFoundException {

        if (!tried) {
            Scanner readFile = new Scanner(new File(filename));

            //Doing this to ignore the first line of the csv file which outlines the column names within the .csv file
            readFile.nextLine();

            //counting all lines
            while (readFile.hasNextLine()) {
                countLine++;
                readFile.nextLine();
            }
            readFile.close();
            readFile = null;
            //initialising linesOfData and setting the size to be the same as countLine
            linesOfData = new String[countLine];
            Scanner fileToArray = new Scanner(new File(filename));
            int i = 0;
            //doing this to ignore the first line of the csv file
            fileToArray.nextLine();
            //while loop to add each line of the csv file to an array
            while (fileToArray.hasNextLine()) {
                linesOfData[i] = fileToArray.nextLine();
                System.out.println(linesOfData[i]);
                i++;
            }
            System.out.println("Hello World");
            fileToArray.close();
            fileToArray = null;
            dataStrings = new String[linesOfData.length][6];

            //going in reverse to sort by date in ascending order
            //in the file, the newest date is first
            //this makes it so that the oldest date is first
            for (i = linesOfData.length - 1; i >= 0; i--) {
                dataStrings[i] = linesOfData[i].split(",");
            }

        }
        return dataStrings;
    }

    public Integer averageCases(){
        int total = 0;
        for (int i = 0; i < casesPerDay.length - 1; i++) {
            total += casesPerDay[i];
        }
        int average = total / casesPerDay.length;
        return average;
    }

    public int highestandlowestCases(String selection){
        int max = 0;
        int min = casesPerDay[0];
        int value = 0;
        if (selection == "highest") {
            for (int i = 0; i < casesPerDay.length; i++) {
                if (casesPerDay[i] > max) {
                    max = casesPerDay[i];
                }
                value = max;
            }
        }
        else {
            for (int i = 0; i < casesPerDay.length; i++) {
                if (casesPerDay[i] < min) {
                    min = casesPerDay[i];
                }
                value = min;
            }
        }
        return value;
    }

    public int casesPopuationComparison (String country) {

        return 0;

    }
}



