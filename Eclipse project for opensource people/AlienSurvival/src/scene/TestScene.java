package scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import entity.Background;
import entity.BackgroundMountains;
import entity.Chest;
import entity.Clouds;
import entity.Enemy;
import entity.Entity;
import entity.Moon;
import entity.Platform;
import entity.Player;
import entity.Tree;
import entity.base.EscapePod;
import entity.enemy.BasicRunner;
import entity.enemy.Plane;
import main.Main;

public class TestScene extends Scene {

	Background background;
	BackgroundMountains backgroundMountains;
	Clouds clouds1;
	Clouds clouds2;
	Clouds frontClouds1;
	Moon moon;

	// camera refrence point
	int cameraRefPoint[] = { 0, 0 };

	int cameraMinY = 400;

	int cameraMinX = -1000;
	int cameraMaxX = 1200;

	int villageSize = 1;

	int treeDensity = 3;

	boolean cameraUnderGround = false;

	boolean overEscapePod = false;

	Player p;

	ArrayList<Platform> solids = new ArrayList<Platform>();

	ArrayList<Entity> decorations = new ArrayList<Entity>();

	ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	// length of map
	int mapLength = 100;

	int enemySpawnCounter = (int) (Math.random() * 100);

	// fist platform y location
	int seed = Math.abs((int) ((Math.random() * 240) + 120) / 60) * 60;

	int seaLevel = 0;

	// scale of tile for camera
	double tileSize = 1;

	int difficulty = 1;

	// rhythmMap for level gen
	// rhythm map 0 => no platform
	// rhythm map 1 => platform
	int[] rhythmMap = new int[mapLength];

	// pause system
	boolean paused = false;

	Rectangle escapePodBounds;

	int EscapePodIndex = 0;

	public void init() {
		System.out.println("begin level gen");

		p = new Player(((int) Math.round((int) Math.round(tileSize * -720))
				+ (int) Math.round((tileSize * 60) * (8 / tileSize)) + 10), (int) Math.round(38 * tileSize));

		p.size = tileSize;

		background = new Background(160, -50);

		clouds1 = new Clouds(0, 100);
		clouds2 = new Clouds(100, 140);
		frontClouds1 = new Clouds(0, -50);

		backgroundMountains = new BackgroundMountains(160, 160);

		moon = new Moon(180, 100);

		decorations.add(new EscapePod((int) Math.round(p.x), (int) Math.round(seed - (60 * tileSize))));
		EscapePodIndex = decorations.size() - 1;
		escapePodBounds = new Rectangle((int) decorations.get(EscapePodIndex).x,
				(int) decorations.get(EscapePodIndex).y, 240, 120);

		// water

		seaLevel = seed + 60;

		cameraMinY = seaLevel;

		// left
		solids.add(new Platform((int) Math.round((p.x + 50 / tileSize) - (12 * 60 / tileSize)), seaLevel,
				(int) Math.round((12 / tileSize)), (int) Math.round(6 / tileSize), "water"));
		// right
		solids.add(new Platform((int) Math.round((p.x + 50 / tileSize) + (mapLength - 1) * 60), seaLevel,
				(int) Math.round((12 / tileSize)), (int) Math.round(6 / tileSize), "water"));

		// rhythm map
		for (int i = 0; i < mapLength; i++) {
			int nextRandomForMap = (int) Math.round(Math.random() * 2);
			if (nextRandomForMap > 1) {
				nextRandomForMap = 1;
			}
			rhythmMap[i] = nextRandomForMap;
		}

		// smoothing level
		for (int i = 1; i < mapLength - 1; i++) {
			if ((rhythmMap[i - 1] != rhythmMap[i]) && (rhythmMap[i + 1] != rhythmMap[i])
					&& (rhythmMap[i - 1] == rhythmMap[i + 1])) {
				rhythmMap[i] = rhythmMap[i - 1];
			}
		}
		// smoothing level

		for (int times = 0; times < 10; times++) {

			// checking for unjumpables
			int commons = 0;
			for (int i = 1; i < mapLength; i++) {
				if (rhythmMap[i] == rhythmMap[i - 1]) {
					commons++;
				}
				if (commons >= 2) {
					for (int j = i; j < mapLength; j++) {
						int nextRandomForMap = (int) Math.round(Math.random() * 2);
						if (nextRandomForMap > 1) {
							nextRandomForMap = 1;
						}
						rhythmMap[i] = nextRandomForMap;
					}
					commons = 0;
				}
			}
			// checking for unjumpables
		}

		// smoothing level
		for (int i = 1; i < mapLength - 1; i++) {
			if ((rhythmMap[i - 1] != rhythmMap[i]) && (rhythmMap[i + 1] != rhythmMap[i])
					&& (rhythmMap[i - 1] == rhythmMap[i + 1])) {
				rhythmMap[i] = rhythmMap[i - 1];
			}
		}
		// smoothing level

		// final checking for unjumpables
		int commons = 0;
		for (int i = 1; i < mapLength; i++) {
			if (rhythmMap[i] == rhythmMap[i - 1]) {
				commons++;
			}
			if (commons >= 3) {
				if (rhythmMap[i] == 0) {
					rhythmMap[i] = 1;
				}
				commons = 0;
			}
		}
		// final checking for unjumpables

		// smoothing level
		for (int i = 1; i < mapLength - 1; i++) {
			if (rhythmMap[i - 1] == 1 && rhythmMap[i + 1] == 1) {
				rhythmMap[i] = 1;
			}
		}
		// smoothing level

		// rhythm map

		// place stuff
		boolean blockPlaced = false;
		for (int i = 0; i < mapLength; i++) {
			blockPlaced = false;
			// place platform

			// rhythm map 0 => no platform
			// rhythm map 1 => platform
			if (i < ((mapLength / 16) + 1)) {
				rhythmMap[i] = 1;
				if (seed > seaLevel) {
					seed -= (((Math.round(Math.random())) * (int) Math.round(30 * tileSize)));
				} else {
					seed += (((Math.round(Math.random())) * (int) Math.round(30 * tileSize)));
				}
			}

			if (i > (((15 * mapLength) / 16) - 1)) {
				rhythmMap[i] = 1;
				if (seed > seaLevel) {
					seed -= (((Math.round(Math.random())) * (int) Math.round(30 * tileSize)));
				} else {
					seed += (((Math.round(Math.random())) * (int) Math.round(30 * tileSize)));
				}
			}

			if (rhythmMap[i] == 1) {
				// place platforms
				blockPlaced = true;

				if (i > ((mapLength * 15) / 16)) {
					// beach biome

					solids.add(
							new Platform(-((int) Math.round(60 * tileSize) * 4) + i * (int) Math.round(60 * tileSize),
									(Math.round(seed / (int) Math.round(60 * tileSize))
											* (int) Math.round(60 * tileSize)),
									1, (int) Math.round(8 / tileSize), "beach"));

				} else if (i > ((mapLength * 7) / 8)) {
					// plains biome

					solids.add(
							new Platform(-((int) Math.round(60 * tileSize) * 4) + i * (int) Math.round(60 * tileSize),
									(Math.round(seed / (int) Math.round(60 * tileSize))
											* (int) Math.round(60 * tileSize)),
									1, (int) Math.round(8 / tileSize), "plains"));

					decorations
							.add(new Tree(
									-((int) Math.round(60 * tileSize) * 4) + i
											* (int) Math.round(60 * tileSize),
							(Math.round(seed / (int) Math.round(60 * tileSize)) * (int) Math.round(60 * tileSize))
									- 140, "plains"));

				} else if (i > ((mapLength * 3) / 4)) {
					// forest biome

					solids.add(
							new Platform(-((int) Math.round(60 * tileSize) * 4) + i * (int) Math.round(60 * tileSize),
									(Math.round(seed / (int) Math.round(60 * tileSize))
											* (int) Math.round(60 * tileSize)),
									1, (int) Math.round(8 / tileSize), "forest"));

					decorations
							.add(new Tree(
									-((int) Math.round(60 * tileSize) * 4) + i
											* (int) Math.round(60 * tileSize),
							(Math.round(seed / (int) Math.round(60 * tileSize)) * (int) Math.round(60 * tileSize))
									- 140, "forest"));

				} else if (i > ((mapLength * 1) / 4)) {
					// jungle biome

					solids.add(
							new Platform(-((int) Math.round(60 * tileSize) * 4) + i * (int) Math.round(60 * tileSize),
									(Math.round(seed / (int) Math.round(60 * tileSize))
											* (int) Math.round(60 * tileSize)),
									1, (int) Math.round(8 / tileSize), "jungle"));

					decorations
							.add(new Tree(
									-((int) Math.round(60 * tileSize) * 4) + i
											* (int) Math.round(60 * tileSize),
							(Math.round(seed / (int) Math.round(60 * tileSize)) * (int) Math.round(60 * tileSize))
									- 140, "jungle"));

				} else if (i > ((mapLength * 1) / 8)) {
					// forest biome

					solids.add(
							new Platform(-((int) Math.round(60 * tileSize) * 4) + i * (int) Math.round(60 * tileSize),
									(Math.round(seed / (int) Math.round(60 * tileSize))
											* (int) Math.round(60 * tileSize)),
									1, (int) Math.round(8 / tileSize), "forest"));

					decorations
							.add(new Tree(
									-((int) Math.round(60 * tileSize) * 4) + i
											* (int) Math.round(60 * tileSize),
							(Math.round(seed / (int) Math.round(60 * tileSize)) * (int) Math.round(60 * tileSize))
									- 140, "forest"));

				} else if (i > ((mapLength * 1) / 16)) {
					// plains biome

					solids.add(
							new Platform(-((int) Math.round(60 * tileSize) * 4) + i * (int) Math.round(60 * tileSize),
									(Math.round(seed / (int) Math.round(60 * tileSize))
											* (int) Math.round(60 * tileSize)),
									1, (int) Math.round(8 / tileSize), "plains"));

					decorations
							.add(new Tree(
									-((int) Math.round(60 * tileSize) * 4) + i
											* (int) Math.round(60 * tileSize),
							(Math.round(seed / (int) Math.round(60 * tileSize)) * (int) Math.round(60 * tileSize))
									- 140, "plains"));

				} else if (i > ((mapLength * 0) / 32)) {
					// beach biome

					solids.add(
							new Platform(-((int) Math.round(60 * tileSize) * 4) + i * (int) Math.round(60 * tileSize),
									(Math.round(seed / (int) Math.round(60 * tileSize))
											* (int) Math.round(60 * tileSize)),
									1, (int) Math.round(8 / tileSize), "beach"));

				} // place chests

				if (Math.round(Math.random() * 100) <= 4) {
					solids.add(
							new Chest(-((int) Math.round(60 * tileSize) * 4) + i * (int) Math.round(60 * tileSize),
									(Math.round(seed / (int) Math.round(60 * tileSize))
											* (int) Math.round(60 * tileSize)) - (int) Math.round(60 * tileSize),
							"chest"));
				}
			}

			// reroll seed for next platform
			do {
				if ((int) (Math.random() * 5) == 1) {
					if ((i + 1) < mapLength) {
						if (rhythmMap[i + 1] != 0) {
							seed += (((Math.round(Math.random())) * (int) Math.round(60 * tileSize) * 2)
									- (int) Math.round(60 * tileSize));
						} else {
							seed += (((Math.round(Math.random())) * (int) Math.round(60 * tileSize) / 3)
									- (int) Math.round(60 * tileSize) / 6);
						}
					}
				}
			} while (seed > (int) Math.round(60 * tileSize) * 7 || seed < (int) Math.round(60 * tileSize) * 3);
			// end reroll seed for next platform
			blockPlaced = false;
		}
		// place stuff

		// scale stuff
		for (

		int i = 0; i < solids.size(); i++)

		{
			solids.get(i).size = tileSize;
		}
		for (

		int i = 0; i < decorations.size(); i++)

		{
			if (decorations.get(i).id == "tree") {
				decorations.get(i).size = tileSize - ((Math.random() / 2) * 1 / tileSize);
			} else {
				decorations.get(i).size = tileSize;
			}
		}
		// random level gen

		System.out.println("finished level gen");

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
			if (p.bounds.getBounds().intersects(escapePodBounds.getBounds())) {
				overEscapePod = true;
			} else {
				overEscapePod = false;
			}
			// camera

			// spawn enemies
			enemySpawnCounter++;
			if (enemySpawnCounter >= 500) {
				enemySpawnCounter = (int) (Math.random() * 300);
				enemies.add(new BasicRunner((int) p.x, (int) p.y));
				if(Math.round(Math.random()*5) == 1){
					enemies.add(new Plane(-1000, (int)p.y-200));
				}
				if(Math.round(Math.random()*5) == 2){
					enemies.add(new Plane(5000, (int)p.y-200));
				}
			}
			escapePodBounds = new Rectangle((int) decorations.get(EscapePodIndex).x,
					(int) decorations.get(EscapePodIndex).y, 240, 120);
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
					if (decorations.get(i).x > -200 && decorations.get(i).x < 640) {
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
	}

	// key input
	public void keyDown(KeyEvent e) {
		p.keyDown(e);
		if (e.getKeyCode() == KeyEvent.VK_X) {
			if (overEscapePod) {
				changeScene = true;
				nextScene = new InsideEscapePod();
			}
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
}
