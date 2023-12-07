package main.commands.player.artist;

import main.Command;
import main.CommandVisitor;
import main.SearchBar;

public class AddMerch implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    private final String name;
    private final String description;
    private final double price;
    private String message;


    public AddMerch(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
        this.description = input.getDescription();
        this.price = input.getPrice();
    }
                    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }

    public String getCommand() {
        return command;
    }

    public String getUser() {
        return user;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
