package school.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.net.URL;

public class StudentDashboard extends JFrame implements ActionListener {

    private JLabel studentNameLabel, studentIdLabel, studentEmailLabel, studentImageLabel, studentdobLabel, studentphoneLabel, studentaddressLabel, additionalImageLabel;
    private String username;
    CustomButton viewAttendance, updateInfo, viewGrades, accessMaterials, logout;
    
    int passroll;

    public StudentDashboard(String username) {
        this.username = username;

        setTitle("Student Dashboard");
        setSize(1600, 900); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10)); 
        mainPanel.setBackground(Color.GRAY);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180)); 
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setPreferredSize(new Dimension(0, 100)); 

        JLabel headerLabel = new JLabel("Student Dashboard");
        headerLabel.setFont(new Font("Raleway", Font.BOLD, 24));
        headerLabel.setForeground(Color.BLACK);

        headerPanel.add(headerLabel);

        // Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout(5, 5));
        leftPanel.setBackground(Color.WHITE);

        // Image Panel
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        imagePanel.setBackground(Color.WHITE);

        studentImageLabel = new JLabel("Image will appear here", SwingConstants.CENTER);
        studentImageLabel.setPreferredSize(new Dimension(200, 200));
        imagePanel.add(studentImageLabel);

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        studentNameLabel = new JLabel("Name: ");
        studentIdLabel = new JLabel("ID: ");
        studentEmailLabel = new JLabel("Email: ");
        studentdobLabel = new JLabel("Date of Birth: ");
        studentphoneLabel = new JLabel("Phone No: ");
        studentaddressLabel = new JLabel("Address: ");

        studentNameLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        studentIdLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        studentEmailLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        studentdobLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        studentphoneLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        studentaddressLabel.setFont(new Font("Raleway", Font.PLAIN, 18));

        infoPanel.add(createLabelPanel(studentNameLabel));
        infoPanel.add(createLabelPanel(studentIdLabel));
        infoPanel.add(createLabelPanel(studentEmailLabel));
        infoPanel.add(createLabelPanel(studentdobLabel));
        infoPanel.add(createLabelPanel(studentphoneLabel));
        infoPanel.add(createLabelPanel(studentaddressLabel));

        leftPanel.add(imagePanel, BorderLayout.NORTH); 
        leftPanel.add(infoPanel, BorderLayout.CENTER);

        // Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout(10, 10));
        rightPanel.setBackground(Color.WHITE);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10)); 
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 

        viewAttendance = new CustomButton("View My Attendance");
        viewGrades = new CustomButton("View My Grades");
        accessMaterials = new CustomButton("Access Study Materials");
        updateInfo = new CustomButton("Update Info");
        logout = new CustomButton("Logout");

        viewAttendance.setBackground(new Color(34, 139, 34));
        viewGrades.setBackground(new Color(34, 139, 34));
        accessMaterials.setBackground(new Color(34, 139, 34));
        updateInfo.setBackground(new Color(34, 139, 34));
        logout.setBackground(new Color(34, 139, 34));

        viewAttendance.setPreferredSize(new Dimension(250, 20));
        viewGrades.setPreferredSize(new Dimension(250, 40));
        accessMaterials.setPreferredSize(new Dimension(250, 40));
        updateInfo.setPreferredSize(new Dimension(250, 40));
        logout.setPreferredSize(new Dimension(250, 40));

        viewAttendance.addActionListener(this);
        viewGrades.addActionListener(this);
        accessMaterials.addActionListener(this);
        updateInfo.addActionListener(this);
        logout.addActionListener(this);

        buttonPanel.add(viewAttendance);
        buttonPanel.add(viewGrades);
        buttonPanel.add(accessMaterials);
        buttonPanel.add(updateInfo);
        buttonPanel.add(logout);

        rightPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        retrieveStudentData();

        setVisible(true);
    }

    private JPanel createLabelPanel(JLabel label) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);
        panel.add(label);
        return panel;
    }
     

   private void retrieveStudentData() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish connection using the Connect class
            Connect connect = new Connect(); 
            connection = connect.c; // Use the existing connection from Connect class

            String query = "SELECT FullName, Roll, email, dob, phone, address, image FROM register WHERE username = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);  // Pass the username to the query

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String fullName = resultSet.getString("FullName");
                int roll = resultSet.getInt("Roll");
                String email = resultSet.getString("email");
                String dob = resultSet.getString("dob");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                byte[] imageBytes = resultSet.getBytes("image");
                 
                // passroll update
                
                passroll = roll;
                // Set the text data to the labels
                studentNameLabel.setText("Name: " + fullName);
                studentIdLabel.setText("ID: S-" + roll);
                studentEmailLabel.setText("Email: " + email);
                studentdobLabel.setText("Date of Birth: " + dob);
                studentphoneLabel.setText("Phone No: " + phone);
                studentaddressLabel.setText("Address: " + address);

                // If image exists, set it in the JLabel
                if (imageBytes != null) {
                    InputStream is = new ByteArrayInputStream(imageBytes);
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon(is.readAllBytes()).getImage()
                            .getScaledInstance(200, 200, Image.SCALE_SMOOTH));  
                    studentImageLabel.setIcon(imageIcon);
                    studentImageLabel.setText(null);  // Remove default text if image is found
                } else {
                    studentImageLabel.setText("No image available.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error processing image: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Unexpected error: " + e.getMessage());
        } finally {
            // Close resources to prevent memory leaks
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == viewAttendance) {
            JOptionPane.showMessageDialog(this, "View My Attendance - Feature Coming Soon");
            new StudentAttendance();
        } else if (ae.getSource() == viewGrades) {
            new StudentGrades();
            JOptionPane.showMessageDialog(this, "View My Grades - Feature Coming Soon");
        } else if (ae.getSource() == accessMaterials) {
            new StudentMaterials();
            JOptionPane.showMessageDialog(this, "Access Study Materials - Feature Coming Soon");
        } else if(ae.getSource() == updateInfo) {
            new UpdateStudent(passroll);
        } else if (ae.getSource() == logout) {
            JOptionPane.showMessageDialog(this, "Logged Out");
            setVisible(false);
            //new Login("student");
            new MainPage();
        }
    }

    public static void main(String[] args) {
        new StudentDashboard("ynwa");  // Replace with actual username
    }
}

 


