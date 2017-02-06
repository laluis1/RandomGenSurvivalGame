package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import main.Main;

public class Platform extends Entity {

	Image platform, dirt;

	public int width, height;

	public String type = "";

	public boolean bounded = true;

	public Platform(int x, int y, int width, int height, String type) {
		super(x, y);
		platform = spriteSheet.getSubimage(32, 0, 6, 6);
		dirt = spriteSheet.getSubimage(38, 0, 6, 6);
		// Biome selection
		this.type = type;
		if (type == "forest") {
			bounded = true;
			platform = spriteSheet.getSubimage(32, 0, 6, 6);
			dirt = spriteSheet.getSubimage(38, 0, 6, 6);
		}
		if (type == "beach") {
			bounded = true;
			platform = spriteSheet.getSubimage(56, 66, 6, 6);
			dirt = spriteSheet.getSubimage(56, 72, 6, 6);
		}
		if (type == "jungle") {
			bounded = true;
			platform = spriteSheet.getSubimage(44, 66, 6, 6);
			dirt = spriteSheet.getSubimage(44, 72, 6, 6);
		}
		if (type == "plains") {
			bounded = true;
			platform = spriteSheet.getSubimage(38, 66, 6, 6);
			dirt = spriteSheet.getSubimage(38, 72, 6, 6);
		}
		if (type == "water") {
			bounded = false;
			platform = spriteSheet.getSubimage(50, 66, 6, 6);
			dirt = spriteSheet.getSubimage(50, 72, 6, 6);
		}
		if (type == "metal") {
			bounded = true;
			platform = spriteSheet.getSubimage(62, 66, 6, 6);
			dirt = spriteSheet.getSubimage(62, 72, 6, 6);
		}

		this.width = width;
		this.height = height;
		id = "platform";
		bounds = new Rectangle((int) x, (int) y, (int) Math.round(size * 60 * width),
				(int) Math.round(size * 60 * height));
	}

	public void update(ArrayList<Platform> solids, Main m, Player p, ArrayList<Enemy> enemies) {
		bounds = new Rectangle((int) x, (int) y, (int) Math.round(size * 60 * width),
				(int) Math.round(size * 60 * height));
	}

	public void draw(Graphics g) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (j != 0) {
					g.drawImage(dirt, (int) x + (i * (int) Math.round(size * 60)),
							(int) y + (j * (int) Math.round(size * 60)), (int) Math.round(size * 60),
							(int) Math.round(size * 60), null);
				} else {
					g.drawImage(platform, (int) x + (i * (int) Math.round(size * 60)),
							(int) y + (j * (int) Math.round(size * 60)), (int) Math.round(size * 60),
							(int) Math.round(size * 60), null);
				}

				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(Color.BLACK);
				//g2d.draw(bounds);
			}
		}
	}
}
