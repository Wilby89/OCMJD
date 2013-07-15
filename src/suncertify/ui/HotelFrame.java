package suncertify.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import suncertify.util.ApplicationMode;
import suncertify.util.HotelUtils;
import suncertify.util.PropertyManager;

/**
 * This class holds the code for creating the main GUI
 * 
 * @author William Brosnan
 */
public class HotelFrame extends JFrame {
    
    /**
     * A version number to support serialization and de-serialization.
     */
    private static final long serialVersionUID = 1L;
    /*
     * Adding a logger instance for logging and debugging purposes.
     */
    private Logger logger = Logger.getLogger("suncertify.ui");   
    /**
     * This JPanel will contain the search fields and JButtons.
     */
    private JPanel topPanel; 
    /**
     * This JPanel will contain JTable loaded with records from the database.
     */
    private JPanel tablePanel;
    /**
     * This JPanel will contain the booking field and JButtons.
     */
    private JPanel bottomPanel;
    /*
     * JLabel for hotel name search term
     */
    private JLabel nameLabel;
    /*
     * JLabel for location search term.
     */
    private JLabel locationLabel;
    /*
     * JTextField to enter the hotel name search term.
     */
    private JTextField nameField;
    /*
     * JTextField to enter the location search term.
     */
    private JTextField locationField;
    /*
     * JButton to start search based on input in JTextFields.
     */
    private JButton searchButton;
    /*
     * JButton to load all records in JTable.
     */
    private JButton loadButton;
    /**
     * JLabel for customer ID.
     */
    private JLabel custIDLabel;
    /**
     * JTextField to hold the customer ID.
     */
    private JTextField custIDField;
    /**
     * JButton for reserving a room.
     */
    private JButton reserveButton;
    /**
     * ArrayList holding the records for each room.
     */
    private String[] roomList;
    /**
     * JMenuBar to hold the file menu to quit and the help menu.
     */
    private JMenuBar menuBar = new JMenuBar();
    /**
     * Enumeration to know in what mode to start in.
     */
    private ApplicationMode applicationMode;
    /**
     * A controller to fulfill the controller function of the MVC pattern.
     */
    private HotelFrameController controller;
    /**
     * This is the <code>JTable</code> that will hold the records.
     */
    private JTable hotelTable;
    /**
     * The Custom Table Model created the hold the <code>JTable</code> records
     * It extends <code>AbstractTableModel</code>
     */
    private RoomTableModel tableModel;
    /**
     * String to hold the location of the database got from the 
     * <code>ConfigurationFrame</code> dialog.
     */
    private String dbLocation;
    /**
     * Configuration dialog instance
     */
    ConfigurationDialog configurationFrame = null;
    
    
    /**
     * Constructor for the JFrame, the JPanels that make up the GUI are added
     * the the JFrame here.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public HotelFrame(String[] args) {
        setTitle("URLyBird Hotel User Interface");
        setSize(1000,630);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        PropertyManager propManager = PropertyManager.getInstance();
        
        if (args.length == 0) {
            applicationMode = ApplicationMode.NETWORK;
        }
        else {
            applicationMode = ApplicationMode.ALONE;
        }
        
        switch (applicationMode) {
            case ALONE:
                configurationFrame = new ConfigurationDialog(applicationMode);
                configurationFrame.setVisible(true);
        
            String dbPath = configurationFrame.getDatabaseLocation();
            logger.log(Level.INFO, "Database location is: " + dbPath);

            controller = new HotelFrameController(applicationMode, dbPath 
                     ,configurationFrame.getRMIPort());
            break;
                
            case NETWORK:
                configurationFrame = new ConfigurationDialog(applicationMode);
                configurationFrame.setVisible(true);
        
                String rmiHost = configurationFrame.getRMIHost();
                logger.log(Level.INFO, "RMI hostname is: " + rmiHost);
        
                try {
                controller = new HotelFrameController(applicationMode, rmiHost 
                 ,configurationFrame.getRMIPort());
                } catch (RuntimeException ruex) {
                    JOptionPane.showMessageDialog(this, "No viable "
                    + "connection could be established", "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(this, "Application will be"
                            + " shut down", "Connection Error",
                    JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
        }               
        
        JMenu fileMenu = new JMenu("File");
        JMenuItem quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener(new QuitApplication());
        fileMenu.add(quitMenuItem);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
        topPanel = loadSearchPanel();
        tablePanel = loadTablePanel();
        bottomPanel = loadBookingPanel();
        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Used to separate the GUI construction code out into manageable functions
     * instead of having all the code in the constructor.
     * @return the JPanel that contains the search fields and buttons.
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
     * instead of having all the code in the constructor.
     * @return the JPanel that holds the JTable populated with records from the
     * database.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private JPanel loadTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        hotelTable = loadTable();
        tablePanel.add(new JScrollPane(hotelTable));
        return tablePanel;
    }
    
    /**
     * The records from the database are created in the RoomTableModel which 
     * the JTable is using.
     * @return the JTable populated with records
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private JTable loadTable() {
        tableModel = this.controller.getAllRooms();
        JTable table = new JTable(tableModel);
        return table;
    }
    
    /**
     * Refreshes the data in the <code>JTable</code> by re-setting the 
     * <code>RoomTableModel</code>.
     */
    private void refreshTable() {
        this.hotelTable.setModel(this.tableModel);       
    }
    
    /**
     * Used to separate the GUI construction code out into manageable functions
     * instead of having all the code in the constructor.
     * @return the JPanel that contains the booking label and buttons.
     */
    private JPanel loadBookingPanel() {
        JPanel bookingPanel = new JPanel();
        bookingPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        custIDLabel = new JLabel("Select a record and press Reserve Room button "
                + "to reserve");
        bookingPanel.add(custIDLabel);
        reserveButton = new JButton("Reserve Room");
        reserveButton.addActionListener(new ReserveRoom());
        bookingPanel.add(reserveButton);
        return bookingPanel;
    }
    
    /**
     * Private class used to listen when the Search button is fired.
     * Search will use the entries added in hotel name and location fields
     * as search terms.
     */
    private class SearchRoom implements ActionListener {
    
        @Override
        public void actionPerformed(ActionEvent e) {
            String hotelNameCriteria = nameField.getText();
            String locationCriteria = locationField.getText();
            try {                
                if (hotelNameCriteria.equals("") && locationCriteria.equals("")) {
                    tableModel = controller.getAllRooms();
                    refreshTable();
                }
                else {
                    tableModel = controller.getRoomsByCriteria(hotelNameCriteria, locationCriteria);
                    refreshTable();
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
                System.err.println("Search problem found: " + ex.getMessage());
            }
            nameField.setText("");
            locationField.setText("");
        }
    }
    
    /**
     * Private class used to listen when the Load Table button is fired.
     * This loads all valid records from the DB
     */
    private class LoadRooms implements ActionListener {
    
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                tableModel = controller.getAllRooms();
                refreshTable();
            } catch (Exception ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
                System.err.println("Load all rooms problem found: " + ex.getMessage());
            }            
        }
    }
    
    /**
     * Private class used to listen when the Reserve Room button is fired
     * This reserves a room in the DB
     */
    private class ReserveRoom implements ActionListener {
    
        @Override
        public void actionPerformed(ActionEvent e) {
            int recordRow = hotelTable.getSelectedRow();
            String csrNumber = "";
            boolean csrFlag = false;
            if (recordRow < 0) {
                JOptionPane.showMessageDialog(bottomPanel, "Please select a row");
                csrFlag = false;
            }
            else {
                csrNumber = (String) JOptionPane.showInputDialog
                    (bottomPanel, "Enter CSR number of 8 digits");
                if (csrNumber == null) {
                    csrFlag = false;
                }
                else if (csrNumber.length() != 8) {
                    JOptionPane.showMessageDialog(bottomPanel, 
                        "CSR length must be 8 digits, you have entered " 
                            + csrNumber.length() + " characters.");                                
                    csrFlag = false;
                }
                else if (!HotelUtils.isNumeric(csrNumber)) {
                    JOptionPane.showMessageDialog(bottomPanel, 
                        "CSR length must be all digits");                                
                    csrFlag = false;
                }
                else {
                    csrFlag = true;
                }
            } 
            
            if (csrFlag == true) {
                try {
                    controller.reserveRoom(recordRow, csrNumber);
                    tableModel = controller.getAllRooms();
                    refreshTable();
                } catch(Exception ex) {
                    logger.log(Level.SEVERE, ex.getMessage(), ex);
                    System.err.println("Reserve room problem found: " + ex.getMessage());
                }
            }            
        }
    }    
}
