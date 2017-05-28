package mycontroller;

import java.util.HashMap;
import java.util.Map;

import controller.CarController;
import navigation.Pathfinder;
import navigation.PathfinderInterface;
import tilelogic.LogicTile;
import tilelogic.TileInterpreter;
import tilelogic.TileInterpreterFactory;
import tilelogic.StateVector;
import tiles.MapTile;
import utilities.Coordinate;
import utilities.PeekTuple;
import world.Car;
import world.WorldSpatial;
/** SWEN30006 Software Modeling and Design
MyAIController class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Provides a controller that ties the methods used to drive the car together
*/
public class MyAIController extends CarController{
	private static float CRASH_DELTA = 0.5f;
	
	private TileInterpreter interpreter;
	private PathfinderInterface pathfinder;
	private float delta;
	
	public MyAIController(Car car) {
		super(car);
		interpreter = TileInterpreterFactory.getInstance().getFilledComposite();
		pathfinder = new Pathfinder();
		delta = 0.1f;
	}

	@Override
	public void update(float delta) {
		this.delta = delta;
		StateVector carV = new StateVector(this.getPosition(), this.getHealth(), this.getAngle());
		/* First use the interpreter to convert the map into a form the pathfinder can use */
		HashMap<Coordinate, LogicTile> map = getProcessedMap();
		
		/* Then pass the interpreted map to the pathfinder to find a path to take */
		StateVector aim = pathfinder.findMove(map, carV);

		if(!move(aim)) {
			specialMove();
		}
	}
	
	private boolean move(StateVector aim) {
		float delta = this.delta;
		Coordinate currentCor = new Coordinate(getPosition());
		PeekTuple peek;
		/* First find the next tile the car will land on */
		while(true) {
			peek = peek(getRawVelocity(), getAngle(), WorldSpatial.RelativeDirection.LEFT, delta);
			if(!currentCor.equals(peek.getCoordinate())) {
				break;
			}
			delta += this.delta;
		}
		/* Then check if the tile is the intended one */
		if (!aim.pos.equals(peek.getCoordinate())) {
			if (getTileTurn(currentCor, aim.pos, peek.getCoordinate()) == WorldSpatial.RelativeDirection.RIGHT) {
				turnRight(this.delta);
			} else if (getTileTurn(currentCor, aim.pos, peek.getCoordinate()) == WorldSpatial.RelativeDirection.LEFT) {
				turnLeft(this.delta);
			} else {
				return false;
			}
		} else {
			/* If so match aim angle */
			float dist = aim.angle - getAngle();
			if(dist > 180 || (dist < 0 && dist > -180)) {
				turnLeft(this.delta);
			} else {
				turnRight(this.delta);
			}
		}
		
		/* Match target velocity */
		if (getVelocity() < aim.getVelocity()) {
			applyForwardAcceleration();
		} else {
			applyReverseAcceleration();
		}
		
		return true;
	}
	
	private void specialMove() {
		float right, left;
		right = getAngle() + 90;
		left = getAngle() - 90;
		if (right > 360) {right -= 360;}
		else if (left < 0) {left += 360;}
		
		/* Peek ahead and see if car will crash if turning if possible turn */
		PeekTuple peek = peek(getRawVelocity(), left, WorldSpatial.RelativeDirection.LEFT, CRASH_DELTA);
		if (peek.getReachable()) {
			turnLeft(this.delta);
		} else {
			peek = peek(getRawVelocity(), right, WorldSpatial.RelativeDirection.RIGHT, CRASH_DELTA);
			if (peek.getReachable()) {
				turnRight(this.delta);
			}
		}
		applyReverseAcceleration();
		
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
	
	private WorldSpatial.RelativeDirection getTileTurn(Coordinate car, Coordinate aim, Coordinate peek) {
		int dist;
		dist = findDirect(peek, car) - findDirect(aim, car);
		if(dist > 4 || (dist < 0 && dist >-4)) {
			return WorldSpatial.RelativeDirection.RIGHT;
		}
		if(dist < -4 || (dist < 4 && dist > 0)) {
			return WorldSpatial.RelativeDirection.LEFT;
		}
		else {
			/* For U turns always turn right for consistency */
			return null;
		}
	}
	
	private int findDirect(Coordinate dest, Coordinate car) {
		if(dest.x == car.x && dest.y == car.y+1) {
			return 0;
		}
		if(dest.x == car.x+1 && dest.y == car.y+1) {
			return 1;
		}
		if(dest.x == car.x+1 && dest.y == car.y) {
			return 2;
		}
		if(dest.x == car.x+1 && dest.y == car.y-1) {
			return 3;
		}
		if(dest.x == car.x && dest.y == car.y-1) {
			return 4;
		}
		if(dest.x == car.x-1 && dest.y == car.y-1) {
			return 5;
		}
		if(dest.x == car.x-1 && dest.y == car.y) {
			return 6;
		}
		if(dest.x == car.x-1 && dest.y == car.y+1) {
			return 7;
		}
		System.err.println("ERROR: bad Tiles found by move");
		System.exit(-1);
		return 0;
	}


}
