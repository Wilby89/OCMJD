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
    private static int magicCookie;
    private static int numOfFields;
    
    public static void main(String [ ] args) {
        try {
            loadDataBase();
        } catch (Exception e) {}
    }
    
    
    public static ArrayList loadDataBase() throws FileNotFoundException, IOException {
        
        ArrayList recordList = new ArrayList();
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
                byte[] fieldName = new byte[Room.MAX_RECORD_LENGTH];
                fileObject.read(fieldName);
                String tempRecord = new String(fieldName, "US-ASCII");
                System.out.println(tempRecord); //The character encoding is 8 bit US ASCII
                String tempName = tempRecord.substring(0,Room.HOTEL_NAME_LENGTH);
                String tempLocation = tempRecord.substring(Room.HOTEL_NAME_LENGTH,Room.CITY_LENGTH);
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
