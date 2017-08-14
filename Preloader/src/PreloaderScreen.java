/**
 * Created by JonathanWesterfield on 7/20/17.
 */

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.geometry.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;

public class PreloaderScreen extends Preloader
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
        PreloaderScreen screen = new PreloaderScreen();

        Scene scene = new Scene(screen.getPane(), 800, 825);
        stage.setScene(scene);
        stage.show();
    }

    public PreloaderScreen()
    {
        System.out.println("PreloaderScreen default constructor called");
        setCalligraphyJ();
        setTamuSeal();
        setPane();
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


}

