/**
 * Created by jonathanw on 6/15/17.
 */

import com.sun.javaws.jnl.LibraryDesc;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.util.*;

public class LineGraph extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    public LineGraph()
    {
        //Empty default constructor
    }
    @Override public void start(Stage primaryStage)
    {

    }

    public LineChart<String, Number> LineAvgGPA(String courseSubject, int courseNum, String professor)
    {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Semester");
        yAxis.setLabel("GPA");

        final LineChart<String, Number> avgGPA =
                new LineChart<String, Number>(xAxis, yAxis);

        avgGPA.setTitle("Professor GPA in Recent Years");

        XYChart.Series GPAArray = new XYChart.Series();
        GPAArray.setName("Average GPA");

        //TODO: create for loop to go through and insert the data into the graph

        avgGPA.getData().add(GPAArray);

        return avgGPA;
    }
}


/*
public class LineChartSample extends Application {

    @Override public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        //populating the series with data
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
 */