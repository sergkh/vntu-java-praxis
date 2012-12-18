package org.vntu.practice;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MulticastChat extends JFrame implements Runnable {

	private static final long serialVersionUID = -6539741015936730693L;

	private int port = 5050;
	private String group = "225.4.5.6";
	private int ttl = 3;
	
	private JTextArea chatArea = new JTextArea(25, 20);
	private JTextField msgField = new JTextField(25);
	
	private volatile boolean active = true;
	
	public MulticastChat() {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(chatArea, BorderLayout.CENTER);
		
		JPanel msgPane = new JPanel(new BorderLayout());
		
		msgPane.add(msgField, BorderLayout.CENTER);
		
		JButton sendBtn = new JButton("Send");
		
		sendBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = msgField.getText();
				if(message.isEmpty()) return ;
				send(message);
				msgField.setText("");
			}
		});
		
		msgPane.add(sendBtn, BorderLayout.EAST);
		
		add(msgPane, BorderLayout.SOUTH);
		
		pack();
		
		new Thread(this).start();
	}
		
	public void send(String msg) {
		try {
			MulticastSocket socket = new MulticastSocket(); 
			socket.setTimeToLive(ttl);
			
			byte[] buf = msg.getBytes("UTF-8");
			DatagramPacket pack = new DatagramPacket(buf, buf.length, InetAddress.getByName(group), port);
			socket.send(pack);
			
			socket.close();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void run() {
		try {
			MulticastSocket socket = new MulticastSocket(port);
			InetAddress address = InetAddress.getByName(group);
			socket.joinGroup(address);
	
			byte[] buf = new byte[2048];

			while(active) {
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);

				final String from = packet.getAddress().getHostName();
				final String val = new String(buf, 0, packet.getLength(), "UTF-8");

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						chatArea.append(from + ": " + val + "\n");
					}
				});
			}
			
			socket.leaveGroup(address);
			socket.close();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new MulticastChat().setVisible(true);
	}
	
}
