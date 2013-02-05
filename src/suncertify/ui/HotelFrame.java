package suncertify.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author William Brosnan
 */
public class HotelFrame extends JFrame{
    
    private JPanel topPanel; //top panel will contain search bar items
    private JPanel bottomPanel; //bottom panel will contain JTable loaded with records from the database
    
    public HotelFrame() {
        setTitle("URLyBird Hotel User Interface");
        setSize(1000,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //pack();
        //setVisible(true);
        topPanel = loadSearchPanel();
        bottomPanel = loadTablePanel();
        add(topPanel);
        add(bottomPanel);
    }
    
    private JPanel loadSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setVisible(true);
        return searchPanel;
    }
    
    private JPanel loadTablePanel() {
        JPanel tablePanel = new JPanel();
        JTable hotelTable = loadTable();
        hotelTable.setVisible(true);
        tablePanel.add(hotelTable);
        return tablePanel;
    }
    
    private JTable loadTable() {
        JTable table = new HotelTable();
        return table;
    }
}
