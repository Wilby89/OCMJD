package suncertify.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import suncertify.db.LoadDatabase;
import suncertify.db.Room;

/**
 *
 * @author William Brosnan
 */
public class HotelFrame extends JFrame{
    
    /**
     * This JPanel will contain the search bar items and the JButtons   
     */
    private JPanel topPanel; 
    /**
     * This JPanel will contain JTable loaded with records from the database
     */
    private JPanel bottomPanel;
    /*
     * JLabel for hotel name search term
     */
    private JLabel nameLabel;
    /*
     * JLabel for location search term
     */
    private JLabel locationLabel;
    /*
     * JTextField to enter the hotel name search term
     */
    private JTextField nameField;
    /*
     * JTextField to enter the location search term
     */
    private JTextField locationField;
    /*
     * JButton to start search based on input in previous JTextFields
     */
    private JButton searchButton;
    /*
     * JButton to load all records in JTable
     */
    private JButton loadButton;
    private ArrayList roomList;
    
    public HotelFrame() throws FileNotFoundException, IOException {
        setTitle("URLyBird Hotel User Interface");
        setSize(1000,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //pack();
        topPanel = loadSearchPanel();
        bottomPanel = loadTablePanel();
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
    }
    
    private JPanel loadSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        nameLabel = new JLabel("Name");
        searchPanel.add(nameLabel);
        nameField = new JTextField(30);
        searchPanel.add(nameField);
        locationLabel = new JLabel("Location");
        searchPanel.add(locationLabel);
        locationField = new JTextField(30);
        searchPanel.add(locationField);
        searchButton = new JButton("Search");
        searchPanel.add(searchButton);
        loadButton = new JButton("Load Table");
        searchPanel.add(loadButton);
        return searchPanel;
    }
    
    private JPanel loadTablePanel() throws FileNotFoundException, IOException {
        JPanel tablePanel = new JPanel(new BorderLayout());
        JTable hotelTable = loadTable();
        tablePanel.add(new JScrollPane(hotelTable));
        return tablePanel;
    }
    
    private JTable loadTable() throws FileNotFoundException, IOException {
        RoomTableModel tableModel = new RoomTableModel();
        JTable table = new JTable(tableModel);
        roomList = LoadDatabase.loadDataBase();
        //Iterator it = roomList.iterator();
        //for (Object room: roomList) {
        //    System.out.println(room.toString());
        //    tableModel.addRoomRecord((String) room);
        //}
        //while (it.hasNext()) {
        //    tableModel.addRoomRecord((Room)it.next());
        //}
        return table;
    }
}
