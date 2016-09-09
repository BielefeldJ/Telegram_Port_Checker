
package main;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Service implements Serializable
{

    private final int port;
    private final String ip;
    private boolean message_sended=false;

    public boolean isMessage_sended()
    {
        return message_sended;
    }

    public Service(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public String toString()
    {
       return ip + ":" + port; 
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
    
    public void toggleMessageSended()
    {
        if(message_sended == true)
        {
            message_sended = false;
        }
        else
        {
            message_sended=true;
        }
    }
}
