package school.management.system;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminManageStudents extends JFrame {

    private JTable studentsTable;
    private DefaultTableModel tableModel;

    public AdminManageStudents() {
        // Frame properties
        setTitle("Manage Students");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180)); // Steel Blue
        headerPanel.setPreferredSize(new Dimension(800, 100)); // Increased height for margin effect

        JLabel headerLabel = new JLabel("Student Management", JLabel.CENTER);
        headerLabel.setFont(new Font("Raleway", Font.BOLD, 30));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Top margin for title
        headerPanel.add(headerLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        // Customizing TitledBorder for "Students Table"
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Student Details");
        titledBorder.setTitleFont(new Font("Raleway", Font.PLAIN, 20)); // Increased font size for title
        titledBorder.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        tablePanel.setBorder(titledBorder);

        String[] columnNames = {"Roll Number", "Name", "Class", "Section", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentsTable = new JTable(tableModel);

        // Increase row height
        studentsTable.setRowHeight(30);

        // Customize table header
        JTableHeader tableHeader = studentsTable.getTableHeader();
        tableHeader.setFont(new Font("Raleway", Font.BOLD, 18)); // Bold and larger font size
        tableHeader.setForeground(Color.BLACK);
        ((DefaultTableCellRenderer) tableHeader.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        // Add sample data (You can replace this with database integration later)
        addSampleData();

        JScrollPane scrollPane = new JScrollPane(studentsTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton addButton = new JButton("Add Student");
        JButton editButton = new JButton("Edit Student");
        JButton deleteButton = new JButton("Delete Student");
        JButton backButton = new JButton("Back");

        footerPanel.add(addButton);
        footerPanel.add(editButton);
        footerPanel.add(deleteButton);
        footerPanel.add(backButton);

        addButton.addActionListener(e -> addStudent());
        editButton.addActionListener(e -> editStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        backButton.addActionListener(e -> {
            // Navigate back to Admin Dashboard
            this.dispose();
            new AdminDashboard();
        });

        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"101", "John Doe", "10", "A", "john.doe@example.com"});
        tableModel.addRow(new Object[]{"102", "Jane Smith", "9", "B", "jane.smith@example.com"});
        tableModel.addRow(new Object[]{"103", "Michael Johnson", "11", "C", "michael.johnson@example.com"});
        tableModel.addRow(new Object[]{"104", "Emily Davis", "8", "D", "emily.davis@example.com"});
    }

    private void addStudent() {
        JTextField rollField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField classField = new JTextField();
        JTextField sectionField = new JTextField();
        JTextField emailField = new JTextField();

        Object[] fields = {
            "Roll Number:", rollField,
            "Name:", nameField,
            "Class:", classField,
            "Section:", sectionField,
            "Email:", emailField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Student", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            tableModel.addRow(new Object[]{
                rollField.getText(),
                nameField.getText(),
                classField.getText(),
                sectionField.getText(),
                emailField.getText()
            });
        }
    }

    private void editStudent() {
        int selectedRow = studentsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to edit!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField rollField = new JTextField((String) tableModel.getValueAt(selectedRow, 0));
        JTextField nameField = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        JTextField classField = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
        JTextField sectionField = new JTextField((String) tableModel.getValueAt(selectedRow, 3));
        JTextField emailField = new JTextField((String) tableModel.getValueAt(selectedRow, 4));

        Object[] fields = {
            "Roll Number:", rollField,
            "Name:", nameField,
            "Class:", classField,
            "Section:", sectionField,
            "Email:", emailField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Edit Student", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            tableModel.setValueAt(rollField.getText(), selectedRow, 0);
            tableModel.setValueAt(nameField.getText(), selectedRow, 1);
            tableModel.setValueAt(classField.getText(), selectedRow, 2);
            tableModel.setValueAt(sectionField.getText(), selectedRow, 3);
            tableModel.setValueAt(emailField.getText(), selectedRow, 4);
        }
    }

    private void deleteStudent() {
        int selectedRow = studentsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", "Delete Student", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
        }
    }

    public static void main(String[] args) {
        new AdminManageStudents();
    }
}
