package tilelogic;

import utilities.Coordinate;
import world.WorldSpatial;

/** SWEN30006 Software Modeling and Design
StateVector class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Is used to represent a possible position of the car, is updated by tiles
to create the path for the car to follow
*/
public class StateVector {
	public static final int SENT = -1;	// sentinel value to represent an unconstrained vector

	public Coordinate pos;
	public WorldSpatial.Direction face;
	public int hp;
	public float angle;
	public enum Speed{SLOW, MEDIUM, FAST}
	public Speed speed;
	
	public StateVector(String pos, int hp, float angle) {
		this.pos = new Coordinate(pos);
		this.hp = hp;
		this.angle = angle;
	}

	/**
	 * Constructor for a completely unconstrained vector
	 */
	public StateVector() {
		pos = new Coordinate(SENT,SENT);
		angle = SENT;
		hp = SENT;
	}
}
