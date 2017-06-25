/**
 * Created by jonathanw on 6/24/17.
 */

import javafx.scene.control.*;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.text.*;

import java.sql.SQLException;

public class DropDownList extends Application
{
    private DatabaseAPI db;
    private ComboBox<String> chooseSubject;
    private ComboBox<Number> chooseCourse;
    private ComboBox<String> chooseProfessor;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override public void start(Stage primaryStage)
    {

    }

    public DropDownList()
    {
        // empty default constructor
    }

    public DropDownList(DatabaseAPI db)
    {
        this.db = db;
        setChooseSubject();
    }

    // change to combo box
    private void setChooseSubject()
    {
        ComboBox<String> subjects = new ComboBox<>();

        // goes through and stores the arraylist from the DBAPI class full of subjects
        for(int i = 0; i < db.getSubjects().size(); i++)
        {
            subjects.getItems().add(db.getSubjects().get(i));
        }

        subjects.setPromptText("Select");
        subjects.setValue("Select");
        subjects.setEditable(true);
        subjects.setVisibleRowCount(10);

        this.chooseSubject = subjects;
    }


}
