package tilelogic;

import utilities.Coordinate;
import world.WorldSpatial;

/** SWEN30006 Software Modeling and Design
LogicTile interface
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Provides the general methods that allow for all traps to be generalised
*/
public interface LogicTile {

	// priority values for various instances of tiles
	int OPEN = 0;
	int WALL = -10;
	int GRASS = -1;
	int MUD = -2;
	int LAVA = -3;

	int getPriority();
	Coordinate getPosition();
	void effect(WorldSpatial.Direction in, WorldSpatial.Direction out);
	StateVector getInVector();
	StateVector getOutVector();
	void setInVector(StateVector vector);
	void setOutVector(StateVector vector);

}
