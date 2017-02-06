package entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import main.Main;

public class Chest extends Platform {

	public Image chestImage;

	public boolean open = false;

	public Chest(int x, int y, String type) {
		super(x, y, 1, 1, type);
		bounds = new Rectangle(x, y, (int) Math.round(60 * size), (int) Math.round(60 * size));
		id = "chest";
		type = "chest";
		bounded = false;

		chestImage = spriteSheet.getSubimage(38, 92, 6, 6);
	}

	public void open() {
		open = true;
		chestImage = spriteSheet.getSubimage(44, 92, 6, 6);
	}

	public void update(ArrayList<Platform> solids, Main m, Player p, ArrayList<Enemy> enemies) {
		bounds = new Rectangle((int) x, (int) y, (int) Math.round(size * 60) * width,
				(int) Math.round(size * 60) * height);

		if (!open) {
			if (bounds.getBounds().intersects(p.bounds.getBounds())) {
				open();
				p.maxHp += Math.random() * 20;
				p.spdStat += Math.random() / 8;
				p.jmpStat += Math.random() / 8;
			}
		}

	}

	public void draw(Graphics g) {
		g.drawImage(chestImage, (int) x + (int) Math.round(60 * size), (int) y, (int) -Math.round(60 * size),
				(int) Math.round(60 * size), null);
	}
}