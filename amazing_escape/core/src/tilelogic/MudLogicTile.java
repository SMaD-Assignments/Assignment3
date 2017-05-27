package tilelogic;

import navigation.Move;
/** SWEN30006 Software Modeling and Design
MudLogicTile class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Generalizes the behavior of the MudTrap 
*/
public class MudLogicTile implements LogicTile{

	private StateVector inVector, outVector; // george you screwed me so hard not having a generic LogicTile class

	@Override
	public int getPriority() {
		return MUD;
	}

	@Override
	public void effect(StateVector outVector) {
// TODO; again, no way of knowing the slow down factor (aside from magic numbers, odds are not ok)
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
