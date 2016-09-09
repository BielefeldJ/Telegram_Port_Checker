/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import main.Client;

/**
 *
 * @author Admin
 */
public class Backup
{
    private static final File f = new File("saveddata.dat");
    
    public static void save(ArrayList<Client> list)
    {
        try(FileOutputStream fos = new FileOutputStream(f); ObjectOutputStream oos = new ObjectOutputStream(fos);)
        {
            oos.writeObject(list);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("No file found @ Save backup.class");
        }
        catch (IOException ex)
        {
            System.out.println("IO Err @ Save backup.class");
            ex.printStackTrace();
        }
    }
    
    public static ArrayList<Client> load()
    {
        ArrayList<Client> list =  new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(f);ObjectInputStream ois = new ObjectInputStream(fis))
        {
            list = (ArrayList<Client>)ois.readObject();
        }
        catch (IOException ex)
        {
            System.out.println("IO Err @ load backup.class");
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex)
        {
            System.out.println("Class not Found!");
        }
        return list;
    }
}
