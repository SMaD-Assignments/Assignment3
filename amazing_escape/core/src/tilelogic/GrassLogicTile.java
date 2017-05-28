package tilelogic;

import mycontroller.MyAIController;
import navigation.Move;
import utilities.Coordinate;
import world.WorldSpatial;

/** SWEN30006 Software Modeling and Design
GrassLogicTile class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Generalizes the behavior of the GrassTrap
*/
public class GrassLogicTile implements LogicTile {

	private StateVector outVector, inVector;
	private Coordinate pos;

	public GrassLogicTile (Coordinate pos) {
		this.pos = pos;
	}

	@Override
	public int getPriority() {
		return GRASS;
	}

	@Override
	public void effect(WorldSpatial.Direction in, WorldSpatial.Direction out) {


		inVector.face = in;
		outVector.face = out;


		// if this goes around a corner, make it slow
		if (in == WorldSpatial.Direction.EAST ^ out == WorldSpatial.Direction.WEST ||
				in == WorldSpatial.Direction.SOUTH ^ out == WorldSpatial.Direction.NORTH) {

			inVector.speed = StateVector.Speed.SLOW;
		}

		// set the coordinates of inVector based on the outVector (which will have been set prior to method call
		switch (out) {
			case EAST:
				inVector.pos = new Coordinate(outVector.pos.x - 1, outVector.pos.y);
				break;
			case WEST:
				inVector.pos = new Coordinate(outVector.pos.x + 1, outVector.pos.y);
				break;
			case NORTH:
				inVector.pos = new Coordinate(outVector.pos.x, outVector.pos.y - 1);
				break;
			case SOUTH:
				inVector.pos = new Coordinate(outVector.pos.x, outVector.pos.y + 1);
				break;
			default:
				inVector.pos = null;
				System.out.println("ERROR: out uninitialized in effect() call");
				System.exit(1);
		}

		if (outVector.angle != StateVector.SENT)  {
			inVector.angle = outVector.angle;

		} else {

			if (in == WorldSpatial.Direction.EAST) {

				if (out == WorldSpatial.Direction.WEST) {
					inVector.angle = WorldSpatial.EAST_DEGREE_MIN;
					outVector.angle = WorldSpatial.EAST_DEGREE_MIN;

				} else if (out == WorldSpatial.Direction.NORTH) {
					inVector.angle = WorldSpatial.EAST_DEGREE_MIN + 45;
					outVector.angle = WorldSpatial.EAST_DEGREE_MIN + 45;


				} else if (out == WorldSpatial.Direction.SOUTH) {
					inVector.angle = WorldSpatial.EAST_DEGREE_MAX - 45;
					outVector.angle = WorldSpatial.EAST_DEGREE_MAX - 45;
				}

			} else if (in == WorldSpatial.Direction.WEST) {

				if (out == WorldSpatial.Direction.EAST) {
					inVector.angle = WorldSpatial.WEST_DEGREE;
					outVector.angle = WorldSpatial.WEST_DEGREE;

				} else if (out == WorldSpatial.Direction.NORTH) {
					inVector.angle = WorldSpatial.WEST_DEGREE - 45;
					outVector.angle = WorldSpatial.WEST_DEGREE - 45;


				} else if (out == WorldSpatial.Direction.SOUTH) {
					inVector.angle = WorldSpatial.WEST_DEGREE + 45;
					outVector.angle = WorldSpatial.WEST_DEGREE + 45;
				}

			} else if (in == WorldSpatial.Direction.SOUTH) {

				if (out == WorldSpatial.Direction.NORTH) {
					inVector.angle = WorldSpatial.SOUTH_DEGREE;
					outVector.angle = WorldSpatial.SOUTH_DEGREE;

				} else if (out == WorldSpatial.Direction.WEST) {
					inVector.angle = WorldSpatial.SOUTH_DEGREE - 45;
					outVector.angle = WorldSpatial.SOUTH_DEGREE - 45;

				} else if (out == WorldSpatial.Direction.EAST) {
					inVector.angle = WorldSpatial.SOUTH_DEGREE + 45;
					outVector.angle = WorldSpatial.SOUTH_DEGREE + 45;
				}

			} else if (in == WorldSpatial.Direction.NORTH) {

				if (out == WorldSpatial.Direction.SOUTH) {
					inVector.angle = WorldSpatial.NORTH_DEGREE;
					outVector.angle = WorldSpatial.NORTH_DEGREE;

				} else if (out == WorldSpatial.Direction.WEST) {
					inVector.angle = WorldSpatial.NORTH_DEGREE + 45;
					outVector.angle = WorldSpatial.NORTH_DEGREE + 45;

				} else if (out == WorldSpatial.Direction.EAST) {
					inVector.angle = WorldSpatial.NORTH_DEGREE - 45;
					outVector.angle = WorldSpatial.NORTH_DEGREE - 45;
				}
			}
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
