package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import logging.Logging;
import main.check.Checker;

public class Client
{
    private String clientid;
    private Checker check = new Checker();
    
    public Client(String clientid)
    {
        this.clientid = clientid;
    }

    public String getClientid()
    {
        return clientid;
    }


    public void setClientid(String clientid)
    {
        this.clientid = clientid;
    }
    
    public void addService(String serivce)
    {
        check.addServices(serivce);
    }
    
    public void start()
    {
        check.startCheck(clientid);
    }
    public void stop()
    {
        try
        {
            check.stopCheck();
        }
        catch (InterruptedException ex)
        {
            Logging.log("Error stop command clid: " + clientid);
            System.out.println("Error stop command clid: " + clientid);
        }
    }
    
}
