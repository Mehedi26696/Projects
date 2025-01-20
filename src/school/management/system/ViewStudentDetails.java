package school.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewStudentDetails extends JFrame {

    public ViewStudentDetails() {
        // Frame properties
        setTitle("Student Details");
        setSize(1000, 700); // Increased window size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // Add spacing for better layout

        // Panel for filters
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Left alignment with spacing

        JLabel classLabel = new JLabel("Filter by Class: ");
        JComboBox<String> classDropdown = new JComboBox<>(fetchClasses()); // Fetch class options from the database
        JLabel searchLabel = new JLabel("Search by Roll: ");
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        JButton resetButton = new JButton("Reset");

        searchPanel.add(classLabel);
        searchPanel.add(classDropdown);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);

        // Table and Model
        JTable table = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel();

        // Add columns to table model
        tableModel.addColumn("Name");
        tableModel.addColumn("Father's Name");
        tableModel.addColumn("Roll");
        tableModel.addColumn("Date of Birth");
        tableModel.addColumn("Address");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Email");
        tableModel.addColumn("Birth Cert. No");
        tableModel.addColumn("Class");

        // Fetch and populate initial table data
        fetchData(tableModel, "", "");

        // Set the table model and add it to a scroll pane
        table.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add action listeners for the buttons
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedClass = (String) classDropdown.getSelectedItem();
                String rollInput = searchField.getText().trim();
                fetchData(tableModel, selectedClass, rollInput); // Fetch data based on class and roll
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classDropdown.setSelectedIndex(0); // Reset to "All Classes"
                searchField.setText("");
                fetchData(tableModel, "", ""); // Fetch all data
            }
        });

        // Add components to the frame
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Make the frame visible
        setVisible(true);
    }

    // Method to fetch class options from the database
    private String[] fetchClasses() {
        try {
            Connect connect = new Connect();
            ResultSet resultSet = connect.s.executeQuery("SELECT DISTINCT class FROM student");

            // Use a list to dynamically collect class names
            java.util.List<String> classes = new java.util.ArrayList<>();
            classes.add("All Classes"); // Default option to fetch all records

            while (resultSet.next()) {
                classes.add(resultSet.getString("class"));
            }

            resultSet.close();
            return classes.toArray(new String[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return new String[]{"All Classes"};
        }
    }

    // Method to fetch data from the database
    private void fetchData(DefaultTableModel tableModel, String selectedClass, String roll) {
        // Clear existing rows in the table model
        tableModel.setRowCount(0);

        // Build the query dynamically based on filters
        String query = "SELECT name, fname, rollno, dob, address, phone, email, bc, class FROM student";
        String condition = "";

        if (!selectedClass.equals("All Classes")) {
            condition += " WHERE class = '" + selectedClass + "'";
        }
        if (!roll.isEmpty()) {
            if (!condition.isEmpty()) {
                condition += " AND";
            } else {
                condition = " WHERE";
            }
            condition += " rollno = '" + roll + "'";
        }

        query += condition;

        try {
            Connect connect = new Connect();
            ResultSet resultSet = connect.s.executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String fname = resultSet.getString("fname");
                int rollNo = resultSet.getInt("rollno");
                String dob = resultSet.getString("dob");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String bc = resultSet.getString("bc");
                String cl = resultSet.getString("class");

                tableModel.addRow(new Object[]{name, fname, rollNo, dob, address, phone, email, bc, cl});
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewStudentDetails::new);
    }
}
