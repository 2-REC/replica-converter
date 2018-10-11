/**
 * Class describing the "Level" element to be written in the output Level file.
 */

package derekftseverin.java.replica.converter.output;


public class Level {

    Background background;
    LayerMain layer_main;
    ArrayList<Layer> layers;
    Dialogs dialogs;


    public Level() {
        background = null;
        layer_main = null;
        layers = new ArrayList<Layer>();
        dialogs = null;
    }

}
