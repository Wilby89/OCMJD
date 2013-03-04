package suncertify.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import suncertify.db.LoadDatabase;
import suncertify.db.Room;

/**
 *
 * @author William Brosnan
 * 
 * This class holds the boiler-plate code for creating the main GUI
 */
public class HotelFrame extends JFrame{
    
    /*
     * Adding a logger instance for logging and debugging purposes.
     */
    private Logger logger = Logger.getLogger("suncertify.ui");   
    /**
     * This JPanel will contain the search fields and JButtons   
     */
    private JPanel topPanel; 
    /**
     * This JPanel will contain JTable loaded with records from the database
     */
    private JPanel tablePanel;
    /**
     * This JPanel will contain the booking field and JButtons 
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
    /**
     * JLabel for customer ID
     */
    private JLabel custIDLabel;
    /**
     * JTextField to hold the customer ID
     */
    private JTextField custIDField;
    /**
     * ArrayList holding the records for each room
     */
    private ArrayList<Room> roomList;
    /**
     * JMenuBar to hold the file menu to quit and the help menu
     */
    JMenuBar menuBar = new JMenuBar();
    
    /**
     * Constructor for the JFrame, the JPanels that make up the GUI are added
     * the the JFrame here
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public HotelFrame() throws FileNotFoundException, IOException {
        setTitle("URLyBird Hotel User Interface");
        setSize(1000,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JMenu fileMenu = new JMenu("File");
        JMenuItem quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener(new QuitApplication());
        fileMenu.add(quitMenuItem);
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        this.setJMenuBar(menuBar);
        topPanel = loadSearchPanel();
        tablePanel = loadTablePanel();
        bottomPanel = loadBookingPanel();
        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        //pack();
    }
    
    /**
     * Used to separate the GUI construction code out into manageable functions
     * instead of having all the code in the constructor
     * @return the JPanel that contains the search fields and buttons
     */
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
        searchButton.addActionListener(new SearchRoom());
        searchPanel.add(searchButton);
        loadButton = new JButton("Load Table");
        loadButton.addActionListener(new LoadRooms());
        searchPanel.add(loadButton);
        return searchPanel;
    }
    
    /**
     * Used to separate the GUI construction code out into manageable functions
     * instead of having all the code in the constructor
     * @return the JPanel that holds the JTable populated with records from the
     * database
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private JPanel loadTablePanel() throws FileNotFoundException, IOException {
        JPanel tablePanel = new JPanel(new BorderLayout());
        JTable hotelTable = loadTable();
        tablePanel.add(new JScrollPane(hotelTable));
        return tablePanel;
    }
    
    /**
     * The records from the database are created in the RoomTableModel which 
     * the JTable is using
     * @return the JTable populated with records
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private JTable loadTable() throws FileNotFoundException, IOException {
        RoomTableModel tableModel = new RoomTableModel();
        JTable table = new JTable(tableModel);
        roomList = LoadDatabase.loadDataBase();
        for (Room room: roomList) {
            tableModel.addRoomRecord(room);
        }
        return table;
    }
    
    private JPanel loadBookingPanel() {
        JPanel bookingPanel = new JPanel();
        bookingPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        custIDLabel = new JLabel("Enter Customer ID");
        bookingPanel.add(custIDLabel);
        custIDField = new JTextField(30);
        bookingPanel.add(custIDField);
        return bookingPanel;
    }
    
    /**
     * Private class used to listen when the Search button is fired
     */
    private class SearchRoom implements ActionListener {
    
        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    /**
     * Private class used to listen when the Load Table button is fired
     * This loads all valid records from the DB
     */
    private class LoadRooms implements ActionListener {
    
        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    
}
