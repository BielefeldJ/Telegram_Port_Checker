package main.check;

import java.util.LinkedList;
import java.util.List;
import main.Service;

/**
 *
 * @author Admin
 */
public class Checker
{

    private final List<Service> services = new LinkedList<>();
    private Thread checking;

    public void startCheck(String clid)
    {
        checking = new Thread(new Check_services(services,clid));
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

    public void printServices()
    {
        services.stream().forEach(System.out::println);
    }

    private static int getPortFromString(String s) throws NumberFormatException
    {
        int port = 0;
        port = Integer.parseInt(s.substring(s.indexOf(":") + 1, s.length()));
        return port;
    }

}
