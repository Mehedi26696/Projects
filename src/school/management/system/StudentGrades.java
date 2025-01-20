package school.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;

public class StudentGrades extends JFrame {

    // HashMaps to store student data
    private static HashMap<String, String> studentNames = new HashMap<>();
    private static HashMap<String, Double> overallGradeData = new HashMap<>();
    private static HashMap<String, HashMap<String, String>> subjectGradeData = new HashMap<>();

    public StudentGrades() {
        // Sample student data
        studentNames.put("1001", "Alice Johnson");
        studentNames.put("1002", "Bob Smith");
        studentNames.put("1003", "Charlie Brown");
        studentNames.put("1004", "Diana Prince");

        overallGradeData.put("1001", 3.8);
        overallGradeData.put("1002", 3.9);
        overallGradeData.put("1003", 3.2);
        overallGradeData.put("1004", 3.7);

        HashMap<String, String> grades1001 = new HashMap<>();
        grades1001.put("Math", "A");
        grades1001.put("Physics", "B+");
        grades1001.put("Chemistry", "A-");
        subjectGradeData.put("1001", grades1001);

        // Frame properties
        setTitle("Student Grades Dashboard");
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
        headerPanel.setBackground(new Color(34, 139, 34));
        headerPanel.setBounds(0, 0, 1200, 100);
        headerPanel.setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Student Grades Dashboard", JLabel.CENTER);
        headerLabel.setFont(new Font("Raleway", Font.BOLD, 30));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        layeredPane.add(headerPanel, JLayeredPane.DEFAULT_LAYER);
    }

    private void createMainSection(JLayeredPane layeredPane) {
        // Left Panel for Roll Number Input and Grades Data
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

        JButton checkButton = new JButton("Check Grades");
        checkButton.setFont(new Font("Raleway", Font.BOLD, 16));
        checkButton.setBackground(new Color(34, 139, 34));
        checkButton.setForeground(Color.WHITE);
        checkButton.setBounds(20, 110, 200, 40);

        JLabel overallGradeLabel = new JLabel("Overall Grade: ");
        overallGradeLabel.setFont(new Font("Raleway", Font.PLAIN, 16));
        overallGradeLabel.setBounds(20, 170, 300, 30);

        JTextArea subjectGradesArea = new JTextArea();
        subjectGradesArea.setFont(new Font("Raleway", Font.PLAIN, 16));
        subjectGradesArea.setEditable(false);
        subjectGradesArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        subjectGradesArea.setBackground(new Color(245, 245, 245));
        subjectGradesArea.setBounds(20, 210, 460, 200);

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rollNumber = rollText.getText();
                if (studentNames.containsKey(rollNumber)) {
                    overallGradeLabel.setText("Overall Grade: " + overallGradeData.get(rollNumber));

                    // Display subject-wise grades
                    StringBuilder subjects = new StringBuilder("Subject-wise Grades:\n");
                    HashMap<String, String> subjectsMap = subjectGradeData.get(rollNumber);
                    if (subjectsMap != null) {
                        for (String subject : subjectsMap.keySet()) {
                            subjects.append(subject).append(": ").append(subjectsMap.get(subject)).append("\n");
                        }
                    }
                    subjectGradesArea.setText(subjects.toString());
                } else {
                    overallGradeLabel.setText("Roll Number not found.");
                    subjectGradesArea.setText("");
                }
            }
        });

        leftPanel.add(rollLabel);
        leftPanel.add(rollText);
        leftPanel.add(checkButton);
        leftPanel.add(overallGradeLabel);
        leftPanel.add(subjectGradesArea);

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
        new StudentGrades();
    }
}

