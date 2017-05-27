package tilelogic;

/** SWEN30006 Software Modeling and Design
NullLogicTile class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Represents any tile that should not be entered. Walls, blocked paths etc.
*/
public class NullLogicTile implements LogicTile{

	private StateVector inVector, outVector;
	private int priority;

	public NullLogicTile(int priority) {
		this.priority = priority;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public void effect() {

	}

	@Override
	public void effect(StateVector outVector) {
		if (priority == WALL) {
			inVector = null;
			outVector = null;
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
