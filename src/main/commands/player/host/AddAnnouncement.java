package main.commands.player.host;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.Command;
import main.CommandVisitor;
import main.SearchBar;
import main.User;
import main.commands.types.Announcement;
import main.users.Artist;
import main.users.Host;

public class AddAnnouncement implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String name;
    @JsonIgnore
    private final String description;
    private String message;


    public void execute(User user, Artist artist, Host host) {
        addAnnouncement(user, artist, host);
    }


    public AddAnnouncement(SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
        this.description = input.getDescription();
    }


    public void addAnnouncement(User user, Artist artist, Host host) {


        if (user != null || artist != null) {
            this.message = this.user + " is not a host.";
            return;
        } else if (host == null) {
            this.message = "The username " + this.user + " doesn't exist.";
            return;
        }

//        verifying if an announcement with the same name exists;
        for (int i = 0; i < host.getAnnouncements().size(); i++) {
            if (host.getAnnouncements().get(i).getName().equals(this.name)) {
                this.message = "The host " + this.user + " already has an announcement with the same name.";
                return;
            }
        }

        host.getAnnouncements().add(new Announcement(this.name, this.description, this.user));
        this.message = this.user + " has successfully added new announcement.";
    }


    public void accept(CommandVisitor commandVisitor) {
        commandVisitor.visit(this);
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
