/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.check;

import java.util.List;
import main.Service;

/**
 *
 * @author Admin
 */
public class Check_services implements Runnable
{

    List<Service> services;
    boolean running = true;

    public Check_services(List services)
    {
        this.services = services;
    }

    @Override
    public void run()
    {

        while (!Thread.currentThread().isInterrupted())
        {
            for (Service s : services)
            {
                if (!s.portIsOpen())
                {
                    // fire Telegram Message
                }
            }
            try
            {
                System.out.println("Waiting..");
                Thread.sleep(10000);
            }
            catch (InterruptedException ex)
            {
                System.out.println("Thread closed....");
                Thread.currentThread().interrupt();
            }
        }
    }
}
