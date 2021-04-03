import java.io.LineNumberReader;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.LinkedList;

public class User {
    static LinkedList<User> userList = new LinkedList<>();
    static int LastId = 0;
    transient LinkedList<Tweet> Tweets;
    transient LinkedList<pvChat> pvs;
    LinkedList<String> following;
    LinkedList<String> followers;
    LinkedList<String> blacklist;
    LinkedList<String> requests;
    LinkedList<String> notifs;
    LinkedList<String> mutelist;
    LinkedList<Category> categories;
    transient SavedMessage savedMessage;
    String name;
    String username;
    String password;
    String birthdate;
    String email;
    String number;
    String bio;
    String Id;
    String pageState;
    String lastseenState;
    boolean isactive;
    private String lastseen;
    int reportNumber;
    public User(String name, String username, String password, String birthdate,
                String email, String number, String bio){
        LastId++;
        this.Id = "Id"+LastId;
        this.Tweets = new LinkedList<>();
        this.pvs = new LinkedList<>();
        this.username = username;
        this.name = name;
        this.password = password;
        this.birthdate = birthdate;
        this.categories = new LinkedList<>();
        this.notifs = new LinkedList<>();
        this.email = email;
        this.number = number;
        this.bio = bio;
        this.isactive = true;
        this.lastseen = LocalDateTime.now().format(CLI.dtformatter);
        this.following = new LinkedList<>();
        this.followers = new LinkedList<>();
        this.blacklist = new LinkedList<>();
        this.requests = new LinkedList<>();
        this.pageState = "public";
        this.lastseenState = "all"; // all, nobody, people you've followed
        this.reportNumber = 0;
        this.savedMessage = new SavedMessage(this.Id);
        this.mutelist = new LinkedList<>();
        userList.add(this);
    }
    void addtweet(Tweet m){
        this.Tweets.add(m);
    }
    static int numberofusers(){
        return LastId;
    }
    void setPassword(String npassword){
        this.password = npassword;
    }
    void info(){
        System.out.println("name : "+this.name);
        System.out.println("username : "+this.username);
        System.out.println("email : "+this.email);
        System.out.println("phoneNumber : "+ this.number);
        System.out.println("birthDate : "+this.birthdate);
        System.out.println("bio : "+this.bio);
    }
    String showlastseen(){
        return this.lastseen;
    }
    void lastseennow(){
        this.lastseen = LocalDateTime.now().format(CLI.dtformatter);
    }
    static String id2username(String id){
        for (User user : userList) {
            if (user.Id.equals(id)){
                return user.username;
            }
        }
        return "";
    }
    static String username2id(String username){
        for (User user : userList) {
            if (user.username.equals(username)){
                return user.Id;
            }
        }
        return "";
    }
    static User getUser (String id){
        for (User user : userList) {
            if (user.Id.equals(id) && user.isactive){
                return user;
            }
        }
        return userList.get(0);
    }
}
