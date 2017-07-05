import com.sun.org.apache.regexp.internal.RE;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

/**
 * Created by jonathanw on 7/4/17.
 */
public class RefreshedDisplay
{

    private DatabaseAPI db;
    private GradeChart gradeChart;
    private DisplayData displayData;

    private VBox totalGrades;
    private VBox percentagesDisplay;
    private VBox courseInfo;
    private LineChart<String, Number> lineChart;
    private BarChart<String, Number> barChart;


    // public getter functions
    public VBox getTotalGrades()
    {
        return totalGrades;
    }

    public VBox getPercentagesDisplay()
    {
        return percentagesDisplay;
    }

    public VBox getCourseInfo()
    {
        return courseInfo;
    }

    public LineChart<String, Number> getLineChart()
    {
        return lineChart;
    }

    public BarChart<String, Number> getBarChart()
    {
        return barChart;
    }

    public GradeChart getGradeChart()
    {
        return gradeChart;
    }

    public DisplayData getDisplayData()
    {
        return displayData;
    }

    public RefreshedDisplay()
    {
        // Defualt constructor
        System.err.println("Default ReshreshedDisplay Constructor called");
    }

    public RefreshedDisplay(DatabaseAPI db)
    {
        System.out.println("Real RefreshedDisplay constructor called");

        try
        {
            gradeChart = new GradeChart(db, false);
            this.lineChart = gradeChart.getLineChart();
            this.barChart = gradeChart.getBarChart();

            displayData = new DisplayData(db, false);
            this.totalGrades = displayData.getTotalGrades();
            this.percentagesDisplay = displayData.getPercentagesDisplay();
            this.courseInfo = displayData.getCourseInfo();

            return;
        }
        catch(SQLException e)
        {
            AlertError.showSQLException();
        }
        catch(ClassNotFoundException e)
        {
            AlertError.showClassNotFoundException();
        }

    }
}
