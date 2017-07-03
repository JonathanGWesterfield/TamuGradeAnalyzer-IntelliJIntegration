/**
 * Created by jonathanw on 7/3/17.
 */

import javafx.scene.control.*;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.text.*;
import javafx.application.*;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

public class DisplayAll
{

    public static void main(String[] args)
    {
        try
        {
            DatabaseAPI db = new DatabaseAPI();

            DropDownList dropList = new DropDownList(db);

            DisplayData displayData = new DisplayData(dropList.getReturndbAPI());

            GradeChart gradeChart = new GradeChart(dropList.getReturndbAPI());

            GridPane grid = new GridPane();
        }
        catch(SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
            AlertError.showSQLException();
        }
    }

}
