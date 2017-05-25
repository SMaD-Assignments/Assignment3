package tilelogic;

/** SWEN30006 Software Modeling and Design
TileInterpreterFactory class
George Juliff - 624946
David Murges - 657384
Thomas Miles - 626263

Singleton factory that provides all conversion strategies
*/
public class TileInterpreterFactory {
	private static TileInterpreterFactory instance;
	
	/**
	 * Singleton access method
	 */
	public static TileInterpreterFactory getInstance() {
		if(instance == null) {
			instance = new TileInterpreterFactory();
		}
		return instance;
	}
	
	public LavaInterpreter getLavaInterpreter() {
		return new LavaInterpreter();
	}
	
	public MudInterpreter getMudInterpreter() {
		return new MudInterpreter();
	}
	
	public GrassInterpreter getGrassInterpreter() {
		return new GrassInterpreter();
	}
	
	public BasicInterpreter getBasicInterpreter() {
		return new BasicInterpreter();
	}
	
	public CompositeTileInterpreter getCompositeTileInterpreter() {
		return new CompositeTileInterpreter();
	}
	
	public CompositeTileInterpreter getFilledComposite() {
		CompositeTileInterpreter composite = getCompositeTileInterpreter();
		/* Add all interpreters in the order they should be applied*/
		composite.add(getLavaInterpreter());
		composite.add(getMudInterpreter());
		composite.add(getGrassInterpreter());
		/* MUST be added last */
		composite.add(getBasicInterpreter());
		return composite;
	}
}
