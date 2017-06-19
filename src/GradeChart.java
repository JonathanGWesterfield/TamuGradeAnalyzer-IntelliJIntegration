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
    private double percentA;
    private double percentB;
    private double percentC;
    private double percentD;
    private double percentF;
    private double percentQDrop;

    private String courseSubject;
    private String professor;
    private int courseNum;

    private DatabaseAPI db;

    private VBox percentagesDisplay;
    private LineChart<String, Number> lineChart;
    private BarChart<String, Number> barChart;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override public void start(Stage primaryStage)
    {
        try
        {
            DatabaseAPI db = new DatabaseAPI();


            GradeChart charts = new GradeChart("CSCE", 121, "MOORE", db);
            BarChart grades = charts.getBarChart();

            //HBox percentages = charts.getPercentagesDisplay();

            Scene scene = new Scene(grades, 305, 305);

            // Scene scene = new Scene(charts.getPercentagesDisplay(), 185, 208);

            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            System.out.println("Could not load something and this is broke");
            e.printStackTrace();
        }
    }

    public GradeChart()
    {
        // default empty constructor
    }

    // constructor for this class the takes in all the data
    public GradeChart(String courseSubject, int courseNum, String professor, DatabaseAPI db) throws SQLException, ClassNotFoundException
    {
        this.db = db;
        this.courseSubject = courseSubject;
        this.courseNum = courseNum;
        this.professor = professor;
        this.percentA = db.getPercentA(courseSubject, courseNum, professor);
        this.percentB = db.getPercentB(courseSubject, courseNum, professor);
        this.percentC = db.getPercentC(courseSubject, courseNum, professor);
        this.percentD = db.getPercentD(courseSubject, courseNum, professor);
        this.percentF = db.getPercentF(courseSubject, courseNum, professor);
        this.percentQDrop = db.getPercentQDrops(courseSubject, courseNum, professor);

        this.lineChart = LineAvgGPA();
        this.barChart = gradeBarChart();
        this.percentagesDisplay = displayPercentages();


    }

    // class getters
    public double getPercentA() { return percentA; }
    public double getPercentB() { return percentB; }
    public double getPercentC() { return percentC; }
    public double getPercentD() { return percentD; }
    public double getPercentF() { return percentF; }
    public double getPercentQDrop() { return percentQDrop; }
    public VBox getPercentagesDisplay() { return percentagesDisplay; }
    public LineChart<String, Number> getLineChart() { return lineChart; }
    public BarChart<String, Number> getBarChart() { return barChart; }


    // is static because all of its information is based off of the databaseAPI so it stores nothing
    public LineChart<String, Number> LineAvgGPA() throws SQLException
    {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis("GPA", 0f, 4f, .25);

        xAxis.setLabel("Semester");
        // yAxis.setLabel("GPA"); redundant line since constructor takes care of this

        final LineChart<String, Number> avgGPA =
                new LineChart<String, Number>(xAxis, yAxis);

        avgGPA.setAnimated(true);

        avgGPA.setTitle("Professor GPA in Recent Years");

        XYChart.Series GPAArray = new XYChart.Series();
        GPAArray.setName("Average GPA");

        ArrayList<Double> GPAList = db.getPastSemesterGPAs(courseSubject,courseNum, professor);
        ArrayList<String> pastSemesters = db.getPastSemesters(courseSubject,courseNum, professor);

        for(int i = 0; i < GPAList.size(); i++)
        {
            GPAArray.getData().add(new XYChart.Data(pastSemesters.get(i), GPAList.get(i)));
        }

        avgGPA.getData().add(GPAArray);

        return avgGPA;
    }

    // also add the passing percentage of the class

    // Would probably work best if the barchart was 350x350
    // creates a bar chart with the percentage of A's, B's, C's etc.
    public BarChart<String, Number> gradeBarChart() throws SQLException
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
        BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);

        bc.setTitle("Grade Percentages"); // sets the title of the graph

        // sets the graph labels
        xAxis.setLabel("Grades");
        yAxis.setLabel(" Percentage of Total Grades ");

        XYChart.Series grades = new XYChart.Series();

        grades.setName("Grade Percentages"); // was commented out because it is redundant

        // adds the data to the chart
        XYChart.Data dataA = new XYChart.Data(numA, percentA);
        XYChart.Data dataB = new XYChart.Data(numB, percentB);
        XYChart.Data dataC = new XYChart.Data(numC, percentC);
        XYChart.Data dataD = new XYChart.Data(numD, percentD);
        XYChart.Data dataF = new XYChart.Data(numF, percentF);
        XYChart.Data dataQ = new XYChart.Data(numQDrop, percentQDrop);

        // adds the inputs to the bar graph to be displayed
        grades.getData().add(dataA);
        grades.getData().add(dataB);
        grades.getData().add(dataC);
        grades.getData().add(dataD);
        grades.getData().add(dataF);
        grades.getData().add(dataQ);

        bc.getData().add(grades);

        return bc;
    }

    // displays all of the percentages for the course. Needs to be 185x208
    public VBox displayPercentages()
    {
        VBox percentages = new VBox(10);
        percentages.setPadding(new Insets(15, 12,15,12));
        DecimalFormat df = new DecimalFormat("##.0");

        double percentPassing = percentA + percentB + percentC;

        Label perA = new Label("Percent A:  " + df.format(getPercentA()) + "%");
        Label perB = new Label("Percent B:  " + df.format(getPercentB()) + "%");
        Label perC = new Label("Percent C:  " + df.format(getPercentC()) + "%");
        Label perD = new Label("Percent D:  " + df.format(getPercentD()) + "%");
        Label perF = new Label("Percent F:  " + df.format(getPercentF()) + "%");
        Label perQ = new Label("Percent Q Drops:  " + df.format(getPercentQDrop()) + "%");
        Label perPass = new Label("Passing Rate:  " + df.format(percentPassing) + "%");

        percentages.getChildren().addAll(perA, perB, perC, perD, perF, perQ, perPass);

        return percentages;
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
                        for (XYChart.Series<String, Number> series : bc.getData())
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
