/* 
 * Program calculates how many times each word appears in a text.
 */
package org.vntu.practice;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class WordsCount extends JFrame {
    public WordsCount() {
        super("Count words in any text");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Text: "), BorderLayout.NORTH);

        // final to access variable from ActionListener
        final JTextArea textArea = new JTextArea(10, 20);
        textArea.setLineWrap(true);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JButton btnCheck = new JButton("Count");
        add(btnCheck, BorderLayout.SOUTH);

        btnCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                add(new JLabel("Result: "), BorderLayout.NORTH);

                DefaultListModel listModel = new DefaultListModel();

                Map<String, Integer> uniqueWords = countUniqueWords(textArea.getText());

                for(String key: uniqueWords.keySet()) {
                    listModel.addElement(key + " : " + uniqueWords.get(key));
                }

                JComponent[] controls = new JComponent[] { new JScrollPane(new JList(listModel)) };
                JOptionPane.showMessageDialog(WordsCount.this, controls, "Words count:", JOptionPane.PLAIN_MESSAGE);
            }
        });

        pack();
    }

    public Map<String, Integer> countUniqueWords(String text) {
        String[] words = text.toLowerCase().split("[\\p{Space},.;:!]+");

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
