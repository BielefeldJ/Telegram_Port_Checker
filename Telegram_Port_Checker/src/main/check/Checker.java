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
    Thread checking;

    public void startCheck()
    {
        checking = new Thread(new Check_services(services));
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

    public void getServices(String[] data)
    {
        for (String s : data)
        {
            try
            {
                services.add(new Service(s.substring(0, s.indexOf(":")), Integer.parseInt(s.substring(s.indexOf(":") + 1, s.length()))));
            }
            catch (NumberFormatException e)
            {
                System.out.println("Error: incorrect port in " + s);
                System.exit(1);  // exit if port isnt a number
            }
        }
    }

    public void printServices()
    {
        services.stream().forEach(System.out::println);
    }

}
