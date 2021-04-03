import java.util.LinkedList;

public class PersonalPage {
    static void main(){
        System.out.println("1-tweet\n2-show tweets\n3-edit profile\n4-lists\n5-info\n6-notifs\n7-log out\n8-back");
        String order = CLI.input.next();
        CLI.activeuser.lastseennow();
        Logic.savedata();
        if (order.equals("1")){
            Logic.tweeting(CLI.activeuser);
            Logic.savedata();
        }
        else if (order.equals("2")){
            Logic.showTweets(CLI.activeuser.Tweets);
        }
        else if (order.equals("3")){
            Logic.editprof(CLI.activeuser);
            CLI.activeuser.lastseennow();
            Logic.savedata();
            CLI.state = "main page";
        }
        else if (order.equals("4")){
            System.out.println("1-followers\n2-followings\n3-black list");
            order = CLI.input.next();
            CLI.activeuser.lastseennow();
            Logic.savedata();
            if (order.equals("1")){
                Logic.showFLists(CLI.activeuser.followers, CLI.activeuser);
            }
            else if (order.equals("2")){
                Logic.showFLists(CLI.activeuser.following, CLI.activeuser);
            }
            else if (order.equals("3")){
                Logic.showFLists(CLI.activeuser.blacklist, CLI.activeuser);
            }
            else if (order.equals("exit")){
                CLI.exit();
            }
        }
        else if (order.equals("5")){
            CLI.activeuser.info();
            CLI.activeuser.lastseennow();
            Logic.savedata();
            System.out.println("enter back to continue");
            order = CLI.input.next();
            if (order.equals("back")){
                return;
            }
        }
        else if (order.equals("6")){
            shownotifs();
        }
        else if (order.equals("7")){
            CLI.state = "login";
            Logic.logging("info", CLI.activeuser.Id + "logged out");
        }
        else if (order.equals("exit")){
            CLI.exit();
        }
        else if (order.equals("8")){
            return;
        }
        else{
            System.out.println("enter something meaningful!");
        }
    }
    static void shownotifs(){
        System.out.println("1-system notifs\n2-follow requests\n3-back");
        String order = CLI.input.next();
        if (order.equals("exit")){
            CLI.exit();
        }
        else if (order.equals("1")){
            for (String notif : CLI.activeuser.notifs) {
                System.out.println(notif);
            }
        }
        else if (order.equals("2")){
            int i = 0 ;
             for (String request : CLI.activeuser.requests) {
                 i++;
                //CLI.activeuser.requests.remove(request);
                System.out.println("1-accept\n2-decline\n3-return");
                System.out.println(User.id2username(request)+" has requested to follow you");
                order = CLI.input.next();
                if (order.equals("1")){
                    CLI.activeuser.followers.add(request);
                    User user = User.getUser(request);
                    user.following.add(CLI.activeuser.Id);
                }
                else if (order.equals("exit")){
                    CLI.exit();
                }
                else if (order.equals("3")){
                    main();
                }
                else if (order.equals("2")){
                    System.out.println("1-send notif to this user 2-dont send");
                    String ans = CLI.input.next();
                    if (ans.equals("exit")){
                        CLI.exit();
                    }
                    else if (ans.equals("1")){
                        User.getUser(request).notifs.add(CLI.activeuser.username+" has declined your follow request");
                    }
                }
            }
            for (int j = 0; j < i; j++) {
                CLI.activeuser.requests.remove(j);
            }
        }
    }
}
