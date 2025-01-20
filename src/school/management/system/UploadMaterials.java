package school.management.system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UploadMaterials {
    private Connect connect;

    public UploadMaterials() {
        connect = new Connect(); // Use the provided Connect class
        createUploadUI();
    }

    private void createUploadUI() {
        // Create the frame
        JFrame frame = new JFrame("Upload Materials");
        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Label for subject
        JLabel subjectLabel = new JLabel("Subject:");
        subjectLabel.setBounds(30, 30, 100, 30);
        frame.add(subjectLabel);

        // Dropdown for subject selection
        String[] subjects = {"Mathematics", "Physics", "Chemistry", "Biology", "Computer Science"};
        JComboBox<String> subjectComboBox = new JComboBox<>(subjects);
        subjectComboBox.setBounds(120, 30, 150, 30);
        frame.add(subjectComboBox);

        // Label for file type
        JLabel fileTypeLabel = new JLabel("File Type:");
        fileTypeLabel.setBounds(30, 80, 100, 30);
        frame.add(fileTypeLabel);

        // Dropdown for file type selection
        String[] fileTypes = {"Image", "PDF"};
        JComboBox<String> fileTypeComboBox = new JComboBox<>(fileTypes);
        fileTypeComboBox.setBounds(120, 80, 150, 30);
        frame.add(fileTypeComboBox);

        // Label for Google Drive link
        JLabel linkLabel = new JLabel("Drive Link:");
        linkLabel.setBounds(30, 130, 100, 30);
        frame.add(linkLabel);

        // Text field for Drive link
        JTextField linkField = new JTextField();
        linkField.setBounds(120, 130, 300, 30);
        frame.add(linkField);

        // Label for file description
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(30, 180, 100, 30);
        frame.add(descriptionLabel);

        // Text field for file description
        JTextField descriptionField = new JTextField();
        descriptionField.setBounds(120, 180, 300, 30);
        frame.add(descriptionField);

        // Button to upload material
        JButton uploadButton = new JButton("Upload");
        uploadButton.setBounds(180, 230, 100, 30);
        frame.add(uploadButton);

        // Text area to display uploaded materials
        JTextArea uploadedFilesArea = new JTextArea();
        uploadedFilesArea.setBounds(30, 280, 430, 100);
        uploadedFilesArea.setEditable(false);
        frame.add(uploadedFilesArea);

        // Scroll pane for the text area
        JScrollPane scrollPane = new JScrollPane(uploadedFilesArea);
        scrollPane.setBounds(30, 280, 430, 100);
        frame.add(scrollPane);

        // Action listener for the upload button
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subject = (String) subjectComboBox.getSelectedItem();
                String fileType = (String) fileTypeComboBox.getSelectedItem();
                String driveLink = linkField.getText().trim();
                String description = descriptionField.getText().trim();

                if (driveLink.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Save to database
                saveToDatabase(subject, fileType, driveLink, description);
                uploadedFilesArea.append(subject + " - " + fileType + " Uploaded: " + description + " (" + driveLink + ")\n");
                JOptionPane.showMessageDialog(frame, "Material uploaded successfully!");

                // Clear input fields
                linkField.setText("");
                descriptionField.setText("");
            }
        });

        // Set frame visibility
        frame.setVisible(true);
    }

    private void saveToDatabase(String subject, String fileType, String driveLink, String description) {
        try {
            String query = "INSERT INTO materials (subject, file_type, drive_link, description) VALUES (?, ?, ?, ?)";
            Connection c = connect.c;
            PreparedStatement preparedStatement = c.prepareStatement(query);
            preparedStatement.setString(1, subject);
            preparedStatement.setString(2, fileType);
            preparedStatement.setString(3, driveLink);
            preparedStatement.setString(4, description);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error saving to database!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UploadMaterials();
    }
}


