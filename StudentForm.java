/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StudentForm extends JFrame {

    JTextField txtName, txtDept;
    JButton btnAdd, btnView;
    JTable table;
    DefaultTableModel model;

    public StudentForm() {

        setTitle("Student Management");
        setSize(600, 400);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtName = new JTextField(15);
        txtDept = new JTextField(15);
        btnAdd = new JButton("Add Student");
        btnView = new JButton("View Students");

        add(new JLabel("Name"));
        add(txtName);

        add(new JLabel("Department"));
        add(txtDept);

        add(btnAdd);
        add(btnView);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Department");

        table = new JTable(model);
        add(new JScrollPane(table));

        btnAdd.addActionListener(e -> addStudent());
        btnView.addActionListener(e -> viewStudents());

        setVisible(true);
    }

    private void addStudent() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO students(name, department) VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, txtName.getText());
            pst.setString(2, txtDept.getText());

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Student Added!");
            viewStudents();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void viewStudents() {
        try {
            model.setRowCount(0);

            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department")
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}