/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class BookForm extends JFrame {

    JTextField txtTitle, txtAuthor, txtQuantity;
    JButton btnAdd, btnView;
    JTable table;
    DefaultTableModel model;

    public BookForm() {

        setTitle("Book Management");
        setSize(700, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 🔹 Top Panel (Form)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        txtTitle = new JTextField(10);
        txtAuthor = new JTextField(10);
        txtQuantity = new JTextField(5);

        topPanel.add(new JLabel("Title"));
        topPanel.add(txtTitle);

        topPanel.add(new JLabel("Author"));
        topPanel.add(txtAuthor);

        topPanel.add(new JLabel("Quantity"));
        topPanel.add(txtQuantity);

        add(topPanel, BorderLayout.NORTH);

        // 🔹 Center Panel (Table)
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Title");
        model.addColumn("Author");
        model.addColumn("Quantity");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        // 🔹 Bottom Panel (Buttons)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        btnAdd = new JButton("Add Book");
        btnView = new JButton("View Books");

        bottomPanel.add(btnAdd);
        bottomPanel.add(btnView);

        add(bottomPanel, BorderLayout.SOUTH);

        // Button Actions
        btnAdd.addActionListener(e -> addBook());
        btnView.addActionListener(e -> viewBooks());

        setVisible(true);
    }

    private void addBook() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO books(title, author, quantity) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, txtTitle.getText());
            pst.setString(2, txtAuthor.getText());
            pst.setInt(3, Integer.parseInt(txtQuantity.getText()));

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book Added!");
            viewBooks();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void viewBooks() {
        try {
            model.setRowCount(0);

            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM books");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("quantity")
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}