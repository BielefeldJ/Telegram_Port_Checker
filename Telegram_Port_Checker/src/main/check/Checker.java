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

    private final static List<Service> services = new LinkedList<>();
    private static Thread checking;

    public static void startCheck()
    {
        checking = new Thread(new Check_services(services));
        checking.start();
    }

    public static void stopCheck() throws InterruptedException
    {
        if (!checking.isInterrupted())
        {
            checking.interrupt();
            checking.join();
        }
    }

    public static void getServices(String[] data) throws NumberFormatException
    {
        for (String s : data)
        {
            services.add(new Service(s.substring(0, s.indexOf(":")), getPortFromString(s)));
        }
    }

    public static void getServices(String s) throws NumberFormatException
    {
        services.add(new Service(s.substring(0, s.indexOf(":")), getPortFromString(s)));

    }

    public static void printServices()
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
