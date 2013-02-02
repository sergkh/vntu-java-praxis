package org.vntu.practice;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Square extends JFrame implements ActionListener {
  private static final long serialVersionUID = 41688112928116370L;

	private JTextField firstNumTextFld;
	private JTextField secondNumTextFld;
	private JLabel resultLabel;
	
    public Square() {
        super("Check Square");
        
        setSize(250, 200);
        setLayout(new FlowLayout());
        add(new JLabel("First  number: "));
        
        firstNumTextFld = new JTextField(10);
        add(firstNumTextFld);

        add(new JLabel("Second number:"));
        secondNumTextFld = new JTextField(10);
        add(secondNumTextFld);

        JButton checkBtn = new JButton("Check");
        add(checkBtn);

        resultLabel = new JLabel();
        add(resultLabel);
        
        checkBtn.addActionListener(this);
        setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
      new Square().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	 String str1 = firstNumTextFld.getText();
         String str2 = secondNumTextFld.getText();
         
         int first = Integer.parseInt(str1);
         int second = Integer.parseInt(str2);
         
         String answer;
         
         if (first*first == second || second*second == first) { 
             answer = "Yes" ;
		 } else {
			 answer = "No";
		 }
         
         resultLabel.setText("Square: " + answer);
    }
}
