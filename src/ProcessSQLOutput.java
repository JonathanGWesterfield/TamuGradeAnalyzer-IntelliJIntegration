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
            double avgGPA = process.getAvgGPA(db, "CSCE", 121, "MOORE"); // change back to double
            double percentA = process.getPercentA(db, "CSCE", 121, "MOORE");
            double percentB = process.getPercentB(db, "CSCE", 121, "MOORE");
            double percentC = process.getPercentC(db, "CSCE", 121, "MOORE");
            double percentD = process.getPercentD(db, "CSCE", 121, "MOORE");
            double percentF = process.getPercentF(db, "CSCE", 121, "MOORE");
            double percentQDrops = process.getPercentQDrop(db, "CSCE", 121, "MOORE");

            System.out.print("Total percentage out of: ");
            System.out.println(percentA + percentB + percentC + percentD + percentF + percentQDrops);
            double totalPercentage = process.getTotalPercentageOutOf(db, "CSCE", 121, "MOORE");


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

    // gets the percentage of A's out of all students taught in by this professor for this course
    public int getPercentA(DatabaseAPI db, String courseSubject, int courseNum, String professor) throws SQLException
    {
        double numA = db.getNumA(courseSubject, courseNum, professor);
        double totalStudents = db.getTotalNumStudentsTaught(courseSubject, courseNum, professor);

        double percentA = (numA / totalStudents) * 100;
        int intPercentA = (int)Math.round(percentA);
        System.out.println("Percentage of A's " + intPercentA + "%");

        return intPercentA;
    }

    // gets the percentage of B's out of all students taught in by this professor for this course
    public int getPercentB(DatabaseAPI db, String courseSubject, int courseNum, String professor) throws SQLException
    {
        double numB = db.getNumB(courseSubject, courseNum, professor);
        double totalStudents = db.getTotalNumStudentsTaught(courseSubject, courseNum, professor);

        double percentB = (numB / totalStudents) * 100;
        int intPercentB = (int)Math.round(percentB);
        System.out.println("Percentage of B's " + intPercentB + "%");

        return intPercentB;
    }

    // gets the percentage of C's out of all students taught in by this professor for this course
    public int getPercentC(DatabaseAPI db, String courseSubject, int courseNum, String professor) throws SQLException
    {
        double numC = db.getNumC(courseSubject, courseNum, professor);
        double totalStudents = db.getTotalNumStudentsTaught(courseSubject, courseNum, professor);

        double percentC = (numC / totalStudents) * 100;
        int intPercentC = (int)Math.round(percentC);
        System.out.println("Percentage of C's " + intPercentC + "%");

        return intPercentC;
    }

    // gets the percentage of D's out of all students taught in by this professor for this course
    public int getPercentD(DatabaseAPI db, String courseSubject, int courseNum, String professor) throws SQLException
    {
        double numD = db.getNumD(courseSubject, courseNum, professor);
        double totalStudents = db.getTotalNumStudentsTaught(courseSubject, courseNum, professor);

        double percentD = (numD / totalStudents) * 100;
        int intPercentD = (int)Math.round(percentD);
        System.out.println("Percentage of D's " + intPercentD + "%");

        return intPercentD;
    }
    // gets the percentage of F's out of all students taught in by this professor for this course
    public int getPercentF(DatabaseAPI db, String courseSubject, int courseNum, String professor) throws SQLException
    {
        double numF = db.getNumF(courseSubject, courseNum, professor);
        double totalStudents = db.getTotalNumStudentsTaught(courseSubject, courseNum, professor);

        double percentF = (numF / totalStudents) * 100;
        int intPercentF = (int)Math.round(percentF);
        System.out.println("Percentage of F's " + intPercentF + "%");

        return intPercentF;
    }

    // gets the percentage of A's out of all students taught in by this professor for this course
    public int getPercentQDrop(DatabaseAPI db, String courseSubject, int courseNum, String professor) throws SQLException
    {
        double numQDrop = db.getNumQDrop(courseSubject, courseNum, professor);
        double totalStudents = db.getTotalNumStudentsTaught(courseSubject, courseNum, professor);

        double percentQDrop = (numQDrop / totalStudents) * 100;
        int intPercentQDrop = (int)Math.round(percentQDrop);
        System.out.println("Percentage of Q Drops " + intPercentQDrop + "%");

        return intPercentQDrop;
    }

    // calculates the total percentage of everything added up
    public double getTotalPercentageOutOf(DatabaseAPI db, String courseSubject, int courseNum, String professor) throws SQLException
    {
        double numA = db.getNumA(courseSubject, courseNum, professor);
        double numB = db.getNumB(courseSubject, courseNum, professor);
        double numC = db.getNumC(courseSubject, courseNum, professor);
        double numD = db.getNumD(courseSubject, courseNum, professor);
        double numF = db.getNumF(courseSubject, courseNum, professor);
        double numQDrop = db.getNumQDrop(courseSubject, courseNum, professor);
        double totalStudents = db.getTotalNumStudentsTaught(courseSubject, courseNum, professor);

        double percentA = (numA / totalStudents) * 100;
        double percentB = (numB / totalStudents) * 100;
        double percentC = (numC / totalStudents) * 100;
        double percentD = (numD / totalStudents) * 100;
        double percentF = (numF / totalStudents) * 100;
        double percentQDrop = (numQDrop / totalStudents) * 100;

        double totalPercentage = percentA + percentB + percentC + percentD + percentF + percentQDrop;
        System.out.println("\nTotal percentage = " + totalPercentage);

        return totalPercentage;
    }
}
