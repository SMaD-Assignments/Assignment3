package testing;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import tilelogic.TileInterpreter;
import tilelogic.TileInterpreterFactory;
import world.Car;
import world.World;

/**
 * Created by Tom Miles on 28/05/2017.
 */
public class debugDriver {
    public static void main(String[] args) {

        World world = new World(new TmxMapLoader().load("lecture-preview.tmx"));
        Car car = new Car(new Sprite(new Texture("sprites/car2.png")));

        try {
            TileInterpreter interpreter =
                    TileInterpreterFactory.getInstance().getFilledComposite();

            interpreter.ProcessMap(car.getView());


        } catch (Exception e) {
            System.out.println("CompositeTileInterpreter Exception");
        }



    }


}