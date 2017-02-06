package scene;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import main.Main;

public class MainMenu extends Scene {
	
	BufferedImage SpriteSheet;
	boolean start = false, debug = false;
	
	public void init() {
		try{
			SpriteSheet = ImageIO.read(new File("sprites.png"));
		}catch(Exception e){}
	}

	public void update(Main m) {
		if(start){
			nextScene = new TestScene();
			changeScene = true;
		}
		if(debug){
			nextScene = new BlankWorld();
			changeScene = true;
		}
	}

	public void draw(Graphics g) {
		g.drawImage(SpriteSheet.getSubimage(64, 91, 39, 37), (640-(390/2))/2, 100, 390/2, 370/2, null);
		g.drawImage(SpriteSheet.getSubimage(64, 78, 46, 13), (640-(460/2))/2, 300, 460/2, 130/2, null);
	}

	public void keyDown(KeyEvent e) {
		if(e.getKeyCode() == 66){
			debug = true;
		}else{
			start = true;
		}
	}

	public void keyUp(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

}
