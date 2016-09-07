package main;

import main.check.Checker;
import java.util.Scanner;
import logging.Logging;

public class main
{

    public static void main(String[] args)
    {
        new Logging(); // initializiere logger
        boolean exit = false;
        Scanner sc = null;    
        String[] test = new String[2];
        test[0] = "ts3.bielefeld-server.de:9399";
        test[1] = "ts3.bielefeld-server.de:30033";

        try
        {
            Checker.getServices(test);
        }
        catch(NumberFormatException ex)
        {
            System.out.println("Wrong port");
        }
        Checker.printServices();
        Checker.startCheck();
        while (!exit)
        {
            System.out.println("Running.. type q to quit!");
            sc = new Scanner(System.in);
            if ("q".equals(sc.next()))
            {
                exit = true;
            }
        }
        
        sc.close();
        try
        {
            Checker.stopCheck();
        }
        catch (InterruptedException ex)
        {
            System.out.println("Error join @ main.class");
        }
        System.exit(0);

    }

}
