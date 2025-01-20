package school.management.system;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class AddNewTeacher extends JFrame implements ActionListener {
    JTextField tfname, tffname, tfid,tfaddress, tfphone, tfemail, tfnid;
     
    JDateChooser dcdob;

    JButton submit, cancel;

    Random ran = new Random();
    long first4 = Math.abs((ran.nextLong() % 9000L) + 1000L);

    AddNewTeacher() {
        setSize(1000, 700);
        setLocation(350, 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main container panel
        JPanel container = new JPanel();
        container.setBackground(new Color(245, 245, 245)); // Lighter background
        container.setLayout(null);
        add(container);

        JLabel heading = new JLabel("New Teacher Details");
        heading.setBounds(300, 30, 500, 50);
        heading.setFont(new Font("Raleway", Font.BOLD, 30));
        heading.setForeground(new Color(70, 130, 180));
        container.add(heading);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(50, 120, 150, 30);
        lblname.setFont(new Font("Raleway", Font.BOLD, 18));
        container.add(lblname);

        tfname = new JTextField();
        tfname.setBounds(250, 120, 200, 30);
        tfname.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        tfname.setBackground(Color.WHITE);
        container.add(tfname);

        JLabel lblfname = new JLabel("Father's Name");
        lblfname.setBounds(500, 120, 150, 30);
        lblfname.setFont(new Font("Raleway", Font.BOLD, 18));
        container.add(lblfname);

        tffname = new JTextField();
        tffname.setBounds(700, 120, 200, 30);
        tffname.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        tffname.setBackground(Color.WHITE);
        container.add(tffname);

        JLabel lblrollno = new JLabel("ID Number");
        lblrollno.setBounds(50, 180, 150, 30);
        lblrollno.setFont(new Font("Raleway", Font.BOLD, 18));
        container.add(lblrollno);

        tfid = new JTextField();
        tfid.setBounds(250, 180, 200, 30);
        tfid.setFont(new Font("Raleway", Font.BOLD, 18));
        tfid.setForeground(new Color(70, 130, 180));
        container.add(tfid);

        JLabel lbldob = new JLabel("Date of Birth");
        lbldob.setBounds(500, 180, 150, 30);
        lbldob.setFont(new Font("Raleway", Font.BOLD, 18));
        container.add(lbldob);

        dcdob = new JDateChooser();
        dcdob.setBounds(700, 180, 200, 30);
        dcdob.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        dcdob.setBackground(Color.WHITE);
        container.add(dcdob);

        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(50, 240, 150, 30);
        lbladdress.setFont(new Font("Raleway", Font.BOLD, 18));
        container.add(lbladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(250, 240, 200, 30);
        tfaddress.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        tfaddress.setBackground(Color.WHITE);
        container.add(tfaddress);

        JLabel lblphone = new JLabel("Phone");
        lblphone.setBounds(500, 240, 150, 30);
        lblphone.setFont(new Font("Raleway", Font.BOLD, 18));
        container.add(lblphone);

        tfphone = new JTextField();
        tfphone.setBounds(700, 240, 200, 30);
        tfphone.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        tfphone.setBackground(Color.WHITE);
        container.add(tfphone);

        JLabel lblemail = new JLabel("Email ID");
        lblemail.setBounds(50, 300, 150, 30);
        lblemail.setFont(new Font("Raleway", Font.BOLD, 18));
        container.add(lblemail);

        tfemail = new JTextField();
        tfemail.setBounds(250, 300, 200, 30);
        tfemail.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        tfemail.setBackground(Color.WHITE);
        container.add(tfemail);

        JLabel lblnid = new JLabel("NID No");
        lblnid.setBounds(500, 300, 200, 30);
        lblnid.setFont(new Font("Raleway", Font.BOLD, 18));
        container.add(lblnid);

        tfnid = new JTextField();
        tfnid.setBounds(700, 300, 200, 30);
        tfnid.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        tfnid.setBackground(Color.WHITE);
        container.add(tfnid);

        submit = new JButton("Submit");
        submit.setBounds(300, 450, 150, 40);
        submit.setBackground(new Color(70, 130, 180));
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Raleway", Font.BOLD, 18));
        submit.setFocusPainted(false);
        submit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submit.addActionListener(this);
        container.add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(500, 450, 150, 40);
        cancel.setBackground(new Color(220, 53, 69));
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Raleway", Font.BOLD, 18));
        cancel.setFocusPainted(false);
        cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancel.addActionListener(this);
        container.add(cancel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            JOptionPane.showMessageDialog(this, "Details Submitted!");

            String name = tfname.getText();
            String fname = tffname.getText();
            String IdText = tfid.getText();
            
            String dob = ((JTextField) dcdob.getDateEditor().getUiComponent()).getText();
            
            String address = tfaddress.getText();
            String phone = tfphone.getText();
            String email = tfemail.getText();
            String nid = tfnid.getText();

            if (name.isEmpty() || fname.isEmpty() || email.isEmpty() || IdText.isEmpty() || dob.isEmpty() || nid.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill out all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = Integer.parseInt(IdText);

            String query = "INSERT INTO teacher (name,fname, id, dob, address, phone, email, nid) VALUES ('"
                    + name + "', '" + fname + "', " + id + ", '" + dob + "', '" + address + "', '" + phone + "', '" + email + "', '" + nid + "')";

            try {
                Connect c = new Connect();
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "Teacher add Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AddNewTeacher();
    }
}


