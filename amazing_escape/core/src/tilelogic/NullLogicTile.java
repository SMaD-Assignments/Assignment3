package tilelogic;

import navigation.Move;
/** SWEN30006 Software Modeling and Design
NullLogicTile class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Represents any tile that should not be entered. Walls, blocked paths etc.
*/
public class NullLogicTile implements LogicTile{



	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Move move(StateVector position) {
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
