package interpreters;

import java.util.HashMap;
import java.util.Map;

import tilelogic.LogicTile;
import tilelogic.NullLogicTile;
import tilelogic.OpenLogicTile;
import tiles.MapTile;
import utilities.Coordinate;

/** SWEN30006 Software Modeling and Design
BasicInterpreter class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Must be the final interpreter applied to the HashMap, first creates wall tiles, then sets all other non-logic
tiles to open, maintaining their name
*/
public class BasicInterpreter  implements TileInterpreter {

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * This ProcessMap must be called last as it will override all remaining traps with open space. This allows for
	 * the program to run with unrecognized traps, but will override known traps if called first
	 */
	@Override
	public HashMap<Coordinate, Object> ProcessMap(HashMap<Coordinate, Object> map) {
		for (Map.Entry<Coordinate, Object> tile : map.entrySet()) {
			if (tile.getValue() instanceof MapTile) {
				if (((MapTile)tile.getValue()).getName() == "Wall") {
					map.replace(tile.getKey(), tile.getValue(), new NullLogicTile(LogicTile.WALL));
				}
				else {
					// Name is used to recognize end tiles, etc.
					map.replace(tile.getKey(), tile.getValue(), new OpenLogicTile(((MapTile)tile.getValue()).getName()));
				}
				
			}
		}
		return map;
	}

}
