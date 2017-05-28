package tilelogic;

import java.util.HashMap;
import java.util.Map;

import tiles.LavaTrap;
import utilities.Coordinate;
/** SWEN30006 Software Modeling and Design
LavaInterpreter class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Converts the LavaTraps to LavaLogicTiles in the HashMap
*/
public class LavaInterpreter implements TileInterpreter {

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public HashMap<Coordinate, Object> ProcessMap(HashMap<Coordinate, Object> map) {
		for (Map.Entry<Coordinate, Object> tile : map.entrySet()) {
			if (tile.getValue() instanceof LavaTrap) {
				map.replace(tile.getKey(), tile.getValue(), new LavaLogicTile(tile.getKey()));
			}
		}
		return map;
	}

}
