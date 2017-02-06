package entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import entity.enemy.BasicRunner;
import main.Main;

public class Enemy extends Entity {

	public double spd = 3;
	public double jmp = 10;
	public double grav = .5;
	
	public double spdStat = 3;
	public double jmpStat = 10;
	public double gravStat = .5;

	// what is the dy/dx of e^x?
	public double dy = 0;
	public double dx = 0;

	public double hp = 1000;
	public double maxHp = 1000;

	public boolean friction = false;

	public boolean onGround = false;

	public boolean left = false;
	public boolean right = false;
	public int animationCounter = 0;
	
	public Image enemyImage;

	protected boolean spawning = true;
	
	public Enemy(int x, int y) {
		super(x, y);
		bounds = new Rectangle(x, y, (int) Math.round(60 * size), (int) Math.round(60 * size));
		id = "enemy";
		enemyImage = spriteSheet.getSubimage(44, 0, 6, 6);
	}

	public void update(ArrayList<Platform> solids, Main m, Player p, ArrayList<Enemy> enemies) {}

	public void draw(Graphics g) {
		// lol satan 6 6 6:
		if(left){
			g.drawImage(enemyImage, (int) x + (int)Math.round(60 * size), (int) y, (int) -Math.round(60 * size),
					(int) Math.round(60 * size), null);
		}
		if(right){
			g.drawImage(enemyImage, (int) x, (int) y, (int) Math.round(60 * size),
					(int) Math.round(60 * size), null);
		}
		g.drawRect((int)x, (int)y, (int)(60*size), (int)(60*size));
	}
}
