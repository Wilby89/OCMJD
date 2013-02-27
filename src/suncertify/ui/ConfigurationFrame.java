package suncertify.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
     * Constructor for class, set parameters for the JFrame on startup
     */
    public ConfigurationFrame() {
        setTitle("Configure Options");
        //Manually setting size
        setSize(600,500);
        //centers the GUI in the middle of the screen
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        configPanel = loadConfigPanel();
        add(configPanel);
        //pack();
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
        dbField = new JTextField(30);
        configPanel.add(dbField);
        dbButton = new JButton("Choose File");
        configPanel.add(dbButton);
        //JFileChooser dbFileChooser = new JFileChooser();
        //configPanel.add(dbFileChooser);
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
    
}
