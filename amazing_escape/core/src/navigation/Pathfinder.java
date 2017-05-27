package navigation;

import java.util.ArrayList;
import java.util.HashMap;

import interpreters.CompositeTileInterpreter;
import tilelogic.LogicTile;
import tilelogic.StateVector;
import utilities.Coordinate;
import world.Car;
import world.World;
import world.WorldSpatial;

/** SWEN30006 Software Modeling and Design
Pathfinder class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Takes the cars current situation and is responsible for finding a new move
*/
public class Pathfinder implements PathfinderInterface {

	private InternalMap map;
	private ArrayList<LogicTile> tilePath;
	private int best;	// best score for path that has been found

	public Pathfinder() {
		map = new InternalMap();
		tilePath = new ArrayList<>();
		best = 0;

	}

	@Override
	public Move findMove(HashMap<Coordinate, LogicTile> map, StateVector carV) {

		ArrayList<LogicTile> tilePath = new ArrayList<>();
		Coordinate searchStart;
		this.map.updateMap(map);
		this.map.wallOff(carV);
		// TODO does wallOff fix the currentView (map)?, im assuming it does for now


		/* Establish the tile on the forward facing edge to begin looking for possible tiles to aim for */

		if (carV.angle < WorldSpatial.EAST_DEGREE_MIN + 45 || carV.angle >= WorldSpatial.EAST_DEGREE_MAX - 45) {
			searchStart = new Coordinate(carV.pos.x + Car.VIEW_SQUARE, carV.pos.y);

		} else if (carV.angle < WorldSpatial.NORTH_DEGREE - 45 || carV.angle >= WorldSpatial.NORTH_DEGREE + 45) {
			searchStart = new Coordinate(carV.pos.x, carV.pos.y + Car.VIEW_SQUARE);

		} else if (carV.angle < WorldSpatial.WEST_DEGREE - 45 || carV.angle >= WorldSpatial.WEST_DEGREE + 45) {
			searchStart = new Coordinate(carV.pos.x - Car.VIEW_SQUARE, carV.pos.y);

		} else {
			searchStart = new Coordinate(carV.pos.x, carV.pos.y - Car.VIEW_SQUARE);
		}
		best = -Integer.MAX_VALUE;

		// find the best route to take


		findDestination(map, searchStart, carV.pos, 0, new ArrayList<>());

		Move move = tilePath.get(0).move(carV);

		if (move == null) {
			return specialMove(map);
		}
		return move;

	}

	/**
	 * Recursive exploration function, updates best and tilePath to the best route found
	 * @param view HashMap of current view
	 * @param look the tile coordinate to be explored
	 * @param car position of the car
	 * @return true if car has been found, false if not; this way, if a path being explored does not reach the car but
	 * 			has added tiles to the path it can remove them before continuing
	 */
	private boolean findDestination (HashMap<Coordinate, LogicTile> view, Coordinate look, Coordinate car,
									 int pathScore, ArrayList<LogicTile> path) {

		int score;
		ArrayList<LogicTile> currentPath = new ArrayList<>(path);
		Coordinate leftMove = null;
		Coordinate rightMove = null;
		Coordinate downMove = null;
		Coordinate upMove = null;

		currentPath.add(0, view.get(look));
		score = pathScore + view.get(look).getPriority();

		/* break conditions */

		// if we have already been here, return
		if (path.contains(view.get(look))) return false;

		// if we are looking at a wall, return
		if (view.get(look).getPriority() == LogicTile.WALL) return false;

		// if we have found a complete path that goes through no traps, we won't keep looking in any subsequent calls
		if (tilePath != null && tilePath.get(0).equals(car) && best == 0) return true;

		// if the score resulting from going to this tile is worse than best, no point going on
		if (score < best) return false;

		// if we reach the car and have a better score than previously found, adjust tilePath and best and return
		if (currentPath.get(0).equals(car) && score > best) {
			tilePath = currentPath;
			best = score;
			return true;
		}

		/* Recursion */

		// look in the direction that get's closer to the car first, since no more recursion will occur if a clear route
		// is found, by looking through closer tiles first, the shortest clear path will be found (if it exists)

		if (Math.abs(car.x - look.x) < Math.abs(car.y - look.y)) {
			// if x difference is negative, look to the left, otherwise look right
			if (car.x - look.x < 0)  {
				leftMove = new Coordinate(look.x - 1, look.y);
				findDestination(view, leftMove, car, score, currentPath);

			} else {
				rightMove = new Coordinate(look.x + 1, look.y);
				findDestination(view, rightMove, car, score, currentPath);
			}
		} else if (Math.abs(car.x - look.x) > Math.abs(car.y - look.y)) {
			// if y difference is negative, look to the down, otherwise look up
			if (car.y - look.y < 0)  {
				downMove = new Coordinate(look.x, look.y - 1);
				findDestination(view, downMove, car, score, currentPath);

			} else {
				upMove = new Coordinate(look.x, look.y + 1);
				findDestination(view, upMove, car, score, currentPath);
			}
		}

		// look though every other direction

		if (upMove == null) {
			upMove = new Coordinate(look.x, look.y + 1);
			findDestination(view, upMove, car, score, currentPath);
		}
		if (downMove == null) {
			downMove = new Coordinate(look.x, look.y - 1);
			findDestination(view, downMove, car, score, currentPath);
		}
		if (rightMove == null) {
			rightMove = new Coordinate(look.x + 1, look.y);
			findDestination(view, rightMove, car, score, currentPath);
		}
		if (leftMove == null) {
			leftMove = new Coordinate(look.x - 1, look.y);
			findDestination(view, leftMove, car, score, currentPath);
		}




	}

	/**
	 *
	 * @param map
	 * @return
	 */
	private Move specialMove(HashMap<Coordinate, LogicTile> map) {
// TODO; do me

	}

}
