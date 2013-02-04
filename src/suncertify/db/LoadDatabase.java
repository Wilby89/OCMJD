package suncertify.db;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author William Brosnan
 */
public class LoadDatabase {
    
    private static RandomAccessFile fileObject;
    private static int magicCookie;
    private static int numOfFields;
    
    public static void main(String [ ] args) {
        try {
            //loadDataBase();
            loadDatabase2();
        } catch (Exception e) {}
    }
    
    
    public static void loadDataBase() throws FileNotFoundException, IOException {
        
        fileObject = new RandomAccessFile(new File("db-1x3.db"),"r");
        magicCookie = fileObject.readInt();
        numOfFields = fileObject.readShort();
        for(int i = 0; i < numOfFields; i++) {
            byte nameSize = fileObject.readByte();
            System.out.println("Name Size: "+nameSize);
            for (int j = 0; j < nameSize; j++) {
                byte[] fieldName = new byte[nameSize];
                fileObject.read(fieldName);
                System.out.println(new String(fieldName));                
            }
        }
        try {
            while (true) {
                byte first = fileObject.readByte();
                byte[] fieldName = new byte[159];
                fileObject.read(fieldName);
                System.out.println(new String(fieldName)); 
            }
        } catch (EOFException e) {
            System.out.println("Something wrong");
        }
        fileObject.close();
        
    }
    
    public static void loadDatabase2() throws FileNotFoundException, IOException {  
          
        RandomAccessFile raf = new RandomAccessFile(new File("db-1x3.db"),"r");  
        int Cookie = raf.readInt();  
        int fields = raf.readShort();  
        System.out.println("Cookie:"+Cookie);  
        System.out.println("Num of Fields:"+fields);  
        for(int i=0; i<fields;i++)  
        {  
            System.out.println("---- Column Description ----");  
            int nameSize = raf.readUnsignedByte();  
            System.out.println("Desc Size:"+nameSize);  
            byte[] buff=new byte[nameSize];  
            raf.read(buff);  
            System.out.println(new String(buff,"US-ASCII"));  
            System.out.println("Value Size:"+raf.readUnsignedByte());  
            System.out.println("---- Column Ends ----");  
        } 
        System.out.println("++++ Records ++++");  
        try{  
            while(true){  
                byte valid = raf.readByte();  
                System.out.print(valid+" - ");  
                byte buff[] = new byte[159];  
                raf.read(buff);  
                String s =  new String (buff,"US-ASCII");  
                System.out.println(s);  
                  
            }  
        }catch (EOFException exc){}  
        raf.close();  
    }  
    
}
