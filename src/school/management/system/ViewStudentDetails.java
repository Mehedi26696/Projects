package school.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewStudentDetails extends JFrame {

    // Constructor to display the table
    public ViewStudentDetails() {
        // Frame properties
        setTitle("Student Details");
        setSize(1000, 700); // Increased window size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // Add spacing for better layout

        // Panel for search bar
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Left alignment with spacing

        JLabel searchLabel = new JLabel("Search by Roll: ");
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        JButton resetButton = new JButton("Reset");

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

        // Fetch and populate initial table data
        fetchData(tableModel, "");

        // Set the table model and add it to a scroll pane
        table.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add action listeners for the buttons
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rollInput = searchField.getText().trim();
                fetchData(tableModel, rollInput); // Fetch data based on roll
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchField.setText("");
                fetchData(tableModel, ""); // Fetch all data
            }
        });

        // Add components to the frame
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Make the frame visible
        setVisible(true);
    }

    // Method to fetch data from database
    private void fetchData(DefaultTableModel tableModel, String roll) {
        // Clear existing rows in the table model
        tableModel.setRowCount(0);

        // Fetch data from database using the Connect class
        try {
            Connect connect = new Connect();
            String query = "SELECT name, fname, rollno, dob, address, phone, email, bc FROM student";

            if (!roll.isEmpty()) {
                query += " WHERE rollno = '" + roll + "'"; // Add WHERE clause for roll
            }

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

                tableModel.addRow(new Object[]{name, fname, rollNo, dob, address, phone, email, bc});
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


