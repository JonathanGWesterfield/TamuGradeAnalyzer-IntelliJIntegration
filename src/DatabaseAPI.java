/**
 * Created by JonathanWesterfield on 2/10/17.
 */

import java.io.IOException;
import java.sql.*;

public class DatabaseAPI {


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
            //db.createDBConn(connectionString, username, password);
            db.insert("PHYS", 201, 512,
                    2.471, "KO", 3, 7,
                    4, 1, 2, 5,
                    "SPRING", 2016, false);
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
        return;
    }

    public void createDBConn() throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");
        this.conn = DriverManager.getConnection(connectionString, username, password);
        System.out.println("Database connection established");
        return;
    }

    public void closeDBConn() throws SQLException
    {
        conn.close();
    }

    /* REDUNDANT CODE
     public Connection createDBConn(String connString, String username, String password)// throws java.sql.SQLException
    {

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection1 = DriverManager.getConnection(connString, username, password);
        System.out.println("Database connection established");
        return connection1;

        //Try Catch block and return NULL is for testing only, exception propagates through the stack
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection1 = DriverManager.getConnection(connString, username, password);
            System.out.println("Database connection established");
            return connection1;
        }
        catch (Exception e)
        {
            System.err.println("Could not connect to TamuData database");
            e.printStackTrace();
        }
        return null;
    } REDUNDANT CODE  */



    public void insert(String Subject, int courseNum, int sectionNum, Double avgGPA,
                       String professor, int numA, int numB, int numC, int numD, int numF, int numQdrop,
                       String termSemester, int termYear, boolean honors) // throws java.sql.SQLException
    {
        try
        {
            //FIXME: CHANGE THIS BACK TO TAMURAWDATA FOR USE ON LAPTOP
            String query = "INSERT INTO TamuGrades " /*TamuRawData*/ + "VALUES (\"" + Subject + "\", " + courseNum + ", " + sectionNum
                    + ", " + avgGPA + ", \"" + professor + "\", " + numA + ", " + numB + ", " + numC + ", "
                    + numD + ", " + numF + ", " + numQdrop + ", \"" + termSemester + "\", " + termYear
                    + ", " + honors + ") ";
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
}





