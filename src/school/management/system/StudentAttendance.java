package school.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;

public class StudentAttendance extends JFrame {

    // HashMaps to store student data
    private static HashMap<String, String> studentNames = new HashMap<>();
    private static HashMap<String, Double> totalAttendanceData = new HashMap<>();
    private static HashMap<String, HashMap<String, Double>> subjectAttendanceData = new HashMap<>();

    public StudentAttendance() {
        // Sample student data
        studentNames.put("1001", "Alice Johnson");
        studentNames.put("1002", "Bob Smith");
        studentNames.put("1003", "Charlie Brown");
        studentNames.put("1004", "Diana Prince");

        totalAttendanceData.put("1001", 85.5);
        totalAttendanceData.put("1002", 92.0);
        totalAttendanceData.put("1003", 78.0);
        totalAttendanceData.put("1004", 88.5);

        HashMap<String, Double> subjects1001 = new HashMap<>();
        subjects1001.put("Math", 90.0);
        subjects1001.put("Physics", 80.0);
        subjects1001.put("Chemistry", 86.5);
        subjectAttendanceData.put("1001", subjects1001);

        // Frame properties
        setTitle("Student Attendance");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        // Add sections
        createHeaderSection(layeredPane);
        createMainSection(layeredPane);

        add(layeredPane);
        setVisible(true);
    }

    private void createHeaderSection(JLayeredPane layeredPane) {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 123, 255));
        headerPanel.setBounds(0, 0, 1200, 100);
        headerPanel.setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Student Attendance Dashboard", JLabel.CENTER);
        headerLabel.setFont(new Font("Raleway", Font.BOLD, 30));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        layeredPane.add(headerPanel, JLayeredPane.DEFAULT_LAYER);
    }

    private void createMainSection(JLayeredPane layeredPane) {
        // Left Panel for Roll Number Input and Attendance Data
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(20, 120, 500, 500);
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel rollLabel = new JLabel("Enter Roll Number:");
        rollLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        rollLabel.setBounds(20, 20, 200, 30);

        JTextField rollText = new JTextField(20);
        rollText.setFont(new Font("Raleway", Font.PLAIN, 16));
        rollText.setBounds(20, 60, 300, 30);

        JButton checkButton = new JButton("Check Attendance");
        checkButton.setFont(new Font("Raleway", Font.BOLD, 16));
        checkButton.setBackground(new Color(0, 123, 255));
        checkButton.setForeground(Color.WHITE);
        checkButton.setBounds(20, 110, 200, 40);

        JLabel totalAttendanceLabel = new JLabel("Total Attendance: ");
        totalAttendanceLabel.setFont(new Font("Raleway", Font.PLAIN, 16));
        totalAttendanceLabel.setBounds(20, 170, 300, 30);

        JTextArea subjectAttendanceArea = new JTextArea();
        subjectAttendanceArea.setFont(new Font("Raleway", Font.PLAIN, 16));
        subjectAttendanceArea.setEditable(false);
        subjectAttendanceArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        subjectAttendanceArea.setBackground(new Color(245, 245, 245));
        subjectAttendanceArea.setBounds(20, 210, 460, 200);

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rollNumber = rollText.getText();
                if (studentNames.containsKey(rollNumber)) {
                    totalAttendanceLabel.setText("Total Attendance: " + totalAttendanceData.get(rollNumber) + "%");

                    // Display subject-wise attendance
                    StringBuilder subjects = new StringBuilder("Subject-wise Attendance:\n");
                    HashMap<String, Double> subjectsMap = subjectAttendanceData.get(rollNumber);
                    if (subjectsMap != null) {
                        for (String subject : subjectsMap.keySet()) {
                            subjects.append(subject).append(": ").append(subjectsMap.get(subject)).append("%\n");
                        }
                    }
                    subjectAttendanceArea.setText(subjects.toString());
                } else {
                    totalAttendanceLabel.setText("Roll Number not found.");
                    subjectAttendanceArea.setText("");
                }
            }
        });

        leftPanel.add(rollLabel);
        leftPanel.add(rollText);
        leftPanel.add(checkButton);
        leftPanel.add(totalAttendanceLabel);
        leftPanel.add(subjectAttendanceArea);

        // Right Panel for Student Info and Image
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(550, 120, 500, 500);
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(125, 50, 250, 250);

        ImageIcon icon;
        URL imageUrl = getClass().getResource("/assets/mehedi.jpg"); // Replace with correct path
        if (imageUrl != null) {
            icon = new ImageIcon(new ImageIcon(imageUrl).getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));
        } else {
            icon = new ImageIcon(); // Default if image not found
        }
        imageLabel.setIcon(icon);

        JLabel studentNameLabel = new JLabel("Student Name");
        studentNameLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        studentNameLabel.setBounds(150, 320, 200, 30);
        studentNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        rightPanel.add(imageLabel);
        rightPanel.add(studentNameLabel);

        // Add panels to the layered pane
        layeredPane.add(leftPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(rightPanel, JLayeredPane.DEFAULT_LAYER);
    }

    public static void main(String[] args) {
        new StudentAttendance();
    }
}

