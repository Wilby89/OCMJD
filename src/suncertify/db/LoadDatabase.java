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
            //loadDatabase2();
        } catch (Exception e) {}
    }
    
    
    public static void loadDataBase() throws FileNotFoundException, IOException {
        
        fileObject = new RandomAccessFile(new File("db-1x3.db"),"r");
        fileObject.readInt(); //magicCookie Value
        numOfFields = fileObject.readShort();
        System.out.println("Num of Fields: "+numOfFields); 
        for(int i = 0; i < numOfFields; i++) {
            byte nameSize = fileObject.readByte();
            System.out.println("Name Size: "+nameSize);
            byte[] fieldName = new byte[nameSize];
            fileObject.read(fieldName);
            System.out.println(new String(fieldName, "US-ASCII")); //The character encoding is 8 bit US ASCII
            fileObject.readByte(); //fieldLength, not needed to display            
        }
        System.out.println("===========Records============");
        try {
            while (true) {
                fileObject.readByte();
                byte[] fieldName = new byte[159]; //length of db field names, 64+64+4+1+8+10+8 = 159
                fileObject.read(fieldName);
                System.out.println(new String(fieldName, "US-ASCII")); //The character encoding is 8 bit US ASCII
            }
        } catch (EOFException e) {
            System.out.println("End of file");
        }
        fileObject.close();
        
    }    
}
