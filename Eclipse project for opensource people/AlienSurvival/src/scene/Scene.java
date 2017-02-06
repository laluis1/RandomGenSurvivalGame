package scene;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import main.Main;

public abstract class Scene {
	public Scene nextScene = this;
	public boolean changeScene = false;

	public void init() {
	}

	public void update(Main m) {

	}

	public void draw(Graphics g) {

	}

	public void keyDown(KeyEvent e) {

	}

	public void keyUp(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}
}
