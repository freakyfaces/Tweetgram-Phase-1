import java.time.LocalDateTime;
import java.util.LinkedList;

public class Tweet {
    String text;
    LinkedList<Tweet> comments;
    String datetime;
    LinkedList<String> likes;
    String Id;
    public Tweet(String text, String Id) {
        this.Id = Id;
        this.text = text;
        this.likes = new LinkedList<>();
        this.comments = new LinkedList<>();
        this.datetime = LocalDateTime.now().format(CLI.dtformatter);
    }
    public Tweet(Tweet tweet){
        this.Id = tweet.Id;
        this.text = tweet.text;
        this.likes = tweet.likes;
        this.comments = tweet.comments;
        this.datetime = tweet.datetime;
    }
    void edit(String text){
        this.text = text;
    }
    String out(){
        return this.text + "--- tweeted at " + this.datetime +" by "+ User.id2username(this.Id);
    }
    void showcms(){
        for (Tweet i : this.comments) {
            i.out();
        }
    }

}
