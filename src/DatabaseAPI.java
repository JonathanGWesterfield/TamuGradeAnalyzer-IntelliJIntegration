/**
 * Created by JonathanWesterfield on 2/10/17.
 * This class is nothing but functions that allow the user to access the database
 * through the application. As such it is very long and has very many functions
 */

import java.io.IOException;
import java.sql.*;

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
            db.selectAllSubjectDistinct();
            db.selectAllCourseNumDistinct("MATH");
            db.selectCourseProfessors("MATH", 152);
            db.selectNumA("CSCE", 121, "MOORE");
            db.selectNumB("CSCE", 121, "MOORE");
            db.selectNumC("CSCE", 121, "MOORE");
            db.selectNumD("CSCE", 121, "MOORE");
            db.selectNumF("CSCE", 121, "MOORE");
            db.selectNumQDrop("CSCE", 121, "MOORE");
            db.getTotalNumStudentsTaught("CSCE", 121, "MOORE");
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

    /* shows a list of all subjects */
    public void selectAllSubjectDistinct() throws SQLException
    {
        //FIXME: CHANGE THIS BACK TO TAMURAWDATA FOR USE ON LAPTOP
        String query1 = "SELECT DISTINCT CourseSubject FROM TamuGrades";
        System.out.println("\nSelecting Distinct From Subject");
        Statement selectDistinctSubject = conn.createStatement();
        ResultSet result1 = selectDistinctSubject.executeQuery(query1);

        //printing out the result of the SQL query
        int count = 1;
        while(result1.next())
        {
            String subject = result1.getString("CourseSubject");
            // System.out.println(count + " " + subject);
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
    }

    //lists all course numbers under a specific subject
    public void selectAllCourseNumDistinct(String courseSubject) throws SQLException
    {
        //FIXME: CHANGE THIS BACK TO TAMURAWDATA FOR USE ON LAPTOP
        String query1 = "SELECT DISTINCT CourseNum FROM TamuGrades " +
                "WHERE CourseSubject=\"" + courseSubject + "\"";
        System.out.println("\nSelecting Distinct From CourseNum");
        Statement selectDistinctCourseNum = conn.createStatement();
        ResultSet result1 = selectDistinctCourseNum.executeQuery(query1);

        //printing out the result of the SQL query
        int count = 1;
        while(result1.next())
        {
            int courseNum = result1.getInt("CourseNum");
            // System.out.println(count + " " + subject);
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
    }

    //lists all professors under a specific subject and course number
    public void selectCourseProfessors(String courseSubject, int courseNum) throws SQLException
    {
        //FIXME: CHANGE THIS BACK TO TAMURAWDATA FOR USE ON LAPTOP
        String query1 = "SELECT DISTINCT Professor From TamuGrades " +
                "WHERE CourseSubject=\"" + courseSubject + "\" AND CourseNum=" + courseNum;
        System.out.println("\nLooking for professors of this subject and course");
        Statement getProfessors = conn.createStatement();
        ResultSet result1 = getProfessors.executeQuery(query1);

        //printing out the result of the SQL query
        int count = 1;
        while(result1.next())
        {
            String professor = result1.getString("Professor");
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
    }

    // counts total number of A's given by a professor in a specific subject and course number
    public int selectNumA(String courseSubject, int courseNum, String professor) throws SQLException
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
    public int selectNumB(String courseSubject, int courseNum, String professor) throws SQLException
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
    public int selectNumC(String courseSubject, int courseNum, String professor) throws SQLException
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
    public int selectNumD(String courseSubject, int courseNum, String professor) throws SQLException
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
    public int selectNumF(String courseSubject, int courseNum, String professor) throws SQLException
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
            System.out.println("The total number of D's is " + totalNumF);
        }
        return totalNumF;
    }

    //FIXME: ADD A public int countNumSemestersTaught()

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
    public int selectNumQDrop(String courseSubject, int courseNum, String professor) throws SQLException
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

    //FIXME: ADD FUNCTION THAT CALCULATES TOTAL NUMBER OF A'S, B'S, ETC FOR A TEACHER

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
            System.out.println("Could not create insert statement");
            e.printStackTrace();
        }
        catch(Exception e)
        {
            System.err.println("Could not Insert into Database");
            e.printStackTrace();
        }
    }

    public void closeDBConn() throws SQLException
    {
        System.out.println("\nClosing Database connection");
        conn.close();
        System.out.println("\nDatabase connection closed");
    }

}
