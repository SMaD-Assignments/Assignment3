package tilelogic;

import utilities.Coordinate;
/** SWEN30006 Software Modeling and Design
StateVector class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Is used to represent a possible position of the car, is updated by tiles
to create the path for the car to follow
*/
public class StateVector {
	public Coordinate pos;
	public int hp;
	public int angle;
	
	public StateVector(Coordinate pos, int hp, int angle) {
		this.pos = pos;
		this.hp = hp;
		this.angle = angle;
	}
}
