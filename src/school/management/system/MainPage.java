package school.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame implements ActionListener {

    JButton loginButton, chatButton, aboutUsButton, registerButton;

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

        // School logo
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/assets/DU_Scholars_Academy.png"));
        Image logoImage = logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedLogo = new ImageIcon(logoImage);
        JLabel logoLabel = new JLabel(resizedLogo);
        logoLabel.setBounds(0, -50, 250, 250);
        background.add(logoLabel);

        // Main Heading
        JLabel mainHeading = new JLabel("Welcome to DU Scholars Academy", SwingConstants.CENTER);
        mainHeading.setFont(new Font("Raleway", Font.BOLD, 48));
        mainHeading.setForeground(new Color(211, 230, 253));
        mainHeading.setBounds(150, 200, 900, 60);
        background.add(mainHeading);

        // Subheading
        JLabel subHeading = new JLabel("Empowering Students to Learn, Grow, and Succeed", SwingConstants.CENTER);
        subHeading.setFont(new Font("Raleway", Font.PLAIN, 24));
        subHeading.setForeground(new Color(60, 141, 199));
        subHeading.setBounds(300, 270, 600, 50);
        background.add(subHeading);

        

        // Panel for other buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(750, 50, 400, 40); // Position at the bottom
        buttonPanel.setOpaque(false); // Transparent panel
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 0)); // 1 row, 3 columns with spacing

        // Add other buttons to the panel
        buttonPanel.add(createChatButton());
        buttonPanel.add(createAboutUsButton());
        buttonPanel.add(createLoginButton());
        buttonPanel.add(createRegisterButton());

        // Add button panel to the background
        background.add(buttonPanel);

        // Make the frame visible
        setVisible(true);
    }
   
    private JButton createLoginButton(){
        
        loginButton = createStyledButton("Login");
        loginButton.addActionListener(this);
        return loginButton;
    }

    private JButton createChatButton() {
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

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Raleway", Font.PLAIN, 14));
        button.setBackground(new Color(44, 78, 254));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(60, 141, 199), 1, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            new LoginSelectionPage();
            setVisible(false);
        } else if (e.getSource() == chatButton) {
            Thread serverThread = new Thread(() -> Server.startServer());
            serverThread.start();
            Client.startClient();
        } else if (e.getSource() == aboutUsButton) {
            //JOptionPane.showMessageDialog(this, "Navigating to About Us...");
        } else if (e.getSource() == registerButton) {
            //JOptionPane.showMessageDialog(this, "Navigating to Register...");
            new Register("mainpage");
        }
    }

    public static void main(String[] args) {
        new MainPage();
    }
}