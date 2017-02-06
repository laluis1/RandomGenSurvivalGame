package entity;

import java.awt.Graphics;
import java.awt.Image;

public class Moon extends Entity {

	Image background;

	public Moon(int x, int y) {
		super(x, y);
		background = spriteSheet.getSubimage(0, 64, 32, 32);
	}

	public void update() {

	}

	public void draw(Graphics g) {
		g.drawImage(background, (int) x, (int) y, 320, 320, null);
	}

}
