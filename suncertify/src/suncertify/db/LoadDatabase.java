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
            loadDataBase();
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
    
}
