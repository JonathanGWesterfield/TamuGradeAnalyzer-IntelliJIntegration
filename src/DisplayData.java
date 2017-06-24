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

import javax.xml.crypto.Data;
import java.text.DecimalFormat;


public class DisplayData extends Application
{

    private DatabaseAPI db;
    private VBox totalGrades;
    private VBox percentagesDisplay;
    private HBox courseInfo;

    public static void main(String [] args)
    {
        launch(args);
    }

    @Override public void start(Stage primaryStage)
    {

    }

    public DisplayData(DatabaseAPI database)
    {
        this.db = database;
        gradeData();
        setPercentagesDisplay();
        setCourseInfo();
    }

    public VBox getPercentagesDisplay()
    {
        return percentagesDisplay;
    }

    public VBox getTotalGrades()
    {
        return totalGrades;
    }

    public HBox getCourseInfo()
    {
        return courseInfo;
    }

    // Displays the total number of each grade and the average GPA. Needs to be 185x208
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

        Label labelA = new Label("Numbers of A's: " + numA);
        Label labelB = new Label("Numbers of B's: " + numB);
        Label labelC = new Label("Numbers of C's: " + numC);
        Label labelD = new Label("Numbers of D's: " + numD);
        Label labelF = new Label("Numbers of F's: " + numF);
        Label labelQ = new Label("Numbers of Q's: " + numQ);

        data.getChildren().addAll(labelA, labelB, labelC, labelD, labelF, labelQ);

        this.totalGrades = data;
    }

    // displays all of the percentages for the course. Needs to be 185x208
    private void setPercentagesDisplay()
    {
        VBox percentages = new VBox(10);
        percentages.setPadding(new Insets(15, 12,15,12));
        DecimalFormat df = new DecimalFormat("##.0");

        double percentPassing = db.getPercentageA() + db.getPercentageB() + db.getPercentageC();

        Label perA = new Label("Percent A:  " + df.format(db.getPercentageA()) + "%");
        Label perB = new Label("Percent B:  " + df.format(db.getPercentageB()) + "%");
        Label perC = new Label("Percent C:  " + df.format(db.getPercentageC()) + "%");
        Label perD = new Label("Percent D:  " + df.format(db.getPercentageD()) + "%");
        Label perF = new Label("Percent F:  " + df.format(db.getPercentageF()) + "%");
        Label perQ = new Label("Percent Q Drops:  " + df.format(db.getPercentageQ()) + "%");
        Label perPass = new Label("Passing Rate:  " + df.format(percentPassing) + "%");

        percentages.getChildren().addAll(perA, perB, perC, perD, perF, perQ, perPass);

        this.percentagesDisplay = percentages;
    }

    // TODO: SET THE LABELS TO A PARTICULAR FONT, CHECK OUT GARAMOND, FRUTIGER, TAHOMA, VERDANA
    // Displays what course we are analyzing
    private void setCourseInfo()
    {
        HBox info = new HBox(10);
        info.setPadding(new Insets(15, 15, 15, 15));

        Label course = new Label("Course: " + db.getCourseSubject() + " " +
                db.getCourseNum());

        Label professor = new Label("Professor: " + db.getProfessor());

        info.getChildren().addAll(course, professor);

        this.courseInfo = info;
    }
}
