package entity;

import java.awt.Graphics;
import java.awt.Image;

public class Clouds extends Entity {

	Image background;

	public Clouds(int x, int y) {
		super(x, y);
		background = spriteSheet.getSubimage(0, 96, 32, 32);
	}

	public void update() {

	}

	public void draw(Graphics g) {
		for (int j = -1; j < 1; j++) {
			for (int i = 0; i < 40; i++) {
				g.drawImage(background, (int) x - 320 * i, (int) y - (j * 330), 320, 330, null);
			}
			g.drawImage(background, (int) x, (int) y - (j * 330), 320, 320, null);
			for (int i = 0; i < 40; i++) {
				g.drawImage(background, (int) x + 320 * i, (int) y - (j * 330), 320, 330, null);
			}
		}
	}

}
