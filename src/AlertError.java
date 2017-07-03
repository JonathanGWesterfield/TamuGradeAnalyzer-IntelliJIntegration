/**
 * Created by jonathanw on 6/24/17.
 */

import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import javafx.scene.*;
import javafx.scene.layout.*;

public class AlertError
{

    public AlertError()
    {
        // default constructor
    }

    public static void choiceNotFound(int choice)
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error!");

        // choice structure for which field has the incorrect input
        if(choice == 1)
        {
            alert.setHeaderText("Subject Not Found");
            alert.setContentText("The Subject that you tried to enter was either mispelled or " +
                    "does not exist. Please try again.");
        }
        else if(choice == 2)
        {
            alert.setHeaderText("Course Number Not Found");
            alert.setContentText("The Course Number that you tried to enter was either mispelled or " +
                    "does not exist. Please try again.");
        }
        else if(choice == 3)
        {
            alert.setHeaderText("Professor Not Found");
            alert.setContentText("The Professor that you tried to enter was either mispelled or " +
                    "does not exist. Please try again.");
        }

        System.out.println();


        alert.showAndWait();
    }

    //TODO: set this up once the other components are set up to tell the user how to use the software
    public void showUsageInstructions(int instructionNum)
    {
        //TODO: use the information dialog below to set it up

        /*
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("I have a great message for you!");

        alert.showAndWait();
         */
        if(instructionNum == 0)
        {

        }
    }

    // to be used in any try/catch blocks if a uncovered exception shows up
    // TODO: need to figure out how this code works and how to use it
    public static void showSQLException()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("An Exception Was Generated");
        alert.setContentText("Something went wrong");

        Exception ex = new SQLException("There was a problem connecting to the database");

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }



}
