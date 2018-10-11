/**
 * Class providing methods to build a Level structure from the inout data,
 *  and to generate the required output files.
 */

package derekftseverin.java.replica.converter.output;

public static class OutputUtilities {

/*
    private static int BACKGROUND_NONE = -1;
    private static int BACKGROUND_SUNSET = 0;
    private static int BACKGROUND_ISLAND = 1;
    private static int BACKGROUND_SEWER = 2;
    private static int BACKGROUND_UNDERGROUND = 3;
    private static int BACKGROUND_FOREST = 4;
    private static int BACKGROUND_ISLAND2 = 5;
    private static int BACKGROUND_LAB = 6;

    private static String[] background_resources = {
        "background_sunset", "background_island", "background_sewage", "background_underground",
        "background_grass2", "background_island2", "background_lab01"
    };

*/
// !!!! ???? TODO: handle "BACKGROUND_NONE" (-1)? ???? !!!!
    private static final enum BackgroundResourceType {
        BACKGROUND_SUNSET,
        BACKGROUND_ISLAND,
        BACKGROUND_SEWER,
        BACKGROUND_UNDERGROUND,
        BACKGROUND_FOREST,
        BACKGROUND_ISLAND2,
        BACKGROUND_LAB
    };

    private static final Map<BackgroundResourceType, String> BACKGROUND_RESOURCE_STRINGS;
    static {
        Map<int, String> tmp_map = new EnumMap<BackgroundResourceType, String>(BackgroundResourceType.class);
        tmp_map.put(BackgroundResourceType.BACKGROUND_SUNSET, "background_sunset");
        tmp_map.put(BackgroundResourceType.BACKGROUND_ISLAND, "background_island");
        tmp_map.put(BackgroundResourceType.BACKGROUND_SEWER, "background_sewage");
        tmp_map.put(BackgroundResourceType.BACKGROUND_UNDERGROUND, "background_underground");
        tmp_map.put(BackgroundResourceType.BACKGROUND_FOREST, "background_grass2");
        tmp_map.put(BackgroundResourceType.BACKGROUND_ISLAND2, "background_island2");
        tmp_map.put(BackgroundResourceType.BACKGROUND_LAB, "background_lab01");
        BACKGROUND_RESOURCE_STRINGS = Collections.unmodifiableMap(tmp_map);
    }

/*
    private static int THEME_NONE = -1;
    private static int THEME_GRASS = 0;
    private static int THEME_ISLAND = 1;
    private static int THEME_SEWER = 2;
    private static int THEME_UNDERGROUND = 3;
    private static int THEME_LAB = 4;
    private static int THEME_LIGHTING = 5;
    private static int THEME_TUTORIAL = 6;

    private static String[] tilesheet_resources = {
        "grass", "island", "sewage", "cave",
        "lab", "titletileset", "tutorial"
    };
*/
// !!!! ???? TODO: handle "THEME_NONE" (-1)? ???? !!!!
    private static final enum ThemeResourceType {
        THEME_GRASS,
        THEME_ISLAND,
        THEME_SEWER,
        THEME_UNDERGROUND,
        THEME_LAB,
        THEME_LIGHTING,
        THEME_TUTORIAL
    };

    private static final Map<ThemeResourceType, String> THEME_RESOURCE_STRINGS;
    static {
        Map<int, String> tmp_map = new EnumMap<ThemeResourceType, String>(ThemeResourceType.class);
        tmp_map.put(ThemeResourceType.THEME_GRASS, "grass");
        tmp_map.put(ThemeResourceType.THEME_ISLAND, "island");
        tmp_map.put(ThemeResourceType.THEME_SEWER, "sewage");
        tmp_map.put(ThemeResourceType.THEME_UNDERGROUND, "cave");
        tmp_map.put(ThemeResourceType.THEME_LAB, "lab");
        tmp_map.put(ThemeResourceType.THEME_LIGHTING, "titletileset");
        tmp_map.put(ThemeResourceType.THEME_TUTORIAL, "tutorial");
        THEME_RESOURCE_STRINGS = Collections.unmodifiableMap(tmp_map);
    }


    private static int BACKGROUND_WIDTH = 512;
    private static int BACKGROUND_HEIGHT = 512;


    /**
      */
    public static Level generateLevel(String level_name, LevelInput level_input, Resource dialogs) {
        Level level = new Level();
        level.background = generateBackground(level_input.background_index);
        level.layer_main = generateMainLayer(level_name, level_input);
        generateLayers(level_input.layers, level.layers);

        if (dialogs != null) {
            level.dialogs = dialogs;
        }

        return level;
    }

    /**
      */
    public static Resource generateLevelOutput(String level_name, Level level) {
        XmlLevelHandler.writeXml(level_name + ".xml", level);
        return (new Resource(ResourceType.XML, level_name));
    }


    /**
      */
    private static Background generateBackground(int background_index) {
        Resource resource = new Resource(ResourceType.DRAWABLE,
                background_resources[background_index]);
        Background background = new Background(resource, 1.5f, 1.5f,
                BACKGROUND_WIDTH, BACKGROUND_HEIGHT, true);

        return background;
    }

    /**
      */
    private static LayerMain generateMainLayer(String level_name, LevelInput level_input) {
        LayerMain main_layer = new LayerMain();

        LayerInput main_layer_input = getMainLayer(level_input.layers);

        layer_main.tile_sheet = new Resource(ResourceType.DRAWABLE,
                tilesheet_resources[main_layer_input.tile_index]);

        layer_main.tile_width = 32;
        layer_main.tile_height = 32;

        layer_main.resource = generateBinaryLayer(level_name, main_layer_input.resource, LayerType.BACKGROUND);
        layer_main.collision_resource = generateBinaryLayer(level_name, level_input.collision_resource, LayerType.COLLISION);
        layer_main.objects_resource = generateBinaryLayer(level_name, level_input.objects_resource, LayerType.OBJECTS);
        layer_main.hotspots_resource = generateBinaryLayer(level_name, level_input.hotspots_resource, LayerType.HOTSPOTS);

        return main_layer;
    }

    /**
      */
    private static void generateLayers(ArrayList<LayerInput> input_layers, ArrayList<Layer> output_layers) {
        int foreground_count = 0;
        int background_count = 0;

        foreach (LayerInput input_layer : input_layers) {
            Layer layer = new Layer();

            layer.tile_sheet = new Resource(ResourceType.DRAWABLE,
                    tilesheet_resources[input_layer.tile_index]);

            Resource resource = generateBinaryLayer(level_name, input_layer.resource, LayerType.BACKGROUND);

            if (input_layer.tile_index == THEME_LIGHTING) {
                layer.foreground = true;
                // name "hack" (should make cleaner code)
                ++foreground_count;
                resource.name += "_fore_" + foreground_count;
            }
            else {
                layer.foreground = false;
                // name "hack" (should make cleaner code)
                ++background_count;
                resource.name += "_back_" + background_count;
            }

            layer.resource = resource;

            layer.tile_width = 32;
            layer.tile_height = 32;
// !!!! TODO: check that ok !!!!
            layer.size_factor_x = input_layer.scroll_speed;
            layer.size_factor_y = input_layer.scroll_speed; // or leave default
            layer.moving_speed_x = 0.0f;
            layer.moving_speed_y = 0.0f;

            layers.add(layer);
        }
    }

    /**
      Return the last layer that is not an overlay (lighting theme),
       and removes it from the list.
      */
    private static LayerInput getMainLayer(ArrayList<LayerInput> layers) {
        for (int i=layers.length()-1; i>=0; --i) {
            if (layers[i].tile_index != THEME_LIGHTING) {
                return layers.remove(i);
            }
        }

    }

    /**
      */
    private static Resource generateBinaryLayer(String base_name, byte[] input_resource_stream, LayerType layer_type) {

        byte[] resource_stream = Mappings.remapStream(input_resource_stream, layer_type);

        String name = base_name;
        switch(layer_type) {
            case LayerType.BACKGROUND:
// !!!! TODO: all the background layers will have the same name !!!!
// => set different names here, or make sure it is done elsewhere!
//...
                break;
            case LayerType.COLLISION:
                name += "_collision";
                break;
            case LayerType.OBJECTS:
                name += "_objects";
                break;
            case LayerType.HOTSPOTS:
                name += "_hotspots";
                break;
            default:
                // do what?
                break;
        }

        writeBinaryFile(name + ".bin", resource_stream);

        return new Resource(name, ResourceType.RAW);
    }

    /**
      */
    private static void writeBinaryFile(String filename, byte[] bytestream);

        FileOutputStream output_file = new FileOutputStream(filename);
        BufferedOutputStream bytestream = new BufferedOutputStream(output_file);

        bytestream.write(stream);
        bytestream.flush();
        bytestream.close();
    }

}
