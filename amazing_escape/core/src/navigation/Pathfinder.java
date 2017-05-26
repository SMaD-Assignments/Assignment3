package navigation;

import java.util.ArrayList;
import java.util.HashMap;

import tilelogic.LogicTile;
import tilelogic.TileInterpreter;
import utilities.Coordinate;

/** SWEN30006 Software Modeling and Design
Pathfinder class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Takes the cars current situation and is responsible for finding a new move
*/
public class Pathfinder implements PathfinderInterface {
	
	private ArrayList <LogicTile> tilePath;
	private InternalMap map;
	
	private ArrayList<LogicTile> findDestination(HashMap<Integer,LogicTile> tiles){
		
		return null;
	}

	@Override
	public Move findMove(HashMap<Coordinate, TileInterpreter> interpreters) {
		
		return null;
	}
	
	public Move specialMove(HashMap<Integer,LogicTile> tiles){
		return null;
	}
	
	public ArrayList<LogicTile> recursivePath(LogicTile tiles){
		return null;
	}
	public int averageWeight(ArrayList<LogicTile> tiles){
		return 0;
	}
	
	public Move enforce(ArrayList<LogicTile> tiles){
		return null;
	}
}
