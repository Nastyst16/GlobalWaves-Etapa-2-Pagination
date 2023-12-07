package main.commands.types;

public class Merch {
    private final String user;
    private final String name;
    private final String description;
    private final int price;


    public Merch (String user, String name, String description, int price) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
