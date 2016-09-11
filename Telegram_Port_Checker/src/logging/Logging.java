package logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logging
{

    private static FileWriter fw;
    private static BufferedWriter bf;
    private static File f = null;

    public Logging()
    {
        f = new File(date() + "_TPChecker.log");
        try
        {
            fw = new FileWriter(f);
            bf = new BufferedWriter(fw);
            bf.write(getTimestamp());
            bf.write("\nTelegram_Port_Checker started.... \n" );
            bf.flush();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File not found");
        }
        catch (IOException ex)
        {
            System.out.println("IO err @ Log.class");
        }
    }

    public static void log(String message)
    {
        try
        {
            bf.write("[" + getTimestamp() + "] : " + message + "\n");
            bf.flush();
        }
        catch (IOException ex)
        {
            System.out.println("IO err @ Log.class");
        }
    }

    private static String date()
    {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter df;
        df = DateTimeFormatter.ISO_LOCAL_DATE;
        return now.format(df);
    }

    private static String getTimestamp()
    {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter df;
        df = DateTimeFormatter.ISO_DATE_TIME;
        return now.format(df);
    }
}
