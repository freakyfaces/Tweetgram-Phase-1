public class TimeLine {
    static void main(){
        int i = 0 ;
        for (String s : CLI.activeuser.following) {
            if (User.getUser(s).isactive && (i+1)<=User.getUser(s).Tweets.size() && !CLI.activeuser.mutelist.contains(s)){
                Logic.showTweets(User.getUser(s).Tweets);
                i = 1;
            }
        }
        if (i == 0){
            System.out.println("theres nothing here to show");
        }
    }
}
