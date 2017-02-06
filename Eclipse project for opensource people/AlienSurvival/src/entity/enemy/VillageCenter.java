package entity.enemy;

import java.awt.Graphics;
import java.awt.Image;

import entity.Entity;

public class VillageCenter extends Entity{

	public Image centerImage;
	
	public int width = 330, height = 235;
	
	public VillageCenter(int x, int y) {
		super(x, y);
		centerImage = spriteSheet.getSubimage(108, 17, 66, 47);
		z=9;
	}
	
	public void draw(Graphics g){
		g.drawImage(centerImage, (int)x, (int)(y), width, height, null);
	}

}
