/**
 * Class providing methods to extract dialogs from input XML dialog files and to merge them into a new output XML dialog file.
 */

/*
 !!!! TODO !!!!
 - check required imports
 - check for exceptions...
 - check if xml error handler is working
*/


package derekftseverin.java.replica.converter.dialogs;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;


public static class DialogUtilities {

    /**
     * Extract the dialogs from 2 input XML dialog files (if provided), and merge them into one.
     *
     * @params
     *  level_name: Name of the current level, used as base for the dialogs file name.
     *  dialogs1_filename: Path to the first XML dialog file.
     *  dialogs2_filename: Path to the second XML dialog file.
     * @throws IOException if an error occurs while extracting a dialog or when writing the XML dialog file.
     */
    public static Resource generateDialogs(String level_name, String dialogs1_filename, String dialogs2_filename)
            throws IOException {
        if ((dialogs1_filename == null) && (dialogs2_filename == null)) {
            return null;
        }

        Dialog dialog1 = getDialog(dialogs1_filename, 0);
        Dialog dialog2 = getDialog(dialogs2_filename, (NPC_SELECT_DIALOG_2_1 - NPC_SELECT_DIALOG_1_1));

        dialog1 = mergeDialogs(dialog1, dialog2);
        if (dialog1 == null) {
            return null;

        String dialogs_name = level_name + "_dialogs";
        XmlDialogHandler.writeXml(dialogs_filename + ".xml", dialog1);

        return (new Resource(ResourceType.XML, dialogs_name));
    }


    /**
      * Extract the dialog from an XML dialog file (setting IDs)).
      *
      * @params
      *  dialog_filename: Path to the XML dialog file.
      *  start_id: Start index for the first Conversation ID.
      * @throws IOException if an error occurs while parsing the XML dialog file.
     */
    private static Dialog getDialog(String dialog_filename, int start_id) throws IOException {
        if (dialog_filename == null) {
            return null;
        }

        Dialog dialog = null;
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setValidating(true); //?

        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XmlDialogHandler handler = new XmlDialogHandler(start_id);
//            saxParser.parse(new File(convertToFileURL(dialog_filename)), handler);
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(handler);
            xmlReader.setErrorHandler(new XmlDialogErrorHandler()); //?
            xmlReader.parse(convertToFileURL(dialog_filename));

            dialog = handler.getDialog();

        } catch (ParserConfigurationException | SAXException | IOException e) {
// !!!! ???? TODO: ok? ???? !!!!
// or simply rethrow?
//            e.printStackTrace();
            throw new IOException("Error parsing dialog file " + dialog_filename , e);
        }

        return dialog;
    }

    /**
     * Merge the 2 dialogs into one (dialog1).
     *
     * @params
     *  dialog1: The first Dialog, in which to merge both (can be null).
     *  dialog2: The second Dialog, to merge in first one (can be null).
     */
    private static Dialog mergeDialogs(Dialog dialog1, Dialog dialog2) {
        if (dialog1 != null) {
            dialog1.append(dialog2);
        }
        else {
            dialog1 = dialog2;
        }
        return dialog1;
    }

}
