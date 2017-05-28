package tilelogic;

import tilelogic.StateVector.Speed;
import utilities.Coordinate;
import world.WorldSpatial;

/** SWEN30006 Software Modeling and Design
OpenLogicTile class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Represents the behavior of tiles that are open for navigation
*/
public class OpenLogicTile implements LogicTile {

	private StateVector inVector, outVector;

	private Coordinate pos;

	OpenLogicTile (Coordinate pos) {
		this.pos = pos;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Coordinate getPosition() {
		return pos;
	}
	@Override
	public void effect(WorldSpatial.Direction in, WorldSpatial.Direction out) {

		inVector.face = in;
		outVector.face = out;
		inVector.speed = outVector.speed; // go in at the same speed as required for out to give time to slow down
		if (inVector.speed == null) {
			inVector.speed = Speed.FAST;
		}
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



/* 	getters and setters
/*--------------------------------------------------------------------------------------------------------------------*/

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
