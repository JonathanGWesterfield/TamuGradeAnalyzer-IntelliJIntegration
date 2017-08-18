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

/**
 * This entire class is a collection of error messages that can be used anywhere else in this
 * library. They are all static functions so there is no need for a constructor or a declaration
 * of the AlertError object in any other class.
 */
public class AlertError
{

    /**
     * This is for when the user enters a choice in a combo box for the DropDownList class.
     * This will tell the user what is missing so they can choose their course properly.
     * @param choice
     */
    public static void choiceNotFound(int choice)
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error!");

        // choice structure for which field has the incorrect input
        if(choice == 1)
        {
            alert.setHeaderText("Subject Not Found");
            alert.setContentText("The Subject that you tried to enter was either mispelled or " +
                    "does not exist.\n\nMake sure you type the subject in ALL CAPS with " +
                    "no spaces.\n\nPlease try again.");
        }
        /**
         * The choices after choice == 1 are unnecessary since only the Subject drop down list
         * in the DropDownList class has the possibility of a user entering an incorrect choice.
         * But I'm leaving this here because it doesn't hurt anything. Maybe it will change later.
         */
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

    /**
     * The error for the splash screen in the main function. This alert is for when the application
     * cannot startup normally. It will tell the user that the application cannot start
     */
    public static void failedToStart()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Application failed to start");
        alert.setContentText("Whoops! Looks like I'm having trouble getting it up. Please make sure " +
                "that you are connected to the internet and have the latest version of the " +
                "Java runtime environment (JRE).\n\nThis can be downloaded FOR FREE at " +
                "http://www.oracle.com/technetwork/java/javase/downloads/index.html");

        System.out.println();

        alert.showAndWait();
    }

    public static boolean confirmExit()
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("You don't have any other professors to look at?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK)
            return true;
        else
            return false;

    }

    // to be used in any try/catch blocks if a uncovered exception shows up

    /**
     * Creates a window that will show up if there is a SQL exception. However the code can
     * be used with any exception. It will create a pop up window that will detail what the
     * exception is and print out the stack trace in that pop up window.
     */
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

    /**
     * An alert to let the user know that the image that is being looked for has not been found.
     * This is to be used in catch blocks that throw and ImageNotFound FileIO exception. This
     * also prints out the stack trace.
     */
    public static void showImageNotFound()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("An Exception Was Generated");
        alert.setContentText("Something went wrong");

        Exception ex = new SQLException("A certain image file could not be found");

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

    /**
     * Creates a window that will show up if there is a ClassNotFound exception.
     * However the code can be used with any exception. It will create a pop up
     * window that will detail what the exception is and print out the stack trace
     * in the pop up window.
     */
    public static void showClassNotFoundException()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("An Exception Was Generated");
        alert.setContentText("Something went wrong");

        Exception ex = new SQLException("There was a class not found exception");

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

    /**
     * Error for if the user has not chosen a subject before clicking the 'Generate Report'
     * button, which means he has not chosen a course number or professor as well.
     * This error tells the user this so they can choose their course correctly.
     */
    public static void showNeedChooseSubject()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Subject Not Chosen");
        alert.setContentText("You Need to choose a Subject, Course Number and Professor." +
                " Try again.");

        alert.showAndWait();
    }

    /**
     * Error for if the user has not chosen a Course number before clicking the 'Generate Report'
     * button, which means he has not chosen a professor as well. This error tells the
     * user this so they can choose their course correctly.
     */
    public static void showNeedChooseCourseNum()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Course Number Not Chosen");
        alert.setContentText("You Need to choose a Course Number and Professor." +
                " Try again.");

        alert.showAndWait();
    }

    /**
     * Error for if the user has not chosen a professor before clicking the 'Generate Report'
     * button. This error tells that this so they can choose their course correctly.
     */
    public static void showNeedChooseProfessor()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Professor Not Chosen");
        alert.setContentText("You Need to choose a Professor. Try again.");

        alert.showAndWait();
    }

    /**
     * This error is for if the user has input the search parameters for the course correctly, but
     * there seems to have been some error after the clicked the 'Generate Report' button. This
     * error pops up if the task thread in the DisplayAll class fails. The most likely cause for
     * this would be if their internet connection was suddenly lost, meaning the database
     * connection would also be broken.
     */
    public static void failedToGenerateReport()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Failed to Generate Report");
        alert.setContentText("Whoops! Looks like I'm having trouble getting it up. Please make sure " +
                "that you are connected to the internet. You may have lost the connection. " +
                "Please restart the application after you have done so.");

        System.out.println();

        alert.showAndWait();
    }

}
