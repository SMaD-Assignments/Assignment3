package tilelogic;

import navigation.Move;

/** SWEN30006 Software Modeling and Design
GrassLogicTile class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Generalizes the behavior of the GrassTrap
*/
public class GrassLogicTile implements LogicTile {

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public StateVector move(float delta, Move move) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StateVector getInVector() {
		return null;
	}

	@Override
	public StateVector getOutVector() {
		return null;
	}

	@Override
	public void setInVector(StateVector vector) {

	}

	@Override
	public void setOutVector(StateVector vector) {

	}

}
