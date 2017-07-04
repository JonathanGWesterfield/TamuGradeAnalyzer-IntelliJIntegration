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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;


/** This class ties in all of the components from the GradeChart, DisplayData, and
 * DropDownLists class and puts them all onto one grid pane to be displayed in the scene
 */

//TODO: need to put an action event that extends to all of the other classes to refresh them
    // once a professor is chosen/ MAY HAVE TO MOVE THE ACTIONEVENT FROM THE DROPDOWN CLASS TO THIS ONE
public class DisplayAll extends Application
{

    //TODO: use a grid pane to display everything somewhat easily
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override public void start(Stage primaryStage)
    {
        try
        {
            DatabaseAPI db = new DatabaseAPI();

            DropDownList dropList = new DropDownList(db);

            DisplayData displayData = new DisplayData(dropList.getReturndbAPI(), true);

            GradeChart gradeChart = new GradeChart(dropList.getReturndbAPI(), true);

            GridPane grid = new GridPane();

            primaryStage.getIcons().add(new Image(this.getClass().getResource("/Resource/Calligraphy J.png").toString()));

            Scene scene = new Scene(dropList.getChooseSubject(), 500, 50);

            primaryStage.setScene(scene);
            primaryStage.show();

        }
        catch(SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
            AlertError.showSQLException();
        }
    }

}
