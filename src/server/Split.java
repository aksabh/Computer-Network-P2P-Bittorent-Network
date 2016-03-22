package server;

import java.io.*;

public class Split {
	public static int chunks=0;
    public static String [] fname = new String[100] ;
    public static int splitFile(String path) 
    {
        try
        { 
        
        
     
  
        File f= new File(path);
        
        FileInputStream fis= new FileInputStream(path); 
        int size=100000;
        byte b[]=new byte[size];
        int ch,c=1;

        System.out.println("Name of file"+"    "+ f.getName());

        int filesize=(int)f.length();
        System.out.println("File Size"+"    "+filesize);
        
        while(filesize>0)
           {
                 ch=fis.read(b,0,size);
                 filesize = filesize-ch;
                 //fname[c]="chunk"+c+"."+f.getName()+"";
                 fname[c]=""+c;
                 chunks++;
                 
                 File f1=new File("/Users/akshatsharma/Documents/workspace/Networks/src/server/SplitFile");
                 if (!f1.exists()) {
             		if (f1.mkdir()) {
             }
             }
                 
                 FileOutputStream fos= new FileOutputStream(new File(f1,fname[c]));
                 c++;
                 fos.write(b,0,ch);
                 fos.flush();
                 fos.close();
           }
                 
                 System.out.println("Number of chunks"+"    "+chunks);

        fis.close();
        }
        catch(IOException ex)
        {
            System.out.println("Something went wrong.");
        }
        return chunks;
    }
    
    public static void storeChunk(int a, String name)
	{
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(name, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		writer.println(a);
		writer.close();
	}
    
    public static void storeFileName(String a, String name)
	{
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(name, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		writer.println(a);
		writer.close();
	}
}
