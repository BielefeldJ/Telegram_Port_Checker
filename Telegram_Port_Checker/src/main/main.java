package main;

import main.check.Checker;
import java.util.Scanner;

public class main
{

    public static void main(String[] args)
    {
        boolean exit = false;
        Scanner sc = null;
        Checker ch = new Checker();

        String[] test = new String[2];
        test[0] = "ts3.bielefeld-server.de:9399";
        test[1] = "ts3.bielefeld-server.de:30033";

        ch.getServices(test);
        ch.printServices();
        ch.startCheck();
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
            ch.stopCheck();
        }
        catch (InterruptedException ex)
        {
            System.out.println("Error join @ main.class");
        }

    }

}
