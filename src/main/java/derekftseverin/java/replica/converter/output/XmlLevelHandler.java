/**
 * Class providing methods to write XML Level files.
 */

/*
 TODO:
 - could break "writeXml" in smaller methods
*/

package derekftseverin.java.replica.converter.output;


public class XmlLevelHandler {

    public static String writeXml(String filename, Level level) {
// !!!! ???? OK? ???? !!!!
        String xml_url = Utilities.convertToFileURL(filename);

        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        try {
//?            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new FileOutputStream(filename), "UTF-8");
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new FileOutputStream(xml_url), "UTF-8");

            xmlStreamWriter.writeStartDocument("UTF-8", "1.0");
            xmlStreamWriter.writeCharacters("\n");

            xmlStreamWriter.writeStartElement("level");

            // Background
            Background background = level.backgrougnd;
            xmlStreamWriter.writeCharacters("\n\t");
//            xmlStreamWriter.writeStartElement("background");
// !!!! TODO: check if ok !!!!
            xmlStreamWriter.writeEmptyElement("background");
            xmlStreamWriter.writeAttribute("resource", background.resource.stringValue());
            xmlStreamWriter.writeAttribute("sizeFactorX", background.size_factor_x);
            xmlStreamWriter.writeAttribute("sizeFactorY", background.size_factor_y);
            xmlStreamWriter.writeAttribute("width", background.width);
            xmlStreamWriter.writeAttribute("height", background.height);
            xmlStreamWriter.writeAttribute("ratio", background.keep_width);
// !!!! ???? TODO: possible to write "self-closing" tags""? ???? !!!!
//            xmlStreamWriter.writeCharacters("\n\t");
//            xmlStreamWriter.writeEndElement(); // "background"


            // LayerMain
            LayerMain layer_main = level.layer_main;
            xmlStreamWriter.writeCharacters("\n\t");
//            xmlStreamWriter.writeStartElement("layerMain");
// !!!! TODO: check if ok !!!!
            xmlStreamWriter.writeEmptyElement("layeMain");
            xmlStreamWriter.writeAttribute("tilesheet", layer_main.tile_sheet.stringValue());
            xmlStreamWriter.writeAttribute("tileWidth", layer_main.tile_width);
            xmlStreamWriter.writeAttribute("tileHeight", layer_main.tile_height);
            xmlStreamWriter.writeAttribute("resource", layer_main.resource.stringValue());
            xmlStreamWriter.writeAttribute("collisionResource", layer_main.collision_resource.stringValue());
            xmlStreamWriter.writeAttribute("objectsResource", layer_main.objects_resource.stringValue());
            xmlStreamWriter.writeAttribute("hotspotsResource", layer_main.hotspots_resource.stringValue());
// !!!! ???? TODO: possible to write "self-closing" tags""? ???? !!!!
//            xmlStreamWriter.writeCharacters("\n\t");
//            xmlStreamWriter.writeEndElement(); // "layerMain"


            // Layers
            foreach (Layer layer : level.layers) {
                xmlStreamWriter.writeCharacters("\n\t");
//                xmlStreamWriter.writeStartElement("layer");
// !!!! TODO: check if ok !!!!
                xmlStreamWriter.writeEmptyElement("layer");
                xmlStreamWriter.writeAttribute("tilesheet", layer.tile_sheet.stringValue());
                xmlStreamWriter.writeAttribute("tileWidth", layer.tile_width);
                xmlStreamWriter.writeAttribute("tileHeight", layer.tile_height);
                xmlStreamWriter.writeAttribute("resource", layer.resource.stringValue());
                xmlStreamWriter.writeAttribute("foreground", layer.foreground);
                xmlStreamWriter.writeAttribute("sizeFactorX", layer.size_factor_x);
                xmlStreamWriter.writeAttribute("sizeFactorY", layer.size_factor_y);
                xmlStreamWriter.writeAttribute("movingSpeedX", layer.moving_speed_x);
                xmlStreamWriter.writeAttribute("movingSpeedY", layer.moving_speed_y);
// !!!! ???? TODO: possible to write "self-closing" tags""? ???? !!!!
//                xmlStreamWriter.writeCharacters("\n\t");
//                xmlStreamWriter.writeEndElement(); // "layer"
            }


            // Dialogs
            if (level.dialogs != null) {
                xmlStreamWriter.writeCharacters("\n\t");
//                xmlStreamWriter.writeStartElement("dialogs");
// !!!! TODO: check if ok !!!!
                xmlStreamWriter.writeEmptyElement("dialogs");
                xmlStreamWriter.writeAttribute("resource", level.dialogs.resource.stringValue());
// !!!! ???? TODO: possible to write "self-closing" tags""? ???? !!!!
//                xmlStreamWriter.writeCharacters("\n\t");
//                xmlStreamWriter.writeEndElement(); // "dialogs"
            }


            xmlStreamWriter.writeCharacters("\n");
            xmlStreamWriter.writeEndElement(); // "level"

            xmlStreamWriter.writeEndDocument();

            xmlStreamWriter.flush();
            xmlStreamWriter.close();
        }
        catch (XMLStreamException | FileNotFoundException e) {
// !!!! TODO: see what to do with error handling !!!!
            e.printStackTrace();
        }

// !!!! ???? TODO: useful? ???? !!!!
        return xml_url;
    }

}
