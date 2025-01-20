package school.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// CustomButton Class
class CustomButton extends JButton {

    // Constructor for a custom button
    public CustomButton(String text, int width, int height, Font font) {
        super(text); // Set the button text
        setPreferredSize(new Dimension(width, height)); // Set custom size
        setFont(font); // Set custom font
        setBackground(new Color(70, 130, 180)); // Steel Blue background
        setForeground(Color.WHITE); // White text
        setFocusPainted(false); // Remove focus border
        setBorder(BorderFactory.createEmptyBorder()); // No border
    }

    // Overloaded constructor with default size and font
    public CustomButton(String text) {
        this(text, 150, 40, new Font("Raleway", Font.PLAIN, 20));
    }
}

// AdminDashboard Class
public class AdminDashboard extends JFrame implements ActionListener {

    CustomButton manageStudents, manageTeachers, manageClasses, generateReports, logout;
    JTable dataTable;

    // Constructor
    AdminDashboard() {
        // Frame properties
        setTitle("Admin Dashboard");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180)); // Steel Blue
        headerPanel.setPreferredSize(new Dimension(800, 100));
        JLabel headerLabel = new JLabel("Admin Dashboard", JLabel.CENTER);
        headerLabel.setFont(new Font("Raleway", Font.BOLD, 40));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Top margin of 20px
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Sidebar Panel
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(240, 248, 255)); // Light Blue
        sidebarPanel.setPreferredSize(new Dimension(250, 80));
        sidebarPanel.setLayout(new BorderLayout());

        // Wrapper Panel for Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255)); // Light Blue
        buttonPanel.setLayout(new GridLayout(8, 1, 0, 10)); // 5 rows, 1 column, 10px vertical gap

        // Buttons
        manageStudents = new CustomButton("Manage Students", 180, 50, new Font("Raleway", Font.BOLD, 16));
        manageTeachers = new CustomButton("Manage Teachers", 180, 50, new Font("Raleway", Font.BOLD, 16));
        manageClasses = new CustomButton("Manage Classes", 180, 50, new Font("Raleway", Font.BOLD, 16));
        generateReports = new CustomButton("Generate Reports", 180, 50, new Font("Raleway", Font.BOLD, 16));
        logout = new CustomButton("Logout", 180, 50, new Font("Raleway", Font.BOLD, 16));

        manageStudents.addActionListener(this);
        manageTeachers.addActionListener(this);
        manageClasses.addActionListener(this);
        generateReports.addActionListener(this);
        logout.addActionListener(this);

        buttonPanel.add(manageStudents);
        buttonPanel.add(manageTeachers);
        buttonPanel.add(manageClasses);
        buttonPanel.add(generateReports);
        buttonPanel.add(logout);

        // Add gap at the top of the sidebar
        sidebarPanel.add(Box.createVerticalStrut(10), BorderLayout.NORTH); // 20px gap
        sidebarPanel.add(buttonPanel, BorderLayout.CENTER);

        add(sidebarPanel, BorderLayout.WEST);
        
        // main content
        
JPanel mainContentPanel = new JPanel(new BorderLayout());
mainContentPanel.setBackground(Color.WHITE);

JLabel imageLabel = new JLabel();
imageLabel.setHorizontalAlignment(JLabel.CENTER);

try {
    // Attempt to load the image from the classpath
    ImageIcon imageIcon = new ImageIcon(getClass().getResource("/assets/adminbg.jpeg"));
    Image scaledImage = imageIcon.getImage().getScaledInstance(900, 450, Image.SCALE_SMOOTH);
    imageLabel.setIcon(new ImageIcon(scaledImage));
} catch (Exception e) {
    System.out.println("Image loading failed: " + e.getMessage());
}

mainContentPanel.add(imageLabel, BorderLayout.CENTER);
add(mainContentPanel, BorderLayout.CENTER);


       
        setVisible(true);
    }

    // Action Performed for Button Clicks
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == manageStudents) {
            new ManagePage("Students");
        } else if (ae.getSource() == manageTeachers) {
            new ManagePage("Teachers");
        } else if (ae.getSource() == manageClasses) {
            new ManageClasses();
        } else if (ae.getSource() == generateReports) {
            new GenerateReports();
        } else if (ae.getSource() == logout) {
            new MainPage();
            setVisible(false);
        }
    }

    // ManagePage Class for Students or Teachers
    static class ManagePage extends JFrame implements ActionListener {
        String type;
        CustomButton updateInfo, totalInfo;

        ManagePage(String type) {
            this.type = type;
            setTitle("Manage " + type);
            setSize(400, 300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(3, 1, 10, 10));

            JLabel label = new JLabel("Manage " + type, JLabel.CENTER);
            label.setFont(new Font("Raleway", Font.BOLD, 20));
            add(label);

            updateInfo = new CustomButton("Update Info");
            totalInfo = new CustomButton("Total Information");

            updateInfo.addActionListener(this);
            totalInfo.addActionListener(this);

            add(updateInfo);
            add(totalInfo);

            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == updateInfo) {
                String input = JOptionPane.showInputDialog(this, "Enter ID or Roll:", "Update Info", JOptionPane.PLAIN_MESSAGE);
                if (input != null && !input.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Proceeding to update info for: " + input, "Update Info", JOptionPane.INFORMATION_MESSAGE);
                    // Add your logic here to update info based on the input
                    int n = Integer.parseInt(input);
                    if("Students".equals(type)){
                        new UpdateStudent(n);
                    }
                    else if("Teachers".equals(type)){
                        new UpdateTeacher(n);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "ID or Roll cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == totalInfo) {
                //JOptionPane.showMessageDialog(this, "Displaying total information for " + type, "Total Information", JOptionPane.INFORMATION_MESSAGE);
                // Add your logic here to display total information
                if("Students".equals(type)){
                        new ViewStudentDetails();
                }
                
                else if("Teachers".equals(type)){
                        new ViewTeacherDetails(); 
                }
                
                
            }
        }
    }

    // ManageClasses Class
    static class ManageClasses extends JFrame {

        ManageClasses() {
            setTitle("Manage Classes");
            setSize(600, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
        }
    }

    static class GenerateReports extends JFrame {

        GenerateReports() {
            setTitle("Generate Reports");
            setSize(600, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
        }
    }

    

    // Main Method
    public static void main(String[] args) {
        new AdminDashboard();
    }
}
