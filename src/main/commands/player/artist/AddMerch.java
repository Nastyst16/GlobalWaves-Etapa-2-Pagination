package main.commands.player.artist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.users.User;
import main.commands.types.Merch;
import main.users.Artist;
import main.users.Host;

public class AddMerch implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String name;
    @JsonIgnore
    private final String description;
    @JsonIgnore
    private final int price;
    private String message;


    public void execute(User user, Artist artist, Host host) {
        this.setMerch(user, artist, host);
    }

    public AddMerch(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
        this.description = input.getDescription();
        this.price = input.getPrice();
    }


    public void setMerch(User user, Artist artist, Host host) {

        if (user != null || host != null) {
            this.message = this.user + " is not an artist.";
            return;
        } else if (artist == null) {
            this.message = "The username " + this.user + " doesn't exist.";
            return;
        }

        for (Merch merch : artist.getMerchandise()) {
            if (merch.getName().equals(this.name)) {
                this.message = this.user + " has merchandise with the same name.";
                return;
            }
        }

        if (this.price < 0) {
            this.message = "Price for merchandise can not be negative.";
            return;
        }

        artist.getMerchandise().add(new Merch(this.user, this.name, this.description, this.price));
        this.setMessage(this.user + " has added new merchandise successfully.");
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
