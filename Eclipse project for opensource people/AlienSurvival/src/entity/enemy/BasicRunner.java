package entity.enemy;

import java.awt.Rectangle;
import java.util.ArrayList;

import entity.Enemy;
import entity.Platform;
import entity.Player;
import main.Main;

public class BasicRunner extends Enemy {

	public BasicRunner(int x, int y) {
		super(x, y);
		bounds = new Rectangle(x, y, (int) Math.round(60 * size), (int) Math.round(60 * size));
		id = "enemy";
		enemyImage = spriteSheet.getSubimage(80, 12, 6, 6);
		animationCounter = 0;
	}

	public void update(ArrayList<Platform> solids, Main m, Player p, ArrayList<Enemy> enemies) {
		spd = (spdStat * size + 1);
		jmp = (jmpStat * size + 1);
		grav = (gravStat * size);

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
		bounds = new Rectangle((int) x, (int) y, (int) Math.round(60 * size), (int) Math.round(60 * size));
		for (int i = 0; i < solids.size(); i++) {
			if (solids.get(i).bounded == true) {
				if (bounds.getBounds().intersects(solids.get(i).bounds.getBounds())) {
					x -= dx;
					x -= dx / Math.abs(dx + 100);
					dx /= 4;
					dx = (int) dx;
					if (onGround && !spawning) {
						dy = -(int) jmp;
					}
				}
			}
		}

		y += dy;
		bounds = new Rectangle((int) x, (int) y, (int) Math.round(60 * size), (int) Math.round(60 * size));
		for (int i = 0; i < solids.size(); i++) {
			if (solids.get(i).bounded == true) {
				if (bounds.getBounds().intersects(solids.get(i).bounds.getBounds())) {
					y -= dy;
					y -= dy / Math.abs(dy + 100);
					dy /= 4;
					dy = (int) dy;
				}
			}
		}
		// physics

		// ground check
		onGround = false;
		for (int i = 0; i < solids.size(); i++) {
			if (solids.get(i).bounded == true) {
				if (new Rectangle((int) x, (int) y + (int) Math.round(60 * size), (int) Math.round(60 * size), 5)
						.getBounds().intersects(solids.get(i).bounds.getBounds())) {
					onGround = true;
				}
			}
		}
		// ground check

		// gravity
		if (dy < 10) {
			dy += grav;
		}

		// friction
		if (friction) {
			if (dx > 0) {
				dx--;
			}
			if (dx < 0) {
				dx++;
			}
		}

		if (!spawning) {

			// walking/idle animations
			if (left || right) {
				if (Math.round(animationCounter % 32) == 0) {
					enemyImage = spriteSheet.getSubimage(44, 0, 6, 6);
				}
				if (Math.round(animationCounter % 32) == 8) {
					enemyImage = spriteSheet.getSubimage(50, 0, 6, 6);
				}
				if (Math.round(animationCounter % 32) == 16) {
					enemyImage = spriteSheet.getSubimage(44, 0, 6, 6);
				}
				if (Math.round(animationCounter % 32) == 24) {
					enemyImage = spriteSheet.getSubimage(56, 0, 6, 6);
				}
			}
			// AI
			if (x < p.x) {
				right = true;
				left = false;
			}
			if (x > p.x) {
				right = false;
				left = true;
			}

			int ammountNext = solids.size();

			for (int i = 0; i < solids.size(); i++) {
				if ((!(new Rectangle((int) ((x + (60 * size) / 2) + dx), (int) (y + (60 * size)), 1, 10)).getBounds()
						.intersects(solids.get(i).bounds.getBounds())) && (onGround)) {
					ammountNext--;
				}
			}

			if (ammountNext <= 0) {
				dy = -(int) jmp;
			}

			if (hp <= 0) {
				enemies.remove(this);
			}
		} else {
			if (Math.round(animationCounter % 128) >= 64) {
				enemyImage = spriteSheet.getSubimage(80, 12, 6, 6);
			} else if (Math.round(animationCounter % 128) >= 48) {
				enemyImage = spriteSheet.getSubimage(74, 12, 6, 6);
			} else if (Math.round(animationCounter % 128) >= 32) {
				enemyImage = spriteSheet.getSubimage(68, 12, 6, 6);
			} else if (Math.round(animationCounter % 128) >= 16) {
				enemyImage = spriteSheet.getSubimage(62, 12, 6, 6);
			}

			if (animationCounter >= 80) {
				spawning = false;
			}
		}

		// animation
		if (animationCounter >= 10000) {
			animationCounter = 0;
		}
		animationCounter++;

	}
}
