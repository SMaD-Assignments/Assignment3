package tilelogic;

import navigation.Move;
/** SWEN30006 Software Modeling and Design
LogicTile interface
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Provides the general methods that allow for all traps to be generalised
*/
public interface LogicTile {

	public int getPriority();
	public StateVector move(float delta, Move move);
}
