/*
  Main class to convert an Replica level binary file to the modified engine format.
*/

/*
  !!!! TODO !!!!
  - files should be created in corresponding directories
  - check if need to comvert filenames to urls, and where ot do it
  - see if need to throw exceptions in "main" or handle them (as per now)

*/


package derekftseverin.java.replica.converter;


public class ReplicaConverter {

// !!!! ???? TODO: add exceptions? ???? !!!!
//    public static void main(String[] args) throws Exception {
    public static void main(String[] args) {

        if ((args.length == 0) || (args.length > 3)) {
            usage();
            return; // useless
        }

// !!!! ???? TODO: should convert "filename" to uri here? ???? !!!!
        final String level_filename = args[0];
        final String dialogs1_filename = args[1];
        final String dialogs2_filename = args[2];

        try {
            Resource level_resource = LevelConverter.process(level_filename, dialogs1_filename, dialogs2_filename);
// !!!! ???? TODO: do something with resource? ???? !!!!
//...

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void usage() {
        System.err.println("Usage: ReplicaConverter <xml_level_input_file> [<xml_dialogs_file_1> [<xml_dialogs_file_2>]]");
        System.exit(1);
    }

}
