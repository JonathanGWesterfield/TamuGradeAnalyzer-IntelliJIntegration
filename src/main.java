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

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        setDisplay();
    }


    private void setDisplay()
    {
        // sets up the splash screen for the application
        LoadScreen loadScreen = new LoadScreen();
        loadScreen.showStage();

        System.out.println("Attempting the TASK");

        Task<DatabaseAPI> splashScreen = new Task<DatabaseAPI>()
        {
            @Override
            protected DatabaseAPI call() throws Exception
            {
                System.out.println("Trying to open up the DB connection");
                DatabaseAPI db = new DatabaseAPI();

                // makes the splash screen stay up a little longer so they can see my stuff
                Thread.sleep(2000);

                System.out.println("Successfully created the DatabaseAPI object. Returning");
                return db;
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
                loadScreen.hideStage();

                System.out.print("Was able to hide the splashscreen");

                // should free up the space and process for the progress indicator
                // loadScreen = null;

                System.out.println("Trying to create the Main Stage");

                display = new DisplayAll(splashScreen.getValue());

                display.showPrimaryStage();

                return;
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


