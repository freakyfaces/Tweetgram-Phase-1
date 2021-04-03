public class Explorer {
    static void main(){
        System.out.println("1-search\n2-explore\n3-back");
        String order = CLI.input.next();
        if (order.equals("exit")){
            CLI.exit();
        }
        if (order.equals("3")){
            return;
        }
        else if (order.equals("1")){
            System.out.println("enter his/her username!");
            String username = Logic.input.next();
            searchprof(CLI.activeuser, username);
        }
        else if (order.equals("2")){
            explore();
        }
    }
    static void searchprof(User activeuser, String username){
        String[] follow = {"no","no"};
        User user = User.getUser(User.username2id(username));
        follow = Logic.checkfollow(user, activeuser);
        System.out.println("name: " + user.name);
        System.out.println("username: "+user.username);
        if (follow[0].equals("yes") && !user.lastseenState.equals("nobody")){
            System.out.println("last seen: "+user.showlastseen());
        }
        else {
            System.out.println("last seen: " + "last seen recently");
        }
        System.out.println("followed: " + follow[1]);

        String order = "";
        System.out.println("1-message\n2-follow\n3-block\n4-report\n5-add to mute list\n6-show tweets\n7-back");
        order = Logic.input.next();
        if (order.equals("1")){
            if (!follow[1].equals("yes")){
                System.out.println("you have not followed this user yet ! you cant message this user !");
            }
            else{
                for (pvChat pv : activeuser.pvs) {
                    if (pv.id1.equals(User.username2id(username)) || pv.id2.equals(User.username2id(username))){
                        order = CLI.input.next();
                        if (order.equals("exit")){
                            CLI.exit();
                        }
                        pv.sendMsg(order, activeuser.Id);
                        return;
                    }
                }
                Messaging.messaging(username);
            }
        }
        else if (order.equals("2")){
            if (follow[1].equals("yes")){
                System.out.println("you've already followed this user!");
            }
            else{
                if (!user.blacklist.equals(activeuser.Id)){
                    if (user.pageState.equals("public")){
                        user.followers.add(activeuser.Id);
                        activeuser.following.add(user.Id);
                        user.notifs.add(activeuser.username +" followed you !");
                    }
                    else{
                        user.requests.add(activeuser.Id);
                        System.out.println("your request has been sent!");
                        main();
                    }
                }
            }
        }
        else if (order.equals("3")){
            activeuser.blacklist.add(user.username);
        }
        else if (order.equals("4")){
            user.reportNumber++;
        }
        else if (order.equals("5")){
            activeuser.mutelist.add(user.Id);
        }
        else if (order.equals("6")){
            Logic.showTweets(user.Tweets);
        }
        else if (order.equals("7")){
            return;
        }
        else if (order.equals("exit")){
            CLI.exit();
        }
    }
    static void explore(){
        for (User user : User.userList) {
            if (!CLI.activeuser.following.contains(user.Id)){
                Tweet tweet = new Tweet("ah",CLI.activeuser.Id);
                int max = 0;
                for (Tweet tweet1 : user.Tweets) {
                    if (tweet1.likes.size() >= max && User.getUser(tweet1.Id).isactive &&
                            !User.getUser(tweet1.Id).pageState.equals("private")){
                        tweet = tweet1;
                        max = tweet1.likes.size();
                    }
                }
                if (!(tweet.text.equals("ah") && tweet.Id.equals(CLI.activeuser.Id))){
                    Logic.showTweet(tweet);
                    System.out.println("type back if you want to");
                    String order = CLI.input.next();
                    if (order.equals("back")){
                        return;
                    }
                    if (order.equals("exit")){
                        CLI.exit();
                    }
                }
            }
        }
    }
}
