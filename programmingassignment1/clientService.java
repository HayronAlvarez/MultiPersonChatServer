/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programmingassignment1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Hayron
 */
public class clientService implements Runnable
{
    private static ArrayList<client> clients = new ArrayList<client>();
    private Socket s;
    private Scanner in;
    private PrintWriter out;
    private String userName;
    private Lock clientLock;
    private client newClient;
    
    public clientService(Socket aSocket)
    {
       s = aSocket;
       clientLock = new ReentrantLock();
    }
    
    public void run()
    {
        try
        {
            try
            {
              in = new Scanner(s.getInputStream()); 
              out = new PrintWriter(s.getOutputStream());
              doService();
            }
        finally
            {
                clientLock.lock();
                try
                {
                    out.printf("Thank you "+ userName + " for using the chat");
                    out.flush();
                    for(int i = 0; i < clients.size(); i++ )
                    {
                        if(clients.get(i).getUserName().equals(userName))
                        {
                            clients.remove(i);
                           
                            
                        }
                        {
                            clients.get(i).displayMessage("User \""+userName+"\" has exited the chat");
                        }
                        
                    }
               
                    
                }
                    catch (IndexOutOfBoundsException e){
                        
                    }
                    finally
                    {
                        clientLock.unlock();
                    }
                s.close();
            }
        }
        catch(IOException exception)
        {
            exception.printStackTrace();
        }
    }
    
    private void doService() throws IOException
    {
        String userInput="";
       try{ 
            while(true)
            {
                out.print("Please enter your username: ");
                out.flush();
                userName = in.nextLine();
                //add new client 
                clientLock.lock();
                try{
                    for(int i = 0; i < clients.size(); i++)
                    {
                        clients.get(i).displayMessage("User \"" + userName +"\" has joined the chat!");
                    }
                    newClient = new client(userName,s);
                    clients.add(newClient);
                }
                finally
                {
                    clientLock.unlock();
                }
           
                out.println("welcome "+userName);
                out.flush();
                while(!userInput.equals("LOGOUT"))
                {
                    userInput = in.nextLine();
                   
                    
                    if(userInput.equals("LOGOUT"))
                    {
                        break;
                    }
                    else if(userInput.equals("ACTIVE"))
                    {
                        clientLock.lock();
                        try
                        {
                            out.println("There are "+clients.size()+" user(s) online");
                            out.flush();
                        }
                        finally
                        {
                            clientLock.unlock();
                        }
                    }
                    else if(userInput.equals("TIME"))
                    {
                        out.println("Total time online is: "+newClient.getTime()+" seconds");
                        out.flush();
                    }
                    else 
                    {
                        
                        clientLock.lock();
                        try
                        {
                            for(int i = 0; i < clients.size(); i++)
                            {
                                
                                clients.get(i).displayMessage(userName+": "+userInput);
                            }
                        }
                        finally
                        {
                            clientLock.unlock();
                        }
                    } 
                }
                return;
            }
           } 
            catch(NoSuchElementException e)
            {return;}
  }
}
