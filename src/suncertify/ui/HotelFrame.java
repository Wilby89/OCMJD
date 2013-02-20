package suncertify.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author William Brosnan
 */
public class HotelFrame extends JFrame{
    
    private JPanel topPanel; //top panel will contain search bar items
    private JPanel bottomPanel; //bottom panel will contain JTable loaded with records from the database
    private JScrollPane scrollPane; //Scroller for JTable records
    private JLabel nameLabel;
    private JLabel locationLabel;
    private JTextField nameField;
    private JTextField locationField;
    private JButton searchButton;
    private JButton loadButton;
    
    public HotelFrame() {
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
    
    private JPanel loadTablePanel() {
        JPanel tablePanel = new JPanel();
        JTable hotelTable = loadTable();
        tablePanel.add(hotelTable);
        return tablePanel;
    }
    
    private JTable loadTable() {
        JTable table = new JTable(new RoomTableModel());
        return table;
    }
}
