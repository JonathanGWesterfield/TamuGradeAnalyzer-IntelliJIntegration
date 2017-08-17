/**
 * Created by JonathanWesterfield on 12/24/16.
 */

import javafx.application.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.concurrent.*;
import javax.xml.crypto.Data;
import javafx.scene.*;

import java.sql.SQLException;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class main extends Application
{


    protected DisplayAll display;
    private static DatabaseAPI db;

    ImageView calligraphyJ;
    ImageView tamuSeal;
    BorderPane pane;
    StackPane stackPane;
    Stage loadScreenStage;
    Scene loadScene;


    public static void main(String[] args)
    {
        launch(args);
    }

    /*@Override
    public void init()
    {
        loadScreen = new LoadScreen();
    }*/

    @Override
    public void start(Stage primaryStage)
    {
        setLoadScreen();
        setDisplay1();
    }

    // Stage stage;

    public void setLoadScreen()
    {
        setCalligraphyJ();
        setTamuSeal();
        setPane();
        setStackPane();

        loadScene = new Scene(stackPane);
        loadScreenStage = new Stage();
        loadScreenStage.setScene(loadScene);
        // setStage();
    }

    public ImageView getCalligraphyJ()
    {
        return calligraphyJ;
    }

    public ImageView getTamuSeal()
    {
        return tamuSeal;
    }

    public BorderPane getPane()
    {
        return pane;
    }

    public StackPane getStackPane()
    {
        return stackPane;
    }

    private void setPane()
    {
        Label name = new Label("Texas A&M Professor Grade Analysis Tool");
        name.setFont(new Font("Futura", 30));

        HBox title = new HBox(name);
        title.setAlignment(Pos.CENTER);
        title.setSpacing(8);
        title.setPadding(new Insets(8, 8, 8, 8));



        VBox box = new VBox(calligraphyJ, tamuSeal);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(8, 8, 8, 8));
        box.setMinHeight(300);
        box.setMinWidth(300);

        String text = "Written by Jonathan Westerfield";
        String openSource = "This software is open source. Anyone can take this software and modify ";
        openSource += "and distribute as they see fit. ";
        openSource += "But if you do, please credit me.";

        Label writtenBy = new Label(text);
        writtenBy.setFont(new Font("Futura", 20));

        Label source = new Label(openSource);
        source.setFont(new Font("Futura", 12));

        Label classYear = new Label("Class of 2019");
        classYear.setFont(new Font("Futura", 20));

        Label ag = new Label("Redass Ag\n");
        ag.setFont(new Font("Futura", 20));

        VBox labelBox = new VBox(writtenBy, classYear, ag, source);
        labelBox.setAlignment(Pos.CENTER);
        labelBox.setPadding(new Insets(8, 8, 8, 8));

        pane = new BorderPane();
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(8, 8, 0, 8));
        pane.setTop(title);
        BorderPane.setAlignment(box, Pos.CENTER);
        BorderPane.setMargin(box, new Insets(8, 8, 8, 8));
        pane.setCenter(box);
        BorderPane.setAlignment(writtenBy, Pos.CENTER);
        BorderPane.setMargin(writtenBy, new Insets(8, 8, 8, 8));
        pane.setBottom(labelBox);

        pane.setMinHeight(300);
        pane.setMinWidth(300);
    }

    private void setStackPane()
    {
        ProgressIndicator progress = new ProgressIndicator();
        progress.setMaxWidth(200);
        progress.setMaxHeight(200);
        progress.setMinHeight(200);
        progress.setMinWidth(200);

        stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(pane, progress);

        return;
    }

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
            e.printStackTrace();
        }
    }

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
            e.printStackTrace();
        }
    }

    private void setDisplay1()
    {
        // sets up the splash screen for the application
        // loadScreen.showStage();

        loadScreenStage.show();

        System.out.println("Attempting the TASK");

        Task<DatabaseAPI> splashScreen = new Task<DatabaseAPI>()
        {
            @Override
            protected DatabaseAPI call() throws Exception
            {
                System.out.println("Trying to open up the DB connection");
                DatabaseAPI db = new DatabaseAPI();

                System.out.println("Successfully created the DatabaseAPI object. Returning");
                return db;

                /*try
                {
                    DatabaseAPI db = new DatabaseAPI();
                    DisplayAll newDisplay = new DisplayAll(db);

                    return newDisplay;
                }
                catch (SQLException e)
                {
                    AlertError.showSQLException();
                    AlertError.failedToStart();
                }
                catch (ClassNotFoundException e)
                {
                    AlertError.showClassNotFoundException();
                }*/

                    /* long start = System.currentTimeMillis();
                    long end = start + 3*1000; // 3 seconds * 1000 ms/sec

                    while (System.currentTimeMillis() < end)
                    {
                        continue;
                    }*/
            }
        };

        // starts the task that was just created
        Thread createThread = new Thread(splashScreen);
        createThread.setDaemon(true);
        createThread.start();


        // will show the main stage after the splash screen
        splashScreen.setOnSucceeded(new EventHandler<WorkerStateEvent>()
        {
            @Override
            public void handle(WorkerStateEvent event)
            {
                // hides the splash screen
                loadScreenStage.hide();

                System.out.print("Was able to hide the splashscreen");

                // should free up the space and process for the progress indicator
                // loadScreen = null;

                System.out.println("Trying to create the Main Stage");
                display = new DisplayAll(splashScreen.getValue());

                display.showPrimaryStage();


            }
        });

        splashScreen.setOnFailed(new EventHandler<WorkerStateEvent>()
        {
            @Override
            public void handle(WorkerStateEvent event)
            {
                System.out.println("Something failed");
                AlertError.failedToStart();
                System.out.println();
                printStackTrace();
                // forces app shutdown if it couldn't start so the user can try again
                System.exit(0);
            }
        });
        return;

        /*try
        {

        }
        catch (SQLException e)
        {
            AlertError.showSQLException();
            AlertError.failedToStart();
        }
        catch (ClassNotFoundException e)
        {
            AlertError.showClassNotFoundException();
        }*/

    }

    // this is useless i think. It doesn't print the stack trace where I need it
    public void printStackTrace()
    {
        System.err.println("Print Stack Trace: ");
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for(int i = 0; i < elements.length; i++)
        {
            StackTraceElement s  = elements[i];
            System.err.println("\tat " + s.getClassName() + "." + s.getMethodName()
                    + "(" + s.getFileName() + ":" + s.getLineNumber() + ")");
        }
    }


}


