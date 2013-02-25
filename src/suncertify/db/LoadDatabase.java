package suncertify.db;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author William Brosnan
 */
public class LoadDatabase {
    
    private static RandomAccessFile fileObject;
    private static final int MAGIC_COOKIE_LENGTH = 4;
    private static final int NUMBER_OF_FIELDS_LENGTH = 2;
    private static final String ENCODING = "US-ASCII";
    
    
    
    public static void main(String [ ] args) {
        try {
            loadDataBase();
        } catch (Exception e) {}
    }
    
    
    public static ArrayList loadDataBase() throws FileNotFoundException, IOException {
        
        ArrayList recordList = new ArrayList();
        fileObject = new RandomAccessFile(new File("db-1x3.db"),"r");
        //byte[] magicCookieArray = new byte[MAGIC_COOKIE_LENGTH];
        //fileObject.read(magicCookieArray); //magicCookie Value
        final int magicCookie = fileObject.readInt();
        final int numOfFields = fileObject.readShort();
        System.out.println("Magic Cookie: "+magicCookie);
        System.out.println("Num of Fields: "+numOfFields); 
        for(int i = 0; i < numOfFields; i++) {
            byte nameSize = fileObject.readByte();
            System.out.println("Name Size: "+nameSize);
            byte[] fieldName = new byte[nameSize];
            fileObject.read(fieldName);
            System.out.println(new String(fieldName, ENCODING)); //The character encoding is 8 bit US ASCII
            fileObject.readByte(); //fieldLength, not needed to display            
        }
        System.out.println("===========Records============");
        try {
            while (true) {
                fileObject.readByte();
                byte[] fieldName = new byte[Room.MAX_RECORD_LENGTH];
                fileObject.read(fieldName);
                String tempRecord = new String(fieldName, ENCODING);
                System.out.println(tempRecord); //The character encoding is 8 bit US ASCII
                String tempName = tempRecord.substring(0,Room.HOTEL_NAME_LENGTH+1);
                System.out.println(tempName);
                String tempLocation = tempRecord.substring(Room.HOTEL_NAME_LENGTH,Room.CITY_LENGTH+1);
                System.out.println(tempLocation);
                String tempMax = tempRecord.substring(Room.CITY_LENGTH,Room.MAXIMUM_OCCUPANCY_LENGTH);
                Boolean tempSmoke = Boolean.parseBoolean(tempRecord.substring(Room.MAXIMUM_OCCUPANCY_LENGTH, Room.SMOKING_LENGTH));
                String tempPrice = tempRecord.substring(Room.SMOKING_LENGTH, Room.PRICE_LENGTH);
                String tempDate = tempRecord.substring(Room.PRICE_LENGTH, Room.DATE_AVAILABLE_LENGTH);
                int tempCust = Integer.parseInt(tempRecord.substring(Room.DATE_AVAILABLE_LENGTH, Room.CUSTOMER_ID_LENGTH));
                Room newRoom = new Room(tempName, tempLocation, tempMax, tempSmoke
                        , tempPrice, tempDate, tempCust);
                recordList.add(newRoom);
            }
        } catch (EOFException e) {
            System.out.println("End of file");
        }
        fileObject.close();
        return recordList;
        
    }    
}
