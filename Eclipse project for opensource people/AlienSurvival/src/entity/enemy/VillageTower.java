package entity.enemy;

import java.awt.Graphics;
import java.awt.Image;

import entity.Entity;

public class VillageTower extends Entity{

	public Image towerImage;
	
	public int width = 110, height = 160;
	
	public VillageTower(int x, int y) {
		super(x, y);
		towerImage = spriteSheet.getSubimage(86, 32, 22, 32);
		z=9;
	}
	
	public void draw(Graphics g){
		g.drawImage(towerImage, (int)x, (int)(y), width, height, null);
	}

}
