package school.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame implements ActionListener {

    JButton studentLogin, adminLogin, teacherLogin, chatButton, aboutUsButton,registerButton;

    MainPage() {
        // Frame setup
        setTitle("School Management System");
        setSize(1200, 600);
        setLocation(100, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background image
        ImageIcon bg = new ImageIcon(getClass().getResource("/assets/bg1.png"));
        Image i2 = bg.getImage().getScaledInstance(1200, 600, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 1200, 600);
        add(background);

        // School name on the left
        JLabel schoolName = new JLabel("DU High School");
        schoolName.setFont(new Font("Raleway", Font.BOLD, 30));
        schoolName.setForeground(Color.WHITE);
        schoolName.setBounds(40, 30, 300, 50);
        background.add(schoolName);

        // Main Heading
        JLabel mainHeading = new JLabel("Welcome to DU High School", SwingConstants.CENTER);
        mainHeading.setFont(new Font("Raleway", Font.BOLD, 48)); // Font style and size for the main heading
        mainHeading.setForeground(Color.WHITE); // Text color
        mainHeading.setBounds(250, 200, 700, 60); // Centered approximately (x, y, width, height)
        background.add(mainHeading);

       // Subheading Line 1
        JLabel subHeading1 = new JLabel("Empowering Students to Learn, Grow, and Succeed", SwingConstants.CENTER);
        subHeading1.setFont(new Font("Raleway", Font.PLAIN, 24)); // Font style and size for subheading
        subHeading1.setForeground(new Color(30, 144, 255)); // Text color
        subHeading1.setBounds(300, 270, 600, 50); // Centered approximately
        background.add(subHeading1);

        // Subheading Line 2
        JLabel subHeading2 = new JLabel("A Place Where Education Meets Excellence", SwingConstants.CENTER);
        subHeading2.setFont(new Font("Raleway", Font.PLAIN, 24)); // Font style and size for subheading
        subHeading2.setForeground(new Color(30, 144, 255)); // Text color
        subHeading2.setBounds(300, 320, 600, 20); // Centered approximately
        background.add(subHeading2);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(500, 40, 650, 40); //(left, top, width, height)// Adjust dimensions to fit horizontal layout
        buttonPanel.setOpaque(false); // Transparent panel
        buttonPanel.setLayout(new GridLayout(1, 6, 10, 0)); // 1 row, 6 columns with horizontal spacing

        // Add buttons to the panel
        buttonPanel.add(createStudentLoginButton());
        buttonPanel.add(createAdminLoginButton());
        buttonPanel.add(createTeacherLoginButton());
        buttonPanel.add(createAnnouncementButton());
        buttonPanel.add(createAboutUsButton());
        buttonPanel.add(createRegisterButton());

        // Add button panel to the background
        background.add(buttonPanel);

        // Make the frame visible
        setVisible(true);
    }

    // Separate methods to create buttons
    private JButton createStudentLoginButton() {
        studentLogin = createStyledButton("Student Login");
        studentLogin.addActionListener(this);
        return studentLogin;
    }

    private JButton createAdminLoginButton() {
        adminLogin = createStyledButton("Admin Login");
        adminLogin.addActionListener(this);
        return adminLogin;
    }

    private JButton createTeacherLoginButton() {
        teacherLogin = createStyledButton("Teacher Login");
        teacherLogin.addActionListener(this);
        return teacherLogin;
    }

    private JButton createAnnouncementButton() {
        chatButton = createStyledButton("Chat");
        chatButton.addActionListener(this);
        return chatButton;
    }

    private JButton createAboutUsButton() {
        aboutUsButton = createStyledButton("About Us");
        aboutUsButton.addActionListener(this);
        return aboutUsButton;
    }
    
    private JButton createRegisterButton() {
        registerButton = createStyledButton("Register");
        registerButton.addActionListener(this);
        return registerButton;
    }

    // Utility method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Raleway", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(150, 40)); // Smaller button size
        button.setBackground(new Color(0, 102, 204)); // Blue background
        button.setForeground(Color.WHITE); // White text
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true)); // Rounded border
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == studentLogin) {
           // JOptionPane.showMessageDialog(this, "Navigating to Student Login Page...");
            new Login("student");
            setVisible(false);
        } else if (e.getSource() == adminLogin) {
          //  JOptionPane.showMessageDialog(this, "Navigating to Admin Login Page...");
            new Login("admin");
        } else if (e.getSource() == teacherLogin) {
           // JOptionPane.showMessageDialog(this, "Navigating to Teacher Login Page...");
            new Login("teacher");
        } else if (e.getSource() == chatButton) {
            //JOptionPane.showMessageDialog(this, "Navigating to Announcements...");
            Thread serverThread = new Thread(() -> Server.startServer());
            serverThread.start();

           // Start the client
            Client.startClient();
        } else if (e.getSource() == aboutUsButton) {
           // JOptionPane.showMessageDialog(this, "Navigating to About Us...");
        }else if(e.getSource() == registerButton){
            JOptionPane.showMessageDialog( this, "Navigating to register...");
            new Register("mainpage");
        }
    }

    public static void main(String[] args) {
        new MainPage();
    }
}