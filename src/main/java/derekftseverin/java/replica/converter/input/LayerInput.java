/**
 * Class describing a "Layer" element coming from the input Level file.
 */

package derekftseverin.java.replica.converter.input;


public class LayerInput {

    int tile_index;
    float scroll_speed;
    byte resource[];


    LayerInput() {
        tile_index = -1;
        scroll_speed = 0.0f;
        resource = null;
    }

    LayerInput(int tile_index, float scroll_speed, byte resource[]) {
        this.tile_index = tile_index;
        this.scroll_speed = scroll_speed;
        this.resource = resource;
    }

}
