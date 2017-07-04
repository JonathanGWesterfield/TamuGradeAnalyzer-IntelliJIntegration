/**
 * Created by jonathanw on 6/16/17.
 */

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.control.*;
import javafx.animation.*;
import javafx.scene.text.*;
import javafx.util.Duration;
import javafx.stage.Stage;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;
import javafx.event.*;

public class GradeChart extends Application
{
    private DatabaseAPI db;

    private LineChart<String, Number> lineChart;
    private BarChart<String, Number> barChart;

    private LineChart<String, Number> emptyLineChart;
    private BarChart<String, Number> emptyBarChart;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override public void start(Stage primaryStage)
    {
        try
        {
            DatabaseAPI db = new DatabaseAPI("CSCE", 121, "MOORE");

            GradeChart charts = new GradeChart(db, true);

            LineChart line = charts.getLineChart();

            GridPane grid = new GridPane();
            grid.add(charts.getEmptyLineChart(), 3, 0);


            BarChart grades = charts.getBarChart();

            // Scene scene = new Scene(grades, 305, 305);

            // Scene scene = new Scene(charts.getPercentagesDisplay(), 185, 208);

            Scene scene = new Scene(grid, 600, 600);

            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            System.out.println("Could not load something and this is broke");
            e.printStackTrace();
        }
    }

    //TODO: Possibly add a raw data function using a read only function
    public GradeChart()
    {
        // default empty constructor
    }

    //WARNING: using the wrong boolean value may result in a nullpointer exception
    // constructor for this class the takes in all the data
    public GradeChart(DatabaseAPI db, boolean isempty) throws SQLException, ClassNotFoundException
    {
        if(isempty)
        {
            setEmptyLineAvgGPA();
            setEmptyBarChart();
        }
        this.db = db;

        setLineAvgGPA();
        setGradeBarChart();

    }

    // class getters
    public LineChart<String, Number> getLineChart()
    {
        return lineChart;
    }

    public BarChart<String, Number> getBarChart()
    {
        return barChart;
    }

    public LineChart<String, Number> getEmptyLineChart()
    {
        return emptyLineChart;
    }

    public BarChart<String, Number> getEmptyBarChart()
    {
        return emptyBarChart;
    }

    private void setEmptyLineAvgGPA()
    {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis("GPA", 0f, 4f, .25);

        emptyLineChart = new LineChart<String, Number>(xAxis, yAxis);

        emptyLineChart.setAnimated(true);

        emptyLineChart.setTitle("Professor GPA in Recent Years");

        XYChart.Series GPAArray = new XYChart.Series();
        GPAArray.setName("Average GPA");

        GPAArray.getData().add(new XYChart.Data("No Professor Chosen", 0));

        // changes the color of the line on the graph to maroon
        emptyLineChart.setStyle("CHART_COLOR_1: #800000"); //#228B22;");

        emptyLineChart.getData().add(GPAArray);

        // attempts to sets the dimensions of the barchart
        emptyLineChart.setMaxHeight(500);
        emptyLineChart.setMaxWidth(550);

        emptyLineChart.setMinHeight(305);
        emptyLineChart.setMinWidth(350);


        emptyLineChart.setPrefHeight(305);
        emptyLineChart.setPrefWidth(350);

        return;
    }

    private void setLineAvgGPA() throws SQLException
    {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis("GPA", 0f, 4f, .25);

        xAxis.setLabel("Semester");
        // yAxis.setLabel("GPA"); redundant line since constructor takes care of this

        lineChart = new LineChart<String, Number>(xAxis, yAxis);

        lineChart.setAnimated(true);

        lineChart.setTitle("Professor GPA in Recent Years");

        XYChart.Series GPAArray = new XYChart.Series();
        GPAArray.setName("Average GPA");

        ArrayList<Double> GPAList = db.getPastSemesterGPAs(db.getCourseSubject(), db.getCourseNum(), db.getProfessor());
        ArrayList<String> pastSemesters = db.getPastSemesters(db.getCourseSubject(), db.getCourseNum(), db.getProfessor());

        for(int i = 0; i < GPAList.size(); i++)
        {
            GPAArray.getData().add(new XYChart.Data(pastSemesters.get(i), GPAList.get(i)));
        }

        // changes the color of the line on the graph to maroon
        lineChart.setStyle("CHART_COLOR_1: #800000"); //#228B22;");

        lineChart.getData().add(GPAArray);

        // attempts to sets the dimensions of the barchart
        lineChart.setMaxHeight(500);
        lineChart.setMaxWidth(550);

        lineChart.setMinHeight(305);
        lineChart.setMinWidth(350);


        lineChart.setPrefHeight(305);
        lineChart.setPrefWidth(350);

        return;
    }

    private void setEmptyBarChart()
    {
        final String numA = "A";
        final String numB = "B";
        final String numC = "C";
        final String numD = "D";
        final String numF = "F";
        final String numQDrop = "Q Drops";

        // creates the axis of the graph
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        // creates the graph array
        emptyBarChart = new BarChart<String,Number>(xAxis,yAxis);

        emptyBarChart.setTitle("Grade Percentages"); // sets the title of the graph

        // sets the graph labels
        xAxis.setLabel("Grades");
        yAxis.setLabel(" Percentage of Total Grades ");

        XYChart.Series grades = new XYChart.Series();

        grades.setName("Grade Percentages"); // was commented out because it is redundant

        // adds the data to the chart
        XYChart.Data dataA = new XYChart.Data(numA, 0);
        XYChart.Data dataB = new XYChart.Data(numB, 0);
        XYChart.Data dataC = new XYChart.Data(numC, 0);
        XYChart.Data dataD = new XYChart.Data(numD, 0);
        XYChart.Data dataF = new XYChart.Data(numF, 0);
        XYChart.Data dataQ = new XYChart.Data(numQDrop, 0);

        // adds the inputs to the bar graph to be displayed
        grades.getData().add(dataA);
        grades.getData().add(dataB);
        grades.getData().add(dataC);
        grades.getData().add(dataD);
        grades.getData().add(dataF);
        grades.getData().add(dataQ);

        emptyBarChart.setStyle("CHART_COLOR_1: #800000;");

        emptyBarChart.getData().add(grades);

        //sets the size of the chart
        emptyBarChart.setMaxHeight(500);
        emptyBarChart.setMaxWidth(500);

        emptyBarChart.setMinHeight(250);
        emptyBarChart.setMinWidth(250);

        emptyBarChart.setPrefHeight(350);
        emptyBarChart.setPrefWidth(350);

        return;
    }

    // also add the passing percentage of the class

    // Would probably work best if the barchart was 350x350
    // creates a bar chart with the percentage of A's, B's, C's etc.
    private void setGradeBarChart() throws SQLException
    {
        final String numA = "A";
        final String numB = "B";
        final String numC = "C";
        final String numD = "D";
        final String numF = "F";
        final String numQDrop = "Q Drops";

        // creates the axis of the graph
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        // creates the graph array
        barChart = new BarChart<String,Number>(xAxis,yAxis);

        barChart.setTitle("Grade Percentages"); // sets the title of the graph

        // sets the graph labels
        xAxis.setLabel("Grades");
        yAxis.setLabel(" Percentage of Total Grades ");

        XYChart.Series grades = new XYChart.Series();

        grades.setName("Grade Percentages"); // was commented out because it is redundant

        // adds the data to the chart
        XYChart.Data dataA = new XYChart.Data(numA, db.getPercentageA());
        XYChart.Data dataB = new XYChart.Data(numB, db.getPercentageB());
        XYChart.Data dataC = new XYChart.Data(numC, db.getPercentageC());
        XYChart.Data dataD = new XYChart.Data(numD, db.getPercentageD());
        XYChart.Data dataF = new XYChart.Data(numF, db.getPercentageF());
        XYChart.Data dataQ = new XYChart.Data(numQDrop, db.getPercentageQ());

        // adds the inputs to the bar graph to be displayed
        grades.getData().add(dataA);
        grades.getData().add(dataB);
        grades.getData().add(dataC);
        grades.getData().add(dataD);
        grades.getData().add(dataF);
        grades.getData().add(dataQ);

        barChart.setStyle("CHART_COLOR_1: #800000;");

        barChart.getData().add(grades);

        //sets the size of the chart
        barChart.setMaxHeight(500);
        barChart.setMaxWidth(500);

        barChart.setMinHeight(250);
        barChart.setMinWidth(250);

        barChart.setPrefHeight(350);
        barChart.setPrefWidth(350);

        return;
    }













    /* This code was to put the value of the data over the bars of the bar graph
        but it generates a nullpointerexception

    private static void displayLabelForData(XYChart.Data<String, Number> data)
    {
        final Node node = data.getNode();
        final Text dataText = new Text(data.getYValue() + "");
        node.parentProperty().addListener(new ChangeListener<Parent>()
        {
            @Override public void changed(ObservableValue<? extends Parent> ov, Parent oldParent, Parent parent)
            {
                Group parentGroup = (Group) parent;
                parentGroup.getChildren().add(dataText);
            }
        });

        node.boundsInParentProperty().addListener(new ChangeListener<Bounds>()
        {
            @Override public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds)
            {
                dataText.setLayoutX(
                        Math.round(
                                bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
                        )
                );
                dataText.setLayoutY(
                        Math.round(
                                bounds.getMinY() - dataText.prefHeight(-1) * 0.5
                        )
                );
            }
        });
    }*/

    /* Attempts to animate the bar graph function
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new EventHandler<ActionEvent>()
                        {
                            @Override public void handle(ActionEvent actionEvent)
                            {
                                plot();
                            }
                        }
                ),
                new KeyFrame(
                        Duration.seconds(1)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        Timeline tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(500),
                new EventHandler<ActionEvent>()
                {
                    @Override public void handle(ActionEvent actionEvent)
                    {
                        for (XYChart.Series<String, Number> series : barChart.getData())
                        {
                            for (XYChart.Data<String, Number> data : series.getData())
                            {
                                data.setYValue(Math.random() * 100);
                            }
                        }
                    }
                }));
        tl.setCycleCount(1000);
        tl.play();*/
}
