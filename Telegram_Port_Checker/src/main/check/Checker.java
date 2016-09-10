package main.check;

import java.util.LinkedList;
import java.util.List;
import main.Service;
import exceptions.NoServicesException;
import exceptions.ServiceNotFoundException;
import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class Checker implements Serializable
{

    private final List<Service> services = new LinkedList<>();
    private transient Thread checking;

    public void startCheck(String clid)
    {
        checking = new Thread(new Check_services(services, clid));
        checking.start();
    }

    public void stopCheck() throws InterruptedException
    {
        if (!checking.isInterrupted())
        {
            checking.interrupt();
            checking.join();
        }
    }

    public void addServices(String s) throws NumberFormatException
    {
        services.add(new Service(s.substring(0, s.indexOf(":")), getPortFromString(s)));

    }

    public String printServices() throws NoServicesException
    {
        if (services.isEmpty())
        {
            throw new NoServicesException("services empty");
        }
        else
        {
            String list = "";
            list = services.stream().map((s) -> s.toString() + "\n").reduce(list, String::concat);
            return list;
        }
    }

    private static int getPortFromString(String s) throws NumberFormatException
    {
        int port = 0;
        port = Integer.parseInt(s.substring(s.indexOf(":") + 1, s.length()));
        return port;
    }

    public void delServices(String service) throws NoServicesException, ServiceNotFoundException
    {
        if (!services.isEmpty())
        {
            if(!services.remove(findService(service)))
            {
                throw new ServiceNotFoundException("Service not found");
            }
        }
        else
        {
            throw new NoServicesException("services empty");
        }
    }

    private Service findService(String service)
    {
        for (Service s : services)
        {
            if (s.toString().equals(service))
            {
                return s;
            }
        }
        return null;
    }

}
