package tilelogic;

import utilities.Coordinate;
import world.WorldSpatial;

/** SWEN30006 Software Modeling and Design
NullLogicTile class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Represents any tile that should not be entered. Walls, blocked paths etc.
*/
public class NullLogicTile implements LogicTile{

	private StateVector inVector, outVector;

	public NullLogicTile() {
	}

	@Override
	public int getPriority() {
		return WALL;
	}

	@Override
	public Coordinate getPosition() {
		return null;
	}

	@Override
	public void effect(WorldSpatial.Direction in, WorldSpatial.Direction out) {
		System.out.println("ERROR: Trying to effect() a wall tile");
		// this should never be called
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
