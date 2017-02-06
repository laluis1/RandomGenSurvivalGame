package entity;

import java.awt.Graphics;
import java.awt.Image;

public class Tree extends Entity {

	Image tree;

	public String type;

	int width = 9;
	int height = 14;

	public Tree(int x, int y, String type) {
		super(x, y);
		z = (int) Math.round(Math.random() * 5);
		id = "tree";
		this.type = type;

		if (type == "beach") {
			// beach

			tree = spriteSheet.getSubimage(37, 78, 9, 14);

		} else if (type == "plains") {
			// plains

			if ((int) Math.round(Math.random()) == 1) {
				tree = spriteSheet.getSubimage(37, 78, 9, 14);
			} else {
				tree = spriteSheet.getSubimage(37, 78, 9, 14);
			}

		} else if (type == "forest") {
			// forest

			if ((int) Math.round(Math.random() * 2) <= 1) {
				tree = spriteSheet.getSubimage(32, 12, 9, 14);
			} else {
				tree = spriteSheet.getSubimage(37, 78, 9, 14);
			}

		} else if (type == "jungle") {
			// forest

			if ((int) Math.round(Math.random() * 2) <= 1) {
				tree = spriteSheet.getSubimage(46, 78, 9, 14);
			} else if ((int) Math.round(Math.random() * 2) == 2) {
				tree = spriteSheet.getSubimage(55, 78, 9, 14);
			}

		} else if (type == "random") {
			int randomTree = (int) Math.random() * 3;
			if (randomTree == 0) {
				tree = spriteSheet.getSubimage(37, 78, 9, 14);
			} else if (randomTree == 1) {
				tree = spriteSheet.getSubimage(32, 12, 9, 14);
			} else if (randomTree == 2) {
				tree = spriteSheet.getSubimage(46, 78, 9, 14);
			} else if (randomTree == 3) {
				tree = spriteSheet.getSubimage(55, 78, 9, 14);
			}
		} else {
			tree = spriteSheet.getSubimage(32, 12, 9, 14);
		}
	}

	public void draw(Graphics g) {
		g.drawImage(tree, (int) x + (90 - (int) Math.round(size * 90)),
				(int) y + (140 - (int) Math.abs(Math.round(size * 140))), (int) Math.round(size * 90),
				(int) Math.abs(Math.round(size * 140)), null);
	}
}
