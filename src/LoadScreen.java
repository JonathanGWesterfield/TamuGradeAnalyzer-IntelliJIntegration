/**
 * Created by JonathanWesterfield on 7/20/17.
 */

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadScreen extends Application
{
    ImageView calligraphyJ;
    ImageView tamuSeal;
    BorderPane pane;
    StackPane stackPane;
    Stage stage;


    /**
     * main function as well as the public start function for testing
     * @param args
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    @Override public void start(Stage stage)
    {
        LoadScreen screen = new LoadScreen();
    }

    /**
     * class constructor
     */
    public LoadScreen()
    {
        System.out.println("LoadScreen default constructor called");
        setCalligraphyJ();
        setTamuSeal();
        setPane();
        setStackPane();
        setStage();
    }

    /**
     * public getter functions
     * @return
     */
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

    public void showStage()
    {
        stage.show();
    }

    public void hideStage()
    {
        stage.hide();
    }

    /**
     * Sets up the load screen with the calligraphy J and tamu seal images. Also puts the title
     * of the application and a few labels with my name, class year, and a statement telling
     * people to credit me if they use or distribute my app
     */
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

    /**
     * puts the newly created BorderPane on top of a stack pane and then puts a progress indicator
     * on top of that borderpane
     */
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

    /**
     * puts the newly created stackpane on a stage to be used in a different class
     */
    private void setStage()
    {
        Scene scene = new Scene(stackPane);
        stage = new Stage(StageStyle.UNDECORATED);
        stage.setScene(scene);

        return;
    }

    /**
     * sets and ImageView for the calligraphy J image so that it can be put on the loadscreen
     */
    private void setCalligraphyJ()
    {
        /*try
        {
            // simple displays ImageView the image as is
            calligraphyJ = new ImageView(new Image(new FileInputStream(
                    "resources/images.images/Calligraphy J.png")));
            // calligraphyJ = new ImageView(new Image(main.class.getResourceAsStream(
            //         "Calligraphy J.png")));
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
        }*/

        calligraphyJ = new ImageView(new Image(getClass().getResourceAsStream(
                "images/Calligraphy J.png")));
        calligraphyJ.setFitWidth(325);
        calligraphyJ.setFitHeight(325);
        calligraphyJ.setPreserveRatio(true);
        calligraphyJ.setSmooth(true);
        calligraphyJ.setCache(true);
    }

    /**
     * sets and ImageView for the Tamu Seal image so that it can be put on the loadscreen
     */
    private void setTamuSeal()
    {
        /*try
        {
            tamuSeal = new ImageView(new Image(new FileInputStream(
                    "resources/images.images/No Background TAMU Seal.png")));
            // tamuSeal = new ImageView(new Image(main.class.getResourceAsStream(
            //         "No Background TAMU Seal.png")));
            tamuSeal.setFitWidth(325);
            tamuSeal.setFitHeight(325);
            tamuSeal.setPreserveRatio(true);
            tamuSeal.setSmooth(true);
            tamuSeal.setCache(true);
        }
        catch(IOException e)
        {
            System.err.println("\nProblem loading the Tamu Seal image\n");
            AlertError.showImageNotFound();
            e.printStackTrace();
        }*/


        tamuSeal = new ImageView(new Image(getClass().getResourceAsStream(
                "images/No BackGround TAMU Seal.png")));
        tamuSeal.setFitWidth(325);
        tamuSeal.setFitHeight(325);
        tamuSeal.setPreserveRatio(true);
        tamuSeal.setSmooth(true);
        tamuSeal.setCache(true);
    }
}

