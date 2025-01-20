package school.management.system;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class AdminManageTeachers extends JFrame {

    private JTable teachersTable;
    private DefaultTableModel tableModel;

    public AdminManageTeachers() {
        // Frame properties
        setTitle("Manage Teachers");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180)); // Steel Blue
        headerPanel.setPreferredSize(new Dimension(800, 100)); // Increased height for margin effect

        JLabel headerLabel = new JLabel("Teacher Management", JLabel.CENTER);
        headerLabel.setFont(new Font("Raleway", Font.BOLD, 30));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Top margin for title
        headerPanel.add(headerLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        // Customizing TitledBorder for "Teachers Table"
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Teacher Details");
        titledBorder.setTitleFont(new Font("Raleway", Font.PLAIN, 20)); // Increased font size for title
        titledBorder.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        tablePanel.setBorder(titledBorder);

        String[] columnNames = {"ID", "Name", "Subject", "Email", "Phone"};
        tableModel = new DefaultTableModel(columnNames, 0);
        teachersTable = new JTable(tableModel);

        // Increase row height
        teachersTable.setRowHeight(30);

        // Customize table header
        JTableHeader tableHeader = teachersTable.getTableHeader();
        tableHeader.setFont(new Font("Raleway", Font.BOLD, 18)); // Bold and larger font size
        tableHeader.setForeground(Color.BLACK);
        ((DefaultTableCellRenderer) tableHeader.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        // Add sample data (You can replace this with database integration later)
        addSampleData();

        JScrollPane scrollPane = new JScrollPane(teachersTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton addButton = new JButton("Add Teacher");
        JButton editButton = new JButton("Edit Teacher");
        JButton deleteButton = new JButton("Delete Teacher");
        JButton backButton = new JButton("Back");

        footerPanel.add(addButton);
        footerPanel.add(editButton);
        footerPanel.add(deleteButton);
        footerPanel.add(backButton);

        addButton.addActionListener(e -> addTeacher());
        editButton.addActionListener(e -> editTeacher());
        deleteButton.addActionListener(e -> deleteTeacher());
        backButton.addActionListener(e -> {
            // Navigate back to Admin Dashboard
            this.dispose();
            new AdminDashboard();
        });

        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"T101", "Alice Johnson", "Mathematics", "alice.johnson@example.com", "123-456-7890"});
        tableModel.addRow(new Object[]{"T102", "Bob Smith", "Physics", "bob.smith@example.com", "234-567-8901"});
        tableModel.addRow(new Object[]{"T103", "Carol Davis", "Chemistry", "carol.davis@example.com", "345-678-9012"});
        tableModel.addRow(new Object[]{"T104", "David Lee", "Biology", "david.lee@example.com", "456-789-0123"});
    }

    private void addTeacher() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField subjectField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();

        Object[] fields = {
            "ID:", idField,
            "Name:", nameField,
            "Subject:", subjectField,
            "Email:", emailField,
            "Phone:", phoneField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Teacher", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            tableModel.addRow(new Object[]{
                idField.getText(),
                nameField.getText(),
                subjectField.getText(),
                emailField.getText(),
                phoneField.getText()
            });
        }
    }

    private void editTeacher() {
        int selectedRow = teachersTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a teacher to edit!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField idField = new JTextField((String) tableModel.getValueAt(selectedRow, 0));
        JTextField nameField = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        JTextField subjectField = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
        JTextField emailField = new JTextField((String) tableModel.getValueAt(selectedRow, 3));
        JTextField phoneField = new JTextField((String) tableModel.getValueAt(selectedRow, 4));

        Object[] fields = {
            "ID:", idField,
            "Name:", nameField,
            "Subject:", subjectField,
            "Email:", emailField,
            "Phone:", phoneField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Edit Teacher", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            tableModel.setValueAt(idField.getText(), selectedRow, 0);
            tableModel.setValueAt(nameField.getText(), selectedRow, 1);
            tableModel.setValueAt(subjectField.getText(), selectedRow, 2);
            tableModel.setValueAt(emailField.getText(), selectedRow, 3);
            tableModel.setValueAt(phoneField.getText(), selectedRow, 4);
        }
    }

    private void deleteTeacher() {
        int selectedRow = teachersTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a teacher to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this teacher?", "Delete Teacher", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
        }
    }

    public static void main(String[] args) {
        new AdminManageTeachers();
    }
}

