import java.util.LinkedList;

public class pvChat {
    //static private LinkedList<pvChat> pvsList= new LinkedList<>();
    String id1;
    String id2;
    LinkedList<Message> messages;
    int unreadid1;
    int unreadid2;
    public pvChat(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
        this.unreadid1 = 0;
        this.unreadid2 = 0;
        this.messages = new LinkedList<>();
        for (User user : User.userList) {
            if (user.Id.equals(id1)){
                user.pvs.add(this);
            }
            if (user.Id.equals(id2)){
                user.pvs.add(this);
            }
        }
    }
    void sendMsg(String text, String giverId){
        String reciverId = "";
        if (giverId.equals(id1)){
            reciverId = id2;
        }
        else{
            reciverId = id1;
        }
        Message msg = new Message(reciverId, giverId, text);
        this.messages.add(msg);
        for (pvChat pv : User.getUser(reciverId).pvs) {
            if (pv.id1.equals(giverId) || pv.id2.equals(giverId)){
                if (!pv.messages.contains(msg)){
                    pv.messages.add(msg);
                    if (reciverId.equals(this.id1)){
                        pv.unreadid1++;
                    }
                    else{
                        pv.unreadid2++;
                    }
                }
                break;
            }
        }
        if (reciverId.equals(this.id1)){
            this.unreadid1++;
        }
        else{
            this.unreadid2++;
        }
    }
    void sendMsg(Tweet text, String giverId){
        String reciverId = "";
        if (giverId.equals(id1)){
            reciverId = id2;
        }
        else{
            reciverId = id1;
        }
        Message msg = new Message(reciverId, giverId, text);
        this.messages.add(msg);
        for (pvChat pv : User.getUser(reciverId).pvs) {
            if (pv.id1.equals(giverId) || pv.id2.equals(giverId)) {
                if (!pv.messages.contains(msg)) {
                    pv.messages.add(msg);
                    if (reciverId.equals(this.id1)) {
                        pv.unreadid1++;
                    }
                    else {
                        pv.unreadid2++;
                    }
                }
                break;
            }
        }
        if (reciverId.equals(this.id1)){
            this.unreadid1++;
        }
        else{
            this.unreadid2++;
        }
    }
}

