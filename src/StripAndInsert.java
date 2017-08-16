import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by jonathanw on 8/15/17.
 */
public class StripAndInsert
{
    public static void main(String[] args)
    {
        try
        {
            String strippedTextFilename = PDFReader.loadPDF();
            System.out.println("Output file from main: " + strippedTextFilename + "\n\n");

            InfoCondenser condense = new InfoCondenser();
            String condensedFilename = condense.condenser("resources/GradeData.dat");
            DatabaseAPI db = new DatabaseAPI();
            unpackAndInsert(condensedFilename, db);
            db.closeDBConn();
        }
        catch (FileNotFoundException | ClassNotFoundException e)
        {
            System.err.println(e);
            e.printStackTrace();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        catch (NumberFormatException | IOException e)
        {
            System.err.println("Could not access PDF's");
            e.printStackTrace();
        }
    }

    // unpacks the condensed data and inserts into the database using the DB API
    public static void unpackAndInsert(String condensedFileName, DatabaseAPI db)
            throws FileNotFoundException
    {
        System.out.println("\n");
        StringTokenizer tokenizer;
        int courseNum, sectionNum, year, numA, numB, numC, numD, numF, numQdrop;
        double avgGPA;
        String subject, professor, semester;
        boolean honors;

        String infoLine = "";
        String[] elemArray = new String[14]; // array for the elements in the condensed file


        Scanner reader = new Scanner(new File(condensedFileName));
        reader.useDelimiter(";\n");

        while(reader.hasNextLine())
        {
            infoLine = reader.nextLine();
            System.out.println("\nBefore Unpack: " + infoLine);
            tokenizer = new StringTokenizer(infoLine, ";");

            /* There are some dirty inputs that don't have enough information in the line
             * This verifies that there is enough information to be input into the Database */
            try
            {
                for(int i = 0; i < 13; i++)
                    elemArray[i] = tokenizer.nextToken();

                subject = elemArray[0];
                courseNum = Integer.parseInt(elemArray[1]);
                sectionNum = Integer.parseInt(elemArray[2]);
                avgGPA = Double.parseDouble(elemArray[3]);
                professor = elemArray[4];
                numA = Integer.parseInt(elemArray[5]);
                numB = Integer.parseInt(elemArray[6]);
                numC = Integer.parseInt(elemArray[7]);
                numD = Integer.parseInt(elemArray[8]);
                numF = Integer.parseInt(elemArray[9]);
                numQdrop = Integer.parseInt(elemArray[10]);
                semester = elemArray[11];
                year = Integer.parseInt(elemArray[12]);

                // if sectionNum is in the 200 range, it is an honors section
                if(sectionNum >= 200 && sectionNum <= 215)
                    honors = true;
                else
                    honors = false;

                System.out.println("After Unpack " + subject + "-" + courseNum + "-" + sectionNum
                        + "-" + avgGPA + "-" + professor + "-" + numA + "-" + numB  + "-" + numC
                        + "-" + numD + "-" + numF + "-" + numQdrop + "-" + semester + "-" + year);
                db.insert(subject, courseNum, sectionNum, avgGPA, professor, numA, numB, numC,
                        numD, numF, numQdrop, semester, year, honors);
            }
            catch(java.util.NoSuchElementException e)
            {
                continue;
            }

        }
    }
}

