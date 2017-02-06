package main;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import entity.Entity;

public class Journal extends Entity{

	Image journal;
	
	public Journal(int x, int y) {
		super(x, y);
		try{
			journal = ImageIO.read(new File("journal.png"));
		}catch(Exception e){
			System.exit(1);
		}
	}
	
	public void draw(Graphics g){
		
	}

}
