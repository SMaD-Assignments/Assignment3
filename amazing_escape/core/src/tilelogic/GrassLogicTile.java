package tilelogic;

import mycontroller.MyAIController;
import navigation.Move;

/** SWEN30006 Software Modeling and Design
GrassLogicTile class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Generalizes the behavior of the GrassTrap
*/
public class GrassLogicTile implements LogicTile {

	StateVector inVector;
	StateVector outVector;

	@Override
	public int getPriority() {
		return GRASS;
	}

	@Override
	public void effect(StateVector outVector) {

		if (inVector == null) inVector = new StateVector(null, outVector.hp, outVector.angle);
		else inVector.angle = outVector.angle;
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
