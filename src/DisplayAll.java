/**
 * Created by jonathanw on 7/3/17.
 */

import javafx.scene.control.*;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.text.*;
import javafx.application.*;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;


/** This class ties in all of the components from the GradeChart, DisplayData, and
 * DropDownLists class and puts them all onto one grid pane to be displayed in the scene
 */

    // once a professor is chosen/ MAY HAVE TO MOVE THE ACTIONEVENT FROM THE DROPDOWN CLASS TO THIS ONE
public class DisplayAll extends Application
{
    private DropDownList dropList;
    private DisplayData displayData;
    private GradeChart gradeChart;
    private Button generate;
    private LoadScreen screen;
    private DatabaseAPI db;
    private Stage primaryStage;
    private GridPane listPane;
    private GridPane grid;
    private Scene scene;
    private BorderPane pane;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override public void start(Stage primaryStage)
    {
        try
        {
            this.primaryStage = primaryStage;
            DatabaseAPI db = new DatabaseAPI();
            DisplayAll display = new DisplayAll(db);

            // Supposedly changes the scene Icon
            this.primaryStage.getIcons().add(new Image(new FileInputStream("resources/Calligraphy J.png")));

            this.primaryStage.setScene(display.getScene());
            this.primaryStage.setTitle("Texas A&M Professor Grade Analyzer");

            this.primaryStage.setOnCloseRequest(e ->
            {
                e.consume();
                exit();
            });

            this.primaryStage.show();

            //TODO: fix the average GPA label
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            AlertError.showImageNotFound();
        }
        catch(SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
            AlertError.showSQLException();
        }
    }

    // default constructor
    public DisplayAll()
    {
        System.out.println("Default constructor has been called");
    }


    //TODO: fix how the DBAPI is called twice in the beginning

    // constructor
    public DisplayAll(DatabaseAPI db)
    {
        this.db = db;
        setGrid();
    }

    // public getter functions
    public Scene getScene()
    {
        return scene;
    }

    public Button getGenerateButton()
    {
        return generate;
    }

    public void setGenerate()
    {
        generate = new Button("Generate Report");
        generate.setOnAction(e -> refreshScreen());

    }

    public void setGrid()
    {
        try
        {
            grid = new GridPane();
            grid.setVgap(4);
            grid.setHgap(10);
            grid.setPadding(new Insets(5,3,5,3));

            dropList = new DropDownList(this.db);

            setGenerate();

            displayData = new DisplayData(dropList.getReturndbAPI(), true);
            gradeChart = new GradeChart(dropList.getReturndbAPI(), true);
            screen = new LoadScreen();
            setEmptyDropListPane();

            grid.add(gradeChart.getBarChart(),0,3);
            grid.add(gradeChart.getLineChart(), 0, 6);
            grid.add(displayData.getPercentagesDisplay(),1, 3);
            grid.add(displayData.getTotalGrades(), 1, 6);
            grid.add(displayData.getAvgGPA(), 1, 4);
            grid.add(screen.getCalligraphyJ(), 3,3);
            grid.add(screen.getTamuSeal(), 3,6);

            pane.setCenter(grid);
            // pane.setBackground(new Background(backgroundImage));

            scene = new Scene(pane, 925, 850);
        }
        //TODO: fix runtime exception in the display all after generating report
        catch(SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
            AlertError.showSQLException();
        }
    }

    public void exit()
    {
        if (!AlertError.confirmExit()) // makes sure user actually wants to quit
        {
            System.out.println("Exit aborted");
            return;
        }

        this.db = null; // closes connection by destroying the class
        System.out.println("\n\nDatabase connection in DisplayAll class closed");

        // System.out.println("Database connection not closed");

        System.out.println("Closing application");
        this.primaryStage.close(); // closes application window
    }

    public void refreshScreen()
    {
        try
        {
            if(dropList.getChosenSubject() == null)
            {
                AlertError.showNeedChooseSubject();
                return;
            }
            else if(dropList.getChosenCourseNum() == 0)
            {
                AlertError.showNeedChooseCourseNum();
                return;
            }
            else if(dropList.getChosenProfessor() == null)
            {
                AlertError.showNeedChooseProfessor();
                return;
            }

            grid = new GridPane();

            DatabaseAPI newDB = dropList.getReturndbAPI();
            displayData = new DisplayData(newDB, false);
            gradeChart = new GradeChart(newDB, false);

            setDropListPane();

            grid.getChildren().clear();
            grid.add(gradeChart.getBarChart(),0,3);
            grid.add(gradeChart.getLineChart(), 0, 6);
            grid.add(displayData.getPercentagesDisplay(),1, 3);
            grid.add(displayData.getTotalGrades(), 1, 6);
            grid.add(displayData.getAvgGPA(), 1, 4);
            // grid.add(generate, 3,0);
            grid.add(screen.getCalligraphyJ(), 3,3);
            grid.add(screen.getTamuSeal(), 3,6);
            pane.setCenter(grid);

            scene = null;

            scene = new Scene(pane, 850, 800);

            return;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            AlertError.showSQLException();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            AlertError.showClassNotFoundException();
        }
    }

    private void setEmptyDropListPane()
    {
        listPane = new GridPane();

        listPane.setVgap(4);
        listPane.setHgap(10);
        listPane.setPadding(new Insets(5,3,5,5));
        listPane.add(dropList.getChooseSubject(), 0, 0);
        listPane.add(dropList.getChooseCourse(), 1, 0);
        listPane.add(dropList.getChooseProfessor(), 2,0);
        listPane.add(generate, 3, 0);
        listPane.add(displayData.getCourseInfo(), 4, 0);


        pane = new BorderPane();
        pane.setTop(listPane);
        pane.setPadding(new Insets(5, 5, 5, 5));
    }

    private void setDropListPane()
    {
        listPane.getChildren().clear();

        listPane.setVgap(4);
        listPane.setHgap(10);
        listPane.setPadding(new Insets(5,3,5,5));
        listPane.add(dropList.getChooseSubject(), 0, 0);
        listPane.add(dropList.getChooseCourse(), 1, 0);
        listPane.add(dropList.getChooseProfessor(), 2,0);
        listPane.add(generate, 3, 0);
        listPane.add(displayData.getCourseInfo(), 4,0);

        pane.getChildren().clear();
        pane.setTop(listPane);
        pane.setPadding(new Insets(5, 5, 5, 5));

    }
}
