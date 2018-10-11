/**
 * Class describing a "Page" element from a conversation of a dialog.
 */

package derekftseverin.java.replica.converter.dialogs;


public class Page {
 
    private Resource image;
    private Resource text;
    private Resource title;


    Page(String image, String text, String title) {
        this.image = new Resource(ResourceType.DRAWABLE, image);
        this.text = new Resource(ResourceType.STRING, text);
        this.title = new Resource(ResourceType.STRING, title);
    }

    public Resource getImage() {
        return image;
    }

    public Resource getText() {
        return text;
    }

    public Resource getTitle() {
        return title;
    }

}


