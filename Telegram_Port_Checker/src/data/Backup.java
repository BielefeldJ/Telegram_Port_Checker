
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


public class Backup
{
    private static final File F = new File("saveddata.dat");
    
    public static void save(ArrayList<Client> list)
    {
        try(FileOutputStream fos = new FileOutputStream(F); ObjectOutputStream oos = new ObjectOutputStream(fos);)
        {
            oos.writeObject(list);
            System.out.println("Save OK");
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("No file found @ Save backup.class");
        }
        catch (IOException ex)
        {
            System.out.println("IO Err @ Save backup.class");

        }
    }
    
    public static ArrayList<Client> load()
    {
        ArrayList<Client> list = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(F);ObjectInputStream ois = new ObjectInputStream(fis))
        {
            list = (ArrayList<Client>)ois.readObject();
        }
        catch (IOException ex)
        {
            System.out.println("IO Err @ load backup.class");

        }
        catch (ClassNotFoundException ex)
        {
            System.out.println("Class not Found!");

        }
        return list;
    }
}
