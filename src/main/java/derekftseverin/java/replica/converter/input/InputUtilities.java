/**
 * Class providing methods to extract Level data from an input binary file into a working structure.
 */

/*
  !!!! TODO !!!!
  - see if better to handle or rethrow exceptions
  - check resources release with "try-with-resource" block
  - check for filename or url in "writeXml"
  - check "getTiledWorld" code...
*/

package derekftseverin.java.replica.converter.input;


public static class InputUtilities {

    /**
     * Parse the input binary file and extract the level data.
     *
     * @param level_filename: path to the binary input file
     * @throws IOException if the file doesn't exist or doesn't have a valid format.
     */
    public static LevelInput parseInput(String level_filename) throws IOException {
        LevelInput level_input = null;

        try (
// !!!! ???? TODO: filename or url? ???? !!!!
            FileInputStream input_file = new FileInputStream(level_filename);
            BufferedInputStream bytestream = new BufferedInputStream(input_file);
        ) {

            if (bytestream.read() != 96) { // "signature" => 96d
                throw new ParseException("Invalid input file! - signature for level != 96d");
            }

            int layer_count = bytestream.read(); // "layer count"

            level_input = new LevelInput();
            level_input.background_index = bytestream.read(); // "background index"

            for (int i = 0; i < layer_count; ++i) {

                int layer_type = bytestream.read(); // "layer type"
                int tile_index = bytestream.read(); // "tilesheet index"
                float scroll_speed = bytestream.read(4); // "scroll speed"
                byte[] resource = getTiledWorld(bytestream);

                switch(layer_type) {
                    case LayerType.BACKGROUND:
                        LayerInput layer_input = new LayerInput(tile_index, scroll_speed, resource);
                        level_input.AddLayer(layer_input);
                        break;
                    case LayerType.COLLISION:
                        level_input.collision_resource = resource;
                        break;
                    case LayerType.OBJECTS:
                        level_input.objects_resource = resource;
                        break;
                    case LayerType.HOTSPOTS:
                        level_input.hotspots_resource = resource;
                        break;
                    default:
                        // ignore layer
                        break;
                }
            }
        }
        catch(IOException e) {
// !!!! ???? TODO: rethrow or wrap in custom exception? ???? !!!!
            System.out.println("InputUtilities - parseInput: Error parsing input file!");
//            e.printStackTrace();
            throw(e);
        }

        return level_input;
    }

    /**
     * Read and copy the bytes constituting a layer,
     *  needing to first read its size (width and height) to know the amount of data to read.
     *
     * ! - THIS CODE IS HORRIBLE!
     *     (because there's no access to "pos" in buffer or "seek" method)
     *
     * @param bytestream: stream from input file (not from beginning!)
     * @throws IOException if the stream doesn't have a valid format or can't be read.
     */
      */
    private static byte[] getTiledWorld(BufferedInputStream bytestream) throws IOException {

        byte signature = bytestream.read(); // "signature" => 42d
        if (signature != 42) {
            throw new ParseException("Invalid input file! - signature for layer != 42d");
        }

// !!!! TODO: check that code is ok !!!!
//        int width = bytestream.read(4); // "width"
        byte[] bin_width = new byte[4];
        bin_width[0] = bytestream.read();
        bin_width[1] = bytestream.read();
        bin_width[2] = bytestream.read();
        bin_width[3] = bytestream.read();
        int width = ByteBuffer.wrap(bin_width).getInt();

// !!!! TODO: check that code is ok !!!!
//        int height = bytestream.read(4); // "height"
        byte[] bin_height = new byte[4];
        bin_height[0] = bytestream.read();
        bin_height[1] = bytestream.read();
        bin_height[2] = bytestream.read();
        bin_height[3] = bytestream.read();
        int height = ByteBuffer.wrap(bin_height).getInt();

        int size = width * height;

        if (size > bytestream.available()) {
            throw new ParseException("Invalid input file! - Not enough remaining bytes (require " + size + ")");
        }

        byte[] buffer = new byte[size + 9]; // "size" + "signature" + "width" + "height"
        buffer[0] = signature;
        buffer[1] = bin_width[0];
        buffer[2] = bin_width[1];
        buffer[3] = bin_width[2];
        buffer[4] = bin_width[3];
        buffer[5] = bin_height[0];
        buffer[6] = bin_height[1];
        buffer[7] = bin_height[2];
        buffer[8] = bin_height[3];
        for (int i=0; i<size; ++i) {
            buffer[i + 9] = bytestream.read();
        }

        return buffer;
    }

}
