package testing;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import navigation.Pathfinder;
import navigation.PathfinderInterface;
import tilelogic.TileInterpreter;
import tilelogic.TileInterpreterFactory;
import world.Car;
import world.World;

/**
 * Created by Tom Miles on 28/05/2017.
 */
public class debugDriver {
    public static void main(String[] args) {

    	TileInterpreter interpreter;
    	PathfinderInterface pathfinder;
    	float delta;
    	TiledMap map;
    	OrthographicCamera camera;
    	World world;
    	OrthogonalTiledMapRenderer tiledMapRenderer;
    	
    	map = new TmxMapLoader().load("/core/assets/easy-map.tmx");

		// Define the asset manager
		// map = new TmxMapLoader().load("easy-map.tmx");
		// map = new TmxMapLoader().load("easy-map-traps.tmx");
		map = new TmxMapLoader().load("lecture-preview.tmx");
		// map = new TmxMapLoader().load("lecture-preview2.tmx");
		// map = new TmxMapLoader().load("map.tmx");
		// map = new TmxMapLoader().load("map2.tmx");
		
		// Create the world
		world = new World(map);
		
		// Set the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false,World.MAP_WIDTH,World.MAP_HEIGHT);
		camera.update();
		
		// Define scale per unit
		float unitScale = 1 / 32f;
		tiledMapRenderer = new OrthogonalTiledMapRenderer(map,unitScale);
        Car car = new Car(new Sprite(new Texture("sprites/car2.png")));
        interpreter = TileInterpreterFactory.getInstance().getFilledComposite();
		pathfinder = new Pathfinder();
		delta = 0.1f;

        try {
        // debug section
        	
        	


        } catch (Exception e) {
            System.out.println("CompositeTileInterpreter Exception");
        }


        System.out.println("Test sucess");
    }


}