import java.util.LinkedList;

public class SavedMessage{
    LinkedList<Message> messages;
    String id;
    public SavedMessage (String id){
        this.id = id;
        this.messages = new LinkedList<>();
    }
    public void saveMessage(Tweet tweet){
        Message message = new Message(this.id, this.id, tweet.out());
        messages.add(message);
    }
    public void saveMessage(String text){
        Message message = new Message(this.id, this.id, text);
        messages.add(message);
    }
}
