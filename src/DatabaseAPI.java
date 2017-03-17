/**
 * Created by JonathanWesterfield on 2/10/17.
 * This class is nothing but functions that allow the user to access the database
 * through the application. As such it is very long and has very many functions
 */

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.io.*;

//TODO: add a function that counts how many semesters a professor has taught a particular course
public class DatabaseAPI
{
    private String connectionString = "jdbc:mysql://localhost:8889/TamuData";
    private String password = "Chrome11"; // on laptop password is "root"
    private String username = "root";
    private Connection conn;

    public static void main(String[] args) {
        String connectionString = "jdbc:mysql://localhost:8889/TamuData";
        String password = "root";
        String username = "root";

        try
        {
            DatabaseAPI db = new DatabaseAPI();
            ArrayList<String> subjects = db.getAllSubjectDistinct();
            for(int i = 0; i < subjects.size(); i++)
            {
                System.out.println(subjects.get(i));
            }

            ArrayList<Integer> courseNumbers = db.getAllCourseNumDistinct("MATH");
            for(int i = 0; i < courseNumbers.size(); i++)
            {
                System.out.println(courseNumbers.get(i));
            }

            ArrayList<String> professors = db.getCourseProfessors("CSCE", 121);
            for(int i = 0; i < professors.size(); i++)
            {
                System.out.println(professors.get(i));
            }

            db.getCourseProfessors("MATH", 152);
            db.getNumA("CSCE", 121, "MOORE");
            db.getNumB("CSCE", 121, "MOORE");
            db.getNumC("CSCE", 121, "MOORE");
            db.getNumD("CSCE", 121, "MOORE");
            db.getNumF("CSCE", 121, "MOORE");
            db.getNumQDrop("CSCE", 121, "MOORE");
            db.getTotalNumStudentsTaught("CSCE", 121, "MOORE");
            System.out.println("Number Semesters " + db.getNumSemestersTaught("CSCE", 121, "MOORE"));

            ArrayList<String> rawData = db.getProfRawData("CSCE", 121, "MOORE");
            for(int i = 0; i < rawData.size(); i++)
            {
                if(i % 9 == 0)
                {
                    //System.out.println();
                    System.out.print("\n" + rawData.get(i) + " ");
                }
                else
                    System.out.print(rawData.get(i) + " ");
            }
            db.closeDBConn();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    // class constructor that throws an exception to the function caller
    DatabaseAPI() throws java.sql.SQLException, java.lang.ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");
        this.conn = DriverManager.getConnection(connectionString, username, password);
        System.out.println("Database connection established");
    }

    public void createDBConn() throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");
        this.conn = DriverManager.getConnection(connectionString, username, password);
        System.out.println("Database connection established");
        return;
    }

    /* returns an arraylist of all subjects in database in alphabetical order*/
    public ArrayList<String> getAllSubjectDistinct() throws SQLException
    {
        //FIXME: CHANGE THIS BACK TO TAMURAWDATA FOR USE ON LAPTOP
        String query1 = "SELECT DISTINCT CourseSubject FROM TamuGrades ORDER BY CourseSubject ASC";
        System.out.println("\nSelecting Distinct From Subject");
        Statement selectDistinctSubject = conn.createStatement();
        ResultSet result1 = selectDistinctSubject.executeQuery(query1);

        //printing out the result of the SQL query
        int count = 1;
        ArrayList<String> allSubjects = new ArrayList<String>();
        while(result1.next())
        {
            String subject = result1.getString("CourseSubject");
            allSubjects.add(subject);
            System.out.printf("%d\t%s\n", count, subject);
            count++;
        }

        System.out.println();

        String query2 = "SELECT COUNT(DISTINCT CourseSubject) FROM TamuGrades";
        System.out.println("Counting number of subjects in Database");
        Statement countCourses = conn.createStatement();
        ResultSet result2 = countCourses.executeQuery(query2);

        System.out.println("Getting number of subjects");

        //printing out the result of the SQL query
        while(result2.next())
        {
            int numSubjects = result2.getInt(1);
            System.out.println("Number of Subjects in database = " + numSubjects);
        }
        return allSubjects;
    }

    //returns an arraylist of all course numbers under a specific subject
    public ArrayList<Integer> getAllCourseNumDistinct(String courseSubject) throws SQLException
    {
        //FIXME: CHANGE THIS BACK TO TAMURAWDATA FOR USE ON LAPTOP
        String query1 = "SELECT DISTINCT CourseNum FROM TamuGrades " +
                "WHERE CourseSubject=\"" + courseSubject + "\"";
        System.out.println("\nSelecting Distinct From CourseNum");
        Statement selectDistinctCourseNum = conn.createStatement();
        ResultSet result1 = selectDistinctCourseNum.executeQuery(query1);

        //printing out the result of the SQL query
        int count = 1;
        ArrayList<Integer> allCourseNums = new ArrayList<Integer>();
        while(result1.next())
        {
            int courseNum = result1.getInt("CourseNum");
            allCourseNums.add(courseNum);
            System.out.printf("%d\t%d\n", count, courseNum);
            count++;
        }

        System.out.println();

        String query2 = "SELECT COUNT(DISTINCT CourseNum) AS total FROM TamuGrades " +
                "WHERE CourseSubject=\"" + courseSubject + "\"";
        System.out.println("Counting number of course numbers in Database where " +
                "subject is " + courseSubject);
        Statement countCourses = conn.createStatement();
        ResultSet result2 = countCourses.executeQuery(query2);

        System.out.println("Getting number of Course Numbers");

        //printing out the result of the SQL query
        while(result2.next())
        {
            int numCourseNums = result2.getInt(1);
            System.out.println("Number of CourseNums in database = " + numCourseNums +
                " where Course Subject is " + courseSubject);
        }
        return allCourseNums;
    }

    // Returns an arraylist of the professors in alphabetical order
    public ArrayList<String> getCourseProfessors(String courseSubject, int courseNum) throws SQLException
    {
        //FIXME: CHANGE THIS BACK TO TAMURAWDATA FOR USE ON LAPTOP
        String query1 = "SELECT DISTINCT Professor FROM TamuGrades WHERE CourseSubject=\""
                 + courseSubject + "\" AND CourseNum=" + courseNum + " ORDER BY Professor ASC";
        System.out.println("\nLooking for professors of this subject and course");
        Statement getProfessors = conn.createStatement();
        ResultSet result1 = getProfessors.executeQuery(query1);

        //printing out the result of the SQL query
        int count = 1;
        ArrayList<String> allCourseProfessors = new ArrayList<>();
        while(result1.next())
        {
            String professor = result1.getString("Professor");
            allCourseProfessors.add(professor);
            System.out.printf("%d\t%s\n", count, professor);
            count++;
        }

        System.out.println();

        String query2 = "SELECT COUNT(DISTINCT Professor) AS total FROM TamuGrades " +
                "WHERE CourseSubject=\"" + courseSubject + "\" AND CourseNum=" + courseNum;
        System.out.println("Counting number of professors teaching this course");
        Statement countProfessors = conn.createStatement();
        ResultSet result2 = countProfessors.executeQuery(query2);

        //printing out the result of the SQL query
        while(result2.next())
        {
            int totalNumProfessors = result2.getInt(1);
            System.out.println("Number of Professors teaching this course = "
                    + totalNumProfessors + "\n");
        }
        return allCourseProfessors;
    }

    // counts total number of A's given by a professor in a specific subject and course number
    public int getNumA(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int totalNumA = 0;
        String query = "SELECT SUM(NumA) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\"";
        System.out.println("\nCounting number of A's given by this professor in this course");
        Statement countNumA = conn.createStatement();
        ResultSet result = countNumA.executeQuery(query);

        while(result.next())
        {
            totalNumA = result.getInt(1);
            System.out.println("The total number of A's is " + totalNumA);
        }
        return totalNumA;
    }

    // counts total number of B's given by a professor in a specific subject and course number
    public int getNumB(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int totalNumB = 0;
        String query = "SELECT SUM(NumB) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\"";
        System.out.println("\nCounting number of B's given by this professor in this course");
        Statement countNumA = conn.createStatement();
        ResultSet result = countNumA.executeQuery(query);

        while(result.next())
        {
            totalNumB = result.getInt(1);
            System.out.println("The total number of B's is " + totalNumB);
        }
        return totalNumB;
    }

    // counts total number of C's given by a professor in a specific subject and course number
    public int getNumC(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int totalNumC = 0;
        String query = "SELECT SUM(NumC) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\"";
        System.out.println("\nCounting number of C's given by this professor in this course");
        Statement countNumC = conn.createStatement();
        ResultSet result = countNumC.executeQuery(query);

        while(result.next())
        {
            totalNumC = result.getInt(1);
            System.out.println("The total number of C's is " + totalNumC);
        }
        return totalNumC;
    }

    // counts total number of D's given by a professor in a specific subject and course number
    public int getNumD(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int totalNumD = 0;
        String query = "SELECT SUM(NumD) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\"";
        System.out.println("\nCounting number of D's given by this professor in this course");
        Statement countNumD = conn.createStatement();
        ResultSet result = countNumD.executeQuery(query);

        while(result.next())
        {
            totalNumD = result.getInt(1);
            System.out.println("The total number of D's is " + totalNumD);
        }
        return totalNumD;
    }

    // counts total number of D's given by a professor in a specific subject and course number
    public int getNumF(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int totalNumF = 0;
        String query = "SELECT SUM(NumF) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\"";
        System.out.println("\nCounting number of F's given by this professor in this course");
        Statement countNumF = conn.createStatement();
        ResultSet result = countNumF.executeQuery(query);

        while(result.next())
        {
            totalNumF = result.getInt(1);
            System.out.println("The total number of F's is " + totalNumF);
        }
        return totalNumF;
    }

    //TODO: add a function to display raw data for a professor

    public int getTotalNumStudentsTaught(String subject, int courseNum,
                                         String professor) throws SQLException
    {
        String query = "SELECT SUM(Num_QDrop + NumA + NumB + NumC + NumD + NumF) FROM" +
                " TamuGrades WHERE CourseSubject=\"" + subject + "\" AND CourseNum=" + courseNum +
                 " AND Professor=\"" + professor + "\"";
        System.out.println("\nCounting number of students this professor has taught");
        Statement getTotalStudents = conn.createStatement();
        ResultSet result = getTotalStudents.executeQuery(query);

        int totalStudents = 0;
        while(result.next())
        {
            totalStudents = result.getInt(1);
            System.out.println("The total number of students this professor has taught is " + totalStudents);
        }
        return totalStudents;
    }

    // counts total number of QDrops given by a professor in a specific subject and course number
    public int getNumQDrop(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int totalQDrop = 0;
        String query = "SELECT SUM(Num_QDrop) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\"";
        System.out.println("\nCounting number of Q Drops given by this professor in this course");
        Statement countQDrop = conn.createStatement();
        ResultSet result = countQDrop.executeQuery(query);

        while(result.next())
        {
            totalQDrop = result.getInt(1);
            System.out.println("The total number of Q Drops is " + totalQDrop);
        }
        return totalQDrop;
    }

    // Counts the number of semesters a professor has taught a class for a specific subject
    // and specific course number
    public int getNumSemestersTaught(String subject, int courseNum, String professor) throws SQLException
    {
        String query = "SELECT COUNT(DISTINCT Semester_Term, Semester_Year) FROM " +
                "TamuGrades WHERE CourseSubject=\"" + subject + "\" and CourseNum=" +
                courseNum + " AND Professor=\"" + professor +"\"";
        Statement getSemestersTaught = conn.createStatement();
        ResultSet result = getSemestersTaught.executeQuery(query);

        int totalSemestersTaught = 0;
        while(result.next())
        {
            totalSemestersTaught = result.getInt(1);
            System.out.println(professor + " has taught this course a minimum " +
                    "of " + totalSemestersTaught + " times!\nIt is possible he " +
                    "has taught more semesters but this is as many as the database has");
        }

        return totalSemestersTaught;
    }

    public ArrayList<String> getProfRawData(String subject, int courseNum, String professor) throws SQLException
    {
        String query = "SELECT professor, NumA, NumB, NumC, NumD, NumF, Num_QDrop, " +
                "Semester_Term, Semester_Year FROM TamuGrades WHERE " +
                "CourseSubject=\"" + subject + "\" AND CourseNum=" + courseNum + " AND " +
                "professor=\"" + professor + "\" ORDER BY Semester_Year, Semester_Term DESC";
        Statement getRawData = conn.createStatement();
        ResultSet result = getRawData.executeQuery(query);

        ArrayList<String> data = new ArrayList<String>();
        while(result.next())
        {
            String resultProfessor = result.getString("Professor");
            String NumA = result.getString("NumA");
            String NumB = result.getString("NumB");
            String NumC = result.getString("NumC");
            String NumD = result.getString("NumD");
            String NumF = result.getString("NumF");
            String Num_QDrop = result.getString("Num_QDrop");
            String semester = result.getString("Semester_Term");
            String year = result.getString("Semester_Year");

            System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t\n", resultProfessor,
                    NumA, NumB, NumC, NumD, NumF, Num_QDrop, semester, year);

            data.add(resultProfessor);
            data.add(NumA);
            data.add(NumB);
            data.add(NumC);
            data.add(NumD);
            data.add(NumF);
            data.add(Num_QDrop);
            data.add(semester);
            data.add(year);
        }
        return data;
    }

    //inserts all of the information given into the database table
    public void insert(String Subject, int courseNum, int sectionNum, Double avgGPA,
                       String professor, int numA, int numB, int numC, int numD, int numF, int numQdrop,
                       String termSemester, int termYear, boolean honors) // throws java.sql.SQLException
    {
        try
        {
            //FIXME: CHANGE THIS BACK TO TAMURAWDATA FOR USE ON LAPTOP
            String query = "INSERT INTO TamuGrades " /*TamuRawData*/ + "VALUES (\"" + Subject + "\", " +
                    courseNum + ", " + sectionNum + ", " + avgGPA + ", \"" + professor + "\", "
                    + numA + ", " + numB + ", " + numC + ", " + numD + ", " + numF
                    + ", " + numQdrop + ", \"" + termSemester + "\", " + termYear + ", " + honors + ") ";
            System.out.println("Inserting query\n" + query);
            PreparedStatement insertStatement = conn.prepareStatement(query);
            insertStatement.execute();
        }
        catch(SQLException e)
        {
            // this small block turns the exception into a string to be compared
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionMessage = sw.toString();

            if(exceptionMessage.contains("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrity" +
                    "ConstraintViolationException"))
            {
                System.out.println("\n" + e);
                System.out.println("\nThis entry is already in the table\nIGNORING");
            }
            else
            {
                System.out.println("\n" + e);
                System.out.println("Could not create insert statement");
                e.printStackTrace();
            }
        }
        catch(Exception e)
        {
            // System.out.println("\n" + e);
            System.err.println("Could not Insert into Database because something strange happened");
            // e.printStackTrace();
        }
    }

    public void closeDBConn() throws SQLException
    {
        System.out.println("\nClosing Database connection");
        conn.close();
        System.out.println("\nDatabase connection closed");
    }

}
/*TODO: What is needed make the database on the laptop CONSTRAINT so that duplicate rows aren't added is
<BEGIN;

ALTER IGNORE TABLE TamuGrades ADD CONSTRAINT TamuGrades_unique
UNIQUE (CourseSubject, CourseNum, SectionNum, Avg_GPA, Professor,
NumA, Numb, NumC, NumD, NumF, Num_QDrop, Semester_Term, Semester_Year, Honors);>

then use the command <COMMIT>
 */