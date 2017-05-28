package tilelogic;

import utilities.Coordinate;
import world.WorldSpatial;

/** SWEN30006 Software Modeling and Design
LavaLogicTile class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Generalizes the behavior of the LavaTrap
*/
public class LavaLogicTile implements LogicTile {

	private StateVector inVector, outVector;
	private Coordinate pos;

	public LavaLogicTile (Coordinate pos) {
		this.pos = pos;
	}
	@Override
	public int getPriority() {
		return LAVA;
	}

	@Override
	public void effect(WorldSpatial.Direction in, WorldSpatial.Direction out) {
		inVector.face = in;
		outVector.face = out;

		// if this goes around a corner, make it slow
		if (in == WorldSpatial.Direction.EAST ^ out == WorldSpatial.Direction.WEST ||
				in == WorldSpatial.Direction.SOUTH ^ out == WorldSpatial.Direction.NORTH) {

			inVector.speed = StateVector.Speed.SLOW;
		}
		// if the next tile wants to come in slow, go into the lava medium to try reduce time spent on fire
		if (outVector.speed == StateVector.Speed.SLOW) {
			inVector.speed = StateVector.Speed.MEDIUM;

		// otherwise speed through it
		} else {
			inVector.speed = StateVector.Speed.FAST;
		}

		// set the coordinates of inVector based on the outVector (which will have been set prior to method call
		switch (out) {
			case EAST:
				inVector.pos = new Coordinate(outVector.pos.x - 1, outVector.pos.y);
				break;
			case WEST:
				inVector.pos = new Coordinate(outVector.pos.x + 1, outVector.pos.y);
				break;
			case NORTH:
				inVector.pos = new Coordinate(outVector.pos.x, outVector.pos.y - 1);
				break;
			case SOUTH:
				inVector.pos = new Coordinate(outVector.pos.x, outVector.pos.y + 1);
				break;
			default:
				inVector.pos = null;
				System.out.println("ERROR: out uninitialized in effect() call");
				System.exit(1);
		}
	}

	@Override
	public StateVector getInVector() {
		return inVector;
	}

	@Override
	public StateVector getOutVector() {
		return outVector;
	}

	@Override
	public void setInVector(StateVector vector) {
		inVector = vector;
	}

	@Override
	public void setOutVector(StateVector vector) {
		outVector = vector;
	}



}
