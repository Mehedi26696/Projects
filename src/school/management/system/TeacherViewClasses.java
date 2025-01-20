package school.management.system;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class TeacherViewClasses extends JFrame {

    private JTable classesTable;
    private DefaultTableModel tableModel;

    public TeacherViewClasses() {
        // Frame properties
        setTitle("View My Classes");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180)); // Steel Blue
        headerPanel.setPreferredSize(new Dimension(800, 100)); // Increased height for margin effect

        JLabel headerLabel = new JLabel("My Classes", JLabel.CENTER);
        headerLabel.setFont(new Font("Raleway", Font.BOLD, 30));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Top margin for title
        headerPanel.add(headerLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        // Customizing TitledBorder for "Assigned Classes"
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Assigned Classes");
        titledBorder.setTitleFont(new Font("Raleway", Font.PLAIN, 20)); // Increased font size for title
        titledBorder.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        tablePanel.setBorder(titledBorder);

        String[] columnNames = {"Class Name", "Subject", "Class Time", "Classroom"};
        tableModel = new DefaultTableModel(columnNames, 0);
        classesTable = new JTable(tableModel);

        // Increase row height
        classesTable.setRowHeight(30); // Set row height to 30 pixels

        // Customize table header
        JTableHeader tableHeader = classesTable.getTableHeader();
        tableHeader.setFont(new Font("Raleway", Font.BOLD, 18)); // Bold and larger font size
        tableHeader.setForeground(Color.BLACK);
        ((DefaultTableCellRenderer) tableHeader.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        // Add sample data (You can replace this with database integration later)
        addSampleData();

        JScrollPane scrollPane = new JScrollPane(classesTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Back");
        footerPanel.add(backButton);

        backButton.addActionListener(e -> {
            // Navigate back to the Teacher Dashboard
            
            this.dispose();
          //  new TeacherDashboard();
        });

        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"Class 10A", "Mathematics", "09:00 AM - 10:00 AM", "Room 101"});
        tableModel.addRow(new Object[]{"Class 9B", "Physics", "11:00 AM - 12:00 PM", "Room 203"});
        tableModel.addRow(new Object[]{"Class 11C", "Chemistry", "02:00 PM - 03:30 PM", "Room 305"});
    }

    public static void main(String[] args) {
        new TeacherViewClasses();
    }
}

