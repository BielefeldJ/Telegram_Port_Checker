package main;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        Thread testt = new Thread(ch.task);
        testt.start();
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
            testt.interrupt();
            testt.join();
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        

  
    }

}
