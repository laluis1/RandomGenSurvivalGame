package scene;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import entity.Enemy;
import entity.Entity;
import entity.Platform;
import entity.Player;
import main.Main;

public class InsideEscapePod extends Scene {
	Player p;

	ArrayList<Platform> solids = new ArrayList<Platform>();

	ArrayList<Entity> decorations = new ArrayList<Entity>();

	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	Image door;

	boolean overDoor = false;
	
	int size = 2;

	public void init() {
		p = new Player(320 - size * 30, 240 - size * 30);
		p.size = size;

		solids.add(new Platform(0, 440, 6, 1, "metal"));
		
		try{
			door = ((p).spriteSheet).getSubimage(68, 66, 6, 12);
		}catch(Exception e){};

		for (int i = 0; i < solids.size(); i++) {
			solids.get(i).size = size;
		}
	}

	public void update(Main m) {
		p.update(solids, m, enemies);
		
		for (int i = 0; i < solids.size(); i++) {
			solids.get(i).update(solids, m, p, enemies);
		}
		if(p.bounds.getBounds().intersects((new Rectangle(60, 200, 60*size, 120*size)).getBounds())){
			overDoor = true;
		}else{
			overDoor = false;
		}
	}

	public void draw(Graphics g) {
		
		g.drawImage(door, 60, 200, 60*size, 120*size, null);
		
		p.draw(g);
		
		for (int i = 0; i < solids.size(); i++) {
			solids.get(i).draw(g);
		}
	}

	public void keyDown(KeyEvent e) {
		p.keyDown(e);
		if (e.getKeyCode() == KeyEvent.VK_X) {
			if (overDoor) {
				changeScene = true;
				nextScene = new TestScene();
			}
		}
	}

	public void keyUp(KeyEvent e) {
		p.keyUp(e);
	}

	public void keyTyped(KeyEvent e) {

	}
}
