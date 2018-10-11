/**
 * Class describing the "Background" element to be written in the output Level file.
 */

package derekftseverin.java.replica.converter.output;


public class Background {

    Resource resource;
    float size_factor_x;
    float size_factor_y;
    int width;
    int height;
    bool keep_width;


    public Background() {
        resource = null;
        size_factor_x = 1.0f;
        size_factor_y = 1.0f;
        width = 512;
        height = 512;
        keep_width = true;
    }

    public Background(Resource resource, float size_factor_x, float size_factor_y,
            int width, int height, bool keep_width) {
        this.resource = resource;
        this.size_factor_x = size_factor_x;
        this.size_factor_y = size_factor_y;
        this.width = width;
        this.height = height;
        this.keep_width = keep_width;
    }

}
