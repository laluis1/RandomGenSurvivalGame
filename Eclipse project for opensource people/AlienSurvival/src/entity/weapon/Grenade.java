package entity.weapon;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import entity.Enemy;
import entity.Entity;
import entity.Platform;
import main.Main;

public class Grenade extends Entity{
	int dy = 0, dx = 0;
	
	public Grenade(int x, int y, int dy, int dx){
		super(x, y);
		
		this.dy = dy;
		this.dx = dx;
	}
	
	public Grenade(int x, int y){
		super(x, y);
	}

	public void update(ArrayList<Platform> solids, Main m, ArrayList<Enemy> enemies) {

		// physics
		if(dy < 10){
			dy++;
		}
		
		x += dx;
		bounds = new Rectangle((int) x, (int) y, (int) Math.round(10 * size), (int) Math.round(10 * size));
		for (int i = 0; i < solids.size(); i++) {
			if (solids.get(i).bounded == true) {
				if (bounds.getBounds().intersects(solids.get(i).bounds.getBounds())) {
					x -= dx;
					x -= dx / Math.abs(dx + 100);
					dx /= 4;
					dx = (int) dx;
				}
			}
		}

		y += dy;
		bounds = new Rectangle((int) x, (int) y, (int) Math.round(10 * size), (int) Math.round(10 * size));
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
	}
	
	public void draw(Graphics g){
		g.fillRect((int)Math.round(x), (int)Math.round(y), (int)Math.round(10*size), (int)Math.round(10*size));
	}
	
}
