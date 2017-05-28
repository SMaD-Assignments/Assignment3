package tilelogic;

import java.util.ArrayList;
import java.util.HashMap;

import utilities.Coordinate;
/** SWEN30006 Software Modeling and Design
CompositeTileInterpreter class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

A composite strategy that applies several interpreters in order 
*/
public class CompositeTileInterpreter implements TileInterpreter {
	private ArrayList<TileInterpreter> interpreters;
	
	public CompositeTileInterpreter() {
		interpreters = new ArrayList<TileInterpreter>();
	}
	//Not exactly sure what this is for
	@Override
	public int getPriority() {
		
		return 0;
	}
	
	@Override
	public HashMap<Coordinate, Object> ProcessMap(HashMap<Coordinate, Object> map) {
		for(TileInterpreter interpreter : interpreters) {
			map = interpreter.ProcessMap(map);
		}
		return map;
	}
	
	public void add(TileInterpreter interpreter) {
		interpreters.add(interpreter);
	}

}
