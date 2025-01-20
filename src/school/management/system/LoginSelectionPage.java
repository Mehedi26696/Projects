package school.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginSelectionPage extends JFrame implements ActionListener {

    JButton adminLogin, teacherLogin, studentLogin, backButton;

    LoginSelectionPage() {
        // Frame setup
        setTitle("Login Selection");
        setSize(800, 400);
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Set background color
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(60, 141, 199)); // Background color
        backgroundPanel.setBounds(0, 0, 800, 400);
        backgroundPanel.setLayout(null); // Use null layout for manual positioning
        add(backgroundPanel);

        // Main Heading
        JLabel heading = new JLabel("How do you want to login?", SwingConstants.CENTER);
        heading.setFont(new Font("Raleway", Font.BOLD, 28));
        heading.setForeground(Color.WHITE); // White text color
        heading.setBounds(100, 30, 600, 50); // Adjusted width and positioning
        backgroundPanel.add(heading);

        // Buttons for login options
        adminLogin = createStyledButton("Admin Login");
        adminLogin.setBounds(300, 100, 200, 40);
        adminLogin.addActionListener(this);
        backgroundPanel.add(adminLogin);

        teacherLogin = createStyledButton("Teacher Login");
        teacherLogin.setBounds(300, 160, 200, 40);
        teacherLogin.addActionListener(this);
        backgroundPanel.add(teacherLogin);

        studentLogin = createStyledButton("Student Login");
        studentLogin.setBounds(300, 220, 200, 40);
        studentLogin.addActionListener(this);
        backgroundPanel.add(studentLogin);

        // Back Button
        backButton = createStyledButton("Back");
        backButton.setBounds(300, 280, 200, 40);
        backButton.addActionListener(this);
        backgroundPanel.add(backButton);

        // Make the frame visible
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Raleway", Font.PLAIN, 16)); // Slightly larger font for readability
        button.setBackground(Color.WHITE); // White background
        button.setForeground(new Color(44, 78, 254)); // Blue text color
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createLineBorder(new Color(44, 78, 254), 2, true)); // Rounded border
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == adminLogin) {
            new Login("admin");
            setVisible(false);
        } else if (e.getSource() == teacherLogin) {
            new Login("teacher");
            setVisible(false);
        } else if (e.getSource() == studentLogin) {
            new Login("student");
            setVisible(false);
        } else if (e.getSource() == backButton) {
            new MainPage();
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new LoginSelectionPage();
    }
}
