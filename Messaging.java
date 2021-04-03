import java.util.LinkedList;

public class Messaging {
    static String order;
    static void main(){
        System.out.println("1-saved messages\n2-chats\n3-message someone new\n4-categories\n5-back");
        order = CLI.input.next();
        if (order.equals("exit")){
            CLI.exit();
        }
        if (order.equals("1")){
            savedmessages();
        }
        else if (order.equals("2")){
            chats();
        }
        else if (order.equals("3")){
            System.out.println("1-one person\n2-more than one\n3-back");
            String order1 = CLI.input.next();
            if (order1.equals("exit")){
                CLI.exit();
            }
            else if (order1.equals("1")){
                System.out.println("enter username of the one you want to message");
                String username = CLI.input.next();
                messaging(username);
            }
            else if (order1.equals("2")){
                multimessage();
            }
            else if(order1.equals("3")){
                main();
            }

        }
        else if (order.equals("4")){
            categories();
        }
        else if (order.equals("5")){
            return;
        }
        else {
            System.out.println("enter something meaningfull");
            main();
        }
    }
    static void chats(){
        if (CLI.activeuser.pvs.equals(new LinkedList<>())){
            System.out.println("oops there is no one to see their messages ");
            return;
        }
        for (pvChat pv : CLI.activeuser.pvs) {
            if (!pv.id1.equals(CLI.activeuser.Id)  && pv.unreadid2 != 0){
                System.out.println(User.id2username(pv.id1) + " - unread: " + pv.unreadid2);
            }
            if (!pv.id2.equals(CLI.activeuser.Id) && pv.unreadid1 != 0){
                System.out.println(User.id2username(pv.id2) + " - unread: " + pv.unreadid1);
            }
        }
        for (pvChat pv : CLI.activeuser.pvs) {
            if (!pv.id1.equals(CLI.activeuser.Id) && pv.unreadid2 == 0){
                System.out.println(User.id2username(pv.id1));
            }
            if (!pv.id2.equals(CLI.activeuser.Id) && pv.unreadid1 == 0){
                System.out.println(User.id2username(pv.id2));
            }
        }
        System.out.println("1-chat with someone\n2-back");
        order = CLI.input.next();
        if (order.equals("1")){
            System.out.println("enter his/her username!");
            order = CLI.input.next();
            String id = User.username2id(order);
            for (pvChat pv : CLI.activeuser.pvs) {
                if (pv.id1.equals(id) || pv.id2.equals(id)){
                    System.out.println("1-see messages 2-message");
                    order = CLI.input.next();
                    if (order.equals("exit")){
                        CLI.exit();
                    }
                    else if (order.equals("1")){
                        for (int i = pv.messages.size() - 1; i >= 0; i--) {
                            Message message = pv.messages.get(i);
                            System.out.println(message.text+" at "+message.dateTime+ " from " +
                                    User.id2username(message.giver) +" to "+ User.id2username(message.reciever));
                            if (CLI.activeuser.Id.equals(pv.id1)){
                                pv.unreadid1 = 0 ;
                                for (pvChat pvChat : User.getUser(pv.id2).pvs) {
                                    if (pvChat.id1.equals(CLI.activeuser.Id)){
                                        pvChat.unreadid1 = 0;
                                    }
                                }
                            }
                            else if (CLI.activeuser.Id.equals(pv.id2)){
                                pv.unreadid2 = 0;
                                for (pvChat pvChat : User.getUser(pv.id1).pvs) {
                                    if (pvChat.id2.equals(CLI.activeuser.Id)){
                                        pvChat.unreadid2 = 0;
                                    }
                                }
                            }
                        }
                    }
                    else if (order.equals("2")){
                        System.out.println("enter your message here!");
                        String message = CLI.input.next();
                        pv.sendMsg(message, CLI.activeuser.Id);
                        return;
                    }
                }
            }
        }
        else if (order.equals("2")){
            main();
        }
    }
    static void messaging(String username){
        if (username.equals("exit")){
            CLI.exit();
        }
        while (!Logic.checkusername(username)){
            System.out.println("oops that username does not exist! enter username again");
            username = CLI.input.next();
            if (username.equals("exit")){
                CLI.exit();
            }
        }
        for (User user : User.userList) {
            if (user.username.equals(username)){
                String[] res = Logic.checkfollow(user, CLI.activeuser);
                if (res[1].equals("no")){
                    System.out.println("you have not followed this user yet! you cant message this user!");
                }
                else{
                    System.out.println("enter your message");
                    String message = CLI.input.next();
                    if (message.equals("exit")){
                        CLI.exit();
                    }
                    pvChat pv1 = new pvChat(User.username2id(username), CLI.activeuser.Id);
                    pv1.sendMsg(message, CLI.activeuser.Id);
                }
            }
        }
    }
    static void savedmessages(){
        System.out.println("1-show messages\n2-send new message\n3-back");
        order = CLI.input.next();
        if (order.equals("exit")){
            CLI.exit();
        }
        if (order.equals("3")){
            main();
        }
        if (order.equals("1")){
            if (CLI.activeuser.savedMessage.messages.equals(new LinkedList<>())){
                System.out.println("there is no messages here!");
            }
            else {
                for (Message message : CLI.activeuser.savedMessage.messages) {
                    System.out.println("--"+message.text+"-- sent at "+message.dateTime);
                }
            }
        }
        if (order.equals("2")){
            System.out.println("enter your message");
            order = CLI.input.next();
            if (order.equals("exit")){
                CLI.exit();
            }
            CLI.activeuser.savedMessage.saveMessage(order);
        }
    }
    static void multimessage(){
        System.out.println("enter your message!");
        String msg = CLI.input.next();
        if (msg.equals("exit")){
            CLI.exit();
        }
        System.out.println("1-categories\n2-custom people");
        order =CLI.input.next();
        if (order.equals("exit")){
            CLI.exit();
        }
        else if (order.equals("2")){
            System.out.println("how many people you want to message?");
            int n = CLI.input.nextInt();
            String username = "";
            for (int i = 0; i < n; i++) {
                username = CLI.input.next();
                if (username.equals("exit")){
                    CLI.exit();
                }
                pvChat pv = new pvChat(CLI.activeuser.Id, User.username2id(username));
                pv.sendMsg(msg, CLI.activeuser.Id);
            }
        }
        else if (order.equals("1")){
            System.out.println("enter name of category");
            String name = CLI.input.next();
            if (name.equals("exit")){
                CLI.exit();
            }
            Category ctgry = Category.getctg(name, CLI.activeuser);
            for (String person : ctgry.people) {
                pvChat pv = new pvChat(CLI.activeuser.Id, person);
                pv.sendMsg(msg, CLI.activeuser.Id);
            }
        }
    }
    static void categories(){
        System.out.println("1-new category\n2-add people to an old category\n3-back");
        String order = CLI.input.next();
        if (order.equals("exit")){
            CLI.exit();
        }
        else if(order.equals("3")){
            return;
        }
        else if (order.equals("1")){
            System.out.println("enter the name of category");
            String name = CLI.input.next();
            if (name.equals("exit")){
                CLI.exit();
            }
            Category ctgry = new Category(name);
            CLI.activeuser.categories.add(ctgry);
            System.out.println("how many people it has?");
            int n = CLI.input.nextInt();
            for (int i = 0; i < n; i++) {
                System.out.println("enter his/her username");
                name = CLI.input.next();
                ctgry.addperson(User.username2id(name));
            }
            System.out.println("done!");
        }
        else if (order.equals("2")){
            if (CLI.activeuser.categories.equals(new LinkedList<>())){
                System.out.println("you have no categories here");
                return;
            }
            System.out.println("enter the name of category");
            String name = CLI.input.next();
            if (name.equals("exit")){
                CLI.exit();
            }
            Category ctg = Category.getctg(name, CLI.activeuser);
            System.out.println("people in this category are :");
            for (String person : ctg.people) {
                System.out.println(User.id2username(person));
            }
            System.out.println("enter username of the person you want to add");
            String username = CLI.input.next();
            ctg.addperson(User.username2id(username));
        }

    }
}
