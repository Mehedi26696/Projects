package school.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JButton login, cancel, register; // Added register button
    JTextField tfusername;
    JPasswordField tfpassword;
    String person;

    // Constructor
    Login(String person) {
        this.person = person;
        setTitle("Login - School Management System");

        // Set Layout and LayeredPane
        setLayout(null); // Custom layout
        getContentPane().setBackground(new Color(240, 248, 255)); // Light background

        // Add Heading Text
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1200, 600); // Set bounds for the layered pane
        add(layeredPane); // Add layered pane to the frame
        JLabel heading = new JLabel("Login as " + person);
        heading.setBounds(300, 20, 500, 40); // Position and size of the heading
        heading.setFont(new Font("Raleway", Font.BOLD, 22)); // Font style and size
        heading.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text
        heading.setForeground(new Color(50, 50, 150)); // Text color
        layeredPane.add(heading, JLayeredPane.PALETTE_LAYER); // Add heading to the layered pane

        // Add Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("assets/login.jpeg"));
        Image i2 = i1.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(50, 150, 400, 300);
        add(image);

        // Username Label
        JLabel lblusername = new JLabel("Username:");
        lblusername.setBounds(500, 150, 150, 30);
        lblusername.setFont(new Font("Raleway", Font.BOLD, 16));
        lblusername.setForeground(new Color(70, 70, 70)); // Darker text color
        add(lblusername);

        // Username Field
        tfusername = new JTextField();
        tfusername.setBounds(650, 150, 250, 30);
        tfusername.setFont(new Font("Raleway", Font.PLAIN, 14));
        tfusername.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2)); // Light blue border
        add(tfusername);

        // Password Label
        JLabel lblpassword = new JLabel("Password:");
        lblpassword.setBounds(500, 220, 150, 30);
        lblpassword.setFont(new Font("Raleway", Font.BOLD, 16));
        lblpassword.setForeground(new Color(70, 70, 70)); // Darker text color
        add(lblpassword);

        // Password Field
        tfpassword = new JPasswordField();
        tfpassword.setBounds(650, 220, 250, 30);
        tfpassword.setFont(new Font("Raleway", Font.PLAIN, 14));
        tfpassword.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2)); // Light blue border
        add(tfpassword);

        // Login Button
        login = new JButton("Login");
        login.setBounds(650, 300, 120, 40);
        login.setBackground(new Color(70, 130, 180)); // Steel blue
        login.setForeground(Color.WHITE);
        login.setFont(new Font("Raleway", Font.BOLD, 14));
        login.setBorder(BorderFactory.createEmptyBorder());
        login.setFocusPainted(false);
        login.addActionListener(this);
        login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(login);

        // Cancel Button
        cancel = new JButton("Cancel");
        cancel.setBounds(780, 300, 120, 40);
        cancel.setBackground(new Color(220, 20, 60)); // Crimson red
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Raleway", Font.BOLD, 14));
        cancel.setBorder(BorderFactory.createEmptyBorder());
        cancel.setFocusPainted(false);
        cancel.addActionListener(this);
        cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(cancel);

        // Register Button
        if("admin".equals(person)){
            
        } else{
        register = new JButton("Register");
        register.setBounds(650, 360, 250, 40); // Positioned below Login and Cancel
        register.setBackground(new Color(34, 139, 34)); // Forest Green
        register.setForeground(Color.WHITE);
        register.setFont(new Font("Raleway", Font.BOLD, 14));
        register.setBorder(BorderFactory.createEmptyBorder());
        register.setFocusPainted(false);
        register.addActionListener(this);
        register.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(register);
        }

        // Frame Properties
        setSize(1000, 600); // Increased screen size
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    // Action Performed
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            String username = tfusername.getText();
            String password = new String(tfpassword.getPassword());

            // Admin credentials check
            if ("admin".equals(person) && "admin".equals(username) && "1234".equals(password)) {
                setVisible(false);
                new AdminDashboard();  
                return;
            }

            // Database credentials check
            String query = "SELECT * FROM register WHERE username = '" + username + "' AND password = '" + password + "'";
            try {
                Connect c = new Connect();
                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) {
                    setVisible(false);
                    if ("teacher".equals(person)) {
                        new TeacherDashboard(username); // Navigate to Teacher Dashboard
                    } else {
                        new StudentDashboard(username); // Navigate to Student Dashboard
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Close the connection
                c.s.close();

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == cancel) {
            // Close or navigate to Splash Screen
            setVisible(false);
            new MainPage();
              } 
         else if (ae.getSource() == register) {
            // Open the Register page
           
            if("student".equals(person)){
                new Register("login1");
            }
            else if("teacher".equals(person)){
                new Register("login2");
            }
            else if("admin".equals(person)){
                new Register("login3");
            }
            
            setVisible(false);
        }
    } 

    // Main Method
    public static void main(String[] args) {
        new Login("student"); // Specify user type (e.g., "admin") if necessary
    }
}
