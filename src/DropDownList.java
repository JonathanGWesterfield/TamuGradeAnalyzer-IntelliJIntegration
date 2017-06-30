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
    private DatabaseAPI dbAPI;
    private DatabaseAPI returndbAPI;
    HBox allLists;

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

            Label lbl = new Label("At least something works");

            HBox box = new HBox(10, lbl);

            /*
            // sets the other lists
            // setNullLists();

            ObservableList<String> data = FXCollections.observableArrayList();
            ArrayList<String> course = dbAPI.getSubjects();

            // goes through and stores the arraylist from the dbAPIAPI class full of subjects
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


            this.chooseSubject.setOnAction(e -> setChosenSubject());
            */

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
        System.err.println("\nEmpty Default Constructor Called.\nNothing Happening\n\n");
    }

    public DropDownList(DatabaseAPI db)
    {
        this.dbAPI = db;

        this.chooseSubject = null;
        this.chooseCourse = null;
        this.chooseProfessor = null;

        this.chosenSubject = null;
        this.chosenCourseNum = 000;
        this.chosenProfessor = null;

        setNullLists();

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

    public HBox getAllLists()
    {
        return allLists;
    }

    //TODO: fix functions so that if a function is null, they can still all be displayed
    private void setAllLists()
    {
        HBox dropLists = new HBox();
        dropLists.getChildren().addAll(getChooseSubject(), getChooseCourse(), getChooseProfessor());

        this.allLists = dropLists;
    }

    // sets the lists for the initial state when nothing is entered
    private void setNullLists()
    {
        if(chosenSubject == null)
        {
            ObservableList<Integer> courseNumData = FXCollections.observableArrayList();
            courseNumData.add(000);
            ComboBox<Integer> courseNums = new ComboBox<>(courseNumData);
            courseNums.setPromptText("Select Subject");
            this.chooseCourse = courseNums;

            ObservableList<String> profData = FXCollections.observableArrayList();
            profData.add("        "); // adds an empty option to the list
            ComboBox<String> emptyProf = new ComboBox<>(profData);
            emptyProf.setPromptText("Choose Subject");
            this.chooseProfessor = emptyProf;
        }

        return;
    }

    private void resetLists()
    {
        ObservableList<Integer> courseNumData = FXCollections.observableArrayList();
        courseNumData.add(000);
        ComboBox<Integer> courseNums = new ComboBox<>(courseNumData);
        courseNums.setPromptText("Select Subject");
        this.chooseCourse = courseNums;

        ObservableList<String> profData = FXCollections.observableArrayList();
        profData.add("        "); // adds an empty option to the list
        ComboBox<String> emptyProf = new ComboBox<>(profData);
        emptyProf.setPromptText("Choose Subject");
        this.chooseProfessor = emptyProf;
    }

    // change to combo box
    private void setChooseSubject()
    {
        // sets the other lists
        setNullLists();

        ObservableList<String> data = FXCollections.observableArrayList();
        ArrayList<String> course = dbAPI.getSubjects();

        // goes through and stores the arraylist from the dbAPIAPI class full of subjects
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
            AlertError.choiceNotFound(1);
            return;
        }

        // gets the value from the list and makes sure it is upper case
        this.chosenSubject = chooseSubject.getValue();

        // resetLists();

        setChooseCourse();
    }

    //TODO: set the other 'set' functions to look like the setChooseSubject function
    // store the course numbers from the dbAPIAPI into the combo box list
    private void setChooseCourse()
    {
        try
        {
            ObservableList<Integer> data = FXCollections.observableArrayList();

            // put in a static subject to test
            // chosenSubject = "CSCE";
            ArrayList<Integer> nums = dbAPI.getAllCourseNumDistinct(chosenSubject);

            for(int i = 0; i < nums.size(); i++)
            {
                data.add(nums.get(i));
            }

            ComboBox<Integer> courseNums = new ComboBox<>(data);
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
            AlertError.choiceNotFound(2);
            return;
        }

        this.chosenCourseNum = chooseCourse.getValue();

        setChooseProfessor();
    }

    private void setChooseProfessor()
    {
        try
        {
            ObservableList<String> data = FXCollections.observableArrayList();

            //setting static subject and course for testing
            // ArrayList<String> profList = dbAPI.getCourseProfessors("CSCE", 121);

            ArrayList<String> profList = dbAPI.getCourseProfessors(chosenSubject, chosenCourseNum);

            for(int i = 0; i < profList.size(); i++)
            {
                data.add(profList.get(i));
            }

            ComboBox prof = new ComboBox(data);
            prof.setPromptText("Select Prof");
            // prof.setValue(profList.get(0));
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
        if(!chooseProfessor.getItems().contains(chooseProfessor.getValue()))
        {
            System.err.println("Incorrect Choice was: " + chooseProfessor.getValue());
            AlertError.choiceNotFound(3);
            return;
        }

        this.chosenProfessor = chooseProfessor.getValue();

        createdbAPIObject();
    }

    private void createdbAPIObject()
    {
        try
        {
            returndbAPI = new DatabaseAPI(chosenSubject, chosenCourseNum, chosenProfessor);
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
            AlertError.showSQLException();
        }
    }
}





























