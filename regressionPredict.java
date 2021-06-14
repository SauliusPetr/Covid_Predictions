package sample;

import java.io.FileNotFoundException;

public class regressionPredict {

    public float xMean;
    public float yMean;
    public float predict;

    public float regressionPredict(int myArray[], int myArray2[], float xFirst, float yFirst, float uInput)
    {
        meanOfY(myArray);
        meanOfX(myArray2);
        float myGradient = lineGradient(xMean, yMean, xFirst, yFirst);
        float interception = yInterception(xMean, yMean, myGradient);
        float predict = (myGradient * uInput);
        System.out.println(predict);
        return predict;
    }
    //getting the current mean of y
    //xMean == 1 as the x axis rises by 1 each time
    public float meanOfX(int xArray[])
    {
        for (int i = 0; i < xArray.length; i++) {
            xMean += xArray[i];
        }
        xMean = xMean / xArray.length;
        System.out.println(xMean);
        return xMean;
    }

    public float meanOfY(int yArray[]) {
        for (int i = 0; i < yArray.length; i++) {
            yMean += yArray[i];
        }
        yMean = yMean / yArray.length;
        System.out.println(yMean);
        return yMean;
    }

    public float lineGradient(float meanOfX, float meanOfY, float xFirstIndex, float yFirstIndex)
    {
        float firstNum = xFirstIndex - meanOfX;
        float secNum = yFirstIndex - meanOfY;
        float myNum = firstNum * firstNum;
        float product = (firstNum * secNum) / myNum;
        return product;
    }

    public float yInterception(float meanOfX, float meanOfY, float lineGradient)
    {
        return meanOfY - (meanOfX * lineGradient);
    }


}
