package school.management.system;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class TeacherAttendanceSubmission extends JFrame {

    private JTable attendanceTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> classDropdown;
    private JComboBox<String> subjectDropdown;
    private Connect dbConnection;

    public TeacherAttendanceSubmission(String username) {
        dbConnection = new Connect();

        // Frame properties
        setTitle("Submit Attendance");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180)); // Steel Blue
        headerPanel.setPreferredSize(new Dimension(800, 100));

        JLabel headerLabel = new JLabel("Submit Attendance", JLabel.CENTER);
        headerLabel.setFont(new Font("Raleway", Font.BOLD, 30));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Selection Panel
JPanel selectionPanel = new JPanel();
selectionPanel.setLayout(new FlowLayout());

JLabel classLabel = new JLabel("Class: ");
classLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
selectionPanel.add(classLabel);

classDropdown = new JComboBox<>(fetchClasses());
classDropdown.setFont(new Font("Raleway", Font.PLAIN, 16));
selectionPanel.add(classDropdown);

JLabel subjectLabel = new JLabel("Subject: ");
subjectLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
selectionPanel.add(subjectLabel);

subjectDropdown = new JComboBox<>(new String[]{"Math", "Science", "History", "English"}); // Example subjects
subjectDropdown.setFont(new Font("Raleway", Font.PLAIN, 16));
selectionPanel.add(subjectDropdown);

JButton fetchButton = new JButton("Fetch Students");
fetchButton.setFont(new Font("Raleway", Font.PLAIN, 16));
fetchButton.addActionListener(e -> fetchStudentData());
selectionPanel.add(fetchButton);

// Add Selection Panel to JFrame
add(selectionPanel, BorderLayout.NORTH); // Place selectionPanel at the top

// Table Panel
JPanel tablePanel = new JPanel();
tablePanel.setLayout(new BorderLayout());

TitledBorder titledBorder = BorderFactory.createTitledBorder("Mark Attendance");
titledBorder.setTitleFont(new Font("Raleway", Font.PLAIN, 20));
tablePanel.setBorder(titledBorder);

// Table Columns and Model
String[] columnNames = {"Roll Number", "Student Name", "Attendance (P/A)"};
tableModel = new DefaultTableModel(columnNames, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 2; // Only the "Attendance (P/A)" column is editable
    }
};

// Create JTable and Customize
attendanceTable = new JTable(tableModel);
attendanceTable.setRowHeight(30);

JTableHeader tableHeader = attendanceTable.getTableHeader();
tableHeader.setFont(new Font("Raleway", Font.BOLD, 18));
tableHeader.setForeground(Color.BLACK);

// Center-align table header text
((DefaultTableCellRenderer) tableHeader.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

// Add Table to ScrollPane
JScrollPane scrollPane = new JScrollPane(attendanceTable);
tablePanel.add(scrollPane, BorderLayout.CENTER);

// Add Table Panel to JFrame
add(tablePanel, BorderLayout.CENTER); // Keep tablePanel in the center


        
        
        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton submitButton = new JButton("Submit Attendance");
        submitButton.setFont(new Font("Raleway", Font.PLAIN, 16));
        submitButton.addActionListener(e -> submitAttendance());

        footerPanel.add(submitButton);
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String[] fetchClasses() {
        try {
            Statement stmt = dbConnection.c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT class FROM students");
            java.util.List<String> classes = new java.util.ArrayList<>();
            while (rs.next()) {
                classes.add(rs.getString("class"));
                
                System.out.println(rs.getString("class"));
            }
            return classes.toArray(new String[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[]{};
        }
    }

    private void fetchStudentData() {
        String selectedClass = (String) classDropdown.getSelectedItem();
        tableModel.setRowCount(0); // Clear existing rows

        try {
            PreparedStatement pstmt = dbConnection.c.prepareStatement("SELECT roll_number, name FROM students WHERE class = ?");
            pstmt.setString(1, selectedClass);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                
                int rollNumber = rs.getInt("roll_number");
                String name = rs.getString("name");
              //  tableModel.addRow(new Object[]{rs.getInt("roll_number"), rs.getString("name"), ""});
                
//                System.out.println(rs.getInt("roll_number"));
//                System.out.println(rs.getString("name"));

             tableModel.addRow(new Object[]{rollNumber, name, ""});

              System.out.println("Added Row: Roll Number = " + rollNumber + ", Name = " + name);
              
              
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching student data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void submitAttendance() {
        int rowCount = tableModel.getRowCount();
        String selectedClass = (String) classDropdown.getSelectedItem();
        String selectedSubject = (String) subjectDropdown.getSelectedItem();
        String currentDate = LocalDate.now().toString();

        try {
            PreparedStatement pstmt = dbConnection.c.prepareStatement(
                    "INSERT INTO attendance_records (roll_number, class, subject, date, attendance) VALUES (?, ?, ?, ?, ?)");

            for (int i = 0; i < rowCount; i++) {
                int rollNumber = (int) tableModel.getValueAt(i, 0);
                String attendance = (String) tableModel.getValueAt(i, 2);
                
                System.out.println(attendance);
                if (attendance == null || attendance.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please mark attendance for all students!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                pstmt.setInt(1, rollNumber);
                pstmt.setString(2, selectedClass);
                pstmt.setString(3, selectedSubject);
                pstmt.setString(4, currentDate);
                pstmt.setString(5, attendance);
                pstmt.addBatch(); // Add to batch for efficiency
            }

            pstmt.executeBatch(); // Execute batch update
            JOptionPane.showMessageDialog(this, "Attendance submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting attendance.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new TeacherAttendanceSubmission("sir");
    }
}

