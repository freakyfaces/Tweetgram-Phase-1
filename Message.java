import java.time.LocalDateTime;


public class Message {
    String reciever;
    String text;
    String giver;
    String dateTime;
    boolean seen ;
    public Message(String rId, String gId, String text) {
        this.reciever = rId;
        this.giver = gId;
        this.text = text;
        this.dateTime = LocalDateTime.now().format(CLI.dtformatter);
        this.seen = false;
    }
    public Message(String rId, String gId, Tweet message) {
        this.reciever = rId;
        this.giver = gId;
        this.text = message.out();
        this.dateTime = LocalDateTime.now().format(CLI.dtformatter);
        this.seen = false;
    }
    void edit(String text){
        this.text = text;
    }
    @Override
    public String toString(){
        return this.text;
    }

}
