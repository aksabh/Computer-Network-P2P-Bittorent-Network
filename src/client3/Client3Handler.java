package client3;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Client3Handler implements Runnable{

	private Socket connection ;
	private int no;							//The index number of the client
	  private BufferedReader in = null;
	    private PrintStream out;
	    private String temp;
	    private int clientnum;
	    private int arr[];
	
	public Client3Handler(Socket connection, int no, int clientnum, int arr[]) 
	{
    	this.connection = connection;
		this.no = no;
		this.clientnum=clientnum;
		//this.arr=new int[no+1];
		this.arr=arr;
		//System.out.println("Connection received from client" + clientNum);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
        
int j=0;
int count=0;
while(j<arr.length)
{
	if(arr[j]==1)
		count++;
	
	j++;
	}
	
           InputStream in=null;
           
		 
				try {
					in = connection.getInputStream();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
		    	OutputStream op=null;
				try {
					op = connection.getOutputStream();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
		    	DataInputStream inp = new DataInputStream(in);
		    	DataOutputStream out = new DataOutputStream(op);
               
		    	
		    	try{
		    		out.write(count);
		    		out.flush();
		    	} catch(Exception e){
		            System.out.println("errooooooorrrr");
		        }
		    
		    	
		    	int i=1;
		    	int temp;
		    	String f2s;
		    	while(i<arr.length){
		    		if(arr[i]==1)
		    		{
		    			try{
				    		out.write(i);
				    		System.out.println(i);
				    	} catch(Exception e){
				            System.out.println("eroorrrr");
				        }
		    			try {
							String dec = inp.readUTF();
							if(dec.equalsIgnoreCase("done"))
							{
								temp=inp.read();
								//System.out.println(temp);
								f2s=temp+"";
								System.out.println("Sending file: "+f2s);
								sendFile(f2s,clientnum);
							}
						} catch (IOException e) {
							
							e.printStackTrace();
						}
					}
					i++;
				}
				try {
					connection.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    
	}
		    	
		    	
		    	
		    	
	
		
		
                         
	
	public void sendFile(String fileName2, int x) {
        try {
          // String fileName2="/Users/akshatsharma/Documents/workspace/Networks/src/client1/RecievedFiles/"+fileName;
        	fileName2="src/client3/"+fileName2;
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

            System.out.println("Chunk:  " + fileName2 + " send to neighbour client "+ x);

        } catch (Exception e) {
            System.err.println("Error! " + e);
        }
    }
	
}
