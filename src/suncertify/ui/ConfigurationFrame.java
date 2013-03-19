package suncertify.ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import suncertify.db.DataDBAccess;
import suncertify.util.ApplicationMode;

/**
 *
 * @author William Brosnan
 */
public class ConfigurationFrame extends JFrame {
    
    /**
     * JPanel to hold the swing components
     */
    private JPanel configPanel;
    /**
     * JLabel for database location
     */
    private JLabel dbLabel;
    /**
     * JTextField for database location
     */
    private JTextField dbField;
    /**
     * JButton for database location, action listener on button
     * will call a JFileChooser making it easier for user to choose file
     * can be used independently of dbField
     */
    private JButton dbButton;
    /**
     * JLabel for port number for RMI server
     */
    private JLabel portLabel;
    /**
     * JTextField to enter RMI port number
     */
    private JTextField portField;
    /**
     * JLabel for host name for RMI
     */
    private JLabel hostLabel;
    /**
     * JTextField for host name location
     */
    private JTextField hostField;
    /**
     * The mode that the application is running in (standalone, server or
     * network client)
     */
    ApplicationMode appMode;
    
    /**
     * Constructor for class, set parameters for the JFrame on startup
     */
    public ConfigurationFrame(ApplicationMode applicationMode) {
        appMode = applicationMode;
        setTitle("Configure Options");
        //Manually setting size
        setSize(600,500);
        setResizable(false);
        //centers the GUI in the middle of the screen
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        configPanel = loadConfigPanel();
        add(configPanel);
    }
    
    /**
     * Load a JPanel with the necessary swing components for the GUI
     * @return JPanel which is added into main JFrame
     */
    private JPanel loadConfigPanel() {
        configPanel = new JPanel();
        dbLabel = new JLabel("Enter database location");
        configPanel.add(dbLabel);
        //Manually set size of JTextField
        dbField = new JTextField(DataDBAccess.DATABASE_NAME, 30);
        configPanel.add(dbField);
        dbButton = new JButton("Choose File");
        dbButton.addActionListener(new ChooseFile());
        configPanel.add(dbButton);
        portLabel = new JLabel("Enter an RMI port");
        configPanel.add(portLabel);
        //Manually set size of JTextField
        portField = new JTextField(10);
        configPanel.add(portField);
        hostLabel = new JLabel("Enter an RMI hostname");
        configPanel.add(hostLabel);
        //Manually set size of JTextField
        hostField = new JTextField(20);
        configPanel.add(hostField);
        //returns the JPanel to the class constructor
        return configPanel;
    }
    
    public String getDBLocation() {
        return this.dbField.getText();
    }
    
    /**
     * Private class used to listen when the Search button is fired
     * Search will use the entries added in hotel name and location fields
     * as search terms
     */
    private class ChooseFile implements ActionListener {
    
        @Override
        public void actionPerformed(ActionEvent e) {           
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Database file", "db");
            fileChooser.setFileFilter(filter);
            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                dbField.setText(fileChooser.getSelectedFile().toString());
            }
        }
    }
}
