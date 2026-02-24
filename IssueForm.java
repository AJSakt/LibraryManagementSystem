/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class IssueForm extends JFrame {

    JTextField txtStudentId, txtBookId;
    JButton btnIssue, btnView;
    JTable table;
    DefaultTableModel model;

    public IssueForm() {

        setTitle("Issue Book");
        setSize(650, 400);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtStudentId = new JTextField(10);
        txtBookId = new JTextField(10);

        btnIssue = new JButton("Issue Book");
        btnView = new JButton("View Issued Books");

        add(new JLabel("Student ID"));
        add(txtStudentId);

        add(new JLabel("Book ID"));
        add(txtBookId);

        add(btnIssue);
        add(btnView);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Student ID");
        model.addColumn("Book ID");
        model.addColumn("Issue Date");
        model.addColumn("Return Date");

        table = new JTable(model);
        add(new JScrollPane(table));

        btnIssue.addActionListener(e -> issueBook());
        btnView.addActionListener(e -> viewIssuedBooks());

        setVisible(true);
    }

    private void issueBook() {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO issue_book(student_id, book_id, issue_date, return_date) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, Integer.parseInt(txtStudentId.getText()));
            pst.setInt(2, Integer.parseInt(txtBookId.getText()));
            pst.setDate(3, Date.valueOf(LocalDate.now()));
            pst.setDate(4, Date.valueOf(LocalDate.now().plusDays(7)));

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book Issued Successfully!");
            viewIssuedBooks();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void viewIssuedBooks() {
        try {
            model.setRowCount(0);

            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM issue_book");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getInt("book_id"),
                        rs.getDate("issue_date"),
                        rs.getDate("return_date")
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}