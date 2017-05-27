package tilelogic;

import navigation.Move;
/** SWEN30006 Software Modeling and Design
OpenLogicTile class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Represents the behavior of tiles that are open for navigation
*/
public class OpenLogicTile implements LogicTile {

	private StateVector inVector, outVector;


	public OpenLogicTile() {

	}

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
