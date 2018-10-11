/**
 * Class describing the "Level" element coming from the input Level file.
 */

package derekftseverin.java.replica.converter.input;


public class LevelInput {

    int background_index;
    byte collision_resource[];
    byte objects_resource[];
    byte hotspots_resource[];
    ArrayList<LayerInput> layers;


    public LevelInput() {
        background_index = -1;
        collision_resource = null;
        objects_resource = null;
        hotspots_resource = null;
        layers = new ArrayList<LayerInput>();
    }

    public void addLayer(LayerInput layer_input) {
        layers.add(layer_input);
    }

}
