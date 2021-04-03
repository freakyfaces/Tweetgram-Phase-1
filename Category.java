import java.util.LinkedList;

public class Category {
    LinkedList<String> people;
    String name;
    public Category(String name){
        this.name = name;
        people = new LinkedList<>();
    }
    void addperson(String id){
        this.people.add(id);
    }
    static Category getctg(String name, User user){
        for (Category category : user.categories) {
            if (category.name.equals(name)){
                return category;
            }
        }
        return new Category("none");
    }
}
