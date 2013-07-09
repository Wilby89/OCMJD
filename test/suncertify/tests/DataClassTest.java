/* 
 * @(#)DataClassTest.java    1.0 05/11/2008 
 *  
 * Candidate: Roberto Perillo 
 * Prometric ID: Your Prometric ID here 
 * Candidate ID: Your candidade ID here 
 *  
 * Sun Certified Developer for Java 2 Platform, Standard Edition Programming 
 * Assignment (CX-310-252A) 
 *  
 * This class is part of the Programming Assignment of the Sun Certified 
 * Developer for Java 2 Platform, Standard Edition certification program, must 
 * not be used out of this context and must be used exclusively by Sun 
 * Microsystems, Inc. 
 */  
package suncertify.tests;  
  
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;  
import java.util.logging.Level;
import java.util.logging.Logger;
import suncertify.db.DBMain;
import suncertify.db.Data;  
import suncertify.db.Room;
  
/** 
 * The <code>DataClassTest</code> tests the main functionalities of the 
 * {@link Data} class. In order to simulate several clients trying to use it and 
 * exercise the locking mechanism, it also has several inner classes that extend 
 * the {@link Thread} class, where each class represents one client requesting 
 * one operation, and mainly requesting updating and deletion of records. The 
 * <code>FindingRecordsThread</code> exercises two functionalities: finding 
 * records and reading records. 
 *  
 * @author Roberto Perillo 
 * @version 1.0 05/11/2008 
 */  
public class DataClassTest {  
    
    private static final String DB_PATH = "db-1x3_test.db";
  
    private static Data data = null;  
  
    /* 
     * If any preparation has to be done before using the Data class, it can be 
     * done in a static block; in this case, before using the Data class, the 
     * loadDbRecords method has to be called prior to any other operation, so 
     * the records in the physical .db file can be placed in the Map that keeps 
     * them in memory; I also have a method called persistDbRecords, which 
     * writes each record back to the physical .db file, but this test aims only 
     * to test the functionalities without altering the database, so this method 
     * is never called anywhere 
     */  
  
    public static void main(String [] args) {
        try {
            data = new Data(DB_PATH);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataClassTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        new DataClassTest().startTests();  
    }  
  
    public void startTests() {  
        try {  
              
            /* 
             * Practically, it is not necessary to execute this loop more than 1 
             * time, but if you want, you can increase the controller variable, 
             * so it is executed as many times as you want 
             */  
            for (int i = 0; i < 10; i++) {  
                Thread updatingRandom = new UpdatingRandomRecordThread();  
                updatingRandom.start();  
                Thread updatingRecord1 = new UpdatingRecord1Thread();  
                updatingRecord1.start();  
                Thread creatingRecord = new CreatingRecordThread();  
                creatingRecord.start();  
                Thread deletingRecord = new DeletingRecord1Thread();  
                deletingRecord.start();  
                Thread findingRecords = new FindingRecordsThread();  
                findingRecords.start();  
            }  
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
  
    }  
  
    private class UpdatingRandomRecordThread extends Thread {  
  
        @SuppressWarnings("deprecation")  
        public void run() {  
            final Room room = new Room();  
            room.setHotelName("Palace");  
            room.setCity("Smallville");  
            room.setMaxOccupancy("2");  
            room.setSmoking("true");  
            room.setPrice("$150.00");  
            room.setDate("2005/05/06");  
            room.setCustId("54120584");  
  
            final int recNo = (int) (Math.random() * 50);  
            try {  
                System.out.println(Thread.currentThread().getId()  
                        + " trying to lock record #" + recNo  
                        + " on UpdatingRandomRecordThread");  
  
                /* 
                 * The generated record number may not exist in the database, so 
                 * a RecordNotFoundException must be thrown by the lock method. 
                 * Since the database records are in a cache, it is not 
                 * necessary to put the unlock instruction in a finally block, 
                 * because an exception can only occur when calling the lock 
                 * method (not when calling the update/delete methods), 
                 * therefore it is not necessary to call the unlock method in a 
                 * finally block, but you can customize this code according to 
                 * your reality 
                 */  
                data.lock(recNo);  
                System.out.println(Thread.currentThread().getId()  
                        + " trying to update record #" + recNo  
                        + " on UpdatingRandomRecordThread");  
  
                /* 
                 * An exception cannot occur here, otherwise, the unlock 
                 * instruction will not be reached, and the record will be 
                 * locked forever. In this case, I created a class called 
                 * RoomRetriever, which transforms from Room to String array, 
                 * and vice-versa, but it could also be done this way: 
                 * 
                 * data.update(recNo, new String[] {"Palace", "Smallville", "2", 
                 * "Y", "$150.00", "2005/07/27", null}); 
                 */  
                data.update(recNo, room.toStringArray());  
                System.out.println(Thread.currentThread().getId()  
                        + " trying to unlock record #" + recNo  
                        + " on UpdatingRandomRecordThread");  
                data.unlock(recNo);  
            } catch (Exception e) {  
                System.out.println(e);  
            }  
        }  
    }  
  
    private class UpdatingRecord1Thread extends Thread {  
  
        @SuppressWarnings("deprecation")  
        public void run() {  
            final Room room = new Room();  
            room.setHotelName("Castle");  
            room.setCity("Digitopolis");  
            room.setMaxOccupancy("2");  
            room.setSmoking("false");  
            room.setPrice("$90.00");  
            room.setDate("2005/04/01");  
            room.setCustId("88006644");  
  
            try {  
                System.out.println(Thread.currentThread().getId()  
                        + " trying to lock record #1 on"  
                        + " UpdatingRecord1Thread");  
                data.lock(1);  
                System.out.println(Thread.currentThread().getId()  
                        + " trying to update record #1 on"  
                        + " UpdatingRecord1Thread");  
                data.update(1, room.toStringArray());  
                System.out.println(Thread.currentThread().getId()  
                        + " trying to unlock record #1 on"  
                        + "UpdatingRecord1Thread");  
                  
                /* 
                 * In order to see the deadlock, this instruction can be 
                 * commented, and the other Threads, waiting to update/delete 
                 * record #1 will wait forever and the deadlock will occur 
                 */  
                data.unlock(1);  
            } catch (Exception e) {  
                System.out.println(e);  
            }  
        }  
    }  
  
    private class CreatingRecordThread extends Thread {  
  
        @SuppressWarnings("deprecation")  
        public void run() {  
            Room room = new Room();  
            room.setHotelName("Elephant Inn");  
            room.setCity("EmeraldCity");  
            room.setMaxOccupancy("6");  
            room.setSmoking("false");  
            room.setPrice("$120.00");  
            room.setDate("2005/02/03");  
  
            try {  
                System.out.println(Thread.currentThread().getId()  
                        + " trying to create a record");  
                data.create(room.toStringArray());  
            } catch (Exception e) {  
                System.out.println(e);  
            }  
        }  
    }  
  
    private class DeletingRecord1Thread extends Thread {  
  
        public void run() {  
            try {  
                System.out.println(Thread.currentThread().getId()  
                        + " trying to lock record #1 on "  
                        + "DeletingRecord1Thread");  
                data.lock(1);  
                System.out.println(Thread.currentThread().getId()  
                        + " trying to delete record #1 on "  
                        + "DeletingRecord1Thread");  
                data.delete(1);  
                System.out.println(Thread.currentThread().getId()  
                        + " trying to unlock record #1 on "  
                        + "DeletingRecord1Thread");  
                data.unlock(1);  
            } catch (Exception e) {  
                System.out.println(e);  
            }  
        }  
    }  
  
    private class FindingRecordsThread extends Thread {  
  
        public void run() {  
            try {  
                System.out.println(Thread.currentThread().getId()  
                        + " trying to find records");  
                final String [] criteria = {"Palace", "Smallville", null, null,  
                        null, null, null};  
                final int [] results = data.find(criteria);  
  
                for (int i = 0; i < results.length; i++) {  
                    System.out.println(results.length + " results found.");  
                    try {  
                        final String message = Thread.currentThread().getId()  
                                + " going to read record #" + results[i]  
                                + " in FindingRecordsThread - still "  
                                + ((results.length - 1) - i) + " to go.";  
                        System.out.println(message);  
                        final String [] room = data.read(results[i]);  
                        System.out.println("Hotel (FindingRecordsThread): "  
                                + room[0]);  
                        System.out.println("Has next? "  
                                + (i < (results.length - 1)));  
                    } catch (Exception e) {  
                        /* 
                         * In case a record was found during the execution of 
                         * the find method, but deleted before the execution of 
                         * the read instruction, a RecordNotFoundException will 
                         * occur, which would be normal then 
                         */  
                        System.out.println("Exception in "  
                                + "FindingRecordsThread - " + e);  
                    }  
                }  
                System.out.println("Exiting for loop");  
            } catch (Exception e) {  
                System.out.println(e);  
            }  
        }  
    }  
} 
