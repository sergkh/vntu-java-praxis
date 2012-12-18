package org.vntu.practice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class TickerTape extends JFrame implements Runnable {

	private static final long serialVersionUID = 6489020857745515959L;

	private String msg;
	private int xpos;
	private int msgLen = 0;
	private final int SPEED = 2;
	private final int TIME_TO_REST = 30;
	private final Color TEXT_COLOR = new Color(0, 0, 80);
	private volatile boolean active = false;
	private BufferedImage buf;
	
	public TickerTape() {
		msg = "Java threads demonstration";
		setFont(new Font("TimesRoman", Font.BOLD, 36));
		setSize(400, getFont().getSize() * 2 + 50);
		xpos = getWidth();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				active = false;
				TickerTape.this.setVisible(false);
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		buf = new BufferedImage(600, 50, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = buf.createGraphics();
		g.setColor(TEXT_COLOR);
		g.setFont(getFont());
		g.drawString(msg, 0, 25);
	}

	public void run() {
		while (active) {
			repaint();
			xpos -= SPEED;
			try {
				Thread.sleep(TIME_TO_REST);
			} catch (InterruptedException e) {
				break;
			}
			if ((xpos + msgLen) < 0) xpos = getWidth();
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		
		if (msgLen == 0) msgLen = g.getFontMetrics().stringWidth(msg);
		
		g.setColor(TEXT_COLOR);
//		g.drawString(msg, xpos, (getHeight() + g.getFontMetrics().getHeight())/ 2);
		g.drawImage(buf, xpos, (getHeight() + 25)/ 2, this);
	}

	public void start() {
		active = true;
		Thread back = new Thread(this);
		back.setPriority(Thread.MIN_PRIORITY);
		back.start();
	}

	public void stop() {
		active = false;
	}

	public static void main(String[] args) {
		TickerTape type = new TickerTape();
		type.setVisible(true);
		type.start();
	}
}
