/**
 * Created by jonathanw on 6/16/17.
 */

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.text.*;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.text.DecimalFormat;


public class DisplayData extends Application
{

    private DatabaseAPI db;
    private VBox totalGrades;
    private VBox percentagesDisplay;
    private VBox courseInfo;
    private Label avgGPA;

    public static void main(String [] args)
    {
        launch(args);
    }

    @Override public void start(Stage primaryStage)
    {
        try
        {
            DatabaseAPI db = new DatabaseAPI("CSCE", 121, "MOORE");

            DisplayData display = new DisplayData(db, true);


            Scene scene = new Scene(display.getPercentagesDisplay(), 300, 85);

            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    public DisplayData()
    {
        // default constructor
    }

    public DisplayData(DatabaseAPI database, boolean isNull)
    {
        if(isNull)
        {
            emptyCourseInfo();
            emptyGradeData();
            emptyPercentages();
            setEmptyAvgGPA();
            return;
        }

        this.db = database;
        gradeData();
        setPercentagesDisplay();
        setCourseInfo();
        setAvgGPA();
    }

    public VBox getPercentagesDisplay()
    {
        return percentagesDisplay;
    }

    public VBox getTotalGrades()
    {
        return totalGrades;
    }

    public VBox getCourseInfo()
    {
        return courseInfo;
    }

    public Label getAvgGPA()
    {
        return avgGPA;
    }

    // Displays the total number of each grade and the average GPA. Needs to be 200x195
    private void gradeData()
    {
        VBox data = new VBox(10);

        data.setPadding(new Insets(15, 12,15,12));

        int numA = db.getNumberA();
        int numB = db.getNumberB();
        int numC = db.getNumberC();
        int numD = db.getNumberD();
        int numF = db.getNumberF();
        int numQ = db.getNumberQ();
        double avgGPA = db.getAverageGPA();

        Label labelA = new Label("Numbers of A's:       " + numA);
        labelA.setFont(new Font("Futura", 15));

        Label labelB = new Label("Numbers of B's:       " + numB);
        labelB.setFont(new Font("Futura", 15));

        Label labelC = new Label("Numbers of C's:       " + numC);
        labelC.setFont(new Font("Futura", 15));

        Label labelD = new Label("Numbers of D's:       " + numD);
        labelD.setFont(new Font("Futura", 15));

        Label labelF = new Label("Numbers of F's:       " + numF);
        labelF.setFont(new Font("Futura", 15));

        Label labelQ = new Label("Numbers of Q's:       " + numQ);
        labelQ.setFont(new Font("Futura", 15));

        data.getChildren().addAll(labelA, labelB, labelC, labelD, labelF, labelQ);

        this.totalGrades = data;
    }

    private void emptyGradeData()
    {
        VBox data = new VBox(10);

        data.setPadding(new Insets(15, 12,15,12));


        Label labelA = new Label("Numbers of A's:       0");
        labelA.setFont(new Font("Futura", 15));

        Label labelB = new Label("Numbers of B's:       0");
        labelB.setFont(new Font("Futura", 15));

        Label labelC = new Label("Numbers of C's:       0");
        labelC.setFont(new Font("Futura", 15));

        Label labelD = new Label("Numbers of D's:       0");
        labelD.setFont(new Font("Futura", 15));

        Label labelF = new Label("Numbers of F's:       0");
        labelF.setFont(new Font("Futura", 15));

        Label labelQ = new Label("Numbers of Q's:       0");
        labelQ.setFont(new Font("Futura", 15));

        data.getChildren().addAll(labelA, labelB, labelC, labelD, labelF, labelQ);

        this.totalGrades = data;
    }

    // displays all of the percentages for the course. Needs to be 210x225
    private void setPercentagesDisplay()
    {
        VBox percentages = new VBox(10);
        percentages.setPadding(new Insets(15, 12,15,12));
        DecimalFormat df = new DecimalFormat("##.0");

        double percentPassing = db.getPercentageA() + db.getPercentageB() + db.getPercentageC();

        Label perA = new Label("Percent A:              " + df.format(db.getPercentageA()) + "%");
        perA.setFont(new Font("Futura", 15));

        Label perB = new Label("Percent B:              " + df.format(db.getPercentageB()) + "%");
        perB.setFont(new Font("Futura", 15));

        Label perC = new Label("Percent C:              " + df.format(db.getPercentageC()) + "%");
        perC.setFont(new Font("Futura", 15));

        Label perD = new Label("Percent D:              " + df.format(db.getPercentageD()) + "%");
        perD.setFont(new Font("Futura", 15));

        Label perF = new Label("Percent F:              " + df.format(db.getPercentageF()) + "%");
        perF.setFont(new Font("Futura", 15));

        Label perQ = new Label("Percent Q Drops:  " + df.format(db.getPercentageQ()) + "%");
        perQ.setFont(new Font("Futura", 15));

        Label perPass = new Label("Passing Rate:        " + df.format(percentPassing) + "%");
        perPass.setFont(new Font("Futura", 15));

        percentages.getChildren().addAll(perA, perB, perC, perD, perF, perQ, perPass);

        this.percentagesDisplay = percentages;
    }

    private void emptyPercentages()
    {
        VBox percentages = new VBox(10);
        percentages.setPadding(new Insets(15, 12,15,12));
        DecimalFormat df = new DecimalFormat("##.0");

        double percentPassing = 0;

        Label perA = new Label("Percent A:              0%");
        perA.setFont(new Font("Futura", 15));

        Label perB = new Label("Percent B:              0%");
        perB.setFont(new Font("Futura", 15));

        Label perC = new Label("Percent C:              0%");
        perC.setFont(new Font("Futura", 15));

        Label perD = new Label("Percent D:              0%");
        perD.setFont(new Font("Futura", 15));

        Label perF = new Label("Percent F:              0%");
        perF.setFont(new Font("Futura", 15));

        Label perQ = new Label("Percent Q Drops:  0%");
        perQ.setFont(new Font("Futura", 15));

        Label perPass = new Label("Passing Rate:        0%");
        perPass.setFont(new Font("Futura", 15));

        percentages.getChildren().addAll(perA, perB, perC, perD, perF, perQ, perPass);

        this.percentagesDisplay = percentages;
    }

    // Displays what course we are analyzing
    //needs to be 300x85
    private void setCourseInfo()
    {
        VBox info = new VBox(10);
        info.setPadding(new Insets(15, 15, 15, 15));

        Label course = new Label("Course:  " + db.getCourseSubject() + " " +
                db.getCourseNum());
        course.setFont(new Font("Futura", 18));

        Label professor = new Label("Professor:  " + db.getProfessor());
        professor.setFont(new Font("Futura", 18));

        info.getChildren().addAll(course, professor);

        this.courseInfo = info;
    }

    private void emptyCourseInfo()
    {
        VBox info = new VBox(10);
        info.setPadding(new Insets(15, 15, 15, 15));

        Label course = new Label("Course: None Chosen ");
        course.setFont(new Font("Futura", 18));

        Label professor = new Label("Professor: None Chosen ");
        professor.setFont(new Font("Futura", 18));

        info.getChildren().addAll(course, professor);

        this.courseInfo = info;
    }

    private void setEmptyAvgGPA()
    {
        avgGPA = new Label("Average GPA: ");
    }

    private void setAvgGPA()
    {
        DecimalFormat df = new DecimalFormat("##.000");
        String localAverage = df.format(db.getAverageGPA());
        avgGPA = new Label("Average GPA: " + localAverage);
        avgGPA.setFont(new Font("Futura", 15));
    }
}
