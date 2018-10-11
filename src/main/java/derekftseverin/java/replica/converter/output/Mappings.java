/**
 * Class providing the mappings between the input and output indexes,
 *  as they can be different in both implementations.
 */

/*
 TODO:
 - maps shouldn't be hardcoded but read in files (no file => no mapping)
 - if objects are not the same in both factories, "OBJECTS_MAPPING" mapping has to be defined
 - could add mappings for the background tilesets and for the collision layer if needed

*/

package derekftseverin.java.replica.converter.output;


public class Mappings {

    private static final Map<int, int> OBJECTS_MAPPING = null;

    private static final Map<int, int> HOTSPOTS_MAPPING;
    static {
        Map<int, int> tmp_map = new Map<int, int>();
        tmp_map.put(-1, -1); // NONE
        tmp_map.put(0, 0); // GO_RIGHT
        tmp_map.put(1, 1); // GO_LEFT
        tmp_map.put(2, 2); // GO_UP
        tmp_map.put(3, 3); // GO_DOWN
        tmp_map.put(4, 10); // WAIT_SHORT
        tmp_map.put(5, 11); // WAIT_MEDIUM
        tmp_map.put(6, 12); // WAIT_LONG
        tmp_map.put(7, 13); // ATTACK
        tmp_map.put(8, 14); // TALK
        tmp_map.put(9, 15); // DIE
        tmp_map.put(10, 16); // WALK_AND_TALK
        tmp_map.put(11, 17); // TAKE_CAMERA_FOCUS
        tmp_map.put(12, 18); // RELEASE_CAMERA_FOCUS
        tmp_map.put(13, 19); // END_LEVEL
        tmp_map.put(14, 20); // GAME_EVENT
        tmp_map.put(15, 21); // NPC_RUN_QUEUED_COMMANDS
        tmp_map.put(16, 22¥; // NPC_GO_RIGHT
        tmp_map.put(17, 23); // NPC_GO_LEFT
        tmp_map.put(18, 24); // NPC_GO_UP
        tmp_map.put(19, 25); // NPC_GO_DOWN
        tmp_map.put(20, 26); // NPC_GO_UP_RIGHT
        tmp_map.put(21, 27); // NPC_GO_UP_LEFT
        tmp_map.put(22, 28); // NPC_GO_DOWN_LEFT
        tmp_map.put(23, 29); // NPC_GO_DOWN_RIGHT
        tmp_map.put(24, 30); // NPC_GO_TOWARDS_PLAYER
        tmp_map.put(25, 31); // NPC_GO_RANDOM
        tmp_map.put(26, 32); // NPC_GO_UP_FROM_GROUND
        tmp_map.put(27, 33); // NPC_GO_DOWN_FROM_CEILING
        tmp_map.put(28, 34); // NPC_STOP
        tmp_map.put(29, 35); // NPC_SLOW
        tmp_map.put(32, 100); // NPC_SELECT_DIALOG_1_1
        tmp_map.put(33, 101); // NPC_SELECT_DIALOG_1_2
        tmp_map.put(34, 102); // NPC_SELECT_DIALOG_1_3
        tmp_map.put(35, 103); // NPC_SELECT_DIALOG_1_4
        tmp_map.put(36, 104); // NPC_SELECT_DIALOG_1_5
        tmp_map.put(38, 106); // NPC_SELECT_DIALOG_2_1
        tmp_map.put(39, 107); // NPC_SELECT_DIALOG_2_2
        tmp_map.put(40, 108); // NPC_SELECT_DIALOG_2_3
        tmp_map.put(41, 109); // NPC_SELECT_DIALOG_2_4
        tmp_map.put(42, 110); // NPC_SELECT_DIALOG_2_5
        HOTSPOTS_MAPPING = Collections.unmodifiableMap(tmp_map);
    }


    /**
     *
     */
    public static byte[] remapStream(byte[] input_stream, LayerType layer_type, int start_offset) {

        Map<int, int> mapping = getMapping(layer_type);
        if (mapping == null) {
// !!!! TODO: should probably return a copy !!!!
            return input_stream;
        }

// !!!! TODO: should check that the length is valid !!!!
        final int length = input_stream.length;
        byte[] stream = new byte[length];

        for (int i=0; i<start_offset; ++i) {
            stream[i] = input_stream[i];
        }

        for (int i=start_offset; i<length; ++i) {
            stream[i] = mapping[input_stream[i]];
        }

        return stream;
    }

    /**
     *
     */
// !!!! TODO: should be a Map instead of a method !!!!
    private Map<int, int> getMapping(LayerType layer_type) {

        Map<int, int> mapping = null;
        switch(layer_type) {
/*
            case LayerType.BACKGROUND:
                break;
            case LayerType.COLLISION:
                break;
*/
            case LayerType.OBJECTS:
                mapping = OBJECTS_MAPPING;
                break;
            case LayerType.HOTSPOTS:
                mapping = HOTSPOTS_MAPPING;
                break;
            default:
                // do nothing
                break;
        }
        return mapping;
    }

}
