package entity;

import java.awt.Graphics;
import java.awt.Image;

public class BackgroundMountains extends Entity{

	Image background;

	public BackgroundMountains(int x, int y) {
		super(x, y);
		background = spriteSheet.getSubimage(32, 32, 32, 32);
	}

	public void update() {

	}

	public void draw(Graphics g) {
		for(int i = 0; i < 20; i++){
			g.drawImage(background, (int) x - 320*i, (int) y, 320, 330, null);
		}
		g.drawImage(background, (int) x, (int) y, 320, 320, null);
		for(int i = 0; i < 10; i++){
			g.drawImage(background, (int) x + 320*i, (int) y, 320, 330, null);
		}
	}

}