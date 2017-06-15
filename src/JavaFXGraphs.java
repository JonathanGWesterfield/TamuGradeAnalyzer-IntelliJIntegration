/**
 * Created by jonathanw on 6/13/17.
 */

import javafx.application.*;
import javax.swing.JOptionPane;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.event.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

import java.sql.SQLException;
import java.util.*;

public class JavaFXGraphs
{

    // creates a bar chart and returns a Bar chart to add to the scene/stage
    public static BarChart<String, Number> gradeBarChart(String courseSubject, int courseNum, String professor) throws SQLException
    {
        final String numA = "A's";
        final String numB = "B's";
        final String numC = "C's";
        final String numD = "D's";
        final String numF = "F's";
        final String numQDrop = "Q Drops";

        try
        {
            DatabaseAPI db = new DatabaseAPI();

            // creates the axis of the graph
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();

            // creates the graph array
            final BarChart<String,Number> bc =
                    new BarChart<String,Number>(xAxis,yAxis);
            bc.setTitle("Grade Percentages"); // sets the title of the graph

            // sets the graph labels
            xAxis.setLabel("Grades");
            yAxis.setLabel("Percentage of Total Grades");

            XYChart.Series grades = new XYChart.Series();
            // grades.setName("Percentage of the total"); commented out because it is redundant

            grades.getData().add(new XYChart.Data<>(numA, db.getPercentA(courseSubject, courseNum, professor)));
            grades.getData().add(new XYChart.Data<>(numB, db.getPercentB(courseSubject, courseNum, professor)));
            grades.getData().add(new XYChart.Data<>(numC, db.getPercentC(courseSubject, courseNum, professor)));
            grades.getData().add(new XYChart.Data<>(numD, db.getPercentD(courseSubject, courseNum, professor)));
            grades.getData().add(new XYChart.Data<>(numF, db.getPercentF(courseSubject, courseNum, professor)));
            grades.getData().add(new XYChart.Data<>(numQDrop, db.getPercentQDrops(courseSubject, courseNum, professor)));

            bc.getData().addAll(grades);

            return bc;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("The DatabaseAPI class could not be found, thus the database\n" +
                    "could not be established.\n\n");
        }



    }



}
