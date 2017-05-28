package tilelogic;

import java.util.HashMap;
import java.util.Map;

import tiles.GrassTrap;
import utilities.Coordinate;

/** SWEN30006 Software Modeling and Design
GrassInterpreter class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Converts GrassTrap to GrassLogicTile in HashMap
*/
public class GrassInterpreter implements TileInterpreter {

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public HashMap<Coordinate, Object> ProcessMap(HashMap<Coordinate, Object> map) {
		for (Map.Entry<Coordinate, Object> tile : map.entrySet()) {
			if (tile.getValue() instanceof GrassTrap) {
				map.replace(tile.getKey(), tile.getValue(), new GrassLogicTile(tile.getKey()));
			}
		}
		return map;
	}

}
