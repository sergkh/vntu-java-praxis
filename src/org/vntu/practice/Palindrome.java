package org.vntu.practice;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Palindrome extends JFrame {
    public Palindrome() {
        super("Check Palindrome");
        setSize(300, 200);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("String: "));

        // final to access variable from ActionListener
        final JTextField textTf = new JTextField(20);
        add(textTf);

        add(new JLabel("Result:"));

        final JTextField resultTf = new JTextField(20);
        resultTf.setEditable(false);
        add(resultTf);

        JButton btnCheck = new JButton("Check");
        add(btnCheck);

        btnCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String val = textTf.getText();
                String[] words = val.split(" ");

                if(words.length != 2) {
                    resultTf.setText("Input two words, please");
                    return;
                }

                String reveseStr = "";

                for (int i = words[1].length() - 1; i >= 0; i--)
                    reveseStr += words[1].charAt(i);
                
                if (words[0].equals(reveseStr)) { 
                    resultTf.setText("It is a palindrome");
                } else {
                    resultTf.setText("It is not a palindrome");
                }
            }
        });
    }

    public static void main(String[] args) {
        new Palindrome().setVisible(true);
    }
}
