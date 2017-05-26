package navigation;
/** SWEN30006 Software Modeling and Design
PathfinderInterface interface
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263
Provides an interface to allow for different Pathfinders
*/

import java.util.HashMap;

import tilelogic.TileInterpreter;
import utilities.Coordinate;

public interface PathfinderInterface {
	Move findMove(HashMap<Coordinate,TileInterpreter> interpreters);
}
