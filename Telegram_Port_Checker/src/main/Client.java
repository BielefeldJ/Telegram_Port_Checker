package main;

import exceptions.NoServicesException;
import exceptions.ServiceNotFoundException;
import java.io.Serializable;
import logging.Logging;
import main.check.Checker;

public class Client implements Serializable
{
    private String clientid;
    private String username;

    private final Checker check = new Checker();
    
    public Client(String clientid, String username)
    {
        this.clientid = clientid;
        this.username = username;
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
    public String listServices() throws NoServicesException
    {
        return check.printServices();
    }

    public void delService(String serivce) throws NoServicesException, ServiceNotFoundException
    {
        check.delServices(serivce);
    }
    
}
