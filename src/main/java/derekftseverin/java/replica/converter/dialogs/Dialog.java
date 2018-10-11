/**
 * Class describing a Dialog extracted from an input XML dialog file.
 */

package derekftseverin.java.replica.converter.dialogs;


public class Dialog {

    private ArrayList<Conversation> conversations;


    Dialog() {
        conversations = new ArrayList<Conversation>();
    }

    public ArrayList<Conversation> getConversations() {
        return conversations;
    }

    public void addConversation(Conversation conversation) {
        conversations.add(conversation);
    }

    public void append(Dialog dialog) {
        if (dialog != null) {
            conversations.addAll(dialog.gerConversations());
        }
    }

}
