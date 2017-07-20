/**
 * Created by JonathanWesterfield on 7/20/17.
 */

import javafx.application.Application;
import javafx.geometry.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;

public class LoadScreen extends Application
{
    ImageView calligraphyJ;
    ImageView tamuSeal;
    BorderPane pane;


    public static void main(String[] args)
    {
        launch(args);
    }
    @Override public void start(Stage stage)
    {
        LoadScreen screen = new LoadScreen();

        VBox box = new VBox(screen.getCalligraphyJ(), screen.getTamuSeal());
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(8, 8, 8, 8));


        // TODO: Fix the labels by using Text area?
        // TODO: Put the borderPane in a function to be used by other classes
        String text = "Written by Jonathan Westerfield";
        String openSource = "This software is open source. Anyone can take this software and modify ";
        openSource += "and distribute as they see fit. ";
        openSource += "But if you do, please credit me.";

        Label writtenBy = new Label(text);
        Label source = new Label(openSource);
        Label classYear = new Label("Class of 2019");
        Label ag = new Label("Redass Ag\n");

        VBox labelBox = new VBox(writtenBy, classYear, ag, source);
        labelBox.setAlignment(Pos.CENTER);
        labelBox.setPadding(new Insets(8, 8, 8, 8));

        BorderPane pane = new BorderPane();
        BorderPane.setAlignment(box, Pos.CENTER);
        BorderPane.setMargin(box, new Insets(8, 8, 8, 8));
        pane.setCenter(box);
        BorderPane.setAlignment(writtenBy, Pos.CENTER);
        BorderPane.setMargin(writtenBy, new Insets(8, 8, 8, 8));
        pane.setBottom(labelBox);




        /*StackPane pane = new StackPane();
        pane.getChildren().add(box);
        StackPane.setAlignment(box, Pos.CENTER);*/

        /*BorderPane pane = new BorderPane();
        pane.setLeft(screen.getCalligraphyJ());
        BorderPane.setAlignment(screen.getCalligraphyJ(), Pos.CENTER);
        pane.setRight(screen.getTamuSeal());
        BorderPane.setAlignment(screen.getTamuSeal(), Pos.CENTER);*/

        Scene scene = new Scene(pane, 800, 800);
        stage.setScene(scene);
        stage.show();
    }

    public LoadScreen()
    {
        System.out.println("LoadScreen default constructor called");
        setCalligraphyJ();
        setTamuSeal();
    }

    public ImageView getCalligraphyJ()
    {
        return calligraphyJ;
    }

    public ImageView getTamuSeal()
    {
        return tamuSeal;
    }

    public GridPane getGrid()
    {
        return grid;
    }

    private void setCalligraphyJ()
    {
        try
        {
            // simple displays ImageView the image as is
            calligraphyJ = new ImageView(new Image(new FileInputStream("resources/Calligraphy J.png")));
            calligraphyJ.setFitWidth(325);
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


}

