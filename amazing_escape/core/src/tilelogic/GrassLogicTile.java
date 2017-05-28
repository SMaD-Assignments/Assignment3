package tilelogic;

import mycontroller.MyAIController;
import navigation.Move;
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


	@Override
	public int getPriority() {
		return GRASS;
	}

	@Override
	public void effect(WorldSpatial.Direction in, WorldSpatial.Direction out) {
		inVector.face = in;
		outVector.face = out;


		//
		if (in == WorldSpatial.Direction.EAST ^ out == WorldSpatial.Direction.WEST ||
				in == WorldSpatial.Direction.SOUTH ^ out == WorldSpatial.Direction.NORTH) {

			inVector.speed = StateVector.Speed.SLOW;
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
