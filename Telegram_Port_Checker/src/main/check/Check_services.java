package main.check;

import java.io.Serializable;
import java.util.List;

import main.Service;

public class Check_services implements Runnable, Serializable
{

    private final List<Service> services;
    private final String clid;


    

    public Check_services(List services, String clid)
    {
        this.services = services;
        this.clid = clid;
    }

    @Override
    public void run()
    {
        while (!Thread.currentThread().isInterrupted())
        {
            services.stream().forEach((s) ->
            {
                if (!s.portIsOpen() && !s.portIsOpen())  //doppelt hält besser
                {
                    if(!s.isOffline())
                    {
                        main.main.TBOT.fireOfflineMessage(s, clid);
                        s.toggleOnlineStatus();
                    }                    
                }
                else
                {
                    if(s.isOffline())
                    {
                        main.main.TBOT.fireOnlineMessage(s, clid);
                        s.toggleOnlineStatus();
                    }
                }
            });
            try
            {
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
