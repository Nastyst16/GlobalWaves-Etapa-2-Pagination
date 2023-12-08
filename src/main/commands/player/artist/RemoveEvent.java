package main.commands.player.artist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.User;
import main.commands.types.Album;
import main.commands.types.Event;
import main.commands.types.Podcast;
import main.commands.types.Song;
import main.users.Artist;
import main.users.Host;

import java.util.ArrayList;

public class RemoveEvent implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String name;
    private String message;

    public void execute(User user, Artist artist, Host host, ArrayList<User> users) {
        this.setRemoveEvent(user, artist, host, users);
    }


    public void setRemoveEvent(User user, Artist artist, Host host, ArrayList<User> users) {

        if (user != null || host != null) {
            this.message = this.user + " is not an artist.";
            return;
        } else if (artist == null) {
            this.message = "The username " + this.user + " doesn't exist.";
            return;
        }

//        verifying if the event we want to remove exists
        boolean exists = false;

        for (Event e : artist.getEvents()) {
            if (e.getName().equals(this.name)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            this.setMessage(this.user + " doesn't have an event with the given name.");
            return;
        }


        for (int i = 0; i < artist.getEvents().size(); i++) {
            if (artist.getEvents().get(i).getName().equals(this.name)) {
                artist.getEvents().remove(i);
                this.setMessage(this.user + " deleted the event successfully.");
                return;
            }
        }

        this.setMessage(this.user + " doesn't have an event with the given name.");
    }


    public RemoveEvent(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
    }


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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
