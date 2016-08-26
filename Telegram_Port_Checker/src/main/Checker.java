package main;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Checker
{

    private List<Service> services = new LinkedList<Service>();

    Runnable task = () -> 
            {

                    services.stream().forEach((s)
                            -> 
                            {
                                if (!s.portIsOpen())
                                {
                                    // fire notify
                                }
                    });

                

    };

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
                System.exit(1);
            }
        }
    }

    public void printServices()
    {
        services.stream().forEach(System.out::println);
    }

}
