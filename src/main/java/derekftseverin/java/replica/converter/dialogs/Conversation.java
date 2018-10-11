/**
 * Class describing a "Conversation" element from a dialog.
 */

package derekftseverin.java.replica.converter.dialogs;


public class Conversation  {

    private int id;
    private ArrayList<Page> pages;


    Conversation(int id) {
        this.id = id;
        this.pages = new ArrayList<Page>();
    }

    public int getId() {
        return id;
    }

    public ArrayList<Page> getPages() {
        return pages;
    }

    public void addPage(Page page)
        pages.add(page);
    }

}
