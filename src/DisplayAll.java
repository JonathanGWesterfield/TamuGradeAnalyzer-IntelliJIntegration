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
import javafx.concurrent.*;
import javax.xml.crypto.Data;
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
    private static DatabaseAPI db;
    private Stage primaryStage;
    private GridPane listPane;
    private GridPane grid;
    private Scene scene;
    private BorderPane pane;
    private Stage stage;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override public void start(Stage primaryStage)
    {
        try
        {
            stage = primaryStage;
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
        setPrimaryStage();
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

    // sets the action for the generate button
    public void setGenerate()
    {
        generate = new Button("Generate Report");
        generate.setOnAction(e -> createNewDBConn());
    }

    public void showPrimaryStage()
    {
        primaryStage.show();
    }

    private void setPrimaryStage()
    {
        try
        {
            primaryStage = new Stage(StageStyle.DECORATED);
            this.primaryStage = primaryStage;

            // Supposedly changes the scene Icon
            this.primaryStage.getIcons().add(new Image(new FileInputStream(
                    "resources/Calligraphy J.png")));

            this.primaryStage.setScene(scene);
            this.primaryStage.setTitle("Texas A&M Professor Grade Analyzer");

            // declares the procedure for the exit button
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
        return;
    }

    // sets up the empty scene when it is first opened
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

            // sets the vertical and horizontal alignment for the Bar chart
            GridPane.setHalignment(gradeChart.getBarChart(), HPos.CENTER);
            GridPane.setValignment(gradeChart.getBarChart(), VPos.CENTER);
            grid.add(gradeChart.getBarChart(),0,3);

            // sets the vertical and horizontal alignment for the Line Chart
            GridPane.setHalignment(gradeChart.getLineChart(), HPos.CENTER);
            GridPane.setValignment(gradeChart.getLineChart(), VPos.CENTER);
            grid.add(gradeChart.getLineChart(), 0, 6);

            grid.add(displayData.getPercentagesDisplay(),1, 3);

            // tries to set the alignment for the number of A's, B's etc.
            GridPane.setValignment(displayData.getTotalGrades(), VPos.BOTTOM);
            grid.add(displayData.getTotalGrades(), 1, 6);

            // sets the vertical and horizontal alignment for the GPA average
            GridPane.setHalignment(displayData.getAvgGPA(), HPos.CENTER);
            GridPane.setValignment(displayData.getAvgGPA(), VPos.BOTTOM);
            grid.add(displayData.getAvgGPA(), 1, 3);

            // sets the vertical and horizontal alignment for J image
            GridPane.setHalignment(screen.getCalligraphyJ(), HPos.CENTER);
            GridPane.setValignment(screen.getCalligraphyJ(), VPos.CENTER);
            grid.add(screen.getCalligraphyJ(), 3,3);

            // sets the vertical and horizontal alignment for Tamu Seal
            GridPane.setHalignment(screen.getTamuSeal(), HPos.CENTER);
            GridPane.setValignment(screen.getTamuSeal(), VPos.CENTER);
            grid.add(screen.getTamuSeal(), 3,6);

            pane.setCenter(grid);
            // pane.setBackground(new Background(backgroundImage));

            scene = new Scene(pane, 980, 825);
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

        System.out.println("New Database Connection created");
        System.out.println("Data for the charts has been updated");

        refreshData();

        scene = null;

        scene = new Scene(pane, 980, 825);

        return;
    }

    public void refreshData()
    {
        setDropListPane();

        // TODO: This is the part that actaully takes the longest so put the progress
        // indicator
        grid.getChildren().clear();

        // sets the vertical and horizontal alignment for the Bar chart
        GridPane.setHalignment(gradeChart.getBarChart(), HPos.CENTER);
        GridPane.setValignment(gradeChart.getBarChart(), VPos.CENTER);
        grid.add(gradeChart.getBarChart(),0,3);

        // sets the vertical and horizontal alignment for the Line Chart
        GridPane.setHalignment(gradeChart.getLineChart(), HPos.CENTER);
        GridPane.setValignment(gradeChart.getLineChart(), VPos.CENTER);
        grid.add(gradeChart.getLineChart(), 0, 6);

        grid.add(displayData.getPercentagesDisplay(),1, 3);

        // tries to set the alignment for the number of A's, B's etc.
        GridPane.setValignment(displayData.getTotalGrades(), VPos.BOTTOM);
        grid.add(displayData.getTotalGrades(), 1, 6);

        // sets the vertical and horizontal alignment for the GPA average
        GridPane.setHalignment(displayData.getAvgGPA(), HPos.CENTER);
        GridPane.setValignment(displayData.getAvgGPA(), VPos.BOTTOM);
        grid.add(displayData.getAvgGPA(), 1, 3);

        // sets the vertical and horizontal alignment for J image
        GridPane.setHalignment(screen.getCalligraphyJ(), HPos.CENTER);
        GridPane.setValignment(screen.getCalligraphyJ(), VPos.CENTER);
        grid.add(screen.getCalligraphyJ(), 3,3);

        // sets the vertical and horizontal alignment for Tamu Seal
        GridPane.setHalignment(screen.getTamuSeal(), HPos.CENTER);
        GridPane.setValignment(screen.getTamuSeal(), VPos.CENTER);
        grid.add(screen.getTamuSeal(), 3,6);

        pane.setCenter(grid);
        // pane.setBackground(new Background(backgroundImage));

        return;
    }

    // TODO: figure out how to fix the refresh screen bug
    private void createNewDBConn()
    {
        ProgressIndicator progress = new ProgressIndicator();
        progress.setMinSize(200, 200);
        progress.setMaxSize(200, 200);

        Label progresslbl1 = new Label("Currently Generating Report");
        progresslbl1.setFont(new Font("Futura", 20));
        Label progresslbl2 = new Label("Please wait shortly");
        progresslbl2.setFont(new Font("Futura", 20));

        VBox updatePane = new VBox(10);
        updatePane.setPadding(new Insets(15, 15, 15, 15));
        updatePane.getChildren().addAll(progress, progresslbl1, progresslbl2);
        updatePane.setAlignment(Pos.CENTER); // centers everything

        Stage taskUpdateStage = new Stage(StageStyle.TRANSPARENT);
        taskUpdateStage.setScene(new Scene(updatePane));
        taskUpdateStage.show();

        // creates a separate thread to set all of the new data
        Task<Void> createnewDB = new Task<Void>()
        {
            @Override
            protected Void call() throws SQLException, ClassNotFoundException
            {
                DatabaseAPI newDB = new DatabaseAPI(dropList.getChosenSubject(),
                        dropList.getChosenCourseNum(), dropList.getChosenProfessor());

                // an attempt to clear these fields
                displayData = null;
                gradeChart = null;

                displayData = new DisplayData(newDB, false);
                gradeChart = new GradeChart(newDB, false);

                return null;
            }
        };

        // I think this makes the progress indicator indeterminate?
        progress.progressProperty().bind(createnewDB.progressProperty());

        taskUpdateStage.show();

        // starts the task that was just created
        Thread createThread = new Thread(createnewDB);
        createThread.setDaemon(true);
        createThread.start();


        // for if the connection is created
        // should just get rid of the progress window
        createnewDB.setOnSucceeded(new EventHandler<WorkerStateEvent>()
        {
            @Override
            public void handle(WorkerStateEvent event)
            {
                taskUpdateStage.hide();
                // had to put the refresh call here because it wouldn't update
                // the screen otherwise
                refreshScreen();
            }
        });

        // procedure for if creating the new connection fails
        // it should quit the application
        createnewDB.setOnFailed(new EventHandler<WorkerStateEvent>()
        {
            @Override
            public void handle(WorkerStateEvent event)
            {
                AlertError.showSQLException();
                exit();
            }
        });

        return;
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
