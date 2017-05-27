package mycontroller;

import java.util.HashMap;
import java.util.Map;

import controller.CarController;
import navigation.Move;
import navigation.Pathfinder;
import navigation.PathfinderInterface;
import tilelogic.LogicTile;
import interpreters.TileInterpreter;
import interpreters.TileInterpreterFactory;
import tilelogic.StateVector;
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

		StateVector carV = new StateVector(this.getPosition(), this.getHealth(), this.getAngle());
		/* First use the interpreter to convert the map into a form the pathfinder can use */
		HashMap<Coordinate, LogicTile> map = getProcessedMap();
		
		/* Then pass the interpreted map to the pathfinder to find a path to take */
		StateVector aim = pathfinder.findMove(map, carV);

		move(aim);
	}
	private boolean move(StateVector aim) {
		//TODO use peek to evaluate an appropriate behavior to meet aim condition. if aim is unreachable,
		//TODO should call specialMove to get a different aim (3. or reverse)
		return false;
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
