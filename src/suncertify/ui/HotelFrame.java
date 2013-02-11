package suncertify.ui;

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
    
    public HotelFrame() {
        setTitle("URLyBird Hotel User Interface");
        setSize(1000,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //pack();
        topPanel = loadSearchPanel();
        bottomPanel = loadTablePanel();
        add(topPanel);
        topPanel.setVisible(true);
        add(bottomPanel);
        bottomPanel.setVisible(true);
    }
    
    private JPanel loadSearchPanel() {
        JPanel searchPanel = new JPanel();
        int align = FlowLayout.CENTER;
        searchPanel.setLayout(new FlowLayout(align));
        nameLabel = new JLabel("Name");
        searchPanel.add(nameLabel);
        nameField = new JTextField();
        searchPanel.add(nameField);
        locationLabel = new JLabel("Location");
        searchPanel.add(locationLabel);
        locationField = new JTextField();
        searchPanel.add(locationField);
        searchButton = new JButton("Search");
        searchPanel.add(searchButton);
        return searchPanel;
    }
    
    private JPanel loadTablePanel() {
        JPanel tablePanel = new JPanel();
        JTable hotelTable = loadTable();
        //hotelTable.setVisible(true);
        tablePanel.add(hotelTable);
        return tablePanel;
    }
    
    private JTable loadTable() {
        JTable table = new HotelTable();
        return table;
    }
}
