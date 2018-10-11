/**
 * Class describing the "LayerMain" element to be written in the output Level file.
 */

package derekftseverin.java.replica.converter.output;


public class LayerMain {

    Resource tile_sheet;
    int tile_width;
    int tile_height;
    Resource resource;
    Resource collision_resource;
    Resource objects_resource;
    Resource hotspots_resource;


    public LayerMain() {
        tile_sheet = null;
        tile_width = 32;
        tile_height = 32;
        resource = null;
        collision_resource = null;
        objects_resource = null;
        hotspots_resource = null;
    }

}
