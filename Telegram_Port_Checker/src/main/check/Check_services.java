package main.check;

import java.io.Serializable;
import java.util.List;

import main.Service;


/**
 *
 * @author Admin
 */
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
                if (!s.portIsOpen())
                {
                    if(!s.isMessage_sended())
                    {
                        main.main.TBOT.fireOfflineMessage(s, clid);
                        s.toggleMessageSended();
                    }                    
                }
                else
                {
                    if(s.isMessage_sended())
                    {
                        main.main.TBOT.fireOnlineMessage(s, clid);
                        s.toggleMessageSended();
                    }
                }
            });
            try
            {
                Thread.sleep(10000);
             //   System.out.println("Checked service from " + clid);
            }
            catch (InterruptedException ex)
            {
                System.out.println("Thread closed....");                
                Thread.currentThread().interrupt();
            }
        }
    }
}
