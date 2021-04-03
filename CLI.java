import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CLI {
    public static Scanner input = new Scanner(System.in).useDelimiter("\n");
    static DateTimeFormatter dtformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static DateTimeFormatter dformatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static User activeuser;
    static String state;
    static void exit(){
        Logic.savedata();
        Logic.logging("info", "program ended");
        System.exit(0);
    }
    public static void main(String[] args) throws IOException {
        try {
            Logic.logging("info", "program started");
            state = "login";
            String order = "";
            Logic.readdata();
            Logic.logging("info", "connected to data files");
            while (true){
                if (state.equals("login")){
                    System.out.println("=========================================================================");
                    Logic.login();
                    Logic.savedata();
                }
                else if (state.equals("main page")){
                    activeuser.lastseennow();
                    System.out.println("=========================================================================");
                    System.out.println("1-personal page\n2-time line\n3-explorer\n4-messaging\n5-setting");
                    order = input.next();
                    if (order.equals("1")){
                        PersonalPage.main();
                    }
                    else if (order.equals("2")){
                        activeuser.lastseennow();
                        Logic.savedata();
                        TimeLine.main();
                    }
                    else if (order.equals("3")){
                        activeuser.lastseennow();
                        Logic.savedata();
                        Explorer.main();
                    }
                    else if (order.equals("4")){
                        activeuser.lastseennow();
                        Logic.savedata();
                        Messaging.main();
                    }
                    else if (order.equals("5")){
                        activeuser.lastseennow();
                        Logic.savedata();
                        Setting.main();
                    }
                    else if (order.equals("exit")){
                        activeuser.lastseennow();
                        Logic.savedata();
                        exit();
                    }
                }
            }
        }
        catch (Exception e){
            Logic.logging("error", e.getMessage());
        }
    }
}
