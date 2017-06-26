/**
 * Created by jonathanw on 6/24/17.
 */

import javafx.scene.control.*;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.collections.*;
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
import java.util.Observable;

public class DropDownList extends Application
{
    private DatabaseAPI db;
    private DatabaseAPI returnDB;

    private ComboBox<String> chooseSubject;
    // private ObservableList<String> subjectData;

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
        try
        {
            DatabaseAPI db = new DatabaseAPI("CSCE", 121, "MOORE");

            DropDownList list = new DropDownList(db);


            Scene scene = new Scene(list.getChooseSubject(), 300, 85);

            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
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

    public ComboBox<String> getChooseSubject()
    {
        return chooseSubject;
    }

    public ComboBox<Integer> getChooseCourse()
    {
        return chooseCourse;
    }

    public ComboBox<String> getChooseProfessor()
    {
        return chooseProfessor;
    }

    // change to combo box
    private void setChooseSubject()
    {
        ObservableList<String> data = FXCollections.observableArrayList();
        ArrayList<String> course = db.getSubjects();

        // goes through and stores the arraylist from the DBAPI class full of subjects
        for (int i = 0; i < course.size(); i++)
        {
            data.add(course.get(i));
        }
        ComboBox combo = new ComboBox(data);
        combo.setPromptText("Select");
        combo.setEditable(true);
        combo.setVisibleRowCount(10);
        chooseSubject = combo;

        /*
        this.chooseSubject.setItems(data);
        this.chooseSubject.setPromptText("Select");
        this.chooseSubject.setValue("Select");
        this.chooseSubject.setEditable(true);
        this.chooseSubject.setVisibleRowCount(10);
        */

        this.chooseSubject.setOnAction(e -> setChosenSubject());
    }

    // gets the selected item from the subject combobox
    private void setChosenSubject()
    {
        if(!chooseSubject.getItems().contains(chooseSubject.getValue()))
        {
            AlertError.choiceNotFound();
            return;
        }

        // gets the value from the list and makes sure it is upper case
        this.chosenSubject = chooseSubject.getValue().toUpperCase();

        setChooseCourse();
    }

    //TODO: set the other 'set' functions to look like the setChooseSubject function
    // store the course numbers from the DBAPI into the combo box list
    private void setChooseCourse()
    {
        try
        {
            ComboBox<Integer> courseNums = new ComboBox<>();

            ArrayList<Integer> nums = db.getAllCourseNumDistinct(chosenSubject);

            for(int i = 0; i < nums.size(); i++)
            {
                courseNums.getItems().add(nums.get(i));
            }

            courseNums.setPromptText("Select");
            courseNums.setValue(101);
            courseNums.setEditable(true);
            courseNums.setVisibleRowCount(10);

            this.chooseCourse = courseNums;

            chooseCourse.setOnAction(e -> setChosenCourseNum());

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            AlertError.showSQLException();
        }
    }

    private void setChosenCourseNum()
    {
        if(!chooseCourse.getItems().contains(chooseCourse.getValue()))
        {
            AlertError.choiceNotFound();
            return;
        }

        this.chosenCourseNum = chooseCourse.getValue();

        setChooseProfessor();
    }

    private void setChooseProfessor()
    {
        try
        {
            ComboBox prof = new ComboBox();
            ArrayList profList = db.getCourseProfessors(chosenSubject, chosenCourseNum);

            for(int i = 0; i < profList.size(); i++)
            {
                prof.getItems().add(profList.get(i));
            }

            prof.setPromptText("Select Prof");
            prof.setValue(profList.get(0));
            prof.setEditable(true);
            prof.setVisibleRowCount(12);

            this.chooseProfessor = prof;

            this.chooseProfessor.setOnAction(e -> setChosenProfessor());


        }
        catch (SQLException e)
        {
            e.printStackTrace();
            AlertError.showSQLException();
        }
    }

    private void setChosenProfessor()
    {
        if(!chooseCourse.getItems().contains(chooseCourse.getValue()))
        {
            AlertError.choiceNotFound();
            return;
        }

        this.chosenProfessor = chooseProfessor.getValue();

        createDBObject();
    }

    private void createDBObject()
    {
        try
        {
            returnDB = new DatabaseAPI(chosenSubject, chosenCourseNum, chosenProfessor);
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
            AlertError.showSQLException();
        }
    }
}





























