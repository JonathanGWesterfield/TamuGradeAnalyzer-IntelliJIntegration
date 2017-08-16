/**
 * Created by JonathanWesterfield on 12/24/16.
 */

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.text.*;
import javafx.application.*;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.concurrent.*;
import javax.xml.crypto.Data;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.xml.crypto.Data;
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
