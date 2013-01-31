package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Paint extends JPanel {

	Main main;
	int dX, dY;
	private BufferedImage image;

	public Paint(Main main) {
		this.main = main;
	}

	public void paintComponent(Graphics g) {
		main.lastTime = main.currentTime;
		main.currentTime = (int) System.currentTimeMillis();
		main.fps = 1000 / (main.currentTime - main.lastTime);
		dX = 0;
		dY = 0;
		super.paintComponent(g);
	
		for (int y = 0; y < 500 / main.blockSize; y++) {
			for (int x = 0; x < 750 / main.blockSize; x++) {

				if (dX == 750) {
					dX = 0;
					dY += main.blockSize;
				}

				switch (main.board[x][y]) {
				case 0:

					if (main.playerPlaced == false && y > 0 && main.board[x][y - 1] == 0 && main.board[x][y + 1] == 1) {
						main.pX  = dX + main.blockSize / 2;
						main.pY = dY;
						main.playerPlaced = true;
					}

					g.setColor(new Color(154, 213, 248));
					g.fillRect(dX, dY, main.blockSize, main.blockSize);
					break;
				case 1:
					g.setColor(new Color(89, 131, 44));
					g.fillRect(dX, dY, main.blockSize, main.blockSize);
					break;
				case 2:
					g.setColor(new Color(96, 73, 49));
					g.fillRect(dX, dY, main.blockSize, main.blockSize);
					break;
				case 3:
					g.setColor(new Color(120, 120, 120));
					g.fillRect(dX, dY, main.blockSize, main.blockSize);
					break;
				case 4:
					g.setColor(new Color(84, 122, 204));
					g.fillRect(dX, dY, main.blockSize, main.blockSize);
					break;
				}

				dX += main.blockSize;

			}
		}

		// DRAW INVENTORY
		int pos;
		pos = main.blockSize / 2;
		for (int x = 0; x < 10; x++) {
			
			if (main.inventoryAmount[x] != 0){
				g.setColor(new Color(255, 255, 255));
				g.drawString("" + main.inventoryAmount[x], pos + 5, 37);
			}
			
			switch(main.inventoryBlock[x]){
			case 1:
				g.setColor(new Color(89, 131, 44));
				g.fillRect(pos + 3, 8, main.blockSize, main.blockSize);
				break;
			case 2:
				g.setColor(new Color(96, 73, 49));
				g.fillRect(pos + 3, 8, main.blockSize, main.blockSize);
				break;
			case 3:
				g.setColor(new Color(120, 120, 120));
				g.fillRect(pos + 3, 8, main.blockSize, main.blockSize);
				break;
			case 4:
				g.setColor(new Color(84, 122, 204));
				g.fillRect(pos + 3, 8, main.blockSize, main.blockSize);
				break;	
			}
			
			if (main.inventorySelected == x) {
				g.setColor(new Color(224, 27, 106));
				g.drawRect(pos, 5, main.blockSize + 5, main.blockSize + 5);
				pos += main.blockSize * 1.5;
			} else {
				g.setColor(new Color(255, 255, 255));
				g.drawRect(pos, 5, main.blockSize + 5, main.blockSize + 5);
				pos += main.blockSize * 1.5 + 1;
			}

		}

		//DRAW PLAYER
		
		int pHeight = main.blockSize * 2, pWidth = 0;
		if(main.facing == 1){
			pWidth = main.blockSize / 4 + main.blockSize / 2;
		}else{
			pWidth = -1 * (main.blockSize / 4 + main.blockSize / 2);

		}
		
		 BufferedImage player;
		try {
			player = ImageIO.read(new File("src/resource/image/player.PNG"));
			 g.drawImage(player, main.pX, main.pY, pWidth, pHeight, this);
		} catch (IOException e) {
		
		}
		
		//DRAW FPS AND COORIDNATES
		
		g.setColor(new Color(255, 255, 255));
		g.drawString("FPS: " + main.fps, 700, 12);
		g.drawString("X: " + main.pX / main.blockSize , 700, 24);
		g.drawString("Y: " + (main.pY / main.blockSize + 2), 700, 36);
	
	}

}
