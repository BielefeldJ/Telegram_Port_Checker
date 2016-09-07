package main;

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
    
}
