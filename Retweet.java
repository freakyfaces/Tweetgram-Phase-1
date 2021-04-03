
public class Retweet extends Tweet{
    String RetweeterID;
    public Retweet(Tweet tweet , String RetweeterID){
        super(tweet);
        this.RetweeterID = RetweeterID;
    }
}
