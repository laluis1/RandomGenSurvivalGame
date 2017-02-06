package scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entity.Background;
import entity.BackgroundMountains;
import entity.Clouds;
import entity.Enemy;
import entity.Entity;
import entity.Moon;
import entity.Platform;
import entity.Player;
import entity.Tree;
import entity.enemy.BasicRunner;
import entity.enemy.Village;
import entity.enemy.VillageCenter;
import entity.enemy.VillageHouse;
import entity.enemy.VillageTower;
import main.Main;

public class BlankWorld extends Scene {

	Background background;
	BackgroundMountains backgroundMountains;
	Clouds clouds1;
	Clouds clouds2;
	Clouds frontClouds1;
	Moon moon;

	Image mouse;

	// camera refrence point
	int cameraRefPoint[] = { 0, 0 };

	int cameraMinY = 400;

	int cameraMinX = -1000;
	int cameraMaxX = 1200;

	int MouseX = 0, MouseY = 0;

	int tool = 0;
	/*
	 * tools: 0: block 1: enemy 2: tree 3: villageHouse 4: villageTower 5:
	 * villageCenter 6: erase 7: moon 8: mountains 9: clouds
	 * 
	 */

	boolean cameraUnderGround = false;

	Player p;

	ArrayList<Platform> solids = new ArrayList<Platform>();

	ArrayList<Entity> decorations = new ArrayList<Entity>();

	ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	ArrayList<Village> villages = new ArrayList<Village>();

	// length of map
	int mapLength = 100;

	int seaLevel = 0;

	// scale of tile for camera
	double tileSize = 1;

	boolean paused = false;

	BufferedImage spriteSheet;

	boolean click = false;

	public void init() {
		try {
			spriteSheet = ImageIO.read(new File("sprites.png"));
		} catch (Exception e) {
		}

		mouse = spriteSheet.getSubimage(86, 12, 6, 6);

		p = new Player(((int) Math.round((int) Math.round(tileSize * -720))
				+ (int) Math.round((tileSize * 60) * (8 / tileSize)) + 10), (int) Math.round(38 * tileSize));

		p.size = tileSize;

		background = new Background(160, -50);

		clouds1 = new Clouds(0, 100);
		clouds2 = new Clouds(100, 140);
		frontClouds1 = new Clouds(0, -50);

		backgroundMountains = new BackgroundMountains(160, 160);

		moon = new Moon(180, 100);

		seaLevel = 120;

		cameraMinY = seaLevel;

		for (int i = -5; i < mapLength; i++) {
			solids.add(new Platform(i * 60, (int) seaLevel, 1, 8, "plains"));
		}
	}

	public void update(Main m) {
		if (!paused) {

			// updates
			p.update(solids, m, enemies);
			background.update();

			for (int i = 0; i < solids.size(); i++) {
				solids.get(i).update(solids, m, p, enemies);
			}

			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).update(solids, m, p, enemies);
			}

			// updates

			// camera
			// camera X
			int centerX = 320 - (int) Math.round(30 * tileSize);
			if (p.x > centerX) {
				for (int i = 0; i < solids.size(); i++) {
					solids.get(i).x -= (p.x - centerX) / 10;
				}
				for (int i = 0; i < decorations.size(); i++) {
					decorations.get(i).x -= (p.x - centerX) / 10;
				}
				for (int i = 0; i < villages.size(); i++) {
					villages.get(i).x -= (p.x - centerX) / 10;
					for(int j = 0; j < villages.get(i).Buildings.size(); j ++){
						villages.get(i).Buildings.get(j).x -= (p.x - centerX) / 10;
					}
				}
				for (int i = 0; i < enemies.size(); i++) {
					enemies.get(i).x -= (p.x - centerX) / 10;
				}
				backgroundMountains.x -= (p.x - centerX) / 100;
				moon.x -= (p.x - centerX) / 1000;
				clouds1.x -= (p.x - centerX) / 50;
				clouds2.x -= (p.x - centerX) / 70;
				frontClouds1.x -= (p.x - centerX) / 7;
				cameraRefPoint[0] -= (p.x - centerX) / 10;
				p.x -= (p.x - centerX) / 10;
			}
			if (p.x < centerX) {
				for (int i = 0; i < solids.size(); i++) {
					solids.get(i).x += (centerX - p.x) / 10;
				}
				for (int i = 0; i < decorations.size(); i++) {
					decorations.get(i).x += (centerX - p.x) / 10;
				}
				for (int i = 0; i < villages.size(); i++) {
					villages.get(i).x += (centerX - p.x) / 10;
					for(int j = 0; j < villages.get(i).Buildings.size(); j ++){
						villages.get(i).Buildings.get(j).x += (centerX - p.x) / 10;
					}
				}
				for (int i = 0; i < enemies.size(); i++) {
					enemies.get(i).x += (centerX - p.x) / 10;
				}
				backgroundMountains.x += (centerX - p.x) / 100;
				moon.x += (centerX - p.x) / 1000;
				clouds1.x += (centerX - p.x) / 50;
				clouds2.x += (centerX - p.x) / 70;
				frontClouds1.x += (centerX - p.x) / 7;
				cameraRefPoint[0] += (centerX - p.x) / 10;
				p.x += (centerX - p.x) / 10;
			}
			if (cameraRefPoint[1] > -150) {
				cameraUnderGround = false;
			} else {
				cameraUnderGround = true;
			}
			// camera Y
			int centerY = 240 - (int) (Math.round(30 * tileSize));
			if (p.y > centerY) {
				if (!cameraUnderGround) {
					for (int i = 0; i < solids.size(); i++) {
						solids.get(i).y -= (p.y - centerY) / 10;
					}
					for (int i = 0; i < decorations.size(); i++) {
						decorations.get(i).y -= (p.y - centerY) / 10;
					}
					for (int i = 0; i < villages.size(); i++) {
						villages.get(i).y -= (p.y - centerY) / 10;
						for(int j = 0; j < villages.get(i).Buildings.size(); j ++){
							villages.get(i).Buildings.get(j).y -= (p.y - centerY) / 10;
						}
					}
					for (int i = 0; i < enemies.size(); i++) {
						enemies.get(i).y -= (p.y - centerY) / 10;
					}
					backgroundMountains.y -= (p.y - centerY) / 100;
					moon.y -= (p.y - centerY) / 1000;
					clouds1.y -= (p.y - centerY) / 50;
					frontClouds1.y -= (p.y - centerY) / 7;
					clouds2.y -= (p.y - centerY) / 70;
					cameraRefPoint[1] -= (p.y - centerY) / 10;
					p.y -= (p.y - centerY) / 10;
				}
			}
			if (p.y < centerY) {
				if (!cameraUnderGround) {
					for (int i = 0; i < solids.size(); i++) {
						solids.get(i).y += (centerY - p.y) / 10;
					}
					for (int i = 0; i < decorations.size(); i++) {
						decorations.get(i).y += (centerY - p.y) / 10;
					}
					for (int i = 0; i < villages.size(); i++) {
						villages.get(i).y += (centerY - p.y) / 10;
						for(int j = 0; j < villages.get(i).Buildings.size(); j ++){
							villages.get(i).Buildings.get(j).y += (centerY - p.y) / 10;
						}
					}
					for (int i = 0; i < enemies.size(); i++) {
						enemies.get(i).y += (centerY - p.y) / 10;
					}
					backgroundMountains.y += (centerY - p.y) / 100;
					moon.y += (centerY - p.y) / 1000;
					clouds1.y += (centerY - p.y) / 50;
					frontClouds1.y += (centerY - p.y) / 7;
					clouds2.y += (centerY - p.y) / 70;
					cameraRefPoint[1] += (centerY - p.y) / 10;
					p.y += (centerY - p.y) / 10;
				}
			}
		}

		if (click) {
			click = false;
			switch (tool) {
			case 0:
				solids.add(new Platform((int) Math.round((Math.round(MouseX / 60) * 60) + solids.get(0).x % 60),
						(int) Math.round((Math.round(MouseY / 60) * 60) + solids.get(0).y % 60), 1, 1, "plains"));
				break;
			case 1:
				enemies.add(new BasicRunner((int) Math.round((Math.round(MouseX / 60) * 60) + solids.get(0).x % 60),
						(int) Math.round((Math.round(MouseY / 60) * 60) + solids.get(0).y % 60)));
				break;
			case 2:
				decorations.add(new Tree((int) Math.round((Math.round(MouseX / 60) * 60) + solids.get(0).x % 60),
						(int) Math.round((Math.round(MouseY / 60) * 60) - 140 + solids.get(0).y % 60), "forest"));
				break;
			case 3:
				decorations
						.add(new VillageHouse((int) Math.round((Math.round(MouseX / 60) * 60) + solids.get(0).x % 60),
								(int) Math.round((Math.round(MouseY / 60) * 60) - 95 + solids.get(0).y % 60)));
				break;
			case 4:
				decorations
						.add(new VillageTower((int) Math.round((Math.round(MouseX / 60) * 60) + solids.get(0).x % 60),
								(int) Math.round((Math.round(MouseY / 60) * 60) - 160 + solids.get(0).y % 60)));
				break;
			case 5:
				decorations
						.add(new VillageCenter((int) Math.round((Math.round(MouseX / 60) * 60) + solids.get(0).x % 60),
								(int) Math.round((Math.round(MouseY / 60) * 60) - 235 + solids.get(0).y % 60)));
				break;
			case 6:
				villages.add(new Village((int) Math.round((Math.round(MouseX / 60) * 60) + solids.get(0).x % 60),
						(int) Math.round((Math.round(MouseY / 60) * 60) + solids.get(0).y % 60)));
				break;

			}
		}

	}

	public void draw(Graphics g) {

		// draw stuff

		background.draw(g);

		moon.draw(g);

		backgroundMountains.draw(g);

		clouds2.draw(g);

		clouds1.draw(g);

		if (!paused) {

			for (int z = 0; z < 10; z++) {
				for (int i = 0; i < decorations.size(); i++) {
					if (decorations.get(i).x > -640 && decorations.get(i).x < 640) {
						if (decorations.get(i).z == z) {
							decorations.get(i).draw(g);
						}
					}
				}
			}

			for (int i = 0; i < solids.size(); i++) {
				if (solids.get(i).x > -60 && solids.get(i).x < 640) {
					if (solids.get(i).type != "water" && solids.get(i).type != "chest") {
						solids.get(i).draw(g);
					}
				}
			}

			for (int i = 0; i < solids.size(); i++) {
				if (solids.get(i).x > -60 && solids.get(i).x < 640) {
					if (solids.get(i).type == "chest") {
						solids.get(i).draw(g);
					}
				}
			}

			for (int i = 0; i < villages.size(); i++) {
				villages.get(i).draw(g);
			}

			for (int i = 0; i < enemies.size(); i++) {
				if (enemies.get(i).x > -60 && enemies.get(i).x < 640) {
					enemies.get(i).draw(g);
				}
			}

			p.draw(g);

			for (int i = 0; i < solids.size(); i++) {
				if (solids.get(i).type == "water") {
					solids.get(i).draw(g);
				}
			}

			frontClouds1.draw(g);

			g.setFont(g.getFont().deriveFont(Font.BOLD, 48));
			g.setColor(Color.white);

			// Add This Later
			/*
			 * if(p.x > ((mapLength * 3) / 4)){ g.drawString("Jungle", 200,
			 * 100); }else if(p.x > ((mapLength * 2) / 4)){
			 * g.drawString("Forest", 200, 100); }else if(p.x > ((mapLength * 1)
			 * / 4)){ g.drawString("Plains", 200, 100); }else{
			 * g.drawString("Beach", 200, 100); }
			 */

		} else {
			g.setFont(g.getFont().deriveFont(Font.BOLD, 48));
			g.setColor(Color.white);
			g.drawString("Paused", 234, 90);
		}

		// draw stuff

		g.drawImage(mouse, (int) Math.round((Math.round(MouseX / 60) * 60) + solids.get(0).x % 60),
				(int) Math.round((Math.round(MouseY / 60) * 60) + solids.get(0).y % 60), 60, 60, null);
	}

	// key input
	public void keyDown(KeyEvent e) {
		p.keyDown(e);
		if (e.getKeyCode() == KeyEvent.VK_X) {
		}
		switch (e.getKeyCode()) {
		case KeyEvent.VK_0:
			tool = 0;
			break;
		case KeyEvent.VK_1:
			tool = 1;
			break;
		case KeyEvent.VK_2:
			tool = 2;
			break;
		case KeyEvent.VK_3:
			tool = 3;
			break;
		case KeyEvent.VK_4:
			tool = 4;
			break;
		case KeyEvent.VK_5:
			tool = 5;
			break;
		case KeyEvent.VK_6:
			tool = 6;
			break;
		case KeyEvent.VK_7:
			tool = 7;
			break;
		case KeyEvent.VK_8:
			tool = 8;
			break;
		case KeyEvent.VK_9:
			tool = 9;
			break;
		}
	}

	public void keyUp(KeyEvent e) {
		p.keyUp(e);
		if (e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			paused = !paused;
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		click = true;
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		click = true;
	}

	public void mouseReleased(MouseEvent e) {
		click = false;
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		MouseX = e.getX();
		MouseY = e.getY();
	}
}
