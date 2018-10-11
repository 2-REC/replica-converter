/**
 * Class describing a "Layer" element to be written in the output Level file.
 */

package derekftseverin.java.replica.converter.output;


public class Layer {

    Resource tile_sheet;
    int tile_width;
    int tile_height;
    Resource resource;
    bool foreground;
    float size_factor_x;
    float size_factor_y;
    float moving_speed_x;
    float moving_speed_y;


    public Layer() {
        tile_sheet = null;
        tile_width = 32;
        tile_height = 32;
        resource = null;
        foreground = false;
        size_factor_x = 1.0f;
        size_factor_y = 1.0f;
        moving_speed_x = 0.0f;
        moving_speed_y = 0.0f;
    }

}
