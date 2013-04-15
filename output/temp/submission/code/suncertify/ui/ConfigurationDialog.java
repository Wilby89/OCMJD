package suncertify.ui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import suncertify.db.DataDBAccess;
import suncertify.util.ApplicationMode;

/**
 *
 * @author William Brosnan
 */
public class ConfigurationDialog extends JDialog {
    
    /**
     * Constant to represent property key in properties file
     */
    public static final String DB_PATH = "dbPath";
    /**
     * Constant to represent property key in properties file
     */
    public static final String RMI_HOST = "rmiHost";
    /**
     * Constant to represent property key in properties file
     */
    public static final String RMI_PORT = "rmiPort";
    /**
     * JPanel to hold the database selection components
     */
    private JPanel dbPanel;
    /**
     * JPanel to hold the RMI configuration components
     */
    private JPanel rmiPanel;
    /**
     * JPanel to hold confirmation buttons
     */
    private JPanel confirmationPanel;
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
    private ApplicationMode appMode;
    /**
     * JButton for confirmation
     */
    private JButton okButton;
    /**
     * JButton to exit the dialog
     */
    private JButton cancelButton;
    /**
     * flag to see if dbPath is valid
     */
    private boolean dbFlag = false;
    /**
     * flag to see if rmiPort is valid
     */
    private boolean portFlag = false;
    /**
     * flag to see if RMI host name is valid
     */
    private boolean hostFlag = false;
    /**
     * Properties instance to get/set properties from properties file
     */
    private Properties properties;
    
    /**
     * Constructor for class, set parameters for the JFrame on startup
     */
    public ConfigurationDialog(ApplicationMode applicationMode) {
        appMode = applicationMode;
        setTitle("Configure Options");
        //Manually setting size
        setSize(600,500);
        setResizable(false);
        //centers the GUI in the middle of the screen
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);        
        add(loadDBPanel(), BorderLayout.NORTH);
        add(loadRMIPanel(), BorderLayout.CENTER);
        add(loadConfirmationPanel(), BorderLayout.SOUTH);
    }
    
    /**
     * Load a JPanel with the necessary swing components for the GUI
     * @return JPanel which is added into main JFrame
     */
    private JPanel loadDBPanel() {
        dbPanel = new JPanel();
        dbPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        dbLabel = new JLabel("Enter database location");
        dbPanel.add(dbLabel);
        //Manually set size of JTextField
        dbField = new JTextField(DataDBAccess.DATABASE_NAME, 30);
        dbPanel.add(dbField);
        dbButton = new JButton("Choose File");
        dbButton.addActionListener(new ChooseFile());
        dbPanel.add(dbButton);
        portLabel = new JLabel("Enter an RMI port");
        dbPanel.add(portLabel);
        //Manually set size of JTextField
        portField = new JTextField(10);
        dbPanel.add(portField);
        hostLabel = new JLabel("Enter an RMI hostname");
        dbPanel.add(hostLabel);
        //Manually set size of JTextField
        hostField = new JTextField(20);
        dbPanel.add(hostField);
        //returns the JPanel to the class constructor
        return dbPanel;
    }
    
    private JPanel loadRMIPanel() {
        rmiPanel = new JPanel();
        portLabel = new JLabel("Enter an RMI port");
        rmiPanel.add(portLabel);
        //Manually set size of JTextField
        portField = new JTextField("5005", 10);
        rmiPanel.add(portField);
        hostLabel = new JLabel("Enter an RMI hostname");
        rmiPanel.add(hostLabel);
        //Manually set size of JTextField
        hostField = new JTextField(20);
        rmiPanel.add(hostField);
        //returns the JPanel to the class constructor
        return rmiPanel;
    }
    
    private JPanel loadConfirmationPanel() {
        confirmationPanel = new JPanel();
        okButton = new JButton("OK");
        okButton.addActionListener(new Confirmation());
        confirmationPanel.add(okButton);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new Cancel());
        confirmationPanel.add(cancelButton);
        //returns the JPanel to the class constructor
        return confirmationPanel;
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
    
    private class Confirmation implements ActionListener {
    
        @Override
        public void actionPerformed(ActionEvent e) {           
            switch (appMode) {
                case ALONE : {
                    portFlag = true;
                    hostFlag = true;
                    if (!dbField.getText().equals("")) {
                        dbFlag = true;
                    }
                    else {
                        JOptionPane.showMessageDialog(confirmationPanel, 
                                "Please enter a path to the local database");
                    }
                    break;
                }
                case SERVER : {
                    
                }
                case NETWORK : {
                    
                }                                       
            }            
        }
    }
    
    private class Cancel implements ActionListener {
    
        @Override
        public void actionPerformed(ActionEvent e) {           
            
        }
    }
}
