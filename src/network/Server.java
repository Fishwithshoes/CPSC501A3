//basic server code provided by Sourish Roy

package network;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	
    public static void main(String args[]) throws Exception
    {
        ServerSocket s = new ServerSocket(5217);

        while(true)
        {
            System.out.println("Waiting For Connection ...");
            Socket soc = s.accept();
            System.out.println("ip: " + s.getInetAddress());
            DataInputStream in = new DataInputStream(soc.getInputStream());
            DataOutputStream out = new DataOutputStream(soc.getOutputStream());
            String test = in.readUTF();
            System.out.println(test);
            
            //out.writeBytes("Server Date "  +(new Date()).toString() + "\n" );
            out.close();
            soc.close();
        }
        
    }

}
