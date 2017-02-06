package entity.enemy;

import java.awt.Graphics;
import java.util.ArrayList;

import entity.Entity;

public class Village extends Entity{
	
	public ArrayList<Entity> Buildings = new ArrayList<Entity>();
	
	int width = (int)Math.round(Math.random()*600);
	
	public Village(int x, int y){
		super(x, y);
		for(int i = 0; i < width; i += 100){
			if(Math.round(Math.random()*3) == 1){ 
				Buildings.add(new VillageHouse(x + i, y-95));
			}else if(Math.round(Math.random()*3) == 1){
				Buildings.add(new VillageTower(x + i, y-160));
			}
		}
	}
	
	public void update(){
		
	}
	
	public void draw(Graphics g){
		for(int i = 0; i < Buildings.size(); i++){
			Buildings.get(i).draw(g);
		}
	}
	
}
