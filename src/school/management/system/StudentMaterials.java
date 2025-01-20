package school.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class StudentMaterials extends JFrame {

    // HashMap to store materials for each subject
    private static HashMap<String, String> subjectMaterials = new HashMap<>();

    public StudentMaterials() {
        // Sample materials data
        subjectMaterials.put("Math", "Algebra Notes, Geometry Diagrams, Calculus Problems");
        subjectMaterials.put("Physics", "Mechanics Tutorials, Optics PDFs, Thermodynamics Problems");
        subjectMaterials.put("Chemistry", "Periodic Table, Organic Reactions Guide, Lab Safety Rules");
        subjectMaterials.put("Biology", "Cell Diagrams, Genetics Practice, Human Anatomy Charts");

        // Frame properties
        setTitle("Student Study Materials");
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
        headerPanel.setBackground(new Color(0, 153, 76));
        headerPanel.setBounds(0, 0, 1200, 100);
        headerPanel.setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Student Materials Dashboard", JLabel.CENTER);
        headerLabel.setFont(new Font("Raleway", Font.BOLD, 30));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        layeredPane.add(headerPanel, JLayeredPane.DEFAULT_LAYER);
    }

    private void createMainSection(JLayeredPane layeredPane) {
        // Left Panel for Subject Selection
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(20, 120, 500, 500);
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel subjectLabel = new JLabel("Select Subject:");
        subjectLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        subjectLabel.setBounds(20, 20, 200, 30);

        JComboBox<String> subjectDropdown = new JComboBox<>(subjectMaterials.keySet().toArray(new String[0]));
        subjectDropdown.setFont(new Font("Raleway", Font.PLAIN, 16));
        subjectDropdown.setBounds(20, 60, 300, 30);

        JButton fetchButton = new JButton("Fetch Materials");
        fetchButton.setFont(new Font("Raleway", Font.BOLD, 16));
        fetchButton.setBackground(new Color(0, 123, 255));
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(20, 110, 200, 40);

        JTextArea materialsArea = new JTextArea();
        materialsArea.setFont(new Font("Raleway", Font.PLAIN, 16));
        materialsArea.setEditable(false);
        materialsArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        materialsArea.setBackground(new Color(245, 245, 245));
        materialsArea.setBounds(20, 170, 460, 300);

        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSubject = (String) subjectDropdown.getSelectedItem();
                if (subjectMaterials.containsKey(selectedSubject)) {
                    materialsArea.setText(subjectMaterials.get(selectedSubject));
                } else {
                    materialsArea.setText("No materials found for the selected subject.");
                }
            }
        });

        leftPanel.add(subjectLabel);
        leftPanel.add(subjectDropdown);
        leftPanel.add(fetchButton);
        leftPanel.add(materialsArea);

        // Right Panel for Info and Placeholder Image
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(550, 120, 500, 500);
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

//        JLabel imageLabel = new JLabel();
//        imageLabel.setBounds(125, 50, 250, 250);
//
//        ImageIcon icon = new ImageIcon(new ImageIcon(getClass().getResource("/assets/books.jpg"))
//                .getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));
//        imageLabel.setIcon(icon);

        JLabel infoLabel = new JLabel("Access Materials Anytime");
        infoLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        infoLabel.setBounds(100, 320, 300, 30);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

//        rightPanel.add(imageLabel);
        rightPanel.add(infoLabel);

        // Add panels to the layered pane
        layeredPane.add(leftPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(rightPanel, JLayeredPane.DEFAULT_LAYER);
    }

    public static void main(String[] args) {
        new StudentMaterials();
    }
}

