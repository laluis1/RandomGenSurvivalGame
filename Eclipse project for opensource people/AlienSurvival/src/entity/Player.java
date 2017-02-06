package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entity.weapon.Grenade;
import main.Main;

public class Player extends Entity {

	public int inventory[] = {0, 0, 0, 0};
	
	public double spd = 5;
	public double jmp = 10;
	public double grav = .5;

	public double spdStat = 5;
	public double jmpStat = 10;
	public double gravStat = .5;

	// what is the dy/dx of e^x?
	double dy = 0;
	// --------
	double dx = 0;

	boolean friction = false;

	boolean onGround = false;

	boolean left = false;
	boolean right = false;

	boolean locked = false;

	double hp = 1000;
	double maxHp = 1000;
	double deltaHp = 0;
	int level = 1;
	double xp = 0;
	double untilNextLevel = 400;

	boolean shooting = false;
	boolean rayHit = false;

	int directionFacing = 1;

	int shieldTime = 0;

	int animationCounter = 0;

	Rectangle shootingRaycast = new Rectangle(0, -1000, 1, 1);
	int shootingRaycastWidth = 1;

	Image playerImage;

	Image shootingEnd;

	// player level/xp, not game level
	BufferedImage levels;
	// level number
	Image currentLevelImage;
	// text = "LV:"
	Image levelTextImage;
	
	Image InventoryHud;

	// shooting grenades
	boolean shootGrenade = false;

	public Player(int x, int y) {
		super(x, y);
		bounds = new Rectangle(x, y, (int) Math.round(60 * size), (int) Math.round(60 * size));
		id = "player";
		try {
			levels = ImageIO.read(new File("levels.png"));
		} catch (Exception e) {
		}
		currentLevelImage = levels.getSubimage(8, 0, 4, 4);
		levelTextImage = levels.getSubimage(0, 0, 8, 4);
		playerImage = spriteSheet.getSubimage(32, 6, 6, 6);
		shootingEnd = spriteSheet.getSubimage(50, 12, 6, 6);
		InventoryHud = spriteSheet.getSubimage(104, 115, 39, 12);
	}

	public void update(ArrayList<Platform> solids, Main m, ArrayList<Enemy> enemies) {
		spd = (spdStat * size + 1);
		jmp = (jmpStat * size + 1);
		grav = (gravStat * size);

		// Hp Stuff
		hp -= deltaHp;

		if (deltaHp > 0) {
			deltaHp /= 1.2;
		}
		if (deltaHp < 0) {
			deltaHp /= 1.2;
		}

		if (hp < maxHp) {
			hp++;
		}

		// enemy collisions
		if (shieldTime <= 0) {
			for (int i = 0; i < enemies.size(); i++) {
				if (enemies.get(i).x > -60 && enemies.get(i).x < 640) {
					if (bounds.getBounds().intersects(enemies.get(i).bounds.getBounds()) && !enemies.get(i).spawning) {
						deltaHp += 32;
						shieldTime = 50;
					}
				}
			}
		} else {
			shieldTime--;
		}

		// physics
		x += dx;
		bounds = new Rectangle((int) x, (int) y, (int) Math.round(60 * size), (int) Math.round(60 * size));
		for (int i = 0; i < solids.size(); i++) {
			if (solids.get(i).bounded == true) {
				if (bounds.getBounds().intersects(solids.get(i).bounds.getBounds())) {
					x -= dx;
					dx = 0;
				}
			}
		}

		y += dy;
		bounds = new Rectangle((int) x+5, (int) y, (int) Math.round(60 * size)-10, (int) Math.round(60 * size));
		for (int i = 0; i < solids.size(); i++) {
			if (solids.get(i).bounded == true) {
				if (bounds.getBounds().intersects(solids.get(i).bounds.getBounds())) {
					y -= dy;
					dy = 0;
				}
			}
		}
		// physics

		// ground check
		onGround = false;
		for (int i = 0; i < solids.size(); i++) {
			if (new Rectangle((int) x, (int) y + (int) Math.round(60 * size), (int) Math.round(60 * size), 10)
					.getBounds().intersects(solids.get(i).bounds.getBounds())) {
				onGround = true;
				y -= .5;
			}
		}
		// ground check

		// gravity
		if (dy < 10) {
			dy += grav;
		}

		// movement
		if (shooting) {
			locked = true;
		} else {
			locked = false;
		}

		if (!locked) {
			if (left) {
				dx = -(int) spd;
				friction = false;
			}
			if (right) {
				dx = (int) spd;
				friction = false;
			}
		} else {
			friction = true;
		}

		// friction
		// if (friction) {
		if (dx > 0) {
			dx--;
		}
		if (dx < 0) {
			dx++;
		}
		// }

		if (level < 10 && level > 0) {
			currentLevelImage = levels.getSubimage((4 + (4 * level)), 0, 4, 4);
		}else{
			level = 9;
		}

		// animation
		if (animationCounter >= 10000) {
			animationCounter = 0;
		}
		animationCounter++;

		// walking/idle animations
		if (left || right) {
			if (Math.round(animationCounter % 32) == 0) {
				playerImage = spriteSheet.getSubimage(32, 6, 6, 6);
			}
			if (Math.round(animationCounter % 32) == 8) {
				playerImage = spriteSheet.getSubimage(38, 6, 6, 6);
			}
			if (Math.round(animationCounter % 32) == 16) {
				playerImage = spriteSheet.getSubimage(32, 6, 6, 6);
			}
			if (Math.round(animationCounter % 32) == 24) {
				playerImage = spriteSheet.getSubimage(44, 6, 6, 6);
			}
		} else {
			if (Math.round(animationCounter % 128) == 0) {
				playerImage = spriteSheet.getSubimage(32, 6, 6, 6);
			}
			if (Math.round(animationCounter % 128) == 64) {
				playerImage = spriteSheet.getSubimage(50, 6, 6, 6);
			}
		}

		if (!onGround) {
			playerImage = spriteSheet.getSubimage(56, 6, 6, 6);
		} else {
			if (playerImage == spriteSheet.getSubimage(56, 6, 6, 6)) {
				playerImage = spriteSheet.getSubimage(32, 6, 6, 6);
			}
		}

		if (shooting) {
			playerImage = spriteSheet.getSubimage(44, 12, 6, 6);

			if (rayHit == false) {
				shootingRaycastWidth += 60;
			}

			////////////////////////////////////////// FIX SHOOTING
			if (directionFacing == 1) {
				shootingRaycast = new Rectangle((int) x + (int) Math.round(60 * size / 2),
						(int) y + (int) Math.round(60 * size / 2), shootingRaycastWidth * directionFacing, 1);
			}

			if (directionFacing == -1) {
				shootingRaycast = new Rectangle((int) x + (int) Math.round(60 * size / 2) - shootingRaycastWidth,
						(int) y + (int) Math.round(60 * size / 2), shootingRaycastWidth, 1);
			}

			rayHit = false;

			for (int i = 0; i < solids.size(); i++) {
				if (solids.get(i).bounds.getBounds().intersects(shootingRaycast.getBounds())) {
					rayHit = true;
					shootingRaycastWidth = 0;
				}
			}

		} else {
			rayHit = false;
			shootingRaycast = new Rectangle(0, -1000, 1, 1);
			shootingRaycastWidth = 1;
		}

		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).x > -60 && enemies.get(i).x < 640) {
				if (shootingRaycast.getBounds().intersects(enemies.get(i).bounds.getBounds())) {
					enemies.get(i).hp -= 100;
					xp += 50 * Math.random();
				}
			}
		}

		if (xp >= untilNextLevel) {
			xp = xp % untilNextLevel;
			level++;
			untilNextLevel *= 1.5;
			maxHp += 100;
		}

	}

	public void draw(Graphics g) {
		// lol satan 6 6 6:

		if (left) {
			directionFacing = -1;
		}
		if (right) {
			directionFacing = 1;
		}

		if (directionFacing == 1) {
			g.drawImage(playerImage, (int) x, (int) y, (int) Math.round(60 * size) * directionFacing,
					(int) Math.round(60 * size), null);
		} else {
			g.drawImage(playerImage, (int) x + 60, (int) y, (int) Math.round(60 * size) * directionFacing,
					(int) Math.round(60 * size), null);
		}

		// hp bar

		// red indicator
		g.setColor(new Color(255, 0, 136));
		g.fillRect(10, 30, (int) (Math.round(200)), 20);
		// green indicator
		g.setColor(new Color(0, 255, 136));
		g.fillRect(10, 30, (int) (Math.round((hp / maxHp) * 200)), 20);

		// levels
		g.drawImage(levelTextImage, 10, 60, 80, 40, null);
		g.drawImage(currentLevelImage, 100, 60, 40, 40, null);

		// Graphics2D g2d = (Graphics2D) g;
		// g2d.draw(shootingRaycast);

		if (shooting) {
			if (animationCounter % 3 == 1) {
				if (directionFacing == 1) {
					g.drawImage(shootingEnd, (int) (x + 60 * size), (int) y, (int) Math.round(60 * size),
							(int) Math.round(60 * size), null);
				}
				if (directionFacing == -1) {
					g.drawImage(shootingEnd, (int) (x - ((size - 1) * 60)), (int) y, -(int) Math.round(60 * size),
							(int) Math.round(60 * size), null);
				}
			}
		}

	}

	public void keyDown(KeyEvent e) {
		// input
		// left/right
		if (e.getKeyCode() == 37) {
			left = true;
		}
		if (e.getKeyCode() == 39) {
			right = true;
		}
		if (e.getKeyCode() == 88) {
			shooting = true;
		}

		// jump
		if (e.getKeyCode() == 90) {
			if (onGround) {
				dy = -(int) jmp;
			}
		}
		// input
	}

	public void keyUp(KeyEvent e) {
		if (e.getKeyCode() == 37) {
			left = false;
		}
		if (e.getKeyCode() == 39) {
			right = false;
		}
		if (e.getKeyCode() == 88) {
			shooting = false;
		}
		if (e.getKeyCode() == 90) {
			friction = true;
		}
	}
}
