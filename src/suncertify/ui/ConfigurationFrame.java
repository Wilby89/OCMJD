package suncertify.ui;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author William Brosnan
 */
public class ConfigurationFrame extends JFrame {
    
    private JPanel configPanel;
    private JLabel dbLabel;
    private JTextField dbField;
    
    public ConfigurationFrame() {
        setTitle("Configure Options");
        setSize(600,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        configPanel = loadConfigPanel();
        add(configPanel);
        //pack();
    }
    
    private JPanel loadConfigPanel() {
        configPanel = new JPanel();
        dbLabel = new JLabel("Enter database location");
        configPanel.add(dbLabel);
        dbField = new JTextField();
        configPanel.add(dbField);
        JButton dbButton = new JButton();
        configPanel.add(dbButton);
        //JFileChooser dbFileChooser = new JFileChooser();
        //configPanel.add(dbFileChooser);
        JLabel portLabel = new JLabel("Enter an RMI port");
        configPanel.add(portLabel);
        JTextField portField = new JTextField();
        configPanel.add(portField);
        JLabel hostLabel = new JLabel("Enter an RMI hostname");
        configPanel.add(hostLabel);
        JTextField hostField = new JTextField();
        configPanel.add(hostField);
        
        return configPanel;
    }
    
}
