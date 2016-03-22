package server;

import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class Server {
	
	public static int chunks;				// store value of chunks created
	public static  int idx=0;
	public static String sendfile;
	public static Socket connection ;
	public static int clientNum = 1;
	public static ServerSocket listener;
	private static final int sPort = 7000;   //The server will be listening on this port number
	private static String fileName;
	
	public void connectToClient(){
		try {
            
            
        } catch (Exception e) {
            System.err.println("Port in use.");
            System.exit(1);
        }

        while (true) {
            try {
                connection = listener.accept();
               // System.out.println("Conection Accept : " + clientSocket);
                System.out.println("Connection received from: " + connection);
                System.out.println("Client "  + clientNum + " is connected!");
                
                
                Thread t = new Thread(new ServerHandler(connection,chunks,clientNum));
                clientNum++;
                t.start();

            } catch (Exception e) {
                System.err.println("Conection Error.");
            }
        }
	}
	
	
	public static void main(String[] args) throws Exception 
	{
		BufferedReader con= new BufferedReader(new InputStreamReader(System.in));
        System.out.println("enter the file name");
        fileName=con.readLine();
        
		chunks=Split.splitFile(fileName);                      // Call splitFile() to split the files in chunks
		
		Split.storeChunk(chunks,"chunkcount.txt");
		Split.storeFileName(fileName, "FileName.txt");
		
		
		System.out.println("The server is running."); 
        listener = new ServerSocket(sPort);           //create a serversocket
        System.out.println("Waiting for connection");              //wait for connection
    	Server s=new Server();
    	s.connectToClient();
       /* try 
        {
            	while(true)
            	{

                	new Handler(listener.accept(),clientNum).start();
					System.out.println("Client "  + clientNum + " is connected!");
					clientNum++;
            	}
        } 
        finally 
        	{
            	listener.close();
        	} 
 */
    }

}
