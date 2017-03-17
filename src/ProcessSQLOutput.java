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
            double percentA = process.getPercentA(db, "CSCE", 121, "MOORE");
            double percentB = process.getPercentB(db, "CSCE", 121, "MOORE");
            double percentC = process.getPercentC(db, "CSCE", 121, "MOORE");
            double percentD = process.getPercentD(db, "CSCE", 121, "MOORE");
            double percentF = process.getPercentF(db, "CSCE", 121, "MOORE");
            double percentQDrops = process.getPercentQDrop(db, "CSCE", 121, "MOORE");

            System.out.print(percentA + percentB + percentC + percentD + percentF + percentQDrops);

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
        averageGPA = (double)Math.round(averageGPA * 1000d) / 1000d;

        System.out.println("Average GPA " + averageGPA);

        return averageGPA;
    }
    //TODO: Add function that calculates percentage of each grade letter given overall

    // gets the percentage of A's out of all students taught in by this professor for this course
    // rounds the output to 3 decimal places
    public double getPercentA(DatabaseAPI db, String courseSubject, int courseNum, String professor) throws SQLException
    {
        double numA = db.getNumA(courseSubject, courseNum, professor);
        double totalStudents = db.getTotalNumStudentsTaught(courseSubject, courseNum, professor);

        double percentA = (numA / totalStudents) * 100;
        percentA = (double)Math.round(percentA * 1000d) / 1000d;
        System.out.println("Percentage of A's " + percentA + "%");

        return percentA;
    }

    // gets the percentage of B's out of all students taught in by this professor for this course
    // rounds the output to 3 decimal places
    public double getPercentB(DatabaseAPI db, String courseSubject, int courseNum, String professor) throws SQLException
    {
        double numB = db.getNumB(courseSubject, courseNum, professor);
        double totalStudents = db.getTotalNumStudentsTaught(courseSubject, courseNum, professor);

        double percentB = (numB / totalStudents) * 100;
        percentB = (double)Math.round(percentB * 1000d) / 1000d;
        System.out.println("Percentage of B's " + percentB + "%");

        return percentB;
    }

    // gets the percentage of C's out of all students taught in by this professor for this course
    // rounds the output to 3 decimal places
    public double getPercentC(DatabaseAPI db, String courseSubject, int courseNum, String professor) throws SQLException
    {
        double numC = db.getNumC(courseSubject, courseNum, professor);
        double totalStudents = db.getTotalNumStudentsTaught(courseSubject, courseNum, professor);

        double percentC = (numC / totalStudents) * 100;
        percentC = (double)Math.round(percentC * 1000d) / 1000d;
        System.out.println("Percentage of C's " + percentC + "%");

        return percentC;
    }

    // gets the percentage of D's out of all students taught in by this professor for this course
    // rounds the output to 3 decimal places
    public double getPercentD(DatabaseAPI db, String courseSubject, int courseNum, String professor) throws SQLException
    {
        double numD = db.getNumD(courseSubject, courseNum, professor);
        double totalStudents = db.getTotalNumStudentsTaught(courseSubject, courseNum, professor);

        double percentD = (numD / totalStudents) * 100;
        percentD = (double)Math.round(percentD * 1000d) / 1000d;
        System.out.println("Percentage of D's " + percentD + "%");

        return percentD;
    }
    // gets the percentage of F's out of all students taught in by this professor for this course
    // rounds the output to 3 decimal places
    public double getPercentF(DatabaseAPI db, String courseSubject, int courseNum, String professor) throws SQLException
    {
        double numF = db.getNumF(courseSubject, courseNum, professor);
        double totalStudents = db.getTotalNumStudentsTaught(courseSubject, courseNum, professor);

        double percentF = (numF / totalStudents) * 100;
        percentF = (double)Math.round(percentF * 1000d) / 1000d;
        System.out.println("Percentage of F's " + percentF + "%");

        return percentF;
    }

    // gets the percentage of A's out of all students taught in by this professor for this course
    // rounds the output to 3 decimal places
    public double getPercentQDrop(DatabaseAPI db, String courseSubject, int courseNum, String professor) throws SQLException
    {
        double numQDrop = db.getNumA(courseSubject, courseNum, professor);
        double totalStudents = db.getTotalNumStudentsTaught(courseSubject, courseNum, professor);

        double percentQDrop = (numQDrop / totalStudents) * 100;
        percentQDrop = (double)Math.round(percentQDrop * 1000d) / 1000d;
        System.out.println("Percentage of Q Drops " + percentQDrop + "%");

        return percentQDrop;
    }





}
