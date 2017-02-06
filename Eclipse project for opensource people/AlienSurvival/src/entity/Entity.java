package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public abstract class Entity {

	public double x, y;

	public BufferedImage spriteSheet;

	public Rectangle bounds;
	
	public double size = 1;
	
	public int z = 0;
	
	public String id = "";

	public Entity(int x, int y) {
		this.x = x;
		this.y = y;

		try {
			spriteSheet = ImageIO.read(new File("sprites.png"));
		} catch (Exception e) {
		}
	}

	public void update() {

	}

	public void draw(Graphics g) {
		
	}

	public void keyDown(KeyEvent e) {

	}

	public void keyUp(KeyEvent e) {

	}
}
