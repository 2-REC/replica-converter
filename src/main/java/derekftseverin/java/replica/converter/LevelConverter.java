/*
  Main class to convert a Replica level binary file to the modified engine format.
*/

/*
  !!!! TODO !!!!
  - files should be created in corresponding directories
  - check if need to comvert filenames to urls, and where to do it
  - see if need to throw exceptions in "main" or handle them (as per now)

*/


package derekftseverin.java.replica.converter;


public class LevelConverter {

    public static void process(String level_filename, String dialogs1_filename, String dialogs2_filename)
            throws Exception {

        LevelInput level_input = InputUtilities.parseInput(level_filename);

        String level_name = level_filename.replaceLast(".xml", "");
        Resource dialogs_resource = DialogUtilities.generateDialogs(level_name, dialogs1_filename, dialogs2_filename);

        Level level = generateLevel(level_name, level_input, dialogs_resource);

        Resource level_resource = generateLevelOutput(level_name, level);

        return level_resource;
    }

}
