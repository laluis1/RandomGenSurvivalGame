package entity;

import java.awt.Graphics;
import java.awt.Image;

public class Background extends Entity {

	Image background;

	public Background(int x, int y) {
		super(x, y);
		background = spriteSheet.getSubimage(0, 0, 32, 64);
	}

	public void update() {

	}

	public void draw(Graphics g) {
		g.drawImage(background, (int) x - 320, (int) y, 320, 640, null);
		g.drawImage(background, (int) x, (int) y, 320, 640, null);
		g.drawImage(background, (int) x + 320, (int) y, 320, 640, null);
	}

}
