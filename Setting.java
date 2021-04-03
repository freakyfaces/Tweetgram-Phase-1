public class Setting {
    static String order;
    static void main(){
        System.out.println("1-privacy\n2-delete account\n3-log out\n4-back");
        order = CLI.input.next();
        if (order.equals("exit")){
            CLI.exit();
        }
        else if (order.equals("1")) {
            privacy();
        }
        else if (order.equals("2")){
            deleteacc();
        }
        else if (order.equals("3")){
            CLI.state = "login";
            Logic.logging("info",CLI.activeuser.Id+" logged out");
            return;
        }
        else if (order.equals("4")){
            return;
        }
    }
    static void privacy(){
        System.out.println("1-page state\n2-last seen\n3-deactive account\n4-change password\n5-back");
        order = CLI.input.next();
        if (order.equals("exit")){
            CLI.exit();
        }
        if (order.equals("1")){
            System.out.println("1-private\n2-public\n3-back");
            order = CLI.input.next();
            if (order.equals("1")){
                CLI.activeuser.pageState = "private";
            }
            else if (order.equals("2")){
                CLI.activeuser.pageState = "public";
            }
            else if (order.equals("3")){
                main();
            }
            else if (order.equals("exit")){
                CLI.exit();
            }
        }
        else if (order.equals("2")){
            System.out.println("1-all\n2-nobody\n3-people you've followed\n4-back");
            order = CLI.input.next();
            if (order.equals("exit")){
                CLI.exit();
            }
            else if (order.equals("1")){
                CLI.activeuser.lastseenState = "all";
            }
            else if (order.equals("2")){
                CLI.activeuser.lastseenState = "nobody";
            }
            else if (order.equals("3")){
                CLI.activeuser.lastseenState = "followed";
            }
            else if (order.equals("4")){
                main();
            }
        }
        else if (order.equals("3")){
            System.out.println("do you wanna deactive your account(yes/no)");
            order = CLI.input.next();
            if (order.equals("exit")){
                CLI.exit();
            }
            else if (order.equals("yes")){
                CLI.activeuser.isactive = false;
            }
            else if (order.equals("no")){
                main();
            }
        }
        else if (order.equals("4")){
            System.out.println("enter your new password");
            order = CLI.input.next();
            CLI.activeuser.password = order;
        }
        else if (order.equals("5")){
            main();
        }
    }
    static void deleteacc(){
        System.out.println("do you wanna delete your account?(yes/no)");
        order = CLI.input.next();
        if (order.equals("yes")){
            CLI.activeuser.isactive = false;
            CLI.state = "login";
            Logic.logging("info", CLI.activeuser.Id+ " deleted account");
        }
        else if (order.equals("no")){
            return;
        }
        else if (order.equals("exit")){
            CLI.exit();
        }
    }
}
