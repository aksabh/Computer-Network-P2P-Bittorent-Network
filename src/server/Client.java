package server;

import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class Client {
	//private static int sPort = 7000;
	private static Socket connection;
    private static String fileName;
    private static String newName;
    private static BufferedReader bufferReader;
    private static PrintStream os;
    private static int chunks;
    
    public static void main(String[] args) throws IOException {
        try {
            connection = new Socket("localhost", 7000);
            bufferReader = new BufferedReader(new InputStreamReader(System.in));
        } catch (Exception e) {
            System.err.println("Error - Try again.");
            System.exit(1);
        }

        os = new PrintStream(connection.getOutputStream());
        System.out.print("Connected to Server");
        getTotalChunks();
        //fileName = bufferReader.readLine();
        //os.println(fileName);
        for(int i=1;i<= chunks;i++)
        receiveFile(fileName);

        connection.close();
    }
    
    public static void receiveFile(String fileName) {
        try {
            int bytesRead;
            InputStream in = connection.getInputStream();

            DataInputStream clientData = new DataInputStream(in);

            fileName = clientData.readUTF();
            OutputStream output = new FileOutputStream(("chunk" + fileName));
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
    
}
