package tilelogic;

/** SWEN30006 Software Modeling and Design
LavaLogicTile class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Generalizes the behavior of the LavaTrap
*/
public class LavaLogicTile implements LogicTile {

	private StateVector inVector, outVector;

	@Override
	public int getPriority() {
		return LAVA;
	}

	@Override
	public void effect(StateVector outVector) {
		//TODO estimate lava's damage dealing, can get vin vout maybe but also maybe cbf, guess like 25 damage or some
		//TODO shit. just needs to NULL it's vectors if it will murder you
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
