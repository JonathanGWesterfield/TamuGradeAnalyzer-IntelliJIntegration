/**
 * Created by JonathanWesterfield on 2/10/17.
 * This class is nothing but functions that allow the user to access the database
 * through the application. As such it is very long and has very many functions
 */

/* Contains the functions: getAllSubjectDistinct, getAllCourseNumDistinct, getCourseProfessors,
 * getNumASem, getNumBSem, getNumCSem, getNumDSem, getNumFSem, getNumA, getNumB, getNumC, getNumD, getNumF,
 * getNumQDrop, getAvgGPA, getAvgGPASem, getTotalNumStudentsTaught, getPercentA, getPercentB,
  * getPercentC, getPercentD, getPercentF, getPercentQDrops,  getNumSemestersTaught,
  * getProfRawData and the insert into table */

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.lang.*;
import java.lang.String;
import java.io.*;

//TODO: create a function to get the average GPA for each semester for the last 5 years (for line graph)
public class DatabaseAPI
{
    private String connectionString = "jdbc:mysql://localhost:8889/TamuData";
    private String password = "root"; //Chrome11"; // on laptop password is "root"
    private String username = "root";
    private Connection conn;

    public static void main(String[] args) {
        String connectionString = "jdbc:mysql://localhost:8889/TamuData";
        String password = "root";
        String username = "root";

        try
        {
            DatabaseAPI db = new DatabaseAPI();
            /*ArrayList<String> subjects = db.getAllSubjectDistinct();
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
                if(i % 10 == 0)
                {
                    //System.out.println();
                    System.out.print("\n" + rawData.get(i) + " ");
                }
                else
                    System.out.print(rawData.get(i) + " ");
            }

            double avgGPA = db.getAvgGPA("CSCE", 121, "MOORE");*/
            db.getNumASem("CSCE", 121, "MOORE", "fall", 2012);
            db.getPastSemesterGPAs("CSCE", 121, "MOORE");
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

    // gets the number of A's for a class for a certain semester and year
    public int getNumASem(String courseSubject, int courseNum, String professor,
                          String term, int year) throws SQLException
    {
        int totalNumA = 0;
        String query = "SELECT SUM(NumA) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Semester_Term=\"" +
                term + "\" AND Semester_Year=" + year;
        System.out.println("\nCounting number of A's given by this professor in this course");
        Statement countNumA = conn.createStatement();
        ResultSet result = countNumA.executeQuery(query);

        while(result.next())
        {
            totalNumA = result.getInt(1);
            System.out.println("The total number of A's for " + term + " "
                    + year + " is " + totalNumA);
        }
        return totalNumA;
    }

    // gets the number of B's for a class for a certain semester and year
    public int getNumBSem(String courseSubject, int courseNum, String professor,
                          String term, int year) throws SQLException
    {
        int totalNumB = 0;
        String query = "SELECT SUM(NumB) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Semester_Term=\"" +
                term + "\" AND Semester_Year=" + year;
        System.out.println("\nCounting number of B's given by this professor in this course");
        Statement countNumA = conn.createStatement();
        ResultSet result = countNumA.executeQuery(query);

        while(result.next())
        {
            totalNumB = result.getInt(1);
            System.out.println("The total number of B's for " + term + " "
                    + year + " is " + totalNumB);
        }
        return totalNumB;
    }

    // gets the number of C's for a class for a certain semester and year
    public int getNumCSem(String courseSubject, int courseNum, String professor,
                          String term, int year) throws SQLException
    {
        int totalNumC = 0;
        String query = "SELECT SUM(NumC) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Semester_Term=\"" +
                term + "\" AND Semester_Year=" + year;
        System.out.println("\nCounting number of C's given by this professor in this course");
        Statement countNumA = conn.createStatement();
        ResultSet result = countNumA.executeQuery(query);

        while(result.next())
        {
            totalNumC = result.getInt(1);
            System.out.println("The total number of C's for " + term + " "
                    + year + " is " + totalNumC);
        }
        return totalNumC;
    }

    // gets the number of D's for a class for a certain semester and year
    public int getNumDSem(String courseSubject, int courseNum, String professor,
                          String term, int year) throws SQLException
    {
        int totalNumD = 0;
        String query = "SELECT SUM(NumD) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Semester_Term=\"" +
                term + "\" AND Semester_Year=" + year;
        System.out.println("\nCounting number of D's given by this professor in this course");
        Statement countNumA = conn.createStatement();
        ResultSet result = countNumA.executeQuery(query);

        while(result.next())
        {
            totalNumD = result.getInt(1);
            System.out.println("The total number of D's for " + term + " "
                    + year + " is " + totalNumD);
        }
        return totalNumD;
    }

    // gets the number of F's for a class for a certain semester and year
    public int getNumFSem(String courseSubject, int courseNum, String professor,
                          String term, int year) throws SQLException
    {
        int totalNumF = 0;
        String query = "SELECT SUM(NumF) AS total FROM TamuGrades WHERE " +
                "CourseSubject=\"" + courseSubject + "\" AND CourseNum=" +
                courseNum + " AND Professor=\"" + professor + "\" AND Semester_Term=\"" +
                term + "\" AND Semester_Year=" + year;
        System.out.println("\nCounting number of F's given by this professor in this course");
        Statement countNumA = conn.createStatement();
        ResultSet result = countNumA.executeQuery(query);

        while(result.next())
        {
            totalNumF = result.getInt(1);
            System.out.println("The total number of F's for " + term + " "
                    + year + " is " + totalNumF);
        }
        return totalNumF;
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
            System.out.println("The total number of D's is " + totalNumF);
        }
        return totalNumF;
    }

    // calculates the average GPA for the professor of this subject and course
    public double getAvgGPA(String courseSubject, int courseNum, String professor) throws SQLException
    {
        //gets the total number of A's, B's, etc. for this professor
        int numA = getNumA(courseSubject, courseNum, professor);
        int numB = getNumB(courseSubject, courseNum, professor);
        int numC = getNumC(courseSubject, courseNum, professor);
        int numD = getNumD(courseSubject, courseNum, professor);
        int numF = getNumF(courseSubject, courseNum, professor);
        int total = numA + numB + numC + numD + numF;

        // weights the numbers
        numA *= 4;
        numB *= 3;
        numC *= 2;
        numD *= 1; // redundant but is there to help see the pattern
        numF *= 0; // again redundant but is to help see the pattern

        System.out.printf("Weighted Numbers:\nNumA: %d\nNumB: %d\nNumC: %d\n", numA, numB, numC);
        System.out.printf("NumD: %d\nNumF: %d\n" ,numD, numF);

        // adds the weighted points
        double totalPoints = numA + numB + numC + numD + numF;

        // divides by the total to get the average
        totalPoints /= total;

        System.out.println("The average GPA is: " + totalPoints);

        return totalPoints;
    }

    // Calculates the percentage of A's given by a teacher
    public double getPercentA(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int total = getTotalNumStudentsTaught(courseSubject, courseNum, professor);
        int numA = getNumA(courseSubject, courseNum, professor);

        return ((numA / total) * 100);
    }

    // Calculates the percentage of B's given by a teacher
    public double getPercentB(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int total = getTotalNumStudentsTaught(courseSubject, courseNum, professor);
        int numB = getNumB(courseSubject, courseNum, professor);

        return ((numB / total) * 100);
    }

    // Calculates the percentage of C's given by a teacher
    public double getPercentC(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int total = getTotalNumStudentsTaught(courseSubject, courseNum, professor);
        int numC = getNumC(courseSubject, courseNum, professor);

        return ((numC / total) * 100);
    }

    // Calculates the percentage of D's given by a teacher
    public double getPercentD(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int total = getTotalNumStudentsTaught(courseSubject, courseNum, professor);
        int numD = getNumD(courseSubject, courseNum, professor);

        return ((numD / total) * 100);
    }

    // Calculates the percentage of F's given by a teacher
    public double getPercentF(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int total = getTotalNumStudentsTaught(courseSubject, courseNum, professor);
        int numF = getNumF(courseSubject, courseNum, professor);

        return ((numF / total) * 100);
    }

    // Calculates the percentage of Q Drops for a teacher
    public double getPercentQDrops(String courseSubject, int courseNum, String professor) throws SQLException
    {
        int total = getTotalNumStudentsTaught(courseSubject, courseNum, professor);
        int numQDrops = getNumQDrop(courseSubject, courseNum, professor);

        return ((numQDrops / total) * 100);
    }



    //TODO: TEST WHAT HAPPENS WHEN SEARCHING FOR A YEAR THAT DOES NOT EXIST OR NO RECORD OF TEACHER
    // calculates the average GPA for a class for a specific semester
    public double getAvgGPASem(String courseSubject, int courseNum, String professor,
                               String term, int year) throws SQLException
    {
        //gets the total number of A's, B's, etc. for this professor
        int numASem = getNumASem(courseSubject, courseNum, professor, term, year);
        int numBSem = getNumBSem(courseSubject, courseNum, professor, term, year);
        int numCSem = getNumCSem(courseSubject, courseNum, professor, term, year);
        int numDSem = getNumDSem(courseSubject, courseNum, professor, term, year);
        int numFSem = getNumFSem(courseSubject, courseNum, professor, term, year);
        int total = numASem + numBSem + numCSem + numDSem + numFSem;

        System.out.println("Calculated the average GPA for " + term + " "
                        + year + ".");

        // weights the numbers
        numASem *= 4;
        numBSem *= 3;
        numCSem *= 2;
        numDSem *= 1; // redundant but is there to help see the pattern
        numFSem *= 0; // again redundant but is to help see the pattern

        System.out.printf("Weighted Numbers:\nNumA: %d\nNumB: %d\nNumC: %d\n", numASem, numBSem, numCSem);
        System.out.printf("NumD: %d\nNumF: %d\n" ,numDSem, numFSem);

        // in case the records don't exist and return 0
        if(total == 0)
        {
            System.out.println("The average for " + term + " " + year + " GPA is: " + total);
            return 0;
        }


        // adds the weighted points
        double totalPoints = numASem + numBSem + numCSem + numDSem + numFSem;

        // divides by the total to get the average
        totalPoints /= total;

        System.out.println("The average for " + term + " " + year + " GPA is: " + totalPoints);


        return totalPoints;
    }

    // returns an array of the average GPAs for the last 5 years. Is sized accordingly in case a
    // professor has taught for less than 5 years
    public ArrayList<Double> getPastSemesterGPAs(String courseSubject, int courseNum, String professor)
            throws SQLException
    {
        ArrayList<Double> GPAList = new ArrayList<Double>();

        int year = Calendar.getInstance().get(Calendar.YEAR) - 5;

        for(int i = 0; i < 5; i++) // goes back 5 years in the database
        {
            // decision structure for getting the GPA of both terms of a year
            for(int j = 0; j < 2; j++)
            {
                if(j == 0 && getAvgGPASem(courseSubject, courseNum, professor, "SPRING", year) != 0)
                {
                    // Gets the grades for that year during the spring
                    GPAList.add(getAvgGPASem(courseSubject, courseNum, professor, "SPRING", year));
                }
                if(j == 1 && getAvgGPASem(courseSubject, courseNum, professor, "FALL", year) != 0)
                {
                    // Gets the grades for that year during the fall
                    GPAList.add(getAvgGPASem(courseSubject, courseNum, professor, "FALL", year));
                }
            }
            year++;
        }
        //prints the contents of GPAList to make sure they are correct
        System.out.println("\nThe recorded GPA's for each semester are:");
        for(int i = 0; i < GPAList.size(); i++)
        {
            System.out.println(GPAList.get(i));
        }

        return GPAList;
    }

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
                "Avg_GPA, Semester_Term, Semester_Year FROM TamuGrades WHERE " +
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
            String avgGPA = result.getString("Avg_GPA");
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
            data.add(avgGPA);
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
/*What is needed make the database on the laptop CONSTRAINT so that duplicate rows aren't added is:
<BEGIN;

ALTER IGNORE TABLE TamuGrades ADD CONSTRAINT TamuGrades_unique
UNIQUE (CourseSubject, CourseNum, SectionNum, Avg_GPA, Professor,
NumA, Numb, NumC, NumD, NumF, Num_QDrop, Semester_Term, Semester_Year, Honors);>

then use the command <COMMIT>
 */


// is the code for listing all of the raw data in case I want to use it in a different file
/* ArrayList<String> rawData = db.getProfRawData("CSCE", 121, "MOORE");
            for(int i = 0; i < rawData.size(); i++)
            {
                if(i % 10 == 0)
                {
                    //System.out.println();
                    System.out.print("\n" + rawData.get(i) + " ");
                }
                else
                    System.out.print(rawData.get(i) + " ");
            } */