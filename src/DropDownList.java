/**
 * Created by jonathanw on 6/24/17.
 */

import javafx.scene.control.*;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.text.*;
import javafx.application.*;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class DropDownList extends Application
{
    private DatabaseAPI db;
    private ComboBox<String> chooseSubject;
    private ComboBox<Integer> chooseCourse;
    private ComboBox<String> chooseProfessor;

    private String chosenSubject;
    private int chosenCourseNum;
    private String chosenProfessor;

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

    //TODO: FINISH UP THE COMBO CHOICE TO CHOOSE SUBJECTS
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

    // gets the selected item from the subject combobox
    private void setChosenSubject() throws SQLException
    {
        if(!chooseSubject.getItems().contains(chooseSubject.getValue()))
        {
            AlertError.choiceNotFound();
            return;
        }

        this.chosenSubject = chooseSubject.getValue();
        db.getAllCourseNumDistinct(chosenSubject);
        return;
    }

    // store the course numbers from the DBAPI into the combo box list
    private void setChooseCourse() throws SQLException
    {
        ComboBox<Integer> courseNums = new ComboBox<>();

        for(int i = 0; i < db.getAllCourseNumDistinct(chosenSubject).size(); i++)
        {
            courseNums.getItems().add(db.getAllCourseNumDistinct(chosenSubject).get(i));
        }

        courseNums.setPromptText("Select");
        courseNums.setValue(101);
        courseNums.setEditable(true);
        courseNums.setVisibleRowCount(10);

        this.chooseCourse = courseNums;
    }


}
