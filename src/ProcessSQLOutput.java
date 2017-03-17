import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by JonathanW on 3/17/17.
 */
public class ProcessSQLOutput
{
    //TODO: Add function that calculates average GPA
    public static void main(String[] args)
    {
        try
        {
            DatabaseAPI db = new DatabaseAPI();
            ProcessSQLOutput process = new ProcessSQLOutput();
            double avgGPA = process.getAvgGPA(db, "CSCE", 121, "MOORE");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    ProcessSQLOutput() {}

    public double getAvgGPA(DatabaseAPI db, String courseSubject, int courseNum, String professor) throws SQLException
    {
        double numA = db.getNumA(courseSubject, courseNum, professor);
        double numB = db.getNumB(courseSubject, courseNum, professor);
        double numC = db.getNumC(courseSubject, courseNum, professor);
        double numD = db.getNumD(courseSubject, courseNum, professor);
        double numF = db.getNumF(courseSubject, courseNum, professor);
        double numQDrop = db.getNumQDrop(courseSubject, courseNum, professor);
        double totalStudents = db.getTotalNumStudentsTaught(courseSubject, courseNum, professor);

        totalStudents -= numQDrop;
        System.out.println("total Grades " + totalStudents);

        double averageGPA =  ((numA * 4) + (numB * 3) + (numC * 2) + (numD * 1) + (numF * 0)) / totalStudents;

        System.out.println("Average GPA " + averageGPA);

        return averageGPA;
    }
    //TODO: Add function that calculates percentage of each grade letter given overall
}
