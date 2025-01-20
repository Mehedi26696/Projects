package school.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.net.URL;

public class TeacherDashboard extends JFrame implements ActionListener {

    private JLabel TeacherNameLabel, TeacherIdLabel, TeacherEmailLabel, TeacherImageLabel, TeacherdobLabel, TeacherphoneLabel, TeacheraddressLabel;
    private String username;
    CustomButton SubmitAttendance, updateInfo, viewClasses, UploadMaterials, logout;
    
    int passroll;

    public TeacherDashboard(String username) {
        this.username = username;

        setTitle("Teacher Dashboard");
        setSize(1600, 900); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10)); 
        mainPanel.setBackground(Color.GRAY);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180)); 
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setPreferredSize(new Dimension(0, 100)); 

        JLabel headerLabel = new JLabel("Teacher Dashboard");
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

        TeacherImageLabel = new JLabel("Image will appear here", SwingConstants.CENTER);
        TeacherImageLabel.setPreferredSize(new Dimension(200, 200));
        imagePanel.add(TeacherImageLabel);

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        TeacherNameLabel = new JLabel("Name: ");
        TeacherIdLabel = new JLabel("ID: ");
        TeacherEmailLabel = new JLabel("Email: ");
        TeacherdobLabel = new JLabel("Date of Birth: ");
        TeacherphoneLabel = new JLabel("Phone No: ");
        TeacheraddressLabel = new JLabel("Address: ");

        TeacherNameLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        TeacherIdLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        TeacherEmailLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        TeacherdobLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        TeacherphoneLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        TeacheraddressLabel.setFont(new Font("Raleway", Font.PLAIN, 18));

        infoPanel.add(createLabelPanel(TeacherNameLabel));
        infoPanel.add(createLabelPanel(TeacherIdLabel));
        infoPanel.add(createLabelPanel(TeacherEmailLabel));
        infoPanel.add(createLabelPanel(TeacherdobLabel));
        infoPanel.add(createLabelPanel(TeacherphoneLabel));
        infoPanel.add(createLabelPanel(TeacheraddressLabel));

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

        SubmitAttendance = new CustomButton("Submit Attendance");
        viewClasses = new CustomButton("View My Classes");
        UploadMaterials = new CustomButton("Upload Study Materials");
        updateInfo = new CustomButton("Update Info");
        logout = new CustomButton("Logout");

        SubmitAttendance.setBackground(new Color(34, 139, 34));
        viewClasses.setBackground(new Color(34, 139, 34));
        UploadMaterials.setBackground(new Color(34, 139, 34));
        updateInfo.setBackground(new Color(34, 139, 34));
        logout.setBackground(new Color(34, 139, 34));

        SubmitAttendance.setPreferredSize(new Dimension(250, 20));
        viewClasses.setPreferredSize(new Dimension(250, 40));
        UploadMaterials.setPreferredSize(new Dimension(250, 40));
        updateInfo.setPreferredSize(new Dimension(250, 40));
        logout.setPreferredSize(new Dimension(250, 40));

        SubmitAttendance.addActionListener(this);
        viewClasses.addActionListener(this);
        UploadMaterials.addActionListener(this);
        updateInfo.addActionListener(this);
        logout.addActionListener(this);

        buttonPanel.add(SubmitAttendance);
        buttonPanel.add(viewClasses);
        buttonPanel.add(UploadMaterials);
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
                TeacherNameLabel.setText("Name: " + fullName);
                TeacherIdLabel.setText("ID: S-" + roll);
                TeacherEmailLabel.setText("Email: " + email);
                TeacherdobLabel.setText("Date of Birth: " + dob);
                TeacherphoneLabel.setText("Phone No: " + phone);
                TeacheraddressLabel.setText("Address: " + address);

                // If image exists, set it in the JLabel
                if (imageBytes != null) {
                    InputStream is = new ByteArrayInputStream(imageBytes);
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon(is.readAllBytes()).getImage()
                            .getScaledInstance(200, 200, Image.SCALE_SMOOTH));  
                    TeacherImageLabel.setIcon(imageIcon);
                    TeacherImageLabel.setText(null);  // Remove default text if image is found
                } else {
                    TeacherImageLabel.setText("No image available.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Teacher not found!");
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
        if (ae.getSource() == SubmitAttendance) {
             
            new TeacherAttendanceSubmission(username);
        } else if (ae.getSource() == viewClasses) {
            new TeacherViewClasses();
            
        } else if (ae.getSource() == UploadMaterials) {
            // new StudentMaterials();
            JOptionPane.showMessageDialog(this, "Access Study Materials - Feature Coming Soon");
        } else if(ae.getSource() == updateInfo) {
            new UpdateTeacher(passroll);
            // setVisible(false);
        } else if (ae.getSource() == logout) {
            JOptionPane.showMessageDialog(this, "Logged Out");
            setVisible(false);
            //new Login("student");
            new MainPage();
        }
    }

    public static void main(String[] args) {
        new TeacherDashboard("sir");  // Replace with actual username
    }
}
