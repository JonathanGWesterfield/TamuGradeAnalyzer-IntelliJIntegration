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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

public class DropDownList extends Application
{
    private DatabaseAPI dbAPI;

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


    /**
     * A main function for testing
     */
    @Override public void start(Stage primaryStage)
    {
        try
        {
            DatabaseAPI db = new DatabaseAPI("CSCE", 121, "MOORE");

            DropDownList list = new DropDownList(db);

            GridPane grid = new GridPane();
            grid.setVgap(4);
            grid.setHgap(10);
            grid.setPadding(new Insets(5,5,5,5));
            grid.add(list.getChooseSubject(), 3,0);
            grid.add(list.getChooseCourse(), 6, 0);
            grid.add(list.getChooseProfessor(), 9, 0);

            Scene scene = new Scene(new Group(),700, 50);

            Group root = (Group)scene.getRoot();
            root.getChildren().add(grid);

            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Empty default constructor for pleasing Java
     */
    public DropDownList()
    {
        // empty default constructor
        System.err.println("\nEmpty Default Constructor Called.\nNothing Happening\n\n");
    }

    /**
     * Class constructor sets up the empty dropdown lists, sets all of the datamembers to null
     * (for checking purposes in the DisplayAll class) and jumps straight into the
     * chooseSubject function so that the subject for analysis can be chosen.
     *
     * @param db
     */
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

    /**
     * Class getter functions for getChooseSubject, getChooseCourse, get ChooseProfessor,
     * getChosenSubject, get ChosenCourseNum, getChosenProfessor, setNullChosenSUbject,
     * setNullChosenCourseNum, and setNullChosenProfessor.
     *
     * @return
     */
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

    public String getChosenSubject()
    {
        return chosenSubject;
    }

    public int getChosenCourseNum()
    {
        return chosenCourseNum;
    }

    public String getChosenProfessor()
    {
        return chosenProfessor;
    }

    public void setNullChosenSubject()
    {
        chosenSubject = null;
    }

    public void setNullChosenCourseNum()
    {
        chosenCourseNum = 0;
    }

    public void setNullChosenProfessor()
    {
        chosenProfessor = null;
    }

    /**
     * Sets the lists for the initial state when nothing is entered. Will set the lists to a
     * default empty state when there is a choice higher in the heirarchy that has not
     * been determined yet.
     */
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
        else if(chosenSubject != null)
        {
            ObservableList<String> profData = FXCollections.observableArrayList();
            profData.add("        "); // adds an empty option to the list
            ComboBox<String> emptyProf = new ComboBox<>(profData);
            emptyProf.setPromptText("Choose Course #");
            this.chooseProfessor = emptyProf;
        }

        return;
    }

    /**
     * Will reset the drop down lists to an empty state for when the Subject is changed and
     * everything else needs to be cleared for a new search.
     */
    private void resetLists()
    {
        chooseCourse.getItems().clear();
        ObservableList<Integer> courseNumData = FXCollections.observableArrayList();
        courseNumData.add(000);
        chooseCourse.getItems().addAll(courseNumData);
        chooseCourse.setPromptText("Select Subject");

        chooseProfessor.getItems().clear();
        ObservableList<String> profData = FXCollections.observableArrayList();
        profData.add("        "); // adds an empty option to the list
        chooseProfessor.getItems().addAll(profData);
        chooseProfessor.setPromptText("Choose Course #");
    }

    /**
     * Sets up the chooseSubject combobox. This comboBox is editable and will list all of
     * the subjects in the database.
     */
    private void setChooseSubject()
    {
        try
        {
            // sets the other lists
            setNullLists();
            setNullChosenSubject();
            setNullChosenCourseNum();
            setNullChosenProfessor();

            ObservableList<String> data = FXCollections.observableArrayList();
            ArrayList<String> course = dbAPI.getAllSubjectDistinctList();

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

            this.chooseSubject.setOnAction(e -> setChosenSubject());
        }

        catch (SQLException e)
        {
            AlertError.showSQLException();
            e.printStackTrace();
        }
    }


    /**
     * Gets the selected item from the subject combobox. Gets the value from the chooseSubject
     * combo box and stores it into the chosenSubject data member. It then calls the
     * chooseCourseNum droplist function.
     */
    private void setChosenSubject()
    {
        if(!chooseSubject.getItems().contains(chooseSubject.getValue()))
        {
            AlertError.choiceNotFound(1);
            return;
        }

        // gets the value from the list and makes sure it is upper case
        this.chosenSubject = chooseSubject.getValue();

        resetLists();

        setChooseCourse();
    }

    /**
     * Store the course numbers from the dbAPI into the combo box list. Then displays the course
     * numbers to choose from in the drop down list. The action event for this combo box is the
     * setChosenCourseNum function.
     */
    private void setChooseCourse()
    {
        try
        {
            setNullChosenCourseNum();
            setNullChosenProfessor();

            chooseCourse.getItems().clear();

            ObservableList<Integer> data = FXCollections.observableArrayList();

            // put in a static subject to test
            // chosenSubject = "CSCE";
            ArrayList<Integer> nums = dbAPI.getAllCourseNumDistinct(chosenSubject);

            for(int i = 0; i < nums.size(); i++)
            {
                data.add(nums.get(i));
            }

            chooseCourse.getItems().addAll(data);
            chooseCourse.setPromptText("Select");
            chooseCourse.setEditable(false);
            chooseCourse.setVisibleRowCount(10);

            chooseCourse.setOnAction(e -> setChosenCourseNum());
            chooseCourse.setDisable(false);

            // setAllLists();
            return;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            AlertError.showSQLException();
        }
    }

    /**
     * Gets the value from the chooseCourse combo box and then stores it into the
     * chosenCourseNum data member. This then starts the setChooseProfessor function.
     */
    private void setChosenCourseNum()
    {
        if(!chooseCourse.getItems().contains(chooseCourse.getValue()))
        {
            // a check to make sure the error message doesn't pop up when
            // simply changing the subject or having empty fields
            if(chooseCourse.getValue() != null)
            {
                /** This is now redundant because the check is in the main class.
                 * Also, becauase this droplist is not editable, there can't be
                 * a case where the user chooses and option that doesn't exist.
                 */
                System.err.println("Incorrect Choice was: " + chooseCourse.getValue());
                AlertError.choiceNotFound(2);
                return;
            }
            return;
        }

        this.chosenCourseNum = chooseCourse.getValue();

        setChooseProfessor();

        return;
    }

    /**
     * Sets up the chooseProfessor drop down list. It is populated with all of the professors
     * for the specified subject and course from the preceding functions.
     * The action event for this list is the setChosenProfessor function.
     */
    private void setChooseProfessor()
    {
        try
        {
            setNullChosenProfessor();

            chooseProfessor.getItems().clear();

            ObservableList<String> data = FXCollections.observableArrayList();

            // setting static subject and course for testing
            // ArrayList<String> profList = dbAPI.getCourseProfessors("CSCE", 121);

            ArrayList<String> profList = dbAPI.getCourseProfessors(chosenSubject, chosenCourseNum);

            for(int i = 0; i < profList.size(); i++)
            {
                data.add(profList.get(i));
            }

            chooseProfessor.getItems().addAll(data);
            chooseProfessor.setPromptText("Select Prof");
            chooseProfessor.setEditable(false);
            chooseProfessor.setVisibleRowCount(12);

            this.chooseProfessor.setOnAction(e -> setChosenProfessor());

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            AlertError.showSQLException();
        }
    }

    /**
     * Sets the chosenProfessor and stores it in the respective data member. The value is
     * obtained from the chooseProfessor ComboBox.
     */
    private void setChosenProfessor()
    {
        if(!chooseProfessor.getItems().contains(chooseProfessor.getValue()))
        {
            /** This is now redundant because the check is in the main class.
             * Also, becauase this droplist is not editable, there can't be
             * a case where the user chooses and option that doesn't exist.
             */
            if(chooseProfessor.getValue() != null)
            {
                System.err.println("Incorrect Choice was: " + chooseProfessor.getValue());
                AlertError.choiceNotFound(3);
                return;
            }
            return;
        }

        this.chosenProfessor = chooseProfessor.getValue();

        return;
    }
}





























