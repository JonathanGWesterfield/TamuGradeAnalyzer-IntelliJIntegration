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
import javafx.util.*;
import javafx.animation.*;
import javafx.scene.text.*;
import javafx.beans.value.*;
import javafx.beans.*;
import javafx.geometry.*;


import java.sql.SQLException;
import java.util.*;

public class GradeBarChart extends Application
{

    public static void main(String[] args)
    {
        launch(args);

    }

    public GradeBarChart()
    {
        // empty default constructor
    }


    @Override public void start(Stage primaryStage)
    {
        try
        {
            DatabaseAPI db = new DatabaseAPI();

            BarChart<String, Number> bc = GradeBarChart.gradeBarChart("CSCE", 121, "MOORE", db);

            Scene scene = new Scene(bc, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
            System.out.println("The DatabaseAPI class could not be found, thus the database\n");
        }
    }
    // creates a bar chart and returns a Bar chart to add to the scene/stage
    public static BarChart<String, Number> gradeBarChart(String courseSubject, int courseNum,
                                                         String professor, DatabaseAPI db) throws SQLException
    {
        final String numA = "A's";
        final String numB = "B's";
        final String numC = "C's";
        final String numD = "D's";
        final String numF = "F's";
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

        // creates the graph array
        BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);

        bc.setTitle("Grade Percentages"); // sets the title of the graph

        // sets the graph labels
        xAxis.setLabel("Grades");
        yAxis.setLabel(" Percentage of Total Grades");

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


     /*for(Node n:bc.lookupAll(".default-color0.chart-bar")) {
          n.setStyle("-fx-bar-fill: maroon;");
        }

        for(Node n:bc.lookupAll(".default-color1.chart-bar")) {
          n.setStyle("-fx-bar-fill: green;");
        }

        // possible animation code for the barchart
        // must be put inside the barchart function

        Timeline tl = new Timeline();
        tl.getKeyFrames().add(
                new KeyFrame(Duration.millis(500),
                        new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent actionEvent) {
                                for (XYChart.Series<String, Number> grades : bc.getData()) {
                                    for (XYChart.Data<String, Number> data : grades.getData()) {
                                        data.setYValue(Math.random() * 1000);
                                    }
                                }
                            }
                        }
                ));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.setAutoReverse(true);
        tl.play();
        xAxis.setAnimated(false);
        */
}
