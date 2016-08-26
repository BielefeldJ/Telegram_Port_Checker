/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jensb
 */
public class main
{
    public static void main(String[] args)
    {
        List<Service> services = new LinkedList<Service>();
        
        String[] test = new String[2];
        test[0] = "localhost:9999";
        test[1] = "example:992w3";
        for(String s : test)
        {
            try
            {
                services.add(new Service(s.substring(0, s.indexOf(":")),Integer.parseInt(s.substring(s.indexOf(":")+1, s.length()))));
            }
            catch(NumberFormatException e)
            {
                System.out.println("Error: incorrect port in " + s);
                System.exit(1);
            }
        }
    }
    
    private static boolean available(int port) {
    try (Socket ignored = new Socket("localhost", port)) {
        return false;
    } catch (IOException ignored) {
        return true;
    }
}
}
