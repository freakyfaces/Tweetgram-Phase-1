import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Scanner;

public class Logic {
    static Logger logger = Logger.getLogger(Logic.class);;
    static Scanner input = new Scanner(System.in).useDelimiter("\n");
    static GsonBuilder gsonBuilder = new GsonBuilder();
    static Gson gson = gsonBuilder.setPrettyPrinting().create();
    static boolean checkusername(String username){
        for (User user : User.userList) {
            if (user.username.equals(username) && user.isactive) {
                return true;
            }
        }
        return false;
    }
    static boolean checkemail(String email){
        for (User user : User.userList) {
            if (user.email.equals(email) && user.isactive) {
                return true;
            }
        }
        return false;
    }
    static boolean checkpass(String username, String password){
        for (User i: User.userList) {
            if (i.password.equals(password) && i.username.equals(username)){
                CLI.activeuser = i;
                CLI.state = "login";
                return true;
            }
        }
        return false;
    }
    static void savedata(){
        try {
            FileWriter writer = new FileWriter("data/Users.json");
            gson.toJson(User.userList, writer);
            writer.flush();
            writer.close();
            for (User user : User.userList) {
                savelist(user, user.Tweets, "Tweets");
                savelist(user, user.pvs, "PVs");
                FileWriter nwriter = new FileWriter("data/"+"savedMessage"+user.Id+".json");
                gson.toJson(user.savedMessage,nwriter);
                nwriter.flush();
                nwriter.close();
            }
        }
        catch (IOException e){
            logging("error", "error in saving data");
            e.printStackTrace();
        }
    }
    static void readdata(){
        try {
            Type listType = new TypeToken<LinkedList<User>>(){}.getType();
            User.userList = gson.fromJson(new FileReader("data/Users.json"),listType);
            User.LastId = User.userList.size();
            for (User user : User.userList) {
                readlist(user, "PVs");
                readlist(user, "tweet");
                try{
                    user.savedMessage = gson.fromJson(new FileReader("data/"+"savedMessage"+user.Id+".json")
                        ,SavedMessage.class);
                }
                catch (FileNotFoundException ee){
                    user.savedMessage = new SavedMessage(user.Id);
                }
            }
        }
        catch (FileNotFoundException e){
            logging("error", "data files not found");
            User.userList = new LinkedList<>();
        }
    }
    static void readlist(User activeuser, String name){
        Type pvsType = new TypeToken<LinkedList<pvChat>>(){}.getType();
        Type tweetsType = new TypeToken<LinkedList<Tweet>>(){}.getType();
        try {
            if (name.equals("tweet")){
                activeuser.Tweets = gson.fromJson(new FileReader("data/"+"Tweets"+activeuser.Id+".json"),tweetsType);
            }
            else{
                activeuser.pvs = gson.fromJson(new FileReader("data/"+"PVs"+activeuser.Id+".json"),pvsType);
            }
        }
        catch (FileNotFoundException e){
            logging("error", "data files not found");
            if (name.equals("tweet")){
                activeuser.Tweets = new LinkedList<Tweet>();
            }
            else{
                activeuser.pvs = new LinkedList<pvChat>();
            }
        }
    }
    static void savelist(User activeuser, LinkedList list, String name){
        try {
            FileWriter writer = new FileWriter("data/"+name+activeuser.Id+".json");
            gson.toJson(list, writer);
            writer.flush();
            writer.close();
        }
        catch (IOException e){
            logging("error", "error in saving data");
            e.printStackTrace();
        }
    }
    static void login(){
        String ans = "";
        System.out.println("do you have an account!?(yes/no)");
        ans = input.next();
        if (ans.equals("yes")){
            System.out.println("enter your username!");
            String username = input.next();
            if (username.equals("exit")){
                CLI.exit();
            }
            while (!checkusername(username)){
                System.out.println("unvalid username! enter your username again");
                username = input.next();
                if (username.equals("exit")){
                    CLI.exit();
                }
            }
            System.out.println("enter your password!");
            String password = "";
            for (int i = 0; i < 3; i++) {
                password = input.next();
                if (!checkpass(username, password)) {
                    System.out.println("password was wrong ! enter it again!");
                }
                else{
                    CLI.state = "main page";
                    System.out.println("successfully logined!");
                    CLI.activeuser = User.getUser(User.username2id(username));
                    logging("info", "user " + CLI.activeuser.Id+" logined successfully");
                    return;
                }
            }
            System.out.println("sorry you entered password wrong for 3 times!");
        }
        else if (ans.equals("no")){
            String[] souts = {"enter your username!", "enter your password!", "enter your name (like this : 'john smith')!"
            , "enter your email!", "enter your date of birth like this : 2002 5 5 (you can type nan if you dont want to)!"
                    , "enter your number (you can type nan if you dont want to)!", "enter your bio (you can type nan if you dont want to)!"};
            String [] inputs = new String[7];
            LocalDate date = LocalDate.of(1000, 1,1);
            for (int i = 0; i < souts.length ; i++) {
                System.out.println(souts[i]);
                if (i == 0){
                    inputs[0] = input.next();
                    while (checkusername(inputs[0])){
                        System.out.println("this username is already taken! enter another username!");
                        inputs[0] = input.next();
                    }
                }
                else if (i == 3){
                    inputs[3] = input.next();
                    while (checkemail(inputs[3])){
                        System.out.println("this email is already taken! enter another email!");
                        inputs[3] = input.next();
                    }
                }
                else if (i == 4){
                    inputs[4] = input.next();
                    if (!inputs[4].equals("nan")){
                        String[] dates = inputs[4].split("\\s+");
                        date = LocalDate.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]),Integer.parseInt(dates[2]));
                    }
                }
                else{
                    inputs[i] = input.next();
                }
                if (inputs[i].equals("exit")){
                    CLI.exit();
                }
            }
            CLI.activeuser = new User(inputs[2], inputs[0], inputs[1], date.format(CLI.dformatter), inputs[3], inputs[5], inputs[6]);
            CLI.state = "main page";
            logging("info", "user " + CLI.activeuser.Id+" signed up successfully");
            System.out.println("account successfuly created!");
        }
        else if (ans.equals("exit")){
            CLI.exit();
        }
        else{
            System.out.println("that was not meaningful");
        }
    }
    static void tweeting(User activeuser){
        System.out.println("enter your tweet!");
        String tweet = input.next();
        Tweet tweet1 = new Tweet(tweet, activeuser.Id);
        activeuser.addtweet(tweet1);
        System.out.println("tweet is now shared");
    }
    static void showTweets(LinkedList<Tweet> Tweets){
        if (Tweets.equals(new LinkedList<Tweet>())){
            System.out.println("There's no tweets/comments here!");
        }
        for (Tweet tweet : Tweets) {
            showTweet(tweet);
        }
    }
    static void showFLists(LinkedList<String> list, User activeuser){
        int check = 0;
        for (String s : list) {
            User user = User.getUser(s);
            if (user.isactive){
                System.out.println(User.id2username(s));
                check = 1;
            }
        }
        if (check == 0){
            System.out.println("there's no one here!");
            return;
        }
        System.out.println("1-see someone profile\n2-back");
        String order = input.next();
        if (order.equals("1")){
            System.out.println("enter his/her username!");
            String username = Logic.input.next();
            if (username.equals("exit")){
                CLI.exit();
            }
            Explorer.searchprof(activeuser, username);
        }
        else if (order.equals("2")){
            return;
        }
    }
    static void editprof(User activeuser){
        System.out.println("which one do you want to edit?");
        System.out.println("1-username\n2-name\n3-password\n4-email\n5-phone number\n6-birthdate\n7-bio\n8-back");
        String ans = input.next();
        if (ans.equals("8")){
            CLI.state = "login";

        }
        else if (ans.equals("1")){
            System.out.println("enter your new username");
            String username = input.next();
            if (username.equals("exit")){
                CLI.exit();
            }
            while (checkusername(username)){
                System.out.println("this username is already taken ! choose another one!");
                username = input.next();
                if (username.equals("exit")){
                    CLI.exit();
                }
            }
            activeuser.username = username;
            logging("info", "user " + activeuser.Id +" changed username");
            System.out.println("username changed!");
            return;
        }
        else if (ans.equals("2")){
            System.out.println("enter your name!");
            String name = input.next();
            activeuser.name = name;
            if (name.equals("exit")){
                CLI.exit();
            }
            logging("info", "user " + activeuser.Id+" changed name");
            System.out.println("name changed!");
            return;
        }
        else if (ans.equals("3")){
            System.out.println("enter your new password");
            String password = input.next();
            if (password.equals("exit")){
                CLI.exit();
            }
            activeuser.setPassword(password);
            logging("info", "user " + activeuser.Id+" changed password");
            System.out.println("password changed!");
            return;
        }
        else if (ans.equals("4")){
            System.out.println("enter your new email");
            String email = input.next();
            while (checkemail(email)){
                System.out.println("this email is already taken ! choose another one!");
                email = input.next();
                if (email.equals("exit")){
                    CLI.exit();
                }
            }
            activeuser.email = email;
            logging("info", "user " + activeuser.Id+" email username");
            System.out.println("email changed!");
            return;
        }
        else if (ans.equals("5")){
            System.out.println("enter your new PNumber");
            String phonenumber = input.next();
            if (phonenumber.equals("exit")){
                CLI.exit();
            }
            activeuser.number = phonenumber;
            logging("info", "user " + activeuser.Id+" changed phoneNumber");
            System.out.println("phoneNumber changed!");
            return;
        }
        else if (ans.equals("6")){
            System.out.println("enter your birthdate!");
            String birthdate = input.next();
            if (birthdate.equals("exit")){
                CLI.exit();
            }
            String[] dates = birthdate.split("\\s+");
            activeuser.birthdate = LocalDate.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]),Integer.parseInt(dates[2])).format(CLI.dformatter);
            logging("info", "user " + activeuser.Id+" changed birthdate");
            System.out.println("birthdate changed!");
            return;
        }
        else if (ans.equals("7")){
            System.out.println("enter your new bio!");
            String bio = input.next();
            if (bio.equals("exit")){
                CLI.exit();
            }
            activeuser.bio = bio;
            logging("info", "user " + activeuser.Id+" changed bio");
            System.out.println("bio changed!");
            return;
        }
    }
    static String[] checkfollow(User user1, User user2){
        String[] state = {"no", "no"};
        for (String s : user1.following) {
            if (s.equals(user2.Id)){
                state[0] =  "yes"; // user 1 follows user 2
            }
        }
        for (String s: user2.following){
            if (s.equals(user1.Id)){
                state[1] = "yes"; // user 2 follows user 1
            }
        }
        return state;
    }
    static void showTweet(Tweet tweet){
        System.out.println("1-next 2-retweet 3-show comments 4-message this tweet \n" +
                "5-comment 6-like 7-writer of this tweet settings 8-back");
        System.out.println(tweet.out());
        String order = input.next();
        if (order.equals("exit")){
            CLI.exit();
        }
        else if (order.equals("1")){
            return;
        }
        else if (order.equals("2")){
            Retweet retweet = new Retweet(tweet, CLI.activeuser.Id);
            CLI.activeuser.Tweets.add(retweet);
        }
        else if (order.equals("3")){
            showTweets(tweet.comments);
        }
        else if (order.equals("4")){
            System.out.println("1-to yourself 2-others");
            order = CLI.input.next();
            if (order.equals("exit")){
                CLI.exit();
            }
            else if (order.equals("1")){
                CLI.activeuser.savedMessage.saveMessage(tweet);
                System.out.println("done!");
            }
            else if (order.equals("2")){
                System.out.println("1-all 2-categories 3-another user");
                order = CLI.input.next();
                if (order.equals("exit")){
                    CLI.exit();
                }
                else if (order.equals("1")){
                    LinkedList<String> check = new LinkedList<>();
                    for (pvChat pv : CLI.activeuser.pvs) {
                        if (pv.id1.equals(CLI.activeuser.Id)){
                            pv.sendMsg(tweet, pv.id1);
                            check.add(pv.id2);
                        }
                        else{
                            pv.sendMsg(tweet, pv.id2);
                            check.add(pv.id1);
                        }
                    }
                    for (String s : CLI.activeuser.following) {
                        if (!check.contains(s)){
                            pvChat pv = new pvChat(CLI.activeuser.Id, s);
                            pv.sendMsg(tweet, CLI.activeuser.Id);
                        }
                    }
                    System.out.println("done!");
                }
            }
            else if (order.equals("3")){
                System.out.println("enter username");
                order = CLI.input.next();
                String[] checkf = checkfollow(CLI.activeuser, User.getUser(User.username2id(order)));
                if ((checkf[0].equals("yes") || checkf[1].equals("yes")) && User.getUser(order).isactive){
                    for (pvChat pv : CLI.activeuser.pvs) {
                        if (pv.id1.equals(User.username2id(order)) || pv.id2.equals(User.username2id(order))){
                            pv.sendMsg(tweet, CLI.activeuser.Id);
                        }
                    }
                }
                else{
                    System.out.println("you cant message someone that don");
                }
            }
        }
        else if (order.equals("5")){
            System.out.println("enter your comment");
            order = CLI.input.next();
            if (order.equals("exit")){
                CLI.exit();
            }
            Tweet cm = new Tweet(order, CLI.activeuser.Id);
            tweet.comments.add(cm);
        }
        else if (order.equals("6")){
            if (!tweet.likes.contains(CLI.activeuser.Id)){
                tweet.likes.add(CLI.activeuser.Id);
                System.out.println("tweet liked");
            }
            else{
                System.out.println("you've already liked this tweet before!");
            }
        }
        else if (order.equals("7")){
            Explorer.searchprof(CLI.activeuser, User.id2username(tweet.Id));
        }
        else if (order.equals("8")){
            return;
        }
    }
    static void logging(String lvl, String msg){
        if (lvl.equals("info")){
            logger.info(msg);
        }
        else if (lvl.equals("error")){
            logger.error(msg);
        }

    }
}