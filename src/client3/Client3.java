package client3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;




public class Client3 {
	//private static int sPort = 7000;
		private static Socket connection,connection2, connection3;
	    private static String fileName;
	    private static String newName;
	    private static BufferedReader bufferReader;
	    private static PrintStream os;
	    private static int chunks;
	    private static final int c3Port = 7003;   //The client2 will be listening on this port number
	    public static ServerSocket c3listener;
	    private static int timeout=0;
	    private static int lcount=0;
	    private static int receivedChunks=0;
	    private static int[] chunk_received;
	    private static BufferedReader in = null;
	    private static PrintStream out;
	    
	    public static void main(String[] args) throws IOException {
	        try {
	            connection = new Socket("localhost", 7000);
	            bufferReader = new BufferedReader(new InputStreamReader(System.in));
	        } catch (Exception e) {
	            System.err.println("Error - Try again.");
	            System.exit(1);
	        }

	        os = new PrintStream(connection.getOutputStream());
	        System.out.println("Connected to Server");
	        getTotalChunks();
	        chunk_received= new int[chunks+1];
	        for(int y=0;y<= chunks;y++)
	        {
	        	chunk_received[y]=0;
	        }
	        //fileName = bufferReader.readLine();
	        //os.println(fileName);
	        for(int i=3;i<= chunks;i=i+5,receivedChunks++)
	        {
	        receiveFile(fileName, i);
	        chunk_received[i]=1;
	        }
	        System.out.println("All files recevied from Server");
	      //  connection.close();
	        
	    
	        
	        connection.close();
	        
	        peer2peer(chunk_received,chunks);
	        
	        
	        
	        
	        
	        /*    while(true)
	        {
	        	 int k=downloadFromNeighbour(); 											//Download from client5 
		          
	          connectToNeighbour();												//Upload To client1
	          
	         
	          if(receivedChunks==chunks){
	          //	mergeFiles();
	          }
	        } */
	    }
	    
	    public static void peer2peer(int a[], int c) throws IOException{
	    	
	    	Boolean t= false;
	    	int m=0;
	    	while(true)
	    	{
	    		downloadFromNeighbour(a);
	    		
	    		System.out.println("Waiting for connection with neighbour client4");  //wait for connection
	    		connectToNeighbour(a);
	    		
	    		if(receivedChunks==chunks)
	    			mergeFiles(chunks,"");
	    	}
	    	
	    }
	    
	    
	    public static void receiveFile(String fileName, int t) {
	        try {
	            int bytesRead;
	           // chunk_received[t]=1;
	            InputStream in = connection.getInputStream();

	            DataInputStream clientData = new DataInputStream(in);

	            fileName = clientData.readUTF();
	         
	            OutputStream output = new FileOutputStream("src/client3/"+fileName);
	            long size = clientData.readLong();
	            byte[] buffer = new byte[1024];
	            while (size > 0
	                    && (bytesRead = clientData.read(buffer, 0,
	                            (int) Math.min(buffer.length, size))) != -1) {
	                output.write(buffer, 0, bytesRead);
	                size -= bytesRead;
	            }
	            output.flush();

	            System.out
	                    .println("File " + fileName + " received from Server.");
	        } catch (IOException ex) {
	          //  Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE,
	                 //   null, ex);
	        	//sysout
	     System.out.println("ERRORRR!");
	        	
	        }
	    }
	    
	    public static String getFileName() {

	        // The name of the file to open.
	        String fileName = "FileName.txt";

	        // This will reference one line at a time
	        String line = null;
	        String name = null;
	        //String ch=null;

	        try {
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = 
	                new FileReader(fileName);

	            // Always wrap FileReader in BufferedReader.
	            BufferedReader bufferedReader = 
	                new BufferedReader(fileReader);

	            while((line = bufferedReader.readLine()) != null) {
	                //System.out.println(line);
	                name=line;
	            }   
	            System.out.println("File name="+name);

	            // Always close files.
	            bufferedReader.close();         
	        }
	        catch(FileNotFoundException ex) {
	            System.out.println(
	                "Unable to open file '" + 
	                fileName + "'");                
	        }
	        catch(IOException ex) {
	            System.out.println(
	                "Error reading file '" 
	                + fileName + "'");                  
	            // Or we could just do this: 
	            // ex.printStackTrace();
	        }
	        return name;
	    }
	    
	    public static void getTotalChunks() {

	        // The name of the file to open.
	        String fileName = "chunkcount.txt";

	        // This will reference one line at a time
	        String line = null;
	        //String ch=null;

	        try {
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = 
	                new FileReader(fileName);

	            // Always wrap FileReader in BufferedReader.
	            BufferedReader bufferedReader = 
	                new BufferedReader(fileReader);

	            while((line = bufferedReader.readLine()) != null) {
	                //System.out.println(line);
	                chunks=Integer.parseInt(line);
	            }   

	            // Always close files.
	            bufferedReader.close();         
	        }
	        catch(FileNotFoundException ex) {
	            System.out.println(
	                "Unable to open file '" + 
	                fileName + "'");                
	        }
	        catch(IOException ex) {
	            System.out.println(
	                "Error reading file '" 
	                + fileName + "'");                  
	            // Or we could just do this: 
	            // ex.printStackTrace();
	        }
	    }
	    
	    public static void receiveFileFromNeighbour(String fileName) {
			 
		        try {
		            int bytesRead;
		          //  chunk_received[h]=1;
		            InputStream in = connection2.getInputStream();

		            DataInputStream clientData = new DataInputStream(in);

		            fileName = clientData.readUTF();
		            
		            OutputStream output = new FileOutputStream("src/client3/"+fileName);
		            long size = clientData.readLong();
		            byte[] buffer = new byte[1024];
		            while (size > 0
		                    && (bytesRead = clientData.read(buffer, 0,
		                            (int) Math.min(buffer.length, size))) != -1) {
		                output.write(buffer, 0, bytesRead);
		                size -= bytesRead;
		            }
		            output.flush();

		            System.out.println("File " + fileName + " received from Client2");
		        } catch (IOException ex) {
		          //  Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE,
		                 //   null, ex);
		        	//sysout
		           
		        	
		        	System.out.println("ERRORRR!");
		            //return 1;
		            
		        }
		    
			 
		 }
	    
	    public static void connectToNeighbour(int ar[]) throws IOException{
			
			c3listener = new ServerSocket(c3Port);                                            //create a serversocket
			try
			{
			
					
				 connection2 = c3listener.accept();
	             // System.out.println("Conection Accept : " + clientSocket);
	              //System.out.println("Connection received from: " + connection);
	              System.out.println("Client3 is connected!");
	              
	              
	              Thread t = new Thread(new Client3Handler(connection2,chunks,4,ar));
	             // clientNum++;
	              t.start();
	              //connection2.close();
	              c3listener.close();

				
	        } catch (Exception e) {
	            System.err.println("Port in use.");
	           // System.exit(1);
	        }         	
	            	
	               

	           
	        }
	    
	    public static int downloadFromNeighbour(int an[]){
	    
	    	while(true)
	        {
	    	try{
	    		System.out.println("Waiting for files to download: ");
	    		connection2 = new Socket("localhost", 7002);	
	            System.out.println("Connected to Client2");
	    		System.out.println("Connectionn established");
	    	
	    	
	        	InputStream in = connection2.getInputStream();
	        	OutputStream op = connection2.getOutputStream();
	        	DataInputStream inp = new DataInputStream(in);
	        	DataOutputStream out = new DataOutputStream(op);
	        	
	    	
	         lcount=inp.read();
	        System.out.println(lcount);
	    	
	        System.out.println("Ready to receive files from neighbours: ");
	        int k=0;
	        int temp;
	        while(k<lcount)
	        {
	        	temp=inp.read();
	        	//System.out.println(temp);
	        	if(an[temp]==0)
	        	{
	        		out.writeUTF("done");
	        		out.write(temp);
	        		//System.out.println(temp);
	        		an[temp]=1;
	        		chunk_received[temp]=1;
	        		receivedChunks++;
	        		receiveFileFromNeighbour(temp+"");
	        		System.out.println("File Received from neighbour");
	        		
	        	}
	        	else
	        	{
	        		out.writeUTF("no");
	        		
	        	}
	        	k++;
	        }
	        
	       
	       connection2.close();
	       
	    	}
	    	catch(Exception e)
	    	{
	    		timeout++;
	    		if(timeout>10)
	    		{
	    			timeout=0;
	    			return 1;
	    		}
	    		System.out.println("Waiting for connection");
	    		//e.printStackTrace();
	    	}
	        }
	    }
	    
	    public static void mergeFiles(int chunkcount,String filename) throws IOException {

	         filename=getFileName();
			 FileOutputStream f = new FileOutputStream("src/client3/"+filename);
	         int i=1;
	         int length;
	     byte[] buffer = new byte[1024];
	         while (i<=chunkcount) {

	             FileInputStream fs = new FileInputStream("src/client3/"+i);
	             while ((length = fs.read(buffer)) > 0){
	                 f.write(buffer, 0, length);
	             }
	             i++;
	             fs.close();
	         }
	         f.close();
	     System.out.println("Merging done");

	     }
	    
}
