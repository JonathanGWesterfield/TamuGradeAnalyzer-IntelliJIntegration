/**
 * Created by JonathanWesterfield on 12/24/16.
 */

import javafx.application.*;
import javafx.stage.Stage;
import javafx.concurrent.*;
import javax.xml.crypto.Data;

import java.sql.SQLException;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        try
        {
            DatabaseAPI db = new DatabaseAPI();
            DisplayAll display = new DisplayAll(db);
            display.showPrimaryStage();
        }
        catch (SQLException e)
        {
            AlertError.showSQLException();
        }
        catch (ClassNotFoundException e)
        {
            AlertError.showClassNotFoundException();
        }
    }
}
