package mycontroller;

import java.util.HashMap;
import java.util.Map;

import controller.CarController;
import navigation.Move;
import navigation.Pathfinder;
import navigation.PathfinderInterface;
import tilelogic.LogicTile;
import tilelogic.TileInterpreter;
import tilelogic.TileInterpreterFactory;
import tiles.MapTile;
import utilities.Coordinate;
import world.Car;
/** SWEN30006 Software Modeling and Design
MyAIController class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Provides a controller that ties the methods used to drive the car together
*/
public class MyAIController extends CarController{
	private TileInterpreter interpreter;
	private PathfinderInterface pathfinder;
	
	
	public MyAIController(Car car) {
		super(car);
		interpreter = TileInterpreterFactory.getInstance().getFilledComposite();
		pathfinder = new Pathfinder();
	}

	@Override
	public void update(float delta) {
		/* First use the interpreter to convert the map into a form the pathfinder can use */
		HashMap<Coordinate, LogicTile> map = getProcessedMap();
		
		/* Then pass the interpreted map to the pathfinder to act on the car */
		Move move = pathfinder.findMove(map);
		
		/* Then enact the move given */
	}
	
	private HashMap<Coordinate, LogicTile> getProcessedMap() {
		HashMap<Coordinate, MapTile> mapCM = getView();
		HashMap<Coordinate, Object> mapCO = mapTileToObject(mapCM);
		mapCO = interpreter.ProcessMap(mapCO);
		HashMap<Coordinate, LogicTile> map = mapObjectToLogic(mapCO);
		return map;
	}
	
	private HashMap<Coordinate, Object> mapTileToObject(HashMap<Coordinate, MapTile> map) {
		HashMap<Coordinate, Object> newMap = new HashMap<Coordinate, Object>();
		for (Map.Entry<Coordinate, MapTile> tile : map.entrySet()) {
			newMap.put(tile.getKey(), (Object)(tile.getValue()));
		}
		return newMap;
	}
	
	private HashMap<Coordinate, LogicTile> mapObjectToLogic(HashMap<Coordinate, Object> map) {
		HashMap<Coordinate, LogicTile> newMap = new HashMap<Coordinate, LogicTile>();
		for (Map.Entry<Coordinate, Object> tile : map.entrySet()) {
			if (tile.getValue() instanceof LogicTile) {
				newMap.put(tile.getKey(), (LogicTile)(tile.getValue()));
			} else {
				System.err.println("ERROR: Improperly configured tile interpreter");
				System.exit(1);
			}
		}
		return newMap;
	}

}
