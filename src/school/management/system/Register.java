package school.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.sql.*;
 

class CustomButton extends JButton {

    public CustomButton(String text, int width, int height, Font font) {
        super(text);
        setPreferredSize(new Dimension(width, height));
        setFont(font);
        setBackground(new Color(34, 139, 34)); // Forest Green background
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder());
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public CustomButton(String text) {
        this(text, 200, 50, new Font("Raleway", Font.BOLD, 20));
    }
}

public class Register extends JFrame implements ActionListener {

    JTextField fullNameField, rollField, emailField, usernameField, dobField, phoneField, bcField;
    JTextArea addressField;
    JPasswordField passwordField;
    CustomButton submit, uploadImageButton;
    Button cancel;
    JLabel uploadedImageLabel;
    File selectedFile; // To store the selected image file
    
    String way;

    public Register(String way) {
        this.way = way;
        setTitle("Registration Portal");
        setSize(1200, 800); // Increased size to fit new fields
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 144, 255));
        headerPanel.setBounds(0, 0, 1200, 100);
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        add(headerPanel);

        JLabel headerLabel = new JLabel("Welcome to Registration Portal");
        headerLabel.setFont(new Font("Raleway", Font.BOLD, 30));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(50, 120, 500, 600);
        formPanel.setBackground(new Color(245, 245, 245));
        add(formPanel);

        Font font = new Font("Raleway", Font.PLAIN, 18);

        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setBounds(30, 30, 150, 30);
        fullNameLabel.setFont(font);
        formPanel.add(fullNameLabel);

        fullNameField = new JTextField();
        fullNameField.setBounds(200, 30, 250, 30);
        fullNameField.setFont(font);
        formPanel.add(fullNameField);

        JLabel rollLabel = new JLabel("Roll/ID:");
        rollLabel.setBounds(30, 80, 150, 30);
        rollLabel.setFont(font);
        formPanel.add(rollLabel);

        rollField = new JTextField();
        rollField.setBounds(200, 80, 250, 30);
        rollField.setFont(font);
        formPanel.add(rollField);

        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(30, 130, 150, 30);
        dobLabel.setFont(font);
        formPanel.add(dobLabel);

        dobField = new JTextField();
        dobField.setBounds(200, 130, 250, 30);
        dobField.setFont(font);
        formPanel.add(dobField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(30, 180, 150, 30);
        addressLabel.setFont(font);
        formPanel.add(addressLabel);

        addressField = new JTextArea();
        addressField.setBounds(200, 180, 250, 60);
        addressField.setFont(font);
        addressField.setLineWrap(true);
        addressField.setWrapStyleWord(true);
        formPanel.add(addressField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(30, 260, 150, 30);
        phoneLabel.setFont(font);
        formPanel.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(200, 260, 250, 30);
        phoneField.setFont(font);
        formPanel.add(phoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 310, 150, 30);
        emailLabel.setFont(font);
        formPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(200, 310, 250, 30);
        emailField.setFont(font);
        formPanel.add(emailField);

        JLabel bcLabel = new JLabel("Birth Cert./NID:");
        bcLabel.setBounds(30, 360, 150, 30);
        bcLabel.setFont(font);
        formPanel.add(bcLabel);

        bcField = new JTextField();
        bcField.setBounds(200, 360, 250, 30);
        bcField.setFont(font);
        formPanel.add(bcField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(30, 410, 150, 30);
        usernameLabel.setFont(font);
        formPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(200, 410, 250, 30);
        usernameField.setFont(font);
        formPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 460, 150, 30);
        passwordLabel.setFont(font);
        formPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 460, 250, 30);
        passwordField.setFont(font);
        formPanel.add(passwordField);

        submit = new CustomButton("Register");
        submit.setBounds(50, 530, 200, 50);
        submit.addActionListener(this);
        formPanel.add(submit);
        
        cancel = new Button("Cancel");
        cancel.setBounds(260, 530, 200, 50);
        cancel.setBackground(Color.red);
        cancel.setFont(new Font("Raleway", Font.BOLD, 20));
        cancel.addActionListener(this);
        formPanel.add(cancel);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(null);
        imagePanel.setBounds(600, 120, 500, 600);
        imagePanel.setBackground(new Color(245, 245, 245));
        add(imagePanel);

        uploadedImageLabel = new JLabel("No Image Uploaded", SwingConstants.CENTER);
        uploadedImageLabel.setBounds(100, 50, 300, 300);
        uploadedImageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagePanel.add(uploadedImageLabel);

        uploadImageButton = new CustomButton("Upload Image");
        uploadImageButton.setBounds(150, 400, 200, 50);
        uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    displayUploadedImage(selectedFile.getAbsolutePath());
                }
            }
        });
        imagePanel.add(uploadImageButton);

        setVisible(true);
    }

    private void displayUploadedImage(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image scaledImage = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        uploadedImageLabel.setIcon(new ImageIcon(scaledImage));
        uploadedImageLabel.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == submit) {
        String fullName = fullNameField.getText();
        String rollText = rollField.getText();
        String dob = dobField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String bc = bcField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (fullName.isEmpty() || rollText.isEmpty() || dob.isEmpty() || address.isEmpty() ||
            phone.isEmpty() || email.isEmpty() || bc.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Please upload an image!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if("login1".equals(way)){
            try {
            // Initialize connection and statement
            Connect c = new Connect();

            int roll = Integer.parseInt(rollText);

            // Prepare the query to check if the roll number exists
            String checkQuery = "SELECT COUNT(*) FROM student WHERE rollno = ?";
            PreparedStatement statement = c.c.prepareStatement(checkQuery);
            statement.setInt(1, roll);

            // Execute the query
            ResultSet rs = statement.executeQuery();

            // Check if the roll number exists
            if (rs.next() && rs.getInt(1) > 0) {
                // Proceed with registration since the roll exists

                // Prepare the image data for insertion
                byte[] imageData = Files.readAllBytes(selectedFile.toPath());

                // Insert new user data into the database
                String query = "INSERT INTO register (FullName, Roll, dob, address, phone, email, bc, username, password, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, fullName);
                pst.setInt(2, roll);
                pst.setString(3, dob);
                pst.setString(4, address);
                pst.setString(5, phone);
                pst.setString(6, email);
                pst.setString(7, bc);
                pst.setString(8, username);
                pst.setString(9, password);
                pst.setBytes(10, imageData);

                // Execute the insert statement
                pst.executeUpdate();

                // Notify the user of a successful registration
                JOptionPane.showMessageDialog(this, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                new StudentDashboard(username);
                setVisible(false);

                // Close the resources
                pst.close();
            } else {
                // Roll number not found in the database
                JOptionPane.showMessageDialog(this, "Roll number not found in the database. Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Close resources
            if (rs != null) rs.close();
            if (statement != null) statement.close();
            if (c != null) c.s.close();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Roll must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading image file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        } else if("login2".equals(way)){
            
            try {
            // Initialize connection and statement
            Connect c = new Connect();

            int id = Integer.parseInt(rollText);

            // Prepare the query to check if the roll number exists
            String checkQuery = "SELECT COUNT(*) FROM teacher WHERE id = ?";
            PreparedStatement statement = c.c.prepareStatement(checkQuery);
            statement.setInt(1, id);

            // Execute the query
            ResultSet rs = statement.executeQuery();

            // Check if the roll number exists
            if (rs.next() && rs.getInt(1) > 0) {
                // Proceed with registration since the roll exists

                // Prepare the image data for insertion
                byte[] imageData = Files.readAllBytes(selectedFile.toPath());

                // Insert new user data into the database
                String query = "INSERT INTO register (FullName, Roll, dob, address, phone, email, bc, username, password, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, fullName);
                pst.setInt(2, id);
                pst.setString(3, dob);
                pst.setString(4, address);
                pst.setString(5, phone);
                pst.setString(6, email);
                pst.setString(7, bc);
                pst.setString(8, username);
                pst.setString(9, password);
                pst.setBytes(10, imageData);

                // Execute the insert statement
                pst.executeUpdate();

                // Notify the user of a successful registration
                JOptionPane.showMessageDialog(this, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                new TeacherDashboard(username);
                setVisible(false);

                // Close the resources
                pst.close();
            } else {
                // Roll number not found in the database
                JOptionPane.showMessageDialog(this, "ID number not found in the database. Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Close resources
            if (rs != null) rs.close();
            if (statement != null) statement.close();
            if (c != null) c.s.close();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading image file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        }
    }
    
    if(ae.getSource() == cancel){
        
        if("mainpage".equals(way)){
            new MainPage();
        }
        else if("login1".equals(way)){
            new Login("student");
        }
        
        else if("login2".equals(way)){
            new Login("teacher");
        }
        
        else if("login3".equals(way)){
            new Login("admin");
        }
        
        setVisible(false);
        
    }
}


    public static void main(String[] args) {
        new Register("mainpage");
    }
}
