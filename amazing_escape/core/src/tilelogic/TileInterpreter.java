package tilelogic;

import java.util.HashMap;

import utilities.Coordinate;
/** SWEN30006 Software Modeling and Design
TileInterpreter interface
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Interfaces the methods required to convert the map to LogicTiles
*/
public interface TileInterpreter {
	public int getPriority();
	public HashMap<Coordinate,Object> ProcessMap(HashMap<Coordinate,Object> map);
}
