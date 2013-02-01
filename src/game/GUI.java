package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JPanel implements KeyListener, MouseListener,
		MouseWheelListener {

	JFrame f = new JFrame("Block Explorer");
	Paint paint;
	Main main;
	Timer paintT = new Timer();

	public GUI(Main main) {
		paint = new Paint(main);
		this.main = main;
	}

	public void drawGUI() {

		paintT.schedule(paintTask, 16, 16);
		f.setSize(765, 535);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.addKeyListener(this);
		f.addMouseListener(this);
		f.addMouseWheelListener(this);
		f.add(paint);
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_A) {
			if (main.playerCanMove == true) {
				main.facing = 1;
				main.direction = 1;
			}
		} else if (key == KeyEvent.VK_D) {
			if (main.playerCanMove == true) {
				main.facing = 2;
				main.direction = 2;
			}
		} else if (key == KeyEvent.VK_SPACE) {

			main.jumped = true;

		} else if (key == KeyEvent.VK_ESCAPE) {

		}

	}

	TimerTask paintTask = new TimerTask() {
		public void run() {
			switch (main.direction) {
			case 1:
				main.pX -= 1;
				break;
			case 2:
				main.pX += 1;
				break;
			}
			main.checkCharacter();
			main.updateWater();
			f.repaint();
		}
	};

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_A) {
			main.direction = 0;
		} else if (key == KeyEvent.VK_D) {
			main.direction = 0;
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {

		int button = e.getButton();

		if (button == MouseEvent.BUTTON1) {

			main.block = (main.board[(e.getX() - 5) / main.blockSize][e.getY()
					/ main.blockSize - 2]);

			main.addInventory();
			main.board[(e.getX() - 5) / main.blockSize][e.getY()
					/ main.blockSize - 2] = 0;
			main.growGrass();
		} else if (button == MouseEvent.BUTTON3) {

			if (main.board[(e.getX() - 5) / main.blockSize][e.getY()
					/ main.blockSize - 2] == 0) {

				if (main.inventoryAmount[main.inventorySelected] != 0) {
					main.board[(e.getX() - 5) / main.blockSize][e.getY()
							/ main.blockSize - 2] = main.inventoryBlock[main.inventorySelected];
					main.inventoryAmount[main.inventorySelected] -= 1;
					if (main.inventoryAmount[main.inventorySelected] == 0) {
						main.inventoryBlock[main.inventorySelected] = 0;
					}
				}

			}
		}

	}

	public void mouseReleased(MouseEvent e) {
		main.block = 0;
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		int direction = e.getWheelRotation();

		if (direction > 0) {
			if (main.inventorySelected == 9) {
				main.inventorySelected = 0;
			} else {
				main.inventorySelected += 1;
			}
		} else if (direction < 0) {
			if (main.inventorySelected == 0) {
				main.inventorySelected = 9;
			} else {
				main.inventorySelected -= 1;
			}
		}
	}
}