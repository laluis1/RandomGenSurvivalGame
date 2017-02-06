package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.EventListener;

import javax.swing.JFrame;

import scene.MainMenu;
import scene.Scene;

public class Main extends JFrame implements KeyListener, EventListener, MouseListener, MouseMotionListener {

	public boolean running = true;

	private int ShouldBeFPS = 60;

	// for fps handle
	private long lastTime;
	public double fps; // could be int or long for integer values

	// double buffer
	Graphics g;
	BufferedImage dbImage;

	// scenes
	public Scene scene;

	// for window resize
	int prevHeight = 640;
	int prevWidth = 480;
	int prevWidthCounter = 0;

	// setup function
	public Main() {
		// window setup
		setSize(640, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("Luis wuz heer");

		// first scene
		scene = new MainMenu();
		// scene = new InsideEscapePod();
		scene.init();

		// start loop
		update();
	}

	// main function
	public static void main(String agrs[]) {
		new Main();
	}

	// loop function
	public void update() {
		while (running) {
			// lastTime = System.nanoTime();
			try {
				Thread.sleep((int) (1000 / ShouldBeFPS));
			} catch (Exception e) {
			}

			this.addKeyListener(this);
			this.addMouseListener(this);
			this.addMouseMotionListener(this);

			// loop

			scene.update(this);
			
			//end loop
			
			repaint();

			fps = 1000000000.0 / (System.nanoTime() - lastTime);
			lastTime = System.nanoTime();

			if (scene.changeScene) {
				scene = scene.nextScene;
				scene.init();
			}

		}
	}

	// drawing function
	// 640 and 480 are the bounds of the window it can be scaled
	public void paint(Graphics g1) {
		dbImage = (BufferedImage) createImage(640, 480);

		g = dbImage.getGraphics();

		g.clearRect(0, 0, 640, 480);

		// drawing code for each frame

		// draw scene
		try {
			scene.draw(g);
		} catch (Exception e) {
		}

		// draw fps
		g.setFont(g.getFont().deriveFont(Font.BOLD, 12));
		g.drawString("FPS: " + ((int) fps), 580, 40);

		// end of drawing code

		// tint
		Color tint = new Color(255, 100, 100, 20);
		g.setColor(tint);
		g.fillRect(0, 0, 640, 480);
		// end of tint

		g1.drawImage(dbImage, 0, 0, getWidth(), getHeight(), null);
	}

	public void keyPressed(KeyEvent e) {
		scene.keyDown(e);
	}

	public void keyReleased(KeyEvent e) {
		scene.keyUp(e);
	}

	public void keyTyped(KeyEvent e) {
		scene.keyTyped(e);
	}

	public void mouseClicked(MouseEvent e) {
		scene.mouseClicked(e);
	}

	public void mouseEntered(MouseEvent e) {
		scene.mouseEntered(e);
	}

	public void mouseExited(MouseEvent e) {
		scene.mouseExited(e);
	}

	public void mousePressed(MouseEvent e) {
		scene.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		scene.mouseReleased(e);
	}

	public void mouseDragged(MouseEvent e) {
		scene.mouseDragged(e);
	}

	public void mouseMoved(MouseEvent e) {
		scene.mouseMoved(e);
	}
}
