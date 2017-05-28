package navigation;

import java.util.HashMap;
import java.util.Map;

import tilelogic.LogicTile;
import tilelogic.NullLogicTile;
import tilelogic.StateVector;
import utilities.Coordinate;
import world.World;

/** SWEN30006 Software Modeling and Design
InternalMap class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263
Records a history of previously and currently visible tiles
*/
public class InternalMap {
	private LogicTile[][] map;
	
	public static final int MIN_PASSAGE_WIDTH = 2;
	public static final int EXPLORED = 1;
	public static final int TO_WALL = 2;
	public static final int EXPLORED_JOIN = 1;
	
	public InternalMap() {
		map = new LogicTile[World.MAP_WIDTH][World.MAP_HEIGHT];
	}
	
	/**
	 * Takes the current view of the car and adds it to the internal map
	 * @param mapSegment - current hashmap view
	 */
	public void updateMap(HashMap<Coordinate, LogicTile> mapSegment) {
		for (Map.Entry<Coordinate, LogicTile> tile : mapSegment.entrySet()) {
			if (map[tile.getKey().x][tile.getKey().y] == null) {
				map[tile.getKey().x][tile.getKey().y] = tile.getValue();
			}
		}
	}
	/**
	 * Walls of the map to prevent backtracking
	 * @param carPos - The current position of the car
	 */
	public void wallOff(StateVector carPos) {
		/* Determine how the car should wall of behind it */
		int wallH = 0, wallV = 0;
		if (carPos.angle >= 337 || carPos.angle < 23) {wallH = -1;}
		else if (carPos.angle >= 23 && carPos.angle < 67) {wallH = -1; wallV = -1;}
		else if (carPos.angle >= 67 && carPos.angle < 113) {wallV = -1;}
		else if (carPos.angle >= 113 && carPos.angle < 157) {wallH = 1; wallV = -1;}
		else if (carPos.angle >= 157 && carPos.angle < 203) {wallH = 1;}
		else if (carPos.angle >= 203 && carPos.angle < 247) {wallH = 1; wallV = 1;}
		else if (carPos.angle >= 247 && carPos.angle < 293) {wallV = 1;}
		else if (carPos.angle >= 293 && carPos.angle < 337) {wallH = -1; wallV = 1;}
		
		/* Wall off behind horizontal movement */
		if (wallH != 0) {
			int wallX = carPos.pos.x + wallH;
			int yUp, yDown, checked[][] = new int[World.MAP_WIDTH][World.MAP_HEIGHT];
			yUp = yDown = carPos.pos.y;
			boolean canWall = false;
			/* Check if there is a wall above to wall from */
			while (yUp < World.MAP_HEIGHT && map[wallX][yUp] != null) {
				if (map[wallX][yUp] instanceof NullLogicTile) {
					canWall = true;
					break;
				}
				checked[wallX][yUp] = TO_WALL;
				yUp++;
			}
			/* Check for a wall below */
			if (canWall) {
				canWall = false;
				while (yDown > 0 && map[wallX][yDown] != null) {
					if (map[wallX][yDown] instanceof NullLogicTile) {
						canWall = true;
						break;
					}
				}
				checked[wallX][yDown] = TO_WALL;
				yDown--;
			}
			/* Check if the wall forms a closed shape */
			if (canWall) {
				canWall = closedCheck(wallX, yUp, wallX, yDown, checked);
			}
			/* Check that creating this wall will not block any passages */
			if (canWall) {
				int y, i, x;
				for (i = MIN_PASSAGE_WIDTH; i >= 0; i--) {
					for (y = yDown; y <= yUp; y++) {
						x = wallX -i*wallH;
						if(x > 0 && x < World.MAP_WIDTH && map[x][y] instanceof NullLogicTile && !(joinedCheck(x,y,checked))){
							canWall = false;
							break;
						}
					}
				}
			}
			
			/* If the wall forms a closed shape replace enclosed area with 2 */
			if (canWall) {
				fillAndWall(checked, carPos);
			}
			
			
		}
		
		if (wallV != 0) {
			int wallY = carPos.pos.y + wallV;
			int xUp, xDown, checked[][] = new int[World.MAP_WIDTH][World.MAP_HEIGHT];
			xUp = xDown = carPos.pos.x;
			boolean canWall = false;
			/* Check if there is a wall above to wall from */
			while (xUp < World.MAP_WIDTH && map[wallY][xUp] != null) {
				if (map[wallY][xUp] instanceof NullLogicTile) {
					canWall = true;
					break;
				}
				checked[wallY][xUp] = TO_WALL;
				xUp++;
			}
			/* Check for a wall below */
			if (canWall) {
				canWall = false;
				while (xDown > 0 && map[wallY][xDown] != null) {
					if (map[wallY][xDown] instanceof NullLogicTile) {
						canWall = true;
						break;
					}
				}
				checked[wallY][xDown] = TO_WALL;
				xDown--;
			}
			/* Check if the wall forms a closed shape */
			if (canWall) {
				canWall = closedCheck(wallY, xUp, wallY, xDown, checked);
			}
			/* Check that creating this wall will not block any passages */
			if (canWall) {
				int y, i, x;
				for (i = MIN_PASSAGE_WIDTH; i >= 0; i--) {
					for (x = xDown; x <= xUp; x++) {
						y = wallY -i*wallV;
						if(y > 0 && y < World.MAP_WIDTH && map[x][y] instanceof NullLogicTile && !(joinedCheck(x,y,checked))){
							canWall = false;
							break;
						}
					}
				}
			}
			
			/* If the wall forms a closed shape replace enclosed area with 2 and replace 2 with wall on map*/
			if (canWall) {
				fillAndWall(checked, carPos);
			}
			
			
		}
	}
	
	/**
	 * Checks the input tile can reach the destination tile along walls
	 * @param x - x coord of start
	 * @param y - y coord of start
	 * @param xDest - x coord of destination
	 * @param yDest - y coord of destination
	 * @param checked - int array used to prevent backtracking and check walled areas safety before commiting to map
	 * @return true if coords joined by wall
	 */
	private boolean closedCheck(int x, int y, int xDest, int yDest, int checked[][]) {
		/* If outside map, unknown tile, not wall, already explored return null */
		if (x < 1 || y < 1 || x > World.MAP_WIDTH-2 || y > World.MAP_HEIGHT-2 || map[x][y] == null || 
				!(map[x][y] instanceof NullLogicTile) || checked[x][y] == EXPLORED) {
			return false;
		}
		/* If at destination return true */
		if (x == xDest && y == yDest) {
			checked[x][y] = TO_WALL;
			return true;
		}
		checked[x][y] = EXPLORED;
		/* Otherwise add all adjacent tiles and recursively explore */
		if (closedCheck(x-1, y, xDest, yDest, checked)) {checked[x][y] = TO_WALL; return true;}
		if (closedCheck(x, y-1, xDest, yDest, checked)) {checked[x][y] = TO_WALL; return true;}
		if (closedCheck(x+1, y, xDest, yDest, checked)) {checked[x][y] = TO_WALL; return true;}
		if (closedCheck(x, y+1, xDest, yDest, checked)) {checked[x][y] = TO_WALL; return true;}
		if (closedCheck(x-1, y-1, xDest, yDest, checked)) {checked[x][y] = TO_WALL; return true;}
		if (closedCheck(x+1, y-1, xDest, yDest, checked)) {checked[x][y] = TO_WALL; return true;}
		if (closedCheck(x-1, y+1, xDest, yDest, checked)) {checked[x][y] = TO_WALL; return true;}
		if (closedCheck(x+1, y+1, xDest, yDest, checked)) {checked[x][y] = TO_WALL; return true;}
		
		return false;
		
	}
	
	/**
	 * Check if a wall is joined by walls to a tile marked to be changed to a wall
	 * @param x - x coord of start
	 * @param y - y coord of start
	 * @param checked - The int array marking the tiles to be changed
	 * @return true if connected
	 */
	private boolean joinedCheck(int x, int y, int checked[][]) {
		if (x < 1 || y < 1 || x > World.MAP_WIDTH-2 || y > World.MAP_HEIGHT-2 || map[x][y] == null ||
				!(map[x][y] instanceof NullLogicTile) || checked[x][y] == EXPLORED_JOIN) {
			return false;
		}
		if (checked[x][y] == TO_WALL) {
			return true;
		}
		checked[x][y] = EXPLORED_JOIN;
		if (joinedCheck(x-1, y, checked)) {checked[x][y] = 0; return true;}
		if (joinedCheck(x, y-1, checked)) {checked[x][y] = 0; return true;}
		if (joinedCheck(x+1, y, checked)) {checked[x][y] = 0; return true;}
		if (joinedCheck(x, y+1, checked)) {checked[x][y] = 0; return true;}
		if (joinedCheck(x-1, y-1, checked)) {checked[x][y] = 0; return true;}
		if (joinedCheck(x+1, y-1, checked)) {checked[x][y] = 0; return true;}
		if (joinedCheck(x-1, y+1, checked)) {checked[x][y] = 0; return true;}
		if (joinedCheck(x+1, y+1, checked)) {checked[x][y] = 0; return true;}
		
		return false;
	}
	
	/**
	 * Fills area enclosed by 2s with 2s
	 */
	private void fillTwos(int checked[][]) {
		for (int x = 0; x < World.MAP_WIDTH; x++) {
			for (int y = 0; y < World.MAP_HEIGHT; y++) {
				if(checked[x][y] == 0 && isClosed(checked,x,y)) {
					checked[x][y] = 2;
				}
			}
		}
	}
	
	/**
	 * Checks if a tile is fully surrounded by 2s, not perfect, but required geometry to break is effectively immposible
	 * @param checked - int array of 2 for walls
	 * @param x - x coord of tile
	 * @param y - y coord of tile
	 * @return true if enclosed
	 */
	private boolean isClosed(int checked[][], int x, int y) {
		int xTemp = x, yTemp = y;
		for (xTemp = x; xTemp >= -1; xTemp--) {
			if (xTemp == -1) { return false;}
			if (checked[xTemp][yTemp] == 2) { break; }
		}
		for (xTemp = x; xTemp <= World.MAP_WIDTH; xTemp++) {
			if (xTemp == World.MAP_WIDTH) { return false;}
			if (checked[xTemp][yTemp] == 2) { break; }
		}
		xTemp = x;
		for (yTemp = y; yTemp >= -1; yTemp--) {
			if (yTemp == -1) { return false;}
			if (checked[xTemp][yTemp] == 2) { break; }
		}
		for (yTemp = y; yTemp <= World.MAP_HEIGHT; yTemp++) {
			if (yTemp == World.MAP_HEIGHT) { return false;}
			if (checked[xTemp][yTemp] == 2) { break; }
		}
		return true;
	}
	
	/**
	 * Fills checked with 2s and then overrides with NullLogicTiles on the map if necessary
	 */
	private void fillAndWall(int checked[][], StateVector carPos) {
		fillTwos(checked);
		if (checked[carPos.pos.x][carPos.pos.y] != TO_WALL) {
			for (int x = 0; x < World.MAP_WIDTH; x++) {
				for (int y = 0; y < World.MAP_HEIGHT; y++) {
					if(checked[x][y] == TO_WALL && !(map[x][y] instanceof NullLogicTile)) {
						map[x][y] = new NullLogicTile();
					}
					printTile(map[x][y]); //Used for bug testing
				}
				System.out.print("\n"); // Part of debugging
			}
		}
	}
	
	/**
	 * Prints the tile (used in debugging to print map whenever walls are created
	 */
	private void printTile(LogicTile tile) {
		if (tile == null) {
			System.out.print("-");
		} else if (tile instanceof NullLogicTile) {
			System.out.print("W");
		} else {
			System.out.print(" ");
		}
	}
}