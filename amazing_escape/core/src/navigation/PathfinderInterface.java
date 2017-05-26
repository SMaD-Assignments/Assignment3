package navigation;

import java.util.HashMap;

import tilelogic.LogicTile;
import utilities.Coordinate;

/** SWEN30006 Software Modeling and Design
PathfinderInterface interface
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Provides an interface to allow for different Pathfinders
*/
public interface PathfinderInterface {
	public Move findMove(HashMap<Coordinate, LogicTile> map);
}
