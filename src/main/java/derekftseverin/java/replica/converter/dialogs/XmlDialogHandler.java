/**
 * Class providing methods to read and write XML Dialog files.
 */

/*
  !!!!  TODO !!!!
  - see for exceptions
  - see for error handler
  - see if "characters" method is required
  - check if self-closing tags are ok
  - check for filename or url in "writeXml"
  - add methods headers...
*/

package derekftseverin.java.replica.converter.dialogs;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XmlDialogHandler extends DefaultHandler {

    private Dialog dialog;
    private int next_id;


    Dialog() {
        next_id = 0;
    }

    Dialog(int next_id) {
        this.next_id = next_id;
    }

    public Dialog getDialog() {
        return dialog;
    }

    /**
     * Called when the start tag of a new element is read.
     * Create the corresponding data structures and fill them with the requirrd data.
     *
     * Overridden from "DefaultHandler", see base class for more details.
     */
    @Override
    public void startElement(String uri, String local_name, String name, Attributes attributes)
            throws SAXException {

        if (name.equalsIgnoreCase("dialog")) {
            dialog = new Dialog();
        }
        else if (name.equalsIgnoreCase("conversation")) {
            conversation = new Conversation(next_id);
            ++next_id;
        }
        else if (name.equalsIgnoreCase("page")) {
            String image = attributes.getValue("image");
            String text = attributes.getValue("text");
            String title = attributes.getValue("title");

            Page page = new Page(image, text, title);
            conversation.addPage(page);
        }

    }

    /**
     * Called when the end tag of an element is read.
     * If it is a Conversation, it is added to the Dialog structure.
     *
     * Overridden from "DefaultHandler", see base class for more details.
     */
    @Override
    public void endElement(String uri, String local_name, String name) throws SAXException {
        if (name.equalsIgnoreCase("conversation")) {
            dialog.addConversation(conversation);
        }
    }

// !!!! TODO: might not be necessary !!!!
/*
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

    }
*/


    /**
     * Write an XML file containing the Dialog data.
     *
     * @params
     *  filename: Path of the iutput XML to write.
     *  dialog: Dialog structure containing the dialog data.
     * @throws IOException if an error occurs while writing the output file.
     */
    public static String writeXml(String filename, Dialog dialog) throws IOException {
// !!!! ???? TODO: filename or url? ???? !!!!
        String xml_url = Utilities.convertToFileURL(filename);

        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        try {
//?            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new FileOutputStream(filename), "UTF-8");
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new FileOutputStream(xml_url), "UTF-8");

            xmlStreamWriter.writeStartDocument("UTF-8", "1.0");
            xmlStreamWriter.writeCharacters("\n");

            xmlStreamWriter.writeStartElement("dialog");

            foreach (Conversation conversation : dialog.getConversations()) {
                xmlStreamWriter.writeCharacters("\n\t");
                xmlStreamWriter.writeStartElement("conversation");
                xmlStreamWriter.writeAttribute("id", conversation.getId());

                foreach (Page page : conversation.getPages()) {
                    xmlStreamWriter.writeCharacters("\n\t\t");
// !!!! TODO: check if ok !!!!
//                    xmlStreamWriter.writeStartElement("page");
                    xmlStreamWriter.writeEmptyElement("page");

                    xmlStreamWriter.writeAttribute("image", page.getImage().stringValue());
                    xmlStreamWriter.writeAttribute("text", page.getText().stringValue());
                    xmlStreamWriter.writeAttribute("title", page.getTitle().stringValue());

// !!!! ???? TODO: possible to write "self-closing" tags""? ???? !!!!
//                    xmlStreamWriter.writeCharacters("\n\t\t");
//                    xmlStreamWriter.writeEndElement(); // "page"
                }

                xmlStreamWriter.writeCharacters("\n\t");
                xmlStreamWriter.writeEndElement(); // "conversation"
            }

            xmlStreamWriter.writeCharacters("\n");
            xmlStreamWriter.writeEndElement(); // "dialog"

            xmlStreamWriter.writeEndDocument();

            xmlStreamWriter.flush();
            xmlStreamWriter.close();
        }
        catch (XMLStreamException | FileNotFoundException e) {
// !!!! ???? TODO: ok? ???? !!!!
//            e.printStackTrace();
            throw new IOException("Error writing XML dialog file!", e);
        }

// !!!! ???? TODO: useful? ???? !!!!
        return xml_url;
    }


    /**
      * Error management class.
      */
    class XmlDalogErrorHandler implements ErrorHandler {

        public void warning(SAXParseException e) throws SAXException {
            show("Warning", e);
            throw(e); // or don't throw anything?
        }

        public void error(SAXParseException e) throws SAXException {
            show("Error", e);
            throw(e); // or SAXException?
        }

        public void fatalError(SAXParseException e) throws SAXException {
            show("Fatal Error", e);
            throw(e); // or SAXException?
        }

        private void show(String type, SAXParseException e) {
            System.out.println(type + ": " + e.getMessage());
            System.out.println("Line " + e.getLineNumber() + " Column " + e.getColumnNumber());
            System.out.println("System ID: " + e.getSystemId());
        }

    }

}
