package org.vntu.practice;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;

public class Rastr extends JFrame implements Runnable {

	private static final long serialVersionUID = 5847708911895635279L;

	final static int QUANTITY = 10;
	
	private Image images[];
	private Image offScreenImage;
	private AtomicBoolean active = new AtomicBoolean(false);
	int counter = 0;
	boolean forward = true;
	private Dimension size;
	
	public Rastr() {
		super("Movie player");
		images = new Image[QUANTITY];
		
		for (int i = 0; i < QUANTITY; i++) {
			images[i] = Toolkit.getDefaultToolkit()
				.getImage("images//pic" + Integer.toString(i + 1) + ".gif");
		}
		
		setSize(135, 115);
		offScreenImage = createImage(getWidth(), getHeight());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		size = getSize();
	}
	
	public void start() {
		active.set(true);
		new Thread(this).start();
	}

	public void run() {
		while (active.get()) {
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) { break; }
			repaint();
		}
	}

	public void paint(Graphics g) {
		g.drawImage(images[counter], 0, 0, this);
		if (forward) counter++;
		else counter--;
		
		if (counter == QUANTITY - 1 || counter == 0) forward = !forward;
	}
	
	public void update(Graphics g) {
		if (offScreenImage == null || !getSize().equals(size)) {
			offScreenImage = createImage(getWidth(), getHeight());
			size = getSize();
		}
		
		Graphics offScreenGraphics = offScreenImage.getGraphics();
		offScreenGraphics.setColor(getBackground());
		offScreenGraphics.fillRect(0, 0, getWidth(), getHeight());
		offScreenGraphics.setColor(g.getColor());
		paint(offScreenGraphics);
		g.drawImage(offScreenImage, 0, 0, this);
	}

	public static void main(String[] args) {
		Rastr rastr = new Rastr();
		rastr.setVisible(true);
		rastr.start();
	}
}