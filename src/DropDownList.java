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
    private DatabaseAPI returndbAPI;
    GridPane allLists;
    // Scene scene;

    private ComboBox<String> chooseSubject;
    // private ObservableList<String> subjectData;

    private ComboBox<Integer> chooseCourse;
    private ComboBox<String> chooseProfessor;

    // this is to let the other classes know to update and get rid of their empty null functions
    private boolean signalChange = false;
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

            // Label lbl = new Label("At least something works");

            //HBox box = new HBox(10, list.getChooseSubject(), list.getChooseCourse(), list.getChooseProfessor());

            //list.setAllLists();

            // list.setChooseCourse();

            // TODO: use this code to finish up the DisplayAll Class
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

    //TODO: comment the hell out of this class

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

        // setAllLists();
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

    public DatabaseAPI getReturndbAPI()
    {
        return returndbAPI;
    }

    public boolean getSignalChange()
    {
        return signalChange;
    }


    //TODO: make this class return the grid pane or figure out how to use it in another class

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

    // change to combo box
    private void setChooseSubject()
    {
        try
        {
            // sets the other lists
            setNullLists();

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

            /*
            this.chooseSubject.setItems(data);
            this.chooseSubject.setPromptText("Select");
            this.chooseSubject.setValue("Select");
            this.chooseSubject.setEditable(true);
            this.chooseSubject.setVisibleRowCount(10);
            */

            this.chooseSubject.setOnAction(e -> setChosenSubject());
        }

        catch (SQLException e)
        {
            AlertError.showSQLException();
            e.printStackTrace();
        }
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

        resetLists();

        // resetLists();

        // refreshHbox();

        setChooseCourse();
    }

    // store the course numbers from the dbAPIAPI into the combo box list
    private void setChooseCourse()
    {
        try
        {
            chooseCourse.getItems().clear();

            ObservableList<Integer> data = FXCollections.observableArrayList();

            // put in a static subject to test
            // chosenSubject = "CSCE";
            ArrayList<Integer> nums = dbAPI.getAllCourseNumDistinct(chosenSubject);

            for(int i = 0; i < nums.size(); i++)
            {
                data.add(nums.get(i));
            }

            // ComboBox<Integer> courseNums = new ComboBox<>(data);
            // courseNums.setPromptText("Select");
            // courseNums.setValue(101);
            // courseNums.setEditable(true);
            // courseNums.setVisibleRowCount(10);

            chooseCourse.getItems().addAll(data);
            chooseCourse.setPromptText("Select");
            chooseCourse.setEditable(false);
            chooseCourse.setVisibleRowCount(10);

            // this.chooseCourse = courseNums;

            // uses function to refresh the combobox
            // this.setAllLists();
            // this.getAllLists();

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

    private void setChosenCourseNum()
    {
        if(!chooseCourse.getItems().contains(chooseCourse.getValue()))
        {
            // a check to make sure the error message doesn't pop up when
            // simply changing the subject or having empty fields
            if(chooseCourse.getValue() != null)
            {
                System.err.println("Incorrect Choice was: " + chooseCourse.getValue());
                AlertError.choiceNotFound(2);
                return;
            }
            return;
        }

        this.chosenCourseNum = chooseCourse.getValue();

        // uses function to refresh the combobox
        // this.setAllLists();
        // this.getAllLists();

        setChooseProfessor();

        return;
    }

    private void setChooseProfessor()
    {
        try
        {
            chooseProfessor.getItems().clear();

            ObservableList<String> data = FXCollections.observableArrayList();

            //setting static subject and course for testing
            // ArrayList<String> profList = dbAPI.getCourseProfessors("CSCE", 121);

            ArrayList<String> profList = dbAPI.getCourseProfessors(chosenSubject, chosenCourseNum);

            for(int i = 0; i < profList.size(); i++)
            {
                data.add(profList.get(i));
            }

            // ComboBox prof = new ComboBox(data);
            // prof.setPromptText("Select Prof");
            // prof.setValue(profList.get(0));
            // prof.setEditable(true);
            // prof.setVisibleRowCount(12);

            chooseProfessor.getItems().addAll(data);
            chooseProfessor.setPromptText("Select Prof");
            chooseProfessor.setEditable(false);
            chooseProfessor.setVisibleRowCount(12);

            // this.chooseProfessor = prof;

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
            // a check to make sure the error message doesn't pop up when
            // simply changing the subject or having empty fields
            if(chooseProfessor.getValue() != null)
            {
                System.err.println("Incorrect Choice was: " + chooseProfessor.getValue());
                AlertError.choiceNotFound(3);
                return;
            }
            return;
        }

        this.chosenProfessor = chooseProfessor.getValue();

        try
        {
            createdbAPIObject();
            return;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            AlertError.showSQLException();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
            AlertError.showClassNotFoundException();
        }

        return;
    }

    public RefreshedDisplay createdbAPIObject() throws SQLException, ClassNotFoundException
    {

        returndbAPI = new DatabaseAPI(chosenSubject, chosenCourseNum, chosenProfessor);
        RefreshedDisplay refreshedDisplay = new RefreshedDisplay(returndbAPI);
        signalChange = true;
        return refreshedDisplay;
    }
}





























