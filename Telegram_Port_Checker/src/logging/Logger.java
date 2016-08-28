/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logging;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 *
 * @author jensb
 */
public class Logger
{
    FileOutputStream fos = null;
    DataOutputStream dos = null;
    File f = null;
    
    public Logger()
    {
        //f = new File("./service_" + );
    }
}
