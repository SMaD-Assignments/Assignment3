package navigation;
/** SWEN30006 Software Modeling and Design
Move class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Represents a move that is being take by the car for a time step
*/

import world.Car;
import world.WorldSpatial;

public class Move {
	
	public static enum State { FORWARD, REVERSE };
	
	public WorldSpatial.RelativeDirection direction;
	
	
}
