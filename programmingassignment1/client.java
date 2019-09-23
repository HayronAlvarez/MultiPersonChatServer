/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programmingassignment1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 *
 * @author Hayron
 */
public class client
{
    private String userName;
    private Socket aSocket;
    private PrintWriter out;
    private long startTime, endTime, elapsedTime;
    private Date current;
    public client(String userName, Socket aSocket) throws IOException
    {
        this.userName = userName;
        this.aSocket = aSocket;
        out = new PrintWriter(aSocket.getOutputStream());
        current = new Date();
        startTime=current.getTime();
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public Socket getaSocket()
    {
        return aSocket;
    }

    public void setaSocket(Socket aSocket)
    {
        this.aSocket = aSocket;
    }
    
    public void displayMessage(String message)
    {
        out.println(message);
        out.flush(); 
    }
    
    public long getTime()
    {
        current = new Date();
        endTime = current.getTime();
        
        
        return (endTime - startTime)/1000;
    }
    
    
    
    
    
}
