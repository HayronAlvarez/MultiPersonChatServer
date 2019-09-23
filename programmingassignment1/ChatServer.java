/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programmingassignment1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Hayron
 */
public class ChatServer
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
       final int SBAP_PORT = 8888;
       ServerSocket server = new ServerSocket(SBAP_PORT);
       System.out.println("Waiting for clients to connect.....");
       
       while(true)
       {
          Socket s = server.accept();
          Scanner in = new Scanner(s.getInputStream());
          PrintWriter out = new PrintWriter(s.getOutputStream());
         
          ////client newClient = new client(in.nextLine(),s);
          clientService service = new clientService(s);
          Thread t = new Thread(service);
          t.start();
       }
       
    }
    
}
