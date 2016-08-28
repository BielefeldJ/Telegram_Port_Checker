
package main;

import java.net.InetSocketAddress;
import java.net.Socket;

public class Service
{

    private final int port;
    private final String ip;
    private boolean message_sended=false;

    public Service(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public String toString()
    {
       return "Checking port "+ port + " at " + ip; 
    }

    public boolean portIsOpen()
    {
        try
        {
            try (Socket socket = new Socket())
            {
                socket.connect(new InetSocketAddress(ip, port), 600);
            }
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }
}
