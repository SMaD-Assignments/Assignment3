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
	
	public InternalMap() {
		map = new LogicTile[World.MAP_WIDTH][World.MAP_HEIGHT];
	}
	
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
				checked[wallX][yUp] = 2;
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
				checked[wallX][yDown] = 2;
				yDown--;
			}
			/* Check if the wall forms a closed shap */
			if (canWall) {
				canWall = closedCheck(wallX, yUp, wallX, yDown, checked);
			}
			
			/* If the wall forms a closed shape replace enclosed area with 2 */
			if (canWall) {
				fillTwos(checked);
				/* And if the car is not enclosed wall off the area */
				if (checked[carPos.pos.x][carPos.pos.y] != 2) {
					for (int x = 0; x < World.MAP_WIDTH; x++) {
						for (int y = 0; y < World.MAP_HEIGHT; y++) {
							if(checked[x][y] == 2) {
								map[x][y] = new NullLogicTile();
							}
						}
					}
				}
			}
			
			
		}
	}
	
	private boolean closedCheck(int x, int y, int xDest, int yDest, int checked[][]) {
		if (x < 1 || y < 1 || x > World.MAP_WIDTH-2 || y > World.MAP_HEIGHT-2 || map[x][y] == null || checked[x][y] == 0) {
			return false;
		}
		if (x == xDest && y == yDest) {
			checked[x][y] = 2;
			return true;
		}
		checked[x][y] = 1;
		if (closedCheck(x-1, y, xDest, yDest, checked)) {checked[x][y] = 2; return true;}
		if (closedCheck(x, y-1, xDest, yDest, checked)) {checked[x][y] = 2; return true;}
		if (closedCheck(x+1, y, xDest, yDest, checked)) {checked[x][y] = 2; return true;}
		if (closedCheck(x, y+1, xDest, yDest, checked)) {checked[x][y] = 2; return true;}
		if (closedCheck(x-1, y-1, xDest, yDest, checked)) {checked[x][y] = 2; return true;}
		if (closedCheck(x+1, y-1, xDest, yDest, checked)) {checked[x][y] = 2; return true;}
		if (closedCheck(x-1, y+1, xDest, yDest, checked)) {checked[x][y] = 2; return true;}
		if (closedCheck(x+1, y+1, xDest, yDest, checked)) {checked[x][y] = 2; return true;}
		
		return false;
		
	}
	private void fillTwos(int checked[][]) {
		for (int x = 0; x < World.MAP_WIDTH; x++) {
			for (int y = 0; y < World.MAP_HEIGHT; y++) {
				if(checked[x][y] == 0 && isClosed(checked,x,y)) {
					checked[x][y] = 2;
				}
			}
		}
	}
	
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
}
