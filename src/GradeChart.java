/**
 * Created by jonathanw on 6/16/17.
 */

import com.sun.javaws.jnl.LibraryDesc;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import java.sql.*;
import java.util.*;


public class GradeChart extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override public void start(Stage primaryStage)
    {
        try
        {
            DatabaseAPI db = new DatabaseAPI();
            BarChart grades = GradeChart.gradeBarChart("CSCE", 121, "MOORE", db);
            Scene scene = new Scene(grades, 350, 305);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            System.out.println("Could not load something and this is broke");
        }
    }

    public GradeChart()
    {
        //Default empty constructor
    }

    public static LineChart<String, Number> LineAvgGPA(String courseSubject, int courseNum, String professor,
                                                       DatabaseAPI db) throws SQLException
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

    //TODO: store percentage info in this class so database doesn't have to be called again
    // also add the passing percentage of the class

    // Would probably work best if the barchart was 350x350 or 350x305
    // creates a bar chart with the percentage of A's, B's, C's etc.
    public static BarChart<String, Number> gradeBarChart(String courseSubject, int courseNum,
                                                         String professor, DatabaseAPI db) throws SQLException
    {
        final String numA = "A";
        final String numB = "B";
        final String numC = "C";
        final String numD = "D";
        final String numF = "F";
        final String numQDrop = "Q Drops";

        double percentA = db.getPercentA(courseSubject, courseNum, professor);
        double percentB = db.getPercentB(courseSubject, courseNum, professor);
        double percentC = db.getPercentC(courseSubject, courseNum, professor);
        double percentD = db.getPercentD(courseSubject, courseNum, professor);
        double percentF = db.getPercentF(courseSubject, courseNum, professor);
        double percentQDrop = db.getPercentQDrops(courseSubject, courseNum, professor);

        // creates the axis of the graph
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        CustomStackedBarChart custStacked = new CustomStackedBarChart(xAxis, yAxis);

        // creates the graph array
        BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);

        bc.setTitle("Grade Percentages"); // sets the title of the graph

        // sets the graph labels
        xAxis.setLabel("Grades");
        yAxis.setLabel(" Percentage of Total Grades ");

        XYChart.Series grades = new XYChart.Series();

        grades.setName("Grade Percentages"); // was commented out because it is redundant

        XYChart.Data dataA = new XYChart.Data(numA, percentA);
        // JavaFXGraphs.displayLabelForData(dataA);

        XYChart.Data dataB = new XYChart.Data(numB, percentB);
        // JavaFXGraphs.displayLabelForData(dataB);

        XYChart.Data dataC = new XYChart.Data(numC, percentC);
        // JavaFXGraphs.displayLabelForData(dataC);

        XYChart.Data dataD = new XYChart.Data(numD, percentD);
        // JavaFXGraphs.displayLabelForData(dataD);

        XYChart.Data dataF = new XYChart.Data(numF, percentF);
        // JavaFXGraphs.displayLabelForData(dataF);

        XYChart.Data dataQ = new XYChart.Data(numQDrop, percentQDrop);
        // JavaFXGraphs.displayLabelForData(dataQ);


        grades.getData().add(dataA);
        grades.getData().add(dataB);
        grades.getData().add(dataC);
        grades.getData().add(dataD);
        grades.getData().add(dataF);
        grades.getData().add(dataQ);




        bc.getData().add(grades);

        return bc;
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
}
