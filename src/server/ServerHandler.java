package server;

import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;


public class ServerHandler implements Runnable{

	private Socket connection ;
	private int no;							//The index number of the client
	  private BufferedReader in = null;
	    private PrintStream out;
	    private String temp;
	    private int clientnum;
	
	public ServerHandler(Socket connection, int no, int clientnum) 
	{
    	this.connection = connection;
		this.no = no;
		this.clientnum=clientnum;
		//System.out.println("Connection received from client" + clientNum);
	}

	@Override
	public void run() {
		 boolean done = false;
	        try{
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            out = new PrintStream(connection.getOutputStream());
	            
	            if(clientnum==1)
	            {
	            for(int i=1; i<= no; i=i+5){
	            	//Split obj= new Split();


	            	temp=""+i;
	                sendFile(temp,clientnum);
	            }
	            }
	            else if(clientnum==2){
	            	
	            	 for(int i=2; i<= no; i=i+5){
	 	            	//Split obj= new Split();


	 	            	temp=""+i;
	 	                sendFile(temp,clientnum);
	 	            }
	            	
	            } 
	            
                else if(clientnum==3){
	            	
                	 for(int i=3; i<= no; i=i+5){
     	            	//Split obj= new Split();


     	            	temp=""+i;
     	                sendFile(temp,clientnum);
     	            }
	            }
	            
                else if(clientnum==4){
 	
                	 for(int i=4; i<= no; i=i+5){
     	            	//Split obj= new Split();


     	            	temp=""+i;
     	                sendFile(temp,clientnum);
     	            }
                  }
	            
                else if(clientnum==5){
 	
                	 for(int i=5; i<= no; i=i+5){
     	            	//Split obj= new Split();


     	            	temp=""+i;
     	                sendFile(temp,clientnum);
     	            }
                  }
	            
	            
	        }
	        catch(Exception e){
	            System.out.println("errooooooorrrr");
	        }
		
	}
	
	public void sendFile(String fileName, int x) {
        try {
           String fileName2="/Users/akshatsharma/Documents/workspace/Networks/src/server/SplitFile/"+fileName;
        	
            File myFile = new File(fileName2);
            byte[] mybytearray = new byte[(int) myFile.length()];

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);

            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(mybytearray, 0, mybytearray.length);

            OutputStream os = connection.getOutputStream();

            DataOutputStream dos = new DataOutputStream(os);

            dos.writeUTF(myFile.getName());
             
              //dos.writeUTF(fileName);
              
            dos.writeLong(mybytearray.length);
            
            dos.write(mybytearray, 0, mybytearray.length);
            //System.out.println("here");
            dos.flush();
            //dos.close();

            System.out.println("Chunk:  " + fileName + " send to client"+ x);

        } catch (Exception e) {
            System.err.println("Error! " + e);
        }
    }
	
}
