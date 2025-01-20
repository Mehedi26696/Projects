package school.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentMaterials extends JFrame {

    public StudentMaterials() {
        // Frame properties
        setTitle("Student Study Materials");
        setSize(1100, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add components
        JPanel mainPanel = new JPanel(new BorderLayout());
        createMainSection(mainPanel);

        add(mainPanel);
        setVisible(true);
    }

    private void createMainSection(JPanel mainPanel) {
        // Top panel for subject selection
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel subjectLabel = new JLabel("Select Subject:");
        JComboBox<String> subjectDropdown = new JComboBox<>(new String[]{"Math", "Physics", "Chemistry", "Biology"});
        JButton fetchButton = new JButton("Fetch Materials");

        topPanel.add(subjectLabel);
        topPanel.add(subjectDropdown);
        topPanel.add(fetchButton);

        // Table to display materials
        String[] columns = {"Description", "Link","Type"};
       // DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
        return false; // Make the entire table non-editable
        }
};
        JTable materialsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(materialsTable);
        
        materialsTable.setRowHeight(30); // Sets the row height to 30 pixels
        materialsTable.getColumnModel().getColumn(0).setPreferredWidth(150); // Set preferred width of the first column
        materialsTable.getColumnModel().getColumn(1).setPreferredWidth(500); // Set preferred width of the second column
        materialsTable.getColumnModel().getColumn(2).setPreferredWidth(50); // Set preferred width of the third column


        // Open link when clicked
        materialsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = materialsTable.rowAtPoint(evt.getPoint());
                int col = materialsTable.columnAtPoint(evt.getPoint());
                if (col == 1) { // Link column
                    String url = (String) materialsTable.getValueAt(row, col);
                    try {
                        Desktop.getDesktop().browse(new java.net.URI(url));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Failed to open link!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Action listener for the fetch button
        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSubject = (String) subjectDropdown.getSelectedItem();
                tableModel.setRowCount(0); // Clear existing rows

                List<Material> materials = fetchMaterialsFromDatabase(selectedSubject);
                for (Material material : materials) {
                    tableModel.addRow(new Object[]{material.getType(), material.getDescription(), material.getLink()});
                }
            }
        });

        // Add panels to the main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private List<Material> fetchMaterialsFromDatabase(String subject) {
        List<Material> materials = new ArrayList<>();
        String query = "SELECT  description, drive_link,file_type FROM materials WHERE subject = '" + subject + "'";

        try {
            Connect connect = new Connect(); // Using your SQL connection class
            ResultSet resultSet = connect.s.executeQuery(query);

            while (resultSet.next()) {
               
                String description = resultSet.getString("description");
                String link = resultSet.getString("drive_link");
                
                String type = resultSet.getString("file_type");
                 
                materials.add(new Material(description, link,type));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return materials;
    }

    public static void main(String[] args) {
        new StudentMaterials();
    }

    // Material class to store details of each material
    static class Material {
        private String type;
        private String description;
        private String link;

        public Material(String type, String description, String link) {
            this.type = type;
            this.description = description;
            this.link = link;
        }

        public String getType() {
            return type;
        }

        public String getDescription() {
            return description;
        }

        public String getLink() {
            return link;
        }
    }
}

