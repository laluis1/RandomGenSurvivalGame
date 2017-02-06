package entity.base;

import java.awt.Graphics;
import java.awt.Image;

import entity.Entity;

public class EscapePod extends Entity {

	Image escapePodImage;

	public EscapePod(int x, int y) {
		super(x, y);

		escapePodImage = spriteSheet.getSubimage(62, 0, 24, 12);
		
		z = 9;
	}

	public void draw(Graphics g) {
		g.drawImage(escapePodImage, (int) x, (int) y, (int) Math.round(size * 240),
				(int) Math.abs(Math.round(size * 120)), null);
	}
}
