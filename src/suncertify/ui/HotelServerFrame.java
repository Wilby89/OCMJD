package suncertify.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import suncertify.rmi.RoomRMIStarter;
import suncertify.util.ApplicationMode;
import suncertify.util.PropertyManager;

/**
 *
 * @author William Brosnan
 * 
 * This class is used to display the configuration options to the user and
 * to give starting and stopping functionality to the server. This user interface
 * is seen when the application is started in server mode.
 */
public class HotelServerFrame extends JFrame {
    
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
    /**
     * Button to start the server
     */
    private JButton startButton;
    /**
     * Button to stop the server
     */
    private JButton stopButton;
    /**
     * JMenuBar to hold the file menu to quit and the help menu
     */
    private JMenuBar menuBar = new JMenuBar();
    /**
     * PropertyManager instance to hold the properties from the 
     * suncertify.properties file
     */
    PropertyManager propManager = PropertyManager.getInstance();
    
    public HotelServerFrame() {
        setTitle("URLyBird Hotel Server Interface");
        setSize(1000,800);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ConfigurationDialog configurationFrame = new ConfigurationDialog(applicationMode);
        configurationFrame.setVisible(true);
        JMenu fileMenu = new JMenu("File");
        JMenuItem quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener(new QuitApplication());
        fileMenu.add(quitMenuItem);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
        topPanel = loadStatusPanel();
        add(topPanel, BorderLayout.NORTH);
        startServer();
    }
    
    private JPanel loadStatusPanel() {
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        dbLabel = new JLabel("Database Location");
        statusPanel.add(dbLabel);
        dbField = new JTextField(30);
        statusPanel.add(dbField);
        hostLabel = new JLabel("Hostname");
        statusPanel.add(hostLabel);
        hostField = new JTextField(30);
        statusPanel.add(hostField);
        portLabel = new JLabel("Hostname");
        statusPanel.add(portLabel);
        portField = new JTextField(30);
        statusPanel.add(portField);
        return statusPanel;
    }
    
    private JPanel loadServerControlPanel() {
        JPanel controlPanel = new JPanel();
        startButton = new JButton("Search");
        //startButton.addActionListener();
        controlPanel.add(startButton);
        stopButton = new JButton("Load Table");
        //stopButton.addActionListener();
        controlPanel.add(stopButton);
        return controlPanel;
    }
    
    private void startServer() {
        RoomRMIStarter.start();
    }
}
