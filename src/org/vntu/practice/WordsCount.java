package org.vntu.practice;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class WordsCount extends JFrame {
    public WordsCount() {
        super("Count words in any text");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel leftPanel = new JPanel(new BorderLayout());    

        leftPanel.add(new JLabel("Text: "), BorderLayout.NORTH);

        // final to access variable from ActionListener
        final JTextArea textTa = new JTextArea(10, 20);
        leftPanel.add(textTa, BorderLayout.CENTER);

        JButton btnCheck = new JButton("Count");
        leftPanel.add(btnCheck, BorderLayout.EAST);

        JPanel rightPanel = new JPanel(new BorderLayout());

        rightPanel.add(new JLabel("Result: "), BorderLayout.NORTH);

        final DefaultListModel listModel = new DefaultListModel();
        JList list = new JList(listModel);

        rightPanel.add(new JScrollPane(list), BorderLayout.CENTER);

        btnCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Map<String, Integer> uniqueWords = countUniqueWords(textTa.getText());

                for(String key: uniqueWords.keySet()) {
                    listModel.addElement(key + " : " + uniqueWords.get(key));
                }
            }
        });

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        pack();
    }

    public Map<String, Integer> countUniqueWords(String text) {
        String[] words = text.split("[\\p{Space},.;:!]+");

        Map<String, Integer> uniqueWords = new HashMap<String, Integer>();

        for (String w : words) {
            if (uniqueWords.containsKey(w)) {
                int count = uniqueWords.get(w);
                uniqueWords.put(w, ++count);
            } else uniqueWords.put(w, 1);
        }

        return uniqueWords;
    }

    public static void main(String[] args) {
        new WordsCount().setVisible(true);
    }
}
