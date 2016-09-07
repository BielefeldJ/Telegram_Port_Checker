/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logging;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jensb
 */
public class Logging
{
    private static FileOutputStream fos;
    private static DataOutputStream dos;
    private static File f = null;
    
    public Logging()
    {
        f = new File(date() + "_TPChecker.log");
        try
        {
            fos = new FileOutputStream(f);
            dos = new DataOutputStream(fos);
            dos.writeChars("Telegram_Port_Checker started....");
            dos.flush();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File not found");
            ex.printStackTrace();
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
            dos.writeChars(message);
            dos.flush();
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
}
