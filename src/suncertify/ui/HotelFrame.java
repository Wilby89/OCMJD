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
 */
public class HotelFrame extends JFrame{
    
    /*
     * Adding a logger instance for logging and debugging purposes.
     */
    private Logger logger = Logger.getLogger("suncertify.ui");   
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
    /**
     * ArrayList holding the records for each room
     */
    private ArrayList<Room> roomList;
    /**
     * JMenuBar to hold the file menu to quit and the help menu
     */
    JMenuBar menuBar = new JMenuBar();
    
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
        bottomPanel = loadTablePanel();
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
        //pack();
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
        searchButton.addActionListener(new SearchRoom());
        searchPanel.add(searchButton);
        loadButton = new JButton("Load Table");
        loadButton.addActionListener(new LoadRooms());
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
        for (Room room: roomList) {
            tableModel.addRoomRecord(room);
        }
        return table;
    }
    
    private class SearchRoom implements ActionListener {
    
        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    private class LoadRooms implements ActionListener {
    
        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    
}
