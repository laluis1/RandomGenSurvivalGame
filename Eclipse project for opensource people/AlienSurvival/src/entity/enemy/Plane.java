package entity.enemy;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import entity.Enemy;
import entity.Platform;
import entity.Player;
import main.Main;

public class Plane extends Enemy {

	public Plane(int x, int y) {
		super(x, y);
		bounds = new Rectangle(x, y, (int) Math.round(60 * size), (int) Math.round(60 * size));
		id = "plane";
		enemyImage = spriteSheet.getSubimage(92, 0, 23, 8);
		spdStat = 10;
	}

	public void update(ArrayList<Platform> solids, Main m, Player p, ArrayList<Enemy> enemies) {
		spd = (spdStat * size + 1);
		// movement
		if (left) {
			dx = -(int) spd;
			friction = false;
		}
		if (right) {
			dx = (int) spd;
			friction = false;
		}

		// physics
		x += dx;
		y += dy;

		// AI
		if (left == false && right == false) {
			if (x < p.x) {
				right = true;
				left = false;
			}
			if (x > p.x) {
				right = false;
				left = true;
			}
		}

	}
	
	public void draw(Graphics g) {
		// lol satan 6 6 6:
		if(left){
			g.drawImage(enemyImage, (int) x + (int)Math.round(230 * size), (int) y, (int) -Math.round(230 * size),
					(int) Math.round(80 * size), null);
		}
		if(right){
			g.drawImage(enemyImage, (int) x, (int) y, (int) Math.round(230 * size),
					(int) Math.round(80 * size), null);
		}
		g.drawRect((int)x, (int)y, (int)(230*size), (int)(80*size));
	}

}
