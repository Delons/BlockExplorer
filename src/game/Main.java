package game;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

	int[][] board;
	int[] inventoryBlock, inventoryAmount;
	int inventorySelected, block;
	int blockSize = 15;
	int pX = 0, pY = 0, direction, facing;
	boolean playerPlaced, jumped, down, playerCanMove = true;
	Timer jump = new Timer();
	int jumpY;
	int lastTime, currentTime, fps;

	Random r = new Random();

	// 15 Blocks on X Axis - Each Block 50 Blocks Wide - 750 Total
	// 10 Blocks on Y Axis = Each Block 50 Blocks Tall - 500

	public static void main(String[] args) {
		Main main = new Main();
		main.startGame();

	}

	public void startGame() {
		inventoryBlock = new int[10];
		inventoryAmount = new int[10];
		generateWorld();
		GUI gui = new GUI(this);
		gui.drawGUI();
		jump.schedule(jumpTask, 20, 20);
	}

	public void generateWorld() {

		// INITALIZE THE BOARD ARRAY
		board = new int[750 / blockSize][500 / blockSize];

		// VARIABLES TO MAKE THE COSE SHORTER
		int segmentY = 500 / blockSize;
		int temp;

		// 0 = Air 1 = Grass 2 = Dirt

		// LOOP THROUGH EVERY BLOCK
		for (int y = 0; y < segmentY; y++) {
			for (int x = 0; x < 750 / blockSize; x++) {

				// DIVIDE THE BOARD UP INTO 4 SECTIONS

				if (y < segmentY / 4) {
					// GENERATE SKY
					board[x][y] = 0;

				} else if (y < segmentY / 2) {

					if (board[x][y] == 0) {
						// GENERATE GRASS
						temp = r.nextInt(10);

						if (temp < 5) {
							board[x][y] = 0;
						} else if (temp < 10) {
							board[x][y] = 2;
						}
					}

					if (board[x][y] == 2 && board[x][y - 1] != 2) {
						board[x][y - 1] = 1;
					}

					if (board[x][y] == 2 || board[x][y] == 3 && board[x][y + 1] != 2) {
						temp = r.nextInt(10);
						if (temp < 7) {
							board[x][y + 1] = 2;
						} else {
							board[x][y + 1] = 3;
						}
					}

				} else if (y < segmentY * .75) {

					// GENERATE MIDDLE
					temp = r.nextInt(10);

					if (temp < 7) {
						board[x][y] = 2;
					} else {
						board[x][y] = 3;
					}

				} else if (y < segmentY * .85) {
					// SMOOTH MIDDLE AND BOTTOM

					temp = r.nextInt(10);

					if (temp < 4) {
						board[x][y] = 3;
					} else {
						board[x][y] = 2;
					}

				} else {

					// GENERATE BOTTOM

					temp = r.nextInt(15);

					if (temp < 7) {
						board[x][y] = 3;
					} else {
						board[x][y] = 2;
					}

				}
			}
		}
			
		
	
		// LAKES

		// int startingDepth = r.nextInt(500 / blockSize / 4), width =
		// r.nextInt(750 / blockSize / 4), depth = r.nextInt(5), startingWidth =
		// r.nextInt(750 / blockSize / 2);

		// for(int y = startingDepth; y < startingDepth + depth + 1; y++){
		// for(int x = startingWidth; x < startingWidth + width + 1; x++){
		// board[x][y] = 4;
		// }
		// }
		
		growGrass();

	}

	public void growGrass() {
		for (int y = 0; y < 500 / blockSize; y++) {
			for (int x = 0; x < 750 / blockSize; x++) {
				if (board[x][y] == 2 && board[x][y - 1] == 0) {
					board[x][y] = 1;
				}
				if (board[x][y] == 1 && board[x][y - 1] != 0) {
					board[x][y] = 2;
				}
			}
		}
	}

	public void addInventory() {

		GUI gui = new GUI(this);
		for (int s = 0; s < 10; s++) {

			if (inventoryBlock[s] == block && inventoryAmount[s] < 99
					&& block != 0) {
				inventoryAmount[s] += 1;
				break;
			} else if (inventoryBlock[s] == 0 && block != 0) {
				inventoryBlock[s] = block;
				inventoryAmount[s] += 1;
				break;
			}
		}

	}

	public void checkCharacter() {
		if (board[pX / blockSize][pY / blockSize + 2] == 0 && jumped == false) {
			pY += 1;
		}

		if (board[pX / blockSize][pY / blockSize] != 0) {
			switch (direction) {
			case 1:
				pX += 1;
				break;
			case 2:
				pX -= 1;
				break;
			}
		}

	}

	public void updateWater() {
		for (int y = 0; y < 500 / blockSize; y++) {
			for (int x = 0; x < 750 / blockSize; x++) {
				// if(board[x][y] == 4){
				// if(board[x - 1][y] == 0){
				// board[x - 1][y] = 4;
				// board[x][y] = 0;
				// }else if(board[x + 1][y] == 4){
				// board[x + 1][y] = 4;
				// board[x][y] = 0;
				// }else if(board[x][y + 1] == 0){
				// board[x][y + 1] = 4;
				// board[x][y] = 1;
				// }
				// }
			}
		}

	}

	TimerTask jumpTask = new TimerTask() {
		public void run() {

			if (jumped == true) {
				if (jumpY < blockSize * 1.5 + 1 && down == false) {
					pY -= 1;
					jumpY += 1;
				} else if (jumpY != 0) {
					if (board[pX / blockSize][pY / blockSize + 2] != 0) {
						pY -= 1;
						jumpY = 0;
						jumped = false;
					}

					down = true;
					pY += 1;
					jumpY -= 1;
				} else {
					jumped = false;
				}
			} else {
				jumpY = 0;
				down = false;
			}
		}
	};

}