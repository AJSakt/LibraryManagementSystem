/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    JButton btnBooks, btnStudents, btnIssue;

    public Dashboard() {

        setTitle("Library Dashboard");
        setSize(400, 300);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnBooks = new JButton("Manage Books");
        btnStudents = new JButton("Manage Students");
        btnIssue = new JButton("Issue Book");

        add(btnBooks);
        add(btnStudents);
        add(btnIssue);

        btnBooks.addActionListener(e -> new BookForm());
        btnStudents.addActionListener(e -> new StudentForm());
        btnIssue.addActionListener(e -> new IssueForm());

        setVisible(true);
    }
}