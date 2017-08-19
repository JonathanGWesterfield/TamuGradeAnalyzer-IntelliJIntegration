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
    private DatabaseAPI db;
    Stage primaryStage;
    private GridPane listPane;
    private GridPane grid;
    private Scene scene;
    private BorderPane pane;
    private Stage stage;
    private ImageView calligraphyJ;
    private ImageView tamuSeal;

    public static void main(String[] args)
    {
        launch(args);
    }

    /**
     * Main function for testing
     *
     * @param primaryStage
     */
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

            /*DatabaseAPI db = new DatabaseAPI();
            DisplayAll newDisplay = new DisplayAll();
            // newDisplay.showPrimaryStage();
            primaryStage = newDisplay.primaryStage;
            primaryStage.show();*/
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

    public DisplayAll()
    {
        System.out.println("Default constructor has been called");
    }

    //TODO: fix how the DBAPI is called twice in the beginning

    /**
     * Constructor sets up the grid and creates the stage to be used by another class
     *
     * @param db
     */
    public DisplayAll(DatabaseAPI db)
    {
        setCalligraphyJ();
        setTamuSeal();
        this.db = db;
        setGrid();
        setPrimaryStage();
    }

    /**
     * public getter functions
     *
     * @return
     */
    public Scene getScene()
    {
        return scene;
    }

    public Button getGenerateButton()
    {
        return generate;
    }

    /**
     * Public getter for the stage of this class
     */
    public void showPrimaryStage()
    {
        primaryStage.show();
    }

    /**
     * hides the primary stage
     */
    public void hidePrimaryStage()
    {
        primaryStage.hide();
    }

    /**
     * sets the action event for the generate button
     */
    public void setGenerate()
    {
        generate = new Button("Generate Report");
        generate.setOnAction(e -> createNewDBConn());
    }

    /**
     * Sets up the primary stage to be used by the other classes.
     * Holds all of the components of this class on the primaryStage
     */
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
            return;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            AlertError.showImageNotFound();
        }
        return;
    }

    /**
     * Sets up the empty scene when it is first opened
     */
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

            displayData = new DisplayData(this.db, true);
            gradeChart = new GradeChart(this.db, true);
            // screen = new LoadScreen();
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
            GridPane.setHalignment(calligraphyJ, HPos.CENTER);
            GridPane.setValignment(calligraphyJ, VPos.CENTER);
            grid.add(calligraphyJ, 3,3);

            // sets the vertical and horizontal alignment for Tamu Seal
            GridPane.setHalignment(tamuSeal, HPos.CENTER);
            GridPane.setValignment(tamuSeal, VPos.CENTER);
            grid.add(tamuSeal, 3,6);

            pane.setCenter(grid);
            // pane.setBackground(new Background(backgroundImage));

            scene = new Scene(pane, 980, 825);
        }
        //TODO: fix runtime exception in the display all after generating report
        /** I'm not fixing the runtime exception because there doesn't seem to be a way
         * that I can see to fix it. Luckily, it doesn't impact the performance or
         * reliability of the application. Oh well. */
        catch(SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
            AlertError.showSQLException();
        }
    }

    /**
     * Sets the exit procedure for the application (which is the primaryStage of this class.
     */
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

    /**
     * The action event for the generate button. Calls the refreshData function to set up
     * the Gridpane to be used.
     */
    public void refreshScreen()
    {
        grid = new GridPane();

        System.out.println("New Database Connection created");
        System.out.println("Data for the charts has been updated");

        refreshData();

        scene = null;

        scene = new Scene(pane, 980, 825);

        dropList.setNullChosenProfessor();

        return;
    }

    /**
     * Sets up the Gridpane with the update data using the new Database connection
     */
    public void refreshData()
    {
        setDropListPane();

        // clears the grid to avoid any issues with populating the gridpane
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
        GridPane.setHalignment(calligraphyJ, HPos.CENTER);
        GridPane.setValignment(calligraphyJ, VPos.CENTER);
        grid.add(calligraphyJ, 3,3);

        // sets the vertical and horizontal alignment for Tamu Seal
        GridPane.setHalignment(tamuSeal, HPos.CENTER);
        GridPane.setValignment(tamuSeal, VPos.CENTER);
        grid.add(tamuSeal, 3,6);

        pane.setCenter(grid);
        // pane.setBackground(new Background(backgroundImage));

        return;
    }

    /**
     * Uses a task to create the new Database connection using the DatabaseAPI class.
     * Updates the GradeChart and DisplayData class objects.
     * It uses this task to do this and also put up a progress indicator to show that the
     * results are being generated.
     */
    private void createNewDBConn()
    {
        // makes sure there are no mistakes to save time
        if(dropList.getChosenSubject() == null)
        {
            AlertError.showNeedChooseSubject();
            return;
        }
        if(dropList.getChosenCourseNum() == 0)
        {
            AlertError.showNeedChooseCourseNum();
            return;
        }
        if(dropList.getChosenProfessor() == null)
        {
            AlertError.showNeedChooseProfessor();
            return;
        }

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

        // binds the progress indicator to the task thread
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
                AlertError.failedToGenerateReport();
                System.exit(0);
            }
        });

        return;
    }

    /**
     * For when the application is first started. Sets up the empty droplist using
     * the DropDownList class object and the generate button
     */
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

    /**
     * Sets up the pane after the database object has been updated.
     */
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

        return;
    }

    /**
     * Sets up the image viewer for the calligraphyJ image and stores it in the
     * classes CalligraphyJ data memeber
     */
    private void setCalligraphyJ()
    {
        try
        {
            // simple displays ImageView the image as is
            calligraphyJ = new ImageView(new Image(new FileInputStream("resources/Calligraphy J.png")));
            calligraphyJ.setFitWidth(325);
            calligraphyJ.setFitHeight(325);
            calligraphyJ.setPreserveRatio(true);
            calligraphyJ.setSmooth(true);
            calligraphyJ.setCache(true);
        }
        catch(IOException e)
        {
            System.err.println("\nProblem loading the Calligraphy J image\n");
            AlertError.showImageNotFound();
            e.printStackTrace();
        }
    }

    /**
     * Sets up the image viewer for the Tamu Seal image and stores it in the
     * classes tamuSeal data memeber
     */
    private void setTamuSeal()
    {
        try
        {
            tamuSeal = new ImageView(new Image(new FileInputStream("resources/No Background TAMU Seal.png")));
            tamuSeal.setFitWidth(325);
            tamuSeal.setFitHeight(325);
            tamuSeal.setPreserveRatio(true);
            tamuSeal.setSmooth(true);
            tamuSeal.setCache(true);
        }
        catch(IOException e)
        {
            System.err.println("\nProblem loading the Calligraphy J image\n");
            AlertError.showImageNotFound();
            e.printStackTrace();
        }
    }
}
