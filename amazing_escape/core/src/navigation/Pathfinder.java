package navigation;

import java.util.ArrayList;
import java.util.HashMap;

import tilelogic.LogicTile;
import tilelogic.StateVector;
import utilities.Coordinate;
import world.Car;
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
	public StateVector findMove(HashMap<Coordinate, LogicTile> map, StateVector carV) {

		best = -Integer.MAX_VALUE;
		ArrayList<Coordinate> edgeTiles;
		Coordinate searchStart;
		this.map.updateMap(map);
		this.map.wallOff(carV);
		// TODO does wallOff fix the currentView (map)?, im assuming it does for now

		// TODO maybe a scanForFinish method?
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
		edgeTiles = getEdgeTiles(map, searchStart);

		// loop through the border of vision, if a clear path is returned (best == 0), break
		for (Coordinate c : edgeTiles) {

			findDestination(map, c, carV.pos, 0, new ArrayList<>());
			if (best == 0) break;
		}
		enforce(tilePath, carV);

		return tilePath.get(0).getOutVector();
	}

	/**
	 * Couples in/out vectors between tiles then calls effect() on each to constrain direction of movement
	 * @param path ArrayList of tiles (in order start-finish)
	 * @param car StateVector of the car at this moment
	 */
	private void enforce(ArrayList<LogicTile> path, StateVector car) {

		path.get(0).setInVector(car);
		path.get(0).setOutVector(new StateVector());

		// couple inVectors of later tiles to outVectors of earlier ones
		for (int i=1; i<path.size(); i++) {
			path.get(i).setInVector ( path.get(i-1).getOutVector() );
			path.get(i).setOutVector(new StateVector());
		}
		// now with the fully coupled path, enforce all the tiles from end to start.
		// NB: due to the pretty useless peek function, it is practically impossible to estimate weather turns are
		// achievable, or how much mud slows you down, so effect assumes the move is doable
		for (int i=path.size()-1; i >= 0; i--) {
			path.get(i).effect();
		}
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
		return false;
	}

	/**
	 *
	 * @param map
	 * @return
	 */
	public StateVector specialMove(HashMap<Coordinate, LogicTile> map) {
// TODO; gahn... gahn get fucked t(-_-t)
return null;
	}




	/**
	 * Generates an ordered ArrayList of LogicTiles that are on the same edge as a given start coordinate.
	 * @param view current view
	 * @param start tile exploration is to begin from; must be a central tile
	 * @return ArrayList ordered from 0 being first tile to explore
	 */
	private ArrayList<Coordinate> getEdgeTiles(HashMap<Coordinate, LogicTile> view, Coordinate start) {

		// TODO I hate this method but cbf, also for some reason it thinks c2 is going to not get initialised or some
		// TODO shit. fuck knows really

		int j=0,i=1;
		Coordinate c1 = null, c2 = null, c3 = null, c4 = null;

		ArrayList<Coordinate> edgeTiles = new ArrayList<>();

		edgeTiles.add(start);

		// if view has exclusively one key in +/- x, increment through until the edge of the view
		if (view.containsKey(new Coordinate(start.x + 1, start.y)) ^
				view.containsKey(new Coordinate(start.x - 1, start.y))) {

			// first go through the primary edge (containing start)
			while (view.containsKey(c1 = new Coordinate(start.x, start.y + i)) &&
					view.containsKey(c2 = new Coordinate(start.x, start.y - i))) {
				edgeTiles.add(c1);
				edgeTiles.add(c2);
				i++;
			}
			i = 1;

			// go through adjacent edges
			// start edge was left
			if (view.containsKey(new Coordinate(c1.x + i, c1.y))) {

				while (view.containsKey(new Coordinate(c1.x + i, c1.y))) {
					edgeTiles.add(c3 = new Coordinate(c1.x + i, c1.y));
					edgeTiles.add(c4 = new Coordinate(c2.x + i, c2.y));
					i++;
				}
				// start edge was right
			} else {
				while (view.containsKey(new Coordinate(c1.x - i, c1.y))) {
					edgeTiles.add(c3 = new Coordinate(c1.x - i, c1.y));
					edgeTiles.add(c4 = new Coordinate(c2.x - i, c2.y));
					i++;
				}
			}
			i = 1;
			// close the loop
			if (view.containsKey(new Coordinate(c3.x,c3.y + i))) {
				while (c3.y + i != c4.y - i) {
					edgeTiles.add (new Coordinate(c3.x, c3.y + i));
					edgeTiles.add (new Coordinate(c4.x, c4.y - i));
					i++;
				}
			} else {
				while (c3.y + i != c4.y + i) {
					edgeTiles.add (new Coordinate(c3.x, c3.y - i));
					edgeTiles.add (new Coordinate(c4.x, c4.y + i));
					i++;
				}
			}


		} else if (view.containsKey(new Coordinate(start.x, start.y + 1)) ^
				view.containsKey(new Coordinate(start.x, start.y - 1))) {

			while (view.containsKey(c1 = new Coordinate(start.x + i, start.y)) &&
					view.containsKey(c2 = new Coordinate(start.x - i, start.y))) {
				edgeTiles.add(c1);
				edgeTiles.add(c2);
				i++;
			}
			i = 1;
			// start edge was bottom
			if (view.containsKey(new Coordinate(c1.x, c1.y + i))) {

				while (view.containsKey(new Coordinate(c1.x, c1.y + i))) {
					edgeTiles.add(new Coordinate(c1.x, c1.y +i ));
					edgeTiles.add(new Coordinate(c2.x, c2.y +i ));
					i++;
				}
				// start edge was top
			} else {
				while (view.containsKey(new Coordinate(c1.x, c1.y - i))) {
					edgeTiles.add(new Coordinate(c1.x, c1.y - i));
					edgeTiles.add(new Coordinate(c2.x, c2.y - i));
					i++;
				}
			}
			// close the loop
			if (view.containsKey(new Coordinate(c3.x + i,c3.y))) {
				while (c3.x + i != c4.x - i) {
					edgeTiles.add (new Coordinate(c3.x + i, c3.y));
					edgeTiles.add (new Coordinate(c4.x - i, c4.y));
					i++;
				}
			} else {
				while (c3.x - i != c4.x + i) {
					edgeTiles.add (new Coordinate(c3.x - i, c3.y));
					edgeTiles.add (new Coordinate(c4.x + i, c4.y));
					i++;
				}
			}


		} else {
			System.out.println("ERROR: getEdgeTiles - start coordinate not an edge piece");
			System.exit(1);
		}

		return edgeTiles;
	}
}