package tilelogic;

import utilities.Coordinate;
import world.WorldSpatial;

/** SWEN30006 Software Modeling and Design
MudLogicTile class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Generalizes the behavior of the MudTrap 
*/
public class MudLogicTile implements LogicTile{

	private StateVector inVector, outVector; // george you screwed me so hard not having a generic LogicTile class
	private Coordinate pos;

	MudLogicTile (Coordinate pos) {
		this.pos = pos;
	}
	@Override
	public Coordinate getPosition() {
		return pos;
	}
	@Override
	public int getPriority() {
		return MUD;
	}

	@Override
	public void effect(WorldSpatial.Direction in, WorldSpatial.Direction out) {

		inVector.face = in;
		outVector.face = out;
		inVector.speed = StateVector.Speed.FAST; // don't want to get stuck


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
