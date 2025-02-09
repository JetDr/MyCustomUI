import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.swing.*;

public class MyCustomUI extends JFrame {

    // Declare UI components
    private JTextArea textArea;  // Text area to display information (e.g., date/time)
    private JTextField textField; // Text field for user input
    private JPanel panel;         // Panel to hold the text field and text area
    private File logFile;         // File to log text input

    // Constructor to set up the user interface and functionality
    public MyCustomUI() {
        setTitle("Simple User Interface");  // Set window title
        setSize(400, 300);                  // Set window size
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Close application when window is closed
        setLocationRelativeTo(null);        // Center the window on the screen
        setLayout(new BorderLayout());      // Set layout to BorderLayout

        // Determine the user's Documents directory and create the log file reference
        String userHome = System.getProperty("user.home"); // Get user's home directory
        String documentsPath = userHome + File.separator + "Documents"; // Path to Documents
        logFile = new File(documentsPath, "log.txt"); // Log file path

        // Create the menu bar and menu items
        JMenuBar menuBar = new JMenuBar(); 
        JMenu menu = new JMenu("Menu");  // Create "Options" menu

        // Create menu items with corresponding actions
        JMenuItem item1 = new JMenuItem("Show Date & Time");
        JMenuItem item2 = new JMenuItem("Log to File");
        JMenuItem item3 = new JMenuItem("Change Background Color");
        JMenuItem item4 = new JMenuItem("Exit");

        // Add action listeners to menu items
        item1.addActionListener(e -> showDateTime()); // Show current date and time
        item2.addActionListener(e -> logToFile());   // Log text field content to file
        item3.addActionListener(e -> changeBackgroundColor()); // Change background color
        item4.addActionListener(e -> System.exit(0)); // Exit the application

        // Add menu items to menu and menu bar
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menu.add(item4);
        menuBar.add(menu);
        setJMenuBar(menuBar);  // Set the menu bar for the window

        // Create panel to hold components
        panel = new JPanel();
        panel.setLayout(new BorderLayout()); // Set layout of panel
        add(panel, BorderLayout.CENTER); // Add panel to the center of the window

        // Create text field for user input and set it to center-aligned
        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(textField, BorderLayout.CENTER); // Add text field to the panel

        // Create text area for displaying information and disable editing
        textArea = new JTextArea();
        textArea.setEditable(false); // Make text area non-editable
        panel.add(new JScrollPane(textArea), BorderLayout.SOUTH); // Add text area to the panel
    }

    // Method to display the current date and time in the text area
    private void showDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now(); // Get current date and time
        textArea.setText(dtf.format(now)); // Set formatted date/time to text area
    }

    // Method to log the input text to a file
    private void logToFile() {
        String content = textField.getText().trim(); // Get and trim the content of text field
        if (!content.isEmpty()) { // Ensure the input is not empty
            try {
                // Append text to the file using BufferedWriter
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                    writer.write(content);   // Write the content to the log file
                    writer.newLine();        // Add a new line after writing
                }

                // Display success message when content is logged
                JOptionPane.showMessageDialog(this, "Logged to file successfully!");

            } catch (IOException e) { // Handle file writing errors
                JOptionPane.showMessageDialog(this, "Error writing to file: " + e.getMessage());
            }
        } else { // If input is empty, show a message
            JOptionPane.showMessageDialog(this, "Input field is empty!");
        }
    }

    // Method to change the background color of UI components to a random shade of green
    private void changeBackgroundColor() {
        Random random = new Random(); // Create a random number generator
        float hue = 0.25f + random.nextFloat() * (0.42f - 0.25f); // Generate random hue for green
        Color randomGreenColor = Color.getHSBColor(hue, 1.0f, 1.0f); // Create a random green color

        // Set background color for all components in the panel
        panel.setBackground(randomGreenColor);
        textField.setBackground(randomGreenColor);
        textArea.setBackground(randomGreenColor);

        // Ensure the UI updates visually
        panel.repaint();
        textField.repaint();
        textArea.repaint();
    }

    // Main method to launch the UI application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyCustomUI myCustomUI = new MyCustomUI(); // Create the MyCustomUI instance
            myCustomUI.setVisible(true);          // Make the UI visible
        });
    }
}

