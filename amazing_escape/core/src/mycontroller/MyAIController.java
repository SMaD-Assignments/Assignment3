package mycontroller;

import java.util.HashMap;

import controller.CarController;
import navigation.Pathfinder;
import navigation.PathfinderInterface;
import tilelogic.TileInterpreter;
import tilelogic.TileInterpreterFactory;
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
		// Gotta figure some why to cast this if we want the strat pattern to be correct /////////////////////////////////////////////////////////
		HashMap<Coordinate, LogicTile> map = interpreter.ProcessMap(getView())
		
	}

}
