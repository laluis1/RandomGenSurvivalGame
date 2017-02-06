package entity.enemy;

import java.awt.Graphics;
import java.awt.Image;

import entity.Entity;

public class VillageHouse extends Entity{

	public Image houseImage;
	
	public int width = 110, height = 95;
	
	public VillageHouse(int x, int y) {
		super(x, y);
		houseImage = spriteSheet.getSubimage(64, 45, 22, 19);
		z=9;
	}
	
	public void draw(Graphics g){
		g.drawImage(houseImage, (int)x, (int)(y), width, height, null);
	}
	
}
