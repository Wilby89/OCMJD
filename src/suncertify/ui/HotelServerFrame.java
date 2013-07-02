package suncertify.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import suncertify.rmi.RoomRMIManager;
import suncertify.util.ApplicationMode;
import suncertify.util.PropertyManager;

/**
 *
 * @author William Brosnan
 *
 * This class is used to display the configuration options to the user and to
 * give starting and stopping functionality to the server. This user interface
 * is seen when the application is started in server mode.
 */
public class HotelServerFrame extends JFrame {

    /**
     * A version number to support serialization and de-serialization.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The mode the application is running in.
     */
    private ApplicationMode applicationMode = ApplicationMode.SERVER;
    private JLabel dbLabel;
    private JTextField dbField;
    private JLabel hostLabel;
    private JTextField hostField;
    private JLabel portLabel;
    private JTextField portField;
    private JPanel topPanel;
    private JPanel bottomPanel;
    /**
     * Button to start the server
     */
    private JButton startButton;
    /**
     * Button to stop the server
     */
    private JButton exitButton;
    /**
     * JMenuBar to hold the file menu to quit and the help menu
     */
    private JMenuBar menuBar = new JMenuBar();
    /**
     * PropertyManager instance to hold the properties from the
     * suncertify.properties file
     */
    PropertyManager propManager = PropertyManager.getInstance();
    /**
     * Configuration Dialog instance
     */
    ConfigurationDialog configurationFrame = null;
    /**
     * String to hold database location
     */
    private String dbPath = null;
    /**
     * String to hold port number
     */
    private String rmiPort = null;
    /**
     * String to hold dbHost
     */
    private String rmiHost = null;

    public HotelServerFrame() {
        setTitle("URLyBird Hotel Server Interface");
        setSize(600, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        configurationFrame = new ConfigurationDialog(applicationMode);
        configurationFrame.setVisible(true);
        JMenu fileMenu = new JMenu("File");
        JMenuItem quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener(new QuitApplication());
        fileMenu.add(quitMenuItem);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
        topPanel = loadStatusPanel();
        bottomPanel = loadServerControlPanel();
        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        initComponents();
    }

    private JPanel loadStatusPanel() {
        JPanel statusPanel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        statusPanel.setLayout(gridBagLayout);
        gridBagConstraints.insets = new Insets(10, 2, 2, 10);
        
        dbLabel = new JLabel("Database Location");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        statusPanel.add(dbLabel, gridBagConstraints);
        
        dbField = new JTextField(40);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        statusPanel.add(dbField, gridBagConstraints);
        
        hostLabel = new JLabel("Hostname");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        statusPanel.add(hostLabel, gridBagConstraints);
        
        hostField = new JTextField(40);        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        statusPanel.add(hostField, gridBagConstraints);
        
        portLabel = new JLabel("Port Number");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        statusPanel.add(portLabel, gridBagConstraints);
        
        portField = new JTextField(40);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        statusPanel.add(portField, gridBagConstraints);
        return statusPanel;
    }

    private JPanel loadServerControlPanel() {
        JPanel controlPanel = new JPanel();
        startButton = new JButton("Start Server");
        startButton.addActionListener(new StartServer());
        controlPanel.add(startButton);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new QuitApplication());
        controlPanel.add(exitButton);
        return controlPanel;
    }

    private void startServer() {
        RoomRMIManager.start();
        if (RoomRMIManager.isServerStarted()) {
            startButton.setEnabled(false);
        }
    }

    /**
     * Used to populate fields
     */
    private void initComponents() {
        dbPath = configurationFrame.getDatabaseLocation();
        rmiPort = configurationFrame.getRMIPort();
        rmiHost = configurationFrame.getRMIHost();

        if (!dbPath.isEmpty()) {
            dbField.setText(dbPath);
        }
        if (!rmiPort.isEmpty()) {
            portField.setText(rmiPort);
        }
        if (!rmiHost.isEmpty()) {
            hostField.setText(rmiHost);
        }
        
        dbField.setEditable(false);
        portField.setEditable(false);
        hostField.setEditable(false);
                       
        startButton.setEnabled(true);                                   
    }
    
    private class StartServer implements ActionListener {
    
        @Override
        public void actionPerformed(ActionEvent e) {
            startServer();
        }
    }
}

