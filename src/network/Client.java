package network;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
	
    public static void main(String args[]) throws Exception
    {

        Socket soc = new Socket(InetAddress.getLocalHost(),5217);
    	DataOutputStream out = new DataOutputStream(soc.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        out.write("hello".getBytes());
        out.writeUTF("hello1");

        System.out.println(in.readLine());
    }   

}
